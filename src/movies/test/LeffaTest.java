package movies.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import movies.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.07.23 13:22:38 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class LeffaTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi39 */
  @Test
  public void testRekisteroi39() {    // Leffa: 39
    Leffa testi1 = new Leffa(); 
    Leffa testi2 = new Leffa(); 
    testi1.setId(5); 
    testi2.rekisteroi(); 
    assertEquals("From: Leffa line: 44", 5, testi1.getId()); 
    assertEquals("From: Leffa line: 45", 6, testi2.getId()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testTaytaTiedot196 */
  @Test
  public void testTaytaTiedot196() {    // Leffa: 196
    Leffa testi = new Leffa(); 
    testi.parse("1|Marley & Me 2|7|ei|2011|-|-"); 
    String[] arr = new String[4]; 
    testi.taytaTiedot(arr); 
    assertEquals("From: Leffa line: 201", true, arr[0].equals("Marley & Me 2")); 
    assertEquals("From: Leffa line: 202", true, arr[3].equals("2011")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString217 */
  @Test
  public void testToString217() {    // Leffa: 217
    Leffa testi= new Leffa(); 
    testi.parse("1|Marley & Me 2|7|ei|2011|-|-"); 
    assertEquals("From: Leffa line: 220", true, testi.toString().equals("1|Marley & Me 2|7|ei|2011|-|-")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse232 */
  @Test
  public void testParse232() {    // Leffa: 232
    Leffa testi= new Leffa(); 
    testi.parse("1|Marley & Me 2|7|ei|2011|-|-"); 
    assertEquals("From: Leffa line: 235", true, testi.toString().equals("1|Marley & Me 2|7|ei|2011|-|-")); 
  } // Generated by ComTest END
}