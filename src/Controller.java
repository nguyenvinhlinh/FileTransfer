
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
 *
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
        view.setRecieveAction(new RecieveAction());
    }

    public Controller() {
    }

    public void setServer(Server s) {
        server = s;
    }

    public void setView(View v) {
        view = v;
    }

    public View getView() {
        return view;
    }

    public Server getServer() {
        return server;
    }
    public void addSendActionListener(){
        view.setSendAction(new SendAction());
    }
    public void addRevieveActionListener(){
        view.setRecieveAction(new RecieveAction());
    }
    class SendAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                server = new Server();
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

    class RecieveAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                client = new Client(view.getServerIPText().getText(),view.getPortTextR(), view.getSavedFileLocation());
                client.connect();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //TEST
    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller();
        controller.setView(view);
        controller.addRevieveActionListener();
        controller.addSendActionListener();
    }
}
