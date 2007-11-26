/*
 * MSAccessClienteDAO.java
 *
 * Created on 20 ottobre 2007, 18.15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package msaccessimpl;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
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
public class MSAccessClienteDAO implements ClienteDAO{
    private Connection connection;
    
    /** Creates a new instance of MSAccessClienteDAO */
    public MSAccessClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insertCliente(Cliente cliente) {
        /*
        try {
            String codOpera = opera.getCodiceOpera();
            Artista artista = opera.getArtista();
            int codArtista = artista.getCodiceArtista();
            boolean venduto = opera.isVenduto();
            String tecnica = opera.getTecnica();
            String dimensioni = opera.getDimensioni();
            String tipo = opera.getTipo();
            String foto = opera.getFoto();
            Fattura fattura = opera.getFattura();
            int numFatt = fattura.getNumeroFattura();
            int annoFatt = fattura.getAnnoFattura();

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
}
