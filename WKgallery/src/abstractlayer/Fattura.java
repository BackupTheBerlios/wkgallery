/*
 * Fattura.java
 *
 * Created on 20 ottobre 2007, 18.22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractlayer;

import java.util.List;
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

    /**
     * 
     * @param numeroFattura Numero della fattura
     * @param dataFattura Data della fattura
     * @param cliente Cliente intestatario della fattura (o dell'ordine)
     * @param listaOpere Lista di opere presenti nella fattura (o nell'ordine)
     */
    public Fattura(int numeroFattura, Data dataFattura, Cliente cliente,
            Vector<Opera> opere, float sconto, float totale) {
        this.numeroFattura = numeroFattura;
        this.dataFattura = dataFattura;
        this.cliente = cliente;
        this.opere = opere;
        this.sconto = sconto;
        this.totale = totale;
    }

    public Fattura() {
        this.numeroFattura = 0;
        this.dataFattura = new Data(Data.OTHER);
        this.cliente = null;
        this.opere = null;
        this.sconto = 0.0f;
        this.totale = 0.0f;
    }

    public Data getDataFattura() {
        return dataFattura;
    }

    public void setDataFattura(Data dataFattura) {
        this.dataFattura = dataFattura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vector<Opera> getOpere() {
        return opere;
    }

    public void setOpere(Vector<Opera> opere) {
        this.opere = opere;
    }

    public int getNumeroFattura() {
        return numeroFattura;
    }

    public void setNumeroFattura(int numeroFattura) {
        this.numeroFattura = numeroFattura;
    }

    public float getSconto() {
        return sconto;
    }

    public void setSconto(float sconto) {
        this.sconto = sconto;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }
}
