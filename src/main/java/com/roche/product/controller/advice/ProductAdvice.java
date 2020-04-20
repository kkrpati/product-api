package com.roche.product.controller.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.roche.product.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductAdvice {

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message processNullPointerException(NullPointerException exception) {

        Message message = new Message();
        message.setMessage("Errors found in request, try again later");
        message.setType("ERROR");
        return message;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message processNullPointerException(IllegalArgumentException exception) {

        Message message = new Message();
        message.setMessage("Provided product is not present, please check");
        message.setType("ERROR");
        return message;
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message processInvalidFormatException(InvalidFormatException exception) {
        Message message = new Message();
        message.setMessage("Probably the price or the date entered is wrong, please check");
        message.setType("ERROR");
        return message;
    }

}
