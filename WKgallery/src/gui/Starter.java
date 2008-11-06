package gui;

import daoabstract.DAOFactory;
import exceptions.ArchivioNonTrovatoException;
import filefilter.MDBFileFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import msaccessimpl.MSAccessDAOFactory;

/**
 * Si occupa delle fasi di inizializzazione.
 * @author Marco Celesti
 */
public class Starter {

    private File dbFile;
    private String pathDB = "";
    private String sourceDB = "";
    private boolean isNewFile = false;
    private boolean dbFound = false;
    private boolean pathToUpdate = false;
    private File fileParam = null;
    private FileInputStream fis = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;
    private FileOutputStream fout = null;
    private PrintStream ps = null;
    private DAOFactory daoFactory = null;
    private GUIController controller = null;
    private Connection connection = null;
    private JFrame parentJFrame = null;
    // DB ammessi
    private Object[] possibilities = {"MS_ACCESS"};

    /**
     * Crea un'istanza della classe.
     * @param controller l'oggetto GUIController da settare
     */
    public Starter(GUIController controller) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.controller = controller;
        parentJFrame = new JFrame();
    }

    /**
     * Inizializza il GUIController determinando origine dati e file dell'archivio.
     */
    public void setup() {
        fileParam = new File(".\\config.ini");
        try {
            isNewFile = fileParam.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (isNewFile) { //file inesistente
            getSource();
            findDB();
            controller.setDAOFactory(daoFactory);
        } else { //file esistente
            try {
                fis = new FileInputStream(fileParam);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            try {
                sourceDB = br.readLine();
                pathDB = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (sourceDB != null) {
                if (sourceDB.equals("MS_ACCESS")) {
                    daoFactory = (MSAccessDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MS_ACCESS);

                    findDB();
                    controller.setDAOFactory(daoFactory);
                } else if (sourceDB.equals("unaltrodatabase")) {
                    //daoFactory = (...) DAOFactory.getDAOFactory(DAOFactory.unaltrodatabase);
                    //controller.setDAOFactory(daoFactory);
                    //findDB();
                } else {
                    getSource();
                    findDB();
                    controller.setDAOFactory(daoFactory);
                }
            } else {
                getSource();
                findDB();
                controller.setDAOFactory(daoFactory);
            }

        }
    }

    /**
     * Permette l'impostazione dell'origine dati.
     */
    private void getSource() {
        String s = (String) JOptionPane.showInputDialog(
                parentJFrame, "Seleziona il formato del database", "Attenzione", JOptionPane.PLAIN_MESSAGE, null, possibilities, "MS_ACCESS");

        if ((s != null) && (s.length() > 0)) {
            try {
                fout = new FileOutputStream(fileParam);
                ps = new PrintStream(fout);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
            }
            sourceDB = s;
            if (sourceDB.equals("MS_ACCESS")) {
                daoFactory = (MSAccessDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MS_ACCESS);
            } else if (sourceDB.equals("unaltrodatabase")) {
                //daoFactory = (...) DAOFactory.getDAOFactory(DAOFactory.unaltrodatabase);
            }
            pathToUpdate = true;
        }
    }

    /**
     * Permette l'impostazione del file dell'archivio.
     */
    private void findDB() {
        while (!dbFound) {
            try {
                connection = daoFactory.getConnection(pathDB);
                dbFound = true;
            } catch (ArchivioNonTrovatoException ante) {
                pathToUpdate = true;
                JOptionPane.showMessageDialog(parentJFrame, "Il file del DB non è stato trovato.", "Attenzione!", JOptionPane.ERROR_MESSAGE);

                JFileChooser dbChooser = new JFileChooser();
                try {
                    // Create a File object containing the canonical path of the
                    // desired directory
                    File current = new File(new File(".").getCanonicalPath());

                    // Set the current directory
                    dbChooser.setCurrentDirectory(current);
                } catch (IOException e) {
                }
                if (sourceDB.equals("MS_ACCESS")) {
                    dbChooser.addChoosableFileFilter(new MDBFileFilter());
                }
                dbChooser.setDialogTitle("Seleziona l'archivio");
                dbChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = dbChooser.showOpenDialog(parentJFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    dbFile = dbChooser.getSelectedFile();
                    pathDB = dbFile.getPath();
                } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                    try {
                        fis.close();
                        fout.close();
                    } catch (IOException ex) {
                        Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
                }
            }
        }
        if (pathToUpdate) {
            if (fout == null && ps == null) {
                try {
                    fout = new FileOutputStream(fileParam);
                    ps = new PrintStream(fout);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ps.println(sourceDB);
            ps.println(pathDB);
        }
        try {
            if (fis != null) {
                fis.close();
            }
            if (fout != null) {
                fout.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(PrincipaleJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
