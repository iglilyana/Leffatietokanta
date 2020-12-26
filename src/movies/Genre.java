/**
 * 
 */
package movies;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Jenni Arovaara
 * @version 22.6.2019
 * 
 * Osaa parsia tietonsa eri muotoon. Osaa pyytää antamaan leffalle id:n.
 */
public class Genre extends Alkio {

    private static int seuraavaId = 1;
    private int id;
    private String nimi;

    /**
     * Konstruktori genrelle.
     */
    public Genre() {
        //
    }
    
    /**
     * Konstruktori genrelle.
     * @param id genren id
     * @param nimi genren nimi
     */
    public Genre(int id, String nimi) {
        this.setId(id);
        this.nimi = nimi;
    }

    // =========================== Muut metodit =======================================================
    /**
     * Annetaan genrelle id-numero.
     * @return genren id
     * @example
     * <pre name="test">
     *  Genre testi1 = new Genre();
     *  Genre testi2 = new Genre();
     *  testi1.setId(5);
     *  testi2.rekisteroi();
     *  testi1.getId() === 5;
     *  testi2.getId() === 6;
     * </pre>
     */
    public int rekisteroi() {
        if(id > 0) return id;
        id = seuraavaId;
        seuraavaId++;
        return id;
    }

    // =========================== Getterit ja setterit =======================================================
    /**
     * Asetetaan genrelle id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
        if (id >= seuraavaId) seuraavaId = id + 1;
    }
    
    /**
     * Saantimetodi genren id:lle.
     * @return genren id
     */
    @Override
    public int getId() {
        return id;
    }
    
    /**
     * Saantimetodi nimelle.
     * @return genren nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * Asetetaan genren nimi.
     * @param nimi genren nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    // =========================== Tulostus ja parsiminen =======================================================
    /**
     * Tulostetaan genren tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%02d %s", id, nimi));
        out.println(" ");
    }
    
    /**
     * Parsitaan rivi genren tietoihin.
     * @param rivi merkkijono
     * @example
     * <pre name="test">
     *  Genre testi= new Genre();
     *  testi.parse("1|komedia");
     *  testi.toString().equals("1|komedia") === true;
     * </pre>
     */
    @Override
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', id));
        nimi = Mjonot.erota(sb, '|', nimi);
    }

    /**
     * Palauttaa genren tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return genre tolppaerotettuna merkkijonona
     * @example
     * <pre name="test">
     *  Genre testi= new Genre();
     *  testi.parse("1|komedia");
     *  testi.toString().equals("1|komedia") === true;
     * </pre>
     */
    @Override
    public String toString() {
        return String.format("%d|%s", id, nimi);
    }

    // =========================== Rakennusvaihe =======================================================
    /*
     * TODO: poistetaan
     * 
     * Rakennusvaiheen apumetodi testiarvojen täyttämiseksi.
     
    public void taytaTiedoillaKomedia() {
        nimi = "Komedia";
    }
    */
}
