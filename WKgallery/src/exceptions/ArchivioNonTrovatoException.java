/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Eccezione in caso di archivio dati non trovato.
 * @author Marco Celesti
 */
public class ArchivioNonTrovatoException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Crea una nuova istanza di <code>ArchivioNonTrovatoException</code> senza messaggi di dettaglio.
     */
    public ArchivioNonTrovatoException() {
    }

    /**
     * Crea una nuova istanza di <code>ArchivioNonTrovatoException</code> con il messaggio di dettaglio specificato.
     * @param msg il messaggio di dettaglio
     */
    public ArchivioNonTrovatoException(String msg) {
        super(msg);
    }
}
