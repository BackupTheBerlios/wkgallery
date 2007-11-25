/*
 * MSAccessOperaDAO.java
 *
 * Created on 20 ottobre 2007, 17.28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package msaccessimpl;

import abstractlayer.Artista;
import abstractlayer.Opera;
import daorules.OperaDAO;
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
public class MSAccessOperaDAO implements OperaDAO {

    private Connection connection;

    /** Creates a new instance of MSAccessOperaDAO */
    public MSAccessOperaDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insertOpera(Opera opera) {
        try {
            String codOpera = opera.getCodiceOpera();
            Artista artista = opera.getArtista();
            int codArtista = artista.getCodiceArtista();
            boolean venduto = opera.isVenduto();
            String tecnica = opera.getTecnica();
            String dimensioni = opera.getDimensioni();
            String tipo = opera.getTipo();
            String foto = opera.getFoto();

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Opera (CodiceOpera, Artista, Tecnica, Dimensioni, Tipo, Foto) values (?,?,?,?,?,?)");
            pstmt.setString(1, codOpera);
            pstmt.setInt(2, codArtista);
            pstmt.setString(3, tecnica);
            pstmt.setString(4, dimensioni);
            pstmt.setString(5, tipo);
            pstmt.setString(6, foto);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteOpera(int codiceOpera) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Opera findOpera(int codiceOpera) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateOpera(Opera opera) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowSet selectOperaRS(Opera criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectOperaTO(Opera criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo "dummy" per rendere persistente l'update alla tabella. Bug di MS Access.
     */
    public void makePersistent() {
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT CodiceOpera FROM Opera");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

