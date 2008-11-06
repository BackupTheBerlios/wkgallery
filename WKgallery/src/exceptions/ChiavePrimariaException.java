/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Eccezione per violazioni di chiave primaria.
 * @author Marco Celesti
 */
public class ChiavePrimariaException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nuova istanza di <code>ChiavePrimariaException</code> senza messaggi di dettaglio.
     */
    public ChiavePrimariaException() {
    }

    /**
     * Crea una nuova istanza di <code>ChiavePrimariaException</code> con il messaggio di dettaglio specificato.
     * @param msg il messaggio di dettaglio
     */
    public ChiavePrimariaException(String msg) {
        super(msg);
    }
}
