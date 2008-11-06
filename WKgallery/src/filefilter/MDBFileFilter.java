/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package filefilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * FileFilter per file di MS Access.
 * @author Marco Celesti
 */
public class MDBFileFilter extends FileFilter{

    /**
     * Sovrascrive il metodo di FileFilter
     * @param f il file da verificare
     * @return true se il file è di tipo MS Access
     */
    @Override
    public boolean accept(File f) {
        String filename = f.getName();
        if (f.isDirectory()) {
            return true;
        } else if (f.isFile()) {
            return filename.endsWith(".mdb");
        } else {
            return false;
        }
    }

    /**
     * Sovrascrive il metodo di FileFilter
     * @return la descrizione dei file accettati
     */
    @Override
    public String getDescription() {
        return "File MS Access (*.mdb)";
    }
}
