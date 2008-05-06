/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import exceptions.BadFormatException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        this.giorno = 1;
        this.mese = 1;
        this.anno = 1980;
    }

    public Data(int giorno, int mese, int anno) throws BadFormatException {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
        checkdata();
    }

    public Data(String dataFormattata) throws BadFormatException {
        String[] aaaammgg = dataFormattata.split("-");
        this.anno = Integer.parseInt(aaaammgg[0]);
        this.mese = Integer.parseInt(aaaammgg[1]);
        this.giorno = Integer.parseInt(aaaammgg[2]);
        checkdata();
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
        cal.set(anno, mese - 1, giorno);
        return new Date(cal.getTimeInMillis());
    }

    public String getDataOdierna() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }
    
    @Override
    public String toString() {
        return anno + "-" + mese + "-" + giorno;
    }

    public String toStringIta() {
        if (mese < 10) {
            return giorno + "/0" + mese + "/" + anno;
        } else {
            return giorno + "/" + mese + "/" + anno;
        }
    }

    private void checkdata() throws BadFormatException{
        if (giorno < 1 || giorno > 31) {
            throw new BadFormatException("Giorno non corretto");
        }
        if (mese < 1 || mese > 12) {
            throw new BadFormatException("Mese non corretto");
        }
        if (anno < 1980 || anno > 2100) {
            throw new BadFormatException("Anno non corretto");
        }
    }
}
