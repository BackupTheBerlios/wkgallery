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
import abstractlayer.Opera;
import exceptions.BadFormatException;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.util.Vector;

/**
 * L'interfaccia di riferimento per l'implementazione delle classi che si occupano di 
 * interagire direttamente con l'archivio dati dell'entità Fattura.
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
            ChiavePrimariaException, RecordCorrelatoException;

    /**
     * Permette la cancellazione di una fattura.
     * @param fattura la fattura da cancellare
     * @throws exceptions.RecordNonPresenteException se la fattura non è presente in archivio
     */
    public void deleteFattura(int numero, int anno) throws RecordNonPresenteException;

    /**
     * Permette di trovare una fattura dati numero ed anno.
     * @param numero numero della fattura
     * @param anno anno della fattura
     * @return la fattura trovata
     * @throws exceptions.RecordNonPresenteException se la fattura non è presente in archivio
     * @throws exceptions.BadFormatException se la data non è corretta
     */
    public Fattura findFattura(int numero, int anno) throws RecordNonPresenteException, BadFormatException;

    /**
     * Permette di recuperare tutte le fatture in archivio.
     * @return la lista di tutte le fatture
     * @throws exceptions.BadFormatException se la data non è corretta
     */
    public Vector<Fattura> findAllFatture() throws BadFormatException;

    /**
     * Permette l'aggiornamento della fattura già presente in archivio: 
     * è possibile modificare il cliente, la data e lo sconto.
     * @param fattura la fattura da aggiornare
     * @throws exceptions.RecordNonPresenteException se la fattura non è presente in archivio
     */
    public void updateFattura(Fattura fattura) throws RecordNonPresenteException;

    /**
     * Permette di recuperare tutte le fatture emesse per un particolare cliente.
     * @param cliente il cliente di cui trovare le fatture
     * @return il vettore contenente le fatture del particolare cliente
     * @throws exceptions.BadFormatException se la data non è corretta
     */
    public Vector<Fattura> selectFatturaPerCliente(Cliente cliente) throws BadFormatException;

    /**
     * Permette di recuperare tutte le fatture emesse contenenti opere di un
     * particolare artista.
     * @param artista l'artista di cui si vogliono conoscere le fatture correlate
     * ad opere da questi prodotte
     * @return il vettore contenente le fatture richieste
     * @throws exceptions.BadFormatException se la data non è corretta
     */
    public Vector<Fattura> selectFatturaPerArtista(Artista artista) throws BadFormatException;

    /**
     * Permette di recuperare la fattura relativa ad un'opera.
     * @param opera l'opera contenuta nella fattura da cercare
     * @return la fattura trovata
     * @throws exceptions.RecordNonPresenteException se la fattura non è presente in archivio
     * @throws exceptions.BadFormatException se la data non è corretta
     */
    public Fattura selectFatturaPerOpera(Opera opera) throws RecordNonPresenteException, BadFormatException;

    /**
     * Cancella tutti le fatture presenti.
     */
    public void deleteAllFatture();
    
    /**
     * Permette il passaggio di stato della fattura, da proforma a definitiva.
     * @param fattura la fattura da modificare
     * @throws exceptions.RecordNonPresenteException se la fattura non viene trovata
     */
    public void updateFatturaDefinitiva(Fattura fattura) throws RecordNonPresenteException;
    
    /**
     * Permette di disimpegnare un'opera prima contenuta in una fattura.
     * @param opera l'opera da disimpegnare
     * @param fattura la fattura da cui l'opera è stata tolta
     * @throws exceptions.RecordNonPresenteException se la fattura non viene trovata
     */
    public void updateOperaDisimpegnata(Opera opera, Fattura fatturaDaAggiornare) throws RecordNonPresenteException;
    
    /**
     * Permette di impegnare un'opera in una fattura <B>già esistente</B>.
     * @param opera l'opera da aggiungere alla fattura
     * @param fattura la fattura in cui aggiungere l'opera
     * @throws exceptions.RecordNonPresenteException se la fattura non viene trovata
     */
    public void updateOperaAggiunta(Opera opera, Fattura fatturaDaAggiornare) throws RecordNonPresenteException;
}
