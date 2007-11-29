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
import abstractlayer.ClienteProfessionista;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import abstractlayer.Regione;
import daorules.*;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            MSAccessDAOFactory msaccessFactory =
                    (MSAccessDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MSACCESS);
            connection = msaccessFactory.getConnection();
            // Create DAOs
            //ArtistaDAO art = msaccessFactory.getArtistaDAO();
            MSAccessArtistaDAO artistaDAO = msaccessFactory.getArtistaDAO();
            MSAccessClienteDAO clienteDAO = msaccessFactory.getClienteDAO();
            MSAccessFatturaDAO fatturaDAO = msaccessFactory.getFatturaDAO();
            MSAccessOperaDAO operaDAO = msaccessFactory.getOperaDAO();
/*
            Artista me = new Artista(1, "Celesti", "Marco", "Figo");
            try {
            artistaDAO.insertArtista(me);
            } catch (RecordGiaPresenteException e) {
            System.out.println(e);
            }
             */
/*
            Fattura f = new Fattura();
            
            boolean operaInserita = operaDAO.insertOpera(o1);
            System.out.println("opera inserita: " + operaInserita);
            ClientePrivato cli1 = new ClientePrivato("PvBM1", "Bertoldi", "Mirko",
            "Via dei dopati", 1, "Capriolo", "Brescia", Regione.Lombardia,
            "Italia", "030xxx", "030111", "3397624198", "mirko@ciao.it",
            "bertoldi@ciao.it", "BRTxxx", "zzz");
            boolean inseritoP = clienteDAO.insertCliente(cli1);
            System.out.println("clientePv inserito: " + inseritoP);
            ClienteProfessionista cli2 = new ClienteProfessionista("PrBM1",
            "Bertoldi snc", "Via dei dopati", 1, "Capriolo", "Brescia",
            Regione.Lombardia, "Italia", "030xxx", "030111", "3397624198",
            "", "mirko@ciao.it", "bertoldi@ciao.it", "0215", "zzz");
            boolean inseritoPr = clienteDAO.insertCliente(cli2);
            System.out.println("clientePr inserito: " + inseritoPr);
            Artista meAncora = new Artista(1, "Light blue", "Mark", "Very figo");
            try {
            artistaDAO.insertArtista(meAncora);
            } catch (Exception e) {
            System.out.println(e);
            }
            System.out.println("see ya");
            artistaDAO.findArtista(5);
            System.out.println("see ya");
             * */
        try {
            artistaDAO.deleteArtista(1);
        } catch (RecordNonPresenteException ex) {
            System.out.println(ex);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        MSAccessMain main = new MSAccessMain();
    }
}
