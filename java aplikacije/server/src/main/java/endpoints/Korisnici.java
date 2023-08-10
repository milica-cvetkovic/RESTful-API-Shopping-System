/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;

/**
 *
 * @author milic
 */
@Path("korisnici")
public class Korisnici {
    
    @Resource(lookup="connectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup="queueProjekat")
    Queue queue;
    
    @Resource(lookup="topicProjekat")
    Topic topic;
    
    @POST
    @Path("autentikacija")
    public Response autentikacija(@FormParam("username") String username, @FormParam("password") String password){

        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 1);
            txtMsg.setIntProperty("choice", 20);
            txtMsg.setStringProperty("username", username);
            txtMsg.setStringProperty("password", password);
            
            producer.send(topic, txtMsg);
            
            Message msg = consumer.receive();
            
            
            if(msg instanceof TextMessage){
                
                try {
                    
                    txtMsg = (TextMessage) msg;
                    
                    int code = txtMsg.getIntProperty("status");
                    
                    String text = txtMsg.getText();
                    
                    if(code != 0){
                        return Response.status(Response.Status.NO_CONTENT).entity(text).build();
                    }
                    return Response.status(Response.Status.OK).entity(text).build();
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Korisnici.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Korisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    @GET
    public Response dohvatanjeKorisnika(){
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 1);
            txtMsg.setIntProperty("choice", 13);
            
            producer.send(topic, txtMsg);
            
            Message msg = consumer.receive();
            
            while(!(msg instanceof ObjectMessage)){
                msg = consumer.receive();
            }
            
            if(msg instanceof ObjectMessage){
                
                try {
                    
                    ObjectMessage objMsg = (ObjectMessage) msg;
                    
                    int code = objMsg.getIntProperty("status");
                    
                    if(code != 0){
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                    return Response.status(Response.Status.OK).entity(new GenericEntity<ArrayList<String>> ((ArrayList<String>) objMsg.getObject()) {}).build();
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Korisnici.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
           
        } catch (JMSException ex) {
            Logger.getLogger(Korisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    @POST
    public Response kreiranjeKorisnika(@FormParam("username") String username, @FormParam("password") String password, @FormParam("ime") String ime, @FormParam("prezime") String prezime, @FormParam("novac") String novac, @FormParam("adresa") String adresa, @FormParam("grad") String grad){

        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 1);
            txtMsg.setIntProperty("choice", 2);
            txtMsg.setStringProperty("username", username);
            txtMsg.setStringProperty("password", password);
            txtMsg.setStringProperty("ime", ime);
            txtMsg.setStringProperty("prezime", prezime);
            txtMsg.setFloatProperty("novac", Float.parseFloat(novac));
            txtMsg.setStringProperty("adresa", adresa);
            txtMsg.setStringProperty("grad", grad);
            
            producer.send(topic, txtMsg);
            
            Message msg = consumer.receive();
            
            if(msg instanceof TextMessage){
                
                TextMessage txtMsg1 = (TextMessage)msg;
                
                int code = txtMsg1.getIntProperty("status");
                
                String text = txtMsg1.getText();
                    
                if(code != 0){
                    return Response.status(Response.Status.OK).entity(text).build();
                }
                
                return Response.status(Response.Status.OK).entity(text).build();
                
            }
            
            return Response.status(Response.Status.OK).entity("Kreiran korisnik.").build();
       
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Korisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    @POST
    @Path("novac")
    public Response dodavanjeNovca(@FormParam("username") String username, @FormParam("novac") String novac ){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 1);
            txtMsg.setIntProperty("choice", 3);
            
            txtMsg.setStringProperty("username", username);
            txtMsg.setStringProperty("novac", novac);
            
            producer.send(topic, txtMsg);
            
            Message msg = consumer.receive();
            
            if(msg instanceof TextMessage){
                
                TextMessage txtMsg1 = (TextMessage)msg;
                
                int code = txtMsg1.getIntProperty("status");
                
                String text = txtMsg1.getText();
                    
                if(code != 0){
                    return Response.status(Response.Status.OK).entity(text).build();
                }
                
                return Response.status(Response.Status.OK).entity(text).build();
                
            }
            
            return Response.status(Response.Status.OK).entity("Dodat novac korisniku.").build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Korisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    @POST
    @Path("promena")
    public Response menjanjeAdreseIGrada(@FormParam("username") String username, @FormParam("adresa") String adresa, @FormParam("grad") String grad){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 1);
            txtMsg.setIntProperty("choice", 4);
            
            txtMsg.setStringProperty("username", username);
            txtMsg.setStringProperty("adresa", adresa);
            txtMsg.setStringProperty("grad", grad);
            
            producer.send(topic, txtMsg);
            
            Message msg = consumer.receive();
            
            if(msg instanceof TextMessage){
                
                TextMessage txtMsg1 = (TextMessage)msg;
                
                int code = txtMsg1.getIntProperty("status");
                
                String text = txtMsg1.getText();
                    
                if(code != 0){
                    return Response.status(Response.Status.OK).entity(text).build();
                }
                
                return Response.status(Response.Status.OK).entity(text).build();
                
            }
            
            return Response.status(Response.Status.OK).entity("Promenjena adresa i grad.").build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Korisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
