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
    
    private String cognome;
    private String nome;
    private String cf;

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

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCell1() {
        return cell1;
    }

    public void setCell1(String cell1) {
        this.cell1 = cell1;
    }

    public String getCell2() {
        return cell2;
    }

    public void setCell2(String cell2) {
        this.cell2 = cell2;
    }

    public int getCodiceCliente() {
        return codiceCliente;
    }

    public void setCodiceCliente(int codiceCliente) {
        this.codiceCliente = codiceCliente;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
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
