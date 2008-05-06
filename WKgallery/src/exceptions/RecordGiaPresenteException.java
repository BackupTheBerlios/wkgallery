/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Marco Celesti
 */
public class RecordGiaPresenteException extends Exception {

    /**
     * Creates a new instance of <code>RecordExistentException</code> without detail message.
     */
    public RecordGiaPresenteException() {
    }

    /**
     * Constructs an instance of <code>RecordExistentException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RecordGiaPresenteException(String msg) {
        super(msg);
    }
}
