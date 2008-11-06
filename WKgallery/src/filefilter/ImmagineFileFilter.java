package filefilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * FileFilter per file di immagini.
 * @author Marco Celesti
 */
public class ImmagineFileFilter extends FileFilter {

    /**
     * Sovrascrive il metodo di FileFilter
     * @param f il file da verificare
     * @return true se il file è un'immagine
     */
    @Override
    public boolean accept(File f) {
        String filename = f.getName().toLowerCase();
        if (f.isDirectory()) {
            return true;
        } else if (f.isFile()) {
            return (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".gif") || filename.endsWith(".png") || filename.endsWith(".tif"));
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
        return "File di immagini (*.jpg, *.jpeg, *.gif, *.png, *.tif)";
    }
}
