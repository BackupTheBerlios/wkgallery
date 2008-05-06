/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.sql.SQLException;

/**
 *
 * @author Marco Celesti
 */
public class RecordCorrelatoException extends SQLException {

    /**
     * Creates a new instance of <code>RecordCorrelatoException</code> without detail message.
     */
    public RecordCorrelatoException() {
    }

    /**
     * Constructs an instance of <code>RecordCorrelatoException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RecordCorrelatoException(String msg) {
        super(msg);
    }
}
