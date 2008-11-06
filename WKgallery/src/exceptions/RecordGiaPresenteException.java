/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Eccezione in caso di record duplicati.
 * @author Marco Celesti
 */
public class RecordGiaPresenteException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nuova istanza di <code>RecordGiaPresenteException</code> senza messaggi di dettaglio.
     */
    public RecordGiaPresenteException() {
    }

    /**
     * Crea una nuova istanza di <code>RecordGiaPresenteException</code> con il messaggio di dettaglio specificato.
     * @param msg il messaggio di dettaglio
     */
    public RecordGiaPresenteException(String msg) {
        super(msg);
    }
}
