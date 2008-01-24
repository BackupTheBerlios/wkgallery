/*
 * Cliente.java
 *
 * Created on 20 ottobre 2007, 17.33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractlayer;

import utilities.EMail;

/**
 * Rapprensenta il cliente della galleria.
 * @author Marco Celesti
 */
public class Cliente {
    // member variables
    private String codiceCliente;
    private String cognRsoc1;
    private String nomeRsoc2;
    private String indirizzo;
    private int nCiv;
    private String cap;
    private String citta;
    private String provincia;
    private Regione regione;
    private String stato;
    private String tel1;
    private String tel2;
    private String cell1;
    private String cell2;
    private EMail mail1;
    private EMail mail2;
    private String cfPiva;
    private String note;
    private boolean professionista;

    public Cliente() {
        this.codiceCliente = "";
        this.cognRsoc1 = "";
        this.nomeRsoc2 = "";
        this.indirizzo = "";
        this.nCiv = -1;
        this.cap = "";
        this.citta = "";
        this.provincia = "";
        this.regione = Regione.Estero;
        this.stato = "";
        this.tel1 = "";
        this.tel2 = "";
        this.cell1 = "";
        this.cell2 = "";
        this.mail1 = new EMail();
        this.mail2 = new EMail();
        this.cfPiva = "";
        this.note = "";
        this.professionista = false;
    }

    public Cliente(String codiceCliente, String cognRsoc1, String nomeRsoc1,
            String indirizzo, int nCiv, String cap, String citta, String provincia,
            Regione regione, String stato, String tel1, String tel2,
            String cell1, String cell2, EMail mail1, EMail mail2,
            String cfPiva, String note, boolean professionista) {
        this.codiceCliente = codiceCliente;
        this.cognRsoc1 = cognRsoc1;
        this.nomeRsoc2 = nomeRsoc1;
        this.indirizzo = indirizzo;
        this.nCiv = nCiv;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.regione = regione;
        this.stato = stato;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.mail1 = mail1;
        this.mail2 = mail2;
        this.cfPiva = cfPiva;
        this.note = note;
        this.professionista = professionista;
    }

    public String getCodiceCliente() {
        return codiceCliente;
    }

    public void setCodiceCliente(String codiceCliente) {
        this.codiceCliente = codiceCliente;
    }

    public String getCognRsoc1() {
        return cognRsoc1;
    }

    public void setCognRsoc1(String cognRsoc1) {
        this.cognRsoc1 = cognRsoc1;
    }

    public String getNomeRsoc2() {
        return nomeRsoc2;
    }

    public void setNomeRsoc2(String nomeRsoc2) {
        this.nomeRsoc2 = nomeRsoc2;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getNCiv() {
        return nCiv;
    }

    public void setNCiv(int nCiv) {
        this.nCiv = nCiv;
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

    public EMail getMail1() {
        return mail1;
    }

    public void setMail1(EMail mail1) {
        this.mail1 = mail1;
    }

    public EMail getMail2() {
        return mail2;
    }

    public void setMail2(EMail mail2) {
        this.mail2 = mail2;
    }

    public String getCfPiva() {
        return cfPiva;
    }

    public void setCfPiva(String cfPiva) {
        this.cfPiva = cfPiva;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isProfessionista() {
        return professionista;
    }

    public void setProfessionista(boolean professionista) {
        this.professionista = professionista;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }
}
