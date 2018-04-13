package com.smartvid.directory.exceptions;

public class DirNotFoundException extends RuntimeException {
    public DirNotFoundException(String dir) {
        super("Could not find directory: '"+ dir+"'");
    }
}
