/*
 * MSAccessArtistaDAO.java
 *
 * Created on 14 ottobre 2007, 18.22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package msaccessimpl;

import abstractlayer.Artista;
import daorules.ArtistaDAO;
import java.sql.Connection;
import java.util.Collection;
import javax.sql.RowSet;

/**
 *
 * @author Marco Celesti
 */
public class MSAccessArtistaDAO implements ArtistaDAO{
    private Connection connection;
    private final String insertinto = "INSERT INTO Artista (CodiceArtista, Cognome, Nome, Note_biografinche) ";
    
    /** Creates a new instance of MSAccessArtistaDAO */
    public MSAccessArtistaDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertArtista(Artista artista) {
        int codArt = artista.getCodiceArtista();
        String cognome = artista.getCognome();
        String nome = artista.getNome();
        String noteBio = artista.getNoteBiografiche();
        String insert = insertinto + " VALUES (" + codArt + ", '" + cognome + "', '" + nome + "', '" + noteBio + "')";
        //connection.e
        //String insert = insertinto + " VALUES (4, 'Haider', 'Ali')";
        return 0;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deleteArtista(int codiceArtista) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Artista findArtista(int codiceArtista) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateArtista(Artista artista) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowSet selectArtistaRS(Artista criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection selectArtistaTO(Artista criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
}