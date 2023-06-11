package LinguaMatchTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import LinguaMatch.Platform;
import LinguaMatch.core.Country;
import LinguaMatch.core.Criterion;
import LinguaMatch.core.CriterionName;
import LinguaMatch.core.Teenager;

public class PlatformTest {
    Teenager t1, t2, t3;

    @BeforeEach
    void initialization() {
        List<Teenager> l = new ArrayList<>();

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

        l.add(t1);
        l.add(t2);
        l.add(t3);

        Platform.teenagers = l;
    }

    @Test
    void testFilterTeenagers() {
        Platform.filterTeenagers();
        assertEquals(2, Platform.teenagers.size());
    }

    @Test
    void testPurgeInvalidRequirement() {
        Platform.teenagers.add(t1);
        Platform.teenagers.add(t2);
        Platform.teenagers.add(t3);

        assertEquals(3, Platform.teenagers.get(0).getRequirements().size());
        assertEquals(5, Platform.teenagers.get(1).getRequirements().size());
        assertEquals(5, Platform.teenagers.get(2).getRequirements().size());

        Platform.purgeInvalidRequirement();

        // Incohérence de type sur le critère HOST_HAS_ANIMAL
        assertEquals(2, Platform.teenagers.get(0).getRequirements().size());
        // Aucune incohérence de type
        assertEquals(5, Platform.teenagers.get(1).getRequirements().size());
        // Incohérence de type sur les critères GUEST_ANIMAL_ALLERGY et HOST_HAS_ANIMAL
        assertEquals(3, Platform.teenagers.get(2).getRequirements().size());
    }
}
