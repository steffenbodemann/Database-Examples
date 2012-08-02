package edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin;

import edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.persistence.SchemaConnection;
import edu.fh.ostfalia.bodemann.examplehibernatemysqlwithlogin.view.dialog.DialogDatabaseLogin;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        boolean connectionEstablished = new DialogDatabaseLogin().createView();

        if (connectionEstablished) {
//            ProjectContainer.getInstance(); //laden aus der DB
//            new ViewApplicationFrame();
            SchemaConnection.getInstance().useDatabase_Befehl("CREATE SCHEMA if not exists SteffenTestTest");
        }
    }
}
