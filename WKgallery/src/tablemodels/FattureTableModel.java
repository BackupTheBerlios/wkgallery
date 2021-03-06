package tablemodels;

import javax.swing.table.DefaultTableModel;

/**
 * Estende TableModel per la tabella contenente le fatture.
 * @author Marco Celesti
 */
public class FattureTableModel extends DefaultTableModel {

    /**
     * Sovrascrive il metodo di DefaultTableModel
     * @param rowIndex indice di riga della tabella
     * @param columnIndex indice di colonna della tabella
     * @return true se la cella � editabile
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     * Sovrascrive il metodo di DefaultTableModel
     * @param columnIndex indice di colonna della tabella
     * @return la classe della colonna selezionata
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        if (columnIndex != 3) {
            return String.class;
        } else {
            return Boolean.class;
        }
    }
}
