/**
 * 
 */
package movies;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Jenni Arovaara
 * @version 22.6.2019
 *
 * Yksittäinen leffa, osaa huolehtia id:stään. Osaa antaa tietonsa parsittuna eteenpäin eri muodoissa.
 */
public class Leffa extends Alkio {
    
    private int id = -1;
    private static int seuraavaId = 1;
    private String nimi = "";
    private int ikasuositus = 99;
    private String puhuttuSuomeksi = "";
    private int valmistusvuosi = 0;
    private String imdb = "";
    private String omaArvio = "";
        
    /**
     * Oletuskonstruktori.
     */
    public Leffa() {
        //
    }
    
    // =========================== Muut metodit =======================================================
    /**
     * Annetaan leffalle seuraava id-numero.
     * @return leffan id-numero
     * @example
     * <pre name="test">
     *  Leffa testi1 = new Leffa();
     *  Leffa testi2 = new Leffa();
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
     * Palautetaan leffan id.
     * @return leffan id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Asetetaan leffalle id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
        if (id >= seuraavaId) seuraavaId = id +1;
    }

    /**
     * Haetaan nimi.
     * @return leffan nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Asetetaan nimi.
     * @param nimi asetettava nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    /**
     * Saantimetodi ikäsuositukselle.
     * @return ikäsuositus
     */
    public int getIkasuositus() {
        return ikasuositus;
    }
    
    /**
     * Asetetaan ikäsuositus.
     * @param ikasuositus ikäsuositus
     */
    public void setIkasuositus(int ikasuositus) {
        this.ikasuositus = ikasuositus;
    }

    /**
     * Haetaan onko leffa puhuttu suomeksi vai ei.
     * @return puhuttuSuomeksi
     */
    public String getPuhuttuSuomeksi() {
        return puhuttuSuomeksi;
    }

    /**
     * Asetetaan onko leffa puhuttu suomeksi vai ei.
     * @param puhuttuSuomeksi asetettava kielivalinta
     */
    public void setPuhuttuSuomeksi(String puhuttuSuomeksi) {
        this.puhuttuSuomeksi = puhuttuSuomeksi;
    }

    /**
     * Haetaan valmistusvuosi.
     * @return valmistusvuosi
     */
    public int getValmistusvuosi() {
        return valmistusvuosi;
    }

    /**
     * Asetetaan valmistusvuosi.
     * @param valmistusvuosi asetettava valmistusvuosi
     */
    public void setValmistusvuosi(int valmistusvuosi) {
        this.valmistusvuosi = valmistusvuosi;
    }

    /**
     * Haetaan IMDB-linkki.
     * @return imdb linkki IMDB-nettisivulle
     */
    public String getImdb() {
        return imdb;
    }

    /**
     * Asetetaan IMDB-linkki leffalle.
     * @param imdb asetettava IMDB-linkki
     */
    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    /**
     * Haetaan oma arvio leffasta.
     * @return omaArvio
     */
    public String getOmaArvio() {
        return omaArvio;
    }

    /**
     * Asetetaan leffalle oma arvio.
     * @param omaArvio asetettava oma arvio leffasta
     */
    public void setOmaArvio(String omaArvio) {
        this.omaArvio = omaArvio;
    }
    
    // =========================== Tulostukset, parsimiset =======================================================
    /**
     * Tulostetaan leffan tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%04d %s", id, nimi));
        out.println("Puhuttu suomeksi: " + puhuttuSuomeksi);
        out.println("Valmistusvuosi: " + valmistusvuosi);
        out.println("IMDB-linkki: " + imdb);
        out.println("Oma arvio: " + omaArvio);
        out.println(" ");
    }
    
    /**
     * Tulostetaan leffan tiedot.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Täytetään merkkijonotaulukko leffan tiedoista StringGridiä varten.
     * @param arr taulukko, jossa on vähintään neljä solua
     * @example
     * <pre name="test">
     *  Leffa testi = new Leffa();
     *  testi.parse("1|Marley & Me 2|7|ei|2011|-|-");
     *  String[] arr = new String[4];
     *  testi.taytaTiedot(arr);
     *  arr[0].equals("Marley & Me 2") === true;
     *  arr[3].equals("2011") === true;
     * </pre>
     */
    public void taytaTiedot(String[] arr) {
        arr[0] = nimi;
        arr[1] = Helpers.intToStringUnless(ikasuositus, 99);
        arr[2] = puhuttuSuomeksi;
        arr[3] = Helpers.intToStringUnless(valmistusvuosi, 0);
        // arr[4] jätetään genrejä varten :P
    }

    /**
     * Palauttaa leffan tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return leffan tiedot tolppaerotettuna merkkijonona
     * @example
     * <pre name="test">
     *  Leffa testi= new Leffa();
     *  testi.parse("1|Marley & Me 2|7|ei|2011|-|-");
     *  testi.toString().equals("1|Marley & Me 2|7|ei|2011|-|-") === true;
     * </pre>
     */
    @Override
    public String toString() {
        return String.format("%d|%s|%d|%s|%d|%s|%s", id, nimi, ikasuositus, puhuttuSuomeksi, valmistusvuosi, imdb, omaArvio);
    }
    
    /**
     * Parsitaan rivi leffan tietoihin. 
     * @param rivi rivi
     * @example
     * <pre name="test">
     *  Leffa testi= new Leffa();
     *  testi.parse("1|Marley & Me 2|7|ei|2011|-|-");
     *  testi.toString().equals("1|Marley & Me 2|7|ei|2011|-|-") === true;
     * </pre>
     */
    @Override
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        nimi = Mjonot.erota(sb, '|', nimi);
        ikasuositus = Mjonot.erota(sb, '|', ikasuositus);
        puhuttuSuomeksi = Mjonot.erota(sb, '|', puhuttuSuomeksi);
        valmistusvuosi = Mjonot.erota(sb, '|', valmistusvuosi);
        imdb = Mjonot.erota(sb, '|', imdb);
        omaArvio = Mjonot.erota(sb, '|', omaArvio);
    }

    /*
     * @param args ei käytössä
     
    public static void main(String[] args) {
        Leffa marleyme2 = new Leffa();
        Leffa funsize = new Leffa();
        marleyme2.rekisteroi();
        funsize.rekisteroi();
        
        marleyme2.tulosta(System.out);
        funsize.tulosta(System.out);

        marleyme2.taytaTiedoillaMarleyMe2(); //rakennusvaiheen metodi
        funsize.taytaTiedoillaFunSize(); //rakennusvaiheen metodi

        marleyme2.tulosta(System.out);
        funsize.tulosta(System.out);
    }

    // =========================== Rakennusvaihe =======================================================
    /**
     * Rakennusvaiheen apumetodi testiarvojen täyttämiseksi.
     
    public void taytaTiedoillaMarleyMe2() {
        nimi = "Marley & Me 2";
        puhuttuSuomeksi = "ei";
        valmistusvuosi = 2011;
        imdb = "";
        omaArvio = "";
    }

    /**
     * Rakennusvaiheen apumetodi testiarvojen täyttämiseksi.
     
    public void taytaTiedoillaFunSize() {
        nimi = "Fun Size";
        puhuttuSuomeksi = "ei";
        valmistusvuosi = 2012;
        imdb = "";
        omaArvio = "";
    }
    */
}
