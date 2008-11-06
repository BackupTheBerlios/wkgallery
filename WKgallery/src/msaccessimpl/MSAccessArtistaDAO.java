package msaccessimpl;

import abstractlayer.Artista;
import daoabstract.ArtistaDAO;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DefaultComparator;

/**
 * Implementazione dell'interfaccia ArtistaDAO per MS Access.
 * @author Marco Celesti
 */
public class MSAccessArtistaDAO implements ArtistaDAO {

    private static Connection connection;
    private static MSAccessArtistaDAO msAccessArtistaDao;
    private DefaultComparator<Artista> defaultComparator = new DefaultComparator<Artista>();

    /**
     * Costruttore privato di MSAccessArtistaDAO.
     */
    private MSAccessArtistaDAO() {
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
    @Override
    public void insertArtista(Artista artista) throws RecordGiaPresenteException,
            ChiavePrimariaException {
        int codiceArtista = artista.getCodiceArtista();
        if (artistaExists(codiceArtista)) {
            throw new RecordGiaPresenteException("Codice artista " + codiceArtista + " già presente in archivio");
        }
        String cognome = artista.getCognome();
        String nome = artista.getNome();
        String noteBio = artista.getNoteBiografiche();
        try {
            PreparedStatement pstmt;
            if (codiceArtista == -1) {
                pstmt =
                        connection.prepareStatement("INSERT INTO Artista (Cognome, Nome, NoteBiografiche) values (?,?,?)");
                pstmt.setString(1, cognome);
                pstmt.setString(2, nome);
                pstmt.setString(3, noteBio);
            } else {
                pstmt =
                        connection.prepareStatement("INSERT INTO Artista (CodiceArtista, Cognome, Nome, NoteBiografiche) values (?,?,?,?)");
                pstmt.setInt(1, codiceArtista);
                pstmt.setString(2, cognome);
                pstmt.setString(3, nome);
                pstmt.setString(4, noteBio);
            }
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
    @Override
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
                throw new RecordNonPresenteException("Codice artista " + codiceArtista + " non presente in archivio");
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
    @Override
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
    @Override
    public Artista findArtista(int codiceArtista) throws RecordNonPresenteException {
        if (!artistaExists(codiceArtista)) {
            throw new RecordNonPresenteException("Codice artista " + codiceArtista + " non presente in archivio");
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
            rs.close();
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
    @Override
    public void updateArtista(Artista artista) throws RecordNonPresenteException {
        int codiceArtista = artista.getCodiceArtista();
        if (!artistaExists(codiceArtista)) {
            throw new RecordNonPresenteException("Codice artista " + codiceArtista + " non presente in archivio");
        }
        String cognome = artista.getCognome();
        String nome = artista.getNome();
        String noteBio = artista.getNoteBiografiche();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Artista SET Cognome = ?, Nome = ?, NoteBiografiche = ? WHERE CodiceArtista = ?");
            pstmt.setInt(4, codiceArtista);
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
    @Override
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
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Determina se un artista si trova su DB, dato il suo codice.
     * @param codiceArtista il codice dell'artista da ricercare
     * @return true se l'artista esiste
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
            rs.close();
            pstmt.close();
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
