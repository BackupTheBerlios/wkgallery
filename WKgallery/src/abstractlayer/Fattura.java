/*
 * Fattura.java
 *
 * Created on 20 ottobre 2007, 18.22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractlayer;

import java.util.Vector;
import utilities.Data;

/**
 * La fattura (o l'ordine) emessa per un cliente.
 * @author Marco Celesti
 */
public class Fattura {

    private int numeroFattura;
    private Data dataFattura;
    private Cliente cliente;
    private Vector<Opera> opere;
    private float sconto;
    private float totale;
    private boolean proforma;

    /**
     * Crea una nuova istanza di Fattura inizializzandola ai parametri passati.
     * @param numeroFattura il numero della fattura
     * @param dataFattura la data della fattura
     * @param cliente il cliente intestatario della fattura (o dell'ordine)
     * @param opere il vettore di opere contenute nella fattura
     * @param sconto lo sconto applicato alla fattura
     * @param proforma true se la fattura non è definitiva (proforma)
     */
    public Fattura(int numeroFattura, Data dataFattura, Cliente cliente,
            Vector<Opera> opere, float sconto, boolean proforma) {
        this.numeroFattura = numeroFattura;
        this.dataFattura = dataFattura;
        this.cliente = cliente;
        this.opere = opere;
        this.sconto = sconto;
        this.totale = 0.0f; // Il totale viene calcolato al momento dell'emissione della fattura
        this.proforma = proforma;
    }

    /**
     * Crea una nuova istanza di Fattura non inizializzata.
     */
    public Fattura() {
        this.numeroFattura = -1;
        this.dataFattura = new Data();
        this.cliente = new Cliente();
        this.opere = new Vector<Opera>();
        this.sconto = 0.0f;
        this.totale = 0.0f;
        this.proforma = true;
    }

    /**
     * Metodo <i>get</i> per <code>dataFattura</code>.
     * @return
     */
    public Data getDataFattura() {
        return dataFattura;
    }

    /**
     * Metodo <i>set</i> per <code>dataFattura</code>.
     * @param dataFattura
     */
    public void setDataFattura(Data dataFattura) {
        this.dataFattura = dataFattura;
    }

    /**
     * Metodo <i>get</i> per <code>cliente</code>.
     * @return il cliente cui è intestata la fattura
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Metodo <i>set</i> per <code>cliente</code>.
     * @param cliente il cliente cui è intestata la fattura
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Metodo <i>get</i> per <code>opere</code>.
     * @return il vettore di opere vhe appartengono alla fattura
     */
    public Vector<Opera> getOpere() {
        return opere;
    }

    /**
     * Metodo <i>set</i> per <code>opere</code>.
     * @param opere il vettore di opere vhe appartengono alla fattura
     */
    public void setOpere(Vector<Opera> opere) {
        this.opere = opere;
    }

    public boolean removeOpera(Opera opera) {
        return opere.removeElement(opera);
    }

    public void addOpera(Opera opera) {
        if (!opere.contains(opera)) {
            opere.addElement(opera);
        }
    }

    /**
     * Metodo <i>get</i> per <code>numeroFattura</code>.
     * @return il numero della fattura
     */
    public int getNumeroFattura() {
        return numeroFattura;
    }

    /**
     * Metodo <i>set</i> per <code>numeroFattura</code>.
     * @param numeroFattura il numero della fattura
     */
    public void setNumeroFattura(int numeroFattura) {
        this.numeroFattura = numeroFattura;
    }

    /**
     * Metodo <i>get</i> per <code>sconto</code>.
     * @return lo sconto applicato alla fattura
     */
    public float getSconto() {
        return sconto;
    }

    /**
     * Metodo <i>set</i> per <code>sconto</code>.
     * @param sconto lo sconto applicato alla fattura
     */
    public void setSconto(float sconto) {
        this.sconto = sconto;
    }

    /**
     * Metodo <i>get</i> per <code>totale</code>.
     * @return il totale della fattura
     */
    public float getTotale() {
        return totale;
    }

    /**
     * Metodo <i>set</i> per <code>totale</code>.
     * @param totale il totale della fattura
     */
    public void setTotale(float totale) {
        this.totale = totale;
    }

    /**
     * Metodo <i>get</i> per <code>proforma</code>.
     * @return true se la fattura è provvisoria (proforma)
     */
    public boolean isProforma() {
        return proforma;
    }

    /**
     * Metodo <i>set</i> per <code>proforma</code>.
     * @param proforma true se la fattura è provvisoria (proforma)
     */
    public void setProforma(boolean proforma) {
        this.proforma = proforma;
    }

    @Override
    public String toString() {
        return numeroFattura + "/" + dataFattura.getAnno();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Fattura) {
            Fattura f = (Fattura) o;
            if (f.getNumeroFattura() == this.numeroFattura && f.getDataFattura().getAnno() == this.dataFattura.getAnno()) {
                return true;
            }
        }
        return false;
    }
}
