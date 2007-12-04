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
     * @throws exceptions.RecordCorrelatoException se l'opera ha record correlati
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
     * 
     * @return
     */
    public Vector<Opera> findAllOpere();

    public void updateOpera(Opera opera) throws RecordNonPresenteException;

    public void updatePrezzo(Opera opera) throws RecordNonPresenteException;

    public Vector<Opera> selectOperaPerArtista(Artista artista);
}
