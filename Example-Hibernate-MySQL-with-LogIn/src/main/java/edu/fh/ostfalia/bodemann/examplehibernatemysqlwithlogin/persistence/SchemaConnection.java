/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.persistence;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.tools.ij;

/**
 *
 * @author Steffen Die Klasse Connection ist ein Singelton!
 */
public class SchemaConnection {

    private static boolean connectionEstablished = false;
    //***************************************************************************
    /*
     * Datenbank Einstellungen. In diesem Beispiel arbeiten wir mit einem MySQL-Server. Die Attribute USER und PSW
     * werden über ein extra LogIn Fenster abgefragt, welches sich zum Programmstart öffnet.
     */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mysql";
    private static String USER;
    private static String PWD;
    //***************************************************************************
    private static Connection dbconn = null;
    // Aufbau-Muster Singelton
    private static SchemaConnection instance = null;

    public static SchemaConnection getInstance() {
        if (SchemaConnection.instance == null) {
            SchemaConnection.instance = new SchemaConnection();
        }

        SchemaConnection.instance.connect();
        return SchemaConnection.instance;
    }

    private void connect() {
        if (dbconn == null) { //nur wenn DB-Verbindung noch nicht besteht
            try {
                try {
                    Class.forName(JDBC_DRIVER);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SchemaConnection.class.getName()).log(Level.SEVERE, null, ex);
                }

                dbconn = DriverManager.getConnection(DATABASE_URL, USER, PWD);  //Wenn DB-Verbindung funktioniert
                System.out.println("Eine Verbindung mit der Datenbank wurde aufgebaut!");
                connectionEstablished = true;

            } catch (SQLException e) {
                System.out.println("Allgemeine Probleme mit SQL. Syntax korrekt? Passwort korrekt?");
                connectionEstablished = false;
                dbconn = null;          //sonst, packe "null" in die DB-Conenction-Referenz
            }
        }
    }

    public boolean getconnectionEstablished() {
        return connectionEstablished;
    }

    public static void setPWD(String password) {
        PWD = password;
    }

    public static void setUSER(String username) {
        USER = username;
    }

    /**
     * Die Methode baut eine Datenbankverbindung auf und ruft in dieser die Methode runScript auf, welche eine .SQL
     * einliest
     *
     * @param Skript Enthält einen String, welches den Pfad zu einer .SQL Datei enthält
     */
    public void useDatabase_Befehl(String SQL_Befehl) {
        Statement stmt = null;
        try {
            stmt = dbconn.createStatement();
            stmt.execute(SQL_Befehl);
            System.out.println("Der SQL-Befehl wurde erfolgreich ausgeführt.");
        } catch (SQLException e) {
            System.out.println("Probleme mit SQL. Syntax korrekt? Passwort korrekt?");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Probleme mit SQL. Syntax korrekt? Passwort korrekt?");
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                dbconn.close();
            } catch (SQLException e) {
                System.out.println("Problem beim Schliessen der Verbindung");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Probleme mit SQL. Syntax korrekt? Passwort korrekt?");
                e.printStackTrace();
            }
        }
    }

    /**
     * Die Methode baut eine Datenbankverbindung auf und führt einen Execute Befehl aus, welcher in Form einens String
     * der Methode übergeben wurde.
     *
     * @param SQL_Befehl
     */
    public void useDatabase_Skript(String Skript) {
        try {
            runScript(new File(Skript), dbconn);
        } finally {
            try {
                dbconn.close();
            } catch (SQLException e) {
                System.out.println("Problem beim Schliessen der Verbindung");
            }
        }
    }

    /**
     * Die Methode ist ein einfaches Dienstprogramm zum Ausführen von Skripts gegen eine Derby-Datenbank.
     *
     * @param scriptFile Enthält den Pfad des auszuführenden Skripts
     * @param connection Enthält die Connection zur aktuellen Datenbank
     * @return Muss true oder false sein. Mit true wurde alles erfolgreich bearbeitet, mit false gab es eine Exception
     */
    public boolean runScript(File scriptFile, java.sql.Connection connection) {
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream(scriptFile);

            int result = ij.runScript(connection, fileStream, "UTF-8", System.out, "UTF-8");

            System.out.println("Result code is: " + result);
            return (result == 0);
        } catch (FileNotFoundException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
