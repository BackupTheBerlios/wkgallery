/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import abstractlayer.Cliente;
import java.util.Comparator;

/**
 *
 * @author Marco Celesti
 */
public class ClienteComparator implements Comparator<Cliente> {

    public int compare(Cliente cli1, Cliente cli2) {
        return cli1.getCognRsoc1().compareToIgnoreCase(cli2.getCognRsoc1());
    }

}
