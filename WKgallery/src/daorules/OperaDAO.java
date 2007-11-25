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
import abstractlayer.Opera;
import java.util.Collection;
import javax.sql.RowSet;

/**
 *
 * @author Marco Celesti
 */
public interface OperaDAO {
    
    public boolean insertOpera(Opera opera);
    public boolean deleteOpera(int codiceOpera);
    public Opera findOpera(int codiceOpera);
    public boolean updateOpera(Opera opera);
    public RowSet selectOperaRS(Opera criteria);
    public Collection selectOperaTO(Opera criteria);
}
