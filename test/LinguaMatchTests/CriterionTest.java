package LinguaMatchTests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import LinguaMatch.core.Criterion;
import LinguaMatch.core.CriterionName;
import LinguaMatch.core.CriterionTypeException;

public class CriterionTest {
    public Criterion criterion, criterion2, criterion3, criterion4, criterion5, criterion6;

    @BeforeEach
    void initialization() {
        this.criterion = new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "yes");
        this.criterion2 = new Criterion(CriterionName.HOST_HAS_ANIMAL, "&");
        this.criterion3 = new Criterion(CriterionName.HOBBIES, "science,technology");
    }

    @Test
    void testTypeInfer() {
        assertEquals('B', this.criterion.infer());
        assertEquals('T', this.criterion2.infer());
        assertEquals('T', this.criterion3.infer());
    }

    @Test
    void testIsValid() {
        Exception e = assertThrows(CriterionTypeException.class, () -> {
            assertTrue(this.criterion.isValid());
            assertTrue(this.criterion3.isValid());
            this.criterion2.isValid();
        });

        assertEquals(
            e.getMessage(),
            "La valeur '" + this.criterion2.getValue() + "' est de type '" + CriterionName.getFullNameType(this.criterion2.infer()) + "' mais le type attendu est de type '" + CriterionName.getFullNameType(this.criterion2.getLabel().getType()) + "'"
        );
    }
}
