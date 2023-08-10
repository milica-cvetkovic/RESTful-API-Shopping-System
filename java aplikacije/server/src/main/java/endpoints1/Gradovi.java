/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints1;

import endpoints.Korisnici;
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
@Path("gradovi")
public class Gradovi {
    
    @Resource(lookup="connectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup="queueProjekat")
    Queue queue;
    
    @Resource(lookup="topicProjekat")
    Topic topic;
    
    @POST
    public Response kreiranjeGrada(@FormParam("naziv") String naziv, @FormParam("drzava") String drzava){

        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 1);
            txtMsg.setIntProperty("choice", 1);
            txtMsg.setStringProperty("naziv", naziv);
            txtMsg.setStringProperty("drzava", drzava);
            
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
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Gradovi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    @GET
    public Response dohvatanjeGradova(){
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 1);
            txtMsg.setIntProperty("choice", 12);
            
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
                    Logger.getLogger(Gradovi.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
           
        } catch (JMSException ex) {
            Logger.getLogger(Gradovi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    
}
