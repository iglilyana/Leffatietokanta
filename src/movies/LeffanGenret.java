/**
 * 
 */
package movies;

import java.util.ArrayList;

/**
 * @author Jenni Arovaara
 * @version 16.7.2019
 * 
 * Lisää listaan leffan ja genren parsittuna tolppajonona. Osaa poistaa leffan ja genren liiman. 
 * Osaa hakea leffan ja genren liiman indeksin tai id:n perusteella. 
 *
 * @example
 * <pre name="test">
 *  LeffanGenret testit = new LeffanGenret();
 *  LeffanGenre testi1 = new LeffanGenre();
 *  LeffanGenre testi2 = new LeffanGenre();
 *  testi1.rekisteroi();
 *  testi2.rekisteroi();
 *  testit.getLkm() === 0;
 *  testit.lisaa(testi1);
 *  testit.getLkm() === 1;
 *  testit.lisaa(testi2);
 *  testit.getLkm() === 2;
 *  testit.getById(1) === testi1;
 *  testit.getById(2) === testi2;
 *  testit.lisaa(testi1);
 *  testit.getLkm() === 3;
 *  testit.get(0) === testi1;
 *  testit.get(1) === testi2;
 *  testit.get(2) === testi1;
 *  testit.lisaa(testi2);
 *  testit.getLkm() === 4;
 *  testit.lisaa(testi1);
 *  testit.getLkm() === 5;
 *  testit.lisaa(testi1);
 *  testit.getLkm() === 6;
 *  testit.lisaa(testi1);
 *  testit.getLkm() === 7;
 *  testit.lisaa(testi1);
 *  testit.getLkm() === 8;
 *  </pre>
 */
public class LeffanGenret extends Tallennettava<LeffanGenre> {

    private final ArrayList<LeffanGenre> lista = new ArrayList<LeffanGenre>();
    
    /**
     * Konstruktori leffojen genreille.
     */
    public LeffanGenret() {
        super("leffatietokanta.leffangenre");
    }

    // =========================== Lisää leffan genre =======================================================
    /**
     * Lisätään uusi leffan genre tietorakenteeseen.
     * @param leffanGenre leffan genre
     */
    @Override
    public void lisaa(LeffanGenre leffanGenre) {
        lista.add(leffanGenre);
        muokattu();
    }
    
    // =========================== Poista leffan genre =======================================================
    /**
     * Poistetaan leffan genre tietorakenteesta.
     * @param leffaId leffan id
     * @param genreId genren id
     * @example
     * <pre name="test">
     *  LeffanGenret testit = new LeffanGenret();
     *  LeffanGenre testi1 = new LeffanGenre();
     *  LeffanGenre testi2 = new LeffanGenre();
     *  LeffanGenre testi3 = new LeffanGenre();
     *  testi1.parse("1|1|1");
     *  testi2.parse("8|7|1");
     *  testi3.parse("10|8|1");
     *  testit.lisaa(testi1);
     *  testit.lisaa(testi2);
     *  testit.lisaa(testi3);
     *  testit.getLkm() === 3;
     *  testit.poista(1, 1);
     *  testit.getLkm() === 2;
     *  testit.poista(8, 1);
     *  testit.poista(7, 2);
     *  testit.getLkm() === 1;
     * </pre>
     */
    public void poista(int leffaId, int genreId) {
        lista.removeIf(lg -> lg.getLeffaId() == leffaId && lg.getGenreId() == genreId);
        muokattu();
    }
    
    /**
     * Poistetaan leffan genre tietorakenteesta.
     * @param id leffanGenren id
     * @example
     * <pre name="test">
     *  LeffanGenret testit = new LeffanGenret();
     *  LeffanGenre testi1 = new LeffanGenre();
     *  LeffanGenre testi2 = new LeffanGenre();
     *  LeffanGenre testi3 = new LeffanGenre();
     *  testi1.parse("1|1|1");
     *  testi2.parse("8|7|1");
     *  testi3.parse("10|8|1");
     *  testit.lisaa(testi1);
     *  testit.lisaa(testi2);
     *  testit.lisaa(testi3);
     *  testit.getLkm() === 3;
     *  testit.poista(1);
     *  testit.getLkm() === 2;
     *  testit.poista(8);
     *  testit.poista(7);
     *  testit.getLkm() === 1;
     * </pre>
     */
    @Override
    public void poista(int id) {
        lista.removeIf(lg -> lg.getId() == id);
        muokattu();
    }
    
    // =========================== Getterit ja setterit =======================================================
    /**
     * Haetaan leffojen genrejen lukumäärä.
     * @return listan koko
     */
    public int getLkm() {
        return lista.size();
    }
    
    /**
     * Haetaan leffan genre indeksin perusteella.
     * @param idx indeksi
     * @return leffan genre
     */
    public LeffanGenre get(int idx) {
        return lista.get(idx);
    }
    
    /**
     * Haetaan leffan genre id:n perusteella.
     * @param id leffan genren id
     * @return leffan genre
     */
    public LeffanGenre getById(int id) {
        return lista.stream().filter(lg -> lg.getId() == id).findFirst().orElseThrow();
    }
    
    /**
     * Palautetaan taulukko leffan genreistä, jotka kuuluvat tietylle leffalle.
     * @param leffaId leffaId
     * @return leffalle leffangenret
     */
    public LeffanGenre[] getLeffalle(int leffaId) {
        return lista.stream().filter(lg -> lg.getLeffaId() == leffaId).toArray(size -> new LeffanGenre[size]);
    }
    
    /**     
     * Palautetaan lista leffan genrejä.
     * @return lista leffangenrejä
     */
    @Override
    protected Iterable<LeffanGenre> getAlkiot() {
        return lista;
    }

    // =========================== Parsiminen =======================================================
    /**
     * Luodaan uusi leffangenre ja parsitaan tiedot.
     * @param rivi merkkijono
     */
    @Override
    protected LeffanGenre parsiUusi(String rivi) {
        LeffanGenre alkio = new LeffanGenre();
        alkio.parse(rivi);
        return alkio;
    }
}
