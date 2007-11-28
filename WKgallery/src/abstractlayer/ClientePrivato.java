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
     * @param codiceCliente Codice cliente
     * @param cognome Cognome del cliente
     * @param nome Nome del cliente
     * @param indirizzo Indirizzo del cliente
     * @param nCiv Numero civico
     * @param citta Città
     * @param provincia Provincia
     * @param regione Regione
     * @param stato Stato
     * @param tel1 Telefono n.1
     * @param tel2 Telefono n.2
     * @param cell1 Cellulare
     * @param mail1 Indirizzo e-mail n.1
     * @param mail2 Indirizzo e-mail n.2
     * @param cf Codice fiscale del cliente
     * @param note Note relative al cliente
     */
    public ClientePrivato(String codiceCliente, String cognome, String nome, String indirizzo, int nCiv, String citta, String provincia, Regione regione, String stato, String tel1, String tel2, String cell, String mail1, String mail2, String cf, String note) {
        super(codiceCliente, indirizzo, nCiv, citta, provincia, regione, stato, tel1, tel2, cell, mail1, mail2, note);
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

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
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
    
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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

    public Regione getRegione() {
        return regione;
    }

    public void setRegione(Regione regione) {
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
