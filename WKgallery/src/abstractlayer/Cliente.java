/*
 * Cliente.java
 *
 * Created on 20 ottobre 2007, 17.33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package abstractlayer;

/**
 * Rapprensenta il cliente della galleria.
 * @author Marco Celesti
 */
public abstract class Cliente {
    // member variables
    protected int codiceCliente;
    protected String indirizzo;
    protected int nCiv;
    protected String regione;
    protected String stato;
    protected String tel1;
    protected String tel2;
    protected String cell1;
    protected String cell2;
    protected String mail1;
    protected String mail2;
    protected String note;

    /** 
     * Crea una nuova istanza di Cliente inizializzandola con i parametri passati.
     * @param codiceCliente Codice cliente
     * @param indirizzo Indirizzo del cliente
     * @param nCiv Numero civico
     * @param regione Regione
     * @param stato Stato
     * @param tel1 Telefono n.1
     * @param tel2 Telefono n.2
     * @param cell1 Cellulare n.1
     * @param cell2 Cellulare n.2
     * @param mail1 Indirizzo e-mail n.1
     * @param mail2 Indirizzo e-mail n.2
     * @param note Note relative al cliente
     */
    public Cliente(int codiceCliente, String indirizzo, int nCiv, String regione, String stato, String tel1, String tel2, String cell1, String cell2, String mail1, String mail2, String note) {
        this.codiceCliente = codiceCliente;
        this.indirizzo = indirizzo;
        this.nCiv = nCiv;
        this.regione = regione;
        this.stato = stato;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.mail1 = mail1;
        this.mail2 = mail2;
        this.note = note;
    }
    
    /**
     * Crea una nuova istanza di cliente non inizializzata.
     */
    public Cliente() {
        this.codiceCliente = -1;
        this.indirizzo = "";
        this.nCiv = -1;
        this.regione = "";
        this.stato = "";
        this.tel1 = "";
        this.tel2 = "";
        this.cell1 = "";
        this.cell2 = "";
        this.mail1 = "";
        this.mail2 = "";
        this.note = "";
    }
    
}
