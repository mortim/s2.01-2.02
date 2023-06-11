
package LinguaMatch.gui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import LinguaMatch.Platform;
import LinguaMatch.core.graph.AffectationUtil;
import LinguaMatch.csv.CSVFileType;
import LinguaMatch.csv.CSVReader;
import LinguaMatch.csv.WrongCSVStructureException;
import LinguaMatch.gui.event.ListTeenWindowEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Controlleur de l'interface de départ (MVC)
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class MainController extends Controller {
    private File selectedFile;
    @FXML private Spinner<Integer> spinner1, spinner2, spinner3;
    @FXML private TextField chemin;
    @FXML private TextField historique;
    @FXML private ListView<HBox> list;

    /**
     * Initialise certains composants de l'interface, notamment les spinners avec les valeurs par défaut et les bornes inférieures, supérieures
    */
    public void initialize() {
        this.spinner1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, AffectationUtil.AFFINITY_BONUS, 50));
        this.spinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, AffectationUtil.STRONG_CONSTRAINT_BONUS, 50));
        this.spinner3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, AffectationUtil.COMPATIBILITY_MALUS, 50));
    }

    /**
     * Ouvre un explorateur de fichiers lors de l'enclenchement de l'un des 2 boutons "Ouvrir un fichier" de l'interface graphique
     * @param e Un évènement ActionEvent enclenché par un bouton "Ouvrir un fichier"
     * @see ActionEvent
     */
    public void openCSV(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
        
        this.selectedFile = fileChooser.showOpenDialog(Controller.stage);

        if(this.selectedFile != null) {
            if(this.getId(e).equals("config_btn"))
                this.chemin.setText(this.selectedFile.toString());
            else
                this.historique.setText(this.selectedFile.toString());
        }
    }

    /**
     * Charge les 2 fichiers CSV (fichier de configuration / historique d'affectations)
     * @param e Un évènement ActionEvent enclenché par le bouton "Charger"
     */
    public void loadCSV(ActionEvent e) {
        if(this.historique.getText().equals(""))
            Controller.showProblemDialog("Le chemin vers l'historique d'affectations est vide");
        else {
            // Utiliser le chemin par défaut (le fichier adosAleatoires.csv du dossier csv de ce projet)
            if(this.chemin.getText().equals(""))
                this.chemin.setText(Platform.CSV_DEFAULT_LOCATION);

            // Dans le cas où l'on recharge un nouveau fichier
            this.list.getItems().clear();

            CSVReader csv = null;
            CSVReader csv2 = null;
            HBox hbox;
            Button b1, b2;

            try {
                csv = new CSVReader(this.chemin.getText());
                csv.load();

                csv2 = new CSVReader(this.historique.getText(), CSVFileType.HISTORY);
                csv2.load();

                // Des logs sur console...
                System.out.println("Erreurs: " + this.chemin.getText());
                System.out.println(csv.getErrors());

                System.out.println("Erreurs: " + this.historique.getText());
                System.out.println(csv2.getErrors());

                Platform.teenagers = csv.getParsed();
                Platform.historiqueAffectation = csv2.getHistoryParsed();

                // Pré-traitement
                Platform.filterTeenagers();
                Platform.purgeInvalidRequirement();
             
                List<String> peers = AffectationUtil.getAllCountriesPeers(csv.getCountries());
                
                for(String peer : peers) {
                    hbox = new HBox();

                    b1 = new Button("Infos " + peer);
                    HBox.setMargin(b1, new Insets(0,0,0,10));
                    b1.setOnAction(new ListTeenWindowEvent("liste_ados.fxml", "Informations sur les adolescents de ces 2 pays", Controller.stage, this.spinner1, this.spinner2, this.spinner3));
                        
                    b2 = new Button("Calculer l'affectation " + peer);
                    HBox.setMargin(b2, new Insets(0,0,0,10));
                    b2.setOnAction(new ListTeenWindowEvent("resultats_affectations.fxml", "Résultats des affectations", Controller.stage, this.spinner1, this.spinner2, this.spinner3));

                    hbox.getChildren().addAll(new Text(peer), b1, b2);

                    this.list.getItems().add(hbox);
                }
            } catch(WrongCSVStructureException e2) {
                Controller.showProblemDialog(e2.getMessage());
            } catch(FileNotFoundException e2) {
                Controller.showProblemDialog(e2.getMessage());
            } finally {
                if(csv != null) csv.close();
                if(csv2 != null) csv2.close();
            }
        }
    }
}