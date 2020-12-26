/**
 * 
 */
package movies;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jenni Arovaara
 * @version 22.6.2019
 *
 * Ylläpitää leffarekisteriä, joka pyytää tietoja Leffat, Genret ja LeffanGenret -luokilta. Käsittelee tiedostoa, tallentaa
 * ja poistaa tietoja. Huolehtii tiedosta, onko tietoja päivitetty. Parsii tiedon leffalle kuuluvista genreistä.
 */
public class Leffarekisteri {

 
    private final Leffat leffat = new Leffat();
    private final LeffanGenret leffanGenret = new LeffanGenret();
    private final Genret genret = new Genret();
    
    private Paivitettava onLeffatPaivitettyCallback;

    // Singleton-pattern poistettu :(
    
    // =========================== Tiedoston käsittely =======================================================
    
    /**
     * Luetaan leffarekisterin tiedot tiedostosta
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta() throws SailoException {
        genret.lueTiedostosta();
        leffanGenret.lueTiedostosta();
        leffat.lueTiedostosta();
    }
    
    /**
     * Kirjoitetaan leffarekisterin tiedot tiedostoihin
     * @throws SailoException jos kirjoittaminen epäonnistuu
     */   
    public void kirjoitaTiedostoon() throws SailoException {
        // TODO: Vai pitäisikö virhetilanteessa palauttaa kaikkien varmuuskopiot kerralla?
        genret.tallenna();
        leffanGenret.tallenna();
        leffat.tallenna();        
    }
    
    // =========================== Datan tallennus / päivitys / poisto =======================================================

    /**
     * Tallennetaan leffa leffarekisteriin.
     * @param leffa leffa, joka tallennetaan
     * @throws SailoException jos yritetään päivittää olematonta leffaa
     */
    public void tallenna(Leffa leffa) throws SailoException {
        if (leffa.getId() == -1) {
            leffa.rekisteroi();
            leffat.lisaa(leffa);
        } else {
            leffat.paivita(leffa);
        }
    }
    

    /**
     * Tallennetaan leffa genreineen leffarekisteriin.
     * @param leffa leffa, joka tallennetaan
     * @param genretArr genret, jotka kuuluu leffaan
     * @throws SailoException jos yritetään päivittää olematonta leffaa
     */
    public void tallenna(Leffa leffa, Genre[] genretArr) throws SailoException {
        tallenna(leffa);
        
        LeffanGenre[] lg = leffanGenret.getLeffalle(leffa.getId());
        ArrayList<Integer> genreIdt = new ArrayList<Integer>(
                Stream.of(genretArr).map(g -> g.getId()).collect(Collectors.toList()));
        
        for (int i = 0; i < lg.length; i++) {
            final int idx = i;
            Integer genreId = genreIdt.stream().filter(id -> id == lg[idx].getGenreId()).findFirst().orElse(null);
            if (genreId != null) {
                // Löytyi, siivotaan listalta
                genreIdt.remove(genreId);
            } else {
                // Ei löytynyt, poistetaan leffalta
                leffanGenret.poista(leffa.getId(), lg[i].getGenreId());
            }
        }
        // Tallennetaan loput uusina
        genreIdt.forEach(genreId -> {
            LeffanGenre leffanGenre = new LeffanGenre(leffa.getId(), genreId);
            leffanGenre.rekisteroi();
            leffanGenret.lisaa(leffanGenre);
        });
        leffatPaivitetty();
    }
    
    /**
     * Tallennetaan genre leffarekisteriin.
     * @param genre genre, joka tallennetaan
     * @throws SailoException jos taulukon koko ei riitä
     */
    public void tallenna(Genre genre) throws SailoException {
        if (genre.getId() == -1) {
            genre.rekisteroi();
            genret.lisaa(genre);
        } else {
            genret.paivita(genre);
        }
    }
    
    /**
     * Poistaa leffan ja siihen liittyvät genret
     * @param leffaId poistettavan leffan id
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  Leffarekisteri testi = new Leffarekisteri();
     *  Leffa testi1 = new Leffa();
     *  Genre komedia = new Genre(-1, "komedia");
     *  Genre satiiri = new Genre(-1, "satiiri");
     *  Genre kauhu = new Genre(-1, "kauhu");
     *  testi.tallenna(komedia);
     *  testi.tallenna(satiiri);
     *  testi.tallenna(kauhu);
     *  testi.tallenna(testi1, new Genre[]{komedia, satiiri});
     *  testi.getLeffanGenretLkm() === 2;
     *  testi.getLeffoja() === 1;
     *  testi.poista(testi1.getId());
     *  testi.getLeffoja() === 0;
     *  testi.getLeffanGenretLkm() === 0;
     * </pre>
     */
    public void poista(int leffaId) {
        leffat.poista(leffaId);
        Stream.of(leffanGenret.getLeffalle(leffaId)).forEach(lg -> {
            leffanGenret.poista(leffaId, lg.getGenreId());
        });
        leffatPaivitetty();
    }

    // =========================== Muut metodit =======================================================
    /**
     * Tarkistetaan onko tietoja muokattu.
     * @return onko jotain tietoa muokattu
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  Leffarekisteri testi = new Leffarekisteri();
     *  testi.onkoMuokattu() === false;
     *  Leffa testi1 = new Leffa();
     *  testi.tallenna(testi1);
     *  testi.onkoMuokattu() === true;
     * </pre>
     */
    public boolean onkoMuokattu() {
        return leffat.onkoMuokattu() || genret.onkoMuokattu() || leffanGenret.onkoMuokattu();
    }
    
    /**
     * Ajetaan, kun leffat on päivitetty. Silloin leffalista pääikkunassa päivittyy.
     */
    private void leffatPaivitetty() {
        if (onLeffatPaivitettyCallback != null)
            onLeffatPaivitettyCallback.paivita();
    }
    
    /**
     * Palautetaan i:n leffa.
     * @param i monesko leffa palautetaan
     * @return viite i:nteen leffaan
     * @throws IndexOutOfBoundsException jos i ei ole taulukon alueella
     * <pre name="test">
     *  #THROWS IndexOutOfBoundsException, SailoException
     *  Leffarekisteri leffarekisteri = new Leffarekisteri();
     *  Leffa l1 = new Leffa();
     *  Leffa l2 = new Leffa();
     *  leffarekisteri.getLeffoja() === 0;
     *  leffarekisteri.tallenna(l1);
     *  leffarekisteri.getLeffoja() === 1;
     *  leffarekisteri.tallenna(l2);
     *  leffarekisteri.getLeffoja() === 2;
     *  Leffa l3 = new Leffa();
     *  leffarekisteri.tallenna(l3);
     *  leffarekisteri.getLeffoja() === 3;
     *  leffarekisteri.annaLeffa(0) === l1;
     *  leffarekisteri.annaLeffa(1) === l2;
     *  leffarekisteri.annaLeffa(2) === l3;
     *  leffarekisteri.annaLeffa(9); #THROWS IndexOutOfBoundsException
     *  Leffa l4 = new Leffa();
     *  leffarekisteri.tallenna(l4);
     *  leffarekisteri.getLeffoja() === 4;
     *  Leffa l5 = new Leffa();
     *  leffarekisteri.tallenna(l5);
     *  leffarekisteri.getLeffoja() === 5;
     *  // Ei päivitetä id:tä, joten tallentuu samana leffana, eikä kasvata määrää 
     *  leffarekisteri.tallenna(l1);
     *  leffarekisteri.getLeffoja() === 5;
     * </pre> 
     */
    public Leffa annaLeffa(int i) throws IndexOutOfBoundsException {
        return leffat.get(i);
    }
    
    // =========================== Getterit ja setterit =======================================================
    /**
     * Palauttaa leffarekisterin leffojen lukumäärän.
     * @return leffojen määrä
     */
    public int getLeffoja() {
        return leffat.getLkm();
    }
    
    /**
     * Palauttaa genret indexin perusteella.
     * @param idx genren indeksi
     * @return genret
     */
    public Genre getGenre(int idx) {
        return genret.get(idx);
    }
    
    /**
     * Palauttaa genret id:n perusteella.
     * @param id genren id
     * @return genret
     */
    public Genre getGenreById(int id) {
        return genret.getById(id);
    }
    
    /**
     * Lisää genren listaan
     * @param genre genre
     */
    public void lisaaGenre(Genre genre) {
        genret.lisaa(genre);
    }
    
    /**
     * Poistaa genren.
     * @param id genren id
     */
    public void poistaGenre(int id) {
        genret.poista(id);
    }
    
    /**
     * Päivittää genren tiedot listaan.
     * @param genre genre
     * @throws SailoException poikkeus
     */
    public void paivitaGenre(Genre genre) throws SailoException {
        genret.paivita(genre);
    }
    
    /**
     * Haetaan genrejen lukumäärä.
     * @return listan koko
     */
    public int getGenreLkm() {
        return genret.getLkm();
    }
    
    /**
     * Lisää leffangenren listaan
     * @param leffanGenre leffangenre
     */
    public void lisaaLeffanGenre(LeffanGenre leffanGenre) {
        leffanGenret.lisaa(leffanGenre);
    }
    
    /**
     * Poistaa leffangenren id:n perusteella
     * @param id leffangenren id
     */
    public void poistaLeffanGenre(int id) {
        leffanGenret.poista(id);
    }

    /**
     * Poistaa leffangenren leffaid:n ja genreid:n
     * @param leffaId leffan id
     * @param genreId genren id
     */
    public void poistaLeffanGenre(int leffaId, int genreId) {
        leffanGenret.poista(leffaId, genreId);
    }
    
    /**
     * Haetaan leffojen genrejen lukumäärä.
     * @return listan koko
     */
    public int getLeffanGenretLkm() {
        return leffanGenret.getLkm();
    }
    
    /**
     * Haetaan leffan genre indeksin perusteella.
     * @param idx indeksi
     * @return leffan genre
     */
    public LeffanGenre getLeffanGenre(int idx) {
        return leffanGenret.get(idx);
    }
    
    /**
     * Haetaan leffan genre id:n perusteella.
     * @param id leffan genren id
     * @return leffan genre
     */
    public LeffanGenre getLeffanGenreById(int id) {
        return leffanGenret.getById(id);
    }
 
    /**
     * Liittää mukaan olion, jonka paivita-metodia pitäisi kutsua leffojen päivittyessä.
     * @param paivitettava päivitettävä olio
     */
    public void setOnLeffatPaivitetty(Paivitettava paivitettava) {
        onLeffatPaivitettyCallback = paivitettava;
    }
    
    // =========================== Parsiminen =======================================================
    /** Luodaan lista leffalle kuuluvista genreistä.
     * @param leffaId leffan id
     * @return lista leffalle kuuluvista genreistä
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  #import java.util.ArrayList;
     *  Leffarekisteri testi = new Leffarekisteri();
     *  Leffa testi1 = new Leffa();
     *  Genre komedia = new Genre(-1, "komedia");
     *  Genre satiiri = new Genre(-1, "satiiri");
     *  Genre kauhu = new Genre(-1, "kauhu");
     *  testi.tallenna(komedia);
     *  testi.tallenna(satiiri);
     *  testi.tallenna(kauhu);
     *  testi.tallenna(testi1, new Genre[]{komedia, satiiri});
     *  ArrayList<Genre> genret = testi.leffalleKuuluvatGenret(testi1.getId());
     *  genret.size() === 2;
     *  testi.leffalleKuuluvatGenretPilkunKera(testi1.getId()).equals("komedia, satiiri");
     * </pre>
     */
    public ArrayList<Genre> leffalleKuuluvatGenret(int leffaId) {
        ArrayList<Genre> lista = new ArrayList<Genre>();
        
        LeffanGenre[] genreLiitokset = leffanGenret.getLeffalle(leffaId);
        for (int i = 0; i < genreLiitokset.length; i++) {
            lista.add(genret.getById(genreLiitokset[i].getGenreId()));
        }
        return lista;
    }
    
    /**
     * Muodostetaan leffalle kuuluvat genret merkkijonoksi, jossa genret erotetaan toisistaan pilkuilla.
     * @param leffaId leffan id
     * @return merkkijono leffalle kuuluvista genreistä
     */
    public String leffalleKuuluvatGenretPilkunKera(int leffaId) {
        return String.join(
                ", ",
                leffalleKuuluvatGenret(leffaId)
                    .stream()
                    .map(genre -> genre.getNimi())
                    .sorted()
                    .toArray(size -> new String[size])
                    );
    }

    /*
     * @param args ei käytössä
     
    public static void main(String[] args) {
        Leffarekisteri leffarekisteri = new Leffarekisteri();

        Leffa marleyme2 = new Leffa();
        Leffa funsize = new Leffa();
        marleyme2.rekisteroi();
        funsize.rekisteroi();
        marleyme2.taytaTiedoillaMarleyMe2(); //rakennusvaiheen metodi
        funsize.taytaTiedoillaFunSize(); //rakennusvaiheen metodi
        
        try {
            leffarekisteri.tallenna(marleyme2);
            leffarekisteri.tallenna(funsize);
            // int id1 = marleyme2.getId();
            //int id2 = funsize.getId();
            Genre komedia = new Genre();
            komedia.taytaTiedoillaKomedia();
            
            System.out.println("---Leffarekisteri-luokan testi---");
            
            for(int i=0; i < leffarekisteri.getLeffoja(); i++) {
                Leffa leffa = leffarekisteri.annaLeffa(i);
                System.out.println("Leffa paikassa: " + i);
                leffa.tulosta(System.out);
//                List<Genre> loytyneet = leffarekisteri.annaGenret(leffa);
//                for (Genre genre : loytyneet)
//                    genre.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println("Virhe: " + ex.getMessage());
        }

    }
*/
}