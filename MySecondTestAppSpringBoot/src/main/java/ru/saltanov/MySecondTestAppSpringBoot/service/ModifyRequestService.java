package ru.saltanov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.saltanov.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}