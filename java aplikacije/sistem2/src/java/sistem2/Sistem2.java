/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistem2;

import entity.Artikal;
import entity.Kategorija;
import entity.Korisnik;
import entity.Korpa;
import entity.Prodaje;
import entity.Sadrzi;
import entity.SadrziPK;
import entity.Sadrzi_;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.*;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

/**
 *
 * @author milic
 */
public class Sistem2 {

        
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sistem2PU");
    static EntityManager em = emf.createEntityManager();

    @Resource(lookup="connectionFactory")
    static ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicProjekat")
    static Topic topic;
    
    @Resource(lookup="queueProjekat")
    static Queue queue;
    
    @Resource(lookup = "queue12")
    static Queue queue12;
    
    @Resource(lookup="queue23")
    static Queue queue23;
    
    @Resource(lookup="queue32")
    static Queue queue32;
   
    
    JMSContext context;
    JMSConsumer consumer;
    JMSProducer producer;
    
    JMSConsumer consumer12;
    JMSProducer producer23;
    JMSConsumer consumer32;
    
    private TextMessage kreiranjeKategorije(String naziv){
        try {
            TextMessage txtMsg = null;

            List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", naziv).getResultList();

            if(kategorije.size() != 0){

                    txtMsg = context.createTextMessage("Kategorija vec postoji!");
                    txtMsg.setIntProperty("status", 1);
                    return txtMsg;
            }
            
            Kategorija kategorija = new Kategorija();
            kategorija.setNaziv(naziv);
            
            em.getTransaction().begin();
            em.persist(kategorija);
            em.getTransaction().commit();
            
            txtMsg = context.createTextMessage("Kreirana kategorija!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
        } catch (JMSException ex) {
                Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
    
    private TextMessage kreiranjeArtikla(String naziv, String opis, float cena, int popust, String kategorija, String username){
        
        try {
            TextMessage txtMsg = null;
            
            List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", kategorija).getResultList();

            if(kategorije.size() == 0){

                    txtMsg = context.createTextMessage("Kategorija ne postoji!");
                    txtMsg.setIntProperty("status", 1);
                    return txtMsg;
            }
            
            Kategorija k = kategorije.get(0);

            List<Artikal> artikli = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getResultList();

            if(artikli.size() != 0){

                txtMsg = context.createTextMessage("Artikal vec postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Artikal artikal = new Artikal();
            artikal.setNaziv(naziv);
            artikal.setOpis(opis);
            artikal.setCena(cena);
            artikal.setPopust(popust);
            artikal.setIdkategorija(k);
            
            em.getTransaction().begin();
            em.persist(artikal);
            em.getTransaction().commit();
            
            Korisnik korisnik = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();
            
            Prodaje prodaje = new Prodaje();
            prodaje.setIdkorisnik(korisnik);
            prodaje.setIdartikal(artikal.getIdartikal());
            prodaje.setArtikal(artikal);
            
            em.getTransaction().begin();
            em.persist(prodaje);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 5);
            txtMsg1.setStringProperty("naziv", artikal.getNaziv());
            txtMsg1.setFloatProperty("cena", artikal.getCena());
            txtMsg1.setStringProperty("opis", artikal.getOpis());
            txtMsg1.setIntProperty("popust", artikal.getPopust());
            
            producer23.send(queue23, txtMsg1);
            
            txtMsg = context.createTextMessage("Kreiran artikal!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
        
        } catch (JMSException ex) {
                Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return null; 
        
    }
    
    private TextMessage promeniCenuArtiklu(String naziv, float cena, String username){
        
        try {
            TextMessage txtMsg = null;
            
            List<Artikal> artikli = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getResultList();

            if(artikli.size() == 0){

                txtMsg = context.createTextMessage("Artikal ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            
            Artikal artikal = artikli.get(0);
            
            List<Prodaje> prodaje = em.createNamedQuery("Prodaje.findByIdartikal", Prodaje.class).setParameter("idartikal", artikal.getIdartikal()).getResultList();
            
            Prodaje pr = null;
            for(Prodaje p: prodaje){
                if(p.getIdkorisnik().getUsername().equals(username)){
                    pr = p;
                }
            }
            
            if(pr == null){
                
                txtMsg = context.createTextMessage("Korisnik ne prodaje artikal!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
                
            }
            
            artikal.setCena(artikal.getCena() + cena);
            
            em.getTransaction().begin();
            em.persist(artikal);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 6);
            txtMsg1.setStringProperty("naziv", artikal.getNaziv());
            txtMsg1.setFloatProperty("cena", artikal.getCena());
            
            producer23.send(queue23, txtMsg1);
            
            txtMsg = context.createTextMessage("Promenjena cena!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
        
        } catch (JMSException ex) {
                Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return null; 
        
    }
    
    private TextMessage postaviPopust(String naziv, int popust, String username){
        
        try {
            TextMessage txtMsg = null;
            
            List<Artikal> artikli = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getResultList();

            if(artikli.size() == 0){

                txtMsg = context.createTextMessage("Artikal ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Artikal artikal = artikli.get(0);
            
            List<Prodaje> prodaje = em.createNamedQuery("Prodaje.findByIdartikal", Prodaje.class).setParameter("idartikal", artikal.getIdartikal()).getResultList();
            
            Prodaje pr = null;
            for(Prodaje p: prodaje){
                if(p.getIdkorisnik().getUsername().equals(username)){
                    pr = p;
                }
            }
            
            if(pr == null){
                
                txtMsg = context.createTextMessage("Korisnik ne prodaje artikal!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
                
            }
            
            artikal.setPopust(popust);
            
            em.getTransaction().begin();
            em.persist(artikal);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 7);
            txtMsg1.setStringProperty("naziv", artikal.getNaziv());
            txtMsg1.setIntProperty("popust", artikal.getPopust());
            
            producer23.send(queue23, txtMsg1);
            
            txtMsg = context.createTextMessage("Postavljen popust!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
        
        } catch (JMSException ex) {
                Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return null; 
        
    }
    
    private TextMessage dodajUKorpu(String naziv, int kolicina, String username){
        
        try {
            TextMessage txtMsg = null;
            
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getResultList();

            if(korisnici.size() == 0){

                txtMsg = context.createTextMessage("Korisnik ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Korisnik kor = korisnici.get(0);
            
            List<Artikal> artikli = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getResultList();

            
            if(artikli.size() == 0){

                txtMsg = context.createTextMessage("Artikal ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Artikal art = artikli.get(0);
            
            List<Korpa> korpe = em.createQuery("SELECT k FROM Korpa k", Korpa.class).getResultList();
            
            int found = 0;
            Korpa korpa = null;
            for(Korpa k: korpe){
                if(k.getIdkorisnik().getUsername().equals(username)){
                    korpa = k;
                    found = 1;
                    break;
                }
            }
            
            
            if(found == 0){
                
                korpa = new Korpa();
                korpa.setIdkorisnik(kor);
                korpa.setUkupnacena(0);

                em.getTransaction().begin();
                em.persist(korpa);
                em.getTransaction().commit();
            }

            float pop = art.getCena()*art.getPopust()/100;
            float c = art.getCena();
            float sum = c-pop;
            float uk = korpa.getUkupnacena();
            sum = sum*kolicina + uk;
            korpa.setUkupnacena(sum);
            
            Sadrzi s = null;
            
            List<Sadrzi> sadrzi = em.createNamedQuery("Sadrzi.findByIdkorpa", Sadrzi.class).setParameter("idkorpa", korpa.getIdkorpa()).getResultList();
            
            System.out.println(sadrzi.size());
            if(sadrzi.size() == 0){
                
                s = new Sadrzi();
                s.setSadrziPK(new SadrziPK(korpa.getIdkorpa(), art.getIdartikal()));
                s.setArtikal(art);
                s.setKorpa(korpa);
                s.setKolicina(kolicina);
            }
            else{
                
                int flag = 0;
                for(Sadrzi ss: sadrzi){
                    if(ss.getArtikal().getNaziv().equals(art.getNaziv())){
                        s=ss;
                        s.setKolicina(s.getKolicina() + kolicina);
                        flag = 1;
                    }
                }
                if(flag == 0){
                    s = new Sadrzi();
                    s.setSadrziPK(new SadrziPK(korpa.getIdkorpa(), art.getIdartikal()));
                    s.setArtikal(art);
                    s.setKorpa(korpa);
                    s.setKolicina(kolicina);
                }
            }
            
            
            
            em.getTransaction().begin();
            em.persist(korpa);
            em.getTransaction().commit();
            
                               
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 8);
            txtMsg1.setStringProperty("username", username);
            txtMsg1.setIntProperty("kolicina", kolicina);
            txtMsg1.setStringProperty("naziv", art.getNaziv());
            
            producer23.send(queue23, txtMsg1);
            
            
            txtMsg = context.createTextMessage("Uneto u korpu!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
        
        } catch (JMSException ex) {
                Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return null; 
        
    }
    
    private TextMessage brisiIzKorpe(String naziv, int kolicina, String username){
        
        try {
            TextMessage txtMsg = null;
            
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getResultList();

            if(korisnici.size() == 0){

                txtMsg = context.createTextMessage("Korisnik ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Korisnik kor = korisnici.get(0);
            
            List<Artikal> artikli = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getResultList();

            if(artikli.size() == 0){

                txtMsg = context.createTextMessage("Artikal ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Artikal art = artikli.get(0);
            
            List<Korpa> korpe = em.createQuery("SELECT k FROM Korpa k", Korpa.class).getResultList();
            
            if(korpe.size() == 0){

                txtMsg = context.createTextMessage("Korpa ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Korpa korpa = null;
            for(Korpa k: korpe){
                if(k.getIdkorisnik().getUsername().equals(username)){
                    korpa = k;
                    break;
                }
            }
            
            if(korpa== null){
                
                txtMsg = context.createTextMessage("Korpa ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
          
            
            Sadrzi s = null;
            
            List<Sadrzi> sadrzi = em.createNamedQuery("Sadrzi.findByIdkorpa", Sadrzi.class).setParameter("idkorpa", korpa.getIdkorpa()).getResultList();
            
            if(sadrzi.size() == 0){
                
                txtMsg = context.createTextMessage("Korpa je prazna!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            for(Sadrzi ss: sadrzi){
                if(ss.getArtikal().getNaziv().equals(art.getNaziv())){
                    s = ss;
                    break;
                }
            }
            
            if(s == null){
                
                txtMsg = context.createTextMessage("Artikal nije u korpi!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
                
            }
            
            if(s.getKolicina() <= kolicina){
                
                em.getTransaction().begin();
                em.remove(s);
                em.getTransaction().commit();
                
                float pop = art.getCena()*art.getPopust()/100;
                float c = art.getCena();
                float sum = c-pop;
                float uk = korpa.getUkupnacena();
                float kol = s.getKolicina();
                sum =uk - sum*kol ;
                
                korpa.setUkupnacena(sum);
                
            } else{
                s.setKolicina(s.getKolicina() - kolicina);
                
                float pop = art.getCena()*art.getPopust()/100;
                float c = art.getCena();
                float sum = c-pop;
                float uk = korpa.getUkupnacena();
                sum =uk - sum*kolicina ;
                
                korpa.setUkupnacena(sum);
            }
            
            em.getTransaction().begin();
            em.persist(korpa);
            em.getTransaction().commit();
            
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 9);
            txtMsg1.setStringProperty("username", username);
            txtMsg1.setIntProperty("kolicina", kolicina);
            txtMsg1.setStringProperty("naziv", art.getNaziv());
            
            producer23.send(queue23, txtMsg1);

            
            txtMsg = context.createTextMessage("Izbrisano iz korpe!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
        
        } catch (JMSException ex) {
                Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return null; 
        
    }
    
    private ObjectMessage dohvatanjeKategorija(){
        
        try {
            ObjectMessage objMsg = null;
            
            List<Kategorija> kategorije = (List<Kategorija>) em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
            
            ArrayList<String> sending = new ArrayList<String>();
            
            for(Kategorija k: kategorije){
                sending.add(k.getNaziv());
            }   
            
            
            objMsg = context.createObjectMessage(sending);
            objMsg.setIntProperty("status", 0);
            
            
            return objMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private ObjectMessage dohvatanjeProdavanihArtikala(String username){
        
        try {
            
            ObjectMessage objMsg = null;
            
            List<Prodaje> prodaje = em.createNamedQuery("Prodaje.findAll", Prodaje.class).getResultList();
            
            
            ArrayList<String> sending = new ArrayList<String>();
            
            for(Prodaje p: prodaje){
                System.out.println(p.getIdkorisnik().getUsername());
                System.out.println(p.getArtikal().getNaziv());
                if(p.getIdkorisnik().getUsername().equals(username)){
                    sending.add(p.getArtikal().getNaziv());
                }
                
            }   
            
            objMsg = context.createObjectMessage(sending);
            objMsg.setIntProperty("status", 0);
            
            
            
            return objMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    private ObjectMessage dohvatanjeSadrzaja(String username){
        
        try {
            ObjectMessage objMsg = null;
            
            List<Korpa> korpe = em.createQuery("SELECT k FROM Korpa k", Korpa.class).getResultList();
            
            if(korpe.size() == 0){

                objMsg = context.createObjectMessage();
                objMsg.setIntProperty("status", 1);
                return objMsg;
            }
            
            Korpa korpa = null;
            
            for(Korpa k: korpe){
                if(k.getIdkorisnik().getUsername().equals(username)){
                    korpa = k;
                    break;
                }
                
            }
            
            if(korpa == null){
                
                objMsg = context.createObjectMessage();
                objMsg.setIntProperty("status", 1);
                return objMsg;
            }
            
            
            List<Sadrzi> sadrzi = em.createNamedQuery("Sadrzi.findByIdkorpa", Sadrzi.class).setParameter("idkorpa", korpa.getIdkorpa()).getResultList();
            
            if(sadrzi.size() == 0){
                
                objMsg = context.createObjectMessage();
                objMsg.setIntProperty("status", 1);
                return objMsg;
            }
            
            ArrayList<String> sending = new ArrayList<String>();
            
            for(Sadrzi s: sadrzi){
                System.out.println(s.getArtikal().getNaziv());
                sending.add(s.getArtikal().getNaziv()+  ";" + s.getKolicina());
            }   
            
            
            objMsg = context.createObjectMessage(sending);
            objMsg.setIntProperty("status", 0);
            
            
            return objMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private void listener12(Message msg){
        
        String username;
        float novac ;
        Korisnik korisnik;
        String adresa, grad;
        
        try {
            Message response = null;
            
            int choice = msg.getIntProperty("choice");
            
            switch(choice){
                case 2:
                    username = msg.getStringProperty("username");
                    String ime = msg.getStringProperty("ime");
                    String prezime = msg.getStringProperty("prezime");
                    novac = msg.getFloatProperty("novac");
                    
                    korisnik = new Korisnik();
                    korisnik.setUsername(username);
                    korisnik.setIme(ime);
                    korisnik.setPrezime(prezime);
                    korisnik.setNovac(novac);
                    
                    em.getTransaction().begin();
                    em.persist(korisnik);
                    em.getTransaction().commit();
                    
                    break;
                case 3:
                    
                    username = msg.getStringProperty("username");
                    novac = msg.getFloatProperty("novac");
                    
                    korisnik = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();
                    
                    korisnik.setNovac(novac);
                    
                    em.getTransaction().begin();
                    em.persist(korisnik);
                    em.getTransaction().commit();
                    
                    break;
                    
            }
        } catch (JMSException ex) {
            Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void listener32(Message msg){
        
        String username;
        
        try {
            Message response = null;
            
            int choice = msg.getIntProperty("choice");
            
            switch(choice){
                case 10:
                    username = msg.getStringProperty("username");
                    
                    List<Korpa> korpe = em.createNamedQuery("Korpa.findAll", Korpa.class).getResultList();

                    Korpa korpa = null;
                    for(Korpa k: korpe){
                        if(k.getIdkorisnik().getUsername().equals(username)){
                            korpa = k;
                        }
                    }
                    
                    float umanji = korpa.getUkupnacena();
                    
                    List<Sadrzi> sadrzi = em.createNamedQuery("Sadrzi.findByIdkorpa", Sadrzi.class).setParameter("idkorpa", korpa.getIdkorpa()).getResultList();

                    for(Sadrzi s: sadrzi){
                        
                        em.getTransaction().begin();
                        em.remove(s);
                        em.getTransaction().commit();

                    }
                    
                    em.getTransaction().begin();
                    em.remove(korpa);
                    em.getTransaction().commit();
                    
                    Korisnik korisnik = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();
                    
                    korisnik.setNovac(korisnik.getNovac() - umanji);
            
                    em.getTransaction().begin();
                    em.persist(korisnik);
                    em.getTransaction().commit();
                    
                    break;
                    
            }
        } catch (JMSException ex) {
            Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void radi(){
        
        context = connectionFactory.createContext();
        context.setClientID("2");
        
        consumer = context.createConsumer(topic, "podsistem=2");
        //consumer = context.createDurableConsumer(topic, "sub1", "podsistem=1", false);
        producer = context.createProducer();
        
        int kolicina;
        String username;
        
        consumer12 = context.createConsumer(queue12);
        consumer12.setMessageListener((Message msg) -> {listener12(msg);});
        
        consumer32 = context.createConsumer(queue32);
        consumer32.setMessageListener((Message msg) -> {listener32(msg);});
        
        producer23 = context.createProducer();
        
        while(true){
            
            System.out.println("Waiting...");
            
            String naziv, kategorija, opis;
            float cena;
            int popust;
            
            try {
                TextMessage txtMsg = (TextMessage) consumer.receive();
                int choice = txtMsg.getIntProperty("choice");
                Message response = null;
                
                switch(choice){
                    
                    case 5:                
                        naziv = txtMsg.getStringProperty("naziv");
                        response = kreiranjeKategorije(naziv);
                        break;
                    case 6:
                        naziv = txtMsg.getStringProperty("naziv");
                        opis = txtMsg.getStringProperty("opis");
                        cena = txtMsg.getFloatProperty("cena");
                        popust = txtMsg.getIntProperty("popust");
                        kategorija = txtMsg.getStringProperty("kategorija");
                        username = txtMsg.getStringProperty("username");
                        response = kreiranjeArtikla(naziv, opis, cena, popust, kategorija, username);
                        break;
                    case 7:
                        System.out.println("udje case 7");
                        naziv = txtMsg.getStringProperty("naziv");
                        cena = txtMsg.getFloatProperty("cena");
                        username = txtMsg.getStringProperty("username");
                        response = promeniCenuArtiklu(naziv, cena, username);
                        break;
                    case 8:
                        naziv = txtMsg.getStringProperty("naziv");
                        popust = txtMsg.getIntProperty("popust");
                        username = txtMsg.getStringProperty("username");
                        response = postaviPopust(naziv, popust, username);
                        break;
                    case 9:
                        naziv = txtMsg.getStringProperty("naziv");
                        kolicina = txtMsg.getIntProperty("kolicina");
                        username = txtMsg.getStringProperty("username");
                        response = dodajUKorpu(naziv, kolicina, username);
                        break;
                    case 10:
                        naziv = txtMsg.getStringProperty("naziv");
                        kolicina = txtMsg.getIntProperty("kolicina");
                        username = txtMsg.getStringProperty("username");
                        response = brisiIzKorpe(naziv, kolicina, username);
                        break;
                    case 14:
                        response = dohvatanjeKategorija();
                        break;
                    case 15:
                        username = txtMsg.getStringProperty("username");
                        response = dohvatanjeProdavanihArtikala(username);
                        break;
                    case 16:
                        username = txtMsg.getStringProperty("username");
                        response = dohvatanjeSadrzaja(username);
                        break;
                }
                
                producer.send(queue, response);
                
            } catch (JMSException ex) {
                Logger.getLogger(Sistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
    }
    
    
    
    public static void main(String[] args) {
        Sistem2 sistem = new Sistem2();
        sistem.radi();
    }
    
}
