package com.smartvid.directory.exceptions;

public class UnexpectedErrorException extends RuntimeException {
    public UnexpectedErrorException(String dir) {
        super("Something went wrong for path:'"+ dir+"'");
    }
}
