package tablemodels;

import javax.swing.table.DefaultTableModel;

/**
 * Estende TableModel per la tabella contenente gli artisti.
 * @author Marco Celesti
 */
public class ArtistiTableModel extends DefaultTableModel {

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
