package msaccessimpl;

import daoabstract.DAOFactory;
import exceptions.ArchivioNonTrovatoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementazione della classe astratta DAOFactory per MS Access.
 * @author Marco Celesti
 */
public class MSAccessDAOFactory extends DAOFactory {

    /**
     * Oggetto per la connessione al DB
     */
    protected static Connection connection;
    private static MSAccessDAOFactory msAccessDAOFactory;

    /** Crea una nuova istanza di MSAccessDAOFactory */
    private MSAccessDAOFactory() {
    }

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static MSAccessDAOFactory getMSAccessDAOFactory() {
        if (msAccessDAOFactory == null) {
            msAccessDAOFactory = new MSAccessDAOFactory();
        }
        return msAccessDAOFactory;

    }

    /**
     * Implementazione del metodo definito dalla classe astratta
     * @return la connessione al DB
     * @throws exceptions.ArchivioNonTrovatoException se il DB non viene trovato nel percorso specificato.
     */
    @Override
    public Connection getConnection(String path) throws ArchivioNonTrovatoException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        String filename = path;
        if (filename == null) {
            throw new ArchivioNonTrovatoException();
        }
        filename = filename.replace('\\', '/').trim();

        String database =
                "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
        database += filename.trim() + ";DriverID=22;READONLY=false}";
        try {
            connection = DriverManager.getConnection(database);
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            throw new ArchivioNonTrovatoException();
        }
        return connection;
    }

    /**
     * Permette la chiusura della connessione con il DB.
     * @return true se la connessione avviene correttamente
     */
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

    @Override
    public MSAccessArtistaDAO getArtistaDAO() {
        return MSAccessArtistaDAO.getMSAccessArtistaDAO();
    }

    @Override
    public MSAccessOperaDAO getOperaDAO() {
        return MSAccessOperaDAO.getMSAccessArtistaDAO();
    }

    @Override
    public MSAccessClienteDAO getClienteDAO() {
        return MSAccessClienteDAO.getMSAccessClienteDAO();
    }

    @Override
    public MSAccessFatturaDAO getFatturaDAO() {
        return MSAccessFatturaDAO.getMSAccessArtistaDAO();
    }
}
