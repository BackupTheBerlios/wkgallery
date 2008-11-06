/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.sql.SQLException;

/**
 * Eccezione per violazioni di integrità referenziali.
 * @author Marco Celesti
 */
public class RecordCorrelatoException extends SQLException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nuova istanza di <code>RecordCorrelatoException</code> senza messaggi di dettaglio.
     */
    public RecordCorrelatoException() {
    }

    /**
     * Crea una nuova istanza di <code>RecordCorrelatoException</code> con il messaggio di dettaglio specificato.
     * @param msg il messaggio di dettaglio
     */
    public RecordCorrelatoException(String msg) {
        super(msg);
    }
}
