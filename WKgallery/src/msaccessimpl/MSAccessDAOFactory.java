package msaccessimpl;

import daorules.DAOFactory;
/*
 * MSAccessDAOFactory.java
 *
 * Utilizza il pattern Singleton
 */
import exceptions.ArchivioNonTrovatoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessDAOFactory extends DAOFactory {

    protected static Connection connection;
    
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

    public Connection getConnection() throws ArchivioNonTrovatoException {
        // now we can get the connection from the DriverManager
        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            // TODO: set right filepath
            String filename =
                    "C:\\Documents and Settings\\Marco Celesti\\Documenti\\Università\\I° Anno LS\\II° Semestre\\Progetto di Informatica III\\Progetto\\DB\\wunderkammer.mdb";
            filename = filename.replace('\\', '/').trim();

            String database =
                    "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            database += filename.trim(); // + ";DriverID=22;READONLY=false}"; // add on to the end
            // now we can get the connection from the DriverManager
            connection = DriverManager.getConnection(database, "", "");
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean closeConnection() {
        try {
            connection.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return false;
    }

    public MSAccessArtistaDAO getArtistaDAO() {
        return MSAccessArtistaDAO.getMSAccessArtistaDAO();
    }

    public MSAccessOperaDAO getOperaDAO() {
        return MSAccessOperaDAO.getMSAccessArtistaDAO();
    }
    
    public MSAccessClienteDAO getClienteDAO() {
        return MSAccessClienteDAO.getMSAccessClienteDAO();
    }

    public MSAccessFatturaDAO getFatturaDAO() {
        return MSAccessFatturaDAO.getMSAccessArtistaDAO();
    }
}
