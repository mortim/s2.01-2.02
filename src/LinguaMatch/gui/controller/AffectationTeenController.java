package LinguaMatch.gui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import LinguaMatch.core.Country;
import LinguaMatch.core.Teenager;
import LinguaMatch.core.graph.Affectation;
import LinguaMatch.csv.CSVWriter;
import LinguaMatch.gui.event.TeenWindowListener;
import fr.ulille.but.sae2_02.graphes.Arete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

/**
 * Controller qui gère les composants de l'interface affichant le résultat des affectations
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class AffectationTeenController extends Controller {
    /**
     * La paire de pays hôte-visiteur pour l'affichage dans cette fenêtre 
    */
    public static Country[] countries;
    /**
     * Résultat des affectations
    */
    public static Affectation affectation;

    @FXML private ListView<Teenager> list;
    @FXML private ListView<Teenager> list2;
    @FXML private ListView<Double> list3;

    /**
     * Initialisation de tous les composants de la fenêtre 
    */
    public void initialize() {
        for(Arete<Teenager> ados : AffectationTeenController.affectation.getAffectations()) {
            this.list.getItems().add(ados.getExtremite1());
            this.list2.getItems().add(ados.getExtremite2());
            this.list3.getItems().add(ados.getPoids());
        }      
    }

    /**
     * Ouvre une boîte de dialogue avec un explorateur de fichiers et retourne le chemin indiqué 
    */
    public File saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Emplacement de la sauvegarde de votre fichier");
        return fileChooser.showSaveDialog(Controller.stage);
    }

    /**
     * Évènement enclenché lors de l'exportation d'un résultat d'affectation en CSV
     * @param e Un evènement de type ActionEvent
    */
    public void exportCSV(ActionEvent e) {
        File file = this.saveFile();
        if(file != null) {
            try {
                CSVWriter w = new CSVWriter(file.toString());
                w.write(AffectationTeenController.affectation.getAffectations());
                w.close();
            } catch(IOException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

    /**
     * Évènement enclenché lors de la sérialisation d'un résultat d'affectation en binaire
     * @param e Un evènement de type ActionEvent
    */
    public void serialize(ActionEvent e) {
        File file = this.saveFile();
        if(file != null) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(affectation);
                oos.close();
            } catch(IOException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

    /**
     * Ajoute un listener dans les 2 ListView de l'interface parmi l'élement selectionné afin d'enclencher une nouvelle action
     * @see TeenWindowListener
    */
    public void listen() {
        this.list.getSelectionModel().getSelectedItems().addListener(new TeenWindowListener(Controller.stage));
        this.list2.getSelectionModel().getSelectedItems().addListener(new TeenWindowListener(Controller.stage));
    }
}
