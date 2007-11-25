/*
 * Opera.java
 *
 * Created on 20 ottobre 2007, 17.25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractlayer;

/**
 *
 * @author Marco Celesti
 */
public class Opera {

    // member variables
    private String codiceOpera;
    private Artista artista;
    private Fattura fattura;
    private boolean venduto;
    private String tecnica;
    private String dimensioni;
    private String tipo;
    private String foto;

    public Opera(String codiceOpera, Artista artista, String tecnica, String dimensioni, String tipo, String foto, boolean venduto, Fattura fattura) {
        this.codiceOpera = codiceOpera;
        this.artista = artista;
        this.tecnica = tecnica;
        this.dimensioni = dimensioni;
        this.tipo = tipo;
        this.foto = foto;
        this.venduto = venduto;
        this.fattura = fattura;
    }

    public Opera() {
        this.codiceOpera = "";
        this.artista = null;
        this.tecnica = "";
        this.dimensioni = "";
        this.tipo = "";
        this.foto = "";
        this.venduto = false;
        this.fattura = null;
    }
    
    /**
     * Metodo <i>set</i> per <code>fattura</code> - la fattura o l'ordine in cui l'opera compare.
     * @param fattura La fattura o l'ordine in cui l'opera compare.
     */
    public void setFattura(Fattura fattura) {
        this.fattura = fattura;
    }

    public Fattura getFattura() {
        return fattura;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public boolean isVenduto() {
        return venduto;
    }

    public void setVenduto(boolean venduto) {
        this.venduto = venduto;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    public String getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodiceOpera() {
        return codiceOpera;
    }

    public void setCodiceOpera(String codiceOpera) {
        this.codiceOpera = codiceOpera;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
