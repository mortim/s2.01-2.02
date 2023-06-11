package LinguaMatch.gui;

import java.io.IOException;
import java.net.URL;

import LinguaMatch.Platform;
import LinguaMatch.core.Country;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Classe avec des méthodes utiles pour l'interface graphique
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
 */
public class Util {
    /**
     * Charge un fichier FXML présent dans le chemin FXML_LOCATION
     * @param loader Chargeur de FXML
     * @param fxmlFile Fichier FXML
     * @throws IOException
     * @see Platform
     */
    public static Parent loadFXML(FXMLLoader loader, String fxmlFile) throws IOException {
        URL fxml = new URL("file", "", Platform.FXML_LOCATION + fxmlFile);
        loader.setLocation(fxml);
        return loader.load();
    }

    /**
     * Récupère les 2 pays hôte-visiteur écrit dans les boutons "Infos [PAYS 1] - [PAYS 2]" et "Calculer l'affectation [PAYS 1] - [PAYS 2]"
     * @param buttonLabel Le texte contenu dans les boutons de l'interface
     */
    public static Country[] getCountries(String buttonLabel) {
        Country[] countries = new Country[2];
        String[] words = buttonLabel.split(" ");
        countries[0] = Country.valueOf(words[words[0].equals("Infos") ? 1 : 2]);
        countries[1] = Country.valueOf(words[words[0].equals("Infos") ? 3 : 4]);
        return countries;
    }
}
