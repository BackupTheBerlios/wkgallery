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
import abstractlayer.Regione;
import daorules.FatturaDAO;
import exceptions.BadFormatException;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
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
import utilities.EMail;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessFatturaDAO implements FatturaDAO {

    private static Connection connection;
    private static MSAccessFatturaDAO msAccessFatturaDao;

    /**
     * Costruttore di MSAccessFatturaDAO.
     */
    public MSAccessFatturaDAO() {
        MSAccessFatturaDAO.connection = MSAccessDAOFactory.connection;
    }

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static MSAccessFatturaDAO getMSAccessArtistaDAO() {
        if (msAccessFatturaDao == null) {
            msAccessFatturaDao = new MSAccessFatturaDAO();
        }
        return msAccessFatturaDao;
    }

    public void insertFattura(Fattura fattura) throws RecordGiaPresenteException,
            ChiavePrimariaException, RecordCorrelatoException {
        try {
            int nFatt = fattura.getNumeroFattura();
            Data dataFatt = fattura.getDataFattura();
            float sconto = fattura.getSconto();
            float totale = fattura.getTotale();
            boolean proforma = fattura.isProforma();

            if (nFatt <= 0) {
                throw new ChiavePrimariaException("Numero della fattura non valido.");
            }
            if (fatturaExists(nFatt, dataFatt.getAnno())) {
                throw new RecordGiaPresenteException("Numero e anno già presenti in archivio");
            }

            Cliente cliente = fattura.getCliente();
            String codCli = cliente.getCodiceCliente();
            Vector<Opera> opere = fattura.getOpere();
            if (totale == 0.0f) { //Se il totale non è 0, significa che si sta ripristinando un salvataggio

                for (Opera opVenduta : opere) {
                    totale += opVenduta.getPrezzo();
                }
                totale = (totale - (totale * sconto)) * 1.2f; // Compreso d'IVA
            }
            fattura.setTotale(totale);

            // Modifica del record di Fattura
            PreparedStatement pstmtF =
                    connection.prepareStatement("INSERT INTO Fattura values (?,?,?,?,?,?,?)");
            pstmtF.setInt(1, nFatt);
            pstmtF.setInt(2, dataFatt.getAnno());
            pstmtF.setString(3, codCli);
            pstmtF.setDate(4, dataFatt.getDate());
            pstmtF.setFloat(5, sconto);
            pstmtF.setFloat(6, totale);
            pstmtF.setBoolean(7, proforma);
            pstmtF.executeUpdate();
            pstmtF.close();
            for (Opera opVenduta : opere) {
                try {
                    PreparedStatement pstmtO =
                            connection.prepareStatement("UPDATE Opera SET NumeroFattura = ?, AnnoFattura = ? WHERE CodiceOpera = ?");
                    pstmtO.setInt(1, nFatt);
                    pstmtO.setInt(2, dataFatt.getAnno());
                    pstmtO.setString(3, opVenduta.getCodiceOpera());
                    pstmtO.executeUpdate();
                    pstmtO.close();
                } catch (SQLException sql) {
                    sql.printStackTrace();
                }
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
            Vector<Opera> v = new Vector<Opera>();
            PreparedStatement pstmtO =
                    connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
            pstmtO.setInt(1, nFatt);
            pstmtO.setInt(2, annoFatt);
            ResultSet rsO = pstmtO.executeQuery();
            while (rsO.next()) {
                String codOpera = rsO.getString("CodiceOpera");
                v.add(findOpera(codOpera));
            }
            pstmtO.close();
            for (Opera opera : v) {
                PreparedStatement pstmtUpd =
                        connection.prepareStatement("UPDATE Opera SET NumeroFattura = NULL, AnnoFattura = NULL WHERE CodiceOpera = ?");
                pstmtUpd.setString(1, opera.getCodiceOpera());
                pstmtUpd.executeUpdate();
                pstmtUpd.close();
                makePersistent();
            }

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



        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void deleteAllFatture() {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Fattura");
            pstmt.executeUpdate();
            makePersistent();
            pstmt.close();
            PreparedStatement pstmtUpd =
                    connection.prepareStatement("UPDATE Opera SET NumeroFattura = NULL, AnnoFattura = NULL");
            pstmtUpd.executeUpdate();
            makePersistent();
            pstmtUpd.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public Fattura findFattura(int nFatt, int annoFatt) throws RecordNonPresenteException, BadFormatException {
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
                fattura.setNumeroFattura(nFatt);
                fattura.setSconto(rsF.getFloat("Sconto"));
                fattura.setTotale(rsF.getFloat("Totale"));

                GregorianCalendar cal = new GregorianCalendar();
                Date dataFatt = rsF.getDate("Data", cal);
                cal.setTime(dataFatt);
                Data data = new Data(new Date(cal.getTimeInMillis()).toString());
                fattura.setDataFattura(data);
                fattura.setCliente(findCliente(rsF.getString("IDCliente")));

                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    String codOpera = rsO.getString("CodiceOpera");
                    v.add(findOpera(codOpera));
                }
            }
            fattura.setOpere(v);


        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return fattura;
    }

    public Vector<Fattura> findAllFatture() throws BadFormatException {
        Vector<Fattura> vF = new Vector<Fattura>();
        try {
            Vector<Opera> vO = new Vector<Opera>();
            PreparedStatement pstmtF =
                    connection.prepareStatement("SELECT * FROM Fattura");
            ResultSet rsF = pstmtF.executeQuery();
            while (rsF.next()) {
                Fattura fattura = new Fattura();
                int nFatt = rsF.getInt("Numero");
                int annoFatt = rsF.getInt("Anno");
                fattura.setNumeroFattura(nFatt);
                GregorianCalendar cal = new GregorianCalendar();
                Date dataFatt = rsF.getDate("Data", cal);
                cal.setTime(dataFatt);
                Data data = new Data(new Date(cal.getTimeInMillis()).toString());
                fattura.setDataFattura(data);
                fattura.setSconto(rsF.getFloat("Sconto"));
                fattura.setTotale(rsF.getFloat("Totale"));
                fattura.setCliente(findCliente(rsF.getString("IDCliente")));

                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    String codOpera = rsO.getString("CodiceOpera");
                    vO.add(findOpera(codOpera));
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

            Vector<Opera> opere = fattura.getOpere();
            for (Opera o : opere) {
                totale += o.getPrezzo();
            }
            totale = (totale - (totale * sconto)) * 1.2f;

            // Modifica del record di Fattura
            PreparedStatement pstmtF =
                    connection.prepareStatement("UPDATE Fattura SET IDCliente = ?, Data = ?, Sconto = ?, Totale = ? WHERE Numero = ? AND Anno = ?");
            pstmtF.setString(1, codCli);
            pstmtF.setDate(2, data.getDate());
            pstmtF.setFloat(3, sconto);
            pstmtF.setFloat(4, totale);
            pstmtF.setInt(5, nFatt);
            pstmtF.setInt(6, annoFatt);
            pstmtF.executeUpdate();
            makePersistent();
            pstmtF.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public Fattura selectFatturaPerOpera(Opera opera) throws RecordNonPresenteException, BadFormatException {
        Vector<Fattura> allFatture = findAllFatture();
        String codOpera = opera.getCodiceOpera();
        for (Fattura fatturaCandidata : allFatture) {
            Vector<Opera> allOpere = fatturaCandidata.getOpere();
            for (Opera o : allOpere) {
                if (o.getCodiceOpera().compareTo(codOpera) == 0) {
                    return fatturaCandidata;
                }
            }
        }
        throw new RecordNonPresenteException("L'opera non compare in alcuna fattura.");
    }

    public Vector<Fattura> selectFatturaPerCliente(Cliente cliente) throws BadFormatException {
        Vector<Fattura> vF = new Vector<Fattura>();
        String codiceCliente = cliente.getCodiceCliente();

        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Fattura WHERE IDCliente = ?");
            pstmt.setString(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Fattura fattura = new Fattura();
                Vector<Opera> v = new Vector<Opera>();
                int nFatt = rs.getInt("Numero");
                int annoFatt = rs.getInt("Anno");
                float sconto = rs.getFloat("Sconto");
                float totale = rs.getFloat("Totale");
                GregorianCalendar cal = new GregorianCalendar();
                Date dataFatt = rs.getDate("Data", cal);
                cal.setTime(dataFatt);
                Data data = new Data(new Date(cal.getTimeInMillis()).toString());
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
                    String codOpera = rsO.getString("CodiceOpera");
                    v.add(findOpera(codOpera));
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

    public Vector<Fattura> selectFatturaPerArtista(Artista artista) throws BadFormatException {
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
                GregorianCalendar cal = new GregorianCalendar();
                Date dataFatt = rsF.getDate("Fattura.Data", cal);
                cal.setTime(dataFatt);
                Data data = new Data(new Date(cal.getTimeInMillis()).toString());
                fattura.setNumeroFattura(nFatt);
                fattura.setDataFattura(data);
                fattura.setSconto(sconto);
                fattura.setTotale(totale);
                fattura.setCliente(findCliente(rsF.getString("IDCliente")));


                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    String codOpera = rsO.getString("CodiceOpera");
                    v.add(findOpera(codOpera));
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

    private Opera findOpera(String codiceOpera) {
        Opera opera = new Opera();
        int codArt = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera WHERE CodiceOpera = ?");
            pstmt.setString(1, codiceOpera);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                opera.setCodiceOpera(rs.getString("CodiceOpera"));
                codArt = rs.getInt("Artista");
                opera.setTitolo(rs.getString("Titolo"));
                opera.setDimensioni(rs.getString("Dimensioni"));
                opera.setTecnica(rs.getString("Tecnica"));
                opera.setTipo(rs.getString("Tipo"));
                opera.setFoto(rs.getString("Foto"));
                opera.setPrezzo(rs.getFloat("Prezzo"));
            }
            pstmt.close();

            opera.setArtista(findArtista(codArt));

        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return opera;
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
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return artista;
    }

    private Cliente findCliente(String codiceCliente) {
        Cliente cliente = new Cliente();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE ID = ?");
            pstmt.setString(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setCodiceCliente(rs.getString("ID"));
                cliente.setCognRsoc1(rs.getString("Cognome_Rsoc1"));
                cliente.setNomeRsoc2(rs.getString("Nome_Rsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getInt("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.toEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.toEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CF_PIVA"));
                cliente.setNote(rs.getString("Annotazioni"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                cliente.setCap(rs.getString("Cap"));
            }
            pstmt.close();
        } catch (BadFormatException ex) {
            Logger.getLogger(MSAccessClienteDAO.class.getName()).log(Level.WARNING,
                    null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return cliente;
    }

    public void updateOperaAggiunta(Opera opera, Fattura fatturaDaAggiornare) throws RecordNonPresenteException {
        String codiceOpera = opera.getCodiceOpera();
        int nFatt = fatturaDaAggiornare.getNumeroFattura();
        int annoFatt = fatturaDaAggiornare.getDataFattura().getAnno();
        if (!fatturaExists(nFatt, annoFatt)) {
            throw new RecordNonPresenteException("Fattura non presente in archivio");
        }
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Opera SET NumeroFattura = ?, AnnoFattura = ? WHERE CodiceOpera = ?");
            pstmt.setInt(1, nFatt);
            pstmt.setInt(2, annoFatt);
            pstmt.setString(3, codiceOpera);
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        updateTotale(nFatt, annoFatt);
    }

    public void updateOperaDisimpegnata(Opera opera, Fattura fatturaDaAggiornare) throws RecordNonPresenteException {
        int nFatt = fatturaDaAggiornare.getNumeroFattura();
        int annoFatt = fatturaDaAggiornare.getDataFattura().getAnno();
        if (!fatturaExists(nFatt, annoFatt)) {
            throw new RecordNonPresenteException("Fattura non presente in archivio");
        }
        String codiceOpera = opera.getCodiceOpera();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Opera SET NumeroFattura = null, AnnoFattura = null WHERE CodiceOpera = ?");
            pstmt.setString(1, codiceOpera);
            pstmt.executeUpdate();
            makePersistent();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        updateTotale(fatturaDaAggiornare.getNumeroFattura(), fatturaDaAggiornare.getDataFattura().getAnno());
    }

    public void updateFatturaDefinitiva(Fattura fattura) throws RecordNonPresenteException {
        try {
            int nFatt = fattura.getNumeroFattura();
            int annoFatt = fattura.getDataFattura().getAnno();

            if (!fatturaExists(nFatt, annoFatt)) {
                throw new RecordNonPresenteException("Fattura non presente in archivio");
            }

            // Modifica del record di Fattura
            PreparedStatement pstmtF =
                    connection.prepareStatement("UPDATE Fattura SET Proforma = TRUE WHERE Numero = ? AND Anno = ?");
            pstmtF.setInt(1, nFatt);
            pstmtF.setInt(2, annoFatt);
            pstmtF.executeUpdate();
            makePersistent();
            pstmtF.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    private void updateTotale(int nFatt, int annoFatt) {
        float totale = 0.0f;
        try {
            PreparedStatement pstmtO =
                    connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
            pstmtO.setInt(1, nFatt);
            pstmtO.setInt(2, annoFatt);
            ResultSet rsO = pstmtO.executeQuery();
            while (rsO.next()) {
                float prezzo = rsO.getFloat("Prezzo");
                totale += prezzo;
            }
            pstmtO.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        float sconto = 0.0f;
        try {
            PreparedStatement pstmtSc =
                    connection.prepareStatement("SELECT * FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmtSc.setInt(1, nFatt);
            pstmtSc.setInt(2, annoFatt);
            ResultSet rsSc = pstmtSc.executeQuery();
            while (rsSc.next()) {
                sconto = rsSc.getFloat("Sconto");
            }
            pstmtSc.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        totale = (totale - (totale * sconto)) * 1.2f;

        try {
            PreparedStatement pstmtTot =
                    connection.prepareStatement("UPDATE Fattura SET Totale = ? WHERE Numero = ? AND Anno = ?");
            pstmtTot.setInt(2, nFatt);
            pstmtTot.setInt(3, annoFatt);
            pstmtTot.setFloat(1, totale);
            pstmtTot.executeUpdate();
            makePersistent();
            pstmtTot.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
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
