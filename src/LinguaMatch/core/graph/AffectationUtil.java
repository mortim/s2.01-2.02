package LinguaMatch.core.graph;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import LinguaMatch.core.Teenager;
import fr.ulille.but.sae2_02.graphes.Arete;
import LinguaMatch.core.Country;
import LinguaMatch.core.Criterion;

/**
 * Ensemble de méthodes utiles pour l'affectation entre adolescents
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class AffectationUtil {
    // Pour la V1 Graphes
    public static int PROHIBITIVE_CONSTRAINT = 50;
    // ---
    public static int BASE = 50;
    public static int AFFINITY_BONUS = 10;
    public static int STRONG_CONSTRAINT_BONUS = 25;
    public static int COMPATIBILITY_MALUS = 20;
    // Les types de critères (utilisés pour la méthode calculateHistoryCriterion)
    public static final int CONSTRAINT_TYPE = 1;
    public static final int PREFERENCE_TYPE = 2;

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

    /** 
    * Calcule le total des bonus/malus d'affinité/contrainte
    * @param host Valeurs de l'hôte
    * @param guest Valeurs du visiteur
    * @param bonusOrMalus Bonus/malus selon si c'est (respectivement) une affinité ou une contrainte
    */
    public static int calculate(String[] host, String[] guest, int bonusOrMalus) {
        int n = 0;
        for(String valeur : guest)
            if(Arrays.asList(host).contains(valeur))
                n += bonusOrMalus;
        return n;
    }

    /** 
    * Calcule le total de bonus/malus pour la contrainte "HISTORY" plus spécifique car basé sur un historique d'affectations
    * @param host L'adolescent hôte
    * @param visitor L'adolescent visiteur
    * @param historiqueAffectations L'historique d'affectations
    * @param type Le type de critère (les contraintes différent selon si c'est une contrainte rédhibitoire, forte ou une préférence, affinité)
    * @see CONSTRAINT_TYPE
    * @see PREFERENCE_TYPE
    */
    public static int calculateHistoryCriterion(Teenager host, Teenager visitor, List<Arete<Teenager>> historiqueAffectations, int type) {
        int weight = 0;
        Criterion hostCrit, visitorCrit;
        String value = type == AffectationUtil.PREFERENCE_TYPE ? "same" : "other";
        
        for(Arete<Teenager> ados : historiqueAffectations) {
            // Si les 2 ados ont été correspondant l'an dernier
            if(ados.getExtremite1().equals(host) && ados.getExtremite2().equals(visitor)) {
                hostCrit = host.getRequirements().get("HISTORY");
                visitorCrit = visitor.getRequirements().get("HISTORY");

                // Vérifier si l'un des 2 a exprimé "other" ou "same" (selon le type de critère)
                if(hostCrit != null && visitorCrit != null) {
                    if(
                        (hostCrit.getValue().equals(value) && visitorCrit.getValue().equals("")) ||
                        (visitorCrit.getValue().equals(value) && hostCrit.getValue().equals(""))
                    )
                        weight += type == AffectationUtil.PREFERENCE_TYPE ? AffectationUtil.AFFINITY_BONUS : AffectationUtil.COMPATIBILITY_MALUS;

                    // Vérifier la contrainte forte suivante : Les 2 adolescents ont exprimé "same"
                    if(type == AffectationUtil.CONSTRAINT_TYPE) {
                        if(hostCrit.getValue().equals("same") && visitorCrit.getValue().equals("same"))
                            // C'est un bonus si la contrainte est respectée
                            weight -= AffectationUtil.STRONG_CONSTRAINT_BONUS;
                    }
                }
                return weight;
            }
        }
        return weight;
    }

    /**
     * Retourne toutes les paires possibles de pays (hôte-visiteur)
     * @param countries Tous les pays (distincts) présents dans la liste
    */
    public static List<String> getAllCountriesPeers(List<String> countries) {
        List<String> peers = new ArrayList<>();

        for(String country1 : countries) {
            for(String country2 : countries) {
                if(!country1.equals(country2))
                    peers.add(country1 + " - " + country2);
            }
        }

        return peers;
    }

    // Inverser les bonus en malus et les malus en bonus pour la méthode weightAdvanced
    private static void changeBonusToMalus() {
        AffectationUtil.AFFINITY_BONUS = -AffectationUtil.AFFINITY_BONUS;
        AffectationUtil.STRONG_CONSTRAINT_BONUS = -AffectationUtil.STRONG_CONSTRAINT_BONUS;
        AffectationUtil.COMPATIBILITY_MALUS = -AffectationUtil.COMPATIBILITY_MALUS;
    }

    /** Ancienne version de weight (sans l'argument de l'historique d'affectations), utilisé pour la rétro-compatiblité des anciens tests (V1 Graphes)
    * Peut avoir d’autres paramètres si nécessaire
    * @param host L'adolescent hôte
    * @param visitor L'adolescent visiteur
    * @see weight C'est une version évolué de la méthode weight (en considérant d'autres critères et un système de malus pour les les incompatibilités) 
    */
    public static double weight(Teenager host, Teenager visitor) {
        return AffectationUtil.weight(host, visitor, null);
    }

    /**
     * Version alternative de weight où les incompatibilités sont considérés comme des malus et les affinités comme des bonus
     * @param host L'adolescent hôte
     * @param visitor L'adolescent visiteur
     * @param historiqueAffectations L'historique d'affectation
     */
    public static double weightAdvanced(Teenager host, Teenager visitor, List<Arete<Teenager>> historiqueAffectations) {
        double weight = 0;
        AffectationUtil.changeBonusToMalus();
        weight = AffectationUtil.weight(host, visitor, historiqueAffectations);
        AffectationUtil.changeBonusToMalus();
        return weight;
    }

    /**
     * Ajoute tous les ados selon s'il est hôte ou visiteur
     * @param a L'affectation
     * @param t La liste des adolescents
     * @param countries Les 2 pays (hôte, visiteur)
    */
    public static void ajouterAdolescents(Affectation a, List<Teenager> t, Country... countries) {
        for(Teenager t1 : t) {
            if(t1.getCountry() == countries[0])
                a.ajouterAdolescent(t1, SubsetGraph.GAUCHE);
            else
                a.ajouterAdolescent(t1, SubsetGraph.DROITE);
        }
    }

    /**
     * Ajoute une arête pour un ado du membre de gauche sur tous les adolescents du membre de droite
     * @param a L'affectation
     * @param t La liste des adolescents
     * @param historiqueAffectation L'historique d'affectations
     * @param countries Les 2 pays (hôte, visiteur)
    */
    public static void ajouterAretesAdolescents(Affectation a, List<Teenager> t, List<Arete<Teenager>> historiqueAffectation, Country... countries) {
        for(Teenager t1 : t) {
            if(t1.getCountry() == countries[0]) {
                for(Teenager t2 : t) {
                    if(t2.getCountry() == countries[1]) {
                        a.ajouterCoupleHoteVisiteur(t1, t2, AffectationUtil.weight(t1, t2, historiqueAffectation));
                    }
                }
            }
        }
    }

    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle
    * @param host L'adolescent hôte
    * @param visitor L'adolescent visiteur
    * @param historiqueAffectations L'historique des affectations
    */
    public static double weight(Teenager host, Teenager visitor, List<Arete<Teenager>> historiqueAffectations) {
        Criterion hostCrit, visitorCrit;
        double weight = AffectationUtil.BASE;

        // Rétro-compatibilité avec la V1 Graphes
        if(historiqueAffectations == null) {
            if(!host.compatibleWithGuest(visitor))
                return weight + AffectationUtil.PROHIBITIVE_CONSTRAINT;
        } else {
            weight += host.calculateCompatibilityV2(visitor, historiqueAffectations);
        }

        // Calcul d'affinités entre adolescents
        // -----
        // Préférence n°1 : Les hobbies
        hostCrit = host.getRequirements().get("HOBBIES");
        visitorCrit = visitor.getRequirements().get("HOBBIES");

        if(hostCrit != null && visitorCrit != null)
            weight -= AffectationUtil.calculate(visitorCrit.toArray(), hostCrit.toArray(), AffectationUtil.AFFINITY_BONUS);

        // -----
        // Préférence n°2 : Genre
        hostCrit = host.getRequirements().get("GENDER");
        visitorCrit = visitor.getRequirements().get("PAIR_GENDER");

        if(hostCrit != null && visitorCrit != null)
            weight -= AffectationUtil.calculate(hostCrit.toArray(), visitorCrit.toArray(), AffectationUtil.AFFINITY_BONUS);

        // -----
        // Préférence n°2 : Différence d'âge
        if(Math.abs(host.getBirthDate().until(visitor.getBirthDate(), ChronoUnit.MONTHS)) < 18)
            weight -= AffectationUtil.AFFINITY_BONUS;

        // -----
        // Préférence n°3 : Historique
        
        // On vérifie si historiqueAffectation est nul, car on passe null en argument pour la retro-compatibilité des tests de AffectationVersion1Test car l'historique n'est pas encore implementé
        if(historiqueAffectations != null)
            weight -= AffectationUtil.calculateHistoryCriterion(host, visitor, historiqueAffectations, AffectationUtil.PREFERENCE_TYPE);
        
        return weight;
	}
}