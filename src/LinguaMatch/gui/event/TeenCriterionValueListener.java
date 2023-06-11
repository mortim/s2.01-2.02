package LinguaMatch.gui.event;

import LinguaMatch.core.Criterion;
import LinguaMatch.core.CriterionName;
import LinguaMatch.gui.controller.TeenInfoController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Listener qui enlenche la modification des critères d'un adolescent
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class TeenCriterionValueListener implements ChangeListener<String> {
    private String criterionName;

    /**
     * Constructeur passant en paramètre le nom du critère
     * @param criterionName
    */
    public TeenCriterionValueListener(String criterionName) {
        this.criterionName = criterionName;
    }

    /**
     * Effectue le changèment lorsqu'il détecte un changement de valeur dans un texte (ici dans un TextField)
     * @param observable Objet vérifiant le changement de valeur en l'occurence ici, le changement de valeur d'un critère
     * @param oldValue Ancienne valeur d'un critère
     * @param newValue Nouvelle valeur d'un critère
    */
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        TeenInfoController.ado.getRequirements().put(this.criterionName, new Criterion(CriterionName.valueOf(this.criterionName), newValue));
    }
}
