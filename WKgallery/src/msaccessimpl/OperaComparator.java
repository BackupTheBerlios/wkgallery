/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package msaccessimpl;

import abstractlayer.Opera;
import java.util.Comparator;

/**
 *
 * @author Marco Celesti
 */
public class OperaComparator implements Comparator<Opera> {

    public int compare(Opera o1, Opera o2) {
        return o1.getArtista().getCognome().compareToIgnoreCase(o2.getArtista().
                getCognome());
    }
}
