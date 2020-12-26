/**
 * 
 */
package movies;

import java.util.*;

/**
 * @author Jenni Arovaara
 * @version 28.6.2019
 *
 * Osaa lisätä genren, päivittää genren tiedot listaan, osaa poistaa genren listalta ja parsia tietojaan.
 * 
 * @example
 * <pre name="test">
 *  Genret testit = new Genret();
 *  Genre testi1 = new Genre();
 *  Genre testi2 = new Genre();
 *  testi1.parse("99|1|8");
 *  testi2.rekisteroi();
 *  testit.getLkm() === 0;
 *  testit.lisaa(testi1);
 *  testit.getLkm() === 1;
 *  testit.lisaa(testi2);
 *  testit.getLkm() === 2;
 *  testit.getById(99) === testi1;
 *  testit.getById(100) === testi2;
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
public class Genret extends Tallennettava<Genre> {

    private final ArrayList<Genre> lista = new ArrayList<Genre>();
    
    /**
     * Konstruktori genreille.
     */
    public Genret() {
        super("leffatietokanta.genre");
    }
    
    // =========================== Lisää genre =======================================================
    /**
     * Lisätään uusi genre tietorakenteeseen.
     * @param genre genre
     */
    @Override
    public void lisaa(Genre genre) {
        lista.add(genre);
        muokattu();
    }
    
    // =========================== Muut metodit =======================================================
    /**
     * Päivitetään genren tiedot listaan.
     * @param genre genre
     * @throws SailoException poikkeus
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  Genre testi1 = new Genre();
     *  Genre testi2 = new Genre();
     *  Genret testit = new Genret();
     *  testi1.parse("1|Marley & Me 2|7|ei|2011|-|-");
     *  testi2.parse("1|Fun Size|7|ei|2012|-|-");
     *  testit.lisaa(testi1);
     *  testit.get(0).toString().equals("1|Marley & Me 2|7|ei|2011|-|-");
     *  testit.paivita(testi2);
     *  testit.get(0).toString().equals("1|Fun Size|7|ei|2012|-|-");
     * </pre>
     */
    public void paivita(Genre genre) throws SailoException {
        for (var i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == genre.getId()) {
                lista.set(i, genre);
                muokattu();
                return;
            }
        }
        throw new SailoException("Yritettiin päivittää genreä, jota ei ole olemassa!");
    }

    /**
     * @example
     * <pre name="test">
     *   Genret testit = new Genret();
     *   Genre testi1 = new Genre();
     *   Genre testi2 = new Genre();
     *   Genre testi3 = new Genre();
     *   testi1.parse("1|komedia");
     *   testi2.parse("8|satiiri");
     *   testi3.parse("10|kauhu");
     *   testit.lisaa(testi1);
     *   testit.lisaa(testi2);
     *   testit.lisaa(testi3);
     *   testit.getLkm() === 3;
     *   testit.poista(1);
     *   testit.getLkm() === 2;
     *   testit.poista(8);
     *   testit.poista(7);
     *   testit.getLkm() === 1;
     * </pre>
     */ 
    @Override
    public void poista(int id) {
        lista.removeIf(genre -> genre.getId() == id);
    }

    // =========================== Getterit ja setterit =======================================================
    /**
     * Haetaan genrejen lukumäärä.
     * @return listan koko
     */
    public int getLkm() {
        return lista.size();
    }
    
    /**
     * Haetaan genre indeksin perusteella.
     * @param idx indeksi
     * @return genre
     */
    public Genre get(int idx) {
        return lista.get(idx);
    }
    
    /**
     * Haetaan genre id:n perusteella.
     * @param id genren id
     * @return genre
     */
    public Genre getById(int id) {
        return lista.stream().filter(genre -> genre.getId() == id).findFirst().orElseThrow();
    }
    
    /**
     * Palautetaan lista genrejä.
     * @return lista genrejä
     */
    @Override
    protected Iterable<Genre> getAlkiot() {
        return lista;
    }

    // =========================== Parsiminen =======================================================
    /**
     * Luodaan uusi genre ja parsitaan tiedot.
     * @param rivi merkkijono
     */
    @Override
    protected Genre parsiUusi(String rivi) {
        Genre alkio = new Genre();
        alkio.parse(rivi);
        return alkio;
    }

    /*
     * @param args ei käytössä
    public static void main(String[] args) {
        Genret genret = new Genret();
        
        Genre komedia = new Genre();
        komedia.rekisteroi();
        //komedia.taytaTiedoillaKomedia(); //rakennusvaiheen metodi
        
        try {
            genret.lisaa(komedia);
        } catch (Exception ex) { //TODO: (SailoException ex) {
            System.err.println("Virhe: " + ex.getMessage());
        }
        
        System.out.println("---Genret-luokan testi---");
        for(int i=0; i < genret.getLkm(); i++) {
            Genre genre = genret.lista.get(i);
            System.out.println("Genre paikassa: " + i);
            genre.tulosta(System.out);
        }
    }
     */
}
