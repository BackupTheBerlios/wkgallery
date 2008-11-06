package utilities;

import exceptions.BadFormatException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Rappresenta la data con la convenzione italiana.
 * @author Marco Celesti
 */
public class Data {

    private int giorno;
    private int mese;
    private int anno;

    /**
     * Crea una nuova istanza di Data inizializzata alla data odierna.
     */
    public Data() {
        Calendar cal = new GregorianCalendar(Locale.ITALY);
        this.giorno = cal.get(Calendar.DAY_OF_MONTH);
        this.mese = cal.get(Calendar.MONTH) + 1;
        this.anno = cal.get(Calendar.YEAR);
    }

    /**
     * Crea una nuova istanza di Data inizializzandola ai parametri passati.
     * @param giorno il giorno
     * @param mese il mese
     * @param anno l'anno
     * @throws exceptions.BadFormatException se la data non è valida
     */
    public Data(int giorno, int mese, int anno) throws BadFormatException {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
        checkdata();
    }

    /**
     * Crea una nuova istanza di Opera a partire da una stringa formattata come segue:
     * <code>gg-mm-aaaa</code>.
     * @param dataFormattata la data formattata
     * @throws exceptions.BadFormatException se la data non è valida
     */
    public Data(String dataFormattata) throws BadFormatException {
        String[] aaaammgg = dataFormattata.split("-");
        this.anno = Integer.parseInt(aaaammgg[0]);
        this.mese = Integer.parseInt(aaaammgg[1]);
        this.giorno = Integer.parseInt(aaaammgg[2]);
        checkdata();
    }

    /**
     * Metodo <i>get</i> per <code>anno</code>.
     * @return l'anno
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Metodo <i>set</i> per <code>anno</code>.
     * @param anno l'anno
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }

    /**
     * Metodo <i>get</i> per <code>giorno</code>.
     * @return il giorno
     */
    public int getGiorno() {
        return giorno;
    }

    /**
     * Metodo <i>set</i> per <code>giorno</code>.
     * @param giorno il giorno
     */
    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    /**
     * Metodo <i>get</i> per <code>mese</code>.
     * @return il mese
     */
    public int getMese() {
        return mese;
    }

    /**
     * Metodo <i>set</i> per <code>mese</code>.
     * @param mese il mese
     */
    public void setMese(int mese) {
        this.mese = mese;
    }

    /**
     * Restituisce un'istanza di <code>java.sql.Date</code>.
     * @return l'istanza di <code>java.sql.Date</code>
     */
    public Date getDate() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(anno, mese - 1, giorno);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * Restituisce una rappresentazione della data nel formato gg-mm-aaaa
     * @return la data così formattata
     */
    @Override
    public String toString() {
        return anno + "-" + mese + "-" + giorno;
    }

    /**
     * Restituisce una rappresentazione della data nel formato gg/mm/aaaa
     * @return la data così formattata
     */
    public String toStringIt() {
        if (mese < 10) {
            return giorno + "/0" + mese + "/" + anno;
        } else {
            return giorno + "/" + mese + "/" + anno;
        }
    }

    public static Data parseData(String dataString) throws BadFormatException {
        String ggmmaaaa[] = dataString.split("/");
        return new Data(Integer.parseInt(ggmmaaaa[0]), Integer.parseInt(ggmmaaaa[1]), Integer.parseInt(ggmmaaaa[2]));
    }

    /**
     * Verifica la correttezza della data.
     * @throws exceptions.BadFormatException se la data risulta malformattata o non valida
     */
    private void checkdata() throws BadFormatException {
        if (giorno < 1 || giorno > 31) {
            throw new BadFormatException("Giorno non corretto");
        }
        if (mese < 1 || mese > 12) {
            throw new BadFormatException("Mese non corretto");
        }
        if (anno < 2008 || anno > 2100) {
            throw new BadFormatException("Anno non corretto");
        }
    }
}
