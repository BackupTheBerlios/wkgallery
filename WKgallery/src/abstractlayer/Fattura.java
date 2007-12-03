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

/**
 * La fattura (o l'ordine) emessa per un cliente.
 * @author Marco Celesti
 */
public class Fattura {

    private int numeroFattura;
    private int annoFattura;
    private Cliente cliente;
    private Vector<Opera> opere;

    /**
     * 
     * @param numeroFattura Numero della fattura
     * @param annoFattura Anno della fattura
     * @param cliente Cliente intestatario della fattura (o dell'ordine)
     * @param listaOpere Lista di opere presenti nella fattura (o nell'ordine)
     */
    public Fattura(int numeroFattura, int annoFattura, Cliente cliente,
            Vector<Opera> opere) {
        this.numeroFattura = numeroFattura;
        this.annoFattura = annoFattura;
        this.cliente = cliente;
        this.opere = opere;
    }

    public Fattura() {
        this.numeroFattura = -1;
        this.annoFattura = -1;
        this.cliente = null;
        this.opere = null;
    }

    public int getAnnoFattura() {
        return annoFattura;
    }

    public void setAnnoFattura(int annoFattura) {
        this.annoFattura = annoFattura;
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

    public int getNumeroFattura() {
        return numeroFattura;
    }

    public void setNumeroFattura(int numeroFattura) {
        this.numeroFattura = numeroFattura;
    }
}
