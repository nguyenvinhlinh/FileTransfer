
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author nguyenvinhlinh
 */
public class Controller {

    private Server server;
    private Client client;
    private View view;

    public Controller(Server server, Client client, View view) {
        this.server = server;
        this.client = client;
        this.view = view;
        view.setSendAction(new SendAction());
        view.setRecieveAction(new ReceiveAction());
    }

    public Controller() {
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    //boolean upstreamming = false ;
    public void addSendActionListener() {

        view.setSendAction(new SendAction());
    }

    public void addRecieveActionListener() {
        view.setRecieveAction(new ReceiveAction());
    }

    class SendAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new Thread(new SendThread()).start();
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    class ReceiveAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new Thread(new ReceiveThread()).start();
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }
    //Thread for send acction
    static boolean isSendActionActive = false;
    class SendThread implements Runnable {
        @Override
        public void run() {
            if(isSendActionActive == false){
                try {
                    isSendActionActive = true;
                    view.getSendButton().setText("Stop!");
                    System.out.println("Line88: isSendActionActive: " + isSendActionActive);
                    String filePath = view.getFilePathText();
                    int port = view.getPortTextS();
                    if (filePath.equals("") || port == -1 || port > 65535){
                        isSendActionActive = false;
                        view.getSendButton().setText("Send");
                        return;
                    }
                    server.setFilePath(filePath);
                    server.setPort(port);
                    server.loadFile();
                    server.startServer();
                    
                    server.upstreamFile();
                    view.getSendButton().setText("Send");
                    isSendActionActive = false;
                } catch (IOException ex) {
                    System.err.println(ex);
                    isSendActionActive = false;
                    view.getSendButton().setText("Send"); 
                }
            }else if (isSendActionActive == true) {
                
                try{
                    isSendActionActive = false;
                    System.out.println("isSendActionActive: "+ isSendActionActive);
//                    server.getSocket().close();
                    server.getServer().close();
                    view.getSendButton().setText("Send");
                }catch (IOException ex){
                    System.err.println(ex);
                    view.getSendButton().setText("Send");
                    
                }
            }
        }
    }
    //Thread for receive action
    static boolean isReceiveActionActive = false;

    class ReceiveThread implements Runnable {

        @Override
        public void run() {
            if (isReceiveActionActive == false) {
                try {
                    isReceiveActionActive = true;
                    view.getRecieveButton().setText("Stop");
                    client.setSavedLocation(view.getSavedFileLocation());
                    client.setServerIP(view.getServerIPText());
                    client.setPort(view.getPortTextR());
                    client.connect();
                    client.download();
                    view.getRecieveButton().setText("Receive");
                } catch (IOException ex) {
                    System.err.println(ex);
                    view.getRecieveButton().setText("Receive");
                }
            } else {
                try {
                    isReceiveActionActive = false;
                    client.getSocket().close();
                } catch (IOException ex) {
                    System.err.println(ex);
                } finally{
                    view.getRecieveButton().setText("Receive");
                }
            }
        }
    }
}
