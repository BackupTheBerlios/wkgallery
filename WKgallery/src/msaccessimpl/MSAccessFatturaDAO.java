/*
 * MSAccessFatturaDao.java
 *
 * Created on 20 ottobre 2007, 18.21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package msaccessimpl;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import daorules.FatturaDAO;
import exceptions.ChiavePrimariaException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.Data;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessFatturaDAO implements FatturaDAO {

    private Connection connection;

    /** Creates a new instance of MSAccessFatturaDao
     * @param connection 
     */
    public MSAccessFatturaDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertFattura(Fattura fattura) throws RecordGiaPresenteException,
            ChiavePrimariaException {
        try {
            int nFatt = fattura.getNumeroFattura();
            Data dataFatt = fattura.getDataFattura();
            float sconto = fattura.getSconto();
            float totale = 0.0f;

            if (nFatt <= 0) {
                throw new ChiavePrimariaException("Numero della fattura non valido.");
            }
            if (fatturaExists(nFatt, dataFatt.getAnno())) {
                throw new RecordGiaPresenteException("Numero e anno già presenti in archivio");
            }
            Cliente cliente = fattura.getCliente();
            String codCli = cliente.getCodiceCliente();
            Vector<Opera> opere = fattura.getOpere();
            for (Opera opVenduta : opere) {
                totale += opVenduta.getPrezzo();
            }
            totale = totale - (totale * sconto);
            // Modifica del record di Fattura
            PreparedStatement pstmt =
                    connection.prepareStatement("INSERT INTO Fattura values (?,?,?,?,?,?)");
            pstmt.setInt(1, nFatt);
            pstmt.setInt(2, dataFatt.getAnno());
            pstmt.setString(3, codCli);
            pstmt.setDate(4, dataFatt.getDate());
            pstmt.setFloat(5, sconto);
            pstmt.setFloat(6, totale);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();

            // Modifica dei record di Opera

            for (Opera opVenduta : opere) {
                MSAccessOperaDAO.staticUpdateOperaVenduta(opVenduta.getCodiceOpera(), connection, nFatt, dataFatt.getAnno());
            }
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void deleteFattura(int nFatt, int annoFatt) throws RecordNonPresenteException {
        if (!fatturaExists(nFatt, annoFatt)) {
            throw new RecordNonPresenteException("Fattura non presente in archivio");
        }
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmt.setInt(1, nFatt);
            pstmt.setInt(2, annoFatt);
            int count = pstmt.executeUpdate();
            pstmt.close();
            // count contiene 1 se la cancellazione è avvenuta con successo
            if (count > 0) {
                makePersistent();
            }
            Vector<Opera> v = new Vector<Opera>();
            PreparedStatement pstmtO =
                    connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
            pstmtO.setInt(1, nFatt);
            pstmtO.setInt(2, annoFatt);
            ResultSet rsO = pstmtO.executeQuery();
            while (rsO.next()) {
                Opera opera = new Opera();
                String codOpera = rsO.getString("CodiceOpera");
                opera =
                        MSAccessOperaDAO.staticFindOpera(codOpera,
                        connection);
                v.add(opera);
            }
            pstmtO.close();
            for (Opera opera : v) {
                PreparedStatement pstmtUpd =
                        connection.prepareStatement("UPDATE Opera SET NumeroFattura = 0, AnnoFattura = 0 WHERE CodiceOpera = ?");
                pstmtUpd.setString(1, opera.getCodiceOpera());
                pstmtUpd.executeUpdate();
                pstmtUpd.close();
                makePersistent();
            }

        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public Fattura findFattura(int nFatt, int annoFatt) throws RecordNonPresenteException {
        Fattura fattura = new Fattura();
        try {
            if (!fatturaExists(nFatt, annoFatt)) {
                throw new RecordNonPresenteException("Fattura non presente in archivio");
            }

            Vector<Opera> v = new Vector<Opera>();
            PreparedStatement pstmtF =
                    connection.prepareStatement("SELECT * FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmtF.setInt(1, nFatt);
            pstmtF.setInt(2, annoFatt);
            ResultSet rsF = pstmtF.executeQuery();
            if (rsF.next()) {
                fattura.setNumeroFattura(annoFatt);
                fattura.setNumeroFattura(annoFatt);
                fattura.setSconto(rsF.getFloat("Sconto"));
                fattura.setTotale(rsF.getFloat("Totale"));
                fattura.setCliente(MSAccessClienteDAO.staticFindCliente(rsF.getString("IDCliente"),
                        connection));

                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    Opera opera = new Opera();
                    String codOpera = rsO.getString("CodiceOpera");
                    opera =
                            MSAccessOperaDAO.staticFindOpera(codOpera,
                            connection);
                    v.add(opera);
                }
            }
            fattura.setOpere(v);


        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return fattura;
    }

    public Vector<Fattura> findAllFatture() {
        Vector<Fattura> vF = new Vector<Fattura>();
        try {
            Vector<Opera> vO = new Vector<Opera>();
            PreparedStatement pstmtF =
                    connection.prepareStatement("SELECT * FROM Fattura");
            ResultSet rsF = pstmtF.executeQuery();
            while (rsF.next()) {
                Fattura fattura = new Fattura();
                int nFatt = rsF.getInt("NumeroFattura");
                int annoFatt = rsF.getInt("AnnoFattura");
                fattura.setNumeroFattura(nFatt);
                fattura.setNumeroFattura(annoFatt);
                fattura.setSconto(rsF.getFloat("Sconto"));
                fattura.setTotale(rsF.getFloat("Totale"));
                fattura.setCliente(MSAccessClienteDAO.staticFindCliente(rsF.getString("IDCliente"),
                        connection));

                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    Opera opera = new Opera();
                    String codOpera = rsO.getString("CodiceOpera");
                    opera =
                            MSAccessOperaDAO.staticFindOpera(codOpera,
                            connection);
                    vO.add(opera);
                }
                fattura.setOpere(vO);
                vF.add(fattura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return vF;
    }

    public void updateFattura(Fattura fattura) throws RecordNonPresenteException {
        try {
            int nFatt = fattura.getNumeroFattura();
            int annoFatt = fattura.getDataFattura().getAnno();
            float sconto = fattura.getSconto();
            float totale = 0.0f;

            if (!fatturaExists(nFatt, annoFatt)) {
                throw new RecordNonPresenteException("Fattura non presente in archivio");
            }
            String codCli = fattura.getCliente().getCodiceCliente();
            Data data = fattura.getDataFattura();
            Vector<Opera> vNew = new Vector<Opera>();
            Vector<Opera> vOld = new Vector<Opera>();
            vNew.addAll(fattura.getOpere());
            for (Opera opVenduta : vNew) {
                totale += opVenduta.getPrezzo();
            }
            totale = totale - (totale * sconto);

            // Modifica del record di Fattura
            PreparedStatement pstmtF =
                    connection.prepareStatement("UPDATE Fattura SET IDCliente = ?, Data = ?, Sconto = ?, Totale = ? WHERE NumeroFattura = ? AND AnnoFattura = ?");
            pstmtF.setString(1, codCli);
            pstmtF.setDate(2, data.getDate());
            pstmtF.setFloat(3, sconto);
            pstmtF.setFloat(4, totale);
            pstmtF.setInt(5, nFatt);
            pstmtF.setInt(6, annoFatt);
            pstmtF.executeUpdate();
            pstmtF.close();
            makePersistent();

            // Modifica dei record di Opera
            PreparedStatement pstmtO =
                    connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
            pstmtO.setInt(1, nFatt);
            pstmtO.setInt(2, annoFatt);
            ResultSet rsO = pstmtO.executeQuery();
            while (rsO.next()) {
                Opera opera = new Opera();
                String codOpera = rsO.getString("CodiceOpera");
                opera =
                        MSAccessOperaDAO.staticFindOpera(codOpera,
                        connection);
                vOld.add(opera);
            }

            for (Opera oNew : vNew) {
                MSAccessOperaDAO.staticUpdateOperaVenduta(oNew.getCodiceOpera(), connection, nFatt, annoFatt);
            }
            makePersistent();

            // Elimino gli elementi comuni
            for (Opera oOld : vOld) {
                for (Opera oNew : vNew) {
                    if (oNew.getCodiceOpera().equalsIgnoreCase(oOld.getCodiceOpera())) {
                        vOld.remove(oOld);
                        vNew.remove(oNew);
                    }
                }
            }
            makePersistent();

            // Il vettore vOld contiene gli elementi che sono stati tolti
            // dalla fattura. Vanno quindi aggiornati i campi relativi alla
            // alla fattura di ognuna di queste opere.
            for (Opera oOld : vOld) {
                MSAccessOperaDAO.staticUpdateOperaVenduta(oOld.getCodiceOpera(), connection, 0, 0);
            }
            makePersistent();

        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public Vector<Fattura> selectFatturaPerCliente(Cliente cliente) {
        Vector<Fattura> vF = new Vector<Fattura>();
        Fattura fattura = new Fattura();
        String codiceCliente = cliente.getCodiceCliente();

        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Fattura WHERE IDCliente = ?");
            pstmt.setString(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Vector<Opera> v = new Vector<Opera>();
                int nFatt = rs.getInt("Numero");
                int annoFatt = rs.getInt("Anno");
                float sconto = rs.getFloat("Sconto");
                float totale = rs.getFloat("Totale");
                Date dataFatt = rs.getDate("Data", new GregorianCalendar());
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dataFatt);
                Data data = new Data(cal.DAY_OF_MONTH, cal.MONTH, cal.YEAR);

                fattura.setNumeroFattura(nFatt);
                fattura.setDataFattura(data);
                fattura.setCliente(cliente);
                fattura.setSconto(sconto);
                fattura.setTotale(totale);

                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    Opera opera = new Opera();
                    String codOpera = rsO.getString("CodiceOpera");
                    opera =
                            MSAccessOperaDAO.staticFindOpera(codOpera,
                            connection);
                    v.add(opera);
                }
                fattura.setOpere(v);
                vF.add(fattura);
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return vF;
    }

    public Vector<Fattura> selectFatturaPerArtista(Artista artista) {

        Vector<Fattura> vF = new Vector<Fattura>();
        int codiceArtista = artista.getCodiceArtista();

        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Fattura INNER JOIN Opera ON Fattura.Numero = Opera.NumeroFattura AND Fattura.Anno = Opera.AnnoFattura WHERE Opera.Artista = ?");
            pstmt.setInt(1, codiceArtista);
            ResultSet rsF = pstmt.executeQuery();
            while (rsF.next()) {
                Vector<Opera> v = new Vector<Opera>();
                Fattura fattura = new Fattura();
                int nFatt = rsF.getInt("Fattura.Numero");
                int annoFatt = rsF.getInt("Fattura.Anno");
                float sconto = rsF.getFloat("Sconto");
                float totale = rsF.getFloat("Totale");
                Date dataFatt = rsF.getDate("Fattura.Data", new GregorianCalendar());
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dataFatt);
                Data data = new Data(cal.DAY_OF_MONTH, cal.MONTH, cal.YEAR);

                fattura.setNumeroFattura(nFatt);
                fattura.setDataFattura(data);
                fattura.setSconto(sconto);
                fattura.setTotale(totale);
                fattura.setCliente(MSAccessClienteDAO.staticFindCliente(rsF.getString("IDCliente"),
                        connection));


                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    Opera opera = new Opera();
                    String codOpera = rsO.getString("CodiceOpera");
                    opera =
                            MSAccessOperaDAO.staticFindOpera(codOpera,
                            connection);
                    v.add(opera);
                }

                fattura.setOpere(v);
                vF.add(fattura);
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return vF;
    }

    public static Fattura staticFindFattura(int numeroFattura, int annoFattura,
            Connection conn) {
        Fattura fattura = new Fattura();
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement("SELECT * FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmt.setInt(1, numeroFattura);
            pstmt.setInt(2, annoFattura);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date dataFatt = rs.getDate("Data", new GregorianCalendar());
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dataFatt);
                Data data = new Data(cal.DAY_OF_MONTH, cal.MONTH, cal.YEAR);
                float sconto = rs.getFloat("Sconto");
                float totale = rs.getFloat("Totale");
                fattura.setNumeroFattura(rs.getInt("Numero"));
                fattura.setDataFattura(data);
                fattura.setSconto(sconto);
                fattura.setTotale(totale);
                fattura.setCliente(MSAccessClienteDAO.staticFindCliente(rs.getString("IDCliente"),
                        conn));
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return fattura;
    }

    private boolean fatturaExists(int nFatt, int annoFatt) {
        int nFattura = -1;
        int annoFattura = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT Numero, Anno FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmt.setInt(1, nFatt);
            pstmt.setInt(2, annoFatt);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nFattura = rs.getInt("Numero");
                annoFattura = rs.getInt("Anno");
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
