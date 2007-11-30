/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package msaccessimpl;

import abstractlayer.Cliente;
import abstractlayer.ClientePrivato;
import abstractlayer.ClienteProfessionista;
import java.util.Comparator;

/**
 *
 * @author Marco Celesti
 */
public class ClienteComparator implements Comparator<Cliente>{

    public int compare(Cliente cli1, Cliente cli2) {
        if((cli1 instanceof ClientePrivato) && (cli1 instanceof ClientePrivato)) {
            ClientePrivato cliP1 = (ClientePrivato) cli1;
            ClientePrivato cliP2 = (ClientePrivato) cli2;
            return cliP1.getCognome().compareToIgnoreCase(cliP2.getCognome());
        } else if ((cli1 instanceof ClienteProfessionista) && (cli1 instanceof ClienteProfessionista)) {
            ClienteProfessionista cliP1 = (ClienteProfessionista) cli1;
            ClienteProfessionista cliP2 = (ClienteProfessionista) cli2;
            return cliP1.getRagioneSociale().compareToIgnoreCase(cliP2.getRagioneSociale());
        }
        return 0;
    }
}
