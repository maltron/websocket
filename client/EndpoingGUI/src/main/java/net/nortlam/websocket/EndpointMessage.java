package net.nortlam.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class EndpointMessage extends Endpoint {
    
    private static final Logger LOG = Logger.getLogger(">>> EndpointMessage");
    
    private Session session;

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        LOG.log(Level.INFO, "onOpen():Connection stablished");
    }
    
    public void send(String message) throws IOException, EndpointException {
        if(session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        } else {
            LOG.log(Level.SEVERE, "sendMessage(): ### Connection is NOT OPEN");
            throw new EndpointException("Connection is not open");
        }
    }
    
    public void ping() throws IOException, EndpointException {
        if(session != null && session.isOpen()) {
            ByteBuffer buffer = ByteBuffer.wrap("Are you there ?".getBytes());
            session.getBasicRemote().sendPing(buffer);
            
        } else {
            LOG.log(Level.SEVERE, "ping(): ### Connection is NOT OPEN");
            throw new EndpointException("Connection is not open");
        }
    }
    
    public boolean close() throws IOException, EndpointException {
        if(session != null && session.isOpen()) {
            session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "The End"));
        } else {
            LOG.log(Level.SEVERE, "close(): ### Connection is NOT OPEN");
            throw new EndpointException("Connection is not open");
        }
        
        return true;
    }
    
    @Override
    public void onError(Session session, Throwable thr) {
        LOG.log(Level.SEVERE, String.format("### onError():%s", thr.getMessage()));
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        LOG.log(Level.INFO, "onClose()");
    }
}
