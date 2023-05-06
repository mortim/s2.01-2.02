package LinguaMatch;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;

/**
 * Décrit un adolescent participant à un séjour linguistique
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class Teenager {
    private static int nauto = 0;
    private int id;
    private String name;
    private final Country country;
    private Map<String, Criterion> requirements;

    /**
     * Constructeur qui prend en paramètre le nom de l'adolescent, sa date de naissance et son pays d'origine (cf: <a href="Country.html">Country</a>)
     * @param name Nom de l'adolescent
     * @param country Pays d'origine
    */
    public Teenager(String name, Country country) {
        this.id = Teenager.nauto++;
        this.name = name;
        this.country = country;
        this.requirements = new HashMap<String, Criterion>();
    }

    /**
     * Retourne l'identifiant de l'adolescent (numéro automatique défini via un attribut de classe)
    */
    public int getId() {
        return this.id;
    }

    /**
     * Retourne le nom de l'adolescent
    */
    public String getName() {
        return this.name;
    }

    /**
     * Retourne le pays d'origine de l'adolescent
     * @see Country
    */
    public Country getCountry() {
        return this.country;
    }

    /**
     * Retourne la table associative des critères renseignés pas l'adolescent
    */
    public Map<String, Criterion> getRequirements() {
        return this.requirements;
    }

    /**
     * Suprpime un critère de la table associative
     * @param k Une clé de la table associative
     */
    public Criterion removeRequirement(String k) {
        return this.requirements.remove(k);
    }

    /**
     * Réaffecte la table associative des critères avec celui en paramètre
     * @param requirements La nouvelle table associative
    */
    public void setRequirements(Map<String, Criterion> requirements) {
        this.requirements = requirements;
    }

    /**
     * Ajoute un critère particulier à la table associative stockant tous les critères de l'adolescent
     * @param criterionName Nom du critère
     * @param criterion Le critère
     * @see getRequirements()
    */
    public void addCriterion(String criterionName, Criterion criterion) {
        if(CriterionName.isCriterionName(criterionName))
            this.requirements.put(criterionName, criterion);
    }

    // Méthode utile pour vérifier qu'une liste contient au moins un élement d'un tableau
    private boolean containsAny(List<String> list, String[] arr) {
        for(int i = 0; i < arr.length; i++) {
            if(list.contains(arr[i]))
                return true;
        }
        return false;
    }

    /**
     * Retourne le nombre d'incohérences d'un adolescent
     * <p>2 types d'incohérences sont prises en compte:</p>
     * <ul>
     *  <li>Un critère censé stocker un booléen mais de valeur effective d’un autre type</li>
     *  <li>Un adolescent déclarant une allergie aux animaux (contrainte rédhibitoire) mais déclarant aussi posséder un animal à la maison</li>
     * </ul>
     * @see CriterionName#GUEST_ANIMAL_ALLERGY
     * @see CriterionName#HOST_HAS_ANIMAL
     * @see Platform#filterTeenagers
     */
    public int getNbMismatch() {
        int nb = 0;
        if(this.requirements != null) {
            Criterion visitorCriterion = this.requirements.get("GUEST_ANIMAL_ALLERGY");
            Criterion guestCriterion = this.requirements.get("HOST_HAS_ANIMAL");

            if(!visitorCriterion.isValid())
                nb++;
            if(!guestCriterion.isValid())
                nb++;
            if(visitorCriterion.getValue().equals("yes") && guestCriterion.getValue().equals("yes"))
                nb++;
        }
        return nb;
    }

    /**
     * Vérifie qu'un adolescent visiteur est compatible avec un adolescent hôte
     * @param guest L'adolescent hôte
    */
    public boolean compatibleWithGuest(Teenager guest) {
        if(this.requirements == null || guest.requirements == null)
            return false;

        // ----------------------
        // Contraine sur l'allergie
        Criterion visitorCrit = this.requirements.get("GUEST_ANIMAL_ALLERGY");
        Criterion guestCrit = guest.requirements.get("HOST_HAS_ANIMAL");

        // Si l'un des 2 est nul (mais pas les 2) (XOR operator)
        if(visitorCrit == null ^ guestCrit == null)
            return false;
        // Si les 2 ne sont pas nuls (dans le cas contraires on regarde les autes contraintes)
        else if(visitorCrit != null && guestCrit != null) {
            if(visitorCrit.getValue().equals("yes") && guestCrit.getValue().equals("yes"))
                return false;
        }

        // ----------------------
        // Contraine sur le régime 
        visitorCrit = this.requirements.get("GUEST_FOOD");
        guestCrit = guest.requirements.get("HOST_FOOD");

        if(visitorCrit == null ^ guestCrit == null)
            return false;
        else if(visitorCrit != null && guestCrit != null) {
            if(!Arrays.asList(guestCrit.getValue().split(",")).containsAll(Arrays.asList(visitorCrit.getValue().split(","))))
                return false;
        }

        // ----------------------
        // Contraine sur les passes-temps
        visitorCrit = this.requirements.get("HOBBIES");
        guestCrit = guest.requirements.get("HOBBIES");

        if(visitorCrit == null ^ guestCrit == null)
            return false;
        else if(visitorCrit != null && guestCrit != null) {
            // Vérifier si le visiteur contient au moins 1 critère requis par l'hôte français
            if(guest.country == Country.FRANCE && !this.containsAny(Arrays.asList(visitorCrit.getValue().split(",")), guestCrit.getValue().split(",")))
                return false;
        }

        // ----------------------
        // Contraine sur l'historique d'affectation
        visitorCrit = this.requirements.get("HISTORY");
        guestCrit = guest.requirements.get("HISTORY");

        if(visitorCrit == null ^ guestCrit == null)
            return false;
        else if(visitorCrit != null && guestCrit != null) {
            if(visitorCrit.getValue().equals("other") || guestCrit.getValue().equals("other"))
                return false;
        }

        return true;
    }

    /**
     * Supprime les critères incohérents au niveau du type
     * @see Criterion#isValid()
    */
    public void purgeInvalidRequirement() {
        Map.Entry<String, Criterion> entry;
        // Nous utilisons un iterateur pour nous simplifier la suppression d'éléments d'une structure de données en cours d'itération
        Iterator<Map.Entry<String, Criterion>> it = this.requirements.entrySet().iterator();
        while(it.hasNext()) {
            entry = it.next();
            if(!entry.getValue().isValid())
                it.remove();
        }
    }

    /**
     * Retourne une représentation en chaîne de caractères de l'objet
    */
    @Override
    public String toString() {
        return this.name + "\n-----\n- Pays d'origine: " + this.country + "\n- Critères: " + this.requirements;
    }
    
}