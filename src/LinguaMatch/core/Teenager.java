package LinguaMatch.core;

import LinguaMatch.core.graph.AffectationUtil;
import fr.ulille.but.sae2_02.graphes.Arete;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

/**
 * Décrit un adolescent participant à un séjour linguistique
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class Teenager implements Serializable {
    private static int nauto = 0;
    private int id;
    private String forename;
    private String name;
    private LocalDate birthDate;
    private Country country;
    private Map<String, Criterion> requirements;

    /**
     * Constructeur qui prend en paramètre le nom de l'adolescent, sa date de naissance et son pays d'origine (cf: <a href="Country.html">Country</a>)
     * @param forename Prénom de l'adolescent
     * @param name Nom de l'adolescent
     * @param birthDate Date de naissance de l'adolescent
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
     * Retourne la table associative des critères renseignés par l'adolescent
    */
    public Map<String, Criterion> getRequirements() {
        return this.requirements;
    }

    /**
     * Retourne la valeur d'un critère en particulier de l'adolescent
    */
    public String getRequirement(String criterionName) {
        if(this.requirements.get(criterionName) != null)
            return this.requirements.get(criterionName).toString();
        else
            return "";
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
     * @param criterion Le critère
     * @see getRequirements
    */
    public boolean addCriterion(Criterion criterion) {
        try {
            // On vérifie que le paramètre criterionName contient l'une des valeurs de l'enum CriterionName
            CriterionName.valueOf(criterion.getLabel().name());
            this.requirements.put(criterion.getLabel().name(), criterion);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Vérifie si un adolescent a des critères incohérents suivant ce cette règle:
     * <ul>
     *  <li>Un adolescent déclarant une allergie aux animaux (contrainte rédhibitoire) mais déclarant aussi posséder un animal à la maison</li>
     * </ul>
    */
    public boolean hasInconsistencyCriterion() throws CriterionTypeException {
        Criterion hostCrit = this.requirements.get("HOST_HAS_ANIMAL");
        Criterion guestCrit = this.requirements.get("GUEST_ANIMAL_ALLERGY");
         
        if(hostCrit != null && guestCrit != null) {
            if(hostCrit.getValue().equals("yes") && guestCrit.getValue().equals("yes"))
                throw new CriterionTypeException("Un adolescent ne peut pas être allergène aux animaux et avoir un animal chez lui.");
        }

        return false;
    }

     /**
     * V2 de la méthode compatibleWithGuest en introduisant des critères supplémentaires et un un degré d'incompatibilité (on ne retourne plus un booléen mais un degré d'incompatiblité via des malus)
     * @param guest L'adolescent visiteur
     * @see compatibleWithGuest
    */
    public int calculateCompatibilityV2(Teenager guest, List<Arete<Teenager>> historiqueAffectations) {
        int malusCompatibility = 0;

        // ----------------------
        // Contrainte sur l'allergie
        Criterion hostCrit = this.requirements.get("HOST_HAS_ANIMAL");
        Criterion guestCrit = guest.requirements.get("GUEST_ANIMAL_ALLERGY");

        if(hostCrit != null && guestCrit != null)
            if(hostCrit.getValue().equals("yes") && guestCrit.getValue().equals("yes"))
                malusCompatibility += AffectationUtil.COMPATIBILITY_MALUS;
        
        // ----------------------
        // Contrainte sur le régime alimentaire
        hostCrit = this.requirements.get("HOST_FOOD");
        guestCrit = guest.requirements.get("GUEST_FOOD");

        if(hostCrit != null && guestCrit != null)
            if(!Arrays.asList(hostCrit.toArray()).containsAll(Arrays.asList(guestCrit.toArray())))
                malusCompatibility += AffectationUtil.calculate(hostCrit.toArray(), guestCrit.toArray(), AffectationUtil.COMPATIBILITY_MALUS);

        // ----------------------
        // Contrainte sur certains pays (ex: France)
        hostCrit = this.requirements.get("HOBBIES");
        guestCrit = guest.requirements.get("HOBBIES");

        if(hostCrit != null && guestCrit != null)
            if((!AffectationUtil.containsAny(hostCrit.toArray(), guestCrit.toArray())) && (this.country == Country.FRANCE || guest.country == Country.FRANCE))
                malusCompatibility += AffectationUtil.COMPATIBILITY_MALUS;

        // ----------------------
        // Contrainte sur l'historique
        malusCompatibility += AffectationUtil.calculateHistoryCriterion(this, guest, historiqueAffectations, AffectationUtil.CONSTRAINT_TYPE);

        return malusCompatibility;
    }

    /**
     * (V1 Graphes) Vérifie qu'un adolescent visiteur est compatible avec un adolescent hôte
     * @param guest L'adolescent visiteur
    */
    public boolean compatibleWithGuest(Teenager guest) {
        // Le critère HISTORY n'est pas implementé au sein de cette méthode

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
            } catch(CriterionTypeException e) {
                it.remove();
            }
        }
    }

    // Sérialisation binaire personalisée
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        this.id = (int)ois.readObject();
        this.forename = (String)ois.readObject();
        this.name = (String)ois.readObject();
        this.birthDate = (LocalDate)ois.readObject();
        this.country = (Country)ois.readObject();
        this.requirements = (Map<String, Criterion>)ois.readObject();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(this.id);
        oos.writeObject(this.forename);
        oos.writeObject(this.name);
        oos.writeObject(this.birthDate);
        oos.writeObject(this.country);
        oos.writeObject(this.requirements);
    }

    /**
     * Vérifie l'égalité entre 2 adolescents
     * @param o Un objet
     */
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        else if(o == this)
            return true;
        else if(this.getClass() == o.getClass()) {
            Teenager t = (Teenager)o;
            // FORENAME / NAME ne peuvent pas être nuls suivant la lecture du fichier CSV
            return this.forename.equals(t.forename) && this.name.equals(t.name);
        } else
            return false;
    }

    /**
     * Retourne une représentation en chaîne de caractères de l'objet
    */
    @Override
    public String toString() {
        return this.forename + " " + this.name;
    }
    
}