package ru.saltanov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.saltanov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.saltanov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

import java.util.Objects;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException {
        if (Objects.equals("123", bindingResult.getRawFieldValue("uid"))) {
            log.error("UnsupportedCodeException! bindingResult.uid = {}", bindingResult.getRawFieldValue("uid"));
            throw new UnsupportedCodeException("UnsupportedCodeException");
        }

        if (bindingResult.hasErrors()) {
            log.error("ValidationFailedException! bindingResult has errors: {}", bindingResult.getAllErrors());
            throw new
                    ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }
}
