package LinguaMatch.gui.controller;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Classe abstraite commune à tous les Controller de l'interface 
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public abstract class Controller {
    /**
     * Fenêtre principale/parente par rapport à la fenêtre fille 
    */
    protected static Stage stage;

    /**
     * Initialisation du Controller
    */
    public abstract void initialize();

    /**
     * Sert à passer la fenêtre principale de l'interface dans le Controller
     * @param stage Fenêtre principale de l'interface
    */
    public void setStage(Stage stage) {
        Controller.stage = stage;
    }

    /**
     * Retourne le fx:id du composant encleché par un évènement de type ActionEvent
     * @param e Évènement de type ActionEvent
     */
    public String getId(ActionEvent e) {
        return ((Node)e.getSource()).getId();
    }

    /**
     * Affichage d'une boîte de dialogue en cas de problèmes
     * @param msg Message dans la boîte de dialogue
    */
    public static void showProblemDialog(String msg) {
        HBox hbox = new HBox();
        Stage stage2 = new Stage();
        Scene scene2 = new Scene(hbox, 650, 100);
        Text text = new Text(msg);
        HBox.setMargin(text, new Insets(10, 10, 10, 10));
        hbox.getChildren().add(text);
        stage2.setResizable(false);
        stage2.setScene(scene2);
        stage2.setTitle("Problème survenu");
        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initOwner(Controller.stage);
        stage2.show();
    }

}
