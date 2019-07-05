package org.slf4j.impl;

import org.slf4j.ILoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LoggerContext  implements ILoggerFactory {

    private static Map<Class,Logger> loggers = new HashMap<>();

    @Override
    public synchronized Logger getLogger(String name) {
        Logger logger = loggers.get(name);
        if(logger==null){
            logger = new Logger(name);
        }
        return logger;
    }

}
