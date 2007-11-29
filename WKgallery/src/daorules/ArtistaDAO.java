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
import exceptions.RecordGiaPresenteException;

/** 
 * 
 *
 * @author Marco Celesti
 */
public interface ArtistaDAO {

    /**
     * Permette l'inserimento nell'archivio di un nuovo artista.
     * @param artista il nuovo record
     * @throws exceptions.RecordGiaPresenteException se il CodiceArtista è già stato utilizzato per un altro artista
     */
    public void insertArtista(Artista artista) throws RecordGiaPresenteException;
    
    /**
     * Permette la cancellazione dall'archivio di un artista.
     * @param codiceArtista
     * @throws java.lang.Exception
     */
    public void deleteArtista(int codiceArtista) throws Exception;
    
    public Artista findArtista(int codiceArtista) throws Exception;
    
    public void updateArtista(Artista artista) throws Exception;
}
