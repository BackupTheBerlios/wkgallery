/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Eccezione in caso di record non presenti in archivio.
 * @author Marco Celesti
 */
public class RecordNonPresenteException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nuova istanza di <code>RecordNonPresenteException</code> senza messaggi di dettaglio.
     */
    public RecordNonPresenteException() {
    }

    /**
     * Crea una nuova istanza di <code>RecordNonPresenteException</code> con il messaggio di dettaglio specificato.
     * @param msg il messaggio di dettaglio
     */
    public RecordNonPresenteException(String msg) {
        super(msg);
    }
}
