/*
 * Opera.java
 *
 * Created on 20 ottobre 2007, 17.25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractlayer;

import java.net.URL;

/**
 * Rappresenta l'opera presente in galleria oppure già venduta.
 * @author Marco Celesti
 */
public class Opera {

    // member variables
    private int codiceOpera;
    private Artista artista;
    private String titolo;
    private String tecnica;
    private String dimensioni;
    private String tipo;
    private URL foto;
    private float prezzo;

    /**
     * Crea una nuova istanza di Opera inizializzandola ai parametri passati.
     * @param codiceOpera il codice dell'opera
     * @param artista l'artista che ha realizzato l'opera
     * @param titolo il titolo dell'opera
     * @param tecnica la tecnica utilizzata per la realizzazione
     * @param dimensioni le dimensioni (e.g. 120x70)
     * @param tipo il tipo (pittura, scultura, etc)
     * @param foto l'indirizzo che individua la foto 
     * @param prezzo il prezzo dell'opera
     */
    public Opera(int codiceOpera, Artista artista, String titolo, String tecnica, String dimensioni, String tipo, URL foto, float prezzo) {
        this.codiceOpera = codiceOpera;
        this.artista = artista;
        this.tecnica = tecnica;
        this.dimensioni = dimensioni;
        this.tipo = tipo;
        this.foto = foto;
        this.prezzo = prezzo;
        this.titolo = titolo;
    }

    /**
     * Crea una nuova istanza di Opera non inizializzata.
     */
    public Opera() {
        this.codiceOpera = -1;
        this.artista = null;
        this.titolo = "";
        this.tecnica = "";
        this.dimensioni = "";
        this.tipo = "";
        this.foto = null;
        this.prezzo = -1.0f;
    }

    /**
     * Metodo <i>get</i> per <code>artista</code>.
     * @return l'artista che ha eseguito l'opera
     */
    public Artista getArtista() {
        return artista;
    }

    /**
     * Metodo <i>set</i> per <code>artista</code>.
     * @param artista l'artista che ha eseguito l'opera
     */
    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    /**
     * Metodo <i>get</i> per <code>tecnica</code>.
     * @return la tecnica di esecuzione
     */
    public String getTecnica() {
        return tecnica;
    }

    /**
     * Metodo <i>set</i> per <code>tecnica</code>.
     * @param tecnica la tecnica di esecuzione
     */
    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    /**
     * Metodo <i>get</i> per <code>dimensioni</code>.
     * @return le dimensioni dell'opera
     */
    public String getDimensioni() {
        return dimensioni;
    }

    /**
     * Metodo <i>set</i> per <code>dimensioni</code>.
     * @param dimensioni le dimensioni dell'opera
     */
    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    /**
     * Metodo <i>get</i> per <code>tipo</code>.
     * @return il tipo di opera
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Metodo <i>set</i> per <code>tipo</code>.
     * @param tipo il tipo di opera
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo <i>get</i> per <code>codiceOpera</code>.
     * @return il codice dell'opera
     */
    public int getCodiceOpera() {
        return codiceOpera;
    }

    /**
     * Metodo <i>set</i> per <code>codiceOpera</code>.
     * @param codiceOpera il codice dell'opera
     */
    public void setCodiceOpera(int codiceOpera) {
        this.codiceOpera = codiceOpera;
    }

    /**
     * Metodo <i>get</i> per <code>foto</code>.
     * @return la foto
     */
    public URL getFoto() {
        return foto;
    }

    /**
     * Metodo <i>set</i> per <code>foto</code>.
     * @param foto la foto
     */
    public void setFoto(URL foto) {
        this.foto = foto;
    }

    /**
     * Metodo <i>get</i> per <code>prezzo</code>.
     * @return il prezzo dell'opera
     */
    public float getPrezzo() {
        return prezzo;
    }

    /**
     * Metodo <i>set</i> per <code>prezzo</code>.
     * @param prezzo il prezzo dell'opera
     */
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * Metodo <i>get</i> per <code>titolo</code>.
     * @return il titolo dell'opera
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Metodo <i>set</i> per <code>titolo</code>.
     * @param titolo il titolo dell'opera
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Opera) {
            Opera op = (Opera) o;
            if (op.getCodiceOpera() == this.codiceOpera) {
                return true;
            }
        }
        return false;
    }
}
