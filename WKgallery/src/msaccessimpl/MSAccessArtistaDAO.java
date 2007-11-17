/*
 * MSAccessArtistaDAO.java
 *
 * Created on 14 ottobre 2007, 18.22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package msaccessimpl;

import abstractlayer.Artista;
import daorules.ArtistaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessArtistaDAO implements ArtistaDAO {

    private Connection connection;
    private final String insertinto = "INSERT INTO Artista (CodiceArtista, Cognome, Nome, Note_biografinche) ";

    /** Creates a new instance of MSAccessArtistaDAO
     * @param connection Oggetto Connection per l'esecuzione di query
     */
    public MSAccessArtistaDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertArtista(Artista artista) {
        try {
            System.out.println("Inserting...");

            int codArt = artista.getCodiceArtista();
            String cognome = artista.getCognome();
            String nome = artista.getNome();
            String noteBio = artista.getNoteBiografiche();
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Artista (CodiceArtista, Cognome, Nome, NoteBiografiche) values (?,?,?,?)");
            pstmt.setInt(1, codArt);
            pstmt.setString(2, cognome);
            pstmt.setString(3, nome);
            pstmt.setString(4, noteBio);
            pstmt.executeUpdate();
            //System.out.println("Inserted... ok? " + ok);

            return 0;
        //throw new UnsupportedOperationException("Not supported yet.");
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    //throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deleteArtista(int codiceArtista) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Artista findArtista(int codiceArtista) {
        Artista artista = new Artista();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * from Artista WHERE CodiceArtista = ?");
            pstmt.setInt(1, codiceArtista);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                artista.setCodiceArtista(rs.getInt("CodiceArtista"));
                artista.setCognome(rs.getString("Cognome"));
                artista.setNome(rs.getString("Nome"));
                artista.setNoteBiografiche(rs.getString("NoteBiografiche"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return artista;
    }

    public boolean updateArtista(Artista artista) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowSet selectArtistaRS(Artista criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectArtistaTO(Artista criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}