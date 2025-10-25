package ru.saltanov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.saltanov.MySecondTestAppSpringBoot.model.Positions;

import static org.junit.jupiter.api.Assertions.*;

class QuarterlyBonusServiceImplTest {

    @Test
    void calculateNotManager() {
        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        //expected
        assertThrows(IllegalArgumentException.class,
                () -> new QuarterlyBonusServiceImpl().calculate(position, salary, bonus, workDays));
    }

    @Test
    void calculateManager() {
        // given
        Positions position = Positions.TPM;
        double bonus = 2.0;
        int workDays = 60;
        double salary = 100000.00;

        // when
        double result = new QuarterlyBonusServiceImpl().calculate(position, salary, bonus, workDays);

        //expected
        double expected = 8666.666666666666;
        assertEquals(expected, result);
    }
}