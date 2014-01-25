
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
    public void setClient(Client client){
        this.client = client;
    }
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
    class SendThread implements Runnable{

        @Override
        public void run() {
            try {
                server.setFilePath(view.getFilePathText());
                server.setPort(view.getPortTextS());
                server.loadFile();
                server.startServer();
                server.upstreamFile();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        
    }
    //Thread for receive action
    class ReceiveThread implements Runnable{

        @Override
        public void run() {
            try {
                client.setSavedLocation(view.getSavedFileLocation());
                client.setServerIP(view.getServerIPText());
                client.setPort(view.getPortTextR());
                client.connect();
                client.download();
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
