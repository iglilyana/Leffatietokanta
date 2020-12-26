package fxMovies;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

/**
 * @author Jenni Arovaara
 * @version 21.7.2019
 *
 * Apuluokka käyttäjän toimien varmistamiseksi.
 */
public class Modals {

    /**
     * Tarkistaa onko käyttäjä varma toiminnastaan.
     * @param viesti varmistusviesti käyttäjälle
     * @return true, jos käyttäjä oli varma ja false, jos käyttäjä oli epävarma
     */
    public static boolean confirmAction(String viesti) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(viesti);
        ButtonType buttonTypeYes = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        
        return result.get() == buttonTypeYes;
    }
    
    /**
     * Ilmoitus käyttäjälle.
     * @param viesti viesti käyttäjälle
     */
    public static void notify(String viesti) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Huomio!");
        alert.setHeaderText(null);
        alert.setContentText(viesti);
        ButtonType buttonTypeYes = new ButtonType("OK", ButtonData.OK_DONE);

        alert.getButtonTypes().setAll(buttonTypeYes);
        alert.showAndWait();
        
    }
    
    /**
     * Ilmoitus käyttäjälle.
     * @param viestit käyttäjälle annettavat viestit
     */
    public static void notify(ArrayList<String> viestit) {
        notify(String.join("\n\n", viestit));
    }
}
