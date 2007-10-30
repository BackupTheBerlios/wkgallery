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

/**
 * La fattura (o l'ordine) emessa per un cliente.
 * @author Marco Celesti
 */
public class Fattura{
    
    int numeroFattura;
    int annoFattura;
    int numero_anno;
    Cliente cliente;
    List<Opera> listaOpere;

    /**
     * 
     * @param numeroFattura Numero della fattura
     * @param annoFattura Anno della fattura
     * @param cliente Cliente intestatario della fattura (o dell'ordine)
     * @param listaOpere Lista di opere presenti nella fattura (o nell'ordine)
     */
    public Fattura(int numeroFattura, int annoFattura, Cliente cliente, List<Opera> listaOpere) {
        this.numeroFattura = numeroFattura;
        this.annoFattura = annoFattura;
        this.cliente = cliente;
        this.listaOpere = listaOpere;
        String numeroanno = "" + numeroFattura + annoFattura;
        numero_anno = Integer.parseInt(numeroanno);
    }
    
    

}
