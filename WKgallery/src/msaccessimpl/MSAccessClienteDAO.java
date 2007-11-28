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
                //int regione = cli.
                String stato = cli.getStato();
                String tel1 = cli.getTel1();
                String tel2 = cli.getTel2();
                String cell = cli.getCell();
                String mail1 = cli.getMail1();
                String mail2 = cli.getMail2();
                String cf = cli.getCf();
                String note = cli.getNote();
                
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO ClientePrivato (ID, Cognome, Nome, Indirizzo, NCiv, Citta, Regione, Stato, TelefonoCasa, TelefonoUff, Cellulare, Mail1, Mail2, CF, Note) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, codCli);
                pstmt.setString(2, cognome);
                pstmt.setString(3, nome);
                pstmt.setString(4, indirizzo);
                pstmt.setInt(5, nCiv);
                pstmt.setString(6, citta);
                pstmt.setInt(7, 9); //dummy----------------------------------------------
                pstmt.setString(8, stato);
                pstmt.setString(9, tel1);
                pstmt.setString(10, tel2);
                pstmt.setString(11, cell);
                pstmt.setString(12, mail1);
                pstmt.setString(13, mail2);
                pstmt.setString(14, cf);
                pstmt.setString(15, note);
                pstmt.executeUpdate();
                pstmt.close();
                makePersistent();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (cliente instanceof ClienteProfessionista) {
            ClienteProfessionista cli = (ClienteProfessionista) cliente;
            String codCli = cli.getCodiceCliente();
            String ragSoc = cli.getRagioneSociale();
            String indirizzo = cli.getIndirizzo();
            int nCiv = cli.getNCiv();
            String citta = cli.getCitta();
            //int regione = cli.
            String stato = cli.getStato();
            String tel1 = cli.getTel1();
            String tel2 = cli.getTel2();
            String cell1 = cli.getCell1();
            String cell2 = cli.getCell2();
            String mail1 = cli.getMail1();
            String mail2 = cli.getMail2();
            String pIva = cli.getPIva();
            String note = cli.getNote();
        } else {
            return false;
        }
        /*
        try {
        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Opera (CodiceOpera, Artista, Tecnica, Dimensioni, Tipo, Foto, NumeroFattura, AnnoFattura) values (?,?,?,?,?,?,?,?)");
        pstmt.setString(1, codOpera);
        pstmt.setInt(2, codArtista);
        pstmt.setString(3, tecnica);
        pstmt.setString(4, dimensioni);
        pstmt.setString(5, tipo);
        pstmt.setString(6, foto);
        pstmt.setInt(7, numFatt);
        pstmt.setInt(8, annoFatt);
        pstmt.executeUpdate();
        pstmt.close();
        makePersistent();
        return true;
        } catch (SQLException ex) {
        Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
         * */
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
            PreparedStatement pstmt = connection.prepareStatement("SELECT ID FROM Cliente");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
