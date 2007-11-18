/*
 * TempMain.java
 *
 * Created on 14 ottobre 2007, 18.28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package main;

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
        MSAccessArtistaDAO artistaDAO = (MSAccessArtistaDAO) msaccessFactory.getArtistaDAO();
        MSAccessClienteDAO clienteDAO = (MSAccessClienteDAO) msaccessFactory.getClienteDAO();
        MSAccessFatturaDAO fatturaDAO = (MSAccessFatturaDAO) msaccessFactory.getFatturaDAO();
        MSAccessOperaDAO operaDAO = (MSAccessOperaDAO) msaccessFactory.getOperaDAO();


        Artista b = artistaDAO.findArtista(1);
        System.out.println("cognome: " + b.getCognome());

        boolean ok = artistaDAO.deleteArtista(3);
        System.out.println("ok? " + ok);
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