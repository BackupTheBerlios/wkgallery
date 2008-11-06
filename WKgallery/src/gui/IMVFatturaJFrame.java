package gui;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import tablemodels.OpereDisponibiliTableModel;
import tablemodels.OpereInFatturaTableModel;

/**
 * La finestra per l'Inserimento, Modifica e Visualizzazione di una fattura.
 * @author  Marco Celesti
 */
public class IMVFatturaJFrame extends javax.swing.JFrame {

    private static IMVFatturaJFrame imvFattura = null;
    private Fattura fattura = null;
    private String titoloFinestra = "";
    private boolean initialized = false;
    private boolean nuovaFattura = false;
    private OpereDisponibiliTableModel opereDisponibiliModel = new OpereDisponibiliTableModel();
    private OpereInFatturaTableModel opereInFatturaModel = new OpereInFatturaTableModel();
    private TableColumn col = null;
    private Vector<Artista> artisti = null;
    private Vector<Cliente> clienti = null;
    private Artista artistaFiltro = null;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private Opera operaDaMovimentare = null;

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static IMVFatturaJFrame getInstance() {
        if (imvFattura == null) {
            imvFattura = new IMVFatturaJFrame();
        }
        return imvFattura;
    }

    /** Costruttore privato di IMVFatturaJFrame */
    private IMVFatturaJFrame() {
    }

    /**
     * Metodo <i>set</i> per <code>fattura</code>.
     * @param fattura la fattura
     */
    public void setFattura(Fattura fattura) {
        this.fattura = fattura;
    }

    /**
     * Metodo <i>get</i> per <code>fattura</code>.
     * @return la fattura
     */
    public Fattura getFattura() {
        return fattura;
    }
    
    /**
     * Metodo <i>set</i> per <code>artisti</code>.
     * @param artisti l'elenco degli artisti presenti in galleria
     */
    public void setArtisti(Vector<Artista> artisti) {
        this.artisti = artisti;
    }

    /**
     * Metodo <i>set</i> per <code>clienti</code>.
     * @param clienti l'elenco dei clienti registrati
     */
    public void setClienti(Vector<Cliente> clienti) {
        this.clienti = clienti;
    }
    
    /**
     * Metodo <i>get</i> per <code>artistaFiltro</code>.
     * @return l'artista con cui creare un filtro per le opere disponibili
     */
    public Artista getArtistaFiltro() {
        return artistaFiltro;
    }
    
    /**
     * Metodo <i>get</i> per <code>operaDaMovimentare</code>.
     * @return l'opera
     */
    public Opera getOperaDaMovimentare() {
        return operaDaMovimentare;
    }

    /**
     * Inizializza il JFrame
     * @param nuovaFattura true se l'operazione da effettuare è di inserimento
     */
    public void init(boolean nuovaFattura) {
        this.nuovaFattura = nuovaFattura;
        if (!nuovaFattura) {
            titoloFinestra = "Modifica della fattura '" + fattura.toString() + "'";
        } else {
            titoloFinestra = "Nuova fattura";
        }
        if (!initialized) {
            initComponents();
            initialized = true;
        }
        initValues();
        operaDaMovimentare = new Opera();
    }

    /**
     * Imposta <code>editable</code> la proprietà editable di JTextField e JTextArea del JFrame
     * @param editable
     */
    public void setEditable(boolean editable) {
        proformajCheckBox.setEnabled(editable);
        clientejComboBox.setEnabled(editable);
        scontojTextField.setEditable(editable);
        applicaScontojButton.setEnabled(editable);
        salvajButton.setVisible(editable);
        aggiungiOperajButton.setEnabled(editable);
        rimuoviOperajButton.setEnabled(editable);
        artistajComboBox.setEnabled(editable);
        ricercajButton.setEnabled(editable);
        resetjButton.setVisible(editable);
        if (!editable) {
            salvaStampajButton.setText("Stampa");
            cancellajButton.setText("Ok");
        } else {
            salvaStampajButton.setText("Salva e stampa");
            cancellajButton.setText("Cancella");
        }
    }

    /**
     * Inizializza i valori della fattura qualora l'operazione non sia di inserimento, ma di modifica o visualizzazione
     */
    private void initValues() {
        numeroFatturajTextField.setText("" + fattura.getNumeroFattura());
        giornojTextField.setText("" + fattura.getDataFattura().getGiorno());
        mesejTextField.setText("" + fattura.getDataFattura().getMese());
        annojTextField.setText("" + fattura.getDataFattura().getAnno());
        proformajCheckBox.setSelected(fattura.isProforma());
        clientejComboBox.setSelectedItem(fattura.getCliente());
        scontojTextField.setText(df.format(fattura.getSconto()));
        this.setTitle(titoloFinestra);
    }

    /**
     * Aggiorna la tabella con le opere disponibili
     * @param opereDisponibili le opere disponibili in galleria
     */
    public void updateOpereDisponibilijTable(Vector<Opera> opereDisponibili) {
        resetjTable(opereDisponibiliModel);
        if (opereDisponibili.size() > 0) {
            for (Opera op : opereDisponibili) {
                float prezzoFloat = op.getPrezzo();
                String prezzo = "";
                if (prezzoFloat != -1) {
                    prezzo += df.format(prezzoFloat);
                }
                opereDisponibiliModel.addRow(new Object[]{op.getCodiceOpera(), op.getTitolo(), op.getArtista().toString(), prezzo});
            }
        }
    }

    /**
     * Aggiorna la tabella con le opere inserite in fattura
     * @param opereInFattura le opere inserite in fattura
     */
    public void updateOpereInFatturajTable(Vector<Opera> opereInFattura) {
        float imponibile = 0;
        resetjTable(opereInFatturaModel);
        if (opereInFattura.size() > 0) {
            for (Opera op : opereInFattura) {
                float prezzoFloat = op.getPrezzo();
                imponibile += prezzoFloat;
                String prezzo = "" + df.format(prezzoFloat);
                opereInFatturaModel.addRow(new Object[]{op.getCodiceOpera(), op.getTitolo(), op.getArtista().toString(), prezzo});
            }
        }
        imponibilejTextField.setText(df.format(imponibile));
        calcolaValori();
    }

    /**
     * Azzera la tabella
     * @param t il DefaultTableModel della tabella da azzerare
     */
    private void resetjTable(DefaultTableModel t) {
        int j = t.getRowCount();
        for (int k = 0; k < j; k++) {
            t.removeRow(0);
        }
    }

    /**
     * Calcola i valori di sconto e imponibile.
     */
    private void calcolaValori() {
        String scontoString = scontojTextField.getText();
        scontoString = scontoString.replaceAll(",", ".");
        String imponibileString = imponibilejTextField.getText();
        imponibileString = imponibileString.replaceAll(",", ".");
        float scontoPercentuale = 0;
        float sconto = 0;
        float imponibile = Float.parseFloat(imponibileString);
        if (!scontoString.isEmpty()) {
            scontoPercentuale = Float.parseFloat(scontoString);
            sconto = imponibile * scontoPercentuale / 100;
        }
        float iva = (imponibile - sconto) * 0.2f;
        float totale = imponibile - sconto + iva;

        ivajTextField.setText(df.format(iva));
        totalejTextField.setText(df.format(totale));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        numeroFatturajLabel = new javax.swing.JLabel();
        dataFatturajLabel = new javax.swing.JLabel();
        clientejLabel = new javax.swing.JLabel();
        opereDisponibilijScrollPane = new javax.swing.JScrollPane();
        opereDisponibilijTable = new javax.swing.JTable();
        numeroFatturajTextField = new javax.swing.JTextField();
        annojTextField = new javax.swing.JTextField();
        slash2jLabel = new javax.swing.JLabel();
        mesejTextField = new javax.swing.JTextField();
        slash1jLabel = new javax.swing.JLabel();
        giornojTextField = new javax.swing.JTextField();
        clientejComboBox = new javax.swing.JComboBox();
        imponibilejLabel = new javax.swing.JLabel();
        imponibilejTextField = new javax.swing.JTextField();
        scontojLabel = new javax.swing.JLabel();
        scontojTextField = new javax.swing.JTextField();
        ivajLabel = new javax.swing.JLabel();
        ivajTextField = new javax.swing.JTextField();
        totalejLabel = new javax.swing.JLabel();
        totalejTextField = new javax.swing.JTextField();
        proformajLabel = new javax.swing.JLabel();
        proformajCheckBox = new javax.swing.JCheckBox();
        opereInseritejLabel = new javax.swing.JLabel();
        opereDisponibilijLabel = new javax.swing.JLabel();
        ricercajLabel = new javax.swing.JLabel();
        artistajComboBox = new javax.swing.JComboBox();
        resetjButton = new javax.swing.JButton();
        salvaStampajButton = new javax.swing.JButton();
        salvajButton = new javax.swing.JButton();
        cancellajButton = new javax.swing.JButton();
        ricercajButton = new javax.swing.JButton();
        rimuoviOperajButton = new javax.swing.JButton();
        aggiungiOperajButton = new javax.swing.JButton();
        opereInFatturajScrollPane = new javax.swing.JScrollPane();
        opereInFatturajTable = new javax.swing.JTable();
        applicaScontojButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        numeroFatturajLabel.setText("Numero fattura*:");

        dataFatturajLabel.setText("Data fattura*:");

        clientejLabel.setText("Cliente*:");

        opereDisponibiliModel.addColumn("ID");
        opereDisponibiliModel.addColumn("Titolo");
        opereDisponibiliModel.addColumn("Artista");
        opereDisponibiliModel.addColumn("Prezzo");
        opereDisponibilijTable.setModel(opereDisponibiliModel);
        opereDisponibilijTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        col = opereDisponibilijTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(40);

        col = opereDisponibilijTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(130);

        col = opereDisponibilijTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(130);

        col = opereDisponibilijTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(70);
        opereDisponibilijTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        opereDisponibilijTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                opereDisponibilijTableMouseReleased(evt);
            }
        });
        opereDisponibilijScrollPane.setViewportView(opereDisponibilijTable);

        numeroFatturajTextField.setEditable(false);

        annojTextField.setEditable(false);

        slash2jLabel.setText("/");

        mesejTextField.setEditable(false);

        slash1jLabel.setText("/");

        giornojTextField.setEditable(false);

        clientejComboBox.setModel(new javax.swing.DefaultComboBoxModel(clienti));

        imponibilejLabel.setText("Imponibile (€):");

        imponibilejTextField.setEditable(false);

        scontojLabel.setText("Sconto (%):");

        scontojTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                scontojTextFieldKeyTyped(evt);
            }
        });

        ivajLabel.setText("IVA 20% (€)");

        ivajTextField.setEditable(false);

        totalejLabel.setText("TOTALE (€):");

        totalejTextField.setEditable(false);

        proformajLabel.setText("Fattura proforma:");

        opereInseritejLabel.setText("Opere inserite in fattura:");

        opereDisponibilijLabel.setText("Opere disponibili in galleria:");

        ricercajLabel.setText("Ricerca opere per Artista:");

        artistajComboBox.setModel(new javax.swing.DefaultComboBoxModel(artisti));

        resetjButton.setText("Reset ricerca per Artista");
        resetjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetjButtonActionPerformed(evt);
            }
        });

        salvaStampajButton.setText("Salva e stampa");
        salvaStampajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvaStampajButtonActionPerformed(evt);
            }
        });

        salvajButton.setText("Salva");
        salvajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvajButtonActionPerformed(evt);
            }
        });

        cancellajButton.setText("Cancella");
        cancellajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancellajButtonActionPerformed(evt);
            }
        });

        ricercajButton.setText("Ricerca");
        ricercajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ricercajButtonActionPerformed(evt);
            }
        });

        rimuoviOperajButton.setText(">>");
        rimuoviOperajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rimuoviOperajButtonActionPerformed(evt);
            }
        });

        aggiungiOperajButton.setText("<<");
        aggiungiOperajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aggiungiOperajButtonActionPerformed(evt);
            }
        });

        opereInFatturaModel.addColumn("ID");
        opereInFatturaModel.addColumn("Titolo");
        opereInFatturaModel.addColumn("Artista");
        opereInFatturaModel.addColumn("Prezzo");
        opereInFatturajTable.setModel(opereInFatturaModel);
        opereInFatturajTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        col = opereInFatturajTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(40);

        col = opereInFatturajTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(120);

        col = opereInFatturajTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(130);

        col = opereInFatturajTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(65);
        opereInFatturajTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        opereInFatturajTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                opereInFatturajTableMouseReleased(evt);
            }
        });
        opereInFatturajScrollPane.setViewportView(opereInFatturajTable);

        applicaScontojButton.setText("Applica");
        applicaScontojButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applicaScontojButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(proformajLabel)
                            .addComponent(clientejLabel)
                            .addComponent(opereInseritejLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ivajLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(opereInFatturajScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(numeroFatturajLabel)
                                        .addComponent(dataFatturajLabel))
                                    .addGap(13, 13, 13)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(proformajCheckBox, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(numeroFatturajTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(giornojTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(slash1jLabel)
                                            .addGap(18, 18, 18)
                                            .addComponent(mesejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(slash2jLabel)
                                            .addGap(18, 18, 18)
                                            .addComponent(annojTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(clientejComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(totalejLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(imponibilejLabel)
                                    .addComponent(scontojLabel))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ivajTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                    .addComponent(imponibilejTextField)
                                    .addComponent(totalejTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(scontojTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(applicaScontojButton)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aggiungiOperajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rimuoviOperajButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(opereDisponibilijLabel)
                                    .addComponent(ricercajLabel))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(artistajComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(ricercajButton))
                                    .addComponent(resetjButton)))
                            .addComponent(opereDisponibilijScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)))
                    .addComponent(salvajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(salvaStampajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 643, Short.MAX_VALUE)
                        .addComponent(cancellajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numeroFatturajLabel)
                            .addComponent(numeroFatturajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ricercajLabel)
                            .addComponent(artistajComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ricercajButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dataFatturajLabel)
                            .addComponent(giornojTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slash1jLabel)
                            .addComponent(mesejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slash2jLabel)
                            .addComponent(annojTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opereDisponibilijLabel)
                            .addComponent(resetjButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(proformajLabel)
                                    .addComponent(proformajCheckBox))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(clientejLabel)
                                    .addComponent(clientejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(opereInseritejLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opereInFatturajScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(imponibilejLabel)
                                    .addComponent(imponibilejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(scontojLabel)
                                    .addComponent(applicaScontojButton)
                                    .addComponent(scontojTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ivajLabel)
                                    .addComponent(ivajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalejLabel)
                                    .addComponent(totalejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addComponent(salvajButton))
                            .addComponent(opereDisponibilijScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(salvaStampajButton)
                            .addComponent(cancellajButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(aggiungiOperajButton)
                        .addGap(18, 18, 18)
                        .addComponent(rimuoviOperajButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Aggiorna l'istanza di fattura con i valori correnti.
     */
    private void salva() {
        fattura.setCliente((Cliente) clientejComboBox.getSelectedItem());
        fattura.setProforma(proformajCheckBox.isSelected());
        String scontoString = scontojTextField.getText();
        scontoString = scontoString.replaceAll(",", ".");
        String totaleString = totalejTextField.getText();
        totaleString = totaleString.replaceAll(",", ".");
        fattura.setSconto(Float.parseFloat(scontoString));
        fattura.setTotale(Float.parseFloat(totaleString));
    }

    /**
     * Action performed per il bottone "Salva"
     * @param evt
     */
private void salvajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvajButtonActionPerformed
    salva();
    firePropertyChange("salva_fattura", null, null);   
}//GEN-LAST:event_salvajButtonActionPerformed

    /**
     * Action performed per il bottone "Salva e stampa"
     * @param evt
     */
private void salvaStampajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvaStampajButtonActionPerformed
    salva();
    firePropertyChange("fattura_stampa", null, null);
}//GEN-LAST:event_salvaStampajButtonActionPerformed

    /**
     * Action performed per il bottone "Cancella"
     * @param evt
     */
private void cancellajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancellajButtonActionPerformed
    this.dispose();
}//GEN-LAST:event_cancellajButtonActionPerformed

    /**
     * Action performed per il bottone "Reset ricerca per artista"
     * @param evt
     */
private void resetjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetjButtonActionPerformed
    firePropertyChange("fattura_reset", null, null);
}//GEN-LAST:event_resetjButtonActionPerformed

    /**
     * Action performed per il bottone "Ricerca"
     * @param evt
     */
private void ricercajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ricercajButtonActionPerformed
    artistaFiltro = (Artista) artistajComboBox.getSelectedItem();
    firePropertyChange("fattura_cerca_opere_per_artista", null, null);
}//GEN-LAST:event_ricercajButtonActionPerformed

    /**
     * Action performed per il bottone "(aggiungi opera)"
     * @param evt
     */
private void aggiungiOperajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aggiungiOperajButtonActionPerformed
    if (operaDaMovimentare.getCodiceOpera() != -1) {
        if (checkFields()) {
            opereDisponibilijTable.clearSelection();
            fattura.setCliente((Cliente) clientejComboBox.getSelectedItem());
            firePropertyChange("fattura_aggiungi_opera", null, null);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Non è stata selezionata nessuna opera.", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_aggiungiOperajButtonActionPerformed
    /**
     * Action performed per il bottone "(rimuovi opera)"
     * @param evt
     */
private void rimuoviOperajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rimuoviOperajButtonActionPerformed
    if (operaDaMovimentare.getCodiceOpera() != -1) {
        if (checkFields()) {
            fattura.setCliente((Cliente) clientejComboBox.getSelectedItem());
            firePropertyChange("fattura_rimuovi_opera", null, null);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Non è stata selezionata nessuna opera.", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_rimuoviOperajButtonActionPerformed
    
    /**
     * Azione per l'evento <code>MouseReleased</code> sulla tabella contenente le opere disponibili
     * @param evt
     */
private void opereDisponibilijTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opereDisponibilijTableMouseReleased
    opereDisponibilijTable.changeSelection(opereDisponibilijTable.rowAtPoint(new Point(evt.getX(), evt.getY())), 0, false, false);
    final int i = opereDisponibilijTable.getSelectedRow();
    operaDaMovimentare.setCodiceOpera(Integer.parseInt((String) "" + opereDisponibiliModel.getValueAt(i, 0)));
}//GEN-LAST:event_opereDisponibilijTableMouseReleased

    /**
     * Azione per l'evento <code>MouseReleased</code> sulla tabella contenente le opere inserite in fattura
     * @param evt
     */
private void opereInFatturajTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opereInFatturajTableMouseReleased
    opereInFatturajTable.changeSelection(opereInFatturajTable.rowAtPoint(new Point(evt.getX(), evt.getY())), 0, false, false);
    final int i = opereInFatturajTable.getSelectedRow();
    operaDaMovimentare.setCodiceOpera(Integer.parseInt((String) "" + opereInFatturaModel.getValueAt(i, 0)));
}//GEN-LAST:event_opereInFatturajTableMouseReleased

    /**
     * Impedisce la digitazione di caratteri diversi da cifre e da '.'
     * @param evt
     */
private void scontojTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_scontojTextFieldKeyTyped
    char c = evt.getKeyChar();
    if ((!(c >= 48 && c <= 57)) && (c != 46)) {
        evt.setKeyChar('\u0008');
    }
}//GEN-LAST:event_scontojTextFieldKeyTyped

    /**
     * Action performed per il bottone "Applica"
     * @param evt
     */
private void applicaScontojButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applicaScontojButtonActionPerformed
    String scontoString = scontojTextField.getText();
    scontoString = scontoString.replaceAll(",", ".");
    float scontoPercentuale = Float.parseFloat(scontoString);
    scontojTextField.setText(df.format(scontoPercentuale));
    calcolaValori();
}//GEN-LAST:event_applicaScontojButtonActionPerformed

    /**
     * Verifica la correttezza dei campi
     * @return true se la verifica va a buon fine
     */
    private boolean checkFields() {
        if (!scontojTextField.getText().isEmpty()) {
            try {
                String scontoString = scontojTextField.getText();
                scontoString = scontoString.replaceAll(",", ".");
                Float.parseFloat(scontoString);
            } catch (NumberFormatException bfe) {
                JOptionPane.showMessageDialog(this, "Il campo 'Sconto' risulta scorretto.\nSi prega di correggere.", "Errore", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            scontojTextField.setText("0");
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aggiungiOperajButton;
    private javax.swing.JTextField annojTextField;
    private javax.swing.JButton applicaScontojButton;
    private javax.swing.JComboBox artistajComboBox;
    private javax.swing.JButton cancellajButton;
    private javax.swing.JComboBox clientejComboBox;
    private javax.swing.JLabel clientejLabel;
    private javax.swing.JLabel dataFatturajLabel;
    private javax.swing.JTextField giornojTextField;
    private javax.swing.JLabel imponibilejLabel;
    private javax.swing.JTextField imponibilejTextField;
    private javax.swing.JLabel ivajLabel;
    private javax.swing.JTextField ivajTextField;
    private javax.swing.JTextField mesejTextField;
    private javax.swing.JLabel numeroFatturajLabel;
    private javax.swing.JTextField numeroFatturajTextField;
    private javax.swing.JLabel opereDisponibilijLabel;
    private javax.swing.JScrollPane opereDisponibilijScrollPane;
    private javax.swing.JTable opereDisponibilijTable;
    private javax.swing.JScrollPane opereInFatturajScrollPane;
    private javax.swing.JTable opereInFatturajTable;
    private javax.swing.JLabel opereInseritejLabel;
    private javax.swing.JCheckBox proformajCheckBox;
    private javax.swing.JLabel proformajLabel;
    private javax.swing.JButton resetjButton;
    private javax.swing.JButton ricercajButton;
    private javax.swing.JLabel ricercajLabel;
    private javax.swing.JButton rimuoviOperajButton;
    private javax.swing.JButton salvaStampajButton;
    private javax.swing.JButton salvajButton;
    private javax.swing.JLabel scontojLabel;
    private javax.swing.JTextField scontojTextField;
    private javax.swing.JLabel slash1jLabel;
    private javax.swing.JLabel slash2jLabel;
    private javax.swing.JLabel totalejLabel;
    private javax.swing.JTextField totalejTextField;
    // End of variables declaration//GEN-END:variables

}
