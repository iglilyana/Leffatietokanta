package movies;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jenni Arovaara
 * @version 17.7.2019
 *
 * Apuluokka, joka parsii tietoja.
 */
public final class Helpers {
    
    /**
     * Minimi ikäsuositus
     */
    public static final int MIN_AGE = 0;
    
    /**
     * Maksimi ikäsuositus
     */
    public static final int MAX_AGE = 18;

    /**
     * Minimi leffan valmistusvuosi
     */
    public static final int MIN_YEAR = 1960;

    /**
     * Maksimi leffan valmistusvuosi
     */
    public static final int MAX_YEAR = 2050;
    
    private static Pattern imdbUrlPattern = Pattern.compile("^https://www.imdb.com/title/tt[0-9]+/$");
    
    /**
     * Yritetään parsia kokonaisluvuksi.
     * @param s parsittava merkkijono
     * @param oletus oletusluku
     * @return parsittu kokonaisluku tai oletusluku
     * @example
     * <pre name="test">
     *  tryParseInt("6", 0) === 6;
     *  tryParseInt("", 0) === 0;
     *  tryParseInt("abc", 0) === 0;
     * </pre>
     */
    public static int tryParseInt(String s, int oletus) {
        int paluuarvo;
        try {
            paluuarvo = Integer.parseInt(s);
        }
        catch(Exception e) {
            paluuarvo = oletus;
        }
        return paluuarvo;
    }
    
    /**
     * Yritetään parsia kokonaisluvuksi.
     * @param s parsittava merkkijono
     * @param min minimiarvo inklusiivinen
     * @param max maksimiarvo inklusiivinen
     * @param oletus oletusluku
     * @return parsittu kokonaisluku tai oletusluku
     * @example
     * <pre name="test">
     *  tryParseInt("6", 0, 12, 0) === 6;
     *  tryParseInt("", 0, 12, 0) === 0;
     *  tryParseInt("abc", 0, 12, 0) === 0;
     *  tryParseInt("13", 0, 12, 0) === 0;
     * </pre>
     */
    public static int tryParseInt(String s, int min, int max, int oletus) {
        int paluuarvo;
        try {
            paluuarvo = Integer.parseInt(s);
        }
        catch(Exception e) {
            paluuarvo = oletus;
        }
        if (paluuarvo < min || paluuarvo > max) {
            paluuarvo = oletus;
        }
        return paluuarvo;
    }
    
    /**
     * Intin muunnos Stringiksi, oletusarvolla tyhjä merkkijono.
     * @param value arvo
     * @param unlessValue arvo, joka muutetaan tyhjäksi merkkijonoksi
     * @return int merkkijonona tai tyhjä merkkijono
     * @example
     * <pre name="test">
     *  intToStringUnless(1, 0).equals("1");
     *  intToStringUnless(0, 0).equals("");
     * </pre>
     */
    public static String intToStringUnless(int value, int unlessValue) {
        return value != unlessValue ? String.valueOf(value) : "";
    }
    
    /**
     * Haetaan esim. pudotusvalikon vaihtoehdoista leffalle tallennettu vaihtoehto tai oletusarvo.
     * @param options vaihtoehdot
     * @param value leffalle tallennettu vaihtoehto
     * @param defaultIdx oletusindeksi
     * @return valinnan indeksi tai sen puuttuessa oletusindeksi
     * @example
     * <pre name="test">
     *  String[] options = new String[] {"kissa", "koira", "hevonen"};
     *  findOptionIndex(options, "koira", 0) === 1;
     *  findOptionIndex(options, "vauva", 0) === 0;
     * </pre>
     */
    public static int findOptionIndex(String[] options, String value, int defaultIdx) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].equalsIgnoreCase(value))
                return i;
        }
        return defaultIdx;
    }
    
    /**
     * Tarkistaa, onko annettu merkkijono uskottavahko IMDB url
     * @param url imdb url
     * @return onko urli uskottava
     * @example
     * <pre name="test">
     *  validateIMDBUrl(null) === false;
     *  validateIMDBUrl("") === false;
     *  validateIMDBUrl("asdfasijw") === false;
     *  validateIMDBUrl("https://www.imdb.com/title/tt1663143/") === true;
     * </pre>
     */
    public static boolean validateIMDBUrl(String url) {
        if (url == null)
            return false;
        Matcher matcher = imdbUrlPattern.matcher(url.trim());
        return matcher.matches();
    }
}
