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
    public static final int TODAY = 0;
    public static final int OTHER = 1;

    public Data(int day) {
        if (day == TODAY) {
            Calendar cal = new GregorianCalendar();
            giorno = cal.get(Calendar.DAY_OF_MONTH);
            mese = cal.get(Calendar.MONTH);
            anno = cal.get(Calendar.YEAR);
        } else if (day == OTHER) {
            giorno = -1;
            mese = -1;
            anno = -1;
        }
    }

    public Data(int giorno, int mese, int anno) {
        this.giorno = giorno;
        this.mese = mese - 1;
        this.anno = anno;
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
        Calendar cal = new GregorianCalendar(anno, mese, giorno);
        return new Date(cal.getTimeInMillis());
    }
    
    public String toString() {
        return giorno + "/" + mese + "/" + anno;
    }
}
