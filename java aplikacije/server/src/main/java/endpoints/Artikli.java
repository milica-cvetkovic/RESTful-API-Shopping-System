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
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author milic
 */
@Path("artikli")
public class Artikli {
    
    @Resource(lookup="connectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup="queueProjekat")
    Queue queue;
    
    @Resource(lookup="topicProjekat")
    Topic topic;
        
    @POST
    public Response kreiranjeArtikla(@FormParam("naziv") String naziv, @FormParam("opis")String opis, @FormParam("cena") String cena, @FormParam("popust") String popust, @FormParam("kategorija") String kategorija, @FormParam("username") String username){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 6);
            txtMsg.setStringProperty("naziv", naziv);
            txtMsg.setStringProperty("opis", opis);
            txtMsg.setFloatProperty("cena", Float.parseFloat(cena));
            txtMsg.setIntProperty("popust", Integer.parseInt(popust));
            txtMsg.setStringProperty("kategorija", kategorija);
            txtMsg.setStringProperty("username", username);
            
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
            return Response.status(Response.Status.OK).entity("Kreiran artikal.").build();
       
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Artikli.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    @POST
    @Path("cena")
    public Response menjanjeCene(@FormParam("naziv") String naziv,@FormParam("cena") String cena, @FormParam("username") String username){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 7);
            txtMsg.setStringProperty("naziv", naziv);
            txtMsg.setFloatProperty("cena", Float.parseFloat(cena));
            txtMsg.setStringProperty("username", username);

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
            return Response.status(Response.Status.OK).entity("Promenjena cena.").build();
       
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Artikli.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    @POST
    @Path("popust")
    public Response postavljanjePopusta(@FormParam("naziv") String naziv,@FormParam("popust") String popust, @FormParam("username") String username){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 8);
            txtMsg.setStringProperty("naziv", naziv);
            txtMsg.setIntProperty("popust", Integer.parseInt(popust));
            txtMsg.setStringProperty("username", username);
            
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
            return Response.status(Response.Status.OK).entity("Postavljen popust.").build();
       
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Artikli.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    @POST
    @Path("prodaja")
    public Response dohvatanjeProdavanihArtikala(@FormParam("username") String username){
        
        try {
            
            System.out.println("usao u prodaju");
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 15);
            txtMsg.setStringProperty("username", username);
            
            
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
                        return Response.status(Response.Status.OK).build();
                    }
                    return Response.status(Response.Status.OK).entity(new GenericEntity<ArrayList<String>> ((ArrayList<String>) objMsg.getObject()) {}).build();
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Artikli.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Artikli.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
        
    }
    
    
}
