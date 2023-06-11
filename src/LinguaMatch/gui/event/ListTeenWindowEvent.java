package LinguaMatch.gui.event;

import java.io.IOException;
import LinguaMatch.Platform;
import LinguaMatch.core.graph.Affectation;
import LinguaMatch.core.graph.AffectationUtil;
import LinguaMatch.gui.Util;
import LinguaMatch.gui.controller.AffectationTeenController;
import LinguaMatch.gui.controller.Controller;
import LinguaMatch.gui.controller.ListTeenController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

/**
 * Évènement qui enlenche l'affichage de la liste des adolescents (que ce soit avant ou après le calcul d'affectation)
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class ListTeenWindowEvent implements EventHandler<ActionEvent> {
    private String fxmlFile;
    private String titlePage;
    private Stage stage;
    private Spinner<Integer> spinner1;
    private Spinner<Integer> spinner2;
    private Spinner<Integer> spinner3;

    /**
     * Constructeur de la classe passant en paramètre la fenêtre principale
     * @param stage Fenêtre principale
    */
    public ListTeenWindowEvent(String fxmlFile, String titlePage, Stage stage, Spinner<Integer> spinner1, Spinner<Integer> spinner2, Spinner<Integer> spinner3) {
        this.fxmlFile = fxmlFile;
        this.titlePage = titlePage;
        this.stage = stage;
        this.spinner1 = spinner1;
        this.spinner2 = spinner2;
        this.spinner3 = spinner3;
    }

    /**
     * Méthode qui sera enclenché lors d'une action de type ActionEvent sur le bouton "Infos/Calculer l'affectation [PAYS 1] - [PAYS 2]"
    */
    public void handle(ActionEvent e) {
        try {
            // Prise en compte des valeurs des bonus/malus sur l'affectation
            AffectationUtil.AFFINITY_BONUS = this.spinner1.getValue();
            AffectationUtil.STRONG_CONSTRAINT_BONUS = this.spinner2.getValue();
            AffectationUtil.COMPATIBILITY_MALUS = this.spinner3.getValue();

            Platform.countries = Util.getCountries(((Button)(e.getTarget())).getText());

            Affectation affectation = new Affectation();
            AffectationUtil.ajouterAdolescents(affectation, Platform.teenagers, Platform.countries);
            
            if(affectation.getSommetsGauche().size() != affectation.getSommetsDroite().size())
                Controller.showProblemDialog("Le membre de gauche n'a pas la même taille que le membre de droite dans le graphe biparti");
            else {
                AffectationUtil.ajouterAretesAdolescents(affectation, Platform.teenagers, Platform.historiqueAffectation, Platform.countries);
                affectation.calculerAffectation();
                AffectationTeenController.affectation = affectation;
    
                FXMLLoader loader = new FXMLLoader();
                Parent parent = Util.loadFXML(loader, this.fxmlFile);

                Stage secondStage = new Stage();
                Scene scene =  new Scene(parent);

                if(this.fxmlFile.equals("liste_ados.fxml")) {
                    ListTeenController controller = (ListTeenController)loader.getController();
                    controller.setStage(secondStage);
                    controller.listen();
                }

                if(this.fxmlFile.equals("resultats_affectations.fxml")) {
                    AffectationTeenController controller = (AffectationTeenController)loader.getController();
                    controller.setStage(secondStage);
                    controller.listen();
                }

                secondStage.setScene(scene);
                secondStage.setResizable(false);
                secondStage.setTitle(this.titlePage);
                secondStage.initOwner(this.stage);
                secondStage.show();
            }
        } catch(IOException e2) {
            e2.printStackTrace();
        }
    }
}
