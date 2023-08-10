
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
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author milic
 */
@Path("molim")
public class Narudzbine {
    
    
    @Resource(lookup="connectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup="queueProjekat")
    Queue queue;
    
    @Resource(lookup="topicProjekat")
    Topic topic;
    
    @POST
    public Response dohvatanjeKorisnika(@FormParam("username") String username){
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            TextMessage txtMsg = context.createTextMessage();
            
            txtMsg.setIntProperty("podsistem", 3);
            txtMsg.setIntProperty("choice", 17);
            txtMsg.setStringProperty("username", username);
            
            System.out.println("usao13");
            
            producer.send(topic, txtMsg);
            
            System.out.println("izasao13");
            
            Message msg = consumer.receive();
            
            System.out.println(msg.getIntProperty("status"));
            System.out.println("primi");
            
            if(msg instanceof ObjectMessage){
                
                try {
                    
                    ObjectMessage objMsg = (ObjectMessage) msg;
                    
                    int code = objMsg.getIntProperty("status");
                    
                    if(code != 0){
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                    return Response.status(Response.Status.OK).entity(new GenericEntity<ArrayList<String>> ((ArrayList<String>) objMsg.getObject()) {}).build();
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Narudzbine.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
           
        } catch (JMSException ex) {
            Logger.getLogger(Narudzbine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
}
