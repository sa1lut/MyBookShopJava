package ru.saltanov.MySecondTestAppSpringBoot.service;

import ru.saltanov.MySecondTestAppSpringBoot.model.Positions;

import java.time.Year;

public class AnnualBonusServiceImpl implements AnnualBonusService {
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays, int year) {
        Year yearForCalc = Year.of(year);
        return salary * bonus * yearForCalc.length() * positions.getPositionCoefficient() / workDays;
    }
}
