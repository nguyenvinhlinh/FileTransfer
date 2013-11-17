
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 *
 * @author nguyenvinhlinh
 */
public class View extends JFrame {

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Option");
    JMenuItem sendOption = new JMenuItem("Send");
    JMenuItem recieveOption = new JMenuItem("Recieve");
    JMenuItem exitOption = new JMenuItem("Exit");
    JPanel sendPanel = new JPanel();
    JPanel recievePanel = new JPanel();
    //common component ---- Got error now
//    JButton browseButtonS = new JButton("Browser");
//    JLabel portLabelS = new JLabel ("At port: ");
//    JTextField portTextS = new JTextField(10);
    //common components
    JTextArea datalog = new JTextArea();
    //components in send Panel
    JLabel filePathLabel = new JLabel("File path: ");
    JTextField filePathText = new JTextField(25);
    JButton sendButton = new JButton("Send");
    JButton browseButtonS = new JButton("Browser");
    JLabel portLabelS = new JLabel("At port: ");
    JTextField portTextS = new JTextField(10);
    //component in recieve Panel
    JLabel savedPathLabel = new JLabel("Recieve at: ");
    JTextField savedPathText = new JTextField(25);
    JLabel serverIPLabel = new JLabel("Server IP: ");

    public JTextField getServerIPText() {
        return serverIPText;
    }

    JTextField serverIPText = new JTextField(10);
    JButton recieveButton = new JButton("Recieve");
    JButton browseButtonR = new JButton("Browser");
    JLabel portLabelR = new JLabel("At port: ");
    JTextField portTextR = new JTextField(10);

    public View() {
        //Frame
        setTitle("File Transfer");
        setSize(600, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Menubar
        setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(sendOption);
        menu.add(recieveOption);
        menu.add(exitOption);
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
        
        //receive panel
        recievePanel.setLayout(null);
        savedPathLabel.setBounds(50, 25, 85, 30);
        savedPathText.setBounds(135, 28, 270, 22);
        browseButtonR.setBounds(420, 28, 100, 22);
        browseButtonR.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnV = chooser.showDialog(recievePanel, "Save");
                if(returnV == JFileChooser.APPROVE_OPTION){
                    
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
        datalog.setBounds(240, 60, 340, 75);
        sendPanel.add(datalog);
        //addup

        add(sendPanel);
        paintComponents(getGraphics());

        sendOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(recievePanel);
                add(sendPanel);
                datalog.setBounds(240, 60, 340, 75);
                recievePanel.remove(datalog);
                sendPanel.add(datalog);
                paintComponents(getGraphics());
            }
        });
        recieveOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                remove(sendPanel);
                add(recievePanel);
                datalog.setBounds(300, 60, 280, 75);
                sendPanel.remove(datalog);
                recievePanel.add(datalog);
                paintComponents(getGraphics());
            }
        });
        exitOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(3);
            }
        });
    }
    public void setSendAction(ActionListener e) {
        sendButton.addActionListener(e);
    }
    public void setRecieveAction(ActionListener e){
        recieveButton.addActionListener(e);
    }
    public String getFilePathText() {
        return filePathText.getText();
    }
    public String getSavedFileLocation(){
        return savedPathText.getText();
    }
    public int getPortTextS() throws NumberFormatException{
        return Integer.parseInt(portTextS.getText());
    }
    public int getPortTextR() throws NumberFormatException{
        return Integer.parseInt(portTextR.getText());
    }
}
