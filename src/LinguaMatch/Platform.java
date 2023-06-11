package LinguaMatch;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import LinguaMatch.core.Country;
import LinguaMatch.core.CriterionTypeException;
import LinguaMatch.core.Teenager;
import LinguaMatch.gui.Util;
import LinguaMatch.gui.controller.MainController;
import fr.ulille.but.sae2_02.graphes.Arete;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Représente la plateforme (interface graphique) d'affectation de tous les adolescents, c'est le point d'entrée de l'interface
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class Platform extends Application {
    /**
     * Liste des adolescents du fichier de configuration (CSV) chargé dans l'interface
    */
    public static List<Teenager> teenagers;
    /**
     * L'historique d'affectation du fichier CSV chargé dans l'interface
    */
    public static List<Arete<Teenager>> historiqueAffectation;
    /**
     * Tableau des 2 pays (hôte-visiteur) sélectionné
     */
    public static Country[] countries;
    /**
     * Chemin vers les fichiers FXML (pour les interfaces FXML)
    */
    public static final String FXML_LOCATION = System.getProperty("user.dir") + File.separator + "mockups" + File.separator + "hifi" + File.separator;
    /**
     * Chemin vers le fichier de configuration par défaut 
    */
    public static final String CSV_DEFAULT_LOCATION = System.getProperty("user.dir") + File.separator + "graphes" + File.separator + "V2" + File.separator + "csv" + File.separator + "exemple_minimal.csv";

    /**
     * Filtre les adolescents (en les supprimant) ayant les valeurs de critères incohérents (suivant les indications de la méthode hasInconsistencyCriterion)
     * @see Teenager#hasInconsistencyCriterion()
    */
    public static void filterTeenagers() {
        Teenager next;
        Iterator<Teenager> it = Platform.teenagers.iterator();
        while(it.hasNext()) {
            next = it.next();
            try {
                next.hasInconsistencyCriterion();
            } catch(CriterionTypeException e) {
                System.out.println(e.getMessage());
                it.remove();
            }
        }
    }

    /**
     * Supprime les critères invalides de tous les adolescents (types incorrects)
     * @see Teenager#purgeInvalidRequirement()
    */
    public static void purgeInvalidRequirement() {
        for(Teenager t : Platform.teenagers)
            t.purgeInvalidRequirement();
    }

    /**
     * Démarrage de l'interface graphique
    */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = Util.loadFXML(loader, "main.fxml");

            MainController main = loader.getController();
            main.setStage(stage);

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("LinguaMatch");
            stage.show();
        } catch(IOException e) {
            System.out.println("Problème survenu au niveau du chargement du fichier 'main.fxml'");
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}