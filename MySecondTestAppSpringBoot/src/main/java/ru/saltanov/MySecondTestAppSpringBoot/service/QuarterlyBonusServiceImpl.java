package ru.saltanov.MySecondTestAppSpringBoot.service;

import ru.saltanov.MySecondTestAppSpringBoot.model.Positions;

public class QuarterlyBonusServiceImpl implements QuarterlyBonusService {
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        if (!positions.isManager()) {
            throw new IllegalArgumentException("Quarterly bonus cannot be calculated for no manager position");
        }

        return salary * bonus * positions.getPositionCoefficient() / workDays;
    }
}
