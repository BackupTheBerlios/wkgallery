
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractlayer;

/**
 *
 * @author Marco Celesti
 */
public enum Regione {

    Abruzzo(1, "Abruzzo"), Basilicata(2, "Basilicata"), Calabria(3, "Calabria"),
    Campania(4, "Campania"), Emilia_Romagna(5, "Emilia_Romagna"),
    Friuli_Venezia_Giulia(6, "Friuli_Venezia_Giulia"), Lazio(7, "Lazio"),
    Liguria(8, "Liguria"), Lombardia(9, "Lombardia"), Marche(10, "Marche"),
    Molise(11, "Molise"), Piemonte(12, "Piemonte"), Puglia(13, "Puglia"),
    Sardegna(14, "Sardegna"), Sicilia(15, "Sicilia"),
    Toscana(16, "Toscana"), Trentino_Alto_Adige(17, "Trentino_Alto_Adige"),
    Umbria(18, "Umbria"), Valle_d_Aosta(19, "Valle_d_Aosta"),
    Veneto(20, "Veneto"), Estero(100, "Estero");
    private int indiceRegione;
    private String nomeRegione;

    private Regione(int indiceRegione, String nomeRegione) {
        this.indiceRegione = indiceRegione;
        this.nomeRegione = nomeRegione;
    }

    public static Regione getRegione(String nomeRegione) {
        for (Regione r : Regione.values()) {
            if (nomeRegione.equalsIgnoreCase(r.nomeRegione)) {
                return r;
            }
        }
        return Regione.Estero;
    }

    public String getNomeRegione() {
        return nomeRegione;
    }
}
