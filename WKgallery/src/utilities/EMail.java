/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import exceptions.BadFormatException;

/**
 * Rappresenta un indirizzo email.
 * @author Marco Celesti
 */
public class EMail {

    String user;
    String dominio;
    String suffisso;

    /**
     * Crea una nuova istanza di EMail inizializzandola ai parametri passati.
     * @param user la prima parte di un indirizzo email, antecedente il simbolo @
     * @param dominio la seconda parte di un indirizzo email
     * @param suffisso il suffisso dell'indirizzo email (e.g. it, com, etc...)
     */
    public EMail(String user, String dominio, String suffisso) {
        this.user = user;
        this.dominio = dominio;
        this.suffisso = suffisso;
    }

    /**
     * Crea una nuova istanza di EMail non inizializzata.
     */
    public EMail() {
        this.user = "";
        this.dominio = "";
        this.suffisso = "";
    }

    /**
     * Metodo statico per la conversione di una stringa in un'istanza di EMail.
     * @param mailNotFormatted la stringa dell'indirizzo email
     * @return l'istanza di EMail
     * @throws exceptions.BadFormatException se la stringa è malformattata
     */
    public static EMail parseEMail(String mailNotFormatted) throws BadFormatException {
        if (mailNotFormatted == null || mailNotFormatted.isEmpty()) {
            return new EMail();
        }
        String user = "";
        String dominio = "";
        String suffisso = "";

        String[] user_dominio = mailNotFormatted.split("@");
        try {
            user = user_dominio[0];
            dominio = user_dominio[1];
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new BadFormatException("Formato mail non corretto");
        }
        if (user.isEmpty() || dominio.isEmpty()) {
            throw new BadFormatException("Formato mail non corretto");
        }
        String dominio_suffisso[] = dominio.split("\\.");
        int len = dominio_suffisso.length;
        if (dominio_suffisso[len - 2].isEmpty() ||
                dominio_suffisso[len - 1].isEmpty()) {
            throw new BadFormatException("Formato mail non corretto");
        }
        suffisso = dominio_suffisso[len - 1];
        dominio = dominio_suffisso[0];
        for (int i = 1; i < len - 1; i++) {
            dominio = dominio.concat(".");
            dominio = dominio.concat("" + dominio_suffisso[i]);
        }
        EMail email = new EMail(user, dominio, suffisso);
        return email;
    }

    /**
     * Permette la rappresentazione dell'istanza di EMail come stringa.
     * @return
     */
    @Override
    public String toString() {
        if (dominio.isEmpty() && suffisso.isEmpty() &&
                user.isEmpty()) {
            return "";
        }
        String stringEmail = "";
        stringEmail = stringEmail.concat(user);
        stringEmail = stringEmail.concat("@");
        stringEmail = stringEmail.concat(dominio);
        stringEmail = stringEmail.concat(".");
        stringEmail = stringEmail.concat(suffisso);
        return stringEmail;
    }

    /**
     * Metodo statico per determinare la correttezza di una stringa
     * @return true se l'oggetto non è inizializzato
     */
    public boolean isEmpty() {
        return (user.isEmpty() || dominio.isEmpty() || suffisso.isEmpty());
    }
}
