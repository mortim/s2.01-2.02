package test.LinguaMatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;
import LinguaMatch.*;

public class TeenagerTest {
    Teenager t1, t2, t3;
    Map<String,Criterion> h1, h2, h3;

    @BeforeEach
    void initialization() {
        this.t1 = new Teenager("Ryan R. Miller", Country.ITALY);
        this.h1 = new HashMap<>();

        this.h1.put(
            "BIRTH_DATE",
            new Criterion("2005-12-18", CriterionName.BIRTH_DATE)
        );

        this.h1.put(
            "GUEST_ANIMAL_ALLERGY",
            new Criterion("yes", CriterionName.GUEST_ANIMAL_ALLERGY)
        );

        this.h1.put(
            "HOST_HAS_ANIMAL",
            new Criterion("&", CriterionName.HOST_HAS_ANIMAL)
        );

        this.h1.put(
            "HOBBIES",
            new Criterion("reading, science", CriterionName.HOBBIES)
        );

        this.h1.put(
            "HISTORY",
            new Criterion("same", CriterionName.HISTORY)
        );

        this.t1.setRequirements(this.h1);

        this.t2 = new Teenager("Jerome A. Rodriquez", Country.ITALY);
        this.h2 = new HashMap<>();

        this.h2.put(
            "BIRTH_DATE",
            new Criterion("2005-7-25", CriterionName.BIRTH_DATE)
        );

        this.h2.put(
            "GUEST_ANIMAL_ALLERGY",
            new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY)
        );

        this.h2.put(
            "HOST_HAS_ANIMAL",
            new Criterion("no", CriterionName.HOST_HAS_ANIMAL)
        );

        this.h2.put(
            "HOBBIES",
            new Criterion("sports", CriterionName.HOBBIES)
        );

        this.h2.put(
            "HISTORY",
            new Criterion("same", CriterionName.HISTORY)
        );
    
        this.t2.setRequirements(this.h2);

        this.t3 = new Teenager("Patricia B. Truitt", Country.ITALY);
        this.h3 = new HashMap<>();

        this.h3.put(
            "BIRTH_DATE",
            new Criterion("2005-13-15", CriterionName.BIRTH_DATE)
        );

        this.h3.put(
            "GUEST_ANIMAL_ALLERGY",
            new Criterion("yes", CriterionName.GUEST_ANIMAL_ALLERGY)
        );

        this.h3.put(
            "HOST_HAS_ANIMAL",
            new Criterion("yes", CriterionName.HOST_HAS_ANIMAL)
        );

        this.h3.put(
            "HOBBIES",
            new Criterion("sports", CriterionName.HOBBIES)
        );

        this.h3.put(
            "HISTORY",
            new Criterion("other", CriterionName.HISTORY)
        );

        this.t3.setRequirements(this.h3);
    }

    @Test
    void testAddCriterion() {
        Teenager teenagerTmp = new Teenager("Ryan R. Miller", Country.ITALY);
        // On copie temporairement le map de t1 pour ce test
        Map<String,Criterion> mapTmp = new HashMap<>(this.h1);
        teenagerTmp.setRequirements(mapTmp);
        teenagerTmp.addCriterion("GENDER", new Criterion("M", CriterionName.GENDER));
        
        this.h1.put("GENDER", new Criterion("M", CriterionName.GENDER));
        this.t1.setRequirements(this.h1);
        
        // La méthode equals est implementé au sein de la classe Criterion pour qu'il l'invoque et qu'il vérifie l'égalité des valeurs entre ces 2 tables associatives
        assertTrue(teenagerTmp.getRequirements().equals(this.t1.getRequirements()));
    }

    @Test
    void testGetNbMismatch() {
        assertEquals(1, this.t1.getNbMismatch());
        assertEquals(0, this.t2.getNbMismatch());
        assertEquals(1, this.t3.getNbMismatch());
    }

    @Test
    void testCompatibleWithGuest() {
        assertTrue(this.t1.compatibleWithGuest(this.t2));
        // t1 est allergique aux animaux mais t2 a un animal allèrgene chez lui
        assertFalse(this.t1.compatibleWithGuest(this.t3));
    }

    @Test
    void purgeInvalidRequirement() {
        Teenager teenagerTmp = new Teenager("Ryan R. Miller", Country.ITALY);
        Map<String,Criterion> mapTmp = new HashMap<>(this.h1);
        teenagerTmp.setRequirements(mapTmp);
        teenagerTmp.removeRequirement("HOST_HAS_ANIMAL");

        this.t1.purgeInvalidRequirement();

        assertTrue(this.t1.getRequirements().equals(teenagerTmp.getRequirements()));
    }

}
