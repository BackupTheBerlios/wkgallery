// Interfaccia obsoleta

/*
 * ClienteDAO.java
 *
 * Created on 20 ottobre 2007, 17.31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package daorules;

import abstractlayer.Cliente;
import java.util.Collection;
import javax.sql.RowSet;

/**
 *
 * @author Marco Celesti
 */
public interface ClienteDAO {
    /**
     * Permette l'inserimento di un nuovo cliente
     * @param cliente Il nuovo cliente
     * @return 0 se tutto bene<br>1 altrimenti
     */
    public int insertCliente(Cliente cliente);
    public boolean deleteCliente(int codiceCliente);
    public Cliente findCliente(int codiceCliente);
    public boolean updateCliente(Cliente cliente);
    public RowSet selectClienteRS(Cliente criteria);
    public Collection selectClienteTO(Cliente criteria);
}
