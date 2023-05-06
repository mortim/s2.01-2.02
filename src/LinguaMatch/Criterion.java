package LinguaMatch;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Décrit un critère (via un nom ou label et sa valeur) et son comportement parmi tous les critères possibles dans le formulaire d'inscription au séjour linguistique
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class Criterion {
    private CriterionName label;
    private String value;

    /**
     * Constructeur qui prend en paramètre la valeur du critère et son nom (cf: <a href="CriterionName.html">CriterionName</a>)
     * @param value Valeur du critère
     * @param label Nom du critère issu (cf: CriterionName enum)
    */
    public Criterion(String value, CriterionName label) {
        this.value = value;
        this.label = label;
    }
    
    /**
     * Getter pour l'attribut label de la classe Criterion
     * @return Le nom du critère (cf: CriterionName enum)
     */
    public CriterionName getLabel(){
        return this.label;
    }

    /**
     * Getter pour l'attribut value de la classe Criterion
     * @return La valeur du critère
     */
    public String getValue(){
        return this.value;
    }

    // Méthode utile qui vérifie si la valeur du critère est un nombre, on vérifie si chaque caractère que compose la valeur est un chiffre.
    private boolean isNumeric() {
        for(int i = 0; i < this.value.length(); i++) {
            if(this.value.charAt(i) < '0' || this.value.charAt(i) > '9')
                return false;
        }
        return true;
    }

    // Méthode utile qui vérifie si la valeur du critère est une date, pour cela on parse (cf: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDate.html#parse(java.lang.CharSequence)) et si l'éxécution de cette méthode lève une exception alors on l'attrape (catch) pour retourner false dans ce genre de situations sinon on renvoie true
    private boolean isCorrectDate() {
        try {
            LocalDate.parse(this.value);
            return true;
        // Exception levé lorsque la date n'a pas été correctement parsé
        } catch(DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Retourne le type "réel" dérrière la valeur du critère,
     * exemple: "123" est de type integer donc on renvoie 'N'
     * @return char représentant le type "réel"
    */
    public char typeInfer() {
        // yes représente true et no représente false
        if(this.value.equals("yes") || this.value.equals("no"))
            return 'B';
        else if(this.isNumeric())
            return 'N';
        else if(this.isCorrectDate())
            return 'D';
        else
            return 'T';
    }

    /**
     * Vérifie que la valeur est cohérente avec le nom du critère (ici représénté par le nom de la colonne dans un CSV), on s'attend à ce que le type reseigné sous cette colonne corresponde à celui de la valeur (ex: GUEST_ANIMAL_ALLERGY est de type booléen, on s'attend à un boléen representé par "yes" ou "no")
     * 
     * @see typeInfer()
     * 
     * @return boléen qui renvoie true si le type de la valeur et le type du critère correspondant, false sinon
    */
    public boolean isValid(){
        return this.typeInfer() == this.label.getType();
    }

    /**
     * Vérifie l'égalité entre 2 critères
     * @param o Un objet
    */
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o.getClass().getSimpleName().equals("Criterion")) {
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