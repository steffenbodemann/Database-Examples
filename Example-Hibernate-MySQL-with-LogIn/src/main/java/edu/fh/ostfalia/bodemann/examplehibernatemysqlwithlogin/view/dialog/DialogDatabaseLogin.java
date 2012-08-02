package edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.view.dialog;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.view.controller.DialogDatabaseLoginController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Jacob
 */
public class DialogDatabaseLogin extends JDialog {

    private DialogDatabaseLoginController controller;
    private boolean connectionEstablished;
    private JTextField usernameTxtF;
    private JPasswordField passwordF;
    private JLabel loginStatus;

    public DialogDatabaseLogin() {
        this.controller = new DialogDatabaseLoginController(this);
    }

//    public void createView() {
    public boolean createView() {

        try {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } catch (Exception e) {
            System.out.println("Fehler bei dem Look And Feel");
        }

        setTitle("Datenbank-Login");

        JPanel mainPanel = new JPanel();
//        JPanel mainPanel = new FormDebugPanel();
        JPanel statusPanel = new JPanel();

        mainPanel.setLayout(new FormLayout(
                "12dlu, left:pref, 10dlu, fill:130, 12dlu", //columns 

                "10dlu,     fill:pref," //rows 
                + "5dlu,    fill:15dlu,"
                + "4dlu,    fill:15dlu,"
                + "5dlu,    fill:pref,"
                + "5dlu,    fill:pref,"
                + "5dlu,    fill:pref,"
                + "5dlu"));



        CellConstraints cc = new CellConstraints();

        JLabel titleLabel = new JLabel("MySQL-Logindaten");
        JLabel usernameLabel = new JLabel("Username:");
        usernameTxtF = new JTextField();
        JLabel passwordLabel = new JLabel("Passwort:");
        passwordF = new JPasswordField();
        JSeparator trennlinie = new JSeparator();
        JButton loginButton = new JButton("Einloggen");
        JLabel loginLabel = new JLabel("Status:");
        loginStatus = new JLabel("Not logged in");


        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                connectionEstablished = controller.logIn();
            }
        });


        //===Main_Panel als Hauptfenster einfügen===
        setContentPane(mainPanel);

        mainPanel.add(titleLabel, cc.xy(4, 2));
        mainPanel.add(usernameLabel, cc.xy(2, 4));
        mainPanel.add(usernameTxtF, cc.xy(4, 4));
        mainPanel.add(passwordLabel, cc.xy(2, 6));
        mainPanel.add(passwordF, cc.xy(4, 6));
        mainPanel.add(trennlinie, cc.xyw(2, 8, 3));
        mainPanel.add(loginButton, cc.xy(4, 10));



        mainPanel.add(loginLabel, cc.xy(2, 12));
        mainPanel.add(loginStatus, cc.xy(4, 12));

        //Allgemeine Anweisungen
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);

        return connectionEstablished;
    }

    public String getUsername() {
        return usernameTxtF.getText();
    }

    public String getPassword() {
        return new String(passwordF.getPassword());
    }

    public JLabel getStatusLabel() {
        return loginStatus;
    }
}
