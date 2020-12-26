package fxMovies;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import movies.Leffarekisteri;
import movies.SailoException;


/**
 * @author Jenni Arovaara
 * @version 5.6.2019
 *
 * Pääohjelma.
 */
public class MoviesMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Luetaan leffarekisteri
            Leffarekisteri leffarekisteri = new Leffarekisteri();
            lueTiedosto(leffarekisteri);
            
            ModalController.<Leffarekisteri, MoviesGUIController>showModeless(
                    MoviesGUIController.class.getResource("MoviesGUIView.fxml"),
                    "Leffat",
                    leffarekisteri);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void lueTiedosto(Leffarekisteri leffarekisteri) {
        try {
            leffarekisteri.lueTiedostosta();
        } catch (SailoException ex) {
            String virhe = ex.getMessage();
            if (virhe != null) {
                System.out.println(virhe);
                Dialogs.showMessageDialog(virhe);
            }
            Platform.exit();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}
