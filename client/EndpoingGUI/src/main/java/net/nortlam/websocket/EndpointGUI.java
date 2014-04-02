package net.nortlam.websocket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.stage.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import org.glassfish.tyrus.client.ClientManager;

public class EndpointGUI extends JFrame implements ActionListener, KeyListener {
    
    private JLabel labelAddress;
    private JTextField textAddress;
    
    private JButton buttonOpen;
    private JTextField textMessage;
    private JButton buttonSend;
    private JButton buttonPing;
    private JButton buttonClose;
    
    private JTextArea areaError;
    
    private JButton buttonExit;
    
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension screenSize = toolkit.getScreenSize();
    
    private ClientEndpointConfig config;
    private ClientManager client;
    private EndpointMessage endpoint;
    
    public static void main(String[] args) {
        EndpointGUI app = new EndpointGUI();
    }
    
    public EndpointGUI() {
        super("WebSocket endpoint");
        setIconImage(toolkit.getImage(getClass().getResource("/web_socket.png")));
        setExtendedState(Frame.NORMAL);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        initComponents();
        setContentPane(buildPanel());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeApplication();
            }
        });
        pack();
        setLocation( (screenSize.width - this.getSize().width) / 2,
                    (screenSize.height - this.getSize().height) / 2);
        setVisible(true);
    }
    
    public void initComponents() {
        // Use Default Look&Feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        labelAddress = new JLabel("Address");
        textAddress = new JTextField(20);
        textAddress.setText("ws://");
        textAddress.addKeyListener(this);
        textAddress.addActionListener(this);
        
        buttonOpen = new JButton("Open");
        buttonOpen.addActionListener(this);
        textMessage = new JTextField(20);
        textMessage.setEnabled(false);
        textMessage.addKeyListener(this);
        textMessage.addActionListener(this);
        buttonSend = new JButton("Send");
        buttonSend.setEnabled(false);
        buttonSend.addActionListener(this);
        buttonPing = new JButton("Ping");
        buttonPing.addKeyListener(this);
        buttonPing.addActionListener(this);
        buttonPing.setEnabled(false);
        buttonClose = new JButton("Close");
        buttonClose.setEnabled(false);
        buttonClose.addActionListener(this);
        
        areaError = new JTextArea(12, 40);
        areaError.setWrapStyleWord(true);
        areaError.setLineWrap(true);
        areaError.setEditable(false);
        areaError.addKeyListener(this);
        
        buttonExit = new JButton("Exit");
        buttonExit.addActionListener(this);
        
        config = ClientEndpointConfig.Builder.create().build();
        client = ClientManager.createClient();
        endpoint = new EndpointMessage();
    }    
                
    public JPanel buildPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        GridBagConstraints gc = new GridBagConstraints();
        
        JPanel panelAddress = new JPanel(new BorderLayout(5,5));
        TitledBorder borderAddress = BorderFactory.createTitledBorder(
                                BorderFactory.createEtchedBorder(), "Address");
        panelAddress.setBorder(borderAddress);
        panelAddress.add(textAddress, BorderLayout.CENTER);
        
        gc.gridx = 0; gc.gridy = 0; 
        gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(panelAddress, gc);
        mainPanel.add(panelAddress);
        
        JPanel panelMessage = new JPanel(new BorderLayout(5,5));
        panelMessage.add(buttonOpen, BorderLayout.WEST);
        panelMessage.add(textMessage, BorderLayout.CENTER);
            JPanel panelButtons = new JPanel(new GridLayout(1, 3, 5, 5));
            panelButtons.add(buttonSend);
            panelButtons.add(buttonPing);
            panelButtons.add(buttonClose);
        panelMessage.add(panelButtons, BorderLayout.EAST);
        
        gc.gridy++; 
        layout.setConstraints(panelMessage, gc);
        mainPanel.add(panelMessage);
        
        gc.gridy++;
        gc.insets.top = 0; gc.insets.bottom = 0;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0; gc.weighty = 1.0;
            JPanel panelError = new JPanel(new BorderLayout());
            panelError.setBorder(BorderFactory.createTitledBorder(
                                    BorderFactory.createEtchedBorder(), "Error"));
            JScrollPane scrollError = new JScrollPane(areaError, 
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            panelError.add(scrollError, BorderLayout.CENTER);
        layout.setConstraints(panelError, gc);
        mainPanel.add(panelError);
        
        gc.gridy++; 
        gc.anchor = GridBagConstraints.SOUTHEAST; gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1.0; gc.weighty = 0.0;
        gc.insets.top = 0; gc.insets.bottom = 0;
        layout.setConstraints(buttonExit, gc);
        mainPanel.add(buttonExit);
        
        return mainPanel;
    }
    
    public void closeApplication() {
        try {
            endpoint.close();
        } catch(IOException | EndpointException e) {
            System.err.printf("### IO Exception:%s", e.getMessage());
        }
        
        setVisible(false);
        dispose();
        System.exit(0);
    }
    
    // ACTION LISTENER ACTION LISTENER ACTION LISTENER ACTION LISTENER ACTION LISTENER 
    //   ACTION LISTENER ACTION LISTENER ACTION LISTENER ACTION LISTENER ACTION LISTENER 
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == buttonExit) closeApplication();
        else if(event.getSource() == buttonOpen || event.getSource() == textAddress) {
            try {
                client.connectToServer(endpoint, new URI(textAddress.getText()));
            } catch(URISyntaxException ex) {
                areaError.selectAll();
                areaError.setText(
                String.format("### URI Syntax Exception\nMessage:%s\nReason:%s\nIndex:%d\nInput:%s", 
                ex.getMessage(), ex.getReason(), ex.getIndex(), ex.getInput()));
                return;
                
            } catch(DeploymentException ex) {
                areaError.selectAll();
                areaError.setText(
                String.format("### Deployment Exception\nMessage:%s", ex.getMessage()));
                return;
                
            } catch(IOException ex) {
                areaError.selectAll();
                areaError.setText(
                String.format("### IO Exception\nMessage:%s", ex.getMessage()));
                return;
            }
            
            enableConnection(true);
            
        } else if(event.getSource() == buttonSend || event.getSource() == textMessage) {
            try {
                endpoint.send(textMessage.getText());
                textMessage.selectAll();
                textMessage.requestFocus();
                
            } catch(IOException e) {
                areaError.setText(String.format("### IOException:\n%s", e.getMessage()));
            } catch(EndpointException e) {
                areaError.setText(String.format("### Endpoint Exception:\n%s", e.getMessage()));
            }
            
        } else if(event.getSource() == buttonPing) {
            try {
                endpoint.ping();
            } catch(IOException e) {
                areaError.setText(String.format("### IO Exception:\n%s", e.getMessage()));
            } catch(EndpointException e) {
                areaError.setText(String.format("### Endpoint Exception:\n%s", e.getMessage()));
            }
            
        } else if(event.getSource() == buttonClose) {
            try {
                endpoint.close();
            } catch(IOException e) {
                areaError.setText(String.format("### IOException:\n%s", e.getMessage()));
            } catch(EndpointException e) {
                areaError.setText(String.format("### Endpoint Exception:\n%s", e.getMessage()));
            }

            enableConnection(false);
        }         
    }
    
    private void enableConnection(boolean isOpen) {
        textAddress.setEnabled(!isOpen);
        buttonOpen.setEnabled(!isOpen);
        textMessage.setEnabled(isOpen);
        buttonSend.setEnabled(isOpen);
        buttonPing.setEnabled(isOpen);
        buttonClose.setEnabled(isOpen);
        areaError.setText("");
    }
    
    // KEY LISTENER KEY LISTENER KEY LISTENER KEY LISTENER KEY LISTENER KEY LISTENER
    //  KEY LISTENER KEY LISTENER KEY LISTENER KEY LISTENER KEY LISTENER KEY LISTENER 
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ESCAPE) closeApplication();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
