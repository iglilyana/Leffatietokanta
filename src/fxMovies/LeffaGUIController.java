package fxMovies;

import java.awt.Desktop;
import java.net.URL;
import java.util.ArrayList;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import movies.Genre;
import movies.Genret;
import movies.Helpers;
import movies.Leffa;
import movies.Leffarekisteri;
import movies.SailoException;


/**
 * @author Jenni Arovaara
 * @version 5.6.2019
 *
 * Käyttöliittymä leffan lisäämiseen.
 */
public class LeffaGUIController implements ModalControllerInterface<Leffa> {

    /**
     * Kentät
     */
    @FXML private TextField imdb;
    @FXML private ComboBoxChooser<?> puhuttuSuomeksi;
    @FXML private TextField nimi;
    @FXML private TextField ikasuositus;
    @FXML private TextArea arvio;
    @FXML private TextField valmistusvuosi;
    @FXML private ComboBoxChooser<Genre> genreValitsin;
    @FXML private ListChooser<Genre> leffanGenret;

    /**
     * Nappulat
     */
    @FXML private Button poista;
    @FXML private Button tallenna;
    @FXML private Button sulje;
    @FXML private Button gotoIMDB;

    /**
     * Ohje menussa
     */
    @FXML private MenuItem ohje;
    
    // =========================== Toimintojen määrittely =======================================================
    /**
     * Tapahtuman käsittelijä: siirrytään IMDB:een linkin avulla
     * @param event tapahtuma
     */
    @FXML void gotoIMDB(ActionEvent event) {
        goToIMDB();
    }

    /**
     * Lisätään genre.
     * @param event tapahtuma
     */
    @FXML void lisaaGenre(Event event) {
        lisaaGenre();
    }

    /**
     * Lisätään genre näppiksellä.
     * @param event tapahtuma
     */
    @FXML void lisaaGenreKB(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE)
            lisaaGenre();
    }
    
    /**
     * Poistetaan genre.
     * @param event tapahtuma
     */
    @FXML void poistaGenre(Event event) {
        poistaGenre();
    }

    /** 
     * Tapahtuman käsittelijä: avataan TIMissä oleva käyttöohje
     * @param event tapahtuma
     */
    @FXML void help(ActionEvent event) {
        help();
    }

    /**
     * Tapahtuman käsittelijä: suljetaan ikkuna
     * @param event tapahtuma
     */
    @FXML void close(ActionEvent event) {
        close();
    }

    /**
     * Tapahtuman käsittelijä: poistetaan leffa
     * @param event tapahtuma
     */
    @FXML void remove(ActionEvent event) {
        remove();
    }

    /**
     * Tapahtuman käsittelijä: tallenetaan muutetut tiedot
     * @param event
     */
    @FXML void save(ActionEvent event) {
        save();
    }

    private int leffaId = -1;
    
    private String[] puhuttuValinnat = new String[] {
            "ei tietoa",
            "kyllä",
            "ei"
    };
    
    private Leffarekisteri leffarekisteri;
    
    // =========================== Metodit =======================================================

    /**
     * TODO: kesken, yritetään korjata valitsin toimimaan toivotulla tavalla.
     * @param valitsin pudotusvalikko
     * @param spesiaali valittu vai ei
     */
    private void korjaaValitsin(ComboBoxChooser<?> valitsin, boolean spesiaali) {
//        valitsin.addEventFilter(KeyEvent.KEY_TYPED, event ->
//            {
//                if (!valitsin.isShowing() && (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE)) {
//                    valitsin.show();
//                    event.consume();
//                }
//            }
//        );
        
//        valitsin.addEventFilter(ActionEvent.ACTION, event ->
//            {
//                if (event.)
//                if (!valitsin.isShowing() && (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE)) {
//                    valitsin.show();
//                    event.consume();
//                }
//            }
//        );

//        valitsin.addEventFilter(MouseEvent.MOUSE_RELEASED, release ->
//            {
//                if (!valitsin.isFocused()) {
//                    release.consume();
//                    valitsin.requestFocus();
//                }
//            }
//        );

    }

    /**
     * Avataan TIM-sivu, jossa on käyttöohje.
     */
    public void help () {
        try {
            Desktop.getDesktop().browse(new URL("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/jemaarov").toURI());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
   }
    
    /**
     * Suljetaan ikkuna.
     */
    public void close() {
        sulje.getScene().getWindow().hide();
    }
    
    /**
     * Siirrytään linkin kautta IMDB:ssä oikeaan leffaan.
     */
    public void goToIMDB() {
        if (!validateAndFixIMDB()) {
            Modals.notify("Osoite ei vaikuta oikealta elokuvan IMDB-osoitteelta.");
            return;            
        }
        String url = imdb.getText();
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Käyttäjän valitessa genren pudotusvalikosta, lisätään genre pudotusvalikon alapuolella olevaan listaan.
     */
    private void lisaaGenre() {
        Genre valittuGenre = genreValitsin.getSelectedObject();
        
        if (valittuGenre == null)
            return;
        
        if (!leffanGenret.getObjects().contains(valittuGenre))
            leffanGenret.add(valittuGenre.getNimi(), valittuGenre);
    }

    /**
     * Poistetaan genre dialogista käyttäjän klikatessa sitä.
     */
    private void poistaGenre() {
        Genre poistaGenre = leffanGenret.getSelectedObject();
        
        if (poistaGenre == null)
            return;
        
        leffanGenret.getItems().removeIf(item -> item.getObject() != null && item.getObject().equals(poistaGenre));
    }
    
    /**
     * TODO: tarkista onko public vai private
     * Siirrytään varmistusikkunaan, jossa varmistetaan onko käyttäjä varma, että haluaa poistaa leffan.
     */
    private void remove() {
        if (Modals.confirmAction("Oletko varma, että haluat poistaa leffan?")) {
            System.out.println("Poistetaan");       
            leffarekisteri.poista(leffaId);
            close();
        }
        try {
            leffarekisteri.kirjoitaTiedostoon();
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa esiintyi ongelma " +ex.getMessage());
            return;
        }
    }
    
    /**
     * TODO: voiko olla private vai pitääkö olla public
     * 
     * Tallennetaan muutetut tai lisätyt tiedot.
     */
    private void save() {
        if(!validoi())
            return;
        Leffa leffa = getResult();
        Genre[] genret = leffanGenret.getObjects().toArray(size -> new Genre[size]);
        try {
            leffarekisteri.tallenna(leffa, genret);
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa esiintyi ongelma " +ex.getMessage());
            return;
        }
        try {
            leffarekisteri.kirjoitaTiedostoon();
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa esiintyi ongelma " +ex.getMessage());
            return;
        }
        close();
    }
    
    /**
     * Validoi ja tarvittaessa yrittää korjata IMDB-urlin kokonaiseksi
     * @return onko url ok
     */
    private boolean validateAndFixIMDB() {
        if (!Helpers.validateIMDBUrl(imdb.getText())) {
            String kokeilu = String.format("https://%s", imdb.getText());
            if (Helpers.validateIMDBUrl(kokeilu)) {
                imdb.setText(kokeilu);
            } else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Validoidaan käyttäjän syötteet.
     * @return onko käyttäjän syötteet valideja
     */
    private boolean validoi() {
        ArrayList<String> viestit = new ArrayList<String>();
        if (nimi.getText().trim().length() == 0) {
            viestit.add("Nimi ei saa olla tyhjä.");
        }
        if (valmistusvuosi.getText().length() > 0 && Helpers.tryParseInt(ikasuositus.getText(), Helpers.MIN_AGE, Helpers.MAX_AGE, 99) == 99) {
            viestit.add(String.format("Ikäsuositus on oltava tyhjä, tai numero väliltä %d-%d", Helpers.MIN_AGE, Helpers.MAX_AGE));
        }
        // PuhuttuSuomeksi ei tarvetta tarkistaa
        if (valmistusvuosi.getText().length() > 0 && Helpers.tryParseInt(valmistusvuosi.getText(), Helpers.MIN_YEAR, Helpers.MAX_YEAR, 0) == 0) {
            viestit.add(String.format("Valmistusvuosi on oltava tyhjä, tai numero väliltä %d-%d", Helpers.MIN_YEAR, Helpers.MAX_YEAR));
        }
        if (imdb.getText().length() > 0 && !validateAndFixIMDB()) {
            viestit.add("IMDB-kentän täytyy olla tyhjä, tai siihen tulee antaa täysi IMDB-osoite elokuvan sivulle.");
        }
        // OmaArvio on vapaata tekstiä, joten ei tarvitse tarkistaa
        if (viestit.size() > 0) {
            Modals.notify(viestit);
            return false;
        }
        return true;
    }

    /**
     * TODO: Puhuttu suomeksi voisi muuttaa numeroksi tekstin sijasta.
     * 
     * Luodaan leffa dialogin sisällöstä.
     * @return leffa
     */
    @Override
    public Leffa getResult() {
        Leffa leffa = new Leffa();
        leffa.setId(this.leffaId);
        leffa.setNimi(nimi.getText().trim());
        leffa.setIkasuositus(Helpers.tryParseInt(ikasuositus.getText(), Helpers.MIN_AGE, Helpers.MAX_AGE, 99));
        leffa.setPuhuttuSuomeksi(puhuttuValinnat[puhuttuSuomeksi.getSelectedIndex()]);
        leffa.setValmistusvuosi(Helpers.tryParseInt(valmistusvuosi.getText(), Helpers.MIN_YEAR, Helpers.MAX_YEAR, 0));
        leffa.setImdb(imdb.getText().trim());
        leffa.setOmaArvio(arvio.getText().trim());
                
        return leffa;
    }

    @Override
    public void handleShown() {
        korjaaValitsin(genreValitsin, true);
        korjaaValitsin(puhuttuSuomeksi, false);

        leffanGenret.getItems().removeIf(item -> item.getObject() == null);
    }

    @Override
    public void setDefault(Leffa leffa) {
        if (leffa == null)
            return;
        this.leffaId = leffa.getId();
        nimi.setText(leffa.getNimi());
        ikasuositus.setText(Helpers.intToStringUnless(leffa.getIkasuositus(), 99));
        puhuttuSuomeksi.setSelectedIndex(Helpers.findOptionIndex(puhuttuValinnat, leffa.getPuhuttuSuomeksi(), 0));
        valmistusvuosi.setText(Helpers.intToStringUnless(leffa.getValmistusvuosi(), 0));
        imdb.setText(leffa.getImdb());
        arvio.setText(leffa.getOmaArvio());
        poista.setVisible(true);
    }
    
    /**
     * Kytkee controllerille leffarekisterin ja täydentää genret sekä genrevalitsimen
     * @param rekisteri leffarekisteri
     */
    public void connectAndComplete(Leffarekisteri rekisteri) {
        this.leffarekisteri = rekisteri;
        
        leffarekisteri
            .leffalleKuuluvatGenret(this.leffaId)
            .forEach(genre -> leffanGenret.add(genre.getNimi(), genre));

        genreValitsin.addExample("- Valitse genre -");
        for (int i = 0; i < leffarekisteri.getGenreLkm(); i++) {
            Genre genre = leffarekisteri.getGenre(i);
            genreValitsin.add(genre.getNimi(), genre);
        }
        genreValitsin.setSelectedIndex(0);
    }
}
