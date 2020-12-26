package movies;

/**
 * @author Jenni Arovaara
 * @version 16.7.2019
 * 
 * Pohjaluokka alkioille parsimisen ja id:n haun avuksi.
 */
public abstract class Alkio {
    
    /**
     * Parsitaan rivi tiedostosta.
     * @param rivi parsittava rivi
     */
    public abstract void parse(String rivi);
    
    /**
     * Haetaan id.
     * @return id
     */
    public abstract int getId();
}
