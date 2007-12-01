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
import exceptions.BadFormatException;
import exceptions.ChiavePrimariaException;
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
    public void insertCliente(Cliente cliente) throws RecordGiaPresenteException,
            ChiavePrimariaException;

    public void deleteCliente(String codiceCliente) throws RecordNonPresenteException,
            RecordCorrelatoException;

    public Cliente findCliente(String codiceCliente) throws RecordNonPresenteException,
            BadFormatException;

    public Vector<Cliente> findAllClienti() throws BadFormatException;

    public void updateCliente(Cliente cliente) throws RecordNonPresenteException;

    public RowSet selectClienteRS(Cliente criteria);

    public Collection selectClienteTO(Cliente criteria);
}
