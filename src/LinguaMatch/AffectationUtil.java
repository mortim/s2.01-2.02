package LinguaMatch;

import java.util.Arrays;

/**
 * Ensemble de méthodes utiles pour l'affectation entre adolescents
 * 
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
 */
public class AffectationUtil {
    private static int BASE = 50;
    private static int PROHIBITIVE_CONSTRAINT = 50;
    private static int AFFINITY_BONUS = 10;
    // private static int STRONG_CONSTRAINT_BONUS = 25;

    // Méthode utiles
    // --------------
    // Vérifie qu'un tableau contient au moins l'un des élements du deuxième tableau
    public static boolean containsAny(String[] host, String[] guest) {
        for(String valeur : guest)
            if(Arrays.asList(host).contains(valeur))
                return true;
        return false;
    }

    // Calcule le total des bonus d'affinité
    private static int calculateAffinity(String[] host, String[] guest) {
        int n = 0;
        for(String valeur : guest)
            if(Arrays.asList(host).contains(valeur))
                n += AFFINITY_BONUS;
        return n;
    }

    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle.
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager.
    * Peut avoir d’autres paramètres si nécessaire.
    * @param host L'adolescent hôte
    * @param visitor L'adolescent visiteur
    */
    public static double weight(Teenager host, Teenager visitor) {
        Criterion hostCrit, visitorCrit;
        double weight = AffectationUtil.BASE;
        
        if(!host.compatibleWithGuestGraphesV1(visitor))
            return weight + AffectationUtil.PROHIBITIVE_CONSTRAINT;
       
        // Calcul d'affinités entre adolescents
        // -----
        // Préférence n°1 : Les hobbies
        hostCrit = host.getRequirements().get("HOBBIES");
        visitorCrit = visitor.getRequirements().get("HOBBIES");

        weight -= AffectationUtil.calculateAffinity(visitorCrit.toArray(), hostCrit.toArray());

        return weight;
	}

}