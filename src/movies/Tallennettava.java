package movies;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Jenni Arovaara
 * @version 16.7.2019
 * 
 * Käsittelee tiedostoa, tallentaa ja poistaa. Osaa palauttaa varmuuskopion.
 * @param <TSolu> leffan, genren tms. alkio
 */
public abstract class Tallennettava<TSolu> extends Muokattava {
    
    private String tiedostonNimi = "";
    
    /**
     * Konstruktori tallennettava-luokalle.
     */
    public Tallennettava() {
        //
    }
    
    /**
     * Konstruktori tiedostonimen asettamiseksi.
     * @param tiedostonNimi tiedoston nimi
     */
    public Tallennettava(String tiedostonNimi) {
        this.tiedostonNimi = tiedostonNimi;
    }
    
    /**
     * Luodaan uusi alkio ja parsitaan tiedot.
     * @param rivi merkkijono
     * @return alkio
     */
    protected abstract TSolu parsiUusi(String rivi);
    
    /**
     * Luetaan tiedosto.
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta() throws SailoException {
        @SuppressWarnings("resource")
        Scanner fileInput = null;
        
        try {
            File tiedosto = new File(tiedostonNimi);
            if (tiedosto.createNewFile())
                return;
            fileInput = new Scanner(new FileInputStream(tiedosto));
            while (fileInput.hasNext()) {
                String s = fileInput.nextLine().trim();
                if ("".equals(s) || s.charAt(0) == ';') continue;

                lisaa(parsiUusi(s));
            }
            nollaaMuokattu();
        }
        catch (Exception ex) {
            System.err.println("Tiedoston lukemisessa tai luomisessa tapahtui virhe: " + ex.getMessage());
            throw new SailoException(String.format("Tiedoston '%s' lukemisessa tai luomisessa tapahtui virhe", tiedostonNimi));
        }
        finally {
            if (fileInput != null)
                fileInput.close();
        }
    }
    
    /**
     * Lisätään alkio.
     * @param alkio alkio
     */
    protected abstract void lisaa(TSolu alkio);
    
    /**
     * Poistetaan alkio.
     * @param id poistettavan alkion id
     */
    protected abstract void poista(int id);
    
    /**
     * Palauttaa varmuuskopion käyttöön.
     */
    protected void palautaVarmuuskopio() {
        File virheVarmuuskopio = new File(getVarmuuskopionNimi() + ".err");
        File varmuuskopio = new File(getVarmuuskopionNimi());
        File tiedosto = new File(getTiedostonNimi());
        virheVarmuuskopio.delete();
        tiedosto.renameTo(virheVarmuuskopio);
        varmuuskopio.renameTo(tiedosto);
    }
    
    /**
     * Tallentaa alkiot tiedostoon
     * @throws SailoException jos tallentaminen epäonnistuu
     */
    public void tallenna() throws SailoException {
        if (!onkoMuokattu()) return;
        File varmuuskopio = new File(getVarmuuskopionNimi());
        File tiedosto = new File(getTiedostonNimi());
        varmuuskopio.delete();
        tiedosto.renameTo(varmuuskopio);
        
        try (PrintWriter fileOutput = new PrintWriter(new FileWriter(tiedosto.getCanonicalPath()))){
            for (TSolu alkio : getAlkiot()) {
                fileOutput.println(alkio.toString());
            }
        }
        catch (FileNotFoundException ex){
            palautaVarmuuskopio();
            throw new SailoException("Tiedosto " + tiedosto.getName() + " ei aukea.");
        }
        catch (IOException ex) {
            palautaVarmuuskopio();
            throw new SailoException("Tiedoston " + tiedosto.getName() + " tallennuksessa ilmeni ongelma.");
        }

        nollaaMuokattu();
    }

    // =========================== Getterit ja setterit =======================================================
    /**
     * Asetetaan tiedostolle nimi.
     * @param nimi tiedoston nimi
     */
    protected void setTiedostonNimi(String nimi) {
        tiedostonNimi = nimi;
    }
    
    /**
     * Palautetaan tiedoston nimi.
     * @return tiedostonNimi
     */
    protected String getTiedostonNimi() {
        return tiedostonNimi;
    }

    /**
     * Palautetaan varmuuskopion tiedoston nimi.
     * @return vamuuskopion tiedoston nimi
     */
    protected String getVarmuuskopionNimi() {
        return tiedostonNimi +".bak";
    }
    
    /**
     * Palautetaan lista alkioita.
     * @return alkiot
     */
    protected abstract Iterable<TSolu> getAlkiot();
}
