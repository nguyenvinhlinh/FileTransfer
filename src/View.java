
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author nguyenvinhlinh
 */
public class View extends JFrame implements Observer{

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Option");
    private JMenuItem sendOption = new JMenuItem("Send");
    private JMenuItem recieveOption = new JMenuItem("Receive");
    private JMenuItem exitOption = new JMenuItem("Exit");
    
    private JMenu help = new JMenu("Help");
    //private JMenuItem instruction = new JMenuItem("Instruction");
    private JMenuItem aboutMe = new JMenuItem("About");
    
    private JPanel sendPanel = new JPanel();
    private JPanel recievePanel = new JPanel();
    //common component ---- Got error now
//    JButton browseButtonS = new JButton("Browser");
//    JLabel portLabelS = new JLabel ("At port: ");
//    JTextField portTextS = new JTextField(10);
    //common components
    private JTextArea datalog = new JTextArea();

    private JScrollPane scrollPane;
    //-------------------------------------------components in send Panel-----------------------------------------------------
    private JLabel filePathLabel = new JLabel("File path: ");
    private JTextField filePathText = new JTextField(25);
    private JButton sendButton = new JButton("Send");
    private JButton browseButtonS = new JButton("Browser");
    private JLabel portLabelS = new JLabel("At port: ");
    private JTextField portTextS = new JTextField(10);
    //-------------------------------------------component in recieve Panel----------------------------------------------------
    private JLabel savedPathLabel = new JLabel("Recieve at: ");
    private JTextField savedPathText = new JTextField(25);
    private JLabel serverIPLabel = new JLabel("Server IP: ");
    private JTextField serverIPText = new JTextField(10);
    private JButton recieveButton = new JButton("Recieve");
    private JButton browseButtonR = new JButton("Browser");
    private JLabel portLabelR = new JLabel("At port: ");
    private JTextField portTextR = new JTextField(10);

    public View() {
        //Frame
        setTitle("File Transfer");
        setSize(620, 210);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        //Menubar
        setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(sendOption);
        menu.add(recieveOption);
        menu.add(exitOption);
        
        menuBar.add(help);
        //help.add(instruction);
        help.add(aboutMe);
        //Send Panel
        sendPanel.setLayout(null);
        sendPanel.setOpaque(true);
        filePathLabel.setBounds(50, 25, 75, 30);
        filePathText.setBounds(125, 28, 290, 22);
        browseButtonS.setBounds(420, 28, 100, 22);

        browseButtonS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnV = fc.showOpenDialog(sendPanel);
                if (returnV == JFileChooser.APPROVE_OPTION) {
                    filePathText.setText(fc.getSelectedFile().getPath());
                }

            }
        });
        portLabelS.setBounds(50, 60, 75, 30);
        portTextS.setBounds(125, 63, 100, 22);

        sendButton.setBounds(125, 100, 100, 40);

        sendPanel.add(filePathLabel);
        sendPanel.add(filePathText);
        sendPanel.add(browseButtonS);
        sendPanel.add(portLabelS);
        sendPanel.add(portTextS);
        sendPanel.add(sendButton);

        //------------------------receive panel
        recievePanel.setLayout(null);
        savedPathLabel.setBounds(50, 25, 85, 30);
        savedPathText.setBounds(135, 28, 270, 22);
        browseButtonR.setBounds(420, 28, 100, 22);
        browseButtonR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnV = chooser.showDialog(recievePanel, "Save");
                if (returnV == JFileChooser.APPROVE_OPTION) {

                    //savedPathText.setText(chooser.getSelectedFile().getAbsolutePath());
                    System.out.println("Saved location: " + chooser.getSelectedFile().getPath());
                    savedPathText.setText(chooser.getSelectedFile().getPath());
                }
            }
        });
        serverIPLabel.setBounds(50, 60, 75, 30);
        serverIPText.setBounds(135, 60, 155, 22);

        portLabelR.setBounds(50, 95, 75, 30);
        portTextR.setBounds(135, 95, 60, 22);

        recieveButton.setBounds(200, 95, 90, 22);

        recievePanel.add(savedPathLabel);
        recievePanel.add(savedPathText);
        recievePanel.add(serverIPLabel);
        recievePanel.add(serverIPText);
        recievePanel.add(recieveButton);
        recievePanel.add(browseButtonR);
        recievePanel.add(portLabelR);
        recievePanel.add(portTextR);

        //Data log
        scrollPane = new JScrollPane(datalog);
        datalog.setLineWrap(true);
        //datalog.setBounds(240, 60, 340, 75);
        scrollPane.setBounds(240, 60, 340, 77);
        sendPanel.add(scrollPane);
        //addup

        add(sendPanel);
        paintComponents(getGraphics());

        sendOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(recievePanel);
                add(sendPanel);
                scrollPane.setBounds(240, 60, 340, 77);
                recievePanel.remove(scrollPane);
                sendPanel.add(scrollPane);
                paintComponents(getGraphics());
            }
        });
        recieveOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                remove(sendPanel);
                add(recievePanel);
                scrollPane.setBounds(300, 60, 280, 75);
                sendPanel.remove(scrollPane);
                recievePanel.add(scrollPane);
                paintComponents(getGraphics());
            }
        });
        exitOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(3);
            }
        });
        aboutMe.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                datalog.setText("Programmer: Nguyen Vinh Linh"
                        + "\nContact:s3410595@rmit.edu.vn"
                        + "\nThis program is free and opensource"
                        + "\nYou can get its source code on https://github.com/nguyenvinhlinh/FileTransfer");
            }
        });
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getRecieveButton() {
        return recieveButton;
    }
    
    public void setSendAction(ActionListener e) {
        sendButton.addActionListener(e);
    }
    public void setRecieveAction(ActionListener e) {
        recieveButton.addActionListener(e);
    }
    public String getFilePathText() {
        if(filePathText.getText().equals("")){
            appendDatalog("File path must not be null");
        }
        return filePathText.getText();
    }
    public String getSavedFileLocation() {
        return savedPathText.getText();
    }
    public int getPortTextS() throws NumberFormatException {
        int port = -1;
        try{
            port = Integer.parseInt(portTextS.getText());
        }
        catch (NumberFormatException ex){
            System.err.println(ex);
            sendButton.setText("Send");
            appendDatalog("Invalid port number");
        }
        
        return port;
    }
    public int getPortTextR() throws NumberFormatException {
        return Integer.parseInt(portTextR.getText());
    }
    public String getServerIPText() {
        return serverIPText.getText();
    }
    public void appendDatalog(String message){
        datalog.append(message + "\n");
    }


    @Override
    public void update(Observable o, Object arg) {
        appendDatalog((String)arg);
    }
}   
