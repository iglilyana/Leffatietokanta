package fxMovies;

import java.awt.Desktop;
import java.net.URL;
import java.util.function.Predicate;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import movies.Genre;
import movies.Leffa;
import movies.Leffarekisteri;
import movies.Paivitettava;
import movies.SailoException;

/**
 * @author Jenni Arovaara
 * @version 5.6.2019
 *
 * Käyttöliittymä leffarekisterille.
 */
public class MoviesGUIController implements Paivitettava, ModalControllerInterface<Leffarekisteri> {

    // Menu
    @FXML private MenuItem menu; 
    @FXML private MenuItem tulosta;
    @FXML private MenuItem sulje;

    @FXML private MenuItem lisaaLeffa;
    @FXML private MenuItem lisaaGenre;

    @FXML private MenuItem ohje1;
    @FXML private MenuItem tietoja1;

    // Haku
    @FXML private TextField textHakukentta;
    @FXML private Button search; //

    // Lisää
    @FXML private Button add; //
    
    // Taulukko
    @FXML private StringGrid<Leffa> leffaTaulu;


    // =========================== Toiminnot =======================================================
    /**
     * Tapahtuman käsittelijä: siirrytään dialogiin, jossa voi lisätä leffan.
     * @param event ei vielä käytössä
     */
    @FXML void addMovie(ActionEvent event) {
        add();
    }

    /**
     * Tapahtuman käsittelijä: avataan Ohje.
     * @param event ei vielä käytössä
     */
    @FXML void ohje1(ActionEvent event) {
        help();
    }

    /** 
     * TODO: miksi tämä tarvitaan??
     * 
     * Tapahtuman käsittelijä: avataan Tietoja-ikkuna.
     * @param event ei vielä käytössä
     */
    @FXML void help(ActionEvent event) {
        help();
    }

    /** 
     * Tapahtuman käsittelijä: avataan Tietoja-ikkuna.
     * @param event ei vielä käytössä
     */
    @FXML void tietoja1(ActionEvent event) {
        ModalController.showModal(TietojaGUIController.class.getResource("Tietoja.fxml"),
                "Tietoja", null, "");
    }

    /**
     * Tapahtuman käsittelijä: haetaan elokuva hakusanan mukaan.
     * @param event ei vielä käytössä
     */
    @FXML void hae(ActionEvent event) {
        search();
    }
    
    /**
     * Tapahtuman käsittelijä: tulostetaan lista leffoista.
     * @param event tapahtuma
     */
    @FXML void tulosta(ActionEvent event) {
        Dialogs.showMessageDialog("Tulostaminen ei vielä onnistu");
    }

    /**
     * Tapahtuman käsittelijä: suljetaan ikkuna, jossa ollaan.
     * @param event tapahtuma
     */
    @FXML void sulje(ActionEvent event) {
        close();
    }

    /**
     * Tapahtuman käsittelijä: lisätään leffa.
     * @param event tapahtuma
     */
    @FXML void lisaaLeffa(ActionEvent event) {
        add();
    }
    
    /**
     * Tapahtuman käsittelijä: näytetään leffa
     * @param event tapahtuma
     */
    @FXML void lisaaGenre(ActionEvent event) {
        addGenre();
    }
    
    /**
     * Tapahtuman käsittelijä: näytetään leffa
     * @param event tapahtuma
     */
    @FXML void naytaLeffa(MouseEvent event) {
        if (event.getClickCount() == 2)
            naytaLeffa();
    }
    
    private Leffarekisteri leffarekisteri;
    private String haku = "";
    
    // =========================== Metodit =======================================================
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
     * Mennään lisäämään genre.
     */
    public void addGenre() {
        Genre genre = ModalController.<Genre>showModal(
                getClass().getResource("Genre.fxml"),
                "Genren tiedot",
                ModalController.getStage(search),
                null);
        
        if (genre == null)
            return;

        try {
            leffarekisteri.tallenna(genre);
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa esiintyi ongelma " +ex.getMessage());
            return;
        }
        try {
            leffarekisteri.kirjoitaTiedostoon();
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa esiintyi ongelma " +ex.getMessage());
        }
    }
    
    /**
     * Siirrytään leffa-ikkunaan, jossa voi lisätä uuden elokuvan.
     */
    public void add() {
        LeffaGUIController ctrl = ModalController.<Leffa, LeffaGUIController>showModeless(
                getClass().getResource("Leffa.fxml"),
                "Leffan tiedot", null);

        // Koska modelessille ei voi antaa lambdaa extraparametrina...
        ctrl.connectAndComplete(leffarekisteri);
    }
    
    /**
     * Näytetään valittu leffa.
     */
    public void naytaLeffa() {
        Leffa leffa = leffaTaulu.getObject();
            
        if (leffa == null) return;
        
        LeffaGUIController ctrl = ModalController.<Leffa, LeffaGUIController>showModeless(
                getClass().getResource("Leffa.fxml"),
                leffa.getNimi(),
                leffa);

        // Koska modelessille ei voi antaa lambdaa extraparametrina...
        ctrl.connectAndComplete(leffarekisteri);
    }

    /**
     * Asetetaan viite leffarekisteriin, päivitetään näkymä ensimmäistä kertaa
     */
    @Override
    public void handleShown() {
        paivita();
    }    

    /**
     * Suljetaan ikkuna.
     */
    public void close() {
        if (voikoSulkea())
            System.exit(0);
    }

    /**
     * Tarkistetaan, voiko ikkunan sulkea eli onko jossain tallentamatonta tietoa.
     * @return true, jos voi sulkea ja false, jos ei voi sulkea
     */
    public boolean voikoSulkea() {
        if (Window.getWindows().size() > 1) {
            return Modals.confirmAction("Ohjelmalla on useampia ikkunoita auki, haluatko varmasti sulkea?");
        }
        
        if (leffarekisteri.onkoMuokattu()) {
            return Modals.confirmAction("Ohjelmassa on (jostain syystä) tallentamatonta tietoa, suljetaanko silti?");
        }
        return true;
    }

    /**
     * Hakutoiminto, joka hakee leffaa annetun merkkijonon perusteella, kun painetaan hae-nappia.
     * @param haettavaMerkkijono haettava merkkijono
     */
    private void search() {
        String haettavaMerkkijono = textHakukentta.getText();
        haku = String.format(".*?%s.*?", haettavaMerkkijono.replace("*", ".*").trim().toLowerCase());
        
        paivita();
    }
    
    /**
     * Haetaan vertailtavat tiedot merkkijonoksi.
     * @param leffa leffa
     * @return vertailtavat tiedot merkkijonona
     */
    private String getVertailtava(Leffa leffa) {
        return String.format(
                "%s %d %s",
                leffa.getNimi(),
                leffa.getIkasuositus(),
                leffarekisteri.leffalleKuuluvatGenretPilkunKera(leffa.getId())
                );
    }
    
    /**
     * Päivittää näkymän
     */
    @Override
    public void paivita() {
        if (haku.length() > 0) {
            taytaTaulu(leffa -> getVertailtava(leffa).toLowerCase().matches(haku));
        } else {
            taytaTaulu(x -> true);
        }
    }
    
    /**
     * Laitetaan näkyviin vain hakuehtoihin sopivat leffat.
     * @param ehto hakuehto
     */
    private void taytaTaulu(Predicate<Leffa> ehto) {
        leffaTaulu.clear();
        leffaTaulu.setColumnWidth(0, 200);
        leffaTaulu.setColumnWidth(2, 130);
        leffaTaulu.setColumnWidth(4, 200);
        for(int i = 0; i < leffarekisteri.getLeffoja(); i++) {
            Leffa leffa = leffarekisteri.annaLeffa(i);
            String[] arr = new String[5];
            leffa.taytaTiedot(arr);
            arr[4] = leffarekisteri.leffalleKuuluvatGenretPilkunKera(leffa.getId());
            if (ehto.test(leffa))
                leffaTaulu.add(leffa, arr);
        }        
    }

    @Override
    public Leffarekisteri getResult() {
        return null;
    }

    /**
     * Asetetaan leffarekisteri ja ikkunan sulkemistarkistus
     * @param rekisteri leffarekisteri
     */
    @Override
    public void setDefault(Leffarekisteri rekisteri) {
        this.leffarekisteri = rekisteri;
        leffarekisteri.setOnLeffatPaivitetty(this);
        
        search.getScene().getWindow().setOnCloseRequest(event -> {
            if(!voikoSulkea()) {
                event.consume();
            } else {
                Platform.exit();
            }
        });
    }
}
