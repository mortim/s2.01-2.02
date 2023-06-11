package LinguaMatch.gui.controller;

import java.util.Map;
import LinguaMatch.core.Criterion;
import LinguaMatch.core.Teenager;
import LinguaMatch.gui.event.TeenCriterionValueListener;
import LinguaMatch.gui.event.ListTeenWindowEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Controller qui gère les composants de l'interface affichant les informations d'un adolescent
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class TeenInfoController extends Controller {
    /**
     * Informations d'un adolescent en question enclenché par le listener ListView
     * @see ListTeenWindowEvent
    */
    public static Teenager ado;

    @FXML private Text titre;
    @FXML private TextField pays;
    @FXML private TextField date;
    @FXML private ListView<HBox> criteres;

    /**
     * Initialise les composants du Controller 
    */
    public void initialize() {
        HBox hbox;
        TextField t1, t2;

        this.titre.setText("n°" + ado.getId() + " (" + ado.getForename() + " " + ado.getName() + ")");
        this.pays.setText(ado.getCountry().toString());
        this.date.setText(ado.getBirthDate().toString());
        
        for(Map.Entry<String, Criterion> c : ado.getRequirements().entrySet()) {
            hbox = new HBox();

            t1 = new TextField(c.getKey());
            t1.setEditable(false);

            t2 = new TextField(c.getValue().toString());
            t2.textProperty().addListener(new TeenCriterionValueListener(c.getKey()));

            hbox.getChildren().addAll(t1, t2);

            this.criteres.getItems().add(hbox);
        }
    }
}
