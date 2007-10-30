/*
 * GenericDAO.java
 *
 * Created on 20 ottobre 2007, 17.57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

// Interfaccia generica da utilizzarsi al posto delle interfacce specifiche dell'istanza

package obsolete;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Marco Celesti
 */
public interface GenericDAO<T, ID extends Serializable> {
    public int insertItem(T item);
    public boolean deleteArtista();
    public T findArtista(ID id);
    public boolean updateArtista(T item, ID id);

    List<T> findAll();
    List<T> findByExample(T exampleInstance);
    
    //public RowSet selectArtistaRS();
    //public Collection selectArtistaTO();
    //ArtistaDAO getArtistaDAO();
    //Collection selectArtistaTO(Artista criteria);
}
