package net.nortlam.websocket;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnMessage;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/different")
public class DifferentKindEndpoint {
    
    private static final Logger LOG = Logger.getLogger(">>> DifferentKind");

    @OnMessage
    public void textMessage(Session session, String message) {
        LOG.log(Level.INFO, String.format("textMessage: %s"), message);
    }
    
    @OnMessage
    public void binaryMessage(Session session, ByteBuffer message) {
        LOG.log(Level.INFO, String.format("binaryMessage:", ""));
    }
    
    @OnMessage
    public void pontMessage(Session session, PongMessage message) {
        String information = new String(message.getApplicationData().array(), 
                                                    Charset.forName("UTF-8"));
        LOG.log(Level.INFO, String.format("pongMessage:%s", information));
    }
}
