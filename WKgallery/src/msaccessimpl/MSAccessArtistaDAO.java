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
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessArtistaDAO implements ArtistaDAO {

    private Connection connection;

    /** Crea una nuova istanza di MSAccessArtistaDAO
     * @param connection l'oggetto Connection per l'esecuzione di query
     */
    public MSAccessArtistaDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertArtista(Artista artista) throws RecordGiaPresenteException,
            ChiavePrimariaException {
        int codArt = artista.getCodiceArtista();
        if (codArt <= 0) {
            throw new ChiavePrimariaException("CodiceArtista deve essere positivo non nullo.");
        }
        if (artistaExists(codArt)) {
            throw new RecordGiaPresenteException("CodiceArtista già presente in archivio");
        }
        String cognome = artista.getCognome();
        String nome = artista.getNome();
        String noteBio = artista.getNoteBiografiche();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("INSERT INTO Artista values (?,?,?,?)");
            pstmt.setInt(1, codArt);
            pstmt.setString(2, cognome);
            pstmt.setString(3, nome);
            pstmt.setString(4, noteBio);

            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void deleteArtista(int codiceArtista) throws RecordNonPresenteException,
            RecordCorrelatoException {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Artista WHERE CodiceArtista = ?");
            pstmt.setInt(1, codiceArtista);
            int count = pstmt.executeUpdate();
            pstmt.close();
            // count contiene 1 se la cancellazione è avvenuta con successo
            if (count == 0) {
                throw new RecordNonPresenteException("CodiceArtista non presente in archivio");
            }
            makePersistent();
        } catch (SQLException ex) {
            throw new RecordCorrelatoException("Si sta tentando di cancellare un Artista cui sono correlate Opere");
        }
    }

    public Artista findArtista(int codiceArtista) throws RecordNonPresenteException {
        if (!artistaExists(codiceArtista)) {
            throw new RecordNonPresenteException("CodiceArtista non presente in archivio");
        }
        Artista artista = new Artista();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Artista WHERE CodiceArtista = ?");
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
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return artista;
    }

    public void updateArtista(Artista artista) throws RecordNonPresenteException {
        int codArt = artista.getCodiceArtista();
        if (!artistaExists(codArt)) {
            throw new RecordNonPresenteException("CodiceArtista non presente in archivio");
        }
        String cognome = artista.getCognome();
        String nome = artista.getNome();
        String noteBio = artista.getNoteBiografiche();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Artista SET Cognome = ?, Nome = ?, NoteBiografiche = ? WHERE CodiceArtista = ?");
            pstmt.setInt(4, codArt);
            pstmt.setString(1, cognome);
            pstmt.setString(2, nome);
            pstmt.setString(3, noteBio);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();

        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public static Artista staticFindArtista(int codiceArtista, Connection conn) {
        Artista artista = new Artista();
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement("SELECT * FROM Artista WHERE CodiceArtista = ?");
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
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return artista;
    }

    public Vector<Artista> findAllArtisti() {
        Vector<Artista> v = new Vector<Artista>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Artista");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Artista artista = new Artista();
                artista.setCodiceArtista(rs.getInt("CodiceArtista"));
                artista.setCognome(rs.getString("Cognome"));
                artista.setNome(rs.getString("Nome"));
                artista.setNoteBiografiche(rs.getString("NoteBiografiche"));
                v.add(artista);
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return v;
    }

    private boolean artistaExists(int codiceArtista) {
        int codiceReale = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT CodiceArtista FROM Artista WHERE CodiceArtista = ?");
            pstmt.setInt(1, codiceArtista);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                codiceReale = rs.getInt("CodiceArtista");
                System.out.println("CodiceArtista: " + codiceReale);
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
                    connection.prepareStatement("SELECT * FROM Artista");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
