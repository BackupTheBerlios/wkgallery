/*
 * MSAccessFatturaDao.java
 *
 * Created on 20 ottobre 2007, 18.21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package msaccessimpl;

import abstractlayer.Fattura;
import daorules.FatturaDAO;
import java.sql.Connection;
import java.util.Collection;
import javax.sql.RowSet;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessFatturaDAO implements FatturaDAO{
    private Connection connection;
    
    /** Creates a new instance of MSAccessFatturaDao */
    public MSAccessFatturaDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertFattura(Fattura fattura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deleteFattura(int numero_anno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Fattura findFattura(int numero_anno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateFattura(Fattura fattura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowSet selectFatturaRS(Fattura fattura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectFatturaTO(Fattura criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
}
