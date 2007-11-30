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
import exceptions.RecordCorrelatoException;
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
        MSAccessDAOFactory msaccessFactory =
                (MSAccessDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MSACCESS);
        connection = msaccessFactory.getConnection();
        // Create DAOs
        //ArtistaDAO art = msaccessFactory.getArtistaDAO();
        MSAccessArtistaDAO artistaDAO = msaccessFactory.getArtistaDAO();
        MSAccessClienteDAO clienteDAO = msaccessFactory.getClienteDAO();
        MSAccessFatturaDAO fatturaDAO = msaccessFactory.getFatturaDAO();
        MSAccessOperaDAO operaDAO = msaccessFactory.getOperaDAO();

        Artista me =
                new Artista(1, "Celesti", "Marco", "Figo");
        try {
            artistaDAO.insertArtista(me);
            System.out.println("OK me");
        } catch (RecordGiaPresenteException e) {
            System.out.println(e);
        }
        ClienteProfessionista cli2 =
                new ClienteProfessionista("ProfBM1", "Bertoldi snc",
                "Via dei dopati", 1, "Capriolo", "Brescia",
                Regione.Lombardia, "Italia", "030xxx", "030111",
                "3397624198", "", "mirko@ciao.it", "bertoldi@ciao.it",
                "0215", "zzz");
        try {
            clienteDAO.insertCliente(cli2);
            System.out.println("OK cli2");
        } catch (RecordGiaPresenteException e) {
            System.out.println(e);
        }
        Artista meAncora =
                new Artista(1, "Light blue", "Mark", "Very figo");
        try {
            artistaDAO.insertArtista(meAncora);
            System.out.println("OK meAncora");
        } catch (RecordGiaPresenteException e) {
            System.out.println(e);
        }
        Fattura f = new Fattura();
        Opera o1 =
                new Opera("1_1", me, "mista", "100x100", "unica", "", false,
                f);
        try {
            operaDAO.insertOpera(o1);
            System.out.println("OK o1");
        } catch (RecordGiaPresenteException e) {
            System.out.println(e);
        }
        Opera o1ancora =
                new Opera("1_1", me, "mista", "100x100", "unica", "", false,
                f);
        try {
            operaDAO.insertOpera(o1ancora);
            System.out.println("OK o1ancora");
        } catch (RecordGiaPresenteException e) {
            System.out.println(e);
        }
        try {
            Opera o2 = operaDAO.findOpera("1_1");
            System.out.println("OK 1_1");
        } catch (RecordNonPresenteException e) {
            System.out.println(e);
        }
        try {
            Opera oNEx = operaDAO.findOpera("1_5");
            System.out.println("OK 1_5");
        } catch (RecordNonPresenteException e) {
            System.out.println(e);
        }

        try {
            clienteDAO.findCliente("ProfBM1", 2);
            System.out.println("OK ProfBM1");
        } catch (RecordNonPresenteException e) {
            System.out.println(e);
        }

        try {
            clienteDAO.findCliente("ProfXX1", 2);
            System.out.println("OK ProfXX1");
        } catch (RecordNonPresenteException e) {
            System.out.println(e);
        }

        try {
            clienteDAO.deleteCliente("ProfBM1", 2);
            System.out.println("OK ProfBM1 ancora");
        } catch (RecordNonPresenteException e) {
            System.out.println(e);
        } catch (RecordCorrelatoException e) {
            System.out.println(e);
        }

        try {
            operaDAO.deleteOpera("1_1");
            System.out.println("OK del 1_1");
        } catch (RecordNonPresenteException e) {
            System.out.println(e);
        }

        try {
            artistaDAO.deleteArtista(1);
            System.out.println("OK del 1");
        } catch (RecordNonPresenteException ex) {
            Logger.getLogger(MSAccessMain.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (RecordCorrelatoException ex) {
            Logger.getLogger(MSAccessMain.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        ClientePrivato cli1 =
                new ClientePrivato("PrivBM1", "Bertoldi", "Mirko",
                "Via dei dopati", 1, "Capriolo", "Brescia",
                Regione.Lombardia, "Italia", "030xxx", "030111",
                "3397624198", "mirko@ciao.it", "bertoldi@ciao.it", "BRTxxx",
                "zzz");
        try {
            clienteDAO.insertCliente(cli1);
        } catch (RecordGiaPresenteException ex) {
            Logger.getLogger(MSAccessMain.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        try {
            clienteDAO.deleteCliente("PrivBM1", 1);
            System.out.println("OK PrivBM1 ancora");
        } catch (RecordNonPresenteException e) {
            System.out.println(e);
        } catch (RecordCorrelatoException e) {
            System.out.println(e);
        }

        ClientePrivato cliB =
                new ClientePrivato("PrivBM1", "Bertoldi", "Mirko",
                "Via dei dopati", 1, "Capriolo", "Brescia",
                Regione.Lombardia, "Italia", "030xxx", "030111",
                "3397624198", "mirko@ciao.it", "bertoldi@ciao.it", "BRTxxx",
                "zzz");

        try {
            clienteDAO.insertCliente(cliB);
        } catch (RecordGiaPresenteException ex) {
            Logger.getLogger(MSAccessMain.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        ClientePrivato cliS =
                new ClientePrivato("PrivSM1", "Sertoldi", "Mirko",
                "Via dei dopati", 1, "Capriolo", "Brescia",
                Regione.Lombardia, "Italia", "030xxx", "030111",
                "3397624198", "mirko@ciao.it", "bertoldi@ciao.it", "BRTxxx",
                "zzz");

        try {
            clienteDAO.insertCliente(cliS);
        } catch (RecordGiaPresenteException ex) {
            Logger.getLogger(MSAccessMain.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        ClientePrivato cliA =
                new ClientePrivato("PrivAM1", "Aertoldi", "Mirko",
                "Via dei dopati", 1, "Capriolo", "Brescia",
                Regione.Lombardia, "Italia", "030xxx", "030111",
                "3397624198", "mirko@ciao.it", "bertoldi@ciao.it", "BRTxxx",
                "zzz");

        try {
            clienteDAO.insertCliente(cliA);
        } catch (RecordGiaPresenteException ex) {
            Logger.getLogger(MSAccessMain.class.getName()).
                    log(Level.SEVERE, null, ex);
        }


        Vector<ClientePrivato> v = clienteDAO.findAllClientiPrivati();

        for (ClientePrivato a : v) {
            System.out.println(a.getCognome() + "\n");
        }

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
    try {
    artistaDAO.deleteArtista(1);
    } catch (RecordNonPresenteException ex) {
    System.out.println(ex);
    } catch (Exception e) {
    System.out.println(e);
    }
     */ /*
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
    try {
    artistaDAO.deleteArtista(1);
    } catch (RecordNonPresenteException ex) {
    System.out.println(ex);
    } catch (Exception e) {
    System.out.println(e);
    }
     */
    }

    public static void main(String[] args) {
        new MSAccessMain();
    }
}
