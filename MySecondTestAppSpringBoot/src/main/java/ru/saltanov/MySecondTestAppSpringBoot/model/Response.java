package ru.saltanov.MySecondTestAppSpringBoot.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Response {

     /* Уникальный идентификатор сообщения */
     private String uid;

     /* Уникальный идентификатор операции */
     private String operationUid;

     /* Время создания сообщения */
     private String systemTime;

     /* Код успеха */
     private Codes code;

     /* Ежегодный бонус */
     private Double annualBonus;

     /* Код ошибки */
     private ErrorCodes errorCode;

     /* Сообщение об ошибке */
     private ErrorMessages errorMessage;

}
