package org.slf4j.impl; /**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */


import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;


/**
 *
 * The binding of {@link LoggerFactory} class with an actual instance of
 * {@link ILoggerFactory} is performed using information returned by this class.
 *
 * @author Ceki G&uuml;lc&uuml;</a>
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

    private static StaticLoggerBinder staticLoggerBinder;

    private static LoggerContext defaultLoggerContext = new LoggerContext();

    @Override
    public ILoggerFactory getLoggerFactory() {
        return defaultLoggerContext;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return null;
    }

    public static StaticLoggerBinder getSingleton(){
        if(staticLoggerBinder==null){
            staticLoggerBinder = new StaticLoggerBinder();
        }
        return staticLoggerBinder;
    }
}
