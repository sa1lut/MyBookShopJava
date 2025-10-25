package ru.saltanov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.saltanov.MySecondTestAppSpringBoot.model.Positions;

import static org.junit.jupiter.api.Assertions.*;

class AnnualBonusServiceImplTest {

    @Test
    void calculateNotLeapYear() {
        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        //when
        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays, 2025);

        //expected
        double expected = 360493.8271604938;
        assertEquals(expected, result);
    }

    @Test
    void calculateLeapYear() {
        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        //when
        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays, 2024);

        //expected
        double expected = 361481.48148148148;
        assertEquals(expected, result);
    }
}