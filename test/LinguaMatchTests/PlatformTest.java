package LinguaMatchTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.time.LocalDate;
import LinguaMatch.core.Country;
import LinguaMatch.core.Criterion;
import LinguaMatch.core.CriterionName;
import LinguaMatch.core.Platform;
import LinguaMatch.core.Teenager;

public class PlatformTest {
    Teenager t1, t2, t3;
    Map<String,Criterion> h1, h2, h3;
    Platform platform;

    @BeforeEach
    void initialization() {
        this.t1 = new Teenager("Jerome", "A. Rodriquez", LocalDate.of(2005,12,15), Country.FRANCE);
    
        this.t1.addCriterion(new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "yes"));
        this.t1.addCriterion(new Criterion(CriterionName.HOST_HAS_ANIMAL, "&"));
        this.t1.addCriterion(new Criterion(CriterionName.HOBBIES, "reading,science"));

        // -------------------
        this.t2 = new Teenager("Patricia", "B. Truitt", LocalDate.of(2005,11,14), Country.ITALY);

        this.t2.addCriterion(new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "yes"));
        this.t2.addCriterion(new Criterion(CriterionName.HOST_HAS_ANIMAL, "yes"));
        this.t2.addCriterion(new Criterion(CriterionName.HOBBIES, "culture,sports"));
        this.t2.addCriterion(new Criterion(CriterionName.HOST_FOOD, ""));
        this.t2.addCriterion(new Criterion(CriterionName.GUEST_FOOD, "vegetarian"));

        // -------------------
        this.t3 = new Teenager("Ryan", "R. Muller", LocalDate.of(2005,10,13), Country.GERMANY);
    
        this.t3.addCriterion(new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "&"));
        this.t3.addCriterion(new Criterion(CriterionName.HOST_HAS_ANIMAL, ""));
        this.t3.addCriterion(new Criterion(CriterionName.HOBBIES, "science"));
        this.t3.addCriterion(new Criterion(CriterionName.HOST_FOOD, "vegetarian,nonuts"));
        this.t3.addCriterion(new Criterion(CriterionName.GUEST_FOOD, "nonuts"));

        this.platform = new Platform();
    }

    @Test
    void testAddTeenager() {
        assertEquals(0, this.platform.getTeenagers().size());
        this.platform.addTeenager(t1);
        this.platform.addTeenager(t2);
        this.platform.addTeenager(t3);
        assertEquals(3, this.platform.getTeenagers().size());
    }

    @Test
    void testFilterTeenagers() {
        this.platform.filterTeenagers();
        assertEquals(0, this.platform.getTeenagers().size());
    }

    @Test
    void testPurgeInvalidRequirement() {
        this.platform.addTeenager(t1);
        this.platform.addTeenager(t2);
        this.platform.addTeenager(t3);

        assertEquals(3, this.platform.getTeenagers().get(0).getRequirements().size());
        assertEquals(5, this.platform.getTeenagers().get(1).getRequirements().size());
        assertEquals(5, this.platform.getTeenagers().get(2).getRequirements().size());

        this.platform.purgeInvalidRequirement();

        // Incohérence de type sur le critère HOST_HAS_ANIMAL
        assertEquals(2, this.platform.getTeenagers().get(0).getRequirements().size());
        // Aucune incohérence de type
        assertEquals(5, this.platform.getTeenagers().get(1).getRequirements().size());
        // Incohérence de type sur les critères GUEST_ANIMAL_ALLERGY et HOST_HAS_ANIMAL
        assertEquals(3, this.platform.getTeenagers().get(2).getRequirements().size());
    }
}
