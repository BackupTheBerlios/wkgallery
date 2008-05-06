/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Marco Celesti
 */
public class RecordNonPresenteException extends Exception {

    /**
     * Creates a new instance of <code>RecordNonPresente</code> without detail message.
     */
    public RecordNonPresenteException() {
    }

    /**
     * Constructs an instance of <code>RecordNonPresente</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RecordNonPresenteException(String msg) {
        super(msg);
    }
}
