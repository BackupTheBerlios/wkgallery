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

    /** Creates a new instance of MSAccessOperaDAO */
    public MSAccessOperaDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertOpera(Opera opera) throws RecordGiaPresenteException {
        try {
            String codOpera = opera.getCodiceOpera();
            if (operaExists(codOpera)) {
                throw new RecordGiaPresenteException("CodiceOpera già presente in archivio");
            }
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

            PreparedStatement pstmt =
                    connection.prepareStatement("INSERT INTO Opera values (?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, codOpera);
            pstmt.setInt(2, codArtista);
            pstmt.setString(3, tecnica);
            pstmt.setString(4, dimensioni);
            pstmt.setString(5, tipo);
            pstmt.setBoolean(6, false);
            pstmt.setString(7, foto);
            pstmt.setInt(8, numFatt);
            pstmt.setInt(9, annoFatt);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void deleteOpera(String codiceOpera) throws RecordNonPresenteException {
        if (!operaExists(codiceOpera)) {
            throw new RecordNonPresenteException("CodiceOpera già presente in archivio");
        }
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
    }

    public Opera findOpera(String codiceOpera) throws RecordNonPresenteException {
        if (!operaExists(codiceOpera)) {
            throw new RecordNonPresenteException("CodiceOpera non presente in archivio");
        }
        Opera opera = new Opera();
        //Fattura fattura = new Fattura
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
            opera.setFattura(MSAccessFatturaDAO.staticFindFattura(numFatt,
                    annoFatt,
                    connection));
            Fattura f = new Fattura();
            opera.setFattura(f);
//            pstmt = connection.prepareStatement("SELECT * FROM Fattura WHERE NumeroFattura = ? AND AnnoFattura = ?");
//            pstmt.setInt(1, numFatt);
//            pstmt.setInt(2, annoFatt);
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//            //bisogna anche andare a recuperare la fattura...
//                /*
//            artista.setCodiceArtista(rs.getInt("CodiceArtista"));
//            artista.setCognome(rs.getString("Cognome"));
//            artista.setNome(rs.getString("Nome"));
//            artista.setNoteBiografiche(rs.getString("NoteBiografiche"));
//             * */
//            }
//            pstmt.close();
//            makePersistent();
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

            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Opera SET Artista = ?, Tecnica = ?, Dimensioni = ?, Tipo = ?, Foto = ?, Venduto = ? WHERE CodiceOpera = ?");
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
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void updateOperaVenduta(Opera opera) throws RecordNonPresenteException {
        try {
            String codOpera = opera.getCodiceOpera();
            if (!operaExists(codOpera)) {
                throw new RecordNonPresenteException("CodiceOpera non presente in archivio");
            }
            boolean venduto = opera.isVenduto();
            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Opera SET Venduto = ? WHERE CodiceOpera = ?");
            pstmt.setString(7, codOpera);
            pstmt.setBoolean(6, venduto);
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

    private boolean operaExists(String codiceOpera) {
        String codiceReale = "";
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT CodiceOpera FROM Opera WHERE CodiceOpera = ?");
            pstmt.setString(1, codiceOpera);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                codiceReale = rs.getString("CodiceOpera");
                System.out.println("CodiceOpera: " + codiceReale);
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

