package test.LinguaMatch;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import LinguaMatch.AffectationUtil;
import LinguaMatch.Country;
import LinguaMatch.Criterion;
import LinguaMatch.CriterionName;
import LinguaMatch.Teenager;

import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;

public class AffectationVersion1Test {
    Teenager adonia, bellatrix, callista, xolag, yak, zander;
    GrapheNonOrienteValue<Teenager> graphe;
    CalculAffectation<Teenager> affectation;

    @BeforeEach
    void initialization() {
        // Création des adolescents
        adonia = new Teenager("Adonia", "A", LocalDate.of(2005,12,15), Country.FRANCE);

        adonia.addCriterion("GUEST_ANIMAL_ALLERGY", new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "no"));
        adonia.addCriterion("HOBBIES", new Criterion(CriterionName.HOBBIES, "sports,technology"));
        
        // -----------------
        bellatrix = new Teenager("Bellatrix", "B", LocalDate.of(2005,12,15), Country.FRANCE);

        bellatrix.addCriterion("GUEST_ANIMAL_ALLERGY", new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "yes"));
        bellatrix.addCriterion("HOBBIES", new Criterion(CriterionName.HOBBIES, "culture,science"));

        // -----------------
        callista = new Teenager("Callista", "C", LocalDate.of(2005,12,15), Country.FRANCE);
        
        callista.addCriterion("GUEST_ANIMAL_ALLERGY", new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "no"));
        callista.addCriterion("HOBBIES", new Criterion(CriterionName.HOBBIES, "science,reading"));
        
        // -----------------
        xolag = new Teenager("Xolag", "X", LocalDate.of(2005,12,15), Country.ITALY);

        xolag.addCriterion("HOST_HAS_ANIMAL", new Criterion(CriterionName.HOST_HAS_ANIMAL, "no"));
        xolag.addCriterion("HOBBIES", new Criterion(CriterionName.HOBBIES, "culture,technology"));

        // -----------------
        yak = new Teenager("Yak", "Y", LocalDate.of(2005,12,15), Country.ITALY);

        yak.addCriterion("HOST_HAS_ANIMAL", new Criterion(CriterionName.HOST_HAS_ANIMAL, "yes"));
        yak.addCriterion("HOBBIES", new Criterion(CriterionName.HOBBIES, "science,reading"));

        // -----------------
        zander = new Teenager("Zander", "Z", LocalDate.of(2005,12,15), Country.ITALY);

        zander.addCriterion("HOST_HAS_ANIMAL", new Criterion(CriterionName.HOST_HAS_ANIMAL, "no"));
        zander.addCriterion("HOBBIES", new Criterion(CriterionName.HOBBIES, "technology"));

        // Création du graphe non orienté et valué
        graphe = new GrapheNonOrienteValue<>();
        graphe.ajouterSommet(adonia);
        graphe.ajouterSommet(bellatrix);
        graphe.ajouterSommet(callista);
        graphe.ajouterSommet(xolag);
        graphe.ajouterSommet(yak);
        graphe.ajouterSommet(zander);

        // On construit un graphe biparti complet (les sommets en 2 parties et chaque sommet à une arête dans tous les sommets de l'autre partie)
        graphe.ajouterArete(bellatrix, xolag, AffectationUtil.weight(bellatrix, xolag));
        graphe.ajouterArete(bellatrix, zander, AffectationUtil.weight(bellatrix, zander));
        graphe.ajouterArete(bellatrix, yak, AffectationUtil.weight(bellatrix, yak));

        graphe.ajouterArete(adonia, xolag, AffectationUtil.weight(adonia, xolag));
        graphe.ajouterArete(adonia, zander, AffectationUtil.weight(adonia, zander));
        graphe.ajouterArete(adonia, yak, AffectationUtil.weight(adonia, yak));

        graphe.ajouterArete(callista, xolag, AffectationUtil.weight(callista, xolag));
        graphe.ajouterArete(callista, zander, AffectationUtil.weight(callista, zander));
        graphe.ajouterArete(callista, yak, AffectationUtil.weight(callista, yak));

        affectation = new CalculAffectation<>(graphe, List.of(bellatrix, adonia, callista), List.of(xolag, zander, yak));
    }

    @Test
    void testAffectationV1() {
        List<Arete<Teenager>> affectations = affectation.calculerAffectation();

        assertTrue(affectations.contains(graphe.getArete(bellatrix, xolag)));
        assertTrue(affectations.contains(graphe.getArete(adonia, zander)));
        assertTrue(affectations.contains(graphe.getArete(callista, yak)));
    }
}
