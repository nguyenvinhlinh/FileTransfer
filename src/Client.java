
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private int port = 2000;
    private Socket socket;
    private InputStream inData;
    private OutputStream outData;
    private String serverIP;
    private String savedLocation;
    private String fileName;
    public Client(String serverIP,int port, String savedLocation) throws IOException{
        this.serverIP = serverIP;
        this.port = port;
        this.savedLocation = savedLocation;
    }
    public Client(){
        
    }
    public void connect(){
        System.out.println("Connecting to server");
        try {
            socket = new Socket(serverIP, port);
            System.out.println("Connected to server");
            System.out.println("Server IP is: "+ socket.getInetAddress() + " at port: "+ socket.getPort());
            download();
        } catch (UnknownHostException ex) {
            System.out.println("Error");
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }
    public void download() throws IOException{
        System.out.println("Downloading file");
        inData = socket.getInputStream();
        DataInputStream recievefileName = new DataInputStream(inData);
        fileName = recievefileName.readUTF();
        
        outData = new FileOutputStream(savedLocation+"/"+fileName);
        //count the length of inData
        byte[] dataArray = new byte[10000];
        int length = inData.read(dataArray);
        while (length != -1){
            outData.write(dataArray, 0, length);
            length = inData.read(dataArray);
        }
        inData.close();
        outData.close();
        System.out.println("Finished");
    }
}
