/*
 * FatturaDAO.java
 *
 * Created on 21 ottobre 2007, 17.33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package daorules;

import abstractlayer.Cliente;
import abstractlayer.Fattura;
import exceptions.ChiavePrimariaException;
import exceptions.RecordGiaPresenteException;
import java.util.Vector;

/**
 *
 * @author Marco Celesti
 */
public interface FatturaDAO {

    public void insertFattura(Fattura fattura) throws RecordGiaPresenteException, ChiavePrimariaException;

    public void deleteFattura(int numero_anno);

    public Fattura findFattura(int numero_anno);

    public void updateFattura(Fattura fattura);

    public Vector<Fattura> selectFatturaPerCliente(Cliente cliente);

//    public Collection selectFatturaTO(Fattura criteria);
}
