package ru.saltanov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.saltanov.MySecondTestAppSpringBoot.model.Positions;

@Service
public interface QuarterlyBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays);
}
