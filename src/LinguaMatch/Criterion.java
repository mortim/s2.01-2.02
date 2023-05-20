package LinguaMatch;

/**
 * Décrit un critère (via un nom ou label et sa valeur) et son comportement
 * parmi tous les critères possibles dans le formulaire d'inscription au séjour
 * linguistique
 * 
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
 */
public class Criterion {
    private CriterionName label;
    private String value;

    /**
     * Constructeur qui prend en paramètre la valeur du critère et son nom (cf:
     * <a href="CriterionName.html">CriterionName</a>)
     * 
     * @param value Valeur du critère
     * @param label Nom du critère issu (cf: CriterionName enum)
     */
    public Criterion(CriterionName label, String value) {
        this.label = label;
        this.value = value;
    }

    /**
     * Getter pour l'attribut label de la classe Criterion
     * 
     * @return Le nom du critère (cf: CriterionName enum)
     */
    public CriterionName getLabel() {
        return this.label;
    }

    /**
     * Getter pour l'attribut value de la classe Criterion
     * 
     * @return La valeur du critère
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Transforme la/les valeurs d'un critère en tableau
     * 
     * @return Le tableau de valeurs
     */
    public String[] toArray() {
        return this.value.split(",");
    }

    /**
     * Retourne le type "réel" dérrière la valeur du critère,
     * exemple: "yes" représente la valeur true donc on renvoie 'B' (pour boléen)
     * 
     * @return char représentant le type "réel"
     */
    public char infer() {
        if (this.value.equals("yes") || this.value.equals("no"))
            return 'B';
        else
            return 'T';
    }

    /**
     * Vérifie que la valeur est cohérente avec le nom du critère (ici représénté
     * par le nom de la colonne dans un CSV), on s'attend à ce que le type reseigné
     * sous cette colonne corresponde à celui de la valeur (ex: GUEST_ANIMAL_ALLERGY
     * est de type booléen, on s'attend à un boléen representé par "yes" ou "no")
     * 
     * @see infer()
     * @exception WrongCriterionTypeException Si le type actuel de la valeur ne correspond pas à celui attendu par le critère
     * @return boléen qui renvoie true si le type de la valeur et le type du critère correspondent
     *      
    */
    public boolean isValid() throws WrongCriterionTypeException {
        char typeActuel = this.infer();
        char typeAttendu = this.label.getType();
        if(typeActuel != typeAttendu)
            throw new WrongCriterionTypeException(this.value, typeActuel, typeAttendu);
        return true;
    }

    /**
     * Vérifie l'égalité entre 2 critères
     * 
     * @param o Un objet
    */
    public boolean equals(Object o) {
        if (o == null)
            return false;
        else if (this == o)
            return true;
        else if (this.getClass() == o.getClass()) {
            Criterion crit = (Criterion)o;
            return this.label == crit.label && this.value.equals(crit.value);
        } else
            return false;
    }

    /**
     * Retourne une représentation en chaîne de caractères de l'objet
    */
    public String toString() {
        return "[" + this.label + ":" + this.value + "]";
    }
}