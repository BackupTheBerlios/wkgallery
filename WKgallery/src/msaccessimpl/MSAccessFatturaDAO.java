/*
 * MSAccessFatturaDao.java
 *
 * Created on 20 ottobre 2007, 18.21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package msaccessimpl;

import abstractlayer.Fattura;
import daorules.FatturaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessFatturaDAO implements FatturaDAO {

    private Connection connection;

    /** Creates a new instance of MSAccessFatturaDao */
    public MSAccessFatturaDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertFattura(Fattura fattura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deleteFattura(int numero_anno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Fattura findFattura(int numero_anno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateFattura(Fattura fattura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowSet selectFatturaRS(Fattura fattura) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectFatturaTO(Fattura criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Fattura staticFindFattura(int numeroFattura, int annoFattura, Connection conn) {
        Fattura fattura = new Fattura();
        String codiceCliente = "";
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement("SELECT * FROM Fattura WHERE Numero = ? AND Anno = ?");
            pstmt.setInt(1, numeroFattura);
            pstmt.setInt(2, annoFattura);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                fattura.setNumeroFattura(rs.getInt("Numero"));
                fattura.setAnnoFattura(rs.getInt("Anno"));
                codiceCliente = rs.getString("IDCliente");
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MSAccessArtistaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        if (codiceCliente.startsWith("Priv")) {
            //TODO trova cliente privato
        } else if (codiceCliente.startsWith("Prof")) {
            //TODO trova cliente professionista
        } else {
            //TODO se il codice non rispetta le regole...
        }
        return fattura;
    }
}
