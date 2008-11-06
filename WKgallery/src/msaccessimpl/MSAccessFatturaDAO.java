package msaccessimpl;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import abstractlayer.Regione;
import daoabstract.FatturaDAO;
import exceptions.BadFormatException;
import exceptions.ChiavePrimariaException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.Data;
import utilities.DefaultComparator;
import utilities.EMail;

/**
 * Implementazione dell'interfaccia FatturaDAO per MS Access.
 * @author Marco Celesti
 */
public class MSAccessFatturaDAO implements FatturaDAO {

    private static Connection connection;
    private static MSAccessFatturaDAO msAccessFatturaDao;
    private DefaultComparator<Fattura> defaultComparator = new DefaultComparator<Fattura>();

    /**
     * Costruttore privato di MSAccessFatturaDAO.
     */
    private MSAccessFatturaDAO() {
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

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param fattura la fattura da inserire
     * @throws exceptions.RecordGiaPresenteException se la fattura con numero e anno specificato esite già
     * @throws exceptions.ChiavePrimariaException se il numero o l'anno della fattura non sono validi
     */
    @Override
    public void insertFattura(Fattura fattura) throws RecordGiaPresenteException,
            ChiavePrimariaException {
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
            int codCli = cliente.getCodiceCliente();
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
            pstmtF.setInt(3, codCli);
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
                    pstmtO.setInt(3, opVenduta.getCodiceOpera());
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

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param nFatt il nuemero della fattura
     * @param annoFatt l'anno della fattura
     * @throws exceptions.RecordNonPresenteException se la fattura non esiste
     */
    @Override
    public void deleteFattura(int nFatt, int annoFatt) throws RecordNonPresenteException {
        if (!fatturaExists(nFatt, annoFatt)) {
            throw new RecordNonPresenteException("Fattura " + nFatt + "/" + annoFatt + " non presente in archivio");
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
            rsO.close();
            pstmtO.close();
            for (Opera opera : v) {
                PreparedStatement pstmtUpd =
                        connection.prepareStatement("UPDATE Opera SET NumeroFattura = NULL, AnnoFattura = NULL WHERE CodiceOpera = ?");
                pstmtUpd.setInt(1, opera.getCodiceOpera());
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
            // count contiene 1 se la cancellazione ï¿½ avvenuta con successo
            if (count > 0) {
                makePersistent();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     */
    @Override
    public void deleteAllFatture() {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM Fattura");
            pstmt.executeUpdate();
            pstmt.close();
            makePersistent();
            PreparedStatement pstmtUpd =
                    connection.prepareStatement("UPDATE Opera SET NumeroFattura = NULL, AnnoFattura = NULL");
            pstmtUpd.executeUpdate();
            pstmtUpd.close();
            makePersistent();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessOperaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param nFatt il numero della fattura
     * @param annoFatt l'anno della fattura
     * @return la fattura trovata
     * @throws exceptions.RecordNonPresenteException se la fattura non esiste
     */
    @Override
    public Fattura findFattura(int nFatt, int annoFatt) throws RecordNonPresenteException {
        Fattura fattura = new Fattura();
        try {
            if (!fatturaExists(nFatt, annoFatt)) {
                throw new RecordNonPresenteException("Fattura " + nFatt + "/" + annoFatt + " non presente in archivio");
            }

            Vector<Opera> v = new Vector<Opera>();
            PreparedStatement pstmtF =
                    connection.prepareStatement("SELECT * FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmtF.setInt(1, nFatt);
            pstmtF.setInt(2, annoFatt);
            ResultSet rsF = pstmtF.executeQuery();
            if (rsF.next()) {
                fattura.setNumeroFattura(nFatt);

                GregorianCalendar cal = new GregorianCalendar();
                Date dataFatt = rsF.getDate("Data", cal);
                cal.setTime(dataFatt);
                Data data = null;
                try {
                    data = new Data(new Date(cal.getTimeInMillis()).toString());
                } catch (BadFormatException ex) {
                    Logger.getLogger(MSAccessFatturaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura.setDataFattura(data);
                fattura.setSconto(rsF.getFloat("Sconto"));
                fattura.setTotale(rsF.getFloat("Totale"));
                fattura.setCliente(findCliente(rsF.getInt("IDCliente")));
                fattura.setProforma(rsF.getBoolean("Proforma"));
                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    String codOpera = rsO.getString("CodiceOpera");
                    v.add(findOpera(codOpera));
                }
                rsO.close();
                pstmtO.close();
            }
            fattura.setOpere(v);
            rsF.close();
            pstmtF.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return fattura;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @return il vettore di fatture nel DB
     */
    @Override
    public Vector<Fattura> findAllFatture() {
        Vector<Fattura> vFatt = new Vector<Fattura>();
        try {
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
                Data data = null;
                try {
                    data = new Data(new Date(cal.getTimeInMillis()).toString());
                } catch (BadFormatException ex) {
                    Logger.getLogger(MSAccessFatturaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura.setDataFattura(data);
                fattura.setSconto(rsF.getFloat("Sconto"));
                fattura.setTotale(rsF.getFloat("Totale"));
                fattura.setCliente(findCliente(rsF.getInt("IDCliente")));
                fattura.setProforma(rsF.getBoolean("Proforma"));
                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                Vector<Opera> vOpere = new Vector<Opera>();
                while (rsO.next()) {
                    String codOpera = rsO.getString("CodiceOpera");
                    vOpere.add(findOpera(codOpera));
                }
                rsO.close();
                pstmtO.close();
                fattura.setOpere(vOpere);
                vFatt.add(fattura);
            }
            rsF.close();
            pstmtF.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        Collections.sort(vFatt, defaultComparator);
        return vFatt;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param fattura la fattura da aggiornare
     * @throws exceptions.RecordNonPresenteException se la fattura non esiste
     */
    @Override
    public void updateFattura(Fattura fattura) throws RecordNonPresenteException {
        Fattura fatturaVecchia = findFattura(fattura.getNumeroFattura(), fattura.getDataFattura().getAnno());

        for (Opera operaNuova : fattura.getOpere()) {
            if (!fatturaVecchia.getOpere().contains(operaNuova)) {
                try {
                    PreparedStatement pstmt =
                            connection.prepareStatement("UPDATE Opera SET NumeroFattura = ?, AnnoFattura = ? WHERE CodiceOpera = ?");
                    pstmt.setInt(1, fattura.getNumeroFattura());
                    pstmt.setInt(2, fattura.getDataFattura().getAnno());
                    pstmt.setInt(3, operaNuova.getCodiceOpera());
                    pstmt.executeUpdate();
                    pstmt.close();
                    makePersistent();
                } catch (SQLException ex) {
                    Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }
        }

        for (Opera operaVecchia : fatturaVecchia.getOpere()) {
            if (!fattura.getOpere().contains(operaVecchia)) {
                try {
                    PreparedStatement pstmt =
                            connection.prepareStatement("UPDATE Opera SET NumeroFattura = null, AnnoFattura = null WHERE CodiceOpera = ?");
                    pstmt.setInt(1, operaVecchia.getCodiceOpera());
                    pstmt.executeUpdate();
                    makePersistent();
                    pstmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            int nFatt = fattura.getNumeroFattura();
            int annoFatt = fattura.getDataFattura().getAnno();

            if (!fatturaExists(nFatt, annoFatt)) {
                throw new RecordNonPresenteException("Fattura " + nFatt + "/" + annoFatt + " non presente in archivio");
            }
            int codCli = fattura.getCliente().getCodiceCliente();

            // Modifica del record di Fattura
            PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE Fattura SET IDCliente = ?, Sconto = ?, Totale = ?, Proforma = ? WHERE Numero = ? AND Anno = ?");
            pstmt.setInt(1, codCli);
            pstmt.setFloat(2, fattura.getSconto());
            pstmt.setFloat(3, fattura.getTotale());
            pstmt.setBoolean(4, fattura.isProforma());
            pstmt.setInt(5, nFatt);
            pstmt.setInt(6, annoFatt);
            pstmt.executeUpdate();
            makePersistent();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessFatturaDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param opera l'opera di cui trovare la fattura che la contiene
     * @return la fattura trovata
     * @throws exceptions.RecordNonPresenteException se l'opera non compare in alcuna fattura
     */
    @Override
    public Fattura selectFatturePerOpera(Opera opera) throws RecordNonPresenteException {
        Vector<Fattura> allFatture = findAllFatture();
        int codOpera = opera.getCodiceOpera();
        for (Fattura fatturaCandidata : allFatture) {
            Vector<Opera> allOpere = fatturaCandidata.getOpere();
            for (Opera o : allOpere) {
                if (o.getCodiceOpera() == codOpera) {
                    return fatturaCandidata;
                }
            }
        }
        throw new RecordNonPresenteException("L'opera non compare in alcuna fattura.");
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param cliente il cliente di cui trovare le fatture
     * @return il vettore di fatture che appartengono a tale cliente
     */
    @Override
    public Vector<Fattura> selectFatturePerCliente(Cliente cliente) {
        Vector<Fattura> vF = new Vector<Fattura>();
        int codiceCliente = cliente.getCodiceCliente();

        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Fattura WHERE IDCliente = ?");
            pstmt.setInt(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Fattura fattura = new Fattura();
                Vector<Opera> v = new Vector<Opera>();
                
                int nFatt = rs.getInt("Numero");
                int annoFatt = rs.getInt("Anno");
                fattura.setCliente(cliente);
                fattura.setSconto(rs.getFloat("Sconto"));
                fattura.setTotale(rs.getFloat("Totale"));
                fattura.setProforma(rs.getBoolean("Proforma"));
                GregorianCalendar cal = new GregorianCalendar();
                Date dataFatt = rs.getDate("Data", cal);
                cal.setTime(dataFatt);
                Data data = null;
                try {
                    data = new Data(new Date(cal.getTimeInMillis()).toString());
                } catch (BadFormatException ex) {
                    Logger.getLogger(MSAccessFatturaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura.setNumeroFattura(nFatt);
                fattura.setDataFattura(data);
                
                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    String codOpera = rsO.getString("CodiceOpera");
                    v.add(findOpera(codOpera));
                }
                rsO.close();
                pstmtO.close();
                fattura.setOpere(v);
                vF.add(fattura);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(vF, defaultComparator);
        return vF;
    }

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param artista l'artista i cui trovare le fatture in cui siano presenti sue opere
     * @return il vettore di fatture con opere dell'artista
     */
    @Override
    public Vector<Fattura> selectFatturePerArtista(Artista artista) {
        Vector<Fattura> vF = new Vector<Fattura>();
        int codiceArtista = artista.getCodiceArtista();

        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Fattura INNER JOIN Opera ON Fattura.Numero = Opera.NumeroFattura AND Fattura.Anno = Opera.AnnoFattura WHERE Opera.Artista = ?");
            pstmt.setInt(1, codiceArtista);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Vector<Opera> v = new Vector<Opera>();
                Fattura fattura = new Fattura();
                int nFatt = rs.getInt("Fattura.Numero");
                int annoFatt = rs.getInt("Fattura.Anno");
                fattura.setSconto(rs.getFloat("Sconto"));
                fattura.setTotale(rs.getFloat("Totale"));
                fattura.setProforma(rs.getBoolean("Proforma"));
                fattura.setCliente(findCliente(rs.getInt("IDCliente")));
                GregorianCalendar cal = new GregorianCalendar();
                Date dataFatt = rs.getDate("Fattura.Data", cal);
                cal.setTime(dataFatt);
                Data data = null;
                try {
                    data = new Data(new Date(cal.getTimeInMillis()).toString());
                } catch (BadFormatException ex) {
                    Logger.getLogger(MSAccessFatturaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura.setNumeroFattura(nFatt);
                fattura.setDataFattura(data);

                PreparedStatement pstmtO =
                        connection.prepareStatement("SELECT * FROM Opera WHERE NumeroFattura = ? AND AnnoFattura = ?");
                pstmtO.setInt(1, nFatt);
                pstmtO.setInt(2, annoFatt);
                ResultSet rsO = pstmtO.executeQuery();
                while (rsO.next()) {
                    String codOpera = rsO.getString("CodiceOpera");
                    v.add(findOpera(codOpera));
                }
                rsO.close();
                pstmtO.close();
                fattura.setOpere(v);
                vF.add(fattura);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        Collections.sort(vF, defaultComparator);
        return vF;
    }

    /**
     * Permette di trovare un'opera dato il suo codice.
     * @param codiceOpera il codice dell'opera
     * @return l'opera trovata (se esiste)
     */
    private Opera findOpera(String codiceOpera) {
        Opera opera = new Opera();
        int codArt = -1;
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Opera WHERE CodiceOpera = ?");
            pstmt.setString(1, codiceOpera);
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
     * Determina se una fattura, dato numero ed anno, esiste.
     * @param nFatt il numero della fattura
     * @param annoFatt l'anno della fattura
     * @return true se la fattura esiste
     */
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
            rs.close();
            pstmt.close();
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
     * Permette di trovare un artista dato il suo codice.
     * @param codiceArtista il codice dell'artista
     * @return l'artista trovato (se esiste)
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
     * Permette di trovare un cliente dato il suo codice.
     * @param codiceCliente il codice del cliente
     * @return il cliente trovato (se esiste)
     */
    private Cliente findCliente(int codiceCliente) {
        Cliente cliente = new Cliente();
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("SELECT * FROM Cliente WHERE CodiceCliente = ?");
            pstmt.setInt(1, codiceCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente.setCodiceCliente(rs.getInt("CodiceCliente"));
                cliente.setCognRsoc1(rs.getString("CognomeRsoc1"));
                cliente.setNomeRsoc2(rs.getString("NomeRsoc2"));
                cliente.setIndirizzo(rs.getString("Indirizzo"));
                cliente.setNCiv(rs.getString("NCiv"));
                cliente.setCitta(rs.getString("Citta"));
                cliente.setProvincia(rs.getString("Provincia"));
                cliente.setRegione(Regione.getRegione(rs.getString("Regione")));
                cliente.setStato(rs.getString("Stato"));
                cliente.setTel1(rs.getString("Telefono1"));
                cliente.setTel2(rs.getString("Telefono2"));
                cliente.setCell1(rs.getString("Cellulare1"));
                cliente.setCell2(rs.getString("Cellulare2"));
                cliente.setMail1(EMail.parseEMail(rs.getString("Mail1")));
                cliente.setMail2(EMail.parseEMail(rs.getString("Mail2")));
                cliente.setCfPiva(rs.getString("CFPIVA"));
                cliente.setNote(rs.getString("NoteCliente"));
                cliente.setProfessionista(rs.getBoolean("Professionista"));
                cliente.setCap(rs.getString("Cap"));
            }
            rs.close();
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

    /**
     * Implementazione del metodo definito dall'interfaccia.
     * @param fattura la fattura da rendere definitiva, da proforma
     * @throws exceptions.RecordNonPresenteException se la fattura non esiste
     */
    @Override
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
