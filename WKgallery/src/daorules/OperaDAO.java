/*
 * OperaDAO.java
 *
 * Created on 20 ottobre 2007, 17.22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package daorules;

/**
 *
 * @author Marco Celesti
 */
import abstractlayer.Artista;
import abstractlayer.Opera;
import java.util.Vector;
/**
 *
 * @author Marco Celesti
 */
public interface OperaDAO {
    
    public boolean insertOpera(Opera opera);
    public boolean deleteOpera(String codiceOpera);
    public Opera findOpera(String codiceOpera);
    public boolean updateOpera(Opera opera);
    public Vector<Opera> selectOperaPerArtista(Artista artista);
}
