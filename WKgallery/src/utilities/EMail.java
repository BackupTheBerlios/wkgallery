/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import exceptions.BadFormatException;

/**
 *
 * @author Marco Celesti
 */
public class EMail {

    String user;
    String dominio;
    String suffisso;

    public EMail(String user, String dominio, String suffisso) {
        this.user = user;
        this.dominio = dominio;
        this.suffisso = suffisso;
    }

    public EMail() {
        this.user = "";
        this.dominio = "";
        this.suffisso = "";
    }

    public static EMail toEMail(String mailNotFormatted) throws BadFormatException {
        if (mailNotFormatted == null) {
            return new EMail();
        }
        if (mailNotFormatted.length() != 0) {
            String actualUser = "";
            String actualDominio = "";
            String acualSuffisso = "";

            String[] user_dominio = mailNotFormatted.split("@");
            if (user_dominio[0].isEmpty() || user_dominio[1].isEmpty()) {
                throw new BadFormatException("Formato mail non corretto");
            }
            actualUser = user_dominio[0];
            String dominio_suffisso[] = user_dominio[1].split("\\.");
            int len = dominio_suffisso.length;
            if (dominio_suffisso[len - 2].isEmpty() ||
                    dominio_suffisso[len - 1].isEmpty()) {
                throw new BadFormatException("Formato mail non corretto");
            }
            acualSuffisso = dominio_suffisso[len - 1];
            actualDominio = dominio_suffisso[0];
            for (int i = 1; i < len - 1; i++) {
                actualDominio = actualDominio.concat(".");
                actualDominio = actualDominio.concat("" + dominio_suffisso[i]);
            }
            EMail email = new EMail(actualUser, actualDominio, acualSuffisso);
            return email;
        } else {
            return new EMail();
        }
    }

    public static String toString(EMail email) {
        if (email.dominio.isEmpty() && email.suffisso.isEmpty() &&
                email.user.isEmpty()) {
            return "";
        }
        String stringEmail = "";
        stringEmail = stringEmail.concat(email.user);
        stringEmail = stringEmail.concat("@");
        stringEmail = stringEmail.concat(email.dominio);
        stringEmail = stringEmail.concat(".");
        stringEmail = stringEmail.concat(email.suffisso);
        return stringEmail;
    }

    public static boolean isCorrect(String stringEmail) {
        //prova
        String prova;
        String[] user_dominio = stringEmail.split("@");
        if (user_dominio[0].isEmpty() || user_dominio[1].isEmpty()) {
            return false;
        }
        String[] dominio_suffisso = user_dominio[1].split(".");
        int len = dominio_suffisso.length;
        if (dominio_suffisso[len - 2].isEmpty() ||
                dominio_suffisso[len - 1].isEmpty()) {
            return false;
        }
        return true;
    }
}
