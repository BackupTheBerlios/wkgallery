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
 * Classe astratta che rappresenta il punto di accesso alla sorgente dati
 * @author Marco Celesti
 */
public abstract class DAOFactory {

    // List of DAO types supported by the factory
    public static final int MSACCESS = 1;
    /*
    public static final int ORACLE = 2;
    public static final int SYBASE = 3;
     */

    /**
     * Restituisc il punto di accesso ai dati relativi all'entit� Artista
     * @return il riferimento all'istanza corretta.
     */
    public abstract ArtistaDAO getArtistaDAO();

    /**
     * Restituisc il punto di accesso ai dati relativi all'entit� Opera
     * @return il riferimento all'istanza corretta.
     */
    public abstract OperaDAO getOperaDAO();

    /**
     * Restituisc il punto di accesso ai dati relativi all'entit� Cliente
     * @return il riferimento all'istanza corretta.
     */
    public abstract ClienteDAO getClienteDAO();

    /**
     * Restituisc il punto di accesso ai dati relativi all'entit� Fattura
     * @return il riferimento all'istanza corretta.
     */
    public abstract FatturaDAO getFatturaDAO();

    /**
     * Permette di ottenere la connessione all'archivio dati per poter
     * accedere ad esso con i metodi definiti nelle interfacce specifiche.
     * @return l'oggetto per gestire la connesione
     * @throws exceptions.ArchivioNonTrovatoException se l'archivio di dati non
     * viene trovato
     */
    public abstract Connection getConnection() throws ArchivioNonTrovatoException;

    /**
     * Permette di recuperare l'implementazione del punto di accesso della sogente dati specificata.
     * @param whichFactory il tipo di sorgente dati.
     * @return
     */
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
            default:
                return null;
        }
    }
}
