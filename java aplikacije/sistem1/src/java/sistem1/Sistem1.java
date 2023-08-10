/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistem1;

import entity.Grad;
import entity.Korisnik;
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
public class Sistem1 {
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sistem1PU");
    static EntityManager em = emf.createEntityManager();

    @Resource(lookup="connectionFactory")
    static ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicProjekat")
    static Topic topic;
    
    @Resource(lookup="queueProjekat")
    static Queue queue;
    
    @Resource(lookup="queue12")
    static Queue queue12;
    
    @Resource(lookup="queue13")
    static Queue queue13;
    
    @Resource(lookup="queue31")
    static Queue queue31;
    
    JMSContext context;
    JMSConsumer consumer;
    JMSProducer producer;
    
    JMSProducer producer12;
    JMSProducer producer13;
    JMSConsumer consumer31;
    
    private TextMessage kreiranjeGrada(String naziv, String drzava){
        
        try {
            
            TextMessage txtMsg = null;
            
            List<Grad> gradovi = em.createNamedQuery("Grad.findByNaziv", Grad.class).setParameter("naziv", naziv).getResultList();
            
            if(gradovi.size() != 0){
                System.out.println("vec postoji");
                txtMsg = context.createTextMessage("Grad vec postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Grad grad = new Grad();
            grad.setNaziv(naziv);
            grad.setDrzava(drzava);
            
            em.getTransaction().begin();
            em.persist(grad);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 1);
            txtMsg1.setStringProperty("naziv", grad.getNaziv());
            txtMsg1.setStringProperty("drzava", grad.getDrzava());
            
            producer13.send(queue13, txtMsg1);
            
            txtMsg = context.createTextMessage("Kreiran grad!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return null;
        
    }
    
    private TextMessage kreiranjeKorisnika(String username, String password, String ime, String prezime, float novac, String adresa, String grad){
        
        try{
            TextMessage txtMsg = null;

            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getResultList();

            if(korisnici.size() != 0){

                txtMsg = context.createTextMessage("Vec postoji korisnik sa zadatim username-om!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            List<Grad> gradovi = em.createNamedQuery("Grad.findByNaziv", Grad.class).setParameter("naziv", grad).getResultList();
            
            if(gradovi.size() == 0){
                txtMsg = context.createTextMessage("Ne postoji zadati grad!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Korisnik korisnik = new Korisnik();
            korisnik.setUsername(username);
            korisnik.setPassword(password);
            korisnik.setIme(ime);
            korisnik.setPrezime(prezime);
            korisnik.setNovac(novac);
            korisnik.setAdresa(adresa);
            korisnik.setIdgrad(gradovi.get(0));
            
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 2);
            txtMsg1.setStringProperty("username", korisnik.getUsername());
            txtMsg1.setStringProperty("ime", korisnik.getIme());
            txtMsg1.setStringProperty("prezime", korisnik.getPrezime());
            txtMsg1.setFloatProperty("novac", korisnik.getNovac());
            
            producer12.send(queue12, txtMsg1);
            
            txtMsg1.setStringProperty("adresa", korisnik.getAdresa());
            txtMsg1.setStringProperty("grad", korisnik.getIdgrad().getNaziv());
            
            producer13.send(queue13, txtMsg1);
            
            txtMsg = context.createTextMessage("Kreiran korisnik!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            

        } catch (JMSException ex) {
                Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
        
    }
    
    private TextMessage dodavanjeNovca(String username, float novac){
        
        try {
            TextMessage txtMsg = null;
            
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username",username).getResultList();
            if(korisnici.size() == 0){
                txtMsg = context.createTextMessage("Korisnik ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Korisnik korisnik = korisnici.get(0);
            
            System.out.println(korisnik.getNovac() + " " + novac);
            korisnik.setNovac(korisnik.getNovac() + novac);
            System.out.println(korisnik.getNovac());
            
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 3);
            txtMsg1.setStringProperty("username", korisnik.getUsername());
            txtMsg1.setFloatProperty("novac", korisnik.getNovac());
            
            producer12.send(queue12, txtMsg1);
            producer13.send(queue13, txtMsg1);
            
            txtMsg = context.createTextMessage("Dodat novac korisniku!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private TextMessage promenaAdreseIGrada(String adresa, String grad, String username){
        
        try {
            TextMessage txtMsg = null;

            List<Grad> gradovi = em.createNamedQuery("Grad.findByNaziv", Grad.class).setParameter("naziv", grad).getResultList();

            if(gradovi.size() == 0){
            
                txtMsg = context.createTextMessage("Ne postoji zadati grad!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            } 
            
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username",username).getResultList();
            
            if(korisnici.size() == 0){
                txtMsg = context.createTextMessage("Korisnik ne postoji!");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Korisnik korisnik = korisnici.get(0);
            korisnik.setAdresa(adresa);
            korisnik.setIdgrad(gradovi.get(0));
            
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            
            TextMessage txtMsg1 = context.createTextMessage();
            txtMsg1.setIntProperty("choice", 4);
            txtMsg1.setStringProperty("username", korisnik.getUsername());
            txtMsg1.setStringProperty("adresa", korisnik.getAdresa());
            txtMsg1.setStringProperty("grad", korisnik.getIdgrad().getNaziv());
            
            producer12.send(queue12, txtMsg1);
            producer13.send(queue13, txtMsg1);
            
            
            txtMsg = context.createTextMessage("Promenjeni adresa i grad!");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
            
        }catch (JMSException ex) {
                Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
        }  
            
        return null;
        
    }
    
        
    private ObjectMessage dohvatanjeSvihKorisnika(){
        
        try {
            
            ObjectMessage txtMsg = null;
            
            List<Korisnik> korisnici = (List<Korisnik>) em.createNamedQuery("Korisnik.findAll", Korisnik.class).getResultList();
            
            ArrayList<String> sending = new ArrayList<String>();
            
            for(Korisnik k: korisnici){
                sending.add(k.getUsername() + ";" + k.getIme() + ";" + k.getPrezime() + ";" + k.getNovac() + ";" + k.getAdresa() + ";" + k.getIdgrad().getNaziv());
                
            }   
            
            txtMsg = context.createObjectMessage(sending);
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private ObjectMessage dohvatanjeSvihGradova(){
        
        try {
            ObjectMessage objMsg = null;
            
            List<Grad> gradovi = (List<Grad>) em.createNamedQuery("Grad.findAll", Grad.class).getResultList();
            
            ArrayList<String> sending = new ArrayList<String>();
            
            for(Grad g: gradovi){
                sending.add(g.getNaziv() + ";" + g.getDrzava());
            }   
            
            
            objMsg = context.createObjectMessage(sending);
            objMsg.setIntProperty("status", 0);
            
            System.out.println("niz" + sending.get(0));
            
            return objMsg;
            
        } catch (JMSException ex) {
            Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private TextMessage autentikacija(String username, String password){
        
        try {
            TextMessage txtMsg = null;
            
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username",username).getResultList();
            
            if(korisnici.size() == 0){
                txtMsg = context.createTextMessage("Nevalidno");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            Korisnik k = korisnici.get(0);
            
            System.out.println(k.getPassword() + password);
            
            if(!k.getPassword().equals(password)){
                txtMsg = context.createTextMessage("Nevalidno");
                txtMsg.setIntProperty("status", 1);
                return txtMsg;
            }
            
            txtMsg = context.createTextMessage("Ulogovan");
            txtMsg.setIntProperty("status", 0);
            
            return txtMsg;
            
            
        }catch (JMSException ex) {
                Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
        }  
            
        return null;
        
    }
    
    private void listener31(Message msg){
        
        String username;
        
        try {
            Message response = null;
            
            int choice = msg.getIntProperty("choice");
            
            switch(choice){
                case 10:
                    username = msg.getStringProperty("username");
                    float umanji = msg.getFloatProperty("umanji");
                    
                    Korisnik korisnik = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).setParameter("username", username).getSingleResult();
                    
                    korisnik.setNovac(korisnik.getNovac() - umanji);
            
                    em.getTransaction().begin();
                    em.persist(korisnik);
                    em.getTransaction().commit();
                    
                    break;
                    
            }
        } catch (JMSException ex) {
            Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void radi(){
        
        context = connectionFactory.createContext();
        context.setClientID("1");
        
        consumer = context.createConsumer(topic, "podsistem=1");
        //consumer = context.createDurableConsumer(topic, "sub1", "podsistem=1", false);
        producer = context.createProducer();
        
        producer12 = context.createProducer();
        producer13 = context.createProducer();
        
        consumer31 = context.createConsumer(queue31);
        consumer31.setMessageListener((Message msg) -> {listener31(msg);});
        
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
                        String naziv = txtMsg.getStringProperty("naziv");
                        String drzava = txtMsg.getStringProperty("drzava");
                        response = kreiranjeGrada(naziv, drzava);
                        break;
                    case 2:
                        username = txtMsg.getStringProperty("username");
                        String password = txtMsg.getStringProperty("password");
                        String ime = txtMsg.getStringProperty("ime");
                        String prezime = txtMsg.getStringProperty("prezime");
                        novac = txtMsg.getFloatProperty("novac");
                        adresa = txtMsg.getStringProperty("adresa");
                        grad = txtMsg.getStringProperty("grad");
                        response = kreiranjeKorisnika(username, password, ime, prezime, novac, adresa, grad);
                        break;
                    case 3:
                        username = txtMsg.getStringProperty("username");
                        novac = txtMsg.getFloatProperty("novac");
                        response = dodavanjeNovca(username, novac);
                        break;
                    case 4:
                         username = txtMsg.getStringProperty("username");
                         adresa = txtMsg.getStringProperty("adresa");
                         grad = txtMsg.getStringProperty("grad");
                         response = promenaAdreseIGrada(adresa, grad, username);
                         break;
                    case 12:
                        response = dohvatanjeSvihGradova();
                        break;
                    case 13:
                        response = dohvatanjeSvihKorisnika();
                        break;
                    case 20:
                        username = txtMsg.getStringProperty("username");
                        password = txtMsg.getStringProperty("password");
                        response = autentikacija(username, password);
                        break;
                }
                
                producer.send(queue, response);
                
            } catch (JMSException ex) {
                Logger.getLogger(Sistem1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    public static void main(String[] args) {
        
        Sistem1 s = new Sistem1();
        s.radi();
        
    }
    
}
