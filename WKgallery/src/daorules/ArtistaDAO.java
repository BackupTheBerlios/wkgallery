/*
 * ArtistaDAO.java
 *
 * Created on 14 ottobre 2007, 17.55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package daorules;

import abstractlayer.Artista;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.util.Vector;

/** 
 * L'interfaccia di riferimento per l'implementazione delle classi che si occupano di 
 * interagire direttamente con l'archivio dati dell'entità Artista.
 *
 * @author Marco Celesti
 */
public interface ArtistaDAO {

    /**
     * Permette l'inserimento nell'archivio di un nuovo artista.
     * @param artista il nuovo record
     * @throws exceptions.RecordGiaPresenteException se il CodiceArtista è già stato utilizzato per un altro artista
     * @throws exceptions.ChiavePrimariaException se il CodiceArtista del nuovo record è vuoto
     */
    public void insertArtista(Artista artista) throws RecordGiaPresenteException,
            ChiavePrimariaException;

    /**
     * Permette la cancellazione dall'archivio di un artista.
     * @param codiceArtista il CodiceArtista dell'artista da cancellare
     * @throws exceptions.RecordNonPresenteException se il CodiceArtista non corrisponde ad alcun record
     * @throws exceptions.RecordCorrelatoException se l'artista ha record correlati
     */
    public void deleteArtista(int codiceArtista) throws RecordNonPresenteException,
            RecordCorrelatoException;

    /**
     * Permette di trovare un artista tramite il suo CodiceArtista.
     * @param codiceArtista il CodiceArtista dell'artista da cercare
     * @return l'artista trovato
     * @throws exceptions.RecordNonPresenteException se il CodiceArtista non corrisponde ad alcun record
     */
    public Artista findArtista(int codiceArtista) throws RecordNonPresenteException;
    
    /**
     * Restituisce il vettore contenente l'intera lista di artisti.
     * @return il vettore con gli artisti
     */
    public Vector<Artista> findAllArtisti();

    /**
     * Permette di aggiornare i campi dell'artista, ma non il suo CodiceArtista.
     * @param artista l'artista aggiornato
     * @throws exceptions.RecordNonPresenteException se l'artista passato non aggiorna alcun record già esistente
     */
    public void updateArtista(Artista artista) throws RecordNonPresenteException;
}
