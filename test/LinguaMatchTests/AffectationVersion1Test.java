package LinguaMatchTests;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import LinguaMatch.core.graph.AffectationUtil;
import LinguaMatch.core.Country;
import LinguaMatch.core.Criterion;
import LinguaMatch.core.CriterionName;
import LinguaMatch.core.graph.SubsetGraph;
import LinguaMatch.core.Teenager;
import LinguaMatch.core.graph.Affectation;
import fr.ulille.but.sae2_02.graphes.Arete;

public class AffectationVersion1Test {
    Teenager adonia, bellatrix, callista, xolag, yak, zander;
    Affectation affectation;

    @BeforeEach
    void initialization() {
        // Création des adolescents
        adonia = new Teenager("Adonia", "A", LocalDate.of(2005,12,15), Country.FRANCE);

        adonia.addCriterion(new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "no"));
        adonia.addCriterion(new Criterion(CriterionName.HOBBIES, "sports,technology"));
        
        // -----------------
        bellatrix = new Teenager("Bellatrix", "B", LocalDate.of(2005,12,15), Country.FRANCE);

        bellatrix.addCriterion(new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "yes"));
        bellatrix.addCriterion(new Criterion(CriterionName.HOBBIES, "culture,science"));

        // -----------------
        callista = new Teenager("Callista", "C", LocalDate.of(2005,12,15), Country.FRANCE);
        
        callista.addCriterion(new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "no"));
        callista.addCriterion(new Criterion(CriterionName.HOBBIES, "science,reading"));
        
        // -----------------
        xolag = new Teenager("Xolag", "X", LocalDate.of(2005,12,15), Country.ITALY);

        xolag.addCriterion(new Criterion(CriterionName.HOST_HAS_ANIMAL, "no"));
        xolag.addCriterion(new Criterion(CriterionName.HOBBIES, "culture,technology"));

        // -----------------
        yak = new Teenager("Yak", "Y", LocalDate.of(2005,12,15), Country.ITALY);

        yak.addCriterion(new Criterion(CriterionName.HOST_HAS_ANIMAL, "yes"));
        yak.addCriterion(new Criterion(CriterionName.HOBBIES, "science,reading"));

        // -----------------
        zander = new Teenager("Zander", "Z", LocalDate.of(2005,12,15), Country.ITALY);

        zander.addCriterion(new Criterion(CriterionName.HOST_HAS_ANIMAL, "no"));
        zander.addCriterion(new Criterion(CriterionName.HOBBIES, "technology"));

        // Création du graphe non orienté et valué
        affectation = new Affectation();

        affectation.ajouterAdolescent(bellatrix, SubsetGraph.GAUCHE);
        affectation.ajouterAdolescent(adonia, SubsetGraph.GAUCHE);
        affectation.ajouterAdolescent(callista, SubsetGraph.GAUCHE);

        affectation.ajouterAdolescent(xolag, SubsetGraph.DROITE);
        affectation.ajouterAdolescent(zander, SubsetGraph.DROITE);
        affectation.ajouterAdolescent(yak, SubsetGraph.DROITE);
        
        // On construit un graphe biparti complet (les sommets en 2 parties et chaque sommet à une arête dans tous les sommets de l'autre partie)
        affectation.ajouterCoupleHoteVisiteur(bellatrix, xolag, AffectationUtil.weight(bellatrix, xolag));
        affectation.ajouterCoupleHoteVisiteur(bellatrix, zander, AffectationUtil.weight(bellatrix, zander));
        affectation.ajouterCoupleHoteVisiteur(bellatrix, yak, AffectationUtil.weight(bellatrix, yak));

        affectation.ajouterCoupleHoteVisiteur(adonia, xolag, AffectationUtil.weight(adonia, xolag));
        affectation.ajouterCoupleHoteVisiteur(adonia, zander, AffectationUtil.weight(adonia, zander));
        affectation.ajouterCoupleHoteVisiteur(adonia, yak, AffectationUtil.weight(adonia, yak));

        affectation.ajouterCoupleHoteVisiteur(callista, xolag, AffectationUtil.weight(callista, xolag));
        affectation.ajouterCoupleHoteVisiteur(callista, zander, AffectationUtil.weight(callista, zander));
        affectation.ajouterCoupleHoteVisiteur(callista, yak, AffectationUtil.weight(callista, yak));
    }

    @Test
    void testAffectationV1() {
        affectation.calculerAffectation();
        List<Arete<Teenager>> affectations = affectation.getAffectations();

        assertTrue(affectations.contains(affectation.getGrapheBiparti().getArete(bellatrix, xolag)));
        assertTrue(affectations.contains(affectation.getGrapheBiparti().getArete(adonia, zander)));
        assertTrue(affectations.contains(affectation.getGrapheBiparti().getArete(callista, yak)));
    }
}
