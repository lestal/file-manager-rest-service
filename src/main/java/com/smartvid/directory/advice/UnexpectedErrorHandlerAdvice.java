package com.smartvid.directory.advice;

import com.smartvid.directory.exceptions.UnexpectedErrorException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UnexpectedErrorHandlerAdvice {

    @ResponseBody
    @ExceptionHandler(UnexpectedErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    VndErrors unexpectedError(UnexpectedErrorException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
