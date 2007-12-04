/*
 * MSAccessClienteDAO.java
 *
 * Created on 20 ottobre 2007, 18.15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package msaccessimpl;

import exceptions.BadFormatException;
import utilities.ClienteComparator;
import abstractlayer.Cliente;
import abstractlayer.Regione;
import daorules.ClienteDAO;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;
import utilities.EMail;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessClienteDAO implements ClienteDAO {

    private Connection connection;

    /** Creates a new instance of MSAccessClienteDAO */
    public MSAccessClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertCliente(Cliente cliente) throws RecordGiaPresenteException,
            ChiavePrimariaException {
        try {
            String codCliente = cliente.getCodiceCliente();
            if (codCliente.isEmpty()) {
                throw new ChiavePrimariaException("CodiceCliente non può contenere una stringa di lunghezza zero.");
            }
            if (clienteExists(codCliente)) {
                throw new RecordGiaPresenteException("CodiceCliente già presente in archivio");
            }
            String cognRsoc1 = cliente.getCognRsoc1();
            String nomeRsoc2 = cliente.getNomeRsoc2();
            String indirizzo = cliente.getIndirizzo();
            int nCiv = cliente.getNCiv();
            String citta = cliente.getCitta();
            String prov = cliente.getProvincia();
            String regione = cliente.getRegione().getNomeRegione();
            String stato = cliente.getStato();
            String tel1 = cliente.getTel1();
            String tel2 = cliente.getTel2();
            String cell1 = cliente.getCell1();
            String cell2 = cliente.getCell2();
            String mail1 = EMail.toString(cliente.getMail1());
            String mail2 = EMail.toString(cliente.getMail2());
            String cfPiva = cliente.getCfPiva();
            String note = cliente.getNote();
            boolean professionista = cliente.isProfessionista();

            PreparedStatement pstmt =
                    connection.prepareStatement("INSERT INTO Cliente values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, codCliente);
            pstmt.setString(2, cognRsoc1);
            pstmt.setString(3, nomeRsoc2);
            pstmt.setString(4, indirizzo);
            pstmt.setInt(5, nCiv);
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
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    public void deleteCliente(String codiceCliente) throws RecordNonPresenteException,
            RecordCorrelatoException {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Cliente WHERE ID = ?");
            pstmt.setString(1, codiceCliente);
            int count = pstmt.executeUpdate();
            pstmt.close();
            // count contiene 1 se la cancellazione è avvenuta con successo
            if (count == 0) {
                throw new RecordNonPresenteException("CodiceCliente non presente in archivio");
            }
            makePersistent();
        } catch (SQLException ex) {
            throw new RecordCorrelatoException("Si sta tentando di cancellare un Cliente cui sono correlate fatture");
        }

    }

    public Cliente findCliente(String codiceCliente) throws RecordNonPresenteException {
        if (!clienteExists(codiceCliente)) {
            throw new RecordNonPresenteException("CodiceCliente non presente in archivio");
        }
        Cliente cliente = new Cliente();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE ID = ?");
            pstmt.setString(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setCognRsoc1(rs.getString("Cognome_Rsoc1"));
                cliente.setNomeRsoc2(rs.getString("Nome_Rsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.toEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.toEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CF_PIVA"));
                cliente.setNote(rs.getString("Note"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
            }
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return cliente;
    }

    public void updateCliente(Cliente cliente) throws RecordNonPresenteException {
        try {
            String codCli = cliente.getCodiceCliente();
            if (!clienteExists(codCli)) {
                throw new RecordNonPresenteException("CodiceCliente non presente in archivio");
            }
            String cognRsoc1 = cliente.getCognRsoc1();
            String nomeRsoc2 = cliente.getNomeRsoc2();
            String indirizzo = cliente.getIndirizzo();
            int nCiv = cliente.getNCiv();
            String citta = cliente.getCitta();
            String prov = cliente.getProvincia();
            String regione = cliente.getRegione().getNomeRegione();
            String stato = cliente.getStato();
            String tel1 = cliente.getTel1();
            String tel2 = cliente.getTel2();
            String cell1 = cliente.getCell1();
            String cell2 = cliente.getCell2();
            String mail1 = EMail.toString(cliente.getMail1());
            String mail2 = EMail.toString(cliente.getMail2());
            String cfPiva = cliente.getCfPiva();
            String note = cliente.getNote();
            boolean professionista = cliente.isProfessionista();

            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Cliente SET Cognome_Rsoc1 = ?, Nome_Rsoc2 = ?, Dimensioni = ?, Indirizzo = ?, NCiv = ?, Citta = ?, Provincia = ?, Regione = ?, Stato = ?, Telefono1 = ?, Telefono2 = ?, Cellulare1 = ?, Cellulare2 = ?, Cellulare1 = ?, Mail1 = ?, Mail2 = ?, CF_PIVA = ?, Note = ?, Professionista = ? WHERE ID = ?");
            pstmt.setString(18, codCli);
            pstmt.setString(1, cognRsoc1);
            pstmt.setString(2, nomeRsoc2);
            pstmt.setString(3, indirizzo);
            pstmt.setInt(4, nCiv);
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
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();

        } catch (SQLException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public Vector<Cliente> selectClientiPerStringa(String s) {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE Cognome_Rsoc1 LIKE ?");
            String pattern = s + "%";
            pstmt.setString(1, pattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setCognRsoc1(rs.getString("Cognome_Rsoc1"));
                cliente.setNomeRsoc2(rs.getString("Nome_Rsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.toEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.toEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CF_PIVA"));
                cliente.setNote(rs.getString("Note"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                v.add(cliente);
            }
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, new ClienteComparator());
        return v;
    }

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
                    cliente.setCodiceCliente(rs.getString("ID"));
                    cliente.setCognRsoc1(rs.getString("Cognome_Rsoc1"));
                    cliente.setNomeRsoc2(rs.getString("Nome_Rsoc2"));
                    cliente.setIndirizzo(rs.getString("Indirizzo"));
                    cliente.setNCiv(rs.getInt("NCiv"));
                    cliente.setCitta(rs.getString("Citta"));
                    cliente.setProvincia(rs.getString("Provincia"));
                    cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                    cliente.setStato(rs.getString("Stato"));
                    cliente.setTel1(rs.getString("Telefono1"));
                    cliente.setTel2(rs.getString("Telefono2"));
                    cliente.setCell1(rs.getString("Cellulare1"));
                    cliente.setCell2(rs.getString("Cellulare2"));
                    cliente.setMail1(EMail.toEMail(rs.getString("Mail1")));
                    cliente.setMail2(EMail.toEMail(rs.getString("Mail2")));
                    cliente.setCfPiva(rs.getString("CF_PIVA"));
                    cliente.setNote(rs.getString("Note"));
                    cliente.setProfessionista(rs.getBoolean("Professionista"));
                    tempV.add(cliente);
                }
                pstmt.close();
            } catch (BadFormatException ex) {
                Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            Collections.sort(tempV, new ClienteComparator());
            v.addAll(tempV);
        }
        return v;
    }

    public Vector<Cliente> findAllClientiPrivati() {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE Professionista = false");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setCognRsoc1(rs.getString("Cognome_Rsoc1"));
                cliente.setNomeRsoc2(rs.getString("Nome_Rsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.toEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.toEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CF_PIVA"));
                cliente.setNote(rs.getString("Note"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                v.add(cliente);
            }
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, new ClienteComparator());
        return v;
    }

    public Vector<Cliente> findAllClientiProfessionisti() {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE Professionista = true");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setCognRsoc1(rs.getString("Cognome_Rsoc1"));
                cliente.setNomeRsoc2(rs.getString("Nome_Rsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.toEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.toEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CF_PIVA"));
                cliente.setNote(rs.getString("Note"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                v.add(cliente);
            }
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, new ClienteComparator());
        return v;
    }

    public Vector<Cliente> findAllClienti() {
        Cliente cliente;
        Vector<Cliente> v = new Vector<Cliente>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setCognRsoc1(rs.getString("Cognome_Rsoc1"));
                cliente.setNomeRsoc2(rs.getString("Nome_Rsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.toEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.toEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CF_PIVA"));
                cliente.setNote(rs.getString("Note"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                v.add(cliente);
            }
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, new ClienteComparator());
        return v;
    }
    
    public Cliente staticFindCliente(String codiceCliente, Connection conn) throws RecordNonPresenteException {
        if (!clienteExists(codiceCliente)) {
            throw new RecordNonPresenteException("CodiceCliente non presente in archivio");
        }
        Cliente cliente = new Cliente();
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement("SELECT * FROM Cliente WHERE ID = ?");
            pstmt.setString(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setCognRsoc1(rs.getString("Cognome_Rsoc1"));
                cliente.setNomeRsoc2(rs.getString("Nome_Rsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.toEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.toEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CF_PIVA"));
                cliente.setNote(rs.getString("Note"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
            }
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return cliente;

    }

    private boolean clienteExists(String codiceCliente) {
        String codiceReale = "";
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT ID FROM Cliente WHERE ID = ?");
            pstmt.setString(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                codiceReale = rs.getString("ID");
                System.out.println("CodiceCliente: " + codiceReale);
            }
            if (!codiceReale.isEmpty()) {
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
                    connection.prepareStatement("SELECT ID FROM Cliente");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
