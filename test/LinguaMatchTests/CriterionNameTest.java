package LinguaMatchTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import LinguaMatch.core.CriterionName;

public class CriterionNameTest {
    public CriterionName criterionName1, criterionName2, criterionName3, criterionName4;

    @BeforeEach
    void initialization() {
        // On teste avec les valeurs de l'enum ayant un type différent à chaque fois
        this.criterionName1 = CriterionName.GUEST_ANIMAL_ALLERGY;
        this.criterionName2 = CriterionName.GUEST_FOOD;
    }

    @Test
    void testGetType() {
        assertEquals('B', this.criterionName1.getType());
        assertEquals('T', this.criterionName2.getType());
    }
}