/*
 * OperaDAO.java
 *
 * Created on 20 ottobre 2007, 17.22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package daorules;

import abstractlayer.Artista;
import abstractlayer.Opera;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.util.Vector;

/**
 * L'interfaccia di riferimento per l'implementazione delle classi che si occupano di 
 * interagire direttamente con l'archivio dei dati dell'entità Opera.
 * 
 * @author Marco Celesti
 */
public interface OperaDAO {

    public void insertOpera(Opera opera) throws RecordGiaPresenteException,
            ChiavePrimariaException;

    public void deleteOpera(String codiceOpera)
            throws RecordNonPresenteException, RecordCorrelatoException;

    public Opera findOpera(String codiceOpera) throws RecordNonPresenteException;

    public Vector<Opera> findAllOpere();
    
    public void updateOpera(Opera opera) throws RecordNonPresenteException;

    public Vector<Opera> selectOperaPerArtista(Artista artista);
}
