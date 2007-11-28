/*
 * Artista.java
 *
 * Created on 14 ottobre 2007, 18.26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractlayer;

/**
 * Rappresenta l'artista ospitato dalla galleria.
 * @author Marco Celesti
 */
public class Artista {
    // member variables
    private int codiceArtista; // ogni nuovo artista ha il primo numero libero
    private String cognome;
    private String nome;
    private String noteBiografiche;

    /** 
     * Crea una nuova istanza di Artista inizializzandola ai parametri passati.
     * @param codArt Codice dell'artista
     * @param cognome Cognome
     * @param nome Nome
     * @param noteBio Note biografiche
     */
    public Artista(int codArt, String cognome, String nome, String noteBio) {
        this.codiceArtista = codArt; //Nel caso non fosse stato deciso esplicitamente, va gestito altrove
        this.cognome = cognome;
        this.nome = nome;
        this.noteBiografiche = noteBio;
    }

    /**
     * Crea una nuova istanza di Artista non inizializzata.
     */
    public Artista() {
        this.codiceArtista = -1;
        this.cognome = "";
        this.nome = "";
        this.noteBiografiche = "";
    }

    /** 
     * Metodo <i>get</i> per <code>codArt</code>. 
     * @return Codice dell'artista
     */
    public int getCodiceArtista() {
        return codiceArtista;
    }

    /**
     * Metodo <i>set</i> per <code>codiceArtista</code>.
     * @param codiceArtista Codice dell'artista
     */
    public void setCodiceArtista(int codiceArtista) {
        this.codiceArtista = codiceArtista;
    }

    /**
     * Metodo <i>get</i> per <code>cognome</code>.
     * @return Cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo <i>set</i> per <code>cognome</code>.
     * @param cognome Cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Metodo <i>get</i> per <code>nome</code>.
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo <i>set</i> per <code>nome</code>.
     * @param nome Nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo <i>get</i> per <code>noteBio</code>.
     * @return Note biografiche dell'artista
     */
    public String getNoteBiografiche() {
        return noteBiografiche;
    }

    /**
     * Metodo <i>set</i> per <code>noteBio</code>.
     * @param noteBiografiche Note biografiche dell'artista
     */
    public void setNoteBiografiche(String noteBiografiche) {
        this.noteBiografiche = noteBiografiche;
    }
}
