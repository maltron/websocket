package net.nortlam.websocket;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.Extension;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/endpoint")
public class FirstEndpoint {
    
    private static final Logger LOG = Logger.getLogger(">>> FirstEndpoint");

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        List<Class<? extends Decoder>> decoders = config.getDecoders();
        List<Class<? extends Encoder>> encoders = config.getEncoders();
        Map<String, Object> userProperties = config.getUserProperties();
        LOG.log(Level.INFO, String.format(
                "onOpen(Session, EndpointConfig) Encoders:%d Decoders:%d",
                (decoders != null ? decoders.size() : 0),
                (encoders != null ? encoders.size() : 0)));
        if(userProperties != null)
            for(String key: userProperties.keySet())
                LOG.log(Level.INFO, String.format(
                     "onOpen(Session, EndpointConfig) User Property[%s]:%s", 
                                                key, userProperties.get(key)));
    }    

    @OnMessage
    public String onMessage(Session session, String message) {
        LOG.log(Level.INFO, String.format("onMessage(Session, String): %s", message));
        return null;
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        int code = -1;
        if(reason != null && reason.getCloseCode() != null)
            code = reason.getCloseCode().getCode();
        String phrase = null;
        if(reason != null) phrase = reason.getReasonPhrase();
        
        LOG.log(Level.INFO, 
        String.format("onClose(Session, CloseReason) Code:%d Reason:%s",
                            code, phrase));
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        LOG.log(Level.SEVERE, String.format("### onError(Session, Throwable): %s",
               error.getMessage()));
        error.printStackTrace();
    }
    
    public static void describeSession(Session session) {
        boolean isOpen = session.isOpen();
        
        boolean isSecure = session.isSecure();
        URI uri = session.getRequestURI();
        Principal userPrincipal = session.getUserPrincipal();
        Map<String, Object> userProperties = session.getUserProperties();
        
        WebSocketContainer webSocketContainer = session.getContainer();
        String ID = session.getId();

        RemoteEndpoint.Async asyncRemote = session.getAsyncRemote();
        RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        
        long maxIdleTimeout = session.getMaxIdleTimeout();
        int maxTextMessageBufferSize = session.getMaxTextMessageBufferSize();
        int maxBinaryMessageBufferSize = session.getMaxBinaryMessageBufferSize();
        
        Set<MessageHandler> messageHandlers = session.getMessageHandlers();
        
        List<Extension> negotiatedExtentions = session.getNegotiatedExtensions();
        
        String negotiatedSubprotocol = session.getNegotiatedSubprotocol();
        String protocolVersion = session.getProtocolVersion();
        
        Set<Session> openSessions = session.getOpenSessions();
        
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        Map<String, String> pathParameters = session.getPathParameters();
        String queryString = session.getQueryString();
        
        // Other methods
        // 
        // removeMessageHandler(MessageHandler handler)
        // setMaxTextMessageBufferSize(int length)
        // setMaxBinaryMessageBufferSize(int length)
        // setMaxIdleTimeout(long milliseconds)
    }
    
    
    
}
