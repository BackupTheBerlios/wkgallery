/*
 * OperaDAO.java
 *
 * Created on 20 ottobre 2007, 17.22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package daorules;

import abstractlayer.Artista;
import abstractlayer.Opera;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.util.Vector;

/**
 * L'interfaccia di riferimento per l'implementazione delle classi che si occupano di 
 * interagire direttamente con l'archivio dei dati dell'entità Opera.
 * 
 * @author Marco Celesti
 */
public interface OperaDAO {

    /**
     * Permette l'inserimento nell'archivio di una nuova opera.
     * @param opera il nuovo record
     * @throws exceptions.RecordGiaPresenteException se il CodiceOpera è già stato utilizzato per un'altra opera
     * @throws exceptions.ChiavePrimariaException se il CodiceOpera del nuovo record è vuoto
     */
    public void insertOpera(Opera opera) throws RecordGiaPresenteException,
            ChiavePrimariaException;

    /**
     * Permette la cancellazione dall'archivio di un'opera.
     * @param codiceOpera il CodiceOpera dell'opera da cancellare
     * @throws exceptions.RecordNonPresenteException se il CodiceOpera non corrisponde ad alcun record
     */
    public void deleteOpera(String codiceOpera)
            throws RecordNonPresenteException, RecordCorrelatoException;

    /**
     * Permette di trovare un'opera tramite il suo CodiceOpera.
     * @param codiceOpera il CodiceOpera dell'opera da cercare
     * @return l'opera trovata
     * @throws exceptions.RecordNonPresenteException se il CodiceOpera non corrisponde ad alcun record
     */
    public Opera findOpera(String codiceOpera) throws RecordNonPresenteException;

    /**
     * Peremtte di recuperare tutte le opere presenti in archivio.
     * @return l'elenco delle opere
     */
    public Vector<Opera> findAllOpere();

    /**
     * Permette di aggiornare i campi di un'opera.
     * @param opera l'opera aggiornata
     * @throws exceptions.RecordNonPresenteException se l'opera non è presente in archivio
     */
    public void updateOpera(Opera opera) throws RecordNonPresenteException;

    /**
     * Permette di recuperare le opere diun particolare artista.
     * @param artista l'artista di cui si vogliono recuperare le opere
     * @return l'elenco di tutte le opere dell'artista
     */
    public Vector<Opera> selectOperaPerArtista(Artista artista);
    
    /**
     * Cancella tutti le opere presenti.
     * @throws exceptions.RecordCorrelatoException se le opere hanno record correlati
     */
    public void deleteAllOpere() throws RecordCorrelatoException;
}
