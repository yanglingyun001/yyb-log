package org.slf4j.impl;

import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements org.slf4j.Logger, LocationAwareLogger, Serializable {

    private String className;

    private static SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Logger(String className){
        this.className = className;
    }

    @Override
    public void log(Marker marker, String fqcn, int level, String message, Object[] argArray, Throwable t) {
        if (level>=20){
            LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(string_format(message+LogUtil.getExceptionStackTraceToString(t),argArray)));
            LogUtil.msg(log);
        }
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void trace(String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void trace(String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void trace(String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(string_format(format,argArray)));
        LogUtil.msg(log);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.trace, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void debug(String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    private String string_format(String format, Object... arguments){
        format = format.replaceAll("\\{\\}","%s");
        return String.format(format,arguments);
    }

    @Override
    public void debug(String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void debug(String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.debug, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    public  String getBaseInfo(String msg){
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        String formatMsg = string_format("[%s][%s@%s]%s", sdf_datetime.format(new Date()),
                className,
                stacktrace[4].getLineNumber(), msg);
        return  formatMsg;
    }

    @Override
    public void info(String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void info(String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void info(String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void info(String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public void info(Marker marker, String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.info, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void warn(String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void warn(String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void warn(String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return false;
    }

    @Override
    public void warn(Marker marker, String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.warn, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void error(String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void error(String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void error(String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override
    public void error(Marker marker, String msg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(msg));
        LogUtil.msg(log);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(string_format(format,arg)));
        LogUtil.msg(log);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(string_format(format,arg1,arg2)));
        LogUtil.msg(log);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(string_format(format,arguments)));
        LogUtil.msg(log);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        LogUtil.Log log = new LogUtil.Log(LogUtil.Log.error, getBaseInfo(msg+LogUtil.getExceptionStackTraceToString(t)));
        LogUtil.msg(log);
    }
}
