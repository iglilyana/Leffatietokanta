package fxMovies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Jenni Arovaara
 * @version 5.6.2019
 *
 * Käyttöliittymä tietoja-ikkunalla.
 */
public class TietojaGUIController {
    
    @FXML private Button ok;

    @FXML void close(ActionEvent event) {
        close();
    }
    
    // =========================== Metodit =======================================================
    /**
     * Suljetaan ikkuna.
     */
    public void close() {
        ok.getScene().getWindow().hide();
    }
}
