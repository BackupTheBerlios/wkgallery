/*
 * ClienteDAO.java
 *
 * Created on 20 ottobre 2007, 17.31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package daorules;

import abstractlayer.Cliente;
import abstractlayer.Regione;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.util.Vector;

/**
 *
 * @author Marco Celesti
 */
public interface ClienteDAO {

    /**
     * Permette l'inserimento di un nuovo cliente.
     * @param cliente il nuovo cliente
     * @throws exceptions.RecordGiaPresenteException se il nuovo cliente ha un codice già presente in archivio
     * @throws exceptions.ChiavePrimariaException se il nuovo cliente ha il campo CodiceCliente vuoto
     */
    public void insertCliente(Cliente cliente) throws RecordGiaPresenteException,
                                                       ChiavePrimariaException;

    /**
     * Permette la cancellazione di un cliente.
     * @param codiceCliente il codice del cliente da cancellare
     * @throws exceptions.RecordNonPresenteException se il cliente non esiste in archivio
     * @throws exceptions.RecordCorrelatoException se il cliente ha record (fatture, in particolare) correlati
     */
    public void deleteCliente(String codiceCliente) throws RecordNonPresenteException,
                                                            RecordCorrelatoException;

    /**
     * Permette di trovare un cliente dato il suo codice.
     * @param codiceCliente il codice del cliente da trovare
     * @return il cliente trovato
     * @throws exceptions.RecordNonPresenteException se il cliente non esiste in archivio
     */
    public Cliente findCliente(String codiceCliente) throws RecordNonPresenteException;

    /**
     * Permette di recuperare tutti i clienti in archivio
     * @return il vettore contenente i clienti in archivio
     */
    public Vector<Cliente> findAllClienti();

    /**
     * Permette di recuperare tutti i clienti privati in archivio
     * @return il vettore contenente i clienti privati in archivio
     */
    public Vector<Cliente> findAllClientiPrivati();

    /**
     * Permette di recuperare tutti i clienti professionisti in archivio
     * @return il vettore contenente i clienti professionisti in archivio
     */
    public Vector<Cliente> findAllClientiProfessionisti();

    /**
     * Permette di aggiornare un record di un cliente già presente in archivio.
     * @param cliente il cliente aggiornato
     * @throws exceptions.RecordNonPresenteException se il cliente non esiste in archivio
     */
    public void updateCliente(Cliente cliente) throws RecordNonPresenteException;

    /**
     * Permette la ricerca di clienti in cui le lettere iniziali di <code>cognRsoc1</code> sono specificate dal parametro.
     * Ad esempio, con un archivio costituito da</br></br>
     * -Alessi</br>
     * -Bertoldi</br>
     * -Bianchi</br>
     * -Bianzini</br>
     * -Celesti</br></br>
     * <code>Vector<Cliente> v = selectClientiPerStringa("bi");</code></br>
     * restituirà l'elenco costituito da</br></br>
     * -Bianchi</br>
     * -Bianzini</br>
     * @param s la stringa con la/le lettera/e iniziali del congnome o ragione sociale
     * @return il vettore con i clienti che soddisfano il criterio richiesto
     */
    public Vector<Cliente> selectClientiPerStringa(String s);

    /**
     * Permette la ricerca di clienti per regione/i geografica/che
     * @param regioni il vettore con le regioni di interesse
     * @return il vettore con i clienti che soddisfano il criterio richiesto
     */
    public Vector<Cliente> selectClientiPerRegione(Vector<Regione> regioni);
}
