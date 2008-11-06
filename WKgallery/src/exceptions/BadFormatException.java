/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Eccezione per campi dati malformattati.
 * @author Marco Celesti
 */
public class BadFormatException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nuova istanza di <code>BadFormatException</code> senza messaggi di dettaglio.
     */
    public BadFormatException() {
    }

    /**
     * Crea una nuova istanza di <code>BadFormatException</code> con il messaggio di dettaglio specificato.
     * @param msg il messaggio di dettaglio
     */
    public BadFormatException(String msg) {
        super(msg);
    }
}
