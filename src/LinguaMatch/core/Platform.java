package LinguaMatch.core;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Représente la plateforme d'affectation de tous les adolescents
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class Platform {
    private ArrayList<Teenager> teenagers;

    /**
     * Constructeur qui définit une liste d'adolescents vide
    */
    public Platform() {
        this.teenagers = new ArrayList<>();
    }

    /**
     * Retourne la liste des adolescents
    */
    public ArrayList<Teenager> getTeenagers() {
        return this.teenagers;
    }

    /**
     * Ajoute un adolescent dans la liste des adolescents
     * @param t Un adolescent
    */
    public void addTeenager(Teenager t) {
        this.teenagers.add(t);
    }

    /**
     * Filtre les adolescents (en les supprimant) ayant des types/valeurs de critères incohérents (suivant les indications de la méthode hasInconsistencyCriterions)
     * @see Teenager#hasInconsistencyCriterions()
    */
    public void filterTeenagers() {
        Teenager next;
        Iterator<Teenager> it = this.teenagers.iterator();
        while(it.hasNext()) {
            next = it.next();
            try {
                next.hasInconsistencyCriterions();
            } catch(CriterionTypeException e) {
                System.out.println(e.getMessage());
                it.remove();
            }
        }
    }

    /**
     * Supprime les critères invalides de tous les adolescents (types incorrects)
     * @see Teenager#purgeInvalidRequirement()
    */
    public void purgeInvalidRequirement() {
        for(Teenager t : this.teenagers)
            t.purgeInvalidRequirement();
    }
}