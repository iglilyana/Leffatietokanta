package movies;

/**
 * @author Jenni Arovaara
 * @version 16.7.2019
 *
 * Pitää huolta tiedosta onko tietoja muokattu.
 */
public class Muokattava {
    
    private boolean muokattu = false;
    
    /**
     * Asetetaan taulukon tai listan tilaan muokattu.
     */
    protected void muokattu() {
        muokattu = true;
    }

    /**
     * Vastataan onko taulukon tai listan sisältöä muokattu.
     * @return true jos on muokattu tai false jos ei ole muokattu
     */
    public boolean onkoMuokattu() {
        return muokattu;
    };
    
    /**
     * Nollataan muokkauksen tila eli asetetaan tilaan false.
     */
    protected void nollaaMuokattu() {
        muokattu = false;
    };
}
