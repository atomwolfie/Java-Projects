import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * GUI to change employee's own information
 */
public class ChangeEmplInfo {

    private JFrame frame;
    private JLabel imageLabel;
    private JTextField txtFName;
    private JTextField txtLName;
    private JTextField txtUser;

    private JPasswordField txtPassword;
    private Employee curEmployee;
    private Path curPicPath;
    private Path destinationPicPath;
    private JButton btnSave;
    private boolean updatedPic;
    private boolean updatedFName;
    private boolean updatedLName;
    private boolean updatedPassword;
    private boolean updatedUser;

    /**
     * Create application
     */
    ChangeEmplInfo(Employee empl) {
        this.curEmployee = empl;
        this.updatedPic = false;
        this.updatedFName = false;
        this.updatedPassword = false;
        this.updatedUser = false;

        frame = new JFrame();
        frame.setBounds(600, 150, 525, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Load and display employee picture
        int labelWidth = 225;
        int labelHeight = 300;
        ImageIcon image = new ImageIcon(curEmployee.loadEmployeePic().getScaledInstance(labelWidth, labelHeight,
                Image.SCALE_SMOOTH));
        imageLabel = new JLabel(image);
        imageLabel.setBounds(150,25,labelWidth,labelHeight);
        frame.getContentPane().add(imageLabel);
        imageLabel.setVisible(true);

        JButton btnFilePicker = new JButton("Pick New Picture");
        btnFilePicker.setBounds(170,335,175,30);
        btnFilePicker.setToolTipText(".jpg pictures only");
        frame.getContentPane().add(btnFilePicker);

        JLabel btnFileType = new JLabel(".jpg only");
        btnFileType.setBounds(225,357,170,30);
        frame.getContentPane().add(btnFileType);

        JLabel lblProductName = new JLabel("First Name:");
        lblProductName.setBounds(150, 413, 82, 16);
        frame.getContentPane().add(lblProductName);
        txtFName = new JTextField();
        txtFName.setText(this.curEmployee.getFirstName());
        txtFName.setBounds(232, 410, 140, 26);
        frame.getContentPane().add(txtFName);
        txtFName.setColumns(10);

        JLabel lblLName = new JLabel("Last Name:");
        lblLName.setBounds(150, 443, 82, 16);
        frame.getContentPane().add(lblLName);
        txtLName = new JTextField();
        txtLName.setText(this.curEmployee.getLastName());
        txtLName.setBounds(232, 440, 140, 26);
        frame.getContentPane().add(txtLName);
        txtLName.setColumns(10);
        
        JLabel lbluser = new JLabel("Username:");
        lbluser.setBounds(150, 473, 82, 16);
        frame.getContentPane().add(lbluser);
        txtUser = new JTextField();
        txtUser.setText(this.curEmployee.getUser());
        txtUser.setBounds(232, 469, 140, 26);
        frame.getContentPane().add(txtUser);
        txtUser.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(155, 505, 92, 16);
        frame.getContentPane().add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setText(this.curEmployee.getPassword());
        txtPassword.setBounds(232, 502, 140, 26);
        frame.getContentPane().add(txtPassword);
        txtPassword.setColumns(10);

        btnSave = new JButton("Save");
        btnSave.setBounds(90,540,150,50);
        btnSave.setEnabled(false);
        frame.getContentPane().add(btnSave);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(290,540,150,50);
        frame.getContentPane().add(btnCancel);

        ActionListener buttonListener = new ActionListener() {

            //we have to define this method in order for an Action Listener to work
            public void actionPerformed(ActionEvent e) { //'e' is the Action Event which is a button being clicked in our case

                if (e.getSource() == btnCancel) {
                    this.setVisible(false);
                    MainScreen main = new MainScreen(curEmployee);
                    main.setVisible(true);
                    frame.dispose();
                }
                else if (e.getSource() == btnFilePicker) {
                    FileDialog fd = new FileDialog(frame, "Choose Pic", FileDialog.LOAD);
                    fd.setVisible(true);

                    if ((fd.getFile().substring(fd.getFile().lastIndexOf(".") + 1).trim()).equals("jpg")) {
                        try {
                            BufferedImage img = ImageIO.read(new File(fd.getDirectory() + fd.getFile()));
                            ImageIcon newImage = new ImageIcon(img.getScaledInstance(labelWidth, labelHeight,
                                    Image.SCALE_SMOOTH));
                            imageLabel.setIcon(newImage);
                            curPicPath = Paths.get(fd.getDirectory() + fd.getFile());
                            updatedPic = true;
                            enableSaveBtn();
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
                    }
                }
                else if (e.getSource() == btnSave) {

                    if (updatedPic) {
                        System.out.println(curPicPath);
                        destinationPicPath = Paths.get(curEmployee.getPicFilePath());
                        System.out.println(destinationPicPath);
                        try {
                            Files.copy(curPicPath, destinationPicPath, REPLACE_EXISTING);
                        } catch (FileNotFoundException ex) {
                            System.out.println("File not found: " + ex);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (updatedFName) {
                        curEmployee.setFirstName(txtFName.getText());
                    }
                    if (updatedLName) {
                        curEmployee.setLastName(txtLName.getText());
                    }
                    if(updatedUser){
                    	curEmployee.setUserName(txtUser.getText());
                    }
                    if (updatedPassword) {
                        curEmployee.setPassword(String.valueOf(txtPassword.getPassword()));
                    }

                    this.setVisible(false);
                    MainScreen main = new MainScreen(curEmployee);
                    main.setVisible(true);
                    frame.dispose();
                }
            }

            private void setVisible(boolean b) {
                frame.setVisible(b);
            }
        };

        btnCancel.addActionListener(buttonListener);
        btnSave.addActionListener(buttonListener);
        btnFilePicker.addActionListener(buttonListener);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (e.getDocument() == txtFName.getDocument()) {
                    updatedFName = true;
                }
                else if (e.getDocument() == txtLName.getDocument()) {
                    updatedLName = true;
                }
                else if(e.getDocument() == txtUser.getDocument()){
                	updatedUser = true;
                }
                else {
                    updatedPassword = true;
                }
                enableSaveBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (e.getDocument() == txtFName.getDocument()) {
                    updatedFName = true;
                }
                else if (e.getDocument() == txtLName.getDocument()) {
                    updatedLName = true;
                }
                else if (e.getDocument() == txtUser.getDocument()) {
                    updatedUser = true;
                }
                else {
                    updatedPassword = true;
                }
                enableSaveBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (e.getDocument() == txtFName.getDocument()) {
                    updatedFName = true;
                }
                else if (e.getDocument() == txtLName.getDocument()) {
                    updatedLName = true;
                }
                else if (e.getDocument() == txtUser.getDocument()) {
                    updatedUser = true;
                }
                else {
                    updatedPassword = true;
                }
                enableSaveBtn();
            }
        };

        txtFName.getDocument().addDocumentListener(documentListener);
        txtLName.getDocument().addDocumentListener(documentListener);
        txtPassword.getDocument().addDocumentListener(documentListener);
        txtUser.getDocument().addDocumentListener(documentListener);
    }

    private void enableSaveBtn() {
        btnSave.setBackground(new Color(95, 186, 125));
        btnSave.setEnabled(true);
    }

    public void setVisible(boolean b) { this.frame.setVisible(b); }
}
