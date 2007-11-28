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
import daorules.ClienteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;

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

    public boolean insertCliente(Cliente cliente) {
        if (cliente instanceof ClientePrivato) {
            try {
                ClientePrivato cli = (ClientePrivato) cliente;
                String codCli = cli.getCodiceCliente();
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
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        } else if (cliente instanceof ClienteProfessionista) {
            try {
                ClienteProfessionista cli = (ClienteProfessionista) cliente;
                String codCli = cli.getCodiceCliente();
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
                        connection.prepareStatement("INSERT INTO ClientePrivato values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
                System.out.println(pstmt);
                pstmt.executeUpdate();
                pstmt.close();
                makePersistent();
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean deleteCliente(int codiceCliente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Cliente findCliente(int codiceCliente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateCliente(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowSet selectClienteRS(Cliente criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectClienteTO(Cliente criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
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
