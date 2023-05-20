package LinguaMatch;

import java.util.Map;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;

/**
 * Décrit un adolescent participant à un séjour linguistique
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class Teenager {
    private static int nauto = 0;
    private int id;
    private String forename;
    private String name;
    private LocalDate birthDate;
    private final Country country;
    private Map<String, Criterion> requirements;

    /**
     * Constructeur qui prend en paramètre le nom de l'adolescent, sa date de naissance et son pays d'origine (cf: <a href="Country.html">Country</a>)
     * @param name Nom de l'adolescent
     * @param country Pays d'origine
    */
    public Teenager(String forename, String name, LocalDate birthDate, Country country) {
        this.id = Teenager.nauto++;
        this.forename = forename;
        this.name = name;
        this.birthDate = birthDate;
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
     * Retourne le prénom de l'adolescent
    */
    public String getForename() {
        return this.forename;
    }

    /**
     * Retourne le nom de l'adolescent
    */
    public String getName() {
        return this.name;
    }

    /**
     * Retourne la date de naissance de l'adolescent
    */
    public LocalDate getBirthDate() {
        return this.birthDate;
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
    public boolean addCriterion(String criterionName, Criterion criterion) {
        try {
            // On vérifie que le paramètre criterionName contient l'une des valeurs de l'enum CriterionName
            CriterionName.valueOf(criterionName);
            this.requirements.put(criterionName, criterion);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Vérifie si un adolescent a des critères incohérents suivant ces 2 types d'incohérences:
     * <ul>
     *  <li>Un critère censé stocker un booléen mais de valeur effective d'un autre type est incohérent</li>
     *  <li>Un adolescent déclarant une allergie aux animaux (contrainte rédhibitoire) mais déclarant aussi posséder un animal à la maison</li>
     * </ul>
    */
    public boolean hasInconsistencyCriterions() {
        Criterion hostCrit = this.requirements.get("HOST_HAS_ANIMAL");
        Criterion guestCrit = this.requirements.get("GUEST_ANIMAL_ALLERGY");
         
        if(hostCrit != null && guestCrit != null) {
            try {
                hostCrit.isValid();
                guestCrit.isValid();

                if(hostCrit.getValue().equals("yes") && guestCrit.getValue().equals("yes"))
                    return true;
            } catch(WrongCriterionTypeException e) {
                return true;
            }
        }
        return false;
    }

    /**
     * V1 de la méthode compatibleWithGuestGraphesV1
     * @param guest L'adolescent visiteur
     * @see compatibleWithGuest
    */
    public boolean compatibleWithGuestGraphesV1(Teenager guest) {
        // ----------------------
        // Contraine sur l'allergie
        Criterion hostCrit = this.requirements.get("HOST_HAS_ANIMAL");
        Criterion guestCrit = guest.requirements.get("GUEST_ANIMAL_ALLERGY");

        if(hostCrit != null && guestCrit != null)
            if(hostCrit.getValue().equals("yes") && guestCrit.getValue().equals("yes"))
                return false;

        return true;
    }

    /**
     * Vérifie qu'un adolescent visiteur est compatible avec un adolescent hôte
     * @param guest L'adolescent visiteur
    */
    public boolean compatibleWithGuest(Teenager guest) {
        // ----------------------
        // Contrainte sur l'allergie
        Criterion hostCrit = this.requirements.get("HOST_HAS_ANIMAL");
        Criterion guestCrit = guest.requirements.get("GUEST_ANIMAL_ALLERGY");

        // On ignore les critères qui n'existent pas chez l'un des 2 adolescents (ou les 2)
        if(hostCrit != null && guestCrit != null)
            if(hostCrit.getValue().equals("yes") && guestCrit.getValue().equals("yes"))
                return false;

        // ----------------------
        // Contrainte sur le régime alimentaire
        hostCrit = this.requirements.get("HOST_FOOD");
        guestCrit = guest.requirements.get("GUEST_FOOD");

        if(hostCrit != null && guestCrit != null)
            if(!Arrays.asList(hostCrit.toArray()).containsAll(Arrays.asList(guestCrit.toArray())))
                return false;
        
        // ----------------------
        // Contrainte sur l'historique
        // À implementer...
        
        // ----------------------
        // Contrainte sur certains pays (ex: France)
        hostCrit = this.requirements.get("HOBBIES");
        guestCrit = guest.requirements.get("HOBBIES");

        if(hostCrit != null && guestCrit != null)
            if((!AffectationUtil.containsAny(hostCrit.toArray(), guestCrit.toArray())) && (this.country == Country.FRANCE || guest.country == Country.FRANCE))
                return false;

        return true;
    }

    /**
     * Supprime les critères incohérents au niveau du type
     * @see Criterion#isValid()
    */
    public void purgeInvalidRequirement() {
        Map.Entry<String, Criterion> next;
        // Nous utilisons un itérateur pour nous simplifier la suppression d'éléments d'une structure de données en cours d'itération
        Iterator<Map.Entry<String, Criterion>> it = this.requirements.entrySet().iterator();
        while(it.hasNext()) {
            next = it.next();
            try {
                next.getValue().isValid();
            } catch(WrongCriterionTypeException e) {
                it.remove();
            }
        }
    }

    /**
     * Retourne une représentation en chaîne de caractères de l'objet
    */
    @Override
    public String toString() {
        return this.forename + " " + this.name;
    }
    
}