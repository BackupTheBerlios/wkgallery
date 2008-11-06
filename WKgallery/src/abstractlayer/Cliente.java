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
    private int codiceCliente;
    private String cognRsoc1;
    private String nomeRsoc2;
    private String indirizzo;
    private String nCiv;
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

    /**
     * Crea una nuova istanza di Cliente non inizializzata.
     */
    public Cliente() {
        this.codiceCliente = -1;
        this.cognRsoc1 = "";
        this.nomeRsoc2 = "";
        this.indirizzo = "";
        this.nCiv = "";
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

    /**
     * Crea una nuova istanza di Cliente inizializzandola ai parametri passati.
     * @param codiceCliente il codice dell'artita
     * @param cognRsoc1 il cognome o la ragione sociale/1
     * @param nomeRsoc1 il nome o la ragione sociale/2
     * @param indirizzo l'indirizzo
     * @param nCiv il numero civico
     * @param cap il CAP
     * @param citta la città di residenza
     * @param provincia la provincia di residenza
     * @param regione la regioen di residenza
     * @param stato lo stato di residenza
     * @param tel1 telefono/1
     * @param tel2 telefono/2
     * @param cell1 cellulare/1
     * @param cell2 cellulare/2
     * @param mail1 email/1
     * @param mail2 email/2
     * @param cfPiva codice ficale o partita IVA
     * @param note note riferite al cliente
     * @param professionista true se il cliente è proessionista
     */
    public Cliente(int codiceCliente, String cognRsoc1, String nomeRsoc1,
            String indirizzo, String nCiv, String cap, String citta, String provincia,
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

    /**
     * Metodo <i>get</i> per <code>codiceCliente</code>.
     * @return il codice del cliente
     */
    public int getCodiceCliente() {
        return codiceCliente;
    }

    /**
     * Metodo <i>set</i> per <code>codiceArtista</code>.
     * @param codiceCliente il clodice del cliente
     */
    public void setCodiceCliente(int codiceCliente) {
        this.codiceCliente = codiceCliente;
    }

    /**
     * Metodo <i>get</i> per <code>cognRsoc1</code>.
     * @return il cognome o la ragione sociale/1
     */
    public String getCognRsoc1() {
        return cognRsoc1;
    }

    /**
     * Metodo <i>set</i> per <code>cognRsoc1</code>.
     * @param cognRsoc1 il cognome o la ragione sociale/1
     */
    public void setCognRsoc1(String cognRsoc1) {
        this.cognRsoc1 = cognRsoc1;
    }

    /**
     * Metodo <i>get</i> per <code>nomeRsoc2</code>.
     * @return il nome o la ragione sociale/2
     */
    public String getNomeRsoc2() {
        return nomeRsoc2;
    }

    /**
     * Metodo <i>set</i> per <code>nomeRsoc2</code>.
     * @param nomeRsoc2 il nome o la ragione sociale/2
     */
    public void setNomeRsoc2(String nomeRsoc2) {
        this.nomeRsoc2 = nomeRsoc2;
    }

    /**
     * Metodo <i>get</i> per <code>indirizzo</code>.
     * @return l'indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Metodo <i>set</i> per <code>indirizzo</code>.
     * @param indirizzo l'indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Metodo <i>get</i> per <code>nCiv</code>.
     * @return il numero civico
     */
    public String getNCiv() {
        return nCiv;
    }

    /**
     * Metodo <i>set</i> per <code>nCiv</code>.
     * @param nCiv il numero civico
     */
    public void setNCiv(String nCiv) {
        this.nCiv = nCiv;
    }

    /**
     * Metodo <i>get</i> per <code>citta</code>.
     * @return la città di residenza
     */
    public String getCitta() {
        return citta;
    }

    /**
     * Metodo <i>set</i> per <code>citta</code>.
     * @param citta la città di residenza
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /**
     * Metodo <i>get</i> per <code>provincia</code>.
     * @return la provincia di residenza
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Metodo <i>set</i> per <code>provincia</code>.
     * @param provincia la provincia di residenza
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Metodo <i>get</i> per <code>regione</code>.
     * @return la regione di residenza
     */
    public Regione getRegione() {
        return regione;
    }

    /**
     * Metodo <i>set</i> per <code>regione</code>.
     * @param regione la regione di residenza
     */
    public void setRegione(Regione regione) {
        this.regione = regione;
    }

    /**
     * Metodo <i>get</i> per <code>stato</code>.
     * @return lo stato di residenza
     */
    public String getStato() {
        return stato;
    }

    /**
     * Metodo <i>set</i> per <code>stato</code>.
     * @param stato lo stato di residenza
     */
    public void setStato(String stato) {
        this.stato = stato;
    }

    /**
     * Metodo <i>get</i> per <code>tel1</code>.
     * @return il telefono/1
     */
    public String getTel1() {
        return tel1;
    }

    /**
     * Metodo <i>set</i> per <code>tel1</code>.
     * @param tel1 il telefono/1
     */
    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    /**
     * Metodo <i>get</i> per <code>tel2</code>.
     * @return il telefono/2
     */
    public String getTel2() {
        return tel2;
    }

    /**
     * Metodo <i>set</i> per <code>tel2</code>.
     * @param tel2 il telefono/2
     */
    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    /**
     * Metodo <i>get</i> per <code>cell1</code>.
     * @return il cellulare/1
     */
    public String getCell1() {
        return cell1;
    }

    /**
     * Metodo <i>set</i> per <code>cell1</code>.
     * @param cell1 il cellulare/1
     */
    public void setCell1(String cell1) {
        this.cell1 = cell1;
    }

    /**
     * Metodo <i>get</i> per <code>cell2</code>.
     * @return il cellulare/2
     */
    public String getCell2() {
        return cell2;
    }

    /**
     * Metodo <i>set</i> per <code>cell2</code>.
     * @param cell2 il cellulare/2
     */
    public void setCell2(String cell2) {
        this.cell2 = cell2;
    }

    /**
     * Metodo <i>get</i> per <code>mail1</code>.
     * @return l'indirizzo email/1
     */
    public EMail getMail1() {
        return mail1;
    }

    /**
     * Metodo <i>set</i> per <code>mail1</code>.
     * @param mail1 l'indirizzo email/1
     */
    public void setMail1(EMail mail1) {
        this.mail1 = mail1;
    }

    /**
     * Metodo <i>get</i> per <code>mail2</code>.
     * @return l'indirizzo email/2
     */
    public EMail getMail2() {
        return mail2;
    }

    /**
     * Metodo <i>set</i> per <code>mail2</code>.
     * @param mail2 l'indirizzo email/2
     */
    public void setMail2(EMail mail2) {
        this.mail2 = mail2;
    }

    /**
     * Metodo <i>get</i> per <code>cfPiva</code>.
     * @return il codice fiscale o la partita IVA
     */
    public String getCfPiva() {
        return cfPiva;
    }

    /**
     * Metodo <i>set</i> per <code>cfPiva</code>.
     * @param cfPiva il codice fiscale o la partita IVA
     */
    public void setCfPiva(String cfPiva) {
        this.cfPiva = cfPiva;
    }

    /**
     * Metodo <i>get</i> per <code>note</code>.
     * @return le note aggiuntive
     */
    public String getNote() {
        return note;
    }

    /**
     * Metodo <i>set</i> per <code>note</code>.
     * @param note le note aggiuntive
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Metodo <i>get</i> per <code>professionista</code>.
     * @return true se il cliente è di tipo professionista
     */
    public boolean isProfessionista() {
        return professionista;
    }

    /**
     * Metodo <i>set</i> per <code>professionista</code>.
     * @param professionista true se il cliente è di tipo professionista
     */
    public void setProfessionista(boolean professionista) {
        this.professionista = professionista;
    }

    /**
     * Metodo <i>get</i> per <code>cap</code>.
     * @return il CAP
     */
    public String getCap() {
        return cap;
    }

    /**
     * Metodo <i>set</i> per <code>cap</code>.
     * @param cap il CAP
     */
    public void setCap(String cap) {
        this.cap = cap;
    }
    
    @Override
    public String toString() {
        return this.cognRsoc1 + " " + this.nomeRsoc2;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Cliente) {
            Cliente c = (Cliente) o;
            if (c.getCodiceCliente() == this.codiceCliente) {
                return true;
            }
        }
        return false;
    }
}
