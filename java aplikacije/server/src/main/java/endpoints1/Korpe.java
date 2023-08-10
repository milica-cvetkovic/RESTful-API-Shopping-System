/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints1;

import endpoints.Artikli;
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
@Path("korpe")
public class Korpe {
    
    @Resource(lookup="connectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup="queueProjekat")
    Queue queue;
    
    @Resource(lookup="topicProjekat")
    Topic topic;
    
    @POST
    public Response dodavanjeUKorpu(@FormParam("naziv") String naziv, @FormParam("kolicina")String kolicina, @FormParam("username") String username){
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 9);
            txtMsg.setStringProperty("naziv", naziv);
            txtMsg.setIntProperty("kolicina", Integer.parseInt(kolicina));
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
            return Response.status(Response.Status.OK).entity("Dodat u korpu.").build();
       
        } catch (JMSException ex) {
            Logger.getLogger(Artikli.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    @POST
    @Path("obrisi")
    public Response brisanjeIzKorpe(@FormParam("naziv") String naziv, @FormParam("kolicina")String kolicina, @FormParam("username") String username){
        
        try {
            
            System.out.println("usao obrisi");
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 10);
            txtMsg.setStringProperty("naziv", naziv);
            txtMsg.setIntProperty("kolicina", Integer.parseInt(kolicina));
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
            return Response.status(Response.Status.OK).entity("Izbrisan iz korpe.").build();
       
        } catch (JMSException ex) {
            Logger.getLogger(Artikli.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    @POST
    @Path("sadrzaj")
    public Response dohvatanjeSadrzaja(@FormParam("username") String username){
        
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 2);
            txtMsg.setIntProperty("choice", 16);
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
                    Logger.getLogger(Korpe.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
           
        } catch (JMSException ex) {
            Logger.getLogger(Korpe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    
}
