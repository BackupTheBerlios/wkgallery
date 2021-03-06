package gui;

import abstractlayer.Cliente;
import abstractlayer.Regione;
import exceptions.BadFormatException;
import java.awt.Color;
import javax.swing.JOptionPane;
import utilities.EMail;

/**
 * La finestra per l'Inserimento, Modifica e Visualizzazione di un cliente.
 * @author  Marco Celesti
 */
public class IMVClienteJFrame extends javax.swing.JFrame {

    private static IMVClienteJFrame imvCliente = null;
    private Cliente cliente = null;
    private String titoloFinestra = "";
    private boolean initialized = false;
    private boolean nuovoCliente = false;

    /**
     * La classe implementa il pattern Singleton.
     * @return l'istanza di tipo statico della classe
     */
    public static IMVClienteJFrame getInstance() {
        if (imvCliente == null) {
            imvCliente = new IMVClienteJFrame();
        }
        return imvCliente;
    }

    /** Costruttore privato di IMVClienteJFrame */
    private IMVClienteJFrame() {
    }

    /**
     * Metodo <i>set</i> per <code>cliente</code>.
     * @param cliente il cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Metodo <i>get</i> per <code>cliente</code>.
     * @return il cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Inizializza il JFrame
     * @param nuovoCliente true se l'operazione da effettuare � di inserimento
     */
    public void init(boolean nuovoCliente) {
        this.nuovoCliente = nuovoCliente;
        if (!nuovoCliente) {
            titoloFinestra = "Modifica del cliente '" + cliente.toString() + "'";
        } else {
            titoloFinestra = "Nuovo cliente";
        }
        if (!initialized) {
            initComponents();
            initialized = true;
        }
        initValues();
    }

    /**
     * Imposta <code>editable</code> la propriet� editable di JTextField e JTextArea del JFrame
     * @param editable
     */
    public void setEditable(boolean editable) {
        cognomejTextField.setEditable(editable);
        nomejTextField.setEditable(editable);
        indirizzojTextField.setEditable(editable);
        ncivjTextField.setEditable(editable);
        cittajTextField.setEditable(editable);
        provinciajTextField.setEditable(editable);
        regionejComboBox.setEnabled(editable);
        statojTextField.setEditable(editable);
        capjTextField.setEditable(editable);
        pivacfjTextField.setEditable(editable);
        professionistajCheckBox.setEnabled(editable);
        tel1jTextField.setEditable(editable);
        tel2jTextField.setEditable(editable);
        cell1jTextField.setEditable(editable);
        cell2jTextField.setEditable(editable);
        mail1jTextField.setEditable(editable);
        mail2jTextField.setEditable(editable);
        notejTextArea.setEditable(editable);
        salvajButton.setVisible(editable);
        if (!editable) {
            cancellajButton.setText("Ok");
            notejTextArea.setBackground(new Color(224, 223, 227));
        } else {
            cancellajButton.setText("Cancella");
            notejTextArea.setBackground(Color.WHITE);
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

        codiceClientejLabel = new javax.swing.JLabel();
        codiceClientejTextField = new javax.swing.JTextField();
        cognomejLabel = new javax.swing.JLabel();
        cognomejTextField = new javax.swing.JTextField();
        nomejLabel = new javax.swing.JLabel();
        indirizzojLabel = new javax.swing.JLabel();
        ncivjLabel = new javax.swing.JLabel();
        cittajLabel = new javax.swing.JLabel();
        provinciajLabel = new javax.swing.JLabel();
        regionejLabel = new javax.swing.JLabel();
        statojLabel = new javax.swing.JLabel();
        capjLabel = new javax.swing.JLabel();
        pivacfjLabel = new javax.swing.JLabel();
        nomejTextField = new javax.swing.JTextField();
        indirizzojTextField = new javax.swing.JTextField();
        ncivjTextField = new javax.swing.JTextField();
        cittajTextField = new javax.swing.JTextField();
        provinciajTextField = new javax.swing.JTextField();
        statojTextField = new javax.swing.JTextField();
        capjTextField = new javax.swing.JTextField();
        pivacfjTextField = new javax.swing.JTextField();
        regionejComboBox = new javax.swing.JComboBox();
        tel1jLabel = new javax.swing.JLabel();
        tel2jLabel = new javax.swing.JLabel();
        cell1jLabel = new javax.swing.JLabel();
        cell2jLabel = new javax.swing.JLabel();
        mail1jLabel = new javax.swing.JLabel();
        mail2jLabel = new javax.swing.JLabel();
        notejLabel = new javax.swing.JLabel();
        tel1jTextField = new javax.swing.JTextField();
        tel2jTextField = new javax.swing.JTextField();
        cell1jTextField = new javax.swing.JTextField();
        cell2jTextField = new javax.swing.JTextField();
        mail1jTextField = new javax.swing.JTextField();
        mail2jTextField = new javax.swing.JTextField();
        jScrollPane = new javax.swing.JScrollPane();
        notejTextArea = new javax.swing.JTextArea();
        salvajButton = new javax.swing.JButton();
        cancellajButton = new javax.swing.JButton();
        professionistajLabel = new javax.swing.JLabel();
        professionistajCheckBox = new javax.swing.JCheckBox();
        campiObbligatorijLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        codiceClientejLabel.setText("Codice cliente*:");

        codiceClientejTextField.setEditable(false);

        cognomejLabel.setText("Cognome / Rag. soc. 1*:");

        nomejLabel.setText("Nome / Rag. soc. 2:");

        indirizzojLabel.setText("Indirizzo:");

        ncivjLabel.setText("Numero civico:");

        cittajLabel.setText("Citt�:");

        provinciajLabel.setText("Provincia:");

        regionejLabel.setText("Regione:");

        statojLabel.setText("Stato:");

        capjLabel.setText("C.A.P.");

        pivacfjLabel.setText("P.IVA / C.F.");

        statojTextField.setText("Italia");

        regionejComboBox.setModel(new javax.swing.DefaultComboBoxModel(abstractlayer.Regione.values()));
        regionejComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                regionejComboBoxItemStateChanged(evt);
            }
        });

        tel1jLabel.setText("Telefono 1:");

        tel2jLabel.setText("Telefono 2:");

        cell1jLabel.setText("Cellulare 1:");

        cell2jLabel.setText("Cellulare 2:");

        mail1jLabel.setText("Email 1:");

        mail2jLabel.setText("Email 2:");

        notejLabel.setText("Note:");

        notejTextArea.setColumns(20);
        notejTextArea.setRows(5);
        notejTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane.setViewportView(notejTextArea);

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

        professionistajLabel.setText("Professionista?");

        campiObbligatorijLabel.setText("* Campi obbligatori");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(salvajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 438, Short.MAX_VALUE)
                        .addComponent(cancellajButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codiceClientejLabel)
                            .addComponent(cognomejLabel)
                            .addComponent(nomejLabel)
                            .addComponent(indirizzojLabel)
                            .addComponent(ncivjLabel)
                            .addComponent(cittajLabel)
                            .addComponent(provinciajLabel)
                            .addComponent(regionejLabel)
                            .addComponent(statojLabel)
                            .addComponent(capjLabel)
                            .addComponent(pivacfjLabel)
                            .addComponent(professionistajLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(professionistajCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 378, Short.MAX_VALUE)
                                .addComponent(campiObbligatorijLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(regionejComboBox, 0, 214, Short.MAX_VALUE)
                                    .addComponent(pivacfjTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(cognomejTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(codiceClientejTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(nomejTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(indirizzojTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(ncivjTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(cittajTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(provinciajTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(statojTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(capjTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tel1jLabel)
                                    .addComponent(tel2jLabel)
                                    .addComponent(cell1jLabel)
                                    .addComponent(cell2jLabel)
                                    .addComponent(mail1jLabel)
                                    .addComponent(mail2jLabel)
                                    .addComponent(notejLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(mail2jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(mail1jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(cell2jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(cell1jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(tel2jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(tel1jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codiceClientejLabel)
                    .addComponent(codiceClientejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cognomejLabel)
                    .addComponent(cognomejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomejLabel)
                    .addComponent(nomejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tel1jLabel)
                    .addComponent(tel1jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(indirizzojLabel)
                    .addComponent(indirizzojTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tel2jLabel)
                    .addComponent(tel2jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ncivjLabel)
                    .addComponent(ncivjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cell1jLabel)
                    .addComponent(cell1jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cittajLabel)
                    .addComponent(cittajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cell2jLabel)
                    .addComponent(cell2jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(provinciajLabel)
                    .addComponent(provinciajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mail1jLabel)
                    .addComponent(mail1jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regionejLabel)
                    .addComponent(regionejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mail2jLabel)
                    .addComponent(mail2jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statojLabel)
                            .addComponent(statojTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(notejLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(capjLabel)
                            .addComponent(capjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pivacfjLabel)
                            .addComponent(pivacfjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(professionistajLabel)
                            .addComponent(professionistajCheckBox)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(campiObbligatorijLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salvajButton)
                    .addComponent(cancellajButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Inizializza i valori dell'artista qualora l'operazione non sia di inserimento, ma di modifica o visualizzazione
     */
    private void initValues() {
        if (!nuovoCliente) {
            codiceClientejTextField.setText("" + cliente.getCodiceCliente());
            cognomejTextField.setText(cliente.getCognRsoc1());
            nomejTextField.setText(cliente.getNomeRsoc2());
            indirizzojTextField.setText(cliente.getIndirizzo());
            ncivjTextField.setText(cliente.getNCiv());
            cittajTextField.setText(cliente.getCitta());
            provinciajTextField.setText(cliente.getProvincia());
            regionejComboBox.setSelectedItem(cliente.getRegione());
            statojTextField.setText(cliente.getStato());
            capjTextField.setText(cliente.getCap());
            pivacfjTextField.setText(cliente.getCfPiva());
            professionistajCheckBox.setSelected(cliente.isProfessionista());
            tel1jTextField.setText(cliente.getTel1());
            tel2jTextField.setText(cliente.getTel2());
            cell1jTextField.setText(cliente.getCell1());
            cell2jTextField.setText(cliente.getCell2());
            mail1jTextField.setText(cliente.getMail1().toString());
            mail2jTextField.setText(cliente.getMail2().toString());
            notejTextArea.setText(cliente.getNote());
        } else {
            codiceClientejTextField.setText("");
            cognomejTextField.setText("");
            nomejTextField.setText("");
            indirizzojTextField.setText("");
            ncivjTextField.setText("");
            cittajTextField.setText("");
            provinciajTextField.setText("");
            regionejComboBox.setSelectedItem(Regione.Lombardia);
            statojTextField.setText("Italia");
            capjTextField.setText("");
            pivacfjTextField.setText("");
            professionistajCheckBox.setSelected(false);
            tel1jTextField.setText("");
            tel2jTextField.setText("");
            cell1jTextField.setText("");
            cell2jTextField.setText("");
            mail1jTextField.setText("");
            mail2jTextField.setText("");
            notejTextArea.setText("");
        }
        this.setTitle(titoloFinestra);
    }

    /**
     * Action performed per il bottone "Salva"
     * @param evt
     */
private void salvajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvajButtonActionPerformed
    if (checkFields()) {
        cliente.setCognRsoc1(cognomejTextField.getText());
        cliente.setNomeRsoc2(nomejTextField.getText());
        cliente.setIndirizzo(indirizzojTextField.getText());
        cliente.setNCiv(ncivjTextField.getText());
        cliente.setCitta(cittajTextField.getText());
        cliente.setProvincia(provinciajTextField.getText());
        cliente.setRegione((Regione) regionejComboBox.getSelectedItem());
        String stato = statojTextField.getText();
        if (stato.isEmpty() && (Regione) regionejComboBox.getSelectedItem() != Regione.Estero) {
            stato = "Italia";
        }
        cliente.setStato(stato);
        cliente.setCap(capjTextField.getText());
        cliente.setCfPiva(pivacfjTextField.getText().toUpperCase());
        cliente.setTel1(tel1jTextField.getText());
        cliente.setTel2(tel2jTextField.getText());
        cliente.setCell1(cell1jTextField.getText());
        cliente.setCell2(cell2jTextField.getText());
        try {
            cliente.setMail1(EMail.parseEMail(mail1jTextField.getText()));
            cliente.setMail2(EMail.parseEMail(mail2jTextField.getText()));
        } catch (BadFormatException ex) {
        }
        cliente.setProfessionista(professionistajCheckBox.isSelected());
        cliente.setNote(notejTextArea.getText());
        firePropertyChange("salva_cliente", null, null);
    }
}//GEN-LAST:event_salvajButtonActionPerformed

    /**
     * Action performed per il bottone "Cancella"
     * @param evt
     */
private void cancellajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancellajButtonActionPerformed
    this.dispose();
}//GEN-LAST:event_cancellajButtonActionPerformed

    /**
     * Scrive "Italia" nel campo "Stato" qualora non sia selezionato "Estero"
     * @param evt
     */
private void regionejComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_regionejComboBoxItemStateChanged
    Regione regione = (Regione) evt.getItem();
    if (regione != Regione.Estero) {
        statojTextField.setText("Italia");
    } else {
        statojTextField.setText("");
    }
}//GEN-LAST:event_regionejComboBoxItemStateChanged

    /**
     * Verifica la correttezza dei campi e il rispetto di quelli obbligatori
     * @return true se la verifica va a buon fine
     */
    private boolean checkFields() {
        try {
            EMail.parseEMail(mail1jTextField.getText());
        } catch (BadFormatException bfe) {
            JOptionPane.showMessageDialog(this, "Il campo 'Email 1' risulta scorretto.\nSi prega di correggere."  , "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            EMail.parseEMail(mail2jTextField.getText());
        } catch (BadFormatException bfe) {
            JOptionPane.showMessageDialog(this, "Il campo 'Email 2' risulta scorretto.\nSi prega di correggere."  , "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cognomejTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il campo obbligatorio 'Cognome/Rag.soc. 1' non � stato compilato."  , "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel campiObbligatorijLabel;
    private javax.swing.JButton cancellajButton;
    private javax.swing.JLabel capjLabel;
    private javax.swing.JTextField capjTextField;
    private javax.swing.JLabel cell1jLabel;
    private javax.swing.JTextField cell1jTextField;
    private javax.swing.JLabel cell2jLabel;
    private javax.swing.JTextField cell2jTextField;
    private javax.swing.JLabel cittajLabel;
    private javax.swing.JTextField cittajTextField;
    private javax.swing.JLabel codiceClientejLabel;
    private javax.swing.JTextField codiceClientejTextField;
    private javax.swing.JLabel cognomejLabel;
    private javax.swing.JTextField cognomejTextField;
    private javax.swing.JLabel indirizzojLabel;
    private javax.swing.JTextField indirizzojTextField;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel mail1jLabel;
    private javax.swing.JTextField mail1jTextField;
    private javax.swing.JLabel mail2jLabel;
    private javax.swing.JTextField mail2jTextField;
    private javax.swing.JLabel ncivjLabel;
    private javax.swing.JTextField ncivjTextField;
    private javax.swing.JLabel nomejLabel;
    private javax.swing.JTextField nomejTextField;
    private javax.swing.JLabel notejLabel;
    private javax.swing.JTextArea notejTextArea;
    private javax.swing.JLabel pivacfjLabel;
    private javax.swing.JTextField pivacfjTextField;
    private javax.swing.JCheckBox professionistajCheckBox;
    private javax.swing.JLabel professionistajLabel;
    private javax.swing.JLabel provinciajLabel;
    private javax.swing.JTextField provinciajTextField;
    private javax.swing.JComboBox regionejComboBox;
    private javax.swing.JLabel regionejLabel;
    private javax.swing.JButton salvajButton;
    private javax.swing.JLabel statojLabel;
    private javax.swing.JTextField statojTextField;
    private javax.swing.JLabel tel1jLabel;
    private javax.swing.JTextField tel1jTextField;
    private javax.swing.JLabel tel2jLabel;
    private javax.swing.JTextField tel2jTextField;
    // End of variables declaration//GEN-END:variables

 
}
