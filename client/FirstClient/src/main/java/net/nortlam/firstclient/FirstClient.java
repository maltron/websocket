package net.nortlam.firstclient;

import java.util.logging.Logger;
import javax.swing.JFrame;

public class FirstClient extends JFrame {
    
    private static final Logger LOG = Logger.getLogger(">>> FirstClient");
    
    public static final String SERVER = "ws://localhost:8080/hello/endpoint";
    
    public static void main(String[] args) {
        FirstClient app = new FirstClient();
    }
    
//    public FirstClient() {
//        URI uri = null;
//        try {
//            uri = new URI(SERVER);
//        } catch (URISyntaxException ex) {
//            LOG.log(Level.SEVERE, "### URI Syntax Exception:"+ex.getMessage());
//        }
//            
//        ClientEndpointConfig config = ClientEndpointConfig.Builder.create().build();
//        ClientManager client = ClientManager.createClient();
//        try {
//            client.connectToServer(this, config, uri);
//        } catch(DeploymentException ex) {
//            LOG.log(Level.SEVERE, "### DEPLOYMENT Exception:"+ex.getMessage());
//        } catch(IOException ex) {
//            LOG.log(Level.SEVERE, "### IO Exception:"+ex.getMessage());
//        }
//    }
//
//    @Override
//    public void onOpen(Session session, EndpointConfig config) {
//        LOG.log(Level.INFO, String)
//    }
    
    
}
