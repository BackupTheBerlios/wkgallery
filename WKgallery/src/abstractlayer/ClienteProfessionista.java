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

    private String ragioneSociale;
    private String pIva;

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
    public ClienteProfessionista(String codiceCliente, String indirizzo, int nCiv, String citta, String provincia, String regione, String stato, String tel1, String tel2, String cell1, String cell2, String mail1, String mail2, String note, String ragioneSociale, String pIva) {
        super(codiceCliente, indirizzo, nCiv, citta, provincia, regione, stato, tel1, tel2, cell1, mail1, mail2, note);
        this.ragioneSociale = ragioneSociale;
        this.pIva = pIva;
        this.cell2 = cell2;
    }

    public String getPIva() {
        return pIva;
    }

    public void setPIva(String pIva) {
        this.pIva = pIva;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getCell1() {
        return cell;
    }

    public void setCell1(String cell1) {
        this.cell = cell1;
    }

    public String getCell2() {
        return cell2;
    }

    public void setCell2(String cell2) {
        this.cell2 = cell2;
    }

    public String getCodiceCliente() {
        return codiceCliente;
    }

    public void setCodiceCliente(String codiceCliente) {
        this.codiceCliente = codiceCliente;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    
    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }
    
    public String getMail1() {
        return mail1;
    }

    public void setMail1(String mail1) {
        this.mail1 = mail1;
    }

    public String getMail2() {
        return mail2;
    }

    public void setMail2(String mail2) {
        this.mail2 = mail2;
    }

    public int getNCiv() {
        return nCiv;
    }

    public void setNCiv(int nCiv) {
        this.nCiv = nCiv;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }
    
    
}
