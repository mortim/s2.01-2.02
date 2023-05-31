package LinguaMatch.core.graph;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import LinguaMatch.core.Teenager;
import fr.ulille.but.sae2_02.graphes.Arete;
import LinguaMatch.core.Criterion;

/**
 * Ensemble de méthodes utiles pour l'affectation entre adolescents
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
 */
public class AffectationUtil {
    private static int BASE = 50;
    private static int PROHIBITIVE_CONSTRAINT = 50;
    private static int AFFINITY_BONUS = 10;
    // private static int STRONG_CONSTRAINT_BONUS = 25;

    /** 
    * Vérifie qu'un tableau de chaîne de caractères contient au moins l'un des élements du deuxième tableau de chaîne de caractères
    * @param tab1 Le tableau 1
    * @param tab2 Le tableau 2
    */
    public static boolean containsAny(String[] tab1, String[] tab2) {
        List<String> list1 = Arrays.asList(tab1);
        for(String valeur : tab2)
            if(list1.contains(valeur))
                return true;
        return false;
    }

    // Calcule le total des bonus d'affinité
    private static int calculateAffinity(String[] host, String[] guest) {
        int n = 0;
        for(String valeur : guest)
            if(Arrays.asList(host).contains(valeur))
                n += AffectationUtil.AFFINITY_BONUS;
        return n;
    }

    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager
    * Peut avoir d’autres paramètres si nécessaire
    * @param host L'adolescent hôte
    * @param visitor L'adolescent visiteur
    */
    public static double weight(Teenager host, Teenager visitor, List<Arete<Teenager>> historiqueAffectations) {
        Criterion hostCrit, visitorCrit;
        double weight = AffectationUtil.BASE;
        
        if(!host.compatibleWithGuest(visitor))
            return weight + AffectationUtil.PROHIBITIVE_CONSTRAINT;
       
        // Calcul d'affinités entre adolescents
        // -----
        // Préférence n°1 : Les hobbies
        hostCrit = host.getRequirements().get("HOBBIES");
        visitorCrit = visitor.getRequirements().get("HOBBIES");

        if(hostCrit != null && visitorCrit != null)
            weight -= AffectationUtil.calculateAffinity(visitorCrit.toArray(), hostCrit.toArray());

        // -----
        // Préférence n°2 : Genre
        hostCrit = host.getRequirements().get("GENDER");
        visitorCrit = visitor.getRequirements().get("PAIR_GENDER");

        if(hostCrit != null && visitorCrit != null)
            weight -= AffectationUtil.calculateAffinity(hostCrit.toArray(), visitorCrit.toArray());

        // -----
        // Préférence n°2 : Différence d'âge
        if(Math.abs(host.getBirthDate().until(visitor.getBirthDate(), ChronoUnit.MONTHS)) < 18)
            weight -= AffectationUtil.AFFINITY_BONUS;

        // -----
        // Préférence n°3 : Historique
        
        // On vérifie dans l'historique si l'hôte était bien l'hôte du visiteur auparavant
        if(historiqueAffectations != null) {
            for(Arete<Teenager> ados : historiqueAffectations) {
                if(ados.getExtremite1().equals(host) && ados.getExtremite2().equals(visitor)) {
                    hostCrit = host.getRequirements().get("HISTORY");
                    visitorCrit = visitor.getRequirements().get("HISTORY");
    
                    if(hostCrit != null && visitorCrit != null) {
                        if(
                            (hostCrit.getValue().equals("same") && visitorCrit.getValue().equals("")) ||
                            (visitorCrit.getValue().equals("same") && hostCrit.getValue().equals(""))
                        )
                            weight -= AffectationUtil.AFFINITY_BONUS;
                    }
                }
            }
        }
        
        return weight;
	}

}