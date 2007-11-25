/*
 * TempMain.java
 *
 * Created on 14 ottobre 2007, 18.28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package temporarymain;

import abstractlayer.Artista;
import abstractlayer.Opera;
import daorules.*;
import java.sql.Connection;
import msaccessimpl.MSAccessArtistaDAO;
import msaccessimpl.MSAccessClienteDAO;
import msaccessimpl.MSAccessDAOFactory;
import msaccessimpl.MSAccessFatturaDAO;
import msaccessimpl.MSAccessOperaDAO;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessMain {

    Connection connection;

    /** Creates a new instance of TempMain */
    public MSAccessMain() {
        // create the required DAO Factory
        MSAccessDAOFactory msaccessFactory = (MSAccessDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MSACCESS);
        connection = msaccessFactory.getConnection();
        // Create DAOs
        //ArtistaDAO art = msaccessFactory.getArtistaDAO();
        MSAccessArtistaDAO artistaDAO = msaccessFactory.getArtistaDAO();
        MSAccessClienteDAO clienteDAO = msaccessFactory.getClienteDAO();
        MSAccessFatturaDAO fatturaDAO = msaccessFactory.getFatturaDAO();
        MSAccessOperaDAO operaDAO = msaccessFactory.getOperaDAO();
        
        Artista me = artistaDAO.findArtista(2);
        Opera o1 = new Opera("MC1", me, "mista", "120x120", "opera unica", "NP");
        boolean inserito = operaDAO.insertOpera(o1);
        System.out.println("opera inserita: " + inserito);
    }

    public static void main(String[] args) {
        MSAccessMain main = new MSAccessMain();
    }
}