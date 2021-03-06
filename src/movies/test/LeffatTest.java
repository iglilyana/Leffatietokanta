package movies.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import java.io.IOException;
import fi.jyu.mit.ohj2.VertaaTiedosto;
import movies.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.07.23 13:27:16 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class LeffatTest {



  // Generated by ComTest BEGIN
  /** testLeffat12 */
  @Test
  public void testLeffat12() {    // Leffat: 12
    Leffat leffat = new Leffat(); 
    Leffa testi1 = new Leffa(); 
    Leffa testi2 = new Leffa(); 
    testi1.rekisteroi(); 
    testi2.rekisteroi(); 
    assertEquals("From: Leffat line: 18", 0, leffat.getLkm()); 
    leffat.lisaa(testi1); 
    assertEquals("From: Leffat line: 20", 1, leffat.getLkm()); 
    leffat.lisaa(testi2); 
    assertEquals("From: Leffat line: 22", 2, leffat.getLkm()); 
    assertEquals("From: Leffat line: 23", testi1, leffat.getById(1)); 
    assertEquals("From: Leffat line: 24", testi2, leffat.getById(2)); 
    leffat.lisaa(testi1); 
    assertEquals("From: Leffat line: 26", 3, leffat.getLkm()); 
    assertEquals("From: Leffat line: 27", testi1, leffat.get(0)); 
    assertEquals("From: Leffat line: 28", testi2, leffat.get(1)); 
    assertEquals("From: Leffat line: 29", testi1, leffat.get(2)); 
    leffat.lisaa(testi2); 
    assertEquals("From: Leffat line: 31", 4, leffat.getLkm()); 
    leffat.lisaa(testi1); 
    assertEquals("From: Leffat line: 33", 5, leffat.getLkm()); 
    leffat.lisaa(testi1); 
    assertEquals("From: Leffat line: 35", 6, leffat.getLkm()); 
    leffat.lisaa(testi1); 
    assertEquals("From: Leffat line: 37", 7, leffat.getLkm()); 
    leffat.lisaa(testi1); 
    assertEquals("From: Leffat line: 39", 8, leffat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLeffat55 
   * @throws IOException when error
   * @throws SailoException when error
   */
  @Test
  public void testLeffat55() throws IOException, SailoException {    // Leffat: 55
    String tiednimi = "testi.txt"; 
    VertaaTiedosto.tuhoaTiedosto(tiednimi); 
    Leffat testit = new Leffat(tiednimi); 
    Leffa testi1 = new Leffa(); 
    Leffa testi2 = new Leffa(); 
    testi1.parse("1|Marley & Me 2|7|ei|2011|-|-"); 
    testi2.parse("2|Fun Size|7|ei|2012|-|-"); 
    testit.lisaa(testi1); 
    testit.lisaa(testi2); 
    testit.tallenna(); 
    String tulos = "1|Marley & Me 2|7|ei|2011|-|-\n"+
    "2|Fun Size|7|ei|2012|-|-\n"; 
    assertEquals("From: Leffat line: 71", null, VertaaTiedosto.vertaaFileString(tiednimi,tulos)); 
    Leffat testit2 = new Leffat(tiednimi); 
    testit2.lueTiedostosta(); 
    testit2.getById(1).toString().equals(testi1.toString()); 
    testit2.getById(2).toString().equals(testi2.toString()); 
    VertaaTiedosto.tuhoaTiedosto(tiednimi); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPaivita89 
   * @throws SailoException when error
   */
  @Test
  public void testPaivita89() throws SailoException {    // Leffat: 89
    Leffa testi1 = new Leffa(); 
    Leffa testi2 = new Leffa(); 
    Leffat testit = new Leffat(); 
    testi1.parse("1|Marley & Me 2|7|ei|2011|-|-"); 
    testi2.parse("1|Fun Size|7|ei|2012|-|-"); 
    testit.lisaa(testi1); 
    testit.get(0).toString().equals("1|Marley & Me 2|7|ei|2011|-|-"); 
    testit.paivita(testi2); 
    testit.get(0).toString().equals("1|Fun Size|7|ei|2012|-|-"); 
  } // Generated by ComTest END
}