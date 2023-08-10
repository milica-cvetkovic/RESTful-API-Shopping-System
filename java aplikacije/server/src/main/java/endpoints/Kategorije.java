/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.*;
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
@Path("kategorije")
public class Kategorije {
    
    @Resource(lookup="connectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup="queueProjekat")
    Queue queue;
    
    @Resource(lookup="topicProjekat")
    Topic topic;
    
    @GET
    public Response dohvatanjeKategorija(){
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 14);
            
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
    public Response kreiranjeKategorije(@FormParam("naziv") String naziv){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 5);
            txtMsg.setStringProperty("naziv", naziv);
            
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
            return Response.status(Response.Status.OK).entity("Kreirana kategorija.").build();
       
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Kategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
        
    }
    
}
