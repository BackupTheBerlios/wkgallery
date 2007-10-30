/*
 * ClienteProfessionista.java
 *
 * Created on 20 ottobre 2007, 18.19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractlayer;

/**
 * Rapprensenta il cliente professionista della galleria.
 * @author Marco Celesti
 */
public class ClienteProfessionista extends Cliente {

    String ragioneSociale;
    String pIva;

    /** 
     * Crea una nuova istanza di ClienteProfessionista inizializzandola con i parametri passati.
     * @param ragioneSociale Ragione Sociale del cliente
     * @param pIva Partita Iva del cliente
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
    public ClienteProfessionista(int codiceCliente, String indirizzo, int nCiv, String regione, String stato, String tel1, String tel2, String cell1, String cell2, String mail1, String mail2, String note, String ragioneSociale, String pIva) {
        super(codiceCliente, indirizzo, nCiv, regione, stato, tel1, tel2, cell1, cell2, mail1, mail2, note);
        this.ragioneSociale = ragioneSociale;
        this.pIva = pIva;
    }
}
