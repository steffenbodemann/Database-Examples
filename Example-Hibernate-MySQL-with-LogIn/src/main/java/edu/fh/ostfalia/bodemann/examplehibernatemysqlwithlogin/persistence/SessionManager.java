/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Steffen
 */
public class SessionManager {

    private SessionFactory sessionFactory;
    private static Session session;
    private Transaction transaction;
    //***************************************************************************
    private static String USER;
    //***************************************************************************
    private static String PWD;
    //***************************************************************************
    // Aufbau-Muster Singelton
    private static SessionManager instance = null;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (SessionManager.instance == null) {
            SessionManager.instance = new SessionManager();
            session = SessionManager.instance.createSession();
        }
        return SessionManager.instance;
    }

    private Session createSession() {
        //NEU: Hibernate.cfg.xml laden, aber Passwort & User automatisch aus LoginDialog
        //nehmen; diese werden NICHT in der hibernate.cfg.xml abgespeichert
        AnnotationConfiguration cfg = new AnnotationConfiguration();
        cfg.configure();
        cfg.setProperty("hibernate.connection.username", USER);
        cfg.setProperty("hibernate.connection.password", PWD);
        sessionFactory = cfg.buildSessionFactory();

        SessionManager.session = sessionFactory.openSession();
        this.transaction = session.beginTransaction();

        System.out.println("Session Factory samt Session und Transaktion wurde aufgebaut!");

        return session;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void closeDBCon() {
        session.close();
        sessionFactory.close();
    }

    public static void setPWD(String password) {
        PWD = password;
    }

    public static void setUSER(String username) {
        USER = username;
    }
}
