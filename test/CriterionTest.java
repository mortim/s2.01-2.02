package test.LinguaMatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import LinguaMatch.Criterion;
import LinguaMatch.CriterionName;

public class CriterionTest {
    public Criterion criterion, criterion2, criterion3, criterion4, criterion5, criterion6;

    @BeforeEach
    void initialization() {
        this.criterion = new Criterion("yes", CriterionName.GUEST_ANIMAL_ALLERGY);
        this.criterion2 = new Criterion("&", CriterionName.HOST_HAS_ANIMAL);
        this.criterion3 = new Criterion("text", CriterionName.AGE_GAP);
        this.criterion4 = new Criterion("science", CriterionName.HOBBIES);
        this.criterion5 = new Criterion("125", CriterionName.AGE_GAP);
        this.criterion6 = new Criterion("2022-12-17", CriterionName.BIRTH_DATE);
    }

    @Test
    void testTypeInfer() {
        assertEquals('B', this.criterion.typeInfer());
        assertEquals('T', this.criterion2.typeInfer());
        assertEquals('T', this.criterion3.typeInfer());
        assertEquals('T', this.criterion4.typeInfer());
        assertEquals('N', this.criterion5.typeInfer());
        assertEquals('D', this.criterion6.typeInfer());
    }

    @Test
    void testIsValid() {
        assertTrue(this.criterion.isValid());
        assertFalse(this.criterion2.isValid());
        assertFalse(this.criterion3.isValid());
        assertTrue(this.criterion4.isValid());
        assertTrue(this.criterion5.isValid());
        assertTrue(this.criterion6.isValid());
    }
}
