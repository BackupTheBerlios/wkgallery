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
import abstractlayer.ClientePrivato;
import abstractlayer.ClienteProfessionista;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.util.Collection;
import java.util.Vector;
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
    public void insertCliente(Cliente cliente) throws RecordGiaPresenteException;

    public void deleteCliente(String codiceCliente, int tipoCliente) throws RecordNonPresenteException,
            RecordCorrelatoException;

    public Cliente findCliente(String codiceCliente, int tipoCliente) throws RecordNonPresenteException;

    public Vector<ClientePrivato> findAllClientiPrivati();
    
    public Vector<ClienteProfessionista> findAllClientiProfessionisti();

    public void updateCliente(Cliente cliente) throws RecordNonPresenteException;

    public RowSet selectClienteRS(Cliente criteria);

    public Collection selectClienteTO(Cliente criteria);
}
