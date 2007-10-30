/*
 * ClientePrivato.java
 *
 * Created on 20 ottobre 2007, 18.18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package abstractlayer;

/**
 * Rapprensenta il cliente privato della galleria.
 * @author Marco Celesti
 */
public class ClientePrivato extends Cliente{
    
    String cognome;
    String nome;
    String cf;

    /**
     * Crea una nuova istanza di ClientePrivato inizializzandola con i parametri passati.
     * @param cognome Cognome del cliente
     * @param nome Nome del cliente
     * @param cf Codice fiscale del cliente
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
    public ClientePrivato(String cognome, String nome, String cf, int codiceCliente, String indirizzo, int nCiv, String regione, String stato, String tel1, String tel2, String cell1, String cell2, String mail1, String mail2, String note) {
        super(codiceCliente, indirizzo, nCiv, regione, stato, tel1, tel2, cell1, cell2, mail1, mail2, note);
        this.cognome = cognome;
        this.nome = nome;
        this.cf = cf;
    }
    
}
