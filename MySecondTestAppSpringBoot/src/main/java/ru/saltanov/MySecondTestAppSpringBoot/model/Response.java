package ru.saltanov.MySecondTestAppSpringBoot.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Response {

     private String uid;
     private String operationUid;
     private String systemTime;
     private String code;
     private String errorCode;
     private String errorMessage;

}
