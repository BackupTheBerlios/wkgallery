/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import abstractlayer.Opera;

/**
 * Eccezione per la gestione di opere prive di titolo, necessario per la fatturazione.
 * @author Marco Celesti
 */
public class TitoloNonPresenteException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Opera o;

    /**
     * Crea una nuova istanza di <code>TitoloNonPresenteException</code> senza messaggi di dettaglio.
     */
    public TitoloNonPresenteException() {
    }

    /**
     * Crea una nuova istanza di <code>TitoloNonPresenteException</code> con il messaggio di dettaglio specificato.
     * @param msg il messaggio di dettaglio
     * @param o l'opera priva di titolo
     */
    public TitoloNonPresenteException(String msg, Opera o) {
        super(msg);
        this.o = o;
    }

    /**
     * Metodo <i>get</i> per <code>opera</code>.
     * @return l'opera priva di titolo
     */
    public Opera getOpera() {
        return o;
    }
}
