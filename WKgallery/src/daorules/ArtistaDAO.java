/*
 * ArtistaDAO.java
 *
 * Created on 14 ottobre 2007, 17.55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package daorules;

import abstractlayer.Artista;

/**
 *
 * @author Marco Celesti
 */
public interface ArtistaDAO {
    public boolean insertArtista(Artista artista);
    public boolean deleteArtista(int codiceArtista);
    public Artista findArtista(int codiceArtista);
    public boolean updateArtista(Artista artista);
}
