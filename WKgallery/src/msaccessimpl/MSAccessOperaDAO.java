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
import abstractlayer.Fattura;
import abstractlayer.Opera;
import daorules.OperaDAO;
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
public class MSAccessOperaDAO implements OperaDAO {

    private Connection connection;

    /** Creates a new instance of MSAccessOperaDAO
     * @param connection 
     */
    public MSAccessOperaDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertOpera(Opera opera) throws RecordGiaPresenteException,
            ChiavePrimariaException {
        try {
            String codOpera = opera.getCodiceOpera();
            if (codOpera.isEmpty()) {
                throw new ChiavePrimariaException("CodiceOpera non può contenere una stringa di lunghezza zero.");
            }
            if (operaExists(codOpera)) {
                throw new RecordGiaPresenteException("CodiceOpera già presente in archivio");
            }
            Artista artista = opera.getArtista();
            int codArtista = artista.getCodiceArtista();
            String tecnica = opera.getTecnica();
            String dimensioni = opera.getDimensioni();
            String tipo = opera.getTipo();
            String foto = opera.getFoto();
            float prezzo = opera.getPrezzo();

            PreparedStatement pstmt =
                    connection.prepareStatement("INSERT INTO Opera values (?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, codOpera);
            pstmt.setInt(2, codArtista);
            pstmt.setString(3, tecnica);
            pstmt.setString(4, dimensioni);
            pstmt.setString(5, tipo);
            pstmt.setBoolean(6, false);
            pstmt.setString(7, foto);

            pstmt.setInt(8, 0); //Campo relativo alla fattura - numero
            pstmt.setInt(9, 0); //Campo relativo alla fattura - anno

            pstmt.setFloat(10, prezzo);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * Permette la cancellazione dall'archivio di un'opera. Va preventivamente
     * verificato che l'opera non abbia fatture associate. In tal caso il metodo
     * <code>deleteOpera(String codiceOpera)</code> non restituisce alcuna eccezione.
     * @param codiceOpera
     * @throws exceptions.RecordNonPresenteException
     */
    public void deleteOpera(String codiceOpera) throws RecordNonPresenteException, RecordCorrelatoException {
        if (!operaExists(codiceOpera)) {
            throw new RecordNonPresenteException("CodiceOpera non presente in archivio");
        }
        Opera operaToDelete = findOpera(codiceOpera);
        if (operaToDelete.getFattura().getNumeroFattura() == 0) {
            try {
                PreparedStatement pstmt =
                        connection.prepareStatement("DELETE FROM Opera WHERE CodiceOpera = ?");
                pstmt.setString(1, codiceOpera);
                int count = pstmt.executeUpdate();
                pstmt.close();
                // count contiene 1 se la cancellazione è avvenuta con successo
                if (count > 0) {
                    makePersistent();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        } else {
            throw new RecordCorrelatoException("L'opera che si desidera cancellare è contenuta in una fattura.");
        }
    }

    public Opera findOpera(String codiceOpera) throws RecordNonPresenteException {
        if (!operaExists(codiceOpera)) {
            throw new RecordNonPresenteException("CodiceOpera non presente in archivio");
        }
        Opera opera = new Opera();
        int codArt = -1;
        int numFatt = 0;
        int annoFatt = 0;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera WHERE CodiceOpera = ?");
            pstmt.setString(1, codiceOpera);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                opera.setCodiceOpera(rs.getString("CodiceOpera"));
                codArt = rs.getInt("Artista");
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setVenduto(rs.getBoolean("Venduto"));
                opera.setFoto(rs.getString("Foto"));
                numFatt = rs.getInt("NumeroFattura");
                annoFatt = rs.getInt("AnnoFattura");
            }
            pstmt.close();

            opera.setArtista(MSAccessArtistaDAO.staticFindArtista(codArt,
                    connection));
            opera.setFattura(MSAccessFatturaDAO.staticFindFattura(numFatt,
                    annoFatt,
                    connection));
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return opera;
    }

    public void updateOpera(Opera opera) throws RecordNonPresenteException {
        try {
            String codOpera = opera.getCodiceOpera();
            if (!operaExists(codOpera)) {
                throw new RecordNonPresenteException("CodiceOpera non presente in archivio");
            }
            Artista artista = opera.getArtista();
            int codArtista = artista.getCodiceArtista();
            boolean venduto = opera.isVenduto();
            String tecnica = opera.getTecnica();
            String dimensioni = opera.getDimensioni();
            String tipo = opera.getTipo();
            String foto = opera.getFoto();
            float prezzo = opera.getPrezzo();

            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Opera SET Artista = ?, Tecnica = ?, Dimensioni = ?, Tipo = ?, Foto = ?, Venduto = ?, Prezzo = ? WHERE CodiceOpera = ?");
            pstmt.setString(7, codOpera);
            pstmt.setInt(1, codArtista);
            pstmt.setString(2, tecnica);
            pstmt.setString(3, dimensioni);
            pstmt.setString(4, tipo);
            pstmt.setString(5, foto);
            pstmt.setBoolean(6, venduto);
            pstmt.setFloat(7, prezzo);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();

        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public Vector<Opera> selectOperaPerArtista(Artista artista) {
        Vector<Opera> v = new Vector<Opera>();
        try {
            int codArtista = artista.getCodiceArtista();
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera WHERE Artista = ?");
            pstmt.setInt(1, codArtista);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Opera opera = new Opera();
                opera.setCodiceOpera(rs.getString("CodiceOpera"));
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setArtista(artista);
                opera.setFoto(rs.getString("Foto"));
                opera.setVenduto(rs.getBoolean("Venduto"));
                v.add(opera);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return v;
    }

    public Vector<Opera> findAllOpere() {
        Vector<Opera> v = new Vector<Opera>();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Opera opera = new Opera();
                opera.setCodiceOpera(rs.getString("CodiceOpera"));
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setArtista(MSAccessArtistaDAO.staticFindArtista(rs.getInt("Artista"),
                        connection));
                opera.setFoto(rs.getString("Foto"));
                opera.setVenduto(rs.getBoolean("Venduto"));
                int nFatt = rs.getInt("NumeroFattura");
                int annoFatt = rs.getInt("AnnoFattura");
                opera.setFattura(MSAccessFatturaDAO.staticFindFattura(nFatt, annoFatt, connection));
                v.add(opera);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return v;
    }

    public static Opera staticFindOpera(String codiceOpera,
            Connection connection) {
        Opera opera = new Opera();
        int codArt = -1;
        int numFatt = -1;
        int annoFatt = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera WHERE CodiceOpera = ?");
            pstmt.setString(1, codiceOpera);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                opera.setCodiceOpera(rs.getString("CodiceOpera"));
                codArt = rs.getInt("Artista");
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setVenduto(rs.getBoolean("Venduto"));
                opera.setFoto(rs.getString("Foto"));
                numFatt = rs.getInt("NumeroFattura");
                annoFatt = rs.getInt("AnnoFattura");
            }
            pstmt.close();

            opera.setArtista(MSAccessArtistaDAO.staticFindArtista(codArt,
                    connection));
            Fattura f = new Fattura();
            opera.setFattura(f);

        // Non serve recuperare la fattura: è proprio la fattura che setta se stessa all'interno dell'opera.
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return opera;
    }

    public static void staticUpdateOperaVenduta(String codOpera,
            Connection connection, int nFatt, int annoFatt) {
        try {
            boolean venduto = true;
            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Opera SET Venduto = ?, NumeroFattura = ?, AnnoFattura = ? WHERE CodiceOpera = ?");
            pstmt.setString(4, codOpera);
            pstmt.setBoolean(1, venduto);
            pstmt.setInt(2, nFatt);
            pstmt.setInt(3, annoFatt);
            pstmt.executeUpdate();
            pstmt.close();

            // metodo makePersistent "integrato"
            PreparedStatement pstmtDummy =
                    connection.prepareStatement("SELECT CodiceOpera FROM Opera");
            pstmtDummy.executeQuery();
            pstmtDummy.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    private boolean operaExists(String codiceOpera) {
        String codiceReale = "";
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT CodiceOpera FROM Opera WHERE CodiceOpera = ?");
            pstmt.setString(1, codiceOpera);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                codiceReale = rs.getString("CodiceOpera");
            }

            if (!codiceReale.isEmpty()) {
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
                    connection.prepareStatement("SELECT CodiceOpera FROM Opera");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}

