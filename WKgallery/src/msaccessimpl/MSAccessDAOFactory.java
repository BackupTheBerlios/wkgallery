package msaccessimpl;

import abstractlayer.*;
import java.sql.*;
import daorules.DAOFactory;
/*
 * MSAccessDAOFactory.java
 *
 * Utilizza il pattern Singleton
 */

/**
 *
 * @author Marco Celesti
 */
public class MSAccessDAOFactory extends DAOFactory {

    Connection connection;
    private static MSAccessDAOFactory msAccessDAOFactory;

    /** Creates a new instance of MSAccessDAOFactory */
    private MSAccessDAOFactory() {
    }

    public static MSAccessDAOFactory getMSAccessDAOFactory() {
        if (msAccessDAOFactory == null) {
            msAccessDAOFactory = new MSAccessDAOFactory();
        }
        return msAccessDAOFactory;

    }

    public Connection getConnection() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            // TODO: set right filepath
            String filename = "D:\\Documents and Settings\\Marco Celesti\\Documenti\\Università\\Laurea specialistica\\I° Anno LS\\II° Semestre\\Progetto di Informatica III\\Progetto\\DB\\WKgallery.mdb";
            filename = filename.replace('\\', '/').trim();

            String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            database += filename.trim() + ";DriverID=22;READONLY=true}"; // add on to the end
            // now we can get the connection from the DriverManager
            connection = DriverManager.getConnection(database, "", "");
            System.out.println("conn: " + connection);

        } catch (Exception ex) {
            System.err.println(ex);
        }

        return connection;
    }

    public MSAccessArtistaDAO getArtistaDAO() {
        return new MSAccessArtistaDAO(connection);
    }

    public MSAccessOperaDAO getOperaDAO() {
        return new MSAccessOperaDAO(connection);
    }

    public MSAccessClienteDAO getClienteDAO() {
        return new MSAccessClienteDAO(connection);
    }

    public MSAccessFatturaDAO getFatturaDAO() {
        return new MSAccessFatturaDAO(connection);
    }
}
