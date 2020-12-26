package movies;

import java.util.Arrays;

/**
 * @author Jenni Arovaara
 * @version 16.7.2019
 * 
 * Osaa kasvattaa taulukon kokoa. Osaa lisätä ja poistaa alkioita. Osaa asettaa alkiolle arvon ja parsia tietoja.
 * @param <TSolu> Solun tyyppi
 */
public abstract class Taulukko<TSolu extends Alkio> extends Tallennettava<TSolu> {

    private static final int DEFAULT_SIZE = 100;
    private static final int DEFAULT_SIZE_INC = 10;

    /**
     * Alkiot-taulukko, jossa on TSolun alkiot (leffan, genren tms.)
     */
    protected TSolu alkiot[];
    private int lkm;

    /**
     * Alustetaan taulukko oletuskokoon.
     */
    public Taulukko() {
        alkiot = annaTaulukko(DEFAULT_SIZE);
    }

    /**
     * Alustetaan valitun kokoinen taulukko.
     * @param koko taulukon koko
     */
    public Taulukko(int koko) {
        alkiot = annaTaulukko(koko);
    }
    
    /**
     * Konstruktori
     * @param tiedostoNimi tiedoston nimi
     */
    public Taulukko(String tiedostoNimi) {
        super(tiedostoNimi);
        alkiot = annaTaulukko(DEFAULT_SIZE);
    }
    
    /**
     * Konstruktori taulukolle.
     * @param koko taulukon koko
     * @param tiedostoNimi tiedoston nimi
     */
    public Taulukko(int koko, String tiedostoNimi) {
        super(tiedostoNimi);
        alkiot = annaTaulukko(koko);
    }

    /**
     * Palautetaan uusi taulukko halutussa koossa.
     * @param koko taulukon koko
     * @return uusi taulukko
     */
    protected abstract TSolu[] annaTaulukko(int koko);
    
    /**
     * Lisätään taulukkoon yksi alkio.
     * @param alkio lisättävä alkio
     */
    @Override
    public void lisaa(TSolu alkio) {
        if (lkm >= alkiot.length) {
            TSolu[] uusiAlkiot = annaTaulukko(alkiot.length + DEFAULT_SIZE_INC);
            for (int i = 0; i < alkiot.length; i++) {
                uusiAlkiot[i] = alkiot[i];
            }
            alkiot = uusiAlkiot;
        };
        alkiot[lkm++] = alkio;
        muokattu();
    }

    /**
     * Poistetaan alkio taulukosta.
     * @param id alkion id
     */
    @Override
    public void poista(int id) {
        boolean poistettu = false;
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getId() == id) {
                poistettu = true;
            }
            if (poistettu && i + 1 < alkiot.length) {
                alkiot[i] = alkiot[i + 1];
            }
        }
        lkm--;
        muokattu();
    }

    // =========================== Parsiminen =======================================================
    /**
     * Palautetaan taulukko merkkijonona, pilkulla erotettuna.
     * @return taulukko merkkijonona
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        String erotin = "";
        for (int i = 0; i < lkm; i++) {
            s.append(erotin + alkiot[i]);
            erotin = ",";
        }
        return s.toString();
    }

    // =========================== Getterit ja setterit =======================================================
    /**
     * Asetetaan taulukon i:s alkio.
     * @param i mihin paikkaan astetaan
     * @param alkio mikä arvo laitetaan
     * @throws IndexOutOfBoundsException jos väärä indeksi
     */
    public void set(int i, TSolu alkio) throws IndexOutOfBoundsException {
        if ((i < 0) || (lkm <= i))
            throw new IndexOutOfBoundsException("Indeksi " + i + " on taulukon alueen ulkopuolella");
        muokattu();
        alkiot[i] = alkio;
    }

    /**
     * Palautetaan paikasssa i oleva alkio.
     * @param i mistä paikasta luku otetaan
     * @return paikassa i oleva alkio
     * @throws IndexOutOfBoundsException jos indeksi väärin
     */
    public TSolu get(int i) throws IndexOutOfBoundsException {
        if ((i < 0) || (lkm <= i))
            throw new IndexOutOfBoundsException("Indeksi " + i + " on taulukon alueen ulkopuolella");
        return alkiot[i];
    }
    
    /**
     * Palautetaan alkio id:n mukaan.
     * @param id alkion id
     * @return alkio
     */
    public TSolu getById(int id) {
        return Arrays.stream(alkiot).filter(alkio -> alkio.getId() == id).findFirst().orElseThrow();
    }
    
    /**
     * Palauttaa alkioiden lukumäärän.
     * @return alkioiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Palautetaan lista alkioita.
     * @return lista alkioista
     */
    @Override
    protected Iterable<TSolu> getAlkiot() {
        return Arrays.asList(alkiot).subList(0, lkm);
    }
}
