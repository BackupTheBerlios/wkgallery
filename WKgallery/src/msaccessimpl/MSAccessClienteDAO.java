/*
 * MSAccessClienteDAO.java
 *
 * Created on 20 ottobre 2007, 18.15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package msaccessimpl;

import abstractlayer.Cliente;
import abstractlayer.ClientePrivato;
import abstractlayer.ClienteProfessionista;
import abstractlayer.Regione;
import daorules.ClienteDAO;
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

/**
 *
 * @author Marco Celesti
 */
public class MSAccessClienteDAO implements ClienteDAO {

    private Connection connection;
    final private int PRIVATO = 1;
    final private int PROFESSIONISTA = 2;

    /** Creates a new instance of MSAccessClienteDAO */
    public MSAccessClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertCliente(Cliente cliente) throws RecordGiaPresenteException {
        if (cliente instanceof ClientePrivato) {
            try {
                ClientePrivato cli = (ClientePrivato) cliente;
                String codCli = cli.getCodiceCliente();
                if (clienteExists(codCli, PRIVATO)) {
                    throw new RecordGiaPresenteException("ID Cliente Privato già presente in archivio");
                }
                String cognome = cli.getCognome();
                String nome = cli.getNome();
                String indirizzo = cli.getIndirizzo();
                int nCiv = cli.getNCiv();
                String citta = cli.getCitta();
                String prov = cli.getProvincia();
                String regione = cli.getRegione().getNomeRegione();
                String stato = cli.getStato();
                String tel1 = cli.getTel1();
                String tel2 = cli.getTel2();
                String cell = cli.getCell();
                String mail1 = cli.getMail1();
                String mail2 = cli.getMail2();
                String cf = cli.getCf();
                String note = cli.getNote();

                PreparedStatement pstmt =
                        connection.prepareStatement("INSERT INTO ClientePrivato values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, codCli);
                pstmt.setString(2, cognome);
                pstmt.setString(3, nome);
                pstmt.setString(4, indirizzo);
                pstmt.setInt(5, nCiv);
                pstmt.setString(6, citta);
                pstmt.setString(7, prov);
                pstmt.setString(8, regione);
                pstmt.setString(9, stato);
                pstmt.setString(10, tel1);
                pstmt.setString(11, tel2);
                pstmt.setString(12, cell);
                pstmt.setString(13, mail1);
                pstmt.setString(14, mail2);
                pstmt.setString(15, cf);
                pstmt.setString(16, note);
                System.out.println(pstmt);
                pstmt.executeUpdate();
                pstmt.close();
                makePersistent();
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        } else if (cliente instanceof ClienteProfessionista) {
            try {
                ClienteProfessionista cli = (ClienteProfessionista) cliente;
                String codCli = cli.getCodiceCliente();
                if (clienteExists(codCli, PROFESSIONISTA)) {
                    throw new RecordGiaPresenteException("ID Cliente Professionista già presente in archivio");
                }
                String ragSoc = cli.getRagioneSociale();
                String indirizzo = cli.getIndirizzo();
                int nCiv = cli.getNCiv();
                String citta = cli.getCitta();
                String prov = cli.getProvincia();
                String regione = cli.getRegione().getNomeRegione();
                String stato = cli.getStato();
                String tel1 = cli.getTel1();
                String tel2 = cli.getTel2();
                String cell1 = cli.getCell1();
                String cell2 = cli.getCell2();
                String mail1 = cli.getMail1();
                String mail2 = cli.getMail2();
                String pIva = cli.getPIva();
                String note = cli.getNote();
                PreparedStatement pstmt =
                        connection.prepareStatement("INSERT INTO ClienteProfessionista values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, codCli);
                pstmt.setString(2, ragSoc);
                pstmt.setString(3, pIva);
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
                pstmt.setString(16, note);
                pstmt.executeUpdate();
                pstmt.close();
                makePersistent();
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

    public void deleteCliente(String codiceCliente, int tipoCliente) throws RecordNonPresenteException,
            RecordCorrelatoException {
        if (tipoCliente == PRIVATO) {
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("DELETE FROM ClientePrivato WHERE ID = ?");
                pstmt.setString(1, codiceCliente);
                int count = pstmt.executeUpdate();
                pstmt.close();
                // count contiene 1 se la cancellazione è avvenuta con successo
                if (count == 0) {
                    throw new RecordNonPresenteException("ID Cliente Privato non presente in archivio");
                }
                makePersistent();
            } catch (SQLException ex) {
                throw new RecordCorrelatoException("Si sta tentando di cancellare un Cliente cui sono correlate fatture");
            }
        } else if (tipoCliente == PROFESSIONISTA) {
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("DELETE FROM ClienteProfessionista WHERE ID = ?");
                pstmt.setString(1, codiceCliente);
                int count = pstmt.executeUpdate();
                pstmt.close();
                // count contiene 1 se la cancellazione è avvenuta con successo
                if (count == 0) {
                    throw new RecordNonPresenteException("ID Cliente Professionista non presente in archivio");
                }
                makePersistent();
            } catch (SQLException ex) {
                throw new RecordCorrelatoException("Si sta tentando di cancellare un Cliente cui sono correlate fatture");
            }
        } else {
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("DELETE FROM ClientePrivato WHERE ID = ?");
                pstmt.setString(1, codiceCliente);
                int count = pstmt.executeUpdate();
                pstmt.close();
                // count contiene 1 se la cancellazione è avvenuta con successo
                if (count == 0) {
                    try {
                        pstmt.clearParameters();
                        pstmt =
                                connection.prepareStatement("DELETE FROM ClienteProfessionista WHERE ID = ?");
                        pstmt.setString(1, codiceCliente);
                        count = pstmt.executeUpdate();
                        pstmt.close();
                        // count contiene 1 se la cancellazione è avvenuta con successo
                        if (count == 0) {
                            throw new RecordNonPresenteException("ID Cliente non presente in archivio");
                        }
                        makePersistent();
                    } catch (SQLException ex) {
                        throw new RecordCorrelatoException("Si sta tentando di cancellare un Cliente cui sono correlate fatture");
                    }
                }
                makePersistent();
            } catch (SQLException ex) {
                throw new RecordCorrelatoException("Si sta tentando di cancellare un Cliente cui sono correlate fatture");
            }
        }
    }

    public Cliente findCliente(String codiceCliente, int tipoCliente) throws RecordNonPresenteException {
        if (!clienteExists(codiceCliente, tipoCliente)) {
            throw new RecordNonPresenteException("ID Cliente non presente in archivio");
        }
        if (tipoCliente == PRIVATO) {
            ClientePrivato cliente = new ClientePrivato();
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("SELECT * FROM ClientePrivato WHERE ID = ?");
                pstmt.setString(1, codiceCliente);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    cliente.setCodiceCliente(rs.getString("ID"));
                    cliente.setCognome(rs.getString("Cognome"));
                    cliente.setNome(rs.getString("Nome"));
                    cliente.setIndirizzo(rs.getString("Indirizzo"));
                    cliente.setNCiv(rs.getInt("NCiv"));
                    cliente.setCitta(rs.getString("Citta"));
                    cliente.setProvincia(rs.getString("Provincia"));
                    cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                    cliente.setStato(rs.getString("Stato"));
                    cliente.setTel1(rs.getString("TelefonoCasa"));
                    cliente.setTel2(rs.getString("TelefonoUff"));
                    cliente.setCell(rs.getString("Cellulare"));
                    cliente.setMail1(rs.getString("Mail1"));
                    cliente.setMail2(rs.getString("Mail2"));
                    cliente.setCf(rs.getString("CF"));
                    cliente.setNote(rs.getString("Note"));
                }
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            return cliente;
        } else if (tipoCliente == PROFESSIONISTA) {
            ClienteProfessionista cliente = new ClienteProfessionista();
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("SELECT * FROM ClienteProfessionista WHERE ID = ?");
                pstmt.setString(1, codiceCliente);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    cliente.setCodiceCliente(rs.getString("ID"));
                    cliente.setRagioneSociale(rs.getString("RagSoc"));
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
                    cliente.setMail1(rs.getString("Mail1"));
                    cliente.setMail2(rs.getString("Mail2"));
                    cliente.setPIva(rs.getString("PIVA"));
                    cliente.setNote(rs.getString("Note"));
                }
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            return cliente;
        } else {
            return null; //TODO sistemare(?)
        }
    }

    public void updateCliente(Cliente cliente) throws RecordNonPresenteException {
        if (cliente instanceof ClientePrivato) {
            ClientePrivato cliPri = (ClientePrivato) cliente;
            String codCli = cliPri.getCodiceCliente();
        //TODO concludere
        }
    }

    public RowSet selectClienteRS(Cliente criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectClienteTO(Cliente criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vector<ClientePrivato> findAllClientiPrivati() {
        ClientePrivato cliente;
        Vector<ClientePrivato> v = new Vector<ClientePrivato>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM ClientePrivato");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new ClientePrivato();
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setCognome(rs.getString("Cognome"));
                cliente.setNome(rs.getString("Nome"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("TelefonoCasa"));
                cliente.setTel2(rs.getString("TelefonoUff"));
                cliente.setCell(rs.getString("Cellulare"));
                cliente.setMail1(rs.getString("Mail1"));
                cliente.setMail2(rs.getString("Mail2"));
                cliente.setCf(rs.getString("CF"));
                cliente.setNote(rs.getString("Note"));
                v.add(cliente);
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, new ClienteComparator());
        return v;
    }

    public Vector<ClienteProfessionista> findAllClientiProfessionisti() {
        ClienteProfessionista cliente;
        Vector<ClienteProfessionista> v = new Vector<ClienteProfessionista>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM ClienteProfessionista");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente = new ClienteProfessionista();
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setRagioneSociale(rs.getString("RagSoc"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("TelefonoCasa"));
                cliente.setTel2(rs.getString("TelefonoUff"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(rs.getString("Mail1"));
                cliente.setMail2(rs.getString("Mail2"));
                cliente.setPIva(rs.getString("PIVA"));
                cliente.setNote(rs.getString("Note"));
                v.add(cliente);
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, new ClienteComparator());
        return v;
    }

    private boolean clienteExists(String codiceCliente, int tipoCliente) {
        String codiceReale = "";
        if (tipoCliente == PRIVATO) {
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("SELECT ID FROM ClientePrivato WHERE ID = ?");
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
        } else if (tipoCliente == PROFESSIONISTA) {
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("SELECT ID FROM ClienteProfessionista WHERE ID = ?");
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
        } else {
            return false;
        }
    }

    /**
     * Metodo "dummy" per rendere persistente l'update alla tabella. Bug di MS Access.
     */
    public void makePersistent() {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT ID FROM ClientePrivato");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
