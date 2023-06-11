package LinguaMatch.gui.controller;

import LinguaMatch.Platform;
import LinguaMatch.core.Country;
import LinguaMatch.core.Teenager;
import LinguaMatch.gui.event.TeenWindowListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * Controller qui gère les composants de l'interface affichant la liste des adolescents selon la paire de pays
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class ListTeenController extends Controller {
    /**
     * La paire de pays hôte-visiteur pour l'affichage dans cette fenêtre 
    */
    public static Country[] countries;

    @FXML private Text pays1;
    @FXML private Text pays2;
    @FXML private ListView<Teenager> list1;
    @FXML private ListView<Teenager> list2;

    public void initialize() {
        this.pays1.setText(Platform.countries[0] + " (hôte)");
        this.pays2.setText(Platform.countries[1] + " (visiteur)");
        
        for(Teenager t : Platform.teenagers) {
            if(t.getCountry().name().equals(Platform.countries[0].name()))
                this.list1.getItems().add(t);
            if(t.getCountry().name().equals(Platform.countries[1].name()))
                this.list2.getItems().add(t);
        }
    }

    /**
     * Ajoute un listener dans les 2 ListView de l'interface parmi l'élement selectionné afin d'enclencher une nouvelle action
     * @see TeenWindowListener
    */
    public void listen() {
        this.list1.getSelectionModel().getSelectedItems().addListener(new TeenWindowListener(Controller.stage));
        this.list2.getSelectionModel().getSelectedItems().addListener(new TeenWindowListener(Controller.stage));
    }
}
