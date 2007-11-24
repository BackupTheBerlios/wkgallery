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


        Artista b = artistaDAO.findArtista(1);
        System.out.println("cognome: " + b.getCognome());
        /*
        boolean ok = artistaDAO.deleteArtista(3);
        System.out.println("ok? " + ok);
        */
        Artista a = new Artista(1,"Cernuschi","Betty","zzz");
        artistaDAO.updateArtista(a);
        
        
    // select all Artistas in the same city
        //Artista criteria = new Artista();
        //criteria.setCity("New York");
        //Collection artistaList = artistaDAO.selectArtistaTO(criteria);
// returns ArtistasList - collection of Artista
// Transfer Objects. iterate through this collection to
// get values.
    }

    public static void main(String[] args) {
        MSAccessMain main = new MSAccessMain();

    }
}