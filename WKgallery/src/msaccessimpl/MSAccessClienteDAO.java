package msaccessimpl;

import exceptions.BadFormatException;
import abstractlayer.Cliente;
import abstractlayer.Regione;
import daoabstract.ClienteDAO;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DefaultComparator;
import utilities.EMail;

/**
 * Implementazione dell'interfaccia ClienteDAO per MS Access.
 * @author Marco Celesti
 */
public class MSAccessClienteDAO implements ClienteDAO {

    private static Connection connection;
    private static MSAccessClienteDAO msAccessClienteDao;
    private DefaultComparator<Cliente> defaultComparator = new DefaultComparator<Cliente>();

    /**
     * Costruttore privato di MSAccessClienteDAO.
     */
    private MSAccessClienteDAO() {
        MSAccessClienteDAO.connection = MSAccessDAOFactory.connection;
    }

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static MSAccessClienteDAO getMSAccessClienteDAO() {
        if (msAccessClienteDao == null) {
            msAccessClienteDao = new MSAccessClienteDAO();
        }
        return msAccessClienteDao;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param cliente il cliente da inserire nel DB
     * @throws exceptions.RecordGiaPresenteException se il cliente esiste già
     * @throws exceptions.ChiavePrimariaException se non è stata definita la chiave primaria
     */
    @Override
    public void insertCliente(Cliente cliente) throws RecordGiaPresenteException,
            ChiavePrimariaException {
        int codiceCliente = cliente.getCodiceCliente();
        if (clienteExists(codiceCliente)) {
            throw new RecordGiaPresenteException("Codice cliente " + codiceCliente + " già presente in archivio");
        }
        try {
            String cognRsoc1 = cliente.getCognRsoc1();
            String nomeRsoc2 = cliente.getNomeRsoc2();
            String indirizzo = cliente.getIndirizzo();
            String nCiv = cliente.getNCiv();
            String cap = cliente.getCap();
            String citta = cliente.getCitta();
            String prov = cliente.getProvincia();
            String regione = cliente.getRegione().getNomeRegione();
            String stato = cliente.getStato();
            String tel1 = cliente.getTel1();
            String tel2 = cliente.getTel2();
            String cell1 = cliente.getCell1();
            String cell2 = cliente.getCell2();
            String mail1 = cliente.getMail1().toString();
            String mail2 = cliente.getMail2().toString();
            String cfPiva = cliente.getCfPiva();
            String note = cliente.getNote();
            boolean professionista = cliente.isProfessionista();

            PreparedStatement pstmt;
            if (codiceCliente == -1) {
                pstmt =
                        connection.prepareStatement("INSERT INTO Cliente (CognomeRsoc1, NomeRsoc2, Indirizzo, NCiv, Citta, Provincia, Regione, Stato, Telefono1, Telefono2, Cellulare1, Cellulare2, Mail1, Mail2, CFPIVA, NoteCliente, Professionista, Cap) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, cognRsoc1);
                pstmt.setString(2, nomeRsoc2);
                pstmt.setString(3, indirizzo);
                pstmt.setString(4, nCiv);
                pstmt.setString(5, citta);
                pstmt.setString(6, prov);
                pstmt.setString(7, regione);
                pstmt.setString(8, stato);
                pstmt.setString(9, tel1);
                pstmt.setString(10, tel2);
                pstmt.setString(11, cell1);
                pstmt.setString(12, cell2);
                pstmt.setString(13, mail1);
                pstmt.setString(14, mail2);
                pstmt.setString(15, cfPiva);
                pstmt.setString(16, note);
                pstmt.setBoolean(17, professionista);
                pstmt.setString(18, cap);
            } else {
                pstmt =
                        connection.prepareStatement("INSERT INTO Cliente (CodiceCliente, CognomeRsoc1, NomeRsoc2, Indirizzo, NCiv, Citta, Provincia, Regione, Stato, Telefono1, Telefono2, Cellulare1, Cellulare2, Mail1, Mail2, CFPIVA, NoteCliente, Professionista, Cap) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1, codiceCliente);
                pstmt.setString(2, cognRsoc1);
                pstmt.setString(3, nomeRsoc2);
                pstmt.setString(4, indirizzo);
                pstmt.setString(5, nCiv);
                pstmt.setString(6, citta);
                pstmt.setString(7, prov);
                pstmt.setString(8, regione);
                pstmt.setString(9, stato);
                pstmt.setString(10, tel1);
                pstmt.setString(11, tel2);
                pstmt.setString(12, cell1);
                pstmt.setString(13, cell2);
                pstmt.setString(14, mail1);
                pstmt.setString(15, mail2);
                pstmt.setString(16, cfPiva);
                pstmt.setString(17, note);
                pstmt.setBoolean(18, professionista);
                pstmt.setString(19, cap);
            }
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param codiceCliente il codice del cliente da cancellare
     * @throws exceptions.RecordNonPresenteException se il cliente non esiste
     * @throws exceptions.RecordCorrelatoException se il cliente ha record correlati
     */
    @Override
    public void deleteCliente(int codiceCliente) throws RecordNonPresenteException,
            RecordCorrelatoException {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Cliente WHERE CodiceCliente = ?");
            pstmt.setInt(1, codiceCliente);
            int count = pstmt.executeUpdate();
            pstmt.close();
            // count contiene 1 se la cancellazione è avvenuta con successo
            if (count == 0) {
                throw new RecordNonPresenteException("Codice cliente " + codiceCliente + " non presente in archivio");
            }
            makePersistent();
        } catch (SQLException ex) {
            throw new RecordCorrelatoException("Si sta tentando di cancellare un Cliente cui sono correlate Fatture");
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @throws exceptions.RecordCorrelatoException se i clienti hanno record correlati
     */
    @Override
    public void deleteAllClienti() throws RecordCorrelatoException {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Cliente");
            pstmt.executeUpdate();
            makePersistent();
        } catch (SQLException ex) {
            throw new RecordCorrelatoException("Si sta tentando di cancellare un Cliente cui sono correlate Fatture");
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param codiceCliente il clodice del cliente da ricercare
     * @return il cliente trovato
     * @throws exceptions.RecordNonPresenteException se il codice non corrisponde ad alcun cliente
     */
    @Override
    public Cliente findCliente(int codiceCliente) throws RecordNonPresenteException {
        if (!clienteExists(codiceCliente)) {
            throw new RecordNonPresenteException("Codice cliente " + codiceCliente + " non presente in archivio");
        }
        Cliente cliente = new Cliente();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE CodiceCliente = ?");
            pstmt.setInt(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setCodiceCliente(rs.getInt("CodiceCliente"));
                cliente.setCognRsoc1(rs.getString("CognomeRsoc1"));
                cliente.setNomeRsoc2(rs.getString("NomeRsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getString("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.parseEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.parseEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CFPIVA"));
                cliente.setNote(rs.getString("NoteCliente"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                cliente.setCap(rs.getString("Cap"));
            }
            rs.close();
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING,
                    null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return cliente;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param cliente il cliente da aggiornare
     * @throws exceptions.RecordNonPresenteException se il cliente non esiste nel DB
     */
    @Override
    public void updateCliente(Cliente cliente) throws RecordNonPresenteException {
        try {
            int codiceCliente = cliente.getCodiceCliente();
            if (!clienteExists(codiceCliente)) {
                throw new RecordNonPresenteException("Codice cliente " + codiceCliente + " non presente in archivio");
            }
            String cognRsoc1 = cliente.getCognRsoc1();
            String nomeRsoc2 = cliente.getNomeRsoc2();
            String indirizzo = cliente.getIndirizzo();
            String nCiv = cliente.getNCiv();
            String citta = cliente.getCitta();
            String prov = cliente.getProvincia();
            String regione = cliente.getRegione().getNomeRegione();
            String stato = cliente.getStato();
            String tel1 = cliente.getTel1();
            String tel2 = cliente.getTel2();
            String cell1 = cliente.getCell1();
            String cell2 = cliente.getCell2();
            String mail1 = cliente.getMail1().toString();
            String mail2 = cliente.getMail2().toString();
            String cfPiva = cliente.getCfPiva();
            String note = cliente.getNote();
            String cap = cliente.getCap();
            boolean professionista = cliente.isProfessionista();

            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Cliente SET CognomeRsoc1 = ?, NomeRsoc2 = ?, Indirizzo = ?, NCiv = ?, Citta = ?, Provincia = ?, Regione = ?, Stato = ?, Telefono1 = ?, Telefono2 = ?, Cellulare1 = ?, Cellulare2 = ?, Mail1 = ?, Mail2 = ?, CFPIVA = ?, NoteCliente = ?, Professionista = ?, Cap = ? WHERE CodiceCliente = ?");
            pstmt.setString(1, cognRsoc1);
            pstmt.setString(2, nomeRsoc2);
            pstmt.setString(3, indirizzo);
            pstmt.setString(4, nCiv);
            pstmt.setString(5, citta);
            pstmt.setString(6, prov);
            pstmt.setString(7, regione);
            pstmt.setString(8, stato);
            pstmt.setString(9, tel1);
            pstmt.setString(10, tel2);
            pstmt.setString(11, cell1);
            pstmt.setString(12, cell2);
            pstmt.setString(13, mail1);
            pstmt.setString(14, mail2);
            pstmt.setString(15, cfPiva);
            pstmt.setString(16, note);
            pstmt.setBoolean(17, professionista);
            pstmt.setString(18, cap);
            pstmt.setInt(19, codiceCliente);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();

        } catch (SQLException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param s la stringa di ricerca
     * @return il vettore di clienti a cui corrsipondono i criteri di ricerca
     */
    @Override
    public Vector<Cliente> selectClientiPerStringa(String s) {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE CognomeRsoc1 LIKE ?");
            String pattern = s + "%";
            pstmt.setString(1, pattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodiceCliente(rs.getInt("CodiceCliente"));
                cliente.setCognRsoc1(rs.getString("CognomeRsoc1"));
                cliente.setNomeRsoc2(rs.getString("NomeRsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getString("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.parseEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.parseEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CFPIVA"));
                cliente.setNote(rs.getString("NoteCliente"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                cliente.setCap(rs.getString("Cap"));
                v.add(cliente);
            }
            rs.close();
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING,
                    null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param regioni il vettore di regioni da specificare come criterio di ricerca
     * @return il vettore di clienti a cui corrispondono i criteri di ricerca
     */
    @Override
    public Vector<Cliente> selectClientiPerRegione(Vector<Regione> regioni) {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        for (Regione r : regioni) {
            Vector<Cliente> tempV = new Vector<Cliente>();
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("SELECT * FROM Cliente WHERE Regione = ?");
                String nomeRegione = r.getNomeRegione();
                pstmt.setString(1, nomeRegione);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    cliente = new Cliente();
                    cliente.setCodiceCliente(rs.getInt("CodiceCliente"));
                    cliente.setCognRsoc1(rs.getString("CognomeRsoc1"));
                    cliente.setNomeRsoc2(rs.getString("NomeRsoc2"));
                    cliente.setIndirizzo(rs.getString("Indirizzo"));
                    cliente.setNCiv(rs.getString("NCiv"));
                    cliente.setCitta(rs.getString("Citta"));
                    cliente.setProvincia(rs.getString("Provincia"));
                    cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                    cliente.setStato(rs.getString("Stato"));
                    cliente.setTel1(rs.getString("Telefono1"));
                    cliente.setTel2(rs.getString("Telefono2"));
                    cliente.setCell1(rs.getString("Cellulare1"));
                    cliente.setCell2(rs.getString("Cellulare2"));
                    cliente.setMail1(EMail.parseEMail(rs.getString("Mail1")));
                    cliente.setMail2(EMail.parseEMail(rs.getString("Mail2")));
                    cliente.setCfPiva(rs.getString("CFPIVA"));
                    cliente.setNote(rs.getString("NoteCliente"));
                    cliente.setProfessionista(rs.getBoolean("Professionista"));
                    cliente.setCap(rs.getString("Cap"));
                    tempV.add(cliente);
                }
                rs.close();
                pstmt.close();
            } catch (BadFormatException ex) {
                Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING,
                        null,
                        ex);
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                        null,
                        ex);
            }
            Collections.sort(tempV, defaultComparator);
            v.addAll(tempV);
        }
        return v;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @return il vettore di clienti di tipo privato
     */
    @Override
    public Vector<Cliente> findAllClientiPrivati() {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE Professionista = false");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodiceCliente(rs.getInt("CodiceCliente"));
                cliente.setCognRsoc1(rs.getString("CognomeRsoc1"));
                cliente.setNomeRsoc2(rs.getString("NomeRsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getString("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.parseEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.parseEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CFPIVA"));
                cliente.setNote(rs.getString("NoteCliente"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                cliente.setCap(rs.getString("Cap"));
                v.add(cliente);
            }
            rs.close();
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING,
                    null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @return il vettore di clienti di tipo professionista
     */
    @Override
    public Vector<Cliente> findAllClientiProfessionisti() {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE Professionista = true");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodiceCliente(rs.getInt("CodiceCliente"));
                cliente.setCognRsoc1(rs.getString("CognomeRsoc1"));
                cliente.setNomeRsoc2(rs.getString("NomeRsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getString("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.parseEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.parseEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CFPIVA"));
                cliente.setNote(rs.getString("NoteCliente"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                cliente.setCap(rs.getString("Cap"));
                v.add(cliente);
            }
            rs.close();
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING,
                    null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @return il vettore di clienti nel DB
     */
    @Override
    public Vector<Cliente> findAllClienti() {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodiceCliente(rs.getInt("CodiceCliente"));
                cliente.setCognRsoc1(rs.getString("CognomeRsoc1"));
                cliente.setNomeRsoc2(rs.getString("NomeRsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getString("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.parseEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.parseEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CFPIVA"));
                cliente.setNote(rs.getString("NoteCliente"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                cliente.setCap(rs.getString("Cap"));
                v.add(cliente);
            }
            rs.close();
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING,
                    null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Determina se il cliente con un dato codice esiste.
     * @param codiceCliente il codice del cliente
     * @return true se il cliente esiste
     */
    private boolean clienteExists(int codiceCliente) {
        int codiceReale = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT CodiceCliente FROM Cliente WHERE CodiceCliente = ?");
            pstmt.setInt(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                codiceReale = rs.getInt("CodiceCliente");
            }
            if (codiceReale != -1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Metodo "dummy" per rendere persistente l'update alla tabella. Bug di MS Access.
     */
    public void makePersistent() {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT CodiceCliente FROM Cliente");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
