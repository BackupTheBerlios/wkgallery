
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractlayer;

/**
 * Le regioni italiane e la regione "Estero".
 * @author Marco Celesti
 */
public enum Regione {

    /**
     * Regione Abruzzo
     */
    Abruzzo(1, "Abruzzo"),
    /**
     * Regione Basilicata
     */
    Basilicata(2, "Basilicata"),
    /**
     * Regione Calabria
     */
    Calabria(3, "Calabria"),
    /**
     * Regione Campania
     */
    Campania(4, "Campania"),
    /**
     * Regione Emilia Romagna
     */
    Emilia_Romagna(5, "Emilia_Romagna"),
    /**
     * Regione Friuli Venezia-Giulia
     */
    Friuli_Venezia_Giulia(6, "Friuli_Venezia_Giulia"),
    /**
     * Regione Lazio
     */
    Lazio(7, "Lazio"),
    /**
     * Regione Liguria
     */
    Liguria(8, "Liguria"),
    /**
     * Regione Lombardia
     */
    Lombardia(9, "Lombardia"),
    /**
     * Regione Marche
     */
    Marche(10, "Marche"),
    /**
     * Regione Molise
     */
    Molise(11, "Molise"),
    /**
     * Regione Piemonte
     */
    Piemonte(12, "Piemonte"),
    /**
     * Regione Puglia
     */
    Puglia(13, "Puglia"),
    /**
     * Regione Sardegna
     */
    Sardegna(14, "Sardegna"),
    /**
     * Regione Sicilia
     */
    Sicilia(15, "Sicilia"),
    /**
     * Regione Toscana
     */
    Toscana(16, "Toscana"),
    /**
     * Regione Trentino Alto-Adige
     */
    Trentino_Alto_Adige(17, "Trentino_Alto_Adige"),
    /**
     * Regione Umbria
     */
    Umbria(18, "Umbria"),
    /**
     * Regione Valle d'Aosta
     */
    Valle_d_Aosta(19, "Valle_d_Aosta"),
    /**
     * Regione Veneto
     */
    Veneto(20, "Veneto"),
    /**
     * Estero
     */
    Estero(100, "Estero");
    private int indiceRegione;
    private String nomeRegione;

    /**
     * Costruttore privato di Regione
     * @param indiceRegione l'indice della regione
     * @param nomeRegione il nome della regione
     */
    private Regione(int indiceRegione, String nomeRegione) {
        this.setIndiceRegione(indiceRegione);
        this.nomeRegione = nomeRegione;
    }

    /**
     * Metodo statico che permette di ottenere l'istanza di classe a partire dal
     * nome della regione.
     * @param nomeRegione il nome della regione 
     * @return l'istanza di classe
     */
    public static Regione getRegione(String nomeRegione) {
        for (Regione r : Regione.values()) {
            if (nomeRegione.equalsIgnoreCase(r.nomeRegione)) {
                return r;
            }
        }
        return Regione.Estero;
    }

    /**
     * Metodo <i>get</i> per <code>nomeRegione</code>.
     * @return il nome della regione
     */
    public String getNomeRegione() {
        return nomeRegione;
    }

    /**
     * Metodo <i>set</i> per <code>indiceRegione</code>.
     * @param indiceRegione l'indice che individua la regione
     */
    public void setIndiceRegione(int indiceRegione) {
        this.indiceRegione = indiceRegione;
    }

    /**
     * Metodo <i>get</i> per <code>indiceRegione</code>.
     * @return l'indice che individua la regione
     */
    public int getIndiceRegione() {
        return indiceRegione;
    }
}
