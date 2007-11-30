/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package msaccessimpl;

import abstractlayer.Fattura;
import java.util.Comparator;

/**
 *
 * @author Marco Celesti
 */
public class FatturaComparator implements Comparator<Fattura> {

    public int compare(Fattura f1, Fattura f2) {
        int anno1 = f1.getAnnoFattura();
        int anno2 = f2.getAnnoFattura();
        int num1 = f1.getNumeroFattura();
        int num2 = f2.getNumeroFattura();      
        
        if (anno1 < anno2) return -1;
        else if (anno1 > anno2) return 1;
        else {
            if (num1 < num2) return -1;
            else return 1;
        } 
    }
}
