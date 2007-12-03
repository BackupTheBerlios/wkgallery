/*
 * MSAccessFatturaDao.java
 *
 * Created on 20 ottobre 2007, 18.21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package msaccessimpl;

import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import daorules.FatturaDAO;
import exceptions.ChiavePrimariaException;
import exceptions.RecordGiaPresenteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessFatturaDAO implements FatturaDAO {

    private Connection connection;

    /** Creates a new instance of MSAccessFatturaDao */
    public MSAccessFatturaDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertFattura(Fattura fattura) throws RecordGiaPresenteException, ChiavePrimariaException {
        try {
            int nFatt = fattura.getNumeroFattura();
            int annoFatt = fattura.getAnnoFattura();
            System.out.println(nFatt + ", " + annoFatt);
            if ((nFatt <= 0) || (annoFatt <= 0)) {
                throw new ChiavePrimariaException("Numero o anno della fattura non validi.");
            }
            if (fatturaExists(nFatt, annoFatt)) {
                throw new RecordGiaPresenteException("Numero e anno già presenti in archivio");
            }
            Cliente cliente = fattura.getCliente();
            String codCli = cliente.getCodiceCliente();
            // Modifica del record di Fattura
            PreparedStatement pstmt =
                    connection.prepareStatement("INSERT INTO Fattura values (?,?,?)");
            pstmt.setInt(1, nFatt);
            pstmt.setInt(2, annoFatt);
            pstmt.setString(3, codCli);     
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
            // Modifica dei record di Opera
            Vector<Opera> opere = fattura.getOpere();
            for(Opera opVenduta : opere) {
                String codOpera = opVenduta.getCodiceOpera();
                System.out.println("codOpera: " + codOpera);
                Opera dbOpera = MSAccessOperaDAO.staticFindOpera(codOpera, connection);
                dbOpera.setPrezzo(opVenduta.getPrezzo());
                dbOpera.setFattura(opVenduta.getFattura());
                dbOpera.setVenduto(opVenduta.isVenduto());
                MSAccessOperaDAO.staticUpdateOperaVenduta(dbOpera, nFatt, annoFatt, connection);
            }
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        /*
         * try {
            String codOpera = opera.getCodiceOpera();
            if (codOpera.isEmpty()) {
                throw new ChiavePrimariaException("CodiceOpera non può contenere una stringa di lunghezza zero.");
            }
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
         * */
    }

    public void deleteFattura(int numero_anno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Fattura findFattura(int numero_anno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateFattura(Fattura fattura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vector<Fattura> selectFatturaPerCliente(Cliente cliente) {
        Vector<Fattura> v = new Vector<Fattura>();
        Fattura fattura = new Fattura();
        String codiceCliente = cliente.getCodiceCliente();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Fattura WHERE IDCliente = ?");
            pstmt.setString(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fattura.setNumeroFattura(rs.getInt("Numero"));
                fattura.setAnnoFattura(rs.getInt("Anno"));
                fattura.setCliente(cliente);
                v.add(fattura);
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return v;
    }
    

    public Collection selectFatturaTO(Fattura criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Fattura staticFindFattura(int numeroFattura, int annoFattura, Connection conn) {
        Fattura fattura = new Fattura();
        String codiceCliente = "";
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement("SELECT * FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmt.setInt(1, numeroFattura);
            pstmt.setInt(2, annoFattura);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                fattura.setNumeroFattura(rs.getInt("Numero"));
                fattura.setAnnoFattura(rs.getInt("Anno"));
                codiceCliente = rs.getString("IDCliente");
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        //Cliente cliente = MSAccessClienteDAO
        return fattura;
    }
    
    private boolean fatturaExists(int nFatt, int annoFatt) {
        int nFattura = -1;
        int annoFattura = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT Numero,Anno FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmt.setInt(1, nFattura);
            pstmt.setInt(2, annoFattura);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nFattura = rs.getInt("Numero");
                annoFattura = rs.getInt("Numero");
            }
            if ((nFattura != -1) && (annoFattura != -1)) {
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
                    connection.prepareStatement("SELECT Anno FROM Fattura");
            pstmt.executeQuery();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessDAOFactory.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
