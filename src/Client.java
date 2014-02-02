
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class Client extends Observable{
    private int port = 2000;
    private Socket socket;
    private InputStream inData;
    private OutputStream outData;
    private String serverIP;
    private String savedLocation;


    public Client(String serverIP, int port, String savedLocation) throws IOException {
        this.serverIP = serverIP;
        this.port = port;
        this.savedLocation = savedLocation;
    }
    public Client() {
    }
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
    public void setSavedLocation(String savedLocation) {
        this.savedLocation = savedLocation;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public void connect() {
        System.out.println("Connecting to server");
        setChanged();
        notifyObservers("Connecting to server");
        try {
            socket = new Socket(serverIP, port);
            System.out.println("Connected to server");
            System.out.println("Server IP is: " + socket.getInetAddress() + " at port: " + socket.getPort());
            setChanged();
            notifyObservers("Connected to server\nServer IP: "+socket.getInetAddress() +"\nServer port: "+socket.getPort());
            download();
        } catch (IOException ex) {
            System.out.println("Error occured, please check out the connection or status of sender");
            hasChanged();
            notifyObservers("Error occured, please check out the connection or status of server for example server's IP and its port.");
            
        } 
    }

    public void download() throws IOException {
        try{
            System.out.println("Downloading file");
            inData = socket.getInputStream();
            DataInputStream recievefileName = new DataInputStream(inData);
            String fileName = recievefileName.readUTF();
            setChanged();
            notifyObservers("Downloading file named "+ fileName);
            outData = new FileOutputStream(savedLocation + "/" + fileName);
            //count the length of inData
            byte[] dataArray = new byte[10000];
            int length = inData.read(dataArray);
            while (length != -1) {
                outData.write(dataArray, 0, length);
                length = inData.read(dataArray);
            }
            inData.close();
            outData.close();
            System.out.println("Finished");
            setChanged();
            notifyObservers("Download completedly");
            
        } catch (IOException ex){
            System.out.println("Disconnected from server");
            setChanged();
            notifyObservers("Disconnected from server");
            System.err.println(ex);
        } finally{
            socket.close();
            System.out.println("client: socket close");
        }
    }
}
