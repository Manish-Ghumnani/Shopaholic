
package sg.edu.nus.iss.codepirates.warehouse.socket;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *Socket Endpoint to communicate with client application and forwarding message to Incoming order Screen
 * @author Prasanna
 */
@ApplicationScoped
@Named
@ServerEndpoint("/order")
public class IncomingOrder {

     private static final Logger log = Logger.getLogger(CustomerOrder.class.getSimpleName());
     
     private final SessionHandler sessionHandler = SessionHandler.getSessionHandler();
     
     @OnOpen
    public synchronized void open(final Session session, EndpointConfig config) {
         log.log(Level.INFO, "Session is open: {0}", session.getId());
        sessionHandler.create(session);
    }
    

    public  void onMessage(String message)  {
        log.log(Level.INFO, "Fowarding Message to Incoming Order Screen: --{0}", message);
        sessionHandler.sendMessage(message);
    
    }
    
    @OnError
    public void onError(Session session, Throwable ex) { log.log(Level.INFO, "Error: {0}", ex.getMessage()); }
    
    @OnClose
     public synchronized void onClose(Session session) {
            log.info("Session is closed ...");
            sessionHandler.remove(session);
     }
     
     
    
}
