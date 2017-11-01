package sg.edu.nus.iss.codepirates.warehouse.socket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *Helper class to retrieve session and perform operation in that session
 * @author Prasanna
 */
public class SessionHandler {
    
    private static SessionHandler instance =null;
    
    private final Set<Session> sessions =  new HashSet<>();
    
    
    public void create(Session session){
            sessions.add(session);
    }
    
    public void remove(Session session){
            sessions.remove(session);
    }
    
    public void sendMessage(String message){
        
        sessions.forEach((s) -> {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
   /*Retruns the same sesssion object everytime we send the session*/
    public static SessionHandler getSessionHandler(){
            if(instance == null ){
                    instance = new SessionHandler();
            }
            return instance;
    }
    
    
}
