package LinguaMatch;

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

    public void filterTeenagers() {
        Teenager next;
        Iterator<Teenager> it = this.teenagers.iterator();
        while(it.hasNext()) {
            next = it.next();
            if(next.hasInconsistencyCriterions())
                it.remove();
        }
    }

    /**
     * Surppime les critères invalides de tous les adolescents (types incorrects)
     * @see Teenager#purgeInvalidRequirement()
    */
    public void purgeInvalidRequirement() {
        for(Teenager t : this.teenagers)
            t.purgeInvalidRequirement();
    }

}