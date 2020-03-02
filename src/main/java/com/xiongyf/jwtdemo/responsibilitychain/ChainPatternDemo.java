package com.xiongyf.jwtdemo.responsibilitychain;


import com.xiongyf.jwtdemo.responsibilitychain.logImpl.ConsoleLogger;
import com.xiongyf.jwtdemo.responsibilitychain.logImpl.ErrorLogger;
import com.xiongyf.jwtdemo.responsibilitychain.logImpl.FileLogger;

public abstract class ChainPatternDemo {
    private static AbstractLogger getChainOfLoggers() {

        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        return errorLogger;
    }

    public static void main(String[] args) {
        AbstractLogger loggerChain = getChainOfLoggers();

        loggerChain.logMessage(AbstractLogger.INFO, "Standard information.");

        loggerChain.logMessage(AbstractLogger.DEBUG,
                " debug information.");

        loggerChain.logMessage(AbstractLogger.ERROR,
                " error information.");
    }
}