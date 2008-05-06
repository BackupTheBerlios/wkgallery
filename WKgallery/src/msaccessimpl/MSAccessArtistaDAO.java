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

    private static Connection connection;
    private static MSAccessArtistaDAO msAccessArtistaDao;

    /**
     * Costruttore di MSAccessArtistaDAO.
     */
    public MSAccessArtistaDAO() {
        MSAccessArtistaDAO.connection = MSAccessDAOFactory.connection;
    }

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static MSAccessArtistaDAO getMSAccessArtistaDAO() {
        if (msAccessArtistaDao == null) {
            msAccessArtistaDao = new MSAccessArtistaDAO();
        }
        return msAccessArtistaDao;
    }
    
    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param artista l'artista da inserire
     * @throws exceptions.RecordGiaPresenteException se l'artista è già presente nel DB
     * @throws exceptions.ChiavePrimariaException se la chiave primaria non è valida
     */
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

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param codiceArtista il codice dell'artista da rimuovere dal DB
     * @throws exceptions.RecordNonPresenteException se l'artista non è presente sul DB
     * @throws exceptions.RecordCorrelatoException se l'artista ha Opere correlate
     */
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

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @throws exceptions.RecordCorrelatoException se un artista ha Opere correlate
     */
    public void deleteAllArtisti() throws RecordCorrelatoException {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Artista");
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            throw new RecordCorrelatoException("Si sta tentando di cancellare un Artista cui sono correlate Opere");
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param codiceArtista l'artista da ricercare su DB
     * @return l'artista trovato
     * @throws exceptions.RecordNonPresenteException s la ricerca dà esito negativo.
     */
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

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param artista l'artista da aggiornare
     * @throws exceptions.RecordNonPresenteException se l'artista non è presente in archivio
     */
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

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @return il vettore contenente gli artisti presenti su DB
     */
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

    /**
     * Determina se un artista si trova su DB, dato il suo codice.
     * @param codiceArtista il codice dell'artista da ricercare
     * @return true se l'artista esiste
     *         false altrimenti
     */
    private boolean artistaExists(int codiceArtista) {
        int codiceReale = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT CodiceArtista FROM Artista WHERE CodiceArtista = ?");
            pstmt.setInt(1, codiceArtista);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                codiceReale = rs.getInt("CodiceArtista");
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
