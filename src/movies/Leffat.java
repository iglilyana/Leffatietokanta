/**
 * 
 */
package movies;

/**
 * @author Jenni Arovaara
 * @version 22.6.2019
 *
 * Osaa päivittää uuden leffan listaan. Osaa pyytää taulukkoa kasvattamaan itseään.
 * 
 * @example
 * <pre name="test">
 *  Leffat leffat = new Leffat();
 *  Leffa testi1 = new Leffa();
 *  Leffa testi2 = new Leffa();
 *  testi1.rekisteroi();
 *  testi2.rekisteroi();
 *  leffat.getLkm() === 0;
 *  leffat.lisaa(testi1);
 *  leffat.getLkm() === 1;
 *  leffat.lisaa(testi2);
 *  leffat.getLkm() === 2;
 *  leffat.getById(1) === testi1;
 *  leffat.getById(2) === testi2;
 *  leffat.lisaa(testi1);
 *  leffat.getLkm() === 3;
 *  leffat.get(0) === testi1;
 *  leffat.get(1) === testi2;
 *  leffat.get(2) === testi1;
 *  leffat.lisaa(testi2);
 *  leffat.getLkm() === 4;
 *  leffat.lisaa(testi1);
 *  leffat.getLkm() === 5;
 *  leffat.lisaa(testi1);
 *  leffat.getLkm() === 6;
 *  leffat.lisaa(testi1);
 *  leffat.getLkm() === 7;
 *  leffat.lisaa(testi1);
 *  leffat.getLkm() === 8;
 *  </pre>
 */
public class Leffat extends Taulukko<Leffa> {

    /**
     * Oletuskonstruktori.
     */
    public Leffat() {
        super("leffatietokanta.leffa");
    }
    
    /**
     * Tiedostonimen vastaanottava konstruktori Tallennettava-luokan testausta varten.
     * @param tiedostoNimi tiedoston nimi
     * @example
     * <pre name="test">
     *  #THROWS IOException, SailoException
     *  #import java.io.IOException;
     *  #import fi.jyu.mit.ohj2.VertaaTiedosto;
     *  String tiednimi = "testi.txt";
     *  VertaaTiedosto.tuhoaTiedosto(tiednimi);
     *  Leffat testit = new Leffat(tiednimi);
     *  Leffa testi1 = new Leffa();
     *  Leffa testi2 = new Leffa();
     *  testi1.parse("1|Marley & Me 2|7|ei|2011|-|-");
     *  testi2.parse("2|Fun Size|7|ei|2012|-|-");
     *  testit.lisaa(testi1);
     *  testit.lisaa(testi2);
     *  testit.tallenna();
     *  String tulos = "1|Marley & Me 2|7|ei|2011|-|-\n"+
     *                 "2|Fun Size|7|ei|2012|-|-\n";
     *  VertaaTiedosto.vertaaFileString(tiednimi,tulos) === null;
     *  Leffat testit2 = new Leffat(tiednimi);
     *  testit2.lueTiedostosta();
     *  testit2.getById(1).toString().equals(testi1.toString());
     *  testit2.getById(2).toString().equals(testi2.toString());
     *  VertaaTiedosto.tuhoaTiedosto(tiednimi);
     * </pre>
     */
    public Leffat(String tiedostoNimi) {
        super(tiedostoNimi);
    }
    
    // =========================== Muut metodit =======================================================
    /**
     * Päivitetään leffan tiedot listaan.
     * @param leffa leffa
     * @throws SailoException poikkeus
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  Leffa testi1 = new Leffa();
     *  Leffa testi2 = new Leffa();
     *  Leffat testit = new Leffat();
     *  testi1.parse("1|Marley & Me 2|7|ei|2011|-|-");
     *  testi2.parse("1|Fun Size|7|ei|2012|-|-");
     *  testit.lisaa(testi1);
     *  testit.get(0).toString().equals("1|Marley & Me 2|7|ei|2011|-|-");
     *  testit.paivita(testi2);
     *  testit.get(0).toString().equals("1|Fun Size|7|ei|2012|-|-");
     * </pre>
     */
    public void paivita(Leffa leffa) throws SailoException {
        for (var i = 0; i < alkiot.length; i++) {
            if (alkiot[i].getId() == leffa.getId()) {
                set(i, leffa);
                return;
            }
        }
        throw new SailoException("Yritettiin päivittää leffaa, jota ei ole olemassa!");
    }

    /**
     * Palautetaan uusi leffataulukko halutussa koossa.
     * @param koko taulukon koko
     */
    @Override
    protected Leffa[] annaTaulukko(int koko) {
        return new Leffa[koko];
    }

    // =========================== Parsiminen =======================================================
    /**
     * Luodaan uusi leffa ja parsitaan tiedot.
     * @param rivi merkkijono
     */
    @Override
    protected Leffa parsiUusi(String rivi) {
        Leffa alkio = new Leffa();
        alkio.parse(rivi);
        return alkio;
    }

    /*
     * 
     * @param args ei käytössä
     
    public static void main(String[] args) {
        Leffat leffat = new Leffat();
        
        Leffa marleyme2 = new Leffa();
        Leffa funsize = new Leffa();
        marleyme2.rekisteroi();
        funsize.rekisteroi();
        //marleyme2.taytaTiedoillaMarleyMe2(); //rakennusvaiheen metodi
        //funsize.taytaTiedoillaFunSize(); //rakennusvaiheen metodi
    
        //leffat.lisaa(marleyme2);
        //leffat.lisaa(funsize);
        
        System.out.println("---Leffat-luokan testi---");
        for(int i=0; i < leffat.getLkm(); i++) {
            Leffa leffa = leffat.get(i);
            System.out.println("Leffa paikassa: " + i);
            leffa.tulosta(System.out);
        }
    }
*/
}
