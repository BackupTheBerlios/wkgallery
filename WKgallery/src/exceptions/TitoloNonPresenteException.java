/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import abstractlayer.Opera;

/**
 *
 * @author Marco Celesti
 */
public class TitoloNonPresenteException extends Exception {

    private Opera o;

    public TitoloNonPresenteException() {
    }

    public TitoloNonPresenteException(String msg, Opera o) {
        super(msg);
        this.o = o;
    }

    public Opera getOpera() {
        return o;
    }
}
