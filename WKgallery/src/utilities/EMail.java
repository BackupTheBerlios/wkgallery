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
