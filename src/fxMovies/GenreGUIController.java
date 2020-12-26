package fxMovies;

import java.awt.Desktop;
import java.net.URL;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import movies.Genre;


/**
 * @author Jenni Arovaara
 * @version 18.6.2019
 *
 * Käyttöliittymä genren lisäämiseen.
 */
public class GenreGUIController implements ModalControllerInterface<Genre> {

    /**
     * Kentät
     */
    @FXML private TextField nimi;

    /**
     * Nappulat
     */
    @FXML private Button tallenna;
    @FXML private Button sulje;

    /**
     * Ohje menussa
     */
    @FXML private MenuItem ohje;
    
    // =========================== Toimintojen määrittely =======================================================
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
     * Tapahtuman käsittelijä: tallenetaan muutetut tiedot
     * @param event
     */
    @FXML void save(ActionEvent event) {
        save();
    }

    private int genreId = -1;
    private boolean saveMe = false;
    
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
     * Suljetaan ikkuna.
     */
    public void close() {
        ModalController.closeStage(sulje);
        //sulje.getScene().getWindow().hide();
    }
     
    /**
     * Tallennetaan muutetut tai lisätyt tiedot.
     */
    public void save() {
        if (!validoi()) return;
        saveMe = true;
        close();
    }

    /**
     * Validoidaan käyttäjän syötteet.
     * @return onko käyttäjän syötteet valideja
     */
    private boolean validoi() {
        if (nimi.getText().length() < 3) {
            Modals.notify("Genren täytyy olla vähintään kolme merkkiä pitkä.");
            return false;
        }
        return true;
    }

    /**
     * Luodaan genre dialogin sisällöstä.
     * @return genre
     */
    @Override
    public Genre getResult() {
        if (!saveMe)
            return null;
        Genre genre = new Genre(this.genreId, nimi.getText());
        return genre;
    }

    @Override
    public void handleShown() {
        // Auto-generated method stub
    }

    /**
     * Näytetään yksittäisen genren tiedot.
     * @param genre genre
     */
    @Override
    public void setDefault(Genre genre) {
        if (genre == null)
            return;
        this.genreId = genre.getId();
        nimi.setText(genre.getNimi());
    }
}
