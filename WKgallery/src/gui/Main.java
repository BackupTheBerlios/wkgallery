package gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe principale contenente il metodo main.
 * @author Marco Celesti
 */
public class Main {

    /**
     * Avvia l'applicazione.
     * @param args non sono richiesti parametri
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
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
                
                Starter starterUp = new Starter(GUIController.getGUIController());
                starterUp.setup();
                
                GUIController.getGUIController().showPrincipaleJFrame();
            }
        });
    }
}
