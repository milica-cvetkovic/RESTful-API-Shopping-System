/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistem3;

import entity.Artikal;
import entity.Grad;
import entity.Korisnik;
import entity.Korpa;
import entity.Narudzbina;
import entity.Sadrzi;
import entity.SadrziPK;
import entity.Stavka;
import entity.Transakcija;
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
import java.text.*;

/**
 *
 * @author milic
 */
public class Sistem3 {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sistem3PU");
    static EntityManager em = emf.createEntityManager();

    @Resource(lookup="connectionFactory")
    static ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicProjekat")
    static Topic topic;
    
    @Resource(lookup="queueProjekat")
    static Queue queue;
    
    @Resource(lookup="queue13")
    static Queue queue13;
    
    @Resource(lookup="queue23")
    static Queue queue23;
    
    @Resource(lookup="queue32")
    static Queue queue32;
    
    @Resource(lookup="queue31")
    static Queue queue31;
    
    JMSContext context;
    JMSConsumer consumer;
    JMSProducer producer;
    
    JMSConsumer consumer13;
    JMSConsumer consumer23;
    JMSProducer producer32;
    JMSProducer producer31;
    
    
    private TextMessage placanje(String username){
        
        try {

            TextMessage txtMsg = null;

            List<Korpa> korpe = em.createNamedQuery("Korpa.findAll", Korpa.class).getResultList();

            Korpa korpa = null;
            for(Korpa k: korpe){
                if(k.getIdkorisnika().getUsername().equals(username)){
                    korpa = k;
                }
            }
            
            if(korpa == null){

                txtMsg = context.createTextMessage("Korpa ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;

            }
            
            List<Sadrzi> sadrzi = em.createNamedQuery("Sadrzi.findByIdkorpa", Sadrzi.class).setParameter("idkorpa", korpa.getIdkorpa()).getResultList();

            if(sadrzi.size() == 0){

                txtMsg = context.createTextMessage("Korpa je prazna");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;

            }
            
            float umanji = korpa.getUkupnacena();
            
            Korisnik korisnik = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();
            
            
            if(korpa.getUkupnacena() > korisnik.getNovac()){
                
                txtMsg = context.createTextMessage("Nema dovoljno novca!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
                
            }
            
            Date date = new Date();
            
            Narudzbina narudzbina = new Narudzbina();
            narudzbina.setAdresa(korisnik.getAdresa());
            narudzbina.setIdgrad(korisnik.getIdgrad());
            narudzbina.setUkupnacena(korpa.getUkupnacena());
            narudzbina.setVreme(date);
            narudzbina.setIdkorisnika(korisnik);
            
            em.getTransaction().begin();
            em.persist(narudzbina);
            em.getTransaction().commit();
            
            Transakcija transakcija = new Transakcija();
            
            transakcija.setVreme(date);
            transakcija.setSuma(korpa.getUkupnacena());
            transakcija.setIdnarudzbina(narudzbina);
            
            em.getTransaction().begin();
            em.persist(transakcija);
            em.getTransaction().commit();
            
            for(Sadrzi s: sadrzi){
                Artikal a = s.getArtikal();
                
                Stavka st = new Stavka();
                st.setIdartikal(a);
                st.setIdnarudzbina(narudzbina);
                st.setKolicina(s.getKolicina());
                st.setCena(a.getCena());
                
                em.getTransaction().begin();
                em.persist(st);
                em.getTransaction().commit();
                
                em.getTransaction().begin();
                em.remove(s);
                em.getTransaction().commit();
                
            }
            
            em.getTransaction().begin();
            em.remove(korpa);
            em.getTransaction().commit();
            
            korisnik.setNovac(korisnik.getNovac() - umanji);
            
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 10);
            txtMsg1.setStringProperty("username", username);
            
            producer32.send(queue32, txtMsg1);
            
            txtMsg1.setFloatProperty("umanji", umanji);
            
            producer31.send(queue31, txtMsg1);
            
            txtMsg = context.createTextMessage("Placeno!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
        }catch (JMSException ex) {
            Logger.getLogger(Sistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    private ObjectMessage dohvatanjeNarudzbina(String username){
        
        try {
            
            ObjectMessage objMsg = null;
            
            List<Narudzbina> narudzbine = em.createNamedQuery("Narudzbina.findAll", Narudzbina.class).getResultList();
            
            if(narudzbine.size() == 0){

                objMsg = context.createObjectMessage();
                objMsg.setIntProperty("status", 1);
                return objMsg;
            }
            
            ArrayList<String> sending = new ArrayList<String>();
            
            for(Narudzbina n:narudzbine){
                if(n.getIdkorisnika().getUsername().equals(username)){
                    sending.add(n.getUkupnacena() + ";" + n.getVreme());
                }
            }   
            
            
            objMsg = context.createObjectMessage(sending);
            objMsg.setIntProperty("status", 0);
            
            return objMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    private ObjectMessage dohvatiNarudzbine(){
        
        try {
            
            ObjectMessage objMsg = null;
            
            List<Narudzbina> narudzbine = em.createNamedQuery("Narudzbina.findAll", Narudzbina.class).getResultList();
            
            if(narudzbine.size() == 0){

                objMsg = context.createObjectMessage();
                objMsg.setIntProperty("status", 1);
                return objMsg;
            }
            
            ArrayList<String> sending = new ArrayList<String>();
            
            for(Narudzbina n:narudzbine){
                sending.add(n.getIdkorisnika().getUsername() + ";" + n.getUkupnacena() + ";" + n.getVreme());
            }   
            
            objMsg = context.createObjectMessage(sending);
            objMsg.setIntProperty("status", 0);
            
            return objMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    private ObjectMessage dohvatiTransakcije(){
        
        try {
            
            ObjectMessage objMsg = null;
            
            List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
            
            if(transakcije.size() == 0){

                objMsg = context.createObjectMessage();
                objMsg.setIntProperty("status", 1);
                return objMsg;
            }
            
            ArrayList<String> sending = new ArrayList<String>();
            
            for(Transakcija t:transakcije){
                sending.add(t.getIdnarudzbina().getIdkorisnika().getUsername() + ";" + t.getSuma() + ";" + t.getVreme());
            }   
            
            objMsg = context.createObjectMessage(sending);
            objMsg.setIntProperty("status", 0);
            objMsg.setStringProperty("radi", "evomebre");
            
            return objMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    private void listener13(Message msg){
        
        String username;
        float novac ;
        Korisnik korisnik;
        String adresa, grad;
        String naziv,drzava;
        
        try {
            Message response = null;
            
            int choice = msg.getIntProperty("choice");
            
            switch(choice){
                case 1:
                    naziv = msg.getStringProperty("naziv");
                    drzava = msg.getStringProperty("drzava");
                    
                    Grad gg  = new Grad();
                    gg.setNaziv(naziv);
                    gg.setDrzava(drzava);;
                    
                    em.getTransaction().begin();
                    em.persist(gg);
                    em.getTransaction().commit();
                    
                    break;
                    
                case 2:
                    username = msg.getStringProperty("username");
                    String ime = msg.getStringProperty("ime");
                    String prezime = msg.getStringProperty("prezime");
                    novac = msg.getFloatProperty("novac");
                    adresa = msg.getStringProperty("adresa");
                    grad = msg.getStringProperty("grad");
                    
                    Grad g = em.createNamedQuery("Grad.findByNaziv", Grad.class).setParameter("naziv", grad).getSingleResult();
                    
                    korisnik = new Korisnik();
                    korisnik.setUsername(username);
                    korisnik.setIme(ime);
                    korisnik.setPrezime(prezime);
                    korisnik.setNovac(novac);
                    korisnik.setAdresa(adresa);
                    korisnik.setIdgrad(g);
                    
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
                case 4:
                    username = msg.getStringProperty("username");
                    adresa = msg.getStringProperty("adresa");
                    grad = msg.getStringProperty("grad");
                    
                    korisnik = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();
                    
                    Grad g1 = em.createNamedQuery("Grad.findByNaziv", Grad.class).setParameter("naziv", grad).getSingleResult();
                    
                    korisnik.setAdresa(adresa);
                    korisnik.setIdgrad(g1);
                    
                    em.getTransaction().begin();
                    em.persist(korisnik);
                    em.getTransaction().commit();
                    
                    break;
                    
            }
        } catch (JMSException ex) {
            Logger.getLogger(Sistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void listener23(Message msg){
        
        String username;
        float novac ;
        Korisnik korisnik;
        String adresa, grad;
        String naziv,drzava;
        Artikal a;
        String opis;
        float cena;
        int popust, kolicina;
        
        try {
            Message response = null;
            
            int choice = msg.getIntProperty("choice");
            
            switch(choice){
                case 5:
                    naziv = msg.getStringProperty("naziv");
                    opis = msg.getStringProperty("opis");
                    cena = msg.getFloatProperty("cena");
                    popust = msg.getIntProperty("popust");
                    
                    a = new Artikal();
                    a.setNaziv(naziv);
                    a.setOpis(opis);
                    a.setCena(cena);
                    a.setPopust(popust);
                    
                    em.getTransaction().begin();
                    em.persist(a);
                    em.getTransaction().commit();
                    
                    break;
                case 6:
                    
                    naziv = msg.getStringProperty("naziv");
                    cena = msg.getFloatProperty("cena");
                    
                    a = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getSingleResult();
                    
                    a.setCena(cena);
                    
                    em.getTransaction().begin();
                    em.persist(a);
                    em.getTransaction().commit();
                    
                    break;
                case 7:
                    
                    naziv = msg.getStringProperty("naziv");
                    popust = msg.getIntProperty("popust");
                    
                    a = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getSingleResult();
                    
                    a.setPopust(popust);
                    
                    em.getTransaction().begin();
                    em.persist(a);
                    em.getTransaction().commit();
                    
                    break;
                case 8:
                    
                    naziv = msg.getStringProperty("naziv");
                    kolicina = msg.getIntProperty("kolicina");
                    username = msg.getStringProperty("username");
                    
                    a = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getSingleResult();
                    
                    Korisnik kk = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();

                    List<Korpa> korpe = em.createQuery("SELECT k FROM Korpa k", Korpa.class).getResultList();
                    
                    Korpa korpa = null;
                    for(Korpa k: korpe){
                        if(k.getIdkorisnika().getUsername().equals(username)){
                            korpa = k;
                            break;
                        }
                    }
                    
                    if(korpa == null){
                
                        korpa = new Korpa();
                        korpa.setIdkorisnika(kk);
                        korpa.setUkupnacena(0);

                        em.getTransaction().begin();
                        em.persist(korpa);
                        em.getTransaction().commit();
                    }
                    
                    float pop = a.getCena()*a.getPopust()/100;
                    float c = a.getCena();
                    float sum = c-pop;
                     float uk = korpa.getUkupnacena();
                    sum = sum*kolicina + uk;
                    
                    korpa.setUkupnacena(sum);
                    
                    List<Sadrzi> sadrzi = em.createNamedQuery("Sadrzi.findByIdkorpa", Sadrzi.class).setParameter("idkorpa", korpa.getIdkorpa()).getResultList();
            
                    Sadrzi s = null;
                    
                    if(sadrzi.size() == 0){

                        s = new Sadrzi();
                        s.setSadrziPK(new SadrziPK(korpa.getIdkorpa(), a.getIdartikal()));
                        s.setArtikal(a);
                        s.setKorpa(korpa);
                        s.setKolicina(kolicina);
                    }
                    else{
                        s = sadrzi.get(0);
                        if(s.getArtikal().getNaziv().equals(a.getNaziv())){
                            s.setKolicina(s.getKolicina() + kolicina);
                        }
                        else{

                            s = new Sadrzi();
                            s.setSadrziPK(new SadrziPK(korpa.getIdkorpa(), a.getIdartikal()));
                            s.setArtikal(a);
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
                    
                    break;
                case 9:
                    
                    naziv = msg.getStringProperty("naziv");
                    kolicina = msg.getIntProperty("kolicina");
                    username = msg.getStringProperty("username");
                    
                    a = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", naziv).getSingleResult();
                    
                    Korisnik kk1 = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();

                    List<Korpa> korpe1 = em.createQuery("SELECT k FROM Korpa k", Korpa.class).getResultList();
                    
                    Korpa korpa1 = null;
                    for(Korpa k: korpe1){
                        if(k.getIdkorisnika().getUsername().equals(username)){
                            korpa1 = k;
                            break;
                        }
                    }
                    
                    Sadrzi ss = null;
            
                    List<Sadrzi> sadrzi1 = em.createNamedQuery("Sadrzi.findByIdkorpa", Sadrzi.class).setParameter("idkorpa", korpa1.getIdkorpa()).getResultList();
            
                    for(Sadrzi ss1: sadrzi1){
                        if(ss1.getArtikal().getNaziv().equals(a.getNaziv())){
                            ss = ss1;
                            break;
                        }
                    }
                    
                    
                    if(ss.getKolicina() < kolicina){
                
                        em.getTransaction().begin();
                        em.remove(ss);
                        em.getTransaction().commit();
                        
                        float pop1 = a.getCena()*a.getPopust()/100;
                        float c1 = a.getCena();
                        float sum1 = c1-pop1;
                        float uk1 = korpa1.getUkupnacena();
                        float kol = ss.getKolicina();
                        sum1 = uk1 - sum1*kol ;

                        korpa1.setUkupnacena(sum1);

                    } else{
                        ss.setKolicina(ss.getKolicina() - kolicina);
                        
                        float pop1 = a.getCena()*a.getPopust()/100;
                        float c1 = a.getCena();
                        float sum1 = c1-pop1;
                        float uk1 = korpa1.getUkupnacena();
                        sum1 = uk1 - sum1*kolicina ;
                        
                        korpa1.setUkupnacena(sum1);
                    }

                    em.getTransaction().begin();
                    em.persist(korpa1);
                    em.getTransaction().commit();

                    em.getTransaction().begin();
                    em.persist(ss);
                    em.getTransaction().commit();

                    break;
            }
        } catch (JMSException ex) {
            Logger.getLogger(Sistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void radi(){
        
        context = connectionFactory.createContext();
        context.setClientID("3");
        
        consumer = context.createConsumer(topic, "podsistem=3");
        //consumer = context.createDurableConsumer(topic, "sub1", "podsistem=1", false);
        producer = context.createProducer();
        
        consumer13 = context.createConsumer(queue13);
        consumer23 = context.createConsumer(queue23);
        
        consumer13.setMessageListener((Message msg) -> {listener13(msg);});
        consumer23.setMessageListener((Message msg) -> {listener23(msg);});
        
        producer32 = context.createProducer();
        producer31 = context.createProducer();
        
        while(true){
            
            System.out.println("Waiting...");
            
            try {
                
                TextMessage txtMsg = (TextMessage) consumer.receive();
                int choice = txtMsg.getIntProperty("choice");
                Message response = null;
                String username;
                float novac;
                String adresa;
                String grad;
                
                switch(choice){
                    
                    case 1: 
                        username = txtMsg.getStringProperty("username");
                        response = placanje(username);
                        break;
                    case 17:
                        username = txtMsg.getStringProperty("username");
                        
                        response = dohvatanjeNarudzbina(username);
                        if(response instanceof ObjectMessage) System.out.println("true");
                        break;
                    case 18:
                        response = dohvatiNarudzbine();
                        break;
                    case 19:
                        response = dohvatiTransakcije();
                        break;
                }
                
                producer.send(queue, response);
                
            } catch (JMSException ex) {
                Logger.getLogger(Sistem3.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    public static void main(String[] args) {
        Sistem3 sistem = new Sistem3();
        sistem.radi();
    }
    
}
