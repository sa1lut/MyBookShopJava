package ru.saltanov.MySecondTestAppSpringBoot.model;

import lombok.Getter;

@Getter
public enum Positions {
    PO(3.2, false),
    HR(1.2, false),
    TPM(2.6, true),
    TEST(2.0, false),
    CTO(3, true);

    private final double positionCoefficient;

    private final boolean isManager;

    Positions(double positionCoefficient, boolean isManager) {
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }
}
