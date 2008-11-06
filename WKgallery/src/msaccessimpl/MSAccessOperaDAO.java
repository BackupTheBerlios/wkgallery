package msaccessimpl;

import abstractlayer.Artista;
import abstractlayer.Opera;
import daoabstract.OperaDAO;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.io.File;
import java.net.MalformedURLException;
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
 * Implementazione dell'interfaccia OperaDAO per MS Access.
 * @author Marco Celesti
 */
public class MSAccessOperaDAO implements OperaDAO {

    private static Connection connection;
    private static MSAccessOperaDAO msAccessOperaDao;
    private DefaultComparator<Opera> defaultComparator = new DefaultComparator<Opera>();

    /**
     * Costruttore privato di MSAccessOperaDAO.
     */
    private MSAccessOperaDAO() {
        MSAccessOperaDAO.connection = MSAccessDAOFactory.connection;
    }

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static MSAccessOperaDAO getMSAccessArtistaDAO() {
        if (msAccessOperaDao == null) {
            msAccessOperaDao = new MSAccessOperaDAO();
        }
        return msAccessOperaDao;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * Inizialmente l'opera ha i campi relativi alla fattura vuoti: non sono presenti riferimenti
     * alla fattura qualora l'opera venisse immediatamente venduta. Per inseire su DB la vendita
     * di un opera, si usino i metodi della classe che implementa FatturaDao.
     * @param opera l'opera da inserire
     * @throws exceptions.RecordGiaPresenteException se l'opera è già presente in archivio
     * @throws exceptions.ChiavePrimariaException se l'opera ha una chiave primaria non valida
     */
    @Override
    public void insertOpera(Opera opera) throws RecordGiaPresenteException,
            ChiavePrimariaException {
        int codiceOpera = opera.getCodiceOpera();
        if (operaExists(codiceOpera)) {
            throw new RecordGiaPresenteException("Codice opera " + codiceOpera + " già presente in archivio");
        }
        try {
            Artista artista = opera.getArtista();
            int codArtista = artista.getCodiceArtista();
            String titolo = opera.getTitolo();
            String tecnica = opera.getTecnica();
            String dimensioni = opera.getDimensioni();
            String tipo = opera.getTipo();
            String foto = "";
            if (opera.getFoto() != null) {
                foto = opera.getFoto().getPath();
            }
            float prezzo = opera.getPrezzo();

            PreparedStatement pstmt;
            if (codiceOpera == -1) {
                pstmt = connection.prepareStatement("INSERT INTO Opera (Artista, Titolo, Tecnica, Dimensioni, Tipo, Foto, Prezzo) values (?,?,?,?,?,?,?)");
                pstmt.setInt(1, codArtista);
                pstmt.setString(2, titolo);
                pstmt.setString(3, tecnica);
                pstmt.setString(4, dimensioni);
                pstmt.setString(5, tipo);
                pstmt.setString(6, foto);
                pstmt.setFloat(7, prezzo);
            } else {
                pstmt = connection.prepareStatement("INSERT INTO Opera (CodiceOpera, Artista, Titolo, Tecnica, Dimensioni, Tipo, Foto, Prezzo) values (?,?,?,?,?,?,?,?)");
                pstmt.setInt(1, codiceOpera);
                pstmt.setInt(2, codArtista);
                pstmt.setString(3, titolo);
                pstmt.setString(4, tecnica);
                pstmt.setString(5, dimensioni);
                pstmt.setString(6, tipo);
                pstmt.setString(7, foto);
                pstmt.setFloat(8, prezzo);
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
     * @param codiceOpera l'opera da cancellare
     * @throws exceptions.RecordNonPresenteException se l'opera non è presente in archivio
     * @throws exceptions.RecordCorrelatoException se l'opera è presente in una fattura
     */
    @Override
    public void deleteOpera(int codiceOpera) throws RecordNonPresenteException, RecordCorrelatoException {
        if (!operaExists(codiceOpera)) {
            throw new RecordNonPresenteException("Codice opera " + codiceOpera + " non presente in archivio");
        }
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Opera WHERE CodiceOpera = ?");
            pstmt.setInt(1, codiceOpera);
            int count = pstmt.executeUpdate();
            pstmt.close();
            // count contiene 1 se la cancellazione è avvenuta con successo
            if (count > 0) {
                makePersistent();
            }
        } catch (SQLException ex) {
            throw new RecordCorrelatoException("Si sta tentando di cancellare un'Opera cui sono correlate Fatture");
        }

    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @throws exceptions.RecordCorrelatoException se un'opera è inserita in una fattura
     */
    @Override
    public void deleteAllOpere() throws RecordCorrelatoException {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Opera");
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            throw new RecordCorrelatoException("Si sta tentando di cancellare un'Opera cui sono correlate Fatture");
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param codiceOpera l'opera da ricercare
     * @return l'opera trovata
     * @throws exceptions.RecordNonPresenteException se la ricerca dà esito negativo
     */
    @Override
    public Opera findOpera(int codiceOpera) throws RecordNonPresenteException {
        if (!operaExists(codiceOpera)) {
            throw new RecordNonPresenteException("Codice opera " + codiceOpera + " non presente in archivio");
        }
        Opera opera = new Opera();
        int codArt = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera WHERE CodiceOpera = ?");
            pstmt.setInt(1, codiceOpera);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                opera.setCodiceOpera(rs.getInt("CodiceOpera"));
                codArt = rs.getInt("Artista");
                opera.setTitolo(rs.getString("Titolo"));
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                String foto = rs.getString("Foto");
                if (foto != null) {
                    if (!foto.isEmpty()) {
                        try {
                            File imgFile = new File(foto);
                            opera.setFoto(imgFile.toURI().toURL());
                        } catch (MalformedURLException ex) {
                        }
                    }
                }
                opera.setPrezzo(rs.getFloat("Prezzo"));
            }
            rs.close();
            pstmt.close();
            opera.setArtista(findArtista(codArt));
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return opera;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param opera l'opera da aggiornare
     * @throws exceptions.RecordNonPresenteException se l'opera non è presente in archivio
     */
    @Override
    public void updateOpera(Opera opera) throws RecordNonPresenteException {

        int codiceOpera = opera.getCodiceOpera();
        if (!operaExists(codiceOpera)) {
            throw new RecordNonPresenteException("Codice opera " + codiceOpera + " non presente in archivio");
        }
        Artista artista = opera.getArtista();
        int codArtista = artista.getCodiceArtista();
        String titolo = opera.getTitolo();
        String tecnica = opera.getTecnica();
        String dimensioni = opera.getDimensioni();
        String tipo = opera.getTipo();
        String foto = "";
        if (opera.getFoto() != null) {
            foto = opera.getFoto().getPath();
        }
        float prezzo = opera.getPrezzo();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Opera SET Artista = ?, Tecnica = ?, Dimensioni = ?, Tipo = ?, Foto = ?, Prezzo = ?, Titolo = ? WHERE CodiceOpera = ?");
            pstmt.setInt(8, codiceOpera);
            pstmt.setInt(1, codArtista);
            pstmt.setString(2, tecnica);
            pstmt.setString(3, dimensioni);
            pstmt.setString(4, tipo);
            pstmt.setString(5, foto);
            pstmt.setFloat(6, prezzo);
            pstmt.setString(7, titolo);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param artista l'artista di cui cercare le opere
     * @return le opere realizzate dall'artista passato come parametro
     */
    @Override
    public Vector<Opera> selectOperePerArtista(Artista artista) {
        Vector<Opera> v = new Vector<Opera>();
        try {
            int codArtista = artista.getCodiceArtista();
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera WHERE Artista = ?");
            pstmt.setInt(1, codArtista);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Opera opera = new Opera();
                opera.setCodiceOpera(rs.getInt("CodiceOpera"));
                opera.setTitolo(rs.getString("Titolo"));
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setArtista(artista);
                String foto = rs.getString("Foto");
                if (foto != null) {
                    try {
                        File imgFile = new File(foto);
                        opera.setFoto(imgFile.toURI().toURL());
                    } catch (MalformedURLException ex) {
                    }
                }
                opera.setPrezzo(rs.getFloat("Prezzo"));
                v.add(opera);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @return il vettore contenente tutte le opere su DB
     */
    @Override
    public Vector<Opera> findAllOpere() {
        Vector<Opera> v = new Vector<Opera>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Opera opera = new Opera();
                opera.setCodiceOpera(rs.getInt("CodiceOpera"));
                opera.setTitolo(rs.getString("Titolo"));
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setArtista(findArtista(rs.getInt("Artista")));
                String foto = rs.getString("Foto");
                if (foto != null) {
                    try {
                        File imgFile = new File(foto);
                        opera.setFoto(imgFile.toURI().toURL());
                    } catch (MalformedURLException ex) {
                    }
                }
                opera.setPrezzo(rs.getFloat("Prezzo"));
                v.add(opera);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @return le opere opzionate in una fattura
     */
    @Override
    public Vector<Opera> findOpereOpzionate() {
        Vector<Opera> v = new Vector<Opera>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera INNER JOIN Fattura ON Fattura.Numero = Opera.NumeroFattura AND Fattura.Anno = Opera.AnnoFattura WHERE Fattura.Proforma = true");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Opera opera = new Opera();
                opera.setCodiceOpera(rs.getInt("CodiceOpera"));
                opera.setTitolo(rs.getString("Titolo"));
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setArtista(findArtista(rs.getInt("Artista")));
                String foto = rs.getString("Foto");
                if (foto != null) {
                    try {
                        File imgFile = new File(foto);
                        opera.setFoto(imgFile.toURI().toURL());
                    } catch (MalformedURLException ex) {
                    }
                }
                opera.setPrezzo(rs.getFloat("Prezzo"));
                v.add(opera);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @return le opere non opzionate (disimpegnate) in alcuna fattura
     */
    @Override
    public Vector<Opera> findOpereDisponibili() {
        Vector<Opera> v = new Vector<Opera>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura IS NULL");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Opera opera = new Opera();
                opera.setCodiceOpera(rs.getInt("CodiceOpera"));
                opera.setTitolo(rs.getString("Titolo"));
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setArtista(findArtista(rs.getInt("Artista")));
                String foto = rs.getString("Foto");
                if (foto != null) {
                    try {
                        File imgFile = new File(foto);
                        opera.setFoto(imgFile.toURI().toURL());
                    } catch (MalformedURLException ex) {
                    }
                }
                opera.setPrezzo(rs.getFloat("Prezzo"));
                v.add(opera);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(v, defaultComparator);
        return v;
    }

    /**
     * Permette la ricerca di un artista a partire dal suo codice.
     * @param codiceArtista il codice dell'artista da cercare
     * @return l'artista (se esiste)
     */
    private Artista findArtista(int codiceArtista) {
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
     * Determina se l'opera con un dato codice esiste
     * @param codiceOpera l'opera da cercare
     * @return true se l'opera esiste
     */
    private boolean operaExists(int codiceOpera) {
        int codiceReale = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT CodiceOpera FROM Opera WHERE CodiceOpera = ?");
            pstmt.setInt(1, codiceOpera);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                codiceReale = rs.getInt("CodiceOpera");
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
    private void makePersistent() {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT CodiceOpera FROM Opera");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}

