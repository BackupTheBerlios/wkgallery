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

    /** Crea una nuova istanza di MSAccessArtistaDAO
     * @param connection Oggetto Connection per l'esecuzione di query
     */
    public MSAccessArtistaDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Metodo per inserire l'artista passato come parametro
     * @param artista Il nuovo record
     @return true se la cancellazione è avvenuta con successo
     *         false altrimenti
     */
    public boolean insertArtista(Artista artista) {
        try {
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
            pstmt.close();
            makePersistent();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Metodo per cancellare un artista in base al CodiceArtista
     * @param codiceArtista
     * @return true se la cancellazione è avvenuta con successo
     *         false altrimenti
     */
    public boolean deleteArtista(int codiceArtista) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM Artista WHERE CodiceArtista = ?");
            pstmt.setInt(1, codiceArtista);
            int count = pstmt.executeUpdate();
            pstmt.close();
            // count contiene 1 se la cancellazione è avvenuta con successo
            if (count > 0) {
                makePersistent();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Metodo per trovare un record in base al CodiceArtista
     * @param codiceArtista
     * @return l'istanza di <code>Artista</code> trovata oppure una non inizializzata.
     */
    public Artista findArtista(int codiceArtista) {
        Artista artista = new Artista();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Artista WHERE CodiceArtista = ?");
            pstmt.setInt(1, codiceArtista);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                artista.setCodiceArtista(rs.getInt("CodiceArtista"));
                artista.setCognome(rs.getString("Cognome"));
                artista.setNome(rs.getString("Nome"));
                artista.setNoteBiografiche(rs.getString("NoteBiografiche"));
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return artista;
    }

    /**
     * Metodo per l'update di un record
     * @param artista L'istanza aggiornata
     * @return true se l'aggiornamento è avvenuto con successo
     *         false altrimenti
     */
    public boolean updateArtista(Artista artista) {
        int codArt = artista.getCodiceArtista();
        boolean found = deleteArtista(codArt);
        if (found) {
            insertArtista(artista);
            return true;
        } else {
            return false;
        }
    }

    public RowSet selectArtistaRS(Artista criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectArtistaTO(Artista criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo "dummy" per rendere persistente l'update alla tabella. Bug di MS Access.
     */
    public void makePersistent() {
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Artista");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}