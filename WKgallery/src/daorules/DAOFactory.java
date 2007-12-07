/*
 * DAOFactory.java
 *
 * Created on 14 ottobre 2007, 17.40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package daorules;

import exceptions.ArchivioNonTrovatoException;
import java.sql.Connection;
import msaccessimpl.MSAccessDAOFactory;

/**
 *
 * @author Marco Celesti
 */
public abstract class DAOFactory {
    
    // List of DAO types supported by the factory
    public static final int MSACCESS = 1;
    /*
    public static final int ORACLE = 2;
    public static final int SYBASE = 3;
    */
   
    public abstract ArtistaDAO getArtistaDAO();
    public abstract OperaDAO getOperaDAO();
    public abstract ClienteDAO getClienteDAO();
    public abstract FatturaDAO getFatturaDAO();
    
    /**
     * Permette di ottenere la connessione all'archivio dati per poter
     * accedere ad esso con i metodi definiti nelle interfacce specifiche.
     * @return l'oggetto per gestire la connesione
     * @throws exceptions.ArchivioNonTrovatoException se l'archivio di dati non
     * viene trovato
     */
    public abstract Connection getConnection() throws ArchivioNonTrovatoException;
    
    public static DAOFactory getDAOFactory(int whichFactory) {
        
        switch (whichFactory) {
            case MSACCESS:
                return MSAccessDAOFactory.getMSAccessDAOFactory();
            /*
            case ORACLE    :
                return new OracleDAOFactory();
            case SYBASE    :
                return new SybaseDAOFactory();
            */    
            default           :
                return null;
        }
    }
    
}
