
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nguyenvinhlinh
 */
public class Server {
    private int port = 2000;
    private ServerSocket server;
    private Socket socket;
    private InputStream inputData;
    private OutputStream outputData;
    private String filePath;
    private String fileName;
    
    public void setPort(int newP){
        port = newP;
    }
    public int getPort(){
        return port;
    }
    public void setFilePath(String path){
        filePath = path;
    }
    public String getFilePath(){
        return filePath;
    }
    public void startServer() throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server is launched");
        System.out.println("Server now is ready to accept client");
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Server IP: "+ ip + " at port: "+ server.getLocalPort());
        socket = server.accept();
    }

    public void loadFile() throws IOException {
        System.out.println("Loading file");
        inputData = new FileInputStream(filePath);
        fileName = new File(filePath).getName();
        System.out.println("File is loaded at "+ filePath);
    }

    public void upstreamFile() throws IOException {
        DataOutputStream sendName = new DataOutputStream(socket.getOutputStream());
        sendName.writeUTF(fileName);
        sendName.flush();
        System.out.println("Upstreaming file");
        outputData = socket.getOutputStream();
        
        byte[] buf = new byte[10000];
        int len = inputData.read(buf);
        while(len != -1){
            outputData.write(buf, 0, len);
            len = inputData.read(buf);
        }
        inputData.close();
        outputData.close();
        System.out.println("Finished");
    }
    
    class Session implements Runnable{
        Socket socket;
        public Session(Socket s){
            socket =s;
        }
        @Override
        public void run(){
            
        }
    }
    
}
