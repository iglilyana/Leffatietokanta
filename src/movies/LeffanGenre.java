/**
 * 
 */
package movies;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Jenni Arovaara
 * @version 22.6.2019
 *
 * Leffan ja genren yhdistämisluokka. Antaa id:n leffan ja genren yhdistelmälle.
 */
public class LeffanGenre extends Alkio {

    private int id = 0;
    private static int seuraavaId = 1;
    private int leffaId;
    private int genreId;
    
    /**
     * Oletuskonstruktori
     */
    public LeffanGenre() {
        //
    }
    
    /**
     * @param leffaId leffan id
     * @param genreId genren id
     */
    public LeffanGenre(int leffaId, int genreId) {
        this.leffaId = leffaId;
        this.genreId = genreId;
    }

    // =========================== Muut metodit =======================================================
    /**
     * Annetaan liimalle id-numero.
     * @return liiman id
     * @example
     * <pre name="test">
     *  LeffanGenre testi1 = new LeffanGenre();
     *  LeffanGenre testi2 = new LeffanGenre();
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
     * Asetetaan leffalle id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
        if (id >= seuraavaId) seuraavaId = id +1;
    }
    
    /**
     * Haetaan id.
     * @return id
     */
    @Override
    public int getId() {
        return id;
    }
    
    /**
     * Haetaan leffan id.
     * @return leffaId
     */
    public int getLeffaId() {
        return leffaId;
    }
    
    /**
     * Haetaan genren id.
     * @return genreId
     */
    public int getGenreId() {
        return genreId;
    }
    
    // =========================== Tulostus, parsiminen =======================================================
    /**
     * Leffan ja genren id:t merkkijonona.
     * @example
     * <pre name="test">
     *  LeffanGenre testi= new LeffanGenre();
     *  testi.parse("1|1|7");
     *  testi.toString().equals("1|1|7") === true;
     * </pre>
     */
    @Override
    public String toString() {
        return String.format("%d|%d|%d", id, leffaId, genreId);
    }
    
    /**
     * Parsitaan rivi genren ja leffan tiedoista.
     * @param rivi rivi
     * @example
     * <pre name="test">
     *  LeffanGenre testi= new LeffanGenre();
     *  testi.parse("1|1|7");
     *  testi.toString().equals("1|1|7") === true;
     * </pre>
     */
    @Override
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', id));
        leffaId = Mjonot.erota(sb, '|', getLeffaId());
        genreId = Mjonot.erota(sb, '|', getGenreId());
    }
}
