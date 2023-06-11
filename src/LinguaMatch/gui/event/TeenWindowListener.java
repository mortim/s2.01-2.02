package LinguaMatch.gui.event;

import java.io.IOException;
import LinguaMatch.core.Teenager;
import LinguaMatch.gui.Util;
import LinguaMatch.gui.controller.TeenInfoController;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Listener qui enclenche l'affichage des informations d'un adolescent selon les 2 pays 
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
 */
public class TeenWindowListener implements ListChangeListener<Teenager> {
    private Stage stage;

    /**
     * Constructeur passant en paramètre la fenêtre principale
     * @param stage La fenêtre principale
    */
    public TeenWindowListener(Stage stage) {
        this.stage = stage;
    }

    /**
     * Lorsque le listener détecte un changement de sélection dans l'une des 2 ListView il invoquera cette méthode
     * @param report Paramètre qui rapporte le changement effecuté (ex: le nom de l'item sélectionné)
    */
    public void onChanged(Change<? extends Teenager> report) {
        Teenager t = report.getList().get(0);

        Stage thirdStage = new Stage();

        FXMLLoader loader = new FXMLLoader();

        try {
            TeenInfoController.ado = t;
            
            Parent parent = Util.loadFXML(loader, "infos_ado.fxml");
            Scene scene =  new Scene(parent);
            
            thirdStage.setScene(scene);
            thirdStage.setResizable(false);
            thirdStage.setTitle("L'adolescent " + t.getForename() + " " + t.getName());
            thirdStage.initOwner(this.stage);
            thirdStage.show();
        } catch(IOException e) {
            System.out.println("Problème survenu au niveau du chargement du fichier 'infos_ados.fxml'");
        }
        
    }
}
