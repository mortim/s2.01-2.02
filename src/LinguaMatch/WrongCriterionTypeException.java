package LinguaMatch;

public class WrongCriterionTypeException extends Exception {
    public WrongCriterionTypeException(String valeur, char typeActuel, char typeAttendu) {
        super("La valeur '" + valeur + "' est de type '" + CriterionName.getFullNameType(typeActuel) + "' mais le type attendu est de type '" + CriterionName.getFullNameType(typeAttendu) + "'");
    }
}