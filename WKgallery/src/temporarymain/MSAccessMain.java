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
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import abstractlayer.Regione;
import daorules.*;
import exceptions.ArchivioNonTrovatoException;
import exceptions.BadFormatException;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.sql.Connection;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import msaccessimpl.MSAccessArtistaDAO;
import msaccessimpl.MSAccessClienteDAO;
import msaccessimpl.MSAccessDAOFactory;
import msaccessimpl.MSAccessFatturaDAO;
import msaccessimpl.MSAccessOperaDAO;
import utilities.EMail;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessMain {

    Connection connection;
    private Cliente cliLett;
    private Cliente cliLett2;
    private Cliente cliLett3;

    /** Creates a new instance of TempMain */
    public MSAccessMain() {


        MSAccessDAOFactory msaccessFactory =
                (MSAccessDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MSACCESS);
        try {
        connection = msaccessFactory.getConnection();
        } catch (ArchivioNonTrovatoException ante) {
            System.err.println(ante);
        }
        // Create DAOs
        //ArtistaDAO art = msaccessFactory.getArtistaDAO();
        MSAccessArtistaDAO artistaDAO = msaccessFactory.getArtistaDAO();
        MSAccessClienteDAO clienteDAO = msaccessFactory.getClienteDAO();
        MSAccessFatturaDAO fatturaDAO = msaccessFactory.getFatturaDAO();
        MSAccessOperaDAO operaDAO = msaccessFactory.getOperaDAO();
        Fattura f = null;
        try {
            f = fatturaDAO.findFattura(1, 2007);
            for (Opera o : f.getOpere()) {
                System.out.println(o.getCodiceOpera());
            }
        } catch (RecordNonPresenteException e) {
            System.out.println(e);
        }

    /*
    Artista bettyspaghetti =
    new Artista (4, "Spaghetti", "Betty",
    "La più bravissima di tutti");
    artistaDAO.insertArtista(bettyspaghetti);
     * */
    /*
    try {
    operaDAO.insertOpera(o1);
    System.out.println("OK o1");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
     * */

    /*
    try {
    operaDAO.insertOpera(o2);
    System.out.println("OK o2");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
     * */
//        try {
//        operaDAO.updatePrezzo(o1);
//        operaDAO.updatePrezzo(o2);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        Cliente cliente = null;
//        try {
//            cliente = clienteDAO.findCliente("PrivAM1");
//            System.out.println("OK PrivAM1");
//        } catch (RecordNonPresenteException ex) {
//            System.out.println(ex);
//        }
//
//        Vector<Opera> opere = new Vector<Opera>();
//        opere.add(o2);
//        opere.add(o1);
//        Fattura fx = new Fattura(1, 1, cliente, opere);
//        try {
//            fatturaDAO.insertFattura(fx);
//        } catch (RecordGiaPresenteException e) {
//            System.out.println(e);
//        } catch (ChiavePrimariaException e) {
//            System.out.println(e);
//        }
            /*
    Cliente cli1 = null;
    Cliente cli2 = null;
    Cliente cliB = null;
    Cliente cliS = null;
    Cliente cliA = null;
    Cliente cliNull = null;
    Vector<Cliente> v = null;
    Artista me =
    new Artista(1, "Celesti", "Marco", "Figo");
    try {
    artistaDAO.insertArtista(me);
    System.out.println("OK me");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    String mail1 = EMail.toString(EMail.toEMail("mirko@ciao.it"));
    System.out.println("----->mail1: " + mail1);
    cli2 = new Cliente("ProfBM1", "Bertoldi snc", "",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "zzz", "0216452316",
    true);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cli2);
    System.out.println("OK cli2");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    Artista meAncora =
    new Artista(1, "Light blue", "Mark", "Very figo");
    try {
    artistaDAO.insertArtista(meAncora);
    System.out.println("OK meAncora");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
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
    } catch (ChiavePrimariaException e) {
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
    } catch (ChiavePrimariaException e) {
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
    clienteDAO.findCliente("ProfBM1");
    System.out.println("OK ProfBM1");
    } catch (RecordNonPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.findCliente("ProfXX1");
    System.out.println("OK ProfXX1");
    } catch (RecordNonPresenteException e) {
    System.out.println(e);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.deleteCliente("ProfBM1");
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
    try {
    cli1 = new Cliente("PrivBM1", "Bertoldi", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cli1);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    clienteDAO.deleteCliente("PrivBM1");
    System.out.println("OK PrivBM1 ancora");
    } catch (RecordNonPresenteException e) {
    System.out.println(e);
    } catch (RecordCorrelatoException e) {
    System.out.println(e);
    }
    try {
    cliB = new Cliente("PrivBM1", "Bertoldi", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.bye.it"),
    EMail.toEMail(""), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.insertCliente(cliB);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    cliS = new Cliente("PrivSM1", "Sertoldi", "Mirko", "Via dei dopati",
    1, "Capriolo", "Brescia", Regione.Lombardia, "Italia",
    "030xxx", "030111", "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz", false);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.insertCliente(cliS);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    cliA = new Cliente("PrivAM1", "Aertoldi", "Mirko", "Via dei dopati",
    1, "Capriolo", "Brescia", Regione.Lombardia, "Italia",
    "030xxx", "030111", "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz", false);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.insertCliente(cliA);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    v = clienteDAO.findAllClienti();
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    for (Cliente a : v) {
    System.out.println(a.getCognRsoc1() + "\n");
    }
    try {
    cliNull = new Cliente("", "Bertoldi", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.bye.it"),
    EMail.toEMail(""), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.insertCliente(cliNull);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    Vector<Cliente> a = null;
    try {
    a = clienteDAO.findAllClientiPrivati();
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    System.out.println("a.size()=" + a.size());
    try {
    cliLett = new Cliente("PrivBMx", "Bertoldi", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cliLett);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    cliLett2 = new Cliente("PrivBMx3", "Bologna", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Puglia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cliLett2);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    cliLett3 = new Cliente("ProfBMx4", "BaciEAbbracci", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lazio, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz",
    true);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cliLett3);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    Vector<Cliente> lett = null;
    try {
    lett = clienteDAO.selectClientiPerStringa("B");
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    System.out.println("lett.size()=" + lett.size());
    for(Cliente x : lett) {
    System.out.println(x.getCognRsoc1());
    }
    Vector<Cliente> reg = null;
    Vector<Regione> elenco = new Vector<Regione>();
    elenco.add(Regione.Lazio);
    elenco.add(Regione.Lombardia);
    try {
    reg = clienteDAO.selectClientiPerRegione(elenco);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    System.out.println("reg.size()=" + reg.size());
    for(Cliente x : reg) {
    System.out.println(x.getCognRsoc1());
    }
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
    try {
    artistaDAO.deleteArtista(1);
    } catch (RecordNonPresenteException ex) {
    System.out.println(ex);
    } catch (Exception e) {
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
    try {
    artistaDAO.deleteArtista(1);
    } catch (RecordNonPresenteException ex) {
    System.out.println(ex);
    } catch (Exception e) {
    System.out.println(e);
    }
     */
//        } catch (RecordGiaPresenteException ex) {
//            Logger.getLogger(MSAccessMain.class.getName()).
//                    log(Level.SEVERE, null, ex);
//        } catch (ChiavePrimariaException ex) {
//            Logger.getLogger(MSAccessMain.class.getName()).
//                    log(Level.SEVERE, null, ex);
    }
    /*
    try {
    operaDAO.insertOpera(o1);
    System.out.println("OK o1");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
     * */

    /*
    try {
    operaDAO.insertOpera(o2);
    System.out.println("OK o2");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
     * */
//        try {
//        operaDAO.updatePrezzo(o1);
//        operaDAO.updatePrezzo(o2);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        Cliente cliente = null;
//        try {
//            cliente = clienteDAO.findCliente("PrivAM1");
//            System.out.println("OK PrivAM1");
//        } catch (RecordNonPresenteException ex) {
//            System.out.println(ex);
//        }
//        
//        Vector<Opera> opere = new Vector<Opera>();
//        opere.add(o2);
//        opere.add(o1);
//        Fattura fx = new Fattura(1, 1, cliente, opere);
//        try {
//            fatturaDAO.insertFattura(fx);
//        } catch (RecordGiaPresenteException e) {
//            System.out.println(e);
//        } catch (ChiavePrimariaException e) {
//            System.out.println(e);
//        }
    /*
    Cliente cli1 = null;
    Cliente cli2 = null;
    Cliente cliB = null;
    Cliente cliS = null;
    Cliente cliA = null;
    Cliente cliNull = null;
    Vector<Cliente> v = null;
    Artista me =
    new Artista(1, "Celesti", "Marco", "Figo");
    try {
    artistaDAO.insertArtista(me);
    System.out.println("OK me");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    String mail1 = EMail.toString(EMail.toEMail("mirko@ciao.it"));
    System.out.println("----->mail1: " + mail1);
    cli2 = new Cliente("ProfBM1", "Bertoldi snc", "",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "zzz", "0216452316",
    true);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cli2);
    System.out.println("OK cli2");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    Artista meAncora =
    new Artista(1, "Light blue", "Mark", "Very figo");
    try {
    artistaDAO.insertArtista(meAncora);
    System.out.println("OK meAncora");
    } catch (RecordGiaPresenteException e) {
    System.out.println(e);
    } catch (ChiavePrimariaException e) {
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
    } catch (ChiavePrimariaException e) {
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
    } catch (ChiavePrimariaException e) {
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
    clienteDAO.findCliente("ProfBM1");
    System.out.println("OK ProfBM1");
    } catch (RecordNonPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.findCliente("ProfXX1");
    System.out.println("OK ProfXX1");
    } catch (RecordNonPresenteException e) {
    System.out.println(e);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.deleteCliente("ProfBM1");
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
    try {
    cli1 = new Cliente("PrivBM1", "Bertoldi", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cli1);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    clienteDAO.deleteCliente("PrivBM1");
    System.out.println("OK PrivBM1 ancora");
    } catch (RecordNonPresenteException e) {
    System.out.println(e);
    } catch (RecordCorrelatoException e) {
    System.out.println(e);
    }
    try {
    cliB = new Cliente("PrivBM1", "Bertoldi", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.bye.it"),
    EMail.toEMail(""), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.insertCliente(cliB);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    cliS = new Cliente("PrivSM1", "Sertoldi", "Mirko", "Via dei dopati",
    1, "Capriolo", "Brescia", Regione.Lombardia, "Italia",
    "030xxx", "030111", "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz", false);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.insertCliente(cliS);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    cliA = new Cliente("PrivAM1", "Aertoldi", "Mirko", "Via dei dopati",
    1, "Capriolo", "Brescia", Regione.Lombardia, "Italia",
    "030xxx", "030111", "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz", false);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.insertCliente(cliA);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    v = clienteDAO.findAllClienti();
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    for (Cliente a : v) {
    System.out.println(a.getCognRsoc1() + "\n");
    }
    try {
    cliNull = new Cliente("", "Bertoldi", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.bye.it"),
    EMail.toEMail(""), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    try {
    clienteDAO.insertCliente(cliNull);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    Vector<Cliente> a = null;
    try {
    a = clienteDAO.findAllClientiPrivati();
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    System.out.println("a.size()=" + a.size());
    try {
    cliLett = new Cliente("PrivBMx", "Bertoldi", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lombardia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cliLett);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    cliLett2 = new Cliente("PrivBMx3", "Bologna", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Puglia, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz",
    false);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cliLett2);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    try {
    cliLett3 = new Cliente("ProfBMx4", "BaciEAbbracci", "Mirko",
    "Via dei dopati", 1, "Capriolo", "Brescia",
    Regione.Lazio, "Italia", "030xxx", "030111",
    "3397624198", "",
    EMail.toEMail("mirko@ciao.it"),
    EMail.toEMail("bertoldi@ciao.it"), "BRTxxx", "zzz",
    true);
    } catch (BadFormatException bfe) {
    System.out.println(bfe);
    }
    try {
    clienteDAO.insertCliente(cliLett3);
    } catch (RecordGiaPresenteException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    } catch (ChiavePrimariaException e) {
    System.out.println(e);
    }
    Vector<Cliente> lett = null;
    try {
    lett = clienteDAO.selectClientiPerStringa("B");
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    System.out.println("lett.size()=" + lett.size());
    for(Cliente x : lett) {
    System.out.println(x.getCognRsoc1());
    }
    Vector<Cliente> reg = null;
    Vector<Regione> elenco = new Vector<Regione>();
    elenco.add(Regione.Lazio);
    elenco.add(Regione.Lombardia);
    try {
    reg = clienteDAO.selectClientiPerRegione(elenco);
    } catch (BadFormatException ex) {
    Logger.getLogger(MSAccessMain.class.getName()).
    log(Level.SEVERE, null, ex);
    }
    System.out.println("reg.size()=" + reg.size());
    for(Cliente x : reg) {
    System.out.println(x.getCognRsoc1());
    }
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

    public static void main(String[] args) {
        new MSAccessMain();
    }
}
