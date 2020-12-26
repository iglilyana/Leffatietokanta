package movies;

/**
 * @author Jenni Arovaara
 * @version 22.6.2019
 * 
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 */
public class SailoException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Poikkeuksen konstruktori, jolle tuodaan poikkeuksessa käytettävä viesti.
     * @param viesti Poikkeuksen viesti.
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
