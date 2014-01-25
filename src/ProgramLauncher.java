/**
 * Created by nguyenvinhlinh on 1/24/14.
 */
public class ProgramLauncher {
    private static Server server = new Server();
    private static Client client = new Client();
    private static View view = new View();
    private static Controller controller = new Controller();

    public static void main(String[] args) {
        controller.setServer(server);
        controller.setClient(client);
        controller.setView(view);
        controller.addRecieveActionListener();
        controller.addSendActionListener();
        
        server.addObserver(view);
        client.addObserver(view);
        
    }



}
