/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import abstractlayer.Artista;
import java.util.Comparator;

/**
 *
 * @author Marco Celesti
 */
public class ArtistaComparator implements Comparator<Artista> {

    public int compare(Artista a1, Artista a2) {
        return a1.getCognome().compareToIgnoreCase(a2.getCognome());
    }
}

    
