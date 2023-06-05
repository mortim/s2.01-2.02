package LinguaMatch.core;

/**
 * Exception qui traite les types incorrects sur des critères
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class CriterionTypeException extends Exception {
    public CriterionTypeException(String text) {
        super(text);
    }

    public CriterionTypeException(String valeur, char typeActuel, char typeAttendu) {
        this("La valeur '" + valeur + "' est de type '" + CriterionName.getFullNameType(typeActuel) + "' mais le type attendu est de type '" + CriterionName.getFullNameType(typeAttendu) + "'");
    }

}