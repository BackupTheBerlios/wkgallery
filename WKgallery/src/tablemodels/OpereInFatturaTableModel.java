package tablemodels;

import javax.swing.table.DefaultTableModel;

/**
 * Estende TableModel per la tabella contenente le opere contenute in una fattura.
 * @author Marco Celesti
 */
public class OpereInFatturaTableModel extends DefaultTableModel {

    /**
     * Sovrascrive il metodo di DefaultTableModel
     * @param rowIndex indice di riga della tabella
     * @param columnIndex indice di colonna della tabella
     * @return true se la cella è editabile
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
