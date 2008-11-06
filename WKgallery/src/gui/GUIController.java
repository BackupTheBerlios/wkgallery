package gui;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import abstractlayer.Regione;
import backupmanager.XMLImportExporter;
import com.lowagie.text.DocumentException;
import daoabstract.ArtistaDAO;
import daoabstract.ClienteDAO;
import daoabstract.DAOFactory;
import daoabstract.FatturaDAO;
import daoabstract.OperaDAO;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import exceptions.TitoloNonPresenteException;
import filefilter.XMLFileFilter;
import java.awt.Desktop;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import print.FatturaPdfCreator;
import utilities.Data;
import utilities.EMail;

/**
 * Rappresenta il Controller del pattern MVC.<br /><br />
 * Intercetta gli eventi segnalati dalle varie finestre e si preoccupa di aggiornare
 * la View e dialogare con le classi DAO per l'accesso all'archivio dati.<br />
 * Funziona mediante l'implementazione dell'interfaccia PropertyChangeListener:
 * ad ogni input significativo dell'utente, le varie finestre segnalano un evento
 * di tipo PropertyChange, mediante un protocollo definito appositamente.<br />
 * In base al nome dell'evento, si scatenano modifiche sulle finestre stesse e sull'archivio dati.
 * @author Marco Celesti
 */
public class GUIController implements PropertyChangeListener {

    private ArtistaDAO artistaDAO = null;
    private ClienteDAO clienteDAO = null;
    private FatturaDAO fatturaDAO = null;
    private OperaDAO operaDAO = null;
    private DAOFactory daoFactory = null;
    private static GUIController controller = null;
    private Vector<Cliente> clientiInTabella = new Vector<Cliente>();
    private Vector<Fattura> fattureInTabella = new Vector<Fattura>();
    private Vector<Opera> opereInTabella = new Vector<Opera>();
    private Vector<Artista> artistiInTabella = new Vector<Artista>();

    /**
     * Costruttore privato di GUIController
     */
    private GUIController() {
        super();
    }

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static GUIController getGUIController() {
        if (controller == null) {
            controller = new GUIController();
        }
        return controller;
    }

    /**
     * Metodo <i>set</i> per <code>daoFactory</code>.
     * Permette il recupero delle classi DAO per l'accessoa DB.
     * @param daoFactory l'istanza di DAOFactory
     */
    protected void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        artistaDAO = daoFactory.getArtistaDAO();
        clienteDAO = daoFactory.getClienteDAO();
        fatturaDAO = daoFactory.getFatturaDAO();
        operaDAO = daoFactory.getOperaDAO();
    }

    /**
     * Mostra la finestra principale e la inizializza
     */
    protected void showPrincipaleJFrame() {
        if (PrincipaleJFrame.getInstance().getPropertyChangeListeners().length < 1) {
            PrincipaleJFrame.getInstance().addPropertyChangeListener(this);
        }
        PrincipaleJFrame.getInstance().init();
        PrincipaleJFrame.getInstance().setLocation(50, 50);
        PrincipaleJFrame.getInstance().setVisible(true);
    }

    /**
     * Mostra la finestra contenente i clienti cui inviare le mail
     */
    protected void showEmailMultipleJFrame() {
        if (EmailMultipleJFrame.getInstance().getPropertyChangeListeners().length < 1) {
            EmailMultipleJFrame.getInstance().addPropertyChangeListener(this);
        }
        EmailMultipleJFrame.getInstance().init();
        EmailMultipleJFrame.getInstance().setLocation(100, 100);
        EmailMultipleJFrame.getInstance().setVisible(true);
    }

    /**
     * Mostra la finestra per inserimento/modifica/visualizzazione del cliente
     * @param cliente il cliente eventulmente da modificare o visualizzare.
     *        Se il cliente è da inserire, va passata un'istanza non inizializzata.
     */
    private void showIMVClienteJFrame(Cliente cliente) {
        boolean nuovoCliente = true;
        if (cliente.getCodiceCliente() != -1) {
            nuovoCliente = false;
        }
        if (IMVClienteJFrame.getInstance().getPropertyChangeListeners().length < 1) {
            IMVClienteJFrame.getInstance().addPropertyChangeListener(this);
        }
        IMVClienteJFrame.getInstance().setCliente(cliente);
        IMVClienteJFrame.getInstance().init(nuovoCliente);
        IMVClienteJFrame.getInstance().setLocation(100, 100);
        IMVClienteJFrame.getInstance().setVisible(true);
    }

    /**
     * Mostra la finestra per inserimento/modifica/visualizzazione dell'opera
     * @param opera l'opera eventulmente da modificare o visualizzare.
     *        Se l'opera è da inserire, va passata un'istanza non inizializzata.
     */
    private void showIMVOperaJFrame(Opera opera) {
        boolean nuovaOpera = true;
        if (opera.getCodiceOpera() != -1) {
            nuovaOpera = false;
        }
        if (IMVOperaJFrame.getInstance().getPropertyChangeListeners().length < 1) {
            IMVOperaJFrame.getInstance().addPropertyChangeListener(this);
        }
        IMVOperaJFrame.getInstance().setArtisti(artistaDAO.findAllArtisti());
        IMVOperaJFrame.getInstance().setOpera(opera);
        IMVOperaJFrame.getInstance().init(nuovaOpera);
        IMVOperaJFrame.getInstance().setLocation(100, 100);
        IMVOperaJFrame.getInstance().setVisible(true);
    }

    /**
     * Mostra la finestra per inserimento/modifica/visualizzazione della fattura
     * @param fattura la fattura eventulmente da modificare o visualizzare.
     *        Se la fattura è da inserire, va passata un'istanza non inizializzata.
     */
    private void showIMVFatturaJFrame(Fattura fattura) {
        boolean nuovaFattura = false;
        Data oggi = new Data();
        if (fattura.getNumeroFattura() == -1) {
            nuovaFattura = true;
            Vector<Fattura> fatture = fatturaDAO.findAllFatture();
            Vector<Integer> numeriUtilizzati = new Vector<Integer>();
            boolean numeroNonDisponibile = true;
            int numero = 1;
            for (Fattura fatturaEsistente : fatture) {
                if (fatturaEsistente.getDataFattura().getAnno() == oggi.getAnno()) {
                    numeriUtilizzati.add(fatturaEsistente.getNumeroFattura());
                }
            }
            while (numeroNonDisponibile) {
                if (numeriUtilizzati.contains(numero)) {
                    numero++;
                } else {
                    numeroNonDisponibile = false;
                }
            }
            fattura.setNumeroFattura(numero);
            fattura.setDataFattura(oggi);
        }
        if (IMVFatturaJFrame.getInstance().getPropertyChangeListeners().length < 1) {
            IMVFatturaJFrame.getInstance().addPropertyChangeListener(this);
        }
        IMVFatturaJFrame.getInstance().setFattura(fattura);
        IMVFatturaJFrame.getInstance().setArtisti(artistaDAO.findAllArtisti());
        IMVFatturaJFrame.getInstance().setClienti(clienteDAO.findAllClienti());
        IMVFatturaJFrame.getInstance().init(nuovaFattura);
        IMVFatturaJFrame.getInstance().updateOpereDisponibilijTable(operaDAO.findOpereDisponibili());
        IMVFatturaJFrame.getInstance().updateOpereInFatturajTable(fattura.getOpere());
        IMVFatturaJFrame.getInstance().setLocation(100, 100);
        IMVFatturaJFrame.getInstance().setVisible(true);
    }

    /**
     * Mostra la finestra per inserimento/modifica/visualizzazione dell'artista
     * @param artista l'artista eventulmente da modificare o visualizzare.
     *        Se l'artista è da inserire, va passata un'istanza non inizializzata.
     */
    private void showIMVArtistaJFrame(Artista artista) {
        boolean nuovoArtista = true;
        if (artista.getCodiceArtista() != -1) {
            nuovoArtista = false;
        }
        if (IMVArtistaJFrame.getInstance().getPropertyChangeListeners().length < 1) {
            IMVArtistaJFrame.getInstance().addPropertyChangeListener(this);
        }
        IMVArtistaJFrame.getInstance().setArtista(artista);
        IMVArtistaJFrame.getInstance().init(nuovoArtista);
        IMVArtistaJFrame.getInstance().setLocation(100, 100);
        IMVArtistaJFrame.getInstance().setVisible(true);
    }

    /**
     * Rende <code>editable</code> i campi della finestra IMVClienteJFrame
     * @param editable
     */
    private void setEditableIMVClienteJFrame(boolean editable) {
        IMVClienteJFrame.getInstance().setEditable(editable);
    }

    /**
     * Rende <code>editable</code> i campi della finestra IMVOperaJFrame
     * @param editable
     */
    private void setEditableIMVOperaJFrame(boolean editable) {
        IMVOperaJFrame.getInstance().setEditable(editable);
    }

    /**
     * Rende <code>editable</code> i campi della finestra IMVFatturaJFrame
     * @param editable
     */
    private void setEditableIMVFatturaJFrame(boolean editable) {
        IMVFatturaJFrame.getInstance().setEditable(editable);
    }

    /**
     * Rende <code>editable</code> i campi della finestra IMVArtistaJFrame
     * @param editable
     */
    private void setEditableIMVArtistaJFrame(boolean editable) {
        IMVArtistaJFrame.getInstance().setEditable(editable);
    }

    /**
     * Riporta la finestra principale allo stato iniziale
     */
    private void resetPrincipaleJFrame() {
        clearPrincipaleJFrame();

        clientiInTabella.addAll(clienteDAO.findAllClienti());
        fattureInTabella.addAll(fatturaDAO.findAllFatture());
        opereInTabella.addAll(operaDAO.findAllOpere());
        artistiInTabella.addAll(artistaDAO.findAllArtisti());

        PrincipaleJFrame.getInstance().updateClientijTable(clientiInTabella);
        PrincipaleJFrame.getInstance().updateFatturejTable(fattureInTabella);
        PrincipaleJFrame.getInstance().updateOperejTable(opereInTabella);
        PrincipaleJFrame.getInstance().updateArtistijTable(artistiInTabella);
    }

    /**
     * Pulisce le tabelle della finestra principale e i messagi
     */
    private void clearPrincipaleJFrame() {
        clientiInTabella.clear();
        fattureInTabella.clear();
        opereInTabella.clear();
        artistiInTabella.clear();

        PrincipaleJFrame.getInstance().setArtistiMessage("");
        PrincipaleJFrame.getInstance().setClientiMessage("");
        PrincipaleJFrame.getInstance().setFattureMessage("");
        PrincipaleJFrame.getInstance().setOpereMessage("");
    }

    /**
     * Sovrascrive il metodo definito dall'interfaccia.<br />
     * Intercetta gli eventi lanciati dalle finestre.<br />
     * Eventi ascoltati:
     * <ul>
     * <li>"reset": riporta le tabelle del pannello principale allo stato iniziale</li>
     * <li>modifica - l'istanza eventualmente da modificare è recuperata dalla finestra principale
     *  <ul>
     *  <li>"modifica_cliente": modifica o inserisce un cliente</li>
     *  <li>"modifica_artista": modifica o inserisce un artista</li>
     *  <li>"modifica_opera": modifica o inserisce un'opera</li>
     *  <li>"modifica_fattura": modifica o inserisce una fattura</li>
     *  </ul>
     * </li>
     * <li>salva - l'istanza da salvare è recuperata dalle finestre di IMV
     *  <ul>
     *  <li>"salva_cliente": salva il cliente</li>
     *  <li>"salva_artista": salva l'artista</li>
     *  <li>"salva_opera": salva l'opera</li>
     *  <li>"salva_fattura": salva la fattura</li>
     *  </ul>
     * </li>
     * <li>cancella - l'istanza da cancellare è recuperata dalla finestra principale
     *  <ul>
     *  <li>"cancella_cliente": cancella il cliente</li>
     *  <li>"cancella_artista": cancella l'artista</li>
     *  <li>"cancella_opera": cancella l'opera</li>
     *  <li>"cancella_fattura": cancella la fattura</li>
     *  </ul>
     * </li>
     * <li>visualizza - l'istanza da visualizzare è recuperata dalla finestra principale
     *  <ul>
     *  <li>"visualizza_cliente": visualizza il cliente</li>
     *  <li>"visualizza_artista": visualizza l'artista</li>
     *  <li>"visualizza_opera": visualizza l'opera</li>
     *  <li>"visualizza_fattura": visualizza la fattura</li>
     *  </ul>
     * </li>
     * <li>"clienti_selezionati": a cui inviare un'email</li>
     * <li>invia_email - al singolo od a più clienti
     *  <ul>
     *  <li>"invia_email_singola": l'istanza del cliente cui inviare la mail è recuperata dalla finestra principale</li>
     *  <li>"invia_email_multipla": mostra la finestra per la scelta dei clienti cui inviare la mail</li>
     *  </ul>
     * </li>
     * <li>cerca - i criteri di ricerca sono recuperati dalla finestra principale
     *  <ul>
     *  <li>"cerca_cliente": cerca un cliente per codice o per cognome</li>
     *  <li>"cerca_cliente_per regioni": cerca clienti per regioni di appartenenza</li>
     *  <li>"cerca_artista": cerca un artista per codice</li>
     *  <li>"cerca_opera": cerca un'opera per codice</li>
     *  <li>"cerca_fattura": cerca una fattura per numero ed anno</li>
     *  </ul>
     * </li>
     * <li>filtro - l'istanza su cui basare il fitro è recuperata dalla finestra principale
     *  <ul>
     *  <li>"filtro_cliente": cerca le istanze correlate al cliente</li>
     *  <li>"filtro_artista": cerca le istanze correlate all'artista</li>
     *  <li>"filtro_opera": cerca le istanze correlate all'opera</li>
     *  <li>"filtro_fattura": cerca le istanze correlate alla fattura</li>
     *  </ul>
     * </li>
     * <li>fattura - eventi sollevati all'interno della finestra IMVFatturaJFrame
     *  <ul>
     *  <li>"fattura_aggiungi_opera": aggiunge l'opera da movimentare nella fattura</li>
     *  <li>"fattura_rimuovi_opera": rimuove l'opera da movimentare dalla fattura</li>
     *  <li>"fattura_cerca_opere_per_artista": aggiorna la tabella con le opere per l'artista specificato</li>
     *  <li>"fattura_reset": azzera la ricerca per artista</li>
     *  <li>"fattura_stampa": salva e stampa la fattura</li>
     *  </ul>
     * </li>
     * </ul>
     * @param evt l'evento sollevato
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("reset")) {
            resetPrincipaleJFrame();

        } else if (evt.getPropertyName().startsWith("modifica")) {
            if (evt.getPropertyName().equals("modifica_cliente")) {
                Cliente cliente = PrincipaleJFrame.getInstance().getCliente();
                int codiceCliente = cliente.getCodiceCliente();
                if (codiceCliente != -1) { //cliente da aggiornare
                    try {
                        cliente = clienteDAO.findCliente(codiceCliente);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                showIMVClienteJFrame(cliente);
                setEditableIMVClienteJFrame(true);

            } else if (evt.getPropertyName().equals("modifica_artista")) {
                Artista artista = PrincipaleJFrame.getInstance().getArtista();
                int codiceArtista = artista.getCodiceArtista();
                if (codiceArtista != -1) { //artista da aggiornare
                    try {
                        artista = artistaDAO.findArtista(codiceArtista);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                showIMVArtistaJFrame(artista);
                setEditableIMVArtistaJFrame(true);

            } else if (evt.getPropertyName().equals("modifica_opera")) {
                if (artistaDAO.findAllArtisti().size() > 0) {
                    Opera opera = PrincipaleJFrame.getInstance().getOpera();
                    int codiceOpera = opera.getCodiceOpera();
                    boolean operaModificabile = true;
                    Fattura fatturaContenenteLOpera = new Fattura();
                    if (codiceOpera != -1) { //opera da aggiornare
                        try {
                            opera = operaDAO.findOpera(codiceOpera);
                            try {
                                fatturaContenenteLOpera = fatturaDAO.selectFatturePerOpera(opera);
                                if (!fatturaContenenteLOpera.isProforma()) {
                                    operaModificabile = false;
                                }
                            } catch (RecordNonPresenteException ex) {
                            }
                        } catch (RecordNonPresenteException ex) {
                            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (operaModificabile) {
                        showIMVOperaJFrame(opera);
                        setEditableIMVOperaJFrame(true);
                    } else {
                        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Non è possibile modificare l'opera selezionata in quanto\nrisulta inserita nella fattura " + fatturaContenenteLOpera.toString() + " già emessa in data " + fatturaContenenteLOpera.getDataFattura().toStringIt(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Non sono presenti Artisti in archivio.\nPrima di inserire un'opera si prega di inserirne l'autore.", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            } else if (evt.getPropertyName().equals("modifica_fattura")) {
                if (artistaDAO.findAllArtisti().size() > 0 && clienteDAO.findAllClienti().size() > 0 && operaDAO.findAllOpere().size() > 0) {
                    Fattura fattura = PrincipaleJFrame.getInstance().getFattura();
                    int numeroFattura = fattura.getNumeroFattura();
                    if (numeroFattura != -1) { //fattura da aggiornare
                        try {
                            fattura = fatturaDAO.findFattura(numeroFattura, fattura.getDataFattura().getAnno());
                        } catch (RecordNonPresenteException ex) {
                            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (fattura.isProforma()) {
                        showIMVFatturaJFrame(fattura);
                        setEditableIMVFatturaJFrame(true);
                    } else {
                        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Non è possibile modificare una fattura già emessa in maniera definitiva.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Non sono presenti Artisti, Clienti oppure Opere in archivio.\nSi prega di inserire il cliente, l'artista e le opere richieste prima di inserire la fattura relativa.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (evt.getPropertyName().startsWith("salva")) {
            if (evt.getPropertyName().equals("salva_cliente")) {
                Cliente cliente = IMVClienteJFrame.getInstance().getCliente();
                IMVClienteJFrame.getInstance().dispose();
                if (cliente.getCodiceCliente() != -1) { //cliente già esistente
                    try {
                        clienteDAO.updateCliente(cliente);
                        int index = clientiInTabella.indexOf(cliente);
                        clientiInTabella.removeElementAt(index);
                        clientiInTabella.add(index, cliente);
                        PrincipaleJFrame.getInstance().updateClientijTable(clientiInTabella);
                        PrincipaleJFrame.getInstance().updateFatturejTable(fattureInTabella);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { //cliente nuovo
                    try {
                        clienteDAO.insertCliente(cliente);
                        resetPrincipaleJFrame();
                    } catch (RecordGiaPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ChiavePrimariaException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else if (evt.getPropertyName().equals("salva_artista")) {
                Artista artista = IMVArtistaJFrame.getInstance().getArtista();
                IMVArtistaJFrame.getInstance().dispose();
                if (artista.getCodiceArtista() != -1) { //artista già esistente
                    try {
                        artistaDAO.updateArtista(artista);
                        int index = artistiInTabella.indexOf(artista);
                        artistiInTabella.removeElementAt(index);
                        artistiInTabella.add(index, artista);
                        PrincipaleJFrame.getInstance().updateArtistijTable(artistiInTabella);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { //artista nuovo
                    try {
                        artistaDAO.insertArtista(artista);
                        resetPrincipaleJFrame();
                    } catch (RecordGiaPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ChiavePrimariaException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else if (evt.getPropertyName().equals("salva_opera")) {
                Opera opera = IMVOperaJFrame.getInstance().getOpera();
                IMVOperaJFrame.getInstance().dispose();
                if (opera.getCodiceOpera() != -1) { //opera già esistente
                    try {
                        operaDAO.updateOpera(opera);
                        int index = opereInTabella.indexOf(opera);
                        opereInTabella.removeElementAt(index);
                        opereInTabella.add(index, opera);
                        PrincipaleJFrame.getInstance().updateOperejTable(opereInTabella);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { //opera nuova
                    try {
                        operaDAO.insertOpera(opera);
                        resetPrincipaleJFrame();
                    } catch (RecordGiaPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ChiavePrimariaException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else if (evt.getPropertyName().equals("salva_fattura")) {
                Fattura fattura = IMVFatturaJFrame.getInstance().getFattura();
                IMVFatturaJFrame.getInstance().dispose();
                try {
                    fatturaDAO.updateFattura(fattura);
                    int index = fattureInTabella.indexOf(fattura);
                    if (index != -1) { //fattura già presente in tabella
                        fattureInTabella.removeElementAt(index);
                        fattureInTabella.add(index, fattura);
                    } else {
                        fattureInTabella.add(fattura);
                    }
                    PrincipaleJFrame.getInstance().updateFatturejTable(fattureInTabella);
                } catch (RecordNonPresenteException ex) {
                    try {
                        fatturaDAO.insertFattura(fattura);
                        resetPrincipaleJFrame();
                    } catch (RecordGiaPresenteException rgpe) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, rgpe);
                    } catch (ChiavePrimariaException cpe) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, cpe);
                    }
                }
            }
        } else if (evt.getPropertyName().startsWith("cancella")) {
            if (evt.getPropertyName().equals("cancella_cliente")) {
                Cliente cliente = PrincipaleJFrame.getInstance().getCliente();
                int returnVal = JOptionPane.showConfirmDialog(PrincipaleJFrame.getInstance(), "Cancellare il cliente '" + cliente.toString() + "'?", "Attenzione", JOptionPane.OK_CANCEL_OPTION);
                if (returnVal == JOptionPane.OK_OPTION) {
                    int codiceCliente = cliente.getCodiceCliente();
                    try {
                        clienteDAO.deleteCliente(codiceCliente);
                        clientiInTabella.clear();
                        clientiInTabella.addAll(clienteDAO.findAllClienti());
                        PrincipaleJFrame.getInstance().updateClientijTable(clientiInTabella);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RecordCorrelatoException ex) {
                        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Impossibile portare a termine l'operazione richiesta\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Azione annullata", "", JOptionPane.INFORMATION_MESSAGE);
                }

            } else if (evt.getPropertyName().equals("cancella_artista")) {
                Artista artista = PrincipaleJFrame.getInstance().getArtista();
                int returnVal = JOptionPane.showConfirmDialog(PrincipaleJFrame.getInstance(), "Cancellare l'artista '" + artista.toString() + "'?", "Attenzione", JOptionPane.OK_CANCEL_OPTION);
                if (returnVal == JOptionPane.OK_OPTION) {
                    int codiceArtista = artista.getCodiceArtista();
                    try {
                        artistaDAO.deleteArtista(codiceArtista);
                        artistiInTabella.clear();
                        artistiInTabella.addAll(artistaDAO.findAllArtisti());
                        PrincipaleJFrame.getInstance().updateArtistijTable(artistiInTabella);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RecordCorrelatoException ex) {
                        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Impossibile portare a termine l'operazione richiesta\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Azione annullata", "", JOptionPane.INFORMATION_MESSAGE);
                }

            } else if (evt.getPropertyName().equals("cancella_opera")) {
                Opera opera = PrincipaleJFrame.getInstance().getOpera();
                int returnVal = JOptionPane.showConfirmDialog(PrincipaleJFrame.getInstance(), "Cancellare l'opera '" + opera.getTitolo() + "'?", "Attenzione", JOptionPane.OK_CANCEL_OPTION);
                if (returnVal == JOptionPane.OK_OPTION) {
                    int codiceOpera = opera.getCodiceOpera();
                    try {
                        operaDAO.deleteOpera(codiceOpera);
                        opereInTabella.clear();
                        opereInTabella.addAll(operaDAO.findAllOpere());
                        PrincipaleJFrame.getInstance().updateOperejTable(opereInTabella);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RecordCorrelatoException ex) {
                        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Impossibile portare a termine l'operazione richiesta\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Azione annullata", "", JOptionPane.INFORMATION_MESSAGE);
                }

            } else if (evt.getPropertyName().equals("cancella_fattura")) {
                Fattura fattura = PrincipaleJFrame.getInstance().getFattura();
                int returnVal = JOptionPane.showConfirmDialog(PrincipaleJFrame.getInstance(), "Cancellare l'opera '" + fattura.toString() + "'?", "Attenzione", JOptionPane.OK_CANCEL_OPTION);
                if (returnVal == JOptionPane.OK_OPTION) {
                    int numeroFattura = fattura.getNumeroFattura();
                    int annoFattura = fattura.getDataFattura().getAnno();
                    try {
                        fatturaDAO.deleteFattura(numeroFattura, annoFattura);
                        fattureInTabella.clear();
                        fattureInTabella.addAll(fatturaDAO.findAllFatture());
                        PrincipaleJFrame.getInstance().updateFatturejTable(fattureInTabella);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Azione annullata", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } else if (evt.getPropertyName().startsWith("visualizza")) {
            if (evt.getPropertyName().equals("visualizza_cliente")) {
                Cliente cliente = PrincipaleJFrame.getInstance().getCliente();
                int codiceCliente = cliente.getCodiceCliente();
                if (codiceCliente != -1) {
                    try {
                        cliente = clienteDAO.findCliente(codiceCliente);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                showIMVClienteJFrame(cliente);
                setEditableIMVClienteJFrame(false);

            } else if (evt.getPropertyName().equals("visualizza_artista")) {
                Artista artista = PrincipaleJFrame.getInstance().getArtista();
                int codiceArtista = artista.getCodiceArtista();
                if (codiceArtista != -1) { //artista da aggiornare
                    try {
                        artista = artistaDAO.findArtista(codiceArtista);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                showIMVArtistaJFrame(artista);
                setEditableIMVArtistaJFrame(false);

            } else if (evt.getPropertyName().equals("visualizza_opera")) {
                Opera opera = PrincipaleJFrame.getInstance().getOpera();
                int codiceOpera = opera.getCodiceOpera();
                if (codiceOpera != -1) { //opera da aggiornare
                    try {
                        opera = operaDAO.findOpera(codiceOpera);
                    } catch (RecordNonPresenteException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                showIMVOperaJFrame(opera);
                setEditableIMVOperaJFrame(false);

            } else if (evt.getPropertyName().equals("visualizza_fattura")) {
                Fattura fattura = PrincipaleJFrame.getInstance().getFattura();
                try {
                    fattura = fatturaDAO.findFattura(fattura.getNumeroFattura(), fattura.getDataFattura().getAnno());
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                showIMVFatturaJFrame(fattura);
                setEditableIMVFatturaJFrame(false);
            }

        } else if (evt.getPropertyName().equals("clienti_selezionati")) {
            Vector<Cliente> clientiselezionati = EmailMultipleJFrame.getInstance().getClientiSelezionati();
            EmailMultipleJFrame.getInstance().dispose();
            sendEmail(clientiselezionati);

        } else if (evt.getPropertyName().startsWith("invia_email")) {
            if (evt.getPropertyName().equals("invia_email_singola")) {
                Cliente cliente = PrincipaleJFrame.getInstance().getCliente();
                int codiceCliente = cliente.getCodiceCliente();
                try {
                    cliente = clienteDAO.findCliente(codiceCliente);
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Vector<Cliente> singoloCliente = new Vector<Cliente>();
                singoloCliente.add(cliente);
                sendEmail(singoloCliente);

            } else if (evt.getPropertyName().equals("invia_email_multipla")) {
                showEmailMultipleJFrame();
                EmailMultipleJFrame.getInstance().updateClientijTable(clientiInTabella);
            }

        } else if (evt.getPropertyName().startsWith("cerca")) {
            if (evt.getPropertyName().equals("cerca_cliente")) {
                Cliente cliente = PrincipaleJFrame.getInstance().getCliente();
                int codiceCliente = cliente.getCodiceCliente();
                String cognome = cliente.getCognRsoc1();
                if (codiceCliente != -1) {
                    try {
                        cliente = clienteDAO.findCliente(codiceCliente);
                        clientiInTabella.clear();
                        clientiInTabella.add(cliente);
                        PrincipaleJFrame.getInstance().updateClientijTable(clientiInTabella);
                        PrincipaleJFrame.getInstance().setClientiMessage("Ricerca cliente con codice: " + codiceCliente);
                    } catch (RecordNonPresenteException ex) {
                        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (!cognome.isEmpty()) {
                    Vector<Cliente> tmpVector = new Vector<Cliente>();
                    tmpVector.addAll(clienteDAO.selectClientiPerStringa(cognome));
                    if (tmpVector.size() > 0) {
                        clientiInTabella.clear();
                        clientiInTabella.addAll(tmpVector);
                        PrincipaleJFrame.getInstance().updateClientijTable(clientiInTabella);
                        PrincipaleJFrame.getInstance().setClientiMessage("Ricerca cliente con cognome che inizia per: " + cognome.toUpperCase());
                    } else {
                        resetPrincipaleJFrame();
                        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Nessun risultato trovato", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } else if (evt.getPropertyName().equals("cerca_cliente_per_regioni")) {
                Vector<Regione> regioni = PrincipaleJFrame.getInstance().getRegioni();
                Vector<Cliente> tmpVector = new Vector<Cliente>();
                tmpVector.addAll(clienteDAO.selectClientiPerRegione(regioni));
                if (tmpVector.size() > 0) {
                    clientiInTabella.clear();
                    clientiInTabella.addAll(tmpVector);
                    PrincipaleJFrame.getInstance().updateClientijTable(clientiInTabella);
                    PrincipaleJFrame.getInstance().setClientiMessage("Ricerca cliente per regione/i");
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Nessun risultato trovato", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            } else if (evt.getPropertyName().equals("cerca_fattura")) {
                Fattura fattura = PrincipaleJFrame.getInstance().getFattura();
                int numeroFattura = fattura.getNumeroFattura();
                int annoFattura = fattura.getDataFattura().getAnno();
                try {
                    fattura = fatturaDAO.findFattura(numeroFattura, annoFattura);
                    fattureInTabella.clear();
                    fattureInTabella.add(fattura);
                    PrincipaleJFrame.getInstance().updateFatturejTable(fattureInTabella);
                    PrincipaleJFrame.getInstance().setFattureMessage("Ricerca fattura: " + fattura.toString());
                } catch (RecordNonPresenteException ex) {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            } else if (evt.getPropertyName().equals("cerca_opera")) {
                Opera opera = PrincipaleJFrame.getInstance().getOpera();
                int codiceOpera = opera.getCodiceOpera();
                try {
                    opera = operaDAO.findOpera(codiceOpera);
                    opereInTabella.clear();
                    opereInTabella.add(opera);
                    PrincipaleJFrame.getInstance().updateOperejTable(opereInTabella);
                    PrincipaleJFrame.getInstance().setOpereMessage("Ricerca opera con codice: " + codiceOpera);
                } catch (RecordNonPresenteException ex) {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            } else if (evt.getPropertyName().equals("cerca_artista")) {
                Artista artista = PrincipaleJFrame.getInstance().getArtista();
                int codiceArtista = artista.getCodiceArtista();
                try {
                    artista = artistaDAO.findArtista(codiceArtista);
                    artistiInTabella.clear();
                    artistiInTabella.add(artista);
                    PrincipaleJFrame.getInstance().updateArtistijTable(artistiInTabella);
                    PrincipaleJFrame.getInstance().setArtistiMessage("Ricerca artista con codice : " + codiceArtista);
                } catch (RecordNonPresenteException ex) {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (evt.getPropertyName().startsWith("filtro")) {
            String messageClienti = "";
            String messageFatture = "";
            String messageOpere = "";
            String messageArtisti = "";
            clearPrincipaleJFrame();

            if (evt.getPropertyName().equals("filtro_cliente")) {
                Cliente cliente = PrincipaleJFrame.getInstance().getCliente();
                int codiceCliente = cliente.getCodiceCliente();
                try {
                    cliente = clienteDAO.findCliente(codiceCliente);
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                messageClienti = "Cliente: " + cliente.toString().toUpperCase();
                messageFatture = "Fatture del cliente " + cliente.toString().toUpperCase();
                messageOpere = "Opere acquistate del cliente " + cliente.toString().toUpperCase();
                messageArtisti = "Artisti le cui opere sono state acquistate dal cliente " + cliente.toString().toUpperCase();
                //Aggiungo il cliente
                clientiInTabella.add(cliente);
                //Aggiungo le fatture se presenti
                fattureInTabella.addAll(fatturaDAO.selectFatturePerCliente(cliente));
                for (Fattura fattura : fattureInTabella) {
                    //Per ogni fattura aggiungo le opere
                    opereInTabella.addAll(fattura.getOpere());
                }
                for (Opera opera : opereInTabella) {
                    //Per ogni opera aggiungo l'artista se non già presente
                    if (!artistiInTabella.contains(opera.getArtista())) {
                        artistiInTabella.add(opera.getArtista());
                    }
                }

            } else if (evt.getPropertyName().equals("filtro_artista")) {
                Artista artista = PrincipaleJFrame.getInstance().getArtista();
                int codiceArtista = artista.getCodiceArtista();
                try {
                    artista = artistaDAO.findArtista(codiceArtista);
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                messageClienti = "Clienti che hanno acquistato opere dell'artista " + artista.toString().toUpperCase();
                messageFatture = "Fatture contenenti opere dell'artista " + artista.toString().toUpperCase();
                messageOpere = "Opere dell'artista " + artista.toString().toUpperCase();
                messageArtisti = "Artista: " + artista.toString().toUpperCase();
                //Aggiungo l'artista
                artistiInTabella.add(artista);
                //Aggiungo le opere
                opereInTabella.addAll(operaDAO.selectOperePerArtista(artista));
                for (Opera opera : opereInTabella) {
                    //Per ogni opera aggiungo la fattura relativa se esiste e se non già presente
                    Fattura fatturaConOpera;
                    try {
                        fatturaConOpera = fatturaDAO.selectFatturePerOpera(opera);
                        if (!fattureInTabella.contains(fatturaConOpera)) {
                            fattureInTabella.add(fatturaConOpera);
                            //Aggiungo il cliente se non già presente
                            Cliente clienteDellaFattura = fatturaConOpera.getCliente();
                            if (!clientiInTabella.contains(clienteDellaFattura)) {
                                clientiInTabella.add(clienteDellaFattura);
                            }
                        }
                    } catch (RecordNonPresenteException ex) {
                    }
                }

            } else if (evt.getPropertyName().equals("filtro_opera")) {
                Opera opera = PrincipaleJFrame.getInstance().getOpera();
                int codiceOpera = opera.getCodiceOpera();
                try {
                    opera = operaDAO.findOpera(codiceOpera);
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                messageClienti = "Cliente che ha acquistato l'opera " + opera.getTitolo().toUpperCase();
                messageFatture = "Fattura contenente l'opera " + opera.getTitolo().toUpperCase();
                messageOpere = "Opera: " + opera.getTitolo().toUpperCase();
                messageArtisti = "Artista che ha realizzato l'opera " + opera.getTitolo().toUpperCase();
                //Aggiungo l'opera
                opereInTabella.add(opera);
                //Aggiungo l'artista
                artistiInTabella.add(opera.getArtista());
                Fattura fatturaConOpera;
                try {
                    //Aggiungo la fattura se esiste
                    fatturaConOpera = fatturaDAO.selectFatturePerOpera(opera);
                    fattureInTabella.add(fatturaConOpera);
                    //Aggiungo il cliente
                    clientiInTabella.add(fatturaConOpera.getCliente());
                } catch (RecordNonPresenteException ex) {
                }

            } else if (evt.getPropertyName().equals("filtro_fattura")) {
                Fattura fattura = PrincipaleJFrame.getInstance().getFattura();
                try {
                    fattura = fatturaDAO.findFattura(fattura.getNumeroFattura(), fattura.getDataFattura().getAnno());
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                messageClienti = "Cliente intestatario della fattura " + fattura.toString();
                messageFatture = "Fattura: " + fattura.toString();
                messageOpere = "Opere contenute nella fattura " + fattura.toString();
                messageArtisti = "Artisti le cui opere sono presenti nella fattura " + fattura.toString();
                //Aggiungo la fattura
                fattureInTabella.add(fattura);
                //Aggiungo il cliente
                clientiInTabella.add(fattura.getCliente());
                //Aggiungo le opere
                opereInTabella.addAll(fattura.getOpere());
                for (Opera opera : opereInTabella) {
                    //Per ogni opera aggiungo l'artista se non già presente
                    Artista artista = opera.getArtista();
                    if (!artistiInTabella.contains(artista)) {
                        artistiInTabella.add(artista);
                    }
                }
            }

            PrincipaleJFrame.getInstance().updateArtistijTable(artistiInTabella);
            PrincipaleJFrame.getInstance().updateClientijTable(clientiInTabella);
            PrincipaleJFrame.getInstance().updateFatturejTable(fattureInTabella);
            PrincipaleJFrame.getInstance().updateOperejTable(opereInTabella);
            PrincipaleJFrame.getInstance().setArtistiMessage(messageArtisti);
            PrincipaleJFrame.getInstance().setClientiMessage(messageClienti);
            PrincipaleJFrame.getInstance().setFattureMessage(messageFatture);
            PrincipaleJFrame.getInstance().setOpereMessage(messageOpere);

        } else if (evt.getPropertyName().startsWith("fattura")) {
            if (evt.getPropertyName().startsWith("fattura_aggiungi_opera")) {
                Opera opera = IMVFatturaJFrame.getInstance().getOperaDaMovimentare();
                int codiceOpera = opera.getCodiceOpera();
                try {
                    opera = operaDAO.findOpera(codiceOpera);
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                boolean prezzoInserito = true;
                boolean operazioneAnnullata = false;

                if (opera.getPrezzo() <= 0) {
                    do {
                        String input = JOptionPane.showInputDialog(IMVFatturaJFrame.getInstance(), "Per l'opera selezionata non è specificato il prezzo.\nSpecificarlo ora:\n", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        if (input != null) {
                            try {
                                float prezzo = Float.parseFloat(input);
                                opera.setPrezzo(prezzo);
                                prezzoInserito = true;
                                try {
                                    operaDAO.updateOpera(opera);
                                } catch (RecordNonPresenteException ex) {
                                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (NumberFormatException nfe) {
                                prezzoInserito = false;
                                JOptionPane.showMessageDialog(IMVFatturaJFrame.getInstance(), "Valore non valido", "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            operazioneAnnullata = true;
                            prezzoInserito = false;
                            JOptionPane.showMessageDialog(IMVFatturaJFrame.getInstance(), "Operazione annullata", "Informazione", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } while (!prezzoInserito && !operazioneAnnullata);
                }

                if (prezzoInserito) {
                    Fattura fattura = IMVFatturaJFrame.getInstance().getFattura();
                    fattura.addOpera(opera);
                    try {
                        fatturaDAO.updateFattura(fattura);
                    } catch (RecordNonPresenteException ex) {
                        try {
                            fatturaDAO.insertFattura(fattura);
                        } catch (RecordGiaPresenteException rgpe) {
                            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, rgpe);
                        } catch (ChiavePrimariaException cpe) {
                            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, cpe);
                        }
                    }
                    IMVFatturaJFrame.getInstance().setFattura(fattura);
                    IMVFatturaJFrame.getInstance().updateOpereDisponibilijTable(operaDAO.findOpereDisponibili());
                    IMVFatturaJFrame.getInstance().updateOpereInFatturajTable(fattura.getOpere());
                }

            } else if (evt.getPropertyName().equals("fattura_rimuovi_opera")) {
                Opera opera = IMVFatturaJFrame.getInstance().getOperaDaMovimentare();
                int codiceOpera = opera.getCodiceOpera();
                try {
                    opera = operaDAO.findOpera(codiceOpera);
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Fattura fattura = IMVFatturaJFrame.getInstance().getFattura();
                fattura.removeOpera(opera);
                try {
                    fatturaDAO.updateFattura(fattura);
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                IMVFatturaJFrame.getInstance().setFattura(fattura);
                IMVFatturaJFrame.getInstance().updateOpereDisponibilijTable(operaDAO.findOpereDisponibili());
                IMVFatturaJFrame.getInstance().updateOpereInFatturajTable(fattura.getOpere());

            } else if (evt.getPropertyName().equals("fattura_cerca_opere_per_artista")) {
                Artista artistaFiltro = IMVFatturaJFrame.getInstance().getArtistaFiltro();
                Vector<Opera> operePerArtista = new Vector<Opera>();
                Vector<Opera> opereDisponibili = new Vector<Opera>();
                operePerArtista.addAll(operaDAO.selectOperePerArtista(artistaFiltro));
                opereDisponibili.addAll(operaDAO.findOpereDisponibili());
                for (Opera operaDellArtista : operePerArtista) {
                    if (!opereDisponibili.contains(operaDellArtista)) {
                        operePerArtista.remove(operaDellArtista);
                    }
                }
                IMVFatturaJFrame.getInstance().updateOpereDisponibilijTable(operePerArtista);

            } else if (evt.getPropertyName().equals("fattura_reset")) {
                IMVFatturaJFrame.getInstance().updateOpereDisponibilijTable(operaDAO.findOpereDisponibili());

            } else if (evt.getPropertyName().equals("fattura_stampa")) {
                //Salvataggio
                Fattura fattura = IMVFatturaJFrame.getInstance().getFattura();
                IMVFatturaJFrame.getInstance().dispose();
                try {
                    fatturaDAO.updateFattura(fattura);
                    int index = fattureInTabella.indexOf(fattura);
                    if (index != -1) { //fattura già presente in tabella
                        fattureInTabella.removeElementAt(index);
                        fattureInTabella.add(index, fattura);
                    } else {
                        fattureInTabella.add(fattura);
                    }
                    PrincipaleJFrame.getInstance().updateFatturejTable(fattureInTabella);
                } catch (RecordNonPresenteException ex) {
                    try {
                        fatturaDAO.insertFattura(fattura);
                        resetPrincipaleJFrame();
                    } catch (RecordGiaPresenteException rgpe) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, rgpe);
                    } catch (ChiavePrimariaException cpe) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, cpe);
                    }
                }

                //Stampa
                Object[] possibilita = {"Carta di credito", "Contanti", "Assegno", "Bonifico bancario", "Contrassegno"};
                String pagamento = (String) JOptionPane.showInputDialog(IMVFatturaJFrame.getInstance(), "Selezionare il tipo di pagamento", "Tipo di pagamento", JOptionPane.QUESTION_MESSAGE, null, possibilita, possibilita[0]);

                JFileChooser destChooser = new JFileChooser();
                File destDir = null;
                File current = null;
                try {
                    current = new File(new File(".").getCanonicalPath());
                    destChooser.setCurrentDirectory(current);
                } catch (IOException e) {
                }
                destChooser.setDialogTitle("Seleziona la destinazione");
                destChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = destChooser.showOpenDialog(IMVFatturaJFrame.getInstance());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    destDir = destChooser.getSelectedFile();
                } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                    destDir = current;
                }
                FatturaPdfCreator pdfCreator = null;
                File filePdf = null;
                try {
                    pdfCreator = new FatturaPdfCreator(fattura, pagamento, destDir);
                } catch (TitoloNonPresenteException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    filePdf = pdfCreator.generateFattura();
                } catch (DocumentException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    try {
                        Desktop.getDesktop().open(filePdf);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Cartella non valida", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Operazione non supportata", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } else if (evt.getPropertyName().equals("crea_bkp")) {
            File destDir = null;
            try {
                destDir = new File(new File(".").getCanonicalPath() + "\\backup");
            } catch (IOException ex) {
            }
            XMLImportExporter exporter = new XMLImportExporter(artistaDAO, clienteDAO, operaDAO, fatturaDAO);
            File fileBkp = exporter.creaBkp(destDir);
            int returnValOpen = JOptionPane.showConfirmDialog(PrincipaleJFrame.getInstance(), "E' stato creato il file " + fileBkp.getName() + ".\nSi desidera visualizzarlo?", "File creato", JOptionPane.OK_CANCEL_OPTION);

            if (returnValOpen == JOptionPane.OK_OPTION) {
                if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    try {
                        Desktop.getDesktop().open(fileBkp);
                    } catch (IOException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Operazione non supportata", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (evt.getPropertyName().equals("ripristina_bkp")) {
            JFileChooser sourceChooser = new JFileChooser();
            File sourceFile = null;
            File current = null;
            try {
                current = new File(new File(".").getCanonicalPath() + "\\backup");
                sourceChooser.setCurrentDirectory(current);
            } catch (IOException e) {
            }
            sourceChooser.setDialogTitle("Seleziona il file di ripristino");
            sourceChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            sourceChooser.setFileFilter(new XMLFileFilter());
            int returnValSource = sourceChooser.showOpenDialog(IMVFatturaJFrame.getInstance());
            if (returnValSource == JFileChooser.APPROVE_OPTION) {
                sourceFile = sourceChooser.getSelectedFile();
                XMLImportExporter importer = new XMLImportExporter(artistaDAO, clienteDAO, operaDAO, fatturaDAO);
                try {
                    importer.ripristinaBkp(sourceFile);
                    resetPrincipaleJFrame();
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Il salvataggio è stato correttamente ripristinato", "Informazione", JOptionPane.INFORMATION_MESSAGE);
                } catch (ParserConfigurationException ex) {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Il ripristino non è andato a buon fine:\n" + ex, "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (SAXException ex) {
                    JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Il ripristino non è andato a buon fine:\n" + ex, "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }

    /**
     * Lancia il programma di posta predefinito inserendo l'elenco dei clienti
     * passato come destinatari
     * @param clienti i clienti cui mandare una mail
     */
    private void sendEmail(Vector<Cliente> clienti) {
        if (Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
            String uriString = "mailto:";
            for (Cliente cliente : clienti) {
                EMail mail1 = cliente.getMail1();
                EMail mail2 = cliente.getMail2();
                boolean mailFound = true;

                if (!mail1.isEmpty()) {
                    uriString += mail1 + ",";
                }
                if (!mail2.isEmpty()) {
                    uriString += mail2 + ",";
                }
            }
            if (!uriString.substring(7).isEmpty()) {
                try {
                    uriString = uriString.substring(0, uriString.length() - 1); //elimina l'ultimo spazio
                    uriString += "?SUBJECT=Comunicazione";
                    URI mailtoURI = new URI(uriString);
                    try {
                        Desktop.getDesktop().mail(mailtoURI);
                    } catch (IOException ex) {
                        Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (URISyntaxException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Nessun indirizzo email trovato", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Operazione non supportata", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
