/*
 * FatturaDAO.java
 *
 * Created on 21 ottobre 2007, 17.33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package daorules;

import abstractlayer.Fattura;
import java.util.Collection;
import javax.sql.RowSet;

/**
 *
 * @author Marco Celesti
 */
public interface FatturaDAO {
    
    public int insertFattura(Fattura fattura);
    public boolean deleteFattura(int numero_anno);
    public Fattura findFattura(int numero_anno);
    public boolean updateFattura(Fattura fattura);
    public RowSet selectFatturaRS(Fattura fattura);
    public Collection selectFatturaTO(Fattura criteria);

}
