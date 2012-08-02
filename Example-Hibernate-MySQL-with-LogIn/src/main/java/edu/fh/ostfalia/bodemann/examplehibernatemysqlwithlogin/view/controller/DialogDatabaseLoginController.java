/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.view.controller;

//import edu.fh.ostfalia.teama.domain.Proje ctContainer;
import edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.persistence.SchemaConnection;
import edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.persistence.SessionManager;
import edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.view.dialog.DialogDatabaseLogin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Jacob
 */
public class DialogDatabaseLoginController {

    private DialogDatabaseLogin dialog;

    public DialogDatabaseLoginController(DialogDatabaseLogin dialog) {
        this.dialog = dialog;
    }

    public boolean logIn() {
        SchemaConnection sC = SchemaConnection.getInstance();
        
        //JDBC
        SchemaConnection.setPWD(dialog.getPassword());
        SchemaConnection.setUSER(dialog.getUsername());
        //Hibernate
        SessionManager.setPWD(dialog.getPassword());
        SessionManager.setUSER(dialog.getUsername());


        if (sC.getconnectionEstablished() == false) {
            dialog.getStatusLabel().setText("Falsche Logindaten!");
            dialog.getStatusLabel().setForeground(Color.RED.brighter());
        } else {
            dialog.getStatusLabel().setText("Login erfolgreich");
            dialog.getStatusLabel().setForeground(Color.GREEN.darker());
              
//            Das Schema wird in der Main erstellt!
//            sC.useDatabase_Befehl("CREATE SCHEMA if not exists SteffenTest");



            Timer t = new Timer(2000, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dialog.dispose(); //Fenster schließen
                }
            });
            t.start();

        }

        return sC.getconnectionEstablished();
    }
}
