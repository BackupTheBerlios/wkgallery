/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Marco Celesti
 */
public class BadFormatException extends Exception {

    /**
     * Creates a new instance of <code>BadFormatException</code> without detail message.
     */
    public BadFormatException() {
    }

    /**
     * Constructs an instance of <code>BadFormatException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BadFormatException(String msg) {
        super(msg);
    }
}
