package com.xiongyf.jwtdemo.responsibilitychain.logImpl;

import com.xiongyf.jwtdemo.responsibilitychain.AbstractLogger;

public class FileLogger extends AbstractLogger {
    public FileLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("File Console::Logger: " + message);
    }
}
