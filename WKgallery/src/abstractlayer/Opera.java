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
    int codiceOpera;
    Artista artista;
    Fattura fattura;
    boolean venduto;
    String tecnica;
    String dimensioni;
    String tipo;
    

    /** Creates a new instance of Opera
     * @param codiceOpera Codice dell'opera
     * @param venduto <code>false</code> di default, <code>true</code> quando l'opera viene venduta
     * @param artista Artista realizzatore dell'opera
     */
    public Opera(int codiceOpera, Artista artista, String tecnica, String dimensioni, String tipo) {
        this.codiceOpera = codiceOpera;
        this.artista = artista;
        this.tecnica = tecnica;
        this.dimensioni = dimensioni;
        this.tipo = tipo;
        venduto = false;
    }

    /**
     * Metodo <i>set</i> per <code>fattura</code> - la fattura o l'ordine in cui l'opera compare.
     * @param fattura
     */
    public void setFattura(Fattura fattura) {
        this.fattura = fattura;
    }

    
    
    
    

}
