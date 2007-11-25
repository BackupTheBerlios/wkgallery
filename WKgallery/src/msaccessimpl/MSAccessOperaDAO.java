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
    }

    public boolean deleteOpera(String codiceOpera) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM Opera WHERE CodiceOpera = ?");
            pstmt.setString(1, codiceOpera);
            int count = pstmt.executeUpdate();
            pstmt.close();
            // count contiene 1 se la cancellazione è avvenuta con successo
            if (count > 0) {
                makePersistent();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Opera findOpera(String codiceOpera) {
        Opera opera = new Opera();
        Artista artista = new Artista();
        Fattura fattura = new Fattura
        int codArt = -1;
        int numFatt = -1;
        int annoFatt = -1;
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Opera WHERE CodiceOpera = ?");
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
            pstmt.clearParameters();
            pstmt = connection.prepareStatement("SELECT * FROM Artista WHERE CodiceArtista = ?");
            pstmt.setInt(1, codArt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                artista.setCodiceArtista(rs.getInt("CodiceArtista"));
                artista.setCognome(rs.getString("Cognome"));
                artista.setNome(rs.getString("Nome"));
                artista.setNoteBiografiche(rs.getString("NoteBiografiche"));
            }
            opera.setArtista(artista);
            pstmt.close();
            pstmt.clearParameters();
            pstmt = connection.prepareStatement("SELECT * FROM Fattura WHERE NumeroFattura = ? AND AnnoFattura = ?");
            pstmt.setInt(1, numFatt);
            pstmt.setInt(2, annoFatt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                fattura.
                /*
                artista.setCodiceArtista(rs.getInt("CodiceArtista"));
                artista.setCognome(rs.getString("Cognome"));
                artista.setNome(rs.getString("Nome"));
                artista.setNoteBiografiche(rs.getString("NoteBiografiche"));
                 * */
            }
            opera.setArtista(artista);
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return opera;
    }

    public boolean updateOpera(Opera opera) {
        try {
            String codOpera = opera.getCodiceOpera();
            String trovato = findOpera(codOpera).getCodiceOpera();
            if (trovato.isEmpty()) {
                return false;
            } //Opera non presente
            Artista artista = opera.getArtista();
            int codArtista = artista.getCodiceArtista();
            boolean venduto = opera.isVenduto();
            String tecnica = opera.getTecnica();
            String dimensioni = opera.getDimensioni();
            String tipo = opera.getTipo();
            String foto = opera.getFoto();

            PreparedStatement pstmt = connection.prepareStatement("UPDATE Opera SET Artista = ?, Tecnica = ?, Dimensioni = ?, Tipo = ?, Foto = ?, Venduto = ? WHERE CodiceOpera = ?");
            pstmt.setString(7, codOpera);
            pstmt.setInt(1, codArtista);
            pstmt.setString(2, tecnica);
            pstmt.setString(3, dimensioni);
            pstmt.setString(4, tipo);
            pstmt.setString(5, foto);
            pstmt.setBoolean(6, venduto);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();

        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean updateOperaVenduta(Opera opera) {
        try {
            String codOpera = opera.getCodiceOpera();
            boolean venduto = opera.isVenduto();
            String trovato = findOpera(codOpera).getCodiceOpera();
            if (trovato.isEmpty()) {
                return false;
            } //Opera non presente
            PreparedStatement pstmt = connection.prepareStatement("UPDATE Opera SET Venduto = ? WHERE CodiceOpera = ?");
            pstmt.setString(7, codOpera);
            pstmt.setBoolean(6, venduto);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Vector<Opera> selectOperaPerArtista(Artista artista) {
        Vector<Opera> v = new Vector();
        try {
            int codArtista = artista.getCodiceArtista();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Opera WHERE Artista = ?");
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
                opera = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
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

