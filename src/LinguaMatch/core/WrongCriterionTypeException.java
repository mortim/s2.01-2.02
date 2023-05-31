package LinguaMatch.core;

/**
 * Exception qui traite les types incorrects sur des critères
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class WrongCriterionTypeException extends Exception {
    public WrongCriterionTypeException(String valeur, char typeActuel, char typeAttendu) {
        super("La valeur '" + valeur + "' est de type '" + CriterionName.getFullNameType(typeActuel) + "' mais le type attendu est de type '" + CriterionName.getFullNameType(typeAttendu) + "'");
    }
}