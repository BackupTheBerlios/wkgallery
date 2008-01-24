/*
 * FatturaDAO.java
 *
 * Created on 21 ottobre 2007, 17.33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package daorules;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import exceptions.ChiavePrimariaException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.util.Vector;

/**
 *
 * @author Marco Celesti
 */
public interface FatturaDAO {

    /**
     * Permette l'inserimento di una nuova fattura. Tale metodo modifica di conseguenza 
     * i campi relativi all'entità Opera inclusi interessati dalla fattura.
     * @param fattura la nuova fattura
     * @throws exceptions.RecordGiaPresenteException se la nuova fattura è già presente in archivio
     * @throws exceptions.ChiavePrimariaException se la nuova fattura ha il campo Numero o Anno vuoti
     */
    public void insertFattura(Fattura fattura) throws RecordGiaPresenteException,
                                                       ChiavePrimariaException;

    /**
     * Permette la cancellazione di una fattura.
     * @param numero numero della fattura
     * @param anno anno della fattura
     * @throws exceptions.RecordNonPresenteException se la fattura non è presente in archivio
     */
    public void deleteFattura(int numero, int anno) throws RecordNonPresenteException;

    /**
     * Permette di trovare una fattura dati numero ed anno.
     * @param numero numero della fattura
     * @param anno anno della fattura
     * @return la fattura trovata
     * @throws exceptions.RecordNonPresenteException se la fattura non è presente in archivio
     */
    public Fattura findFattura(int numero, int anno) throws RecordNonPresenteException;

    /**
     * Permette di recuperare tutte le fatture in archivio.
     * @return la lista di tutte le fatture
     */
    public Vector<Fattura> findAllFatture();
    
    /**
     * Permette l'aggiornamento dell'istanza di fattura già presente in archivio: 
     * è possibile modificare il cliente correlato nonchè la lista delle opere
     * interessate dalla fattura.
     * @param fattura
     * @throws exceptions.RecordNonPresenteException
     */
    public void updateFattura(Fattura fattura) throws RecordNonPresenteException;

    /**
     * Permette di recuperare tutte le fatture emesse per un particolare cliente.
     * @param cliente il cliente di cui trovare le fatture
     * @return il vettore contenente le fatture del particolare cliente
     */
    public Vector<Fattura> selectFatturaPerCliente(Cliente cliente);

    /**
     * Permette di recuperare tutte le fatture emesse contenenti opere di un
     * particolare artista.
     * @param artista l'artista di cui si vogliono conoscere le fatture correlate
     * ad opere da questi prodotte
     * @return il vettore contenente le fatture richieste
     */
    public Vector<Fattura> selectFatturaPerArtista(Artista artista);
}
