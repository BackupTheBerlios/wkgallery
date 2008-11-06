/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import java.util.Comparator;

/**
 * Classe Comparator per le classi Cliente, Artista, Fattura ed Opera
 * @param <T> 
 * @author Marco Celesti
 */
@SuppressWarnings("unchecked")
public class DefaultComparator<T extends Object> implements Comparator<T> {

    /**
     * Metodo generico per il confronto degli oggetti Artista, Cliente, Opera e Fattura.
     * @param o1 il primo oggetto
     * @param o2 il secondo oggetto
     * @return 0 se o1 è uguale a o2
     *         un valore positivo se o1 è maggiore di o2
     *         un valore negativo se o2 è maggiore di o1 
     */
    @Override
    public int compare(T o1, T o2) {
        if (o1 instanceof Artista && o2 instanceof Artista) {
            Artista a1 = (Artista) o1;
            Artista a2 = (Artista) o2;
            return a1.getCognome().compareToIgnoreCase(a2.getCognome());
        } else if (o1 instanceof Cliente && o2 instanceof Cliente) {
            Cliente c1 = (Cliente) o1;
            Cliente c2 = (Cliente) o2;
            return c1.getCognRsoc1().compareToIgnoreCase(c2.getCognRsoc1());
        } else if (o1 instanceof Fattura && o2 instanceof Fattura) {
            Fattura f1 = (Fattura) o1;
            Fattura f2 = (Fattura) o2;
            int anno1 = f1.getDataFattura().getAnno();
            int anno2 = f2.getDataFattura().getAnno();
            int num1 = f1.getNumeroFattura();
            int num2 = f2.getNumeroFattura();
            if (anno1 < anno2) {
                return -1;
            } else if (anno1 > anno2) {
                return 1;
            } else {
                if (num1 < num2) {
                    return -1;
                } else {
                    return 1;
                }
            }
        } else if (o1 instanceof Opera && o2 instanceof Opera) {
            Opera op1 = (Opera) o1;
            Opera op2 = (Opera) o2;
            return op1.getArtista().getCognome().compareToIgnoreCase(op2.getArtista().
                    getCognome());
        } else {
            return 0;
        }
    }
}
