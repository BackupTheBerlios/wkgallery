package gui;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import abstractlayer.Regione;
import exceptions.BadFormatException;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultSingleSelectionModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import tablemodels.ArtistiTableModel;
import tablemodels.ClientiTableModel;
import tablemodels.FattureTableModel;
import tablemodels.OpereTableModel;
import utilities.Data;

/**
 * Finestra principale contenente gli elenchi di Clienti, Artisti, Oper. e Fatture.
 * @author Marco Celesti
 */
public class PrincipaleJFrame extends javax.swing.JFrame {

    private static PrincipaleJFrame principale = null;
    private ClientiTableModel clientiModel = new ClientiTableModel();
    private FattureTableModel fattureModel = new FattureTableModel();
    private OpereTableModel opereModel = new OpereTableModel();
    private ArtistiTableModel artistiModel = new ArtistiTableModel();
    private TableColumn col = null;
    private Vector<Regione> regioniSelezionate = new Vector<Regione>();
    private Cliente cliente = null;
    private Artista artista = null;
    private Opera opera = null;
    private Fattura fattura = null;

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static PrincipaleJFrame getInstance() {
        if (principale == null) {
            principale = new PrincipaleJFrame();
        }
        return principale;
    }

    /** Costruttore privato di PrincipaleJFrame */
    private PrincipaleJFrame() {
    }

    /**
     * Inizializza il JFrame
     */
    public void init() {
        initComponents();
        firePropertyChange("reset", null, null);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Artista getArtista() {
        return artista;
    }

    public Opera getOpera() {
        return opera;
    }

    public Fattura getFattura() {
        return fattura;
    }
    
    public Vector<Regione> getRegioni() {
        return regioniSelezionate;
    }
    
    /**
     * Aggiorna la tabella dei clienti
     * @param clienti i clienti con cui riempire la tabella
     */
    public void updateClientijTable(Vector<Cliente> clienti) {
        resetjTable(clientiModel);
        if (clienti.size() > 0) {
            for (Cliente cl : clienti) {
                clientiModel.addRow(new Object[]{cl.getCodiceCliente(), cl.getCognRsoc1(), cl.getNomeRsoc2(), cl.getRegione().getNomeRegione(), cl.getMail1().toString()});
            }
        }
    }

    /**
     * Aggiorna la tabella delle fatture
     * @param fatture le fatture con cui riempire la tabella
     */
    public void updateFatturejTable(Vector<Fattura> fatture) {
        resetjTable(fattureModel);
        if (fatture.size() > 0) {
            for (Fattura fatt : fatture) {
                fattureModel.addRow(new Object[]{fatt.getDataFattura().toStringIt(), fatt.getNumeroFattura(), fatt.getCliente().toString(), new Boolean(!fatt.isProforma())});
            }
        }
    }

    /**
     * Aggiorna la tabella delle opere.
     * @param opere le opere con cui riempire la tabella
     */
    public void updateOperejTable(Vector<Opera> opere) {
        resetjTable(opereModel);
        if (opere.size() > 0) {
            for (Opera op : opere) {
                opereModel.addRow(new Object[]{op.getCodiceOpera(), op.getTitolo(), op.getTecnica(), op.getDimensioni(), op.getArtista().toString()});
            }
        }
    }

    /**
     * Aggiorna la tabella degli artisti.
     * @param artisti gli artisti con cui riempire la tabella
     */
    public void updateArtistijTable(Vector<Artista> artisti) {
        resetjTable(artistiModel);
        if (artisti.size() > 0) {
            for (Artista art : artisti) {
                artistiModel.addRow(new Object[]{art.getCodiceArtista(), art.getCognome(), art.getNome(), art.getNoteBiografiche()});
            }
        }
    }

    /**
     * Svuta la tabella in base al DefaultTableMode passato
     * @param t la tabella (rappresentata dal DefaultTableModel) da svuotare
     */
    private void resetjTable(DefaultTableModel t) {
        int j = t.getRowCount();
        for (int k = 0; k < j; k++) {
            t.removeRow(0);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        clientijPanel = new javax.swing.JPanel();
        clientijScrollPane = new javax.swing.JScrollPane();
        clientijTable = new javax.swing.JTable();
        clientijTextField = new javax.swing.JTextField();
        resetClientijButton = new javax.swing.JButton();
        nuovoClientejButton = new javax.swing.JButton();
        codiceClientejLabel = new javax.swing.JLabel();
        cognomeClientejLabel = new javax.swing.JLabel();
        regioniClientejLabel = new javax.swing.JLabel();
        codiceClientejTextField = new javax.swing.JTextField();
        cognomeClientejTextField = new javax.swing.JTextField();
        regionijComboBox = new javax.swing.JComboBox();
        avviaCodiceClientejButton = new javax.swing.JButton();
        avviaCognomeClientejButton = new javax.swing.JButton();
        aggiungiRegionejButton = new javax.swing.JButton();
        rimuoviRegionejButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        regionijTextArea = new javax.swing.JTextArea();
        avviaRegionijButton = new javax.swing.JButton();
        inviaEmailjButton = new javax.swing.JButton();
        fatturejPanel = new javax.swing.JPanel();
        fatturejScrollPane = new javax.swing.JScrollPane();
        fatturejTable = new javax.swing.JTable();
        fatturejTextField = new javax.swing.JTextField();
        resetFatturejButton = new javax.swing.JButton();
        nuovaFatturajButton = new javax.swing.JButton();
        numeroAnnoFatturajLabel = new javax.swing.JLabel();
        numeroFatturajLabel = new javax.swing.JLabel();
        numeroFatturajTextField = new javax.swing.JTextField();
        annoFatturajLabel = new javax.swing.JLabel();
        annoFatturajTextField = new javax.swing.JTextField();
        avviaNumeroAnnoFatturajButton = new javax.swing.JButton();
        operejPanel = new javax.swing.JPanel();
        operejScrollPane = new javax.swing.JScrollPane();
        operejTable = new javax.swing.JTable();
        operejTextField = new javax.swing.JTextField();
        resetOperejButton = new javax.swing.JButton();
        nuovaOperajButton = new javax.swing.JButton();
        codiceOperajLabel = new javax.swing.JLabel();
        codiceOperajTextField = new javax.swing.JTextField();
        avviaCodiceOperajButton = new javax.swing.JButton();
        artistijPanel = new javax.swing.JPanel();
        artistijScrollPane = new javax.swing.JScrollPane();
        artistijTable = new javax.swing.JTable();
        artistijTextField = new javax.swing.JTextField();
        resetArtistijButton = new javax.swing.JButton();
        nuovoArtistajButton = new javax.swing.JButton();
        codiceArtistajLabel = new javax.swing.JLabel();
        codiceArtistajTextField = new javax.swing.JTextField();
        avviaCodiceArtistajButton = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        backupjMenu = new javax.swing.JMenu();
        creaBackupjMenuItem = new javax.swing.JMenuItem();
        ripristinaBackupjMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                DefaultSingleSelectionModel dssm = (DefaultSingleSelectionModel)e.getSource();
                int index = dssm.getSelectedIndex();
                String tabName = jTabbedPane.getTitleAt(index);
                PrincipaleJFrame.getInstance().setTitle("Finestra principale: " + tabName);
            }
        });

        clientiModel.addColumn("ID");
        clientiModel.addColumn("Cognome / Rag. sociale_1");
        clientiModel.addColumn("Nome / Rag. sociale_2");
        clientiModel.addColumn("Regione");
        clientiModel.addColumn("Email");
        clientijTable.setModel(clientiModel);
        clientijTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        col = clientijTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(40);

        col = clientijTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(255);

        col = clientijTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(255);

        col = clientijTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(200);

        col = clientijTable.getColumnModel().getColumn(4);
        col.setPreferredWidth(130);
        clientijTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        clientijTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                clientijTableMouseReleased(evt);
            }
        });
        clientijScrollPane.setViewportView(clientijTable);

        clientijTextField.setEditable(false);
        clientijTextField.setFont(new java.awt.Font("Tahoma", 1, 11));

        resetClientijButton.setText("Reset");
        resetClientijButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetClientijButtonActionPerformed(evt);
            }
        });

        nuovoClientejButton.setText("Inserisci nuovo cliente");
        nuovoClientejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuovoClientejButtonActionPerformed(evt);
            }
        });

        codiceClientejLabel.setText("Ricerca cliente per codice:");

        cognomeClientejLabel.setText("Ricerca cliente per cognome:");

        regioniClientejLabel.setText("Ricerca clienti per regione:");

        codiceClientejTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codiceClientejTextFieldKeyTyped(evt);
            }
        });

        regionijComboBox.setModel(new javax.swing.DefaultComboBoxModel(abstractlayer.Regione.values()));

        avviaCodiceClientejButton.setText("Avvia");
        avviaCodiceClientejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avviaCodiceClientejButtonActionPerformed(evt);
            }
        });

        avviaCognomeClientejButton.setText("Avvia");
        avviaCognomeClientejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avviaCognomeClientejButtonActionPerformed(evt);
            }
        });

        aggiungiRegionejButton.setText("Aggiungi");
        aggiungiRegionejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aggiungiRegionejButtonActionPerformed(evt);
            }
        });

        rimuoviRegionejButton.setText("Rimuovi");
        rimuoviRegionejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rimuoviRegionejButtonActionPerformed(evt);
            }
        });

        regionijTextArea.setColumns(20);
        regionijTextArea.setEditable(false);
        regionijTextArea.setFont(new java.awt.Font("Tahoma", 0, 10));
        regionijTextArea.setRows(4);
        jScrollPane1.setViewportView(regionijTextArea);

        avviaRegionijButton.setText("Avvia");
        avviaRegionijButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avviaRegionijButtonActionPerformed(evt);
            }
        });

        inviaEmailjButton.setText("Invia email multiple");
        inviaEmailjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviaEmailjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout clientijPanelLayout = new javax.swing.GroupLayout(clientijPanel);
        clientijPanel.setLayout(clientijPanelLayout);
        clientijPanelLayout.setHorizontalGroup(
            clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientijPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(clientijPanelLayout.createSequentialGroup()
                        .addComponent(clientijTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(resetClientijButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientijPanelLayout.createSequentialGroup()
                        .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codiceClientejLabel)
                                .addComponent(cognomeClientejLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rimuoviRegionejButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(aggiungiRegionejButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                            .addComponent(regioniClientejLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cognomeClientejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(regionijComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(codiceClientejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(avviaRegionijButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(clientijPanelLayout.createSequentialGroup()
                                .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(avviaCodiceClientejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(avviaCognomeClientejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(311, 311, 311)
                                .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(inviaEmailjButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nuovoClientejButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(clientijScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        clientijPanelLayout.setVerticalGroup(
            clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientijPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientijTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetClientijButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientijScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codiceClientejLabel)
                    .addComponent(avviaCodiceClientejButton)
                    .addComponent(codiceClientejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nuovoClientejButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cognomeClientejLabel)
                    .addComponent(avviaCognomeClientejButton)
                    .addComponent(cognomeClientejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inviaEmailjButton))
                .addGap(18, 18, 18)
                .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regioniClientejLabel)
                    .addComponent(regionijComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(clientijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(clientijPanelLayout.createSequentialGroup()
                        .addComponent(aggiungiRegionejButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rimuoviRegionejButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avviaRegionijButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Clienti", clientijPanel);

        fattureModel.addColumn("Data");
        fattureModel.addColumn("Numero");
        fattureModel.addColumn("Cliente");
        fattureModel.addColumn("Definitiva?");
        fatturejTable.setModel(fattureModel);
        fatturejTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        col = fatturejTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(200);

        col = fatturejTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(100);

        col = fatturejTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(480);

        col = fatturejTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(100);
        fatturejTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fatturejTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fatturejTableMouseReleased(evt);
            }
        });
        fatturejScrollPane.setViewportView(fatturejTable);

        fatturejTextField.setEditable(false);
        fatturejTextField.setFont(new java.awt.Font("Tahoma", 1, 11));

        resetFatturejButton.setText("Reset");
        resetFatturejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFatturejButtonActionPerformed(evt);
            }
        });

        nuovaFatturajButton.setText("Inserisci nuova fattura");
        nuovaFatturajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuovaFatturajButtonActionPerformed(evt);
            }
        });

        numeroAnnoFatturajLabel.setText("Ricerca fattura per numero e anno:");

        numeroFatturajLabel.setText("numero");

        numeroFatturajTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                numeroFatturajTextFieldKeyTyped(evt);
            }
        });

        annoFatturajLabel.setText("anno");

        annoFatturajTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                annoFatturajTextFieldKeyTyped(evt);
            }
        });

        avviaNumeroAnnoFatturajButton.setText("Avvia");
        avviaNumeroAnnoFatturajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avviaNumeroAnnoFatturajButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fatturejPanelLayout = new javax.swing.GroupLayout(fatturejPanel);
        fatturejPanel.setLayout(fatturejPanelLayout);
        fatturejPanelLayout.setHorizontalGroup(
            fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fatturejPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fatturejPanelLayout.createSequentialGroup()
                        .addComponent(fatturejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(resetFatturejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fatturejScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(fatturejPanelLayout.createSequentialGroup()
                        .addComponent(numeroAnnoFatturajLabel)
                        .addGap(18, 18, 18)
                        .addGroup(fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numeroFatturajLabel)
                            .addComponent(annoFatturajLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(annoFatturajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numeroFatturajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fatturejPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 469, Short.MAX_VALUE)
                                .addComponent(nuovaFatturajButton))
                            .addGroup(fatturejPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(avviaNumeroAnnoFatturajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        fatturejPanelLayout.setVerticalGroup(
            fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fatturejPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fatturejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetFatturejButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fatturejScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuovaFatturajButton)
                    .addComponent(numeroAnnoFatturajLabel)
                    .addComponent(numeroFatturajLabel)
                    .addComponent(numeroFatturajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fatturejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(annoFatturajLabel)
                    .addComponent(annoFatturajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avviaNumeroAnnoFatturajButton))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Fatture", fatturejPanel);

        opereModel.addColumn("ID");
        opereModel.addColumn("Titolo");
        opereModel.addColumn("Tecnica");
        opereModel.addColumn("Dimensioni");
        opereModel.addColumn("Artista");
        operejTable.setModel(opereModel);
        operejTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        col = operejTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(40);

        col = operejTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(210);

        col = operejTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(200);

        col = operejTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(100);

        col = operejTable.getColumnModel().getColumn(4);
        col.setPreferredWidth(330);
        operejTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        operejTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                operejTableMouseReleased(evt);
            }
        });
        operejScrollPane.setViewportView(operejTable);

        operejTextField.setEditable(false);
        operejTextField.setFont(new java.awt.Font("Tahoma", 1, 11));

        resetOperejButton.setText("Reset");
        resetOperejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetOperejButtonActionPerformed(evt);
            }
        });

        nuovaOperajButton.setText("Inserisci nuova opera");
        nuovaOperajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuovaOperajButtonActionPerformed(evt);
            }
        });

        codiceOperajLabel.setText("Ricerca opera per codice:");

        codiceOperajTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codiceOperajTextFieldKeyTyped(evt);
            }
        });

        avviaCodiceOperajButton.setText("Avvia");
        avviaCodiceOperajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avviaCodiceOperajButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout operejPanelLayout = new javax.swing.GroupLayout(operejPanel);
        operejPanel.setLayout(operejPanelLayout);
        operejPanelLayout.setHorizontalGroup(
            operejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operejPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(operejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(operejPanelLayout.createSequentialGroup()
                        .addComponent(operejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(resetOperejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(operejScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, operejPanelLayout.createSequentialGroup()
                        .addComponent(codiceOperajLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(codiceOperajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(avviaCodiceOperajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 329, Short.MAX_VALUE)
                        .addComponent(nuovaOperajButton)))
                .addContainerGap())
        );
        operejPanelLayout.setVerticalGroup(
            operejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operejPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(operejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(operejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetOperejButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(operejScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(operejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuovaOperajButton)
                    .addComponent(codiceOperajLabel)
                    .addComponent(codiceOperajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avviaCodiceOperajButton))
                .addContainerGap(159, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Opere", operejPanel);

        artistiModel.addColumn("ID");
        artistiModel.addColumn("Cognome");
        artistiModel.addColumn("Nome");
        artistiModel.addColumn("Note biografiche");
        artistijTable.setModel(artistiModel);
        artistijTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        col = artistijTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(40);

        col = artistijTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(200);

        col = artistijTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(200);

        col = artistijTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(440);
        artistijTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        artistijTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                artistijTableMouseReleased(evt);
            }
        });
        artistijScrollPane.setViewportView(artistijTable);

        artistijTextField.setEditable(false);
        artistijTextField.setFont(new java.awt.Font("Tahoma", 1, 11));

        resetArtistijButton.setText("Reset");
        resetArtistijButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetArtistijButtonActionPerformed(evt);
            }
        });

        nuovoArtistajButton.setText("Inserisci nuovo artista");
        nuovoArtistajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuovoArtistajButtonActionPerformed(evt);
            }
        });

        codiceArtistajLabel.setText("Ricerca artista per codice:");

        codiceArtistajTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codiceArtistajTextFieldKeyTyped(evt);
            }
        });

        avviaCodiceArtistajButton.setText("Avvia");
        avviaCodiceArtistajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avviaCodiceArtistajButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout artistijPanelLayout = new javax.swing.GroupLayout(artistijPanel);
        artistijPanel.setLayout(artistijPanelLayout);
        artistijPanelLayout.setHorizontalGroup(
            artistijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artistijPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(artistijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(artistijScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(artistijPanelLayout.createSequentialGroup()
                        .addComponent(artistijTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(resetArtistijButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, artistijPanelLayout.createSequentialGroup()
                        .addComponent(codiceArtistajLabel)
                        .addGap(18, 18, 18)
                        .addComponent(codiceArtistajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(avviaCodiceArtistajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nuovoArtistajButton)))
                .addContainerGap())
        );
        artistijPanelLayout.setVerticalGroup(
            artistijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artistijPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(artistijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(artistijTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetArtistijButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(artistijScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(artistijPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuovoArtistajButton)
                    .addComponent(codiceArtistajLabel)
                    .addComponent(codiceArtistajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avviaCodiceArtistajButton))
                .addContainerGap(159, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Artisti", artistijPanel);

        backupjMenu.setText("Backup");

        creaBackupjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        creaBackupjMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_backup_small.jpg"))); // NOI18N
        creaBackupjMenuItem.setText("Crea un backup dell'archivio");
        creaBackupjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creaBackupjMenuItemActionPerformed(evt);
            }
        });
        backupjMenu.add(creaBackupjMenuItem);

        ripristinaBackupjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        ripristinaBackupjMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_restore_small.jpg"))); // NOI18N
        ripristinaBackupjMenuItem.setText("Ripristina un backup dell'archivio");
        ripristinaBackupjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ripristinaBackupjMenuItemActionPerformed(evt);
            }
        });
        backupjMenu.add(ripristinaBackupjMenuItem);

        jMenuBar.add(backupjMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action performed per il bottone "Nuovo Cliente"
     * @param evt
     */
private void nuovoClientejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuovoClientejButtonActionPerformed
    cliente = new Cliente();
    firePropertyChange("modifica_cliente", null, null);
}//GEN-LAST:event_nuovoClientejButtonActionPerformed

    /**
     * Action performed per il bottone "Nuova Fattura"
     * @param evt
     */
private void nuovaFatturajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuovaFatturajButtonActionPerformed
    fattura = new Fattura();
    firePropertyChange("modifica_fattura", null, null);
}//GEN-LAST:event_nuovaFatturajButtonActionPerformed

    /**
     * Action performed per il bottone "Nuovo Artista"
     * @param evt
     */
private void nuovoArtistajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuovoArtistajButtonActionPerformed
    artista = new Artista();
    firePropertyChange("modifica_artista", null, null);
}//GEN-LAST:event_nuovoArtistajButtonActionPerformed

    /**
     * Action performed per il bottone "Reset" nel pannello Clienti
     * @param evt
     */
private void resetClientijButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetClientijButtonActionPerformed
    firePropertyChange("reset", null, null);
}//GEN-LAST:event_resetClientijButtonActionPerformed

    /**
     * Action performed per il bottone "Reset" nel pannello Fatture
     * @param evt
     */
private void resetFatturejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFatturejButtonActionPerformed
    firePropertyChange("reset", null, null);
}//GEN-LAST:event_resetFatturejButtonActionPerformed

    /**
     * Action performed per il bottone "Reset" nel pannello Opere
     * @param evt
     */
private void resetOperejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetOperejButtonActionPerformed
    firePropertyChange("reset", null, null);
}//GEN-LAST:event_resetOperejButtonActionPerformed

    /**
     * Action performed per il bottone "Reset" nel pannello Artisti
     * @param evt
     */
private void resetArtistijButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetArtistijButtonActionPerformed
    firePropertyChange("reset", null, null);
}//GEN-LAST:event_resetArtistijButtonActionPerformed

    /**
     * Imposta il messaggio nel pannello Clienti
     * @param message il messaggio da impostare
     */
    public void setClientiMessage(String message) {
        clientijTextField.setText(message);
    }

    /**
     * Imposta il messaggio nel pannello Fatture
     * @param message il messaggio da impostare
     */
    public void setFattureMessage(String message) {
        fatturejTextField.setText(message);
    }

    /**
     * Imposta il messaggio nel pannello Opere
     * @param message il messaggio da impostare
     */
    public void setOpereMessage(String message) {
        operejTextField.setText(message);
    }

    /**
     * Imposta il messaggio nel pannello Artisti
     * @param message il messaggio da impostare
     */
    public void setArtistiMessage(String message) {
        artistijTextField.setText(message);
    }

    /**
     * Azione per l'evento <code>MouseReleased</code> sulla tabella contenente gli artisti
     * @param evt
     */
private void artistijTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_artistijTableMouseReleased
    artistijTable.changeSelection(artistijTable.rowAtPoint(new Point(evt.getX(), evt.getY())), 0, false, false);
    final int i = artistijTable.getSelectedRow();
    JPopupMenu jPopupMenu = new JPopupMenu();

    if (SwingUtilities.isRightMouseButton(evt)) {
        Component source = evt.getComponent();

        JMenuItem visualizzajMenuItem = new JMenuItem("Visualizza l'artista selezionato");
        jPopupMenu.add(visualizzajMenuItem);
        visualizzajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceArtista = Integer.parseInt((String) "" + artistiModel.getValueAt(i, 0));
                artista = new Artista();
                artista.setCodiceArtista(codiceArtista);
                firePropertyChange("visualizza_artista", null, null);
            }
        });

        JMenuItem modificajMenuItem = new JMenuItem("Modifica l'artista selezionato");
        jPopupMenu.add(modificajMenuItem);
        modificajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceArtista = Integer.parseInt((String) "" + artistiModel.getValueAt(i, 0));
                artista = new Artista();
                artista.setCodiceArtista(codiceArtista);
                firePropertyChange("modifica_artista", null, null);
            }
        });

        JMenuItem eliminajMenuItem = new JMenuItem("Elimina l'artista selezionato");
        jPopupMenu.add(eliminajMenuItem);
        eliminajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceArtista = Integer.parseInt((String) "" + artistiModel.getValueAt(i, 0));
                artista = new Artista();
                artista.setCodiceArtista(codiceArtista);
                artista.setCognome((String) artistiModel.getValueAt(i, 1));
                artista.setNome((String) artistiModel.getValueAt(i, 2));
                firePropertyChange("cancella_artista", null, null);
            }
        });

        JMenuItem applicaFiltrojMenuItem = new JMenuItem("Applica filtro");
        jPopupMenu.add(applicaFiltrojMenuItem);
        applicaFiltrojMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceArtista = Integer.parseInt((String) "" + artistiModel.getValueAt(i, 0));
                artista = new Artista();
                artista.setCodiceArtista(codiceArtista);
                firePropertyChange("filtro_artista", null, null);
            }
        });

        jPopupMenu.show(source, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_artistijTableMouseReleased

    /**
     * Azione per l'evento <code>MouseReleased</code> sulla tabella contenente i clienti
     * @param evt
     */
private void clientijTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientijTableMouseReleased
    clientijTable.changeSelection(clientijTable.rowAtPoint(new Point(evt.getX(), evt.getY())), 0, false, false);
    final int i = clientijTable.getSelectedRow();
    JPopupMenu jPopupMenu = new JPopupMenu();

    if (SwingUtilities.isRightMouseButton(evt)) {
        Component source = evt.getComponent();

        JMenuItem inviaEmailjMenuItem = new JMenuItem("Invia una email");
        jPopupMenu.add(inviaEmailjMenuItem);
        inviaEmailjMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceCliente = Integer.parseInt((String) "" + clientiModel.getValueAt(i, 0));
                cliente = new Cliente();
                cliente.setCodiceCliente(codiceCliente);
                firePropertyChange("invia_email_singola", null, null);
            }
        });
        
        JMenuItem visualizzajMenuItem = new JMenuItem("Visualizza il cliente selezionato");
        jPopupMenu.add(visualizzajMenuItem);
        visualizzajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceCliente = Integer.parseInt((String) "" + clientiModel.getValueAt(i, 0));
                cliente = new Cliente();
                cliente.setCodiceCliente(codiceCliente);
                firePropertyChange("visualizza_cliente", null, null);
            }
        });

        JMenuItem modificajMenuItem = new JMenuItem("Modifica il cliente selezionato");
        jPopupMenu.add(modificajMenuItem);
        modificajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceCliente = Integer.parseInt((String) "" + clientiModel.getValueAt(i, 0));
                cliente = new Cliente();
                cliente.setCodiceCliente(codiceCliente);
                firePropertyChange("modifica_cliente", null, null);
            }
        });
        
        JMenuItem eliminajMenuItem = new JMenuItem("Elimina il cliente selezionato");
        jPopupMenu.add(eliminajMenuItem);
        eliminajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceCliente = Integer.parseInt((String) "" + clientiModel.getValueAt(i, 0));
                cliente = new Cliente();
                cliente.setCodiceCliente(codiceCliente);
                cliente.setCognRsoc1((String) clientiModel.getValueAt(i, 1));
                cliente.setNomeRsoc2((String) clientiModel.getValueAt(i, 2));
                firePropertyChange("cancella_cliente", null, null);
            }
        });
        
        JMenuItem applicaFiltrojMenuItem = new JMenuItem("Applica filtro");
        jPopupMenu.add(applicaFiltrojMenuItem);
        applicaFiltrojMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceCliente = Integer.parseInt((String) "" + clientiModel.getValueAt(i, 0));
                cliente = new Cliente();
                cliente.setCodiceCliente(codiceCliente);
                firePropertyChange("filtro_cliente", null, null);
            }
        });
        
        jPopupMenu.show(source, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_clientijTableMouseReleased

    /**
     * Action performed per il bottone "Nuova Opera"
     * @param evt
     */
private void nuovaOperajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuovaOperajButtonActionPerformed
    opera = new Opera();
    firePropertyChange("modifica_opera", null, null);
}//GEN-LAST:event_nuovaOperajButtonActionPerformed

    /**
     * Azione per l'evento <code>MouseReleased</code> sulla tabella contenente le opere
     * @param evt
     */
private void operejTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_operejTableMouseReleased
    operejTable.changeSelection(operejTable.rowAtPoint(new Point(evt.getX(), evt.getY())), 0, false, false);
    final int i = operejTable.getSelectedRow();
    JPopupMenu jPopupMenu = new JPopupMenu();

    if (SwingUtilities.isRightMouseButton(evt)) {
        Component source = evt.getComponent();

        JMenuItem visualizzajMenuItem = new JMenuItem("Visualizza l'opera selezionata");
        jPopupMenu.add(visualizzajMenuItem);
        visualizzajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceOpera = Integer.parseInt((String) "" + opereModel.getValueAt(i, 0));
                opera = new Opera();
                opera.setCodiceOpera(codiceOpera);
                firePropertyChange("visualizza_opera", null, null);
            }
        });

        JMenuItem modificajMenuItem = new JMenuItem("Modifica l'opera selezionata");
        jPopupMenu.add(modificajMenuItem);
        modificajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceOpera = Integer.parseInt((String) "" + opereModel.getValueAt(i, 0));
                opera = new Opera();
                opera.setCodiceOpera(codiceOpera);
                firePropertyChange("modifica_opera", null, null);
            }
        });

        JMenuItem eliminajMenuItem = new JMenuItem("Elimina l'opera selezionata");
        jPopupMenu.add(eliminajMenuItem);
        eliminajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceOpera = Integer.parseInt((String) "" + opereModel.getValueAt(i, 0));
                opera = new Opera();
                opera.setCodiceOpera(codiceOpera);
                opera.setTitolo((String) opereModel.getValueAt(i, 1));
                firePropertyChange("cancella_opera", null, null);
            }
        });

        JMenuItem applicaFiltrojMenuItem = new JMenuItem("Applica filtro");
        jPopupMenu.add(applicaFiltrojMenuItem);
        applicaFiltrojMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codiceOpera = Integer.parseInt((String) "" + opereModel.getValueAt(i, 0));
                opera = new Opera();
                opera.setCodiceOpera(codiceOpera);
                firePropertyChange("filtro_opera", null, null);
            }
        });
        
        jPopupMenu.show(source, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_operejTableMouseReleased

    /**
     * Azione per l'evento <code>MouseReleased</code> sulla tabella contenente le fatture
     * @param evt
     */
private void fatturejTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fatturejTableMouseReleased
    fatturejTable.changeSelection(fatturejTable.rowAtPoint(new Point(evt.getX(), evt.getY())), 0, false, false);
    final int i = fatturejTable.getSelectedRow();
    JPopupMenu jPopupMenu = new JPopupMenu();

    if (SwingUtilities.isRightMouseButton(evt)) {
        Component source = evt.getComponent();

        JMenuItem visualizzajMenuItem = new JMenuItem("Visualizza la fattura selezionata");
        jPopupMenu.add(visualizzajMenuItem);
        visualizzajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int numeroFattura = Integer.parseInt((String) "" + fattureModel.getValueAt(i, 1));
                Data dataFattura = null;
                try {
                    dataFattura = Data.parseData((String) "" + fattureModel.getValueAt(i, 0));
                } catch (BadFormatException ex) {
                    Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura = new Fattura();
                fattura.setNumeroFattura(numeroFattura);
                fattura.setDataFattura(dataFattura);
                firePropertyChange("visualizza_fattura", null, null);
            }
        });

        JMenuItem modificajMenuItem = new JMenuItem("Modifica la fattura selezionata");
        jPopupMenu.add(modificajMenuItem);
        modificajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int numeroFattura = Integer.parseInt((String) "" + fattureModel.getValueAt(i, 1));
                Data dataFattura = null;
                try {
                    dataFattura = Data.parseData((String) "" + fattureModel.getValueAt(i, 0));
                } catch (BadFormatException ex) {
                    Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura = new Fattura();
                fattura.setNumeroFattura(numeroFattura);
                fattura.setDataFattura(dataFattura);
                firePropertyChange("modifica_fattura", null, null);
            }
        });

        JMenuItem eliminajMenuItem = new JMenuItem("Elimina la fattura selezionata");
        jPopupMenu.add(eliminajMenuItem);
        eliminajMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int numeroFattura = Integer.parseInt((String) "" + fattureModel.getValueAt(i, 1));
                Data dataFattura = null;
                try {
                    dataFattura = Data.parseData((String) "" + fattureModel.getValueAt(i, 0));
                } catch (BadFormatException ex) {
                    Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura = new Fattura();
                fattura.setNumeroFattura(numeroFattura);
                fattura.setDataFattura(dataFattura);
                firePropertyChange("cancella_fattura", null, null);
            }
        });

        JMenuItem applicaFiltrojMenuItem = new JMenuItem("Applica filtro");
        jPopupMenu.add(applicaFiltrojMenuItem);
        applicaFiltrojMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int numeroFattura = Integer.parseInt((String) "" + fattureModel.getValueAt(i, 1));
                Data dataFattura = null;
                try {
                    dataFattura = Data.parseData((String) "" + fattureModel.getValueAt(i, 0));
                } catch (BadFormatException ex) {
                    Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura = new Fattura();
                fattura.setNumeroFattura(numeroFattura);
                fattura.setDataFattura(dataFattura);
                firePropertyChange("filtro_fattura", null, null);
            }
        });

        jPopupMenu.show(source, evt.getX(), evt.getY());
    }
}//GEN-LAST:event_fatturejTableMouseReleased

    /**
     * Action performed per il bottone "Avvia" della ricerca per codice nel pannello Clienti
     * @param evt
     */
private void avviaCodiceClientejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avviaCodiceClientejButtonActionPerformed
    String codice = codiceClientejTextField.getText();
    if (!codice.isEmpty()) {
        cliente = new Cliente();
        cliente.setCodiceCliente(Integer.parseInt(codice));
        firePropertyChange("cerca_cliente", null, null);
    } else {
        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Il campo 'Codice cliente' è vuoto", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_avviaCodiceClientejButtonActionPerformed
   
    /**
     * Action performed per il bottone "Avvia" della ricerca per cognome nel pannello Clienti
     * @param evt
     */
private void avviaCognomeClientejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avviaCognomeClientejButtonActionPerformed
    String cognome = cognomeClientejTextField.getText();
    if (!cognome.isEmpty()) {
        cliente = new Cliente();
        cliente.setCognRsoc1(cognome);
        firePropertyChange("cerca_cliente", null, null);
    } else {
        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Il campo 'Cognome' è vuoto", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_avviaCognomeClientejButtonActionPerformed

    /**
     * Action performed per il bottone "Avvia" della ricerca per regione nel pannello Clienti
     * @param evt
     */
private void avviaRegionijButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avviaRegionijButtonActionPerformed
    if (regioniSelezionate.size() > 0) {
        firePropertyChange("cerca_cliente_per_regioni", null, null);
    } else {
        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Selezionare almeno una regione", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_avviaRegionijButtonActionPerformed

    /**
     * Action performed per il bottone "Aggiungi" nel pannello Clienti
     * @param evt
     */
private void aggiungiRegionejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aggiungiRegionejButtonActionPerformed
    Regione r = (Regione) regionijComboBox.getSelectedItem();
    if (!regioniSelezionate.contains(r)) {
        regioniSelezionate.add(r);
        regionijTextArea.append(r.getNomeRegione() + "\n");
    } else {
        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Regione già inclusa nella ricerca", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_aggiungiRegionejButtonActionPerformed

    /**
     * Action performed per il bottone "Rimuovi" nel pannello Clienti
     * @param evt
     */
private void rimuoviRegionejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rimuoviRegionejButtonActionPerformed
    Regione r = (Regione) regionijComboBox.getSelectedItem();
    if (regioniSelezionate.contains(r)) {
        regioniSelezionate.remove(r);
        String regioniInTextArea = regionijTextArea.getText();
        regioniInTextArea = regioniInTextArea.replace(r.getNomeRegione() + "\n", "");
        regionijTextArea.setText(regioniInTextArea);
    } else {
        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Regione non inclusa nella ricerca", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_rimuoviRegionejButtonActionPerformed

    /**
     * Action performed per il bottone "Avvia" della ricerca nel pannello Fatture
     * @param evt
     */
private void avviaNumeroAnnoFatturajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avviaNumeroAnnoFatturajButtonActionPerformed
    String numero = numeroFatturajTextField.getText();
    String anno = annoFatturajTextField.getText();
    if (!numero.isEmpty() && !anno.isEmpty()) {
        fattura = new Fattura();
        fattura.setNumeroFattura(Integer.parseInt(numero));
        Data d = null;
        try {
            d = new Data(1, 1, Integer.parseInt(anno));
            fattura.setDataFattura(d);
            firePropertyChange("cerca_fattura", null, null);
        } catch (BadFormatException ex) {
            JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Compilare entrambi i campi 'Numero' ed 'Anno'", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_avviaNumeroAnnoFatturajButtonActionPerformed

    /**
     * Action performed per il bottone "Avvia" della ricerca nel pannello Opere
     * @param evt
     */
private void avviaCodiceOperajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avviaCodiceOperajButtonActionPerformed
    String codice = codiceOperajTextField.getText();
    if (!codice.isEmpty()) {
        opera = new Opera();
        opera.setCodiceOpera(Integer.parseInt(codice));
        firePropertyChange("cerca_opera", null, null);
    } else {
        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Il campo 'Codice opera' è vuoto", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_avviaCodiceOperajButtonActionPerformed

    /**
     * Action performed per il bottone "Avvia" della ricerca nel pannello Artista
     * @param evt
     */
private void avviaCodiceArtistajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avviaCodiceArtistajButtonActionPerformed
    String codice = codiceArtistajTextField.getText();
    if (!codice.isEmpty()) {
        artista = new Artista();
        artista.setCodiceArtista(Integer.parseInt(codice));
        firePropertyChange("cerca_artista", null, null);
    } else {
        JOptionPane.showMessageDialog(PrincipaleJFrame.getInstance(), "Il campo 'Codice artista' è vuoto", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_avviaCodiceArtistajButtonActionPerformed

    /**
     * Impedisce la digitazione di caratteri diversi da cifre
     * @param evt
     */
private void codiceClientejTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codiceClientejTextFieldKeyTyped
    char c = evt.getKeyChar();
    if (!(c >= 48 && c <= 57)) {
        evt.setKeyChar('\u0008');
    }
}//GEN-LAST:event_codiceClientejTextFieldKeyTyped

    /**
     * Impedisce la digitazione di caratteri diversi da cifre
     * @param evt
     */
private void numeroFatturajTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroFatturajTextFieldKeyTyped
    char c = evt.getKeyChar();
    if (!(c >= 48 && c <= 57)) {
        evt.setKeyChar('\u0008');
    }
}//GEN-LAST:event_numeroFatturajTextFieldKeyTyped

    /**
     * Impedisce la digitazione di caratteri diversi da cifre
     * @param evt
     */
private void annoFatturajTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_annoFatturajTextFieldKeyTyped
    char c = evt.getKeyChar();
    if (!(c >= 48 && c <= 57)) {
        evt.setKeyChar('\u0008');
    }
}//GEN-LAST:event_annoFatturajTextFieldKeyTyped

    /**
     * Impedisce la digitazione di caratteri diversi da cifre
     * @param evt
     */
private void codiceOperajTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codiceOperajTextFieldKeyTyped
    char c = evt.getKeyChar();
    if (!(c >= 48 && c <= 57)) {
        evt.setKeyChar('\u0008');
    }
}//GEN-LAST:event_codiceOperajTextFieldKeyTyped

    /**
     * Impedisce la digitazione di caratteri diversi da cifre
     * @param evt
     */
private void codiceArtistajTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codiceArtistajTextFieldKeyTyped
    char c = evt.getKeyChar();
    if (!(c >= 48 && c <= 57)) {
        evt.setKeyChar('\u0008');
    }
}//GEN-LAST:event_codiceArtistajTextFieldKeyTyped

    /**
     * Action performed per la voce di menu "Crea un backup dell'archivio"
     * @param evt
     */
private void creaBackupjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creaBackupjMenuItemActionPerformed
    firePropertyChange("crea_bkp", null, null);
}//GEN-LAST:event_creaBackupjMenuItemActionPerformed

    /**
     * Action performed per la voce di menu "Ripristina un backup dell'archivio"
     * @param evt
     */
private void ripristinaBackupjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ripristinaBackupjMenuItemActionPerformed
    firePropertyChange("ripristina_bkp", null, null);
}//GEN-LAST:event_ripristinaBackupjMenuItemActionPerformed

    /**
     * Action performed per il bottone "Invia email multiple"
     * @param evt
     */
private void inviaEmailjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviaEmailjButtonActionPerformed
    firePropertyChange("invia_email_multipla", null, null);
}//GEN-LAST:event_inviaEmailjButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aggiungiRegionejButton;
    private javax.swing.JLabel annoFatturajLabel;
    private javax.swing.JTextField annoFatturajTextField;
    private javax.swing.JPanel artistijPanel;
    private javax.swing.JScrollPane artistijScrollPane;
    private javax.swing.JTable artistijTable;
    private javax.swing.JTextField artistijTextField;
    private javax.swing.JButton avviaCodiceArtistajButton;
    private javax.swing.JButton avviaCodiceClientejButton;
    private javax.swing.JButton avviaCodiceOperajButton;
    private javax.swing.JButton avviaCognomeClientejButton;
    private javax.swing.JButton avviaNumeroAnnoFatturajButton;
    private javax.swing.JButton avviaRegionijButton;
    private javax.swing.JMenu backupjMenu;
    private javax.swing.JPanel clientijPanel;
    private javax.swing.JScrollPane clientijScrollPane;
    private javax.swing.JTable clientijTable;
    private javax.swing.JTextField clientijTextField;
    private javax.swing.JLabel codiceArtistajLabel;
    private javax.swing.JTextField codiceArtistajTextField;
    private javax.swing.JLabel codiceClientejLabel;
    private javax.swing.JTextField codiceClientejTextField;
    private javax.swing.JLabel codiceOperajLabel;
    private javax.swing.JTextField codiceOperajTextField;
    private javax.swing.JLabel cognomeClientejLabel;
    private javax.swing.JTextField cognomeClientejTextField;
    private javax.swing.JMenuItem creaBackupjMenuItem;
    private javax.swing.JPanel fatturejPanel;
    private javax.swing.JScrollPane fatturejScrollPane;
    private javax.swing.JTable fatturejTable;
    private javax.swing.JTextField fatturejTextField;
    private javax.swing.JButton inviaEmailjButton;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel numeroAnnoFatturajLabel;
    private javax.swing.JLabel numeroFatturajLabel;
    private javax.swing.JTextField numeroFatturajTextField;
    private javax.swing.JButton nuovaFatturajButton;
    private javax.swing.JButton nuovaOperajButton;
    private javax.swing.JButton nuovoArtistajButton;
    private javax.swing.JButton nuovoClientejButton;
    private javax.swing.JPanel operejPanel;
    private javax.swing.JScrollPane operejScrollPane;
    private javax.swing.JTable operejTable;
    private javax.swing.JTextField operejTextField;
    private javax.swing.JLabel regioniClientejLabel;
    private javax.swing.JComboBox regionijComboBox;
    private javax.swing.JTextArea regionijTextArea;
    private javax.swing.JButton resetArtistijButton;
    private javax.swing.JButton resetClientijButton;
    private javax.swing.JButton resetFatturejButton;
    private javax.swing.JButton resetOperejButton;
    private javax.swing.JButton rimuoviRegionejButton;
    private javax.swing.JMenuItem ripristinaBackupjMenuItem;
    // End of variables declaration//GEN-END:variables

}
