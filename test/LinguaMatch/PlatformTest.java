package test.LinguaMatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;
import LinguaMatch.*;

public class PlatformTest {
    Teenager t1, t2, t3;
    Map<String,Criterion> h1, h2, h3;
    Platform p;

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
            new Criterion("2005-07-25", CriterionName.BIRTH_DATE)
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
            new Criterion("2005-11-15", CriterionName.BIRTH_DATE)
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
            new Criterion("yes", CriterionName.HOBBIES)
        );

        this.h3.put(
            "HISTORY",
            new Criterion("123", CriterionName.HISTORY)
        );

        this.t3.setRequirements(this.h3);

        this.p = new Platform();
    }

    @Test
    void testAddTeenager() {
        assertEquals(0, this.p.getTeenagers().size());
        this.p.addTeenager(t1);
        this.p.addTeenager(t2);
        this.p.addTeenager(t3);
        assertEquals(3, this.p.getTeenagers().size());
    }

    @Test
    void testFilterTeenagers() {
        // Le critère HOST_HAS_ANIMAL de t1 a comme valeur "&"
        this.p.addTeenager(t1);
        this.p.addTeenager(t2);
        // Les critères GUEST_ANIMAL_ALLERGY HOST_HAS_ANIMAL sont tous les deux à "yes"
        this.p.addTeenager(t3);
        this.p.filterTeenagers();
        assertEquals(1, this.p.getTeenagers().size());
    }

    @Test
    void testPurgeInvalidRequirement() {
        this.p.addTeenager(t1);
        this.p.addTeenager(t2);
        this.p.addTeenager(t3);

        assertEquals(5, this.p.getTeenagers().get(0).getRequirements().size());
        assertEquals(5, this.p.getTeenagers().get(1).getRequirements().size());
        assertEquals(5, this.p.getTeenagers().get(2).getRequirements().size());

        this.p.purgeInvalidRequirement();

        // Incohérence de type sur le critère HOST_HAS_ANIMAL
        assertEquals(4, this.p.getTeenagers().get(0).getRequirements().size());
        assertEquals(5, this.p.getTeenagers().get(1).getRequirements().size());
        // Incohérence de type sur les critères HOBBIES, HISTORY
        assertEquals(3, this.p.getTeenagers().get(2).getRequirements().size());
    }
}
