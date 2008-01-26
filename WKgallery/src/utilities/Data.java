/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Marco Celesti
 */
public class Data {

    private int giorno;
    private int mese;
    private int anno;

    public Data() {
        this.giorno = -1;
        this.mese = -1;
        this.anno = -1;
    }

    public Data(int giorno, int mese, int anno) {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
    }

    public Data(String dataFormattata) {
        String[] aaaammgg = dataFormattata.split("-");
        this.anno = Integer.parseInt(aaaammgg[0]);
        this.mese = Integer.parseInt(aaaammgg[1]);
        this.giorno = Integer.parseInt(aaaammgg[2]);
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    public int getMese() {
        return mese;
    }

    public void setMese(int mese) {
        this.mese = mese;
    }

    public Date getDate() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(anno, mese-1, giorno);
        return new Date(cal.getTimeInMillis());
    }

    @Override
    public String toString() {
        return anno + "-" + mese + "-" + giorno;
    }
    
    public String toStringIta() {
        return giorno + "-" + mese + "-" + anno;
    }
}
