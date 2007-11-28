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
import abstractlayer.ClientePrivato;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import abstractlayer.Regione;
import daorules.*;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Vector;
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
        
        Artista me = new Artista(1,"Celesti","Marco","Figo");
        boolean artistaInserito = artistaDAO.insertArtista(me);
        System.out.println("artista inserito: " + artistaInserito);
        
        Fattura f = new Fattura();
        Opera o1 = new Opera("MC1", artistaDAO.findArtista(1), "olio", "100x80", "opera unica", "NP", false, f);
        boolean operaInserita = operaDAO.insertOpera(o1);
        System.out.println("opera inserita: " + operaInserita);
    
       ClientePrivato cli = new ClientePrivato("BS1","Bertoldi","Mirko","Via dei dopati",1,"Capriolo","Brescia",Regione.Lombardia,"Italia","030xxx","030111","3397624198","mirko@ciao.it","bertoldi@ciao.it","BRTxxx","zzz");
       boolean inserito = clienteDAO.insertCliente(cli);
       System.out.println("cliente inserito: " + inserito);
    }

    public static void main(String[] args) {
        MSAccessMain main = new MSAccessMain();
    }
}
