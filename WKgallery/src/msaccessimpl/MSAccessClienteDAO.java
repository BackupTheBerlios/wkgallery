/*
 * MSAccessClienteDAO.java
 *
 * Created on 20 ottobre 2007, 18.15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package msaccessimpl;

import abstractlayer.Cliente;
import daorules.ClienteDAO;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import javax.sql.RowSet;
import obsolete.GenericDAO;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessClienteDAO implements ClienteDAO{
    private Connection connection;
    
    /** Creates a new instance of MSAccessClienteDAO */
    public MSAccessClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertCliente(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deleteCliente(int codiceCliente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Cliente findCliente(int codiceCliente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateCliente(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowSet selectClienteRS(Cliente criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectClienteTO(Cliente criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
