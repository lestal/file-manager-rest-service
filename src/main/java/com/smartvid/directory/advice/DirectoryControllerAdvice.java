package com.smartvid.directory.advice;

import com.smartvid.directory.exceptions.DirNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DirectoryControllerAdvice {

    @ResponseBody
    @ExceptionHandler(DirNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors userNotFoundExceptionHandler(DirNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}