/*
 * MSAccessOperaDAO.java
 *
 * Created on 20 ottobre 2007, 17.28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package msaccessimpl;

import abstractlayer.Opera;
import daorules.OperaDAO;
import java.sql.Connection;
import java.util.Collection;
import javax.sql.RowSet;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessOperaDAO implements OperaDAO{
    private Connection connection;
    
    /** Creates a new instance of MSAccessOperaDAO */
    public MSAccessOperaDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertOpera(Opera opera) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deleteOpera(int codiceOpera) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Opera findOpera(int codiceOpera) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateOpera(Opera opera) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowSet selectOperaRS(Opera criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectOperaTO(Opera criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /*
    
  // The following methods can use
  // CloudscapeDAOFactory.createConnection() 
  // to get a connection as required

  public int insertOpera() {
    // Implement insert customer here.
    // Return newly created customer number
    // or a -1 on error
  }
  
  public boolean deleteOpera() {
    // Implement delete customer here
    // Return true on success, false on failure
  }

  public Opera findOpera() {
    // Implement find a customer here using supplied
    // argument values as search criteria
    // Return a Transfer Object if found,
    // return null on error or if not found
  }

  public boolean updateOpera() {
    // implement update record here using data
    // from the customerData Transfer Object
    // Return true on success, false on failure or
    // error
  }

  public RowSet selectOperaRS() {
    // implement search customers here using the
    // supplied criteria.
    // Return a RowSet. 
  }

  public Collection selectOperaTO() {
    // implement search customers here using the
    // supplied criteria.
    // Alternatively, implement to return a Collection 
    // of Transfer Objects.
  }

    public OperaDAO getOperaDAO() {
    }

    public Collection selectOperaTO(Opera criteria) {
    }
     * */
}

