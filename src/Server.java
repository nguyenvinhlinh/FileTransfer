
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
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
public class Server extends Observable {

    private int port = 2000;
    private ServerSocket server;
    private Socket socket;
    private InputStream inputData;
    private OutputStream outputData;
    private String filePath;
    private String fileName;

    public void setPort(int newP) {
        port = newP;
    }

    public int getPort() {
        return port;
    }

    public void setFilePath(String path) {
        filePath = path;
    }

    public String getFilePath() {
        return filePath;
    }

    public ServerSocket getServer() {
        return server;
    }

    public Socket getSocket() {
        return socket;
    }

    public void startServer() {
        try {
            server = new ServerSocket(port);
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("Server IP: " + ip + " at port: " + server.getLocalPort());
            setChanged();
            notifyObservers("Server started \nServer IP: " + ip + "\nServer port: " + server.getLocalPort() + "\nServer is ready to accept client");
            socket = server.accept();
        } catch (Exception ex) {
            System.err.println(ex);
            setChanged();
            notifyObservers("Port " + port + " has been used, Please choose another");
        }
    }

    public void loadFile() throws IOException {
        System.out.println("Loading file");
        inputData = new FileInputStream(filePath);
        fileName = new File(filePath).getName();
        System.out.println("File is loaded at " + filePath);
    }

    public void upstreamFile() throws IOException {
        DataOutputStream sendName = new DataOutputStream(socket.getOutputStream());
        sendName.writeUTF(fileName);
        sendName.flush();
        System.out.println("Upstreaming file");
        outputData = socket.getOutputStream();

        byte[] buf = new byte[10000];
        int len = inputData.read(buf);
        while (len != -1) {
            outputData.write(buf, 0, len);
            len = inputData.read(buf);
        }
        inputData.close();
        outputData.close();
        System.out.println("Finished");
        setChanged();
        notifyObservers("Transfered Completedly");
        socket.close();
        server.close();
    }

    public void stopSending() {
        try {
            socket.close();
            System.out.println("Server: socket closed");
            server.close();
            System.out.println("Server: server socket closed");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    class Session implements Runnable {

        Socket socket;

        public Session(Socket s) {
            socket = s;
        }

        @Override
        public void run() {

        }
    }

}
