package test.LinguaMatch;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.time.LocalDate;
import java.util.HashMap;
import LinguaMatch.*;

public class TeenagerTest {
    Teenager t1, t2, t3;
    Map<String,Criterion> h1, h2, h3;

    @BeforeEach
    void initialization() {
        this.t1 = new Teenager("Jerome", "A. Rodriquez", LocalDate.of(2005,12,15), Country.FRANCE);
        // On utilise le hashmap ici et non la méthode addCriterion pour la tester plus tard et être sûr qu'elle fonctionne correctement
        this.h1 = new HashMap<>();

        this.h1.put("GUEST_ANIMAL_ALLERGY", new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "yes"));
        this.h1.put("HOST_HAS_ANIMAL", new Criterion(CriterionName.HOST_HAS_ANIMAL, "&"));
        this.h1.put("HOBBIES", new Criterion(CriterionName.HOBBIES, "reading,science"));

        this.t1.setRequirements(this.h1);

        // -------------------
        this.t2 = new Teenager("Patricia", "B. Truitt", LocalDate.of(2005,11,14), Country.ITALY);
        this.h2 = new HashMap<>();

        this.h2.put("GUEST_ANIMAL_ALLERGY", new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "no"));
        this.h2.put("HOST_HAS_ANIMAL", new Criterion(CriterionName.HOST_HAS_ANIMAL, "no"));
        this.h2.put("HOBBIES", new Criterion(CriterionName.HOBBIES, "culture,sports"));
        this.h2.put("HOST_FOOD", new Criterion(CriterionName.HOST_FOOD, ""));
        this.h2.put("GUEST_FOOD", new Criterion(CriterionName.GUEST_FOOD, "vegetarian"));
        
        this.t2.setRequirements(this.h2);

        // -------------------
        this.t3 = new Teenager("Ryan", "R. Muller", LocalDate.of(2005,10,13), Country.GERMANY);
        this.h3 = new HashMap<>();

        this.h3.put("GUEST_ANIMAL_ALLERGY", new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "yes"));
        this.h3.put("HOST_HAS_ANIMAL", new Criterion(CriterionName.HOST_HAS_ANIMAL, "yes"));
        this.h3.put("HOBBIES", new Criterion(CriterionName.HOBBIES, "science"));
        this.h3.put("HOST_FOOD", new Criterion(CriterionName.HOST_FOOD, "vegetarian,nonuts"));
        this.h3.put("GUEST_FOOD", new Criterion(CriterionName.GUEST_FOOD, "nonuts"));

        this.t3.setRequirements(this.h3);
    }

    @Test
    void testAddCriterion() {
        Teenager teenagerTmp = new Teenager("Ryan", "R. Muller", LocalDate.of(2005, 10, 13), Country.GERMANY);
        // On copie temporairement le map de this.t1 pour ce test
        Map<String,Criterion> mapTmp = new HashMap<>(this.h1);
        mapTmp.put("GENDER", new Criterion(CriterionName.GENDER, "M"));
        teenagerTmp.setRequirements(mapTmp);
        
        this.t1.addCriterion("GENDER", new Criterion(CriterionName.GENDER, "M"));

        // La méthode equals est implementé au sein de la classe Criterion pour qu'il l'invoque et qu'il vérifie l'égalité des valeurs au sein de ces 2 tables associatives
        assertTrue(this.t1.getRequirements().equals(teenagerTmp.getRequirements()));
    }

    @Test
    void testCompatibleWithGuest() {
        // t1 est allergique aux animaux mais t3 a un animal allèrgene chez lui
        assertFalse(this.t3.compatibleWithGuest(this.t1));
        // t2 n'accepte aucun régime alimentaire particulier
        assertFalse(this.t2.compatibleWithGuest(this.t3));
        // t3 accepte au moins "vegetarian" comme régime alimentaire
        assertTrue(this.t3.compatibleWithGuest(this.t2));
        // Compatible car la contrainte "HOST_HAS_ANIMAL" de t1 n'est pas cohérente, elle est ignorée + il existe un hobby en commun entre les 2 (t1 est français)
        assertTrue(this.t1.compatibleWithGuest(this.t3));
        // Aucun hobby en commun entre l'adolescent français et italien
        assertFalse(this.t1.compatibleWithGuest(this.t2));
    }

    @Test
    void purgeInvalidRequirement() {
        Teenager teenagerTmp = new Teenager("Ryan", "R. Muller", LocalDate.of(2005, 10, 13), Country.GERMANY);
        // On copie la hashmap this.h1 dans cette nouvelle hashmap "temporaire" dans le cadre de ce test
        Map<String,Criterion> mapTmp = new HashMap<>(this.h1);
        teenagerTmp.setRequirements(mapTmp);
        teenagerTmp.removeRequirement("HOST_HAS_ANIMAL");

        this.t1.purgeInvalidRequirement();

        assertTrue(this.t1.getRequirements().equals(teenagerTmp.getRequirements()));
    }

}
