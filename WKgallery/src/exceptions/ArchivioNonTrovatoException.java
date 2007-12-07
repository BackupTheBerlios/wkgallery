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
public class ArchivioNonTrovatoException extends SQLException {

    /**
     * Creates a new instance of <code>ArchivioNonTrovatoException</code> without detail message.
     */
    public ArchivioNonTrovatoException() {
    }


    /**
     * Constructs an instance of <code>ArchivioNonTrovatoException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ArchivioNonTrovatoException(String msg) {
        super(msg);
    }
}
