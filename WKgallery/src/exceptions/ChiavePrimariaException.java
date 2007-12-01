/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Marco Celesti
 */
public class ChiavePrimariaException extends Exception {

    /**
     * Creates a new instance of <code>ChiavePrimariaException</code> without detail message.
     */
    public ChiavePrimariaException() {
    }


    /**
     * Constructs an instance of <code>ChiavePrimariaException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ChiavePrimariaException(String msg) {
        super(msg);
    }
}
