package org.slf4j.impl;

import com.haojiao.init.config.Config;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogUtil {

    private static String log_path = File.separator + "mylogs" + File.separator;//日志位置
    private static final long max_file_size = 1L * 1024 * 1024;//日志文件大小
    private static boolean sendToConsole;//是否输出到控制台




    private static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat sdf_time = new SimpleDateFormat("yyyy-MM-dd_HHmmss-SSS");

    private static ThreadLocal<List<Log>> threadLogs = new ThreadLocal<>();
    private static ThreadLocal<Boolean> hasErrorLog = new ThreadLocal<>();
    /**
     * 是否自动flush  如果是null,true为自动 ，如果为false则手动
     */
    public static ThreadLocal<Boolean> autoFlush = new ThreadLocal<>();


    private static OutputStreamWriter infoWriter;
    private static OutputStreamWriter errorWriter;

    static {
        sendToConsole= Config.get("spring.profiles.active").equalsIgnoreCase("dev");
        //可以自己实现，是否打印到控制台的逻辑
    }

    public static class Log {
        public static final int no = 0;//无级别

        public static final int trace = 2;
        public static final int debug = 3;
        public static final int info = 4;
        public static final int warn = 5;
        public static final int error = 6;
        int level;
        String msg;

        public Log(int level, String msg) {
            this.level = level;
            this.msg = msg;
        }


        public int getLevel() {
            return level;
        }

        public String getMsg() {
            return msg;
        }
    }

    private static void check_init() {
        if ( threadLogs.get()==null){
            threadLogs.set(new ArrayList<>());
            hasErrorLog.set(false);
        }

    }


    private static OutputStreamWriter getWriter(String filename) {

        OutputStreamWriter writer = null;

        if ("info".equals(filename)) {
            writer = infoWriter;
        } else {
            writer = errorWriter;
        }
        if (writer != null) {
            return writer;
        }

        String dirPath = log_path;

        String filePath = dirPath + filename + ".log";

        try {
            writer = new OutputStreamWriter(new FileOutputStream(filePath,true), "utf8");
            if ("info".equals(filename)) {
                infoWriter = writer;
            } else {
                errorWriter = writer;
            }

            return writer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static void checkFileSize(String filename) {
        String dirPath = log_path;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean re = dir.mkdirs();
            if (!re) {
                throw new RuntimeException("[ERROR]创建日志文件夹失败");
            }
        }

        String filePath = dirPath + filename + ".log";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean re = file.createNewFile();
                if (!re) {
                    throw new RuntimeException("[ERROR]创建日志文件失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (file.length() > max_file_size) {
                String abpath = file.getCanonicalPath();
                String newfilePath = abpath.substring(0,abpath.lastIndexOf(File.separator));
                String newFileName = newfilePath + File.separator + sdf_date.format(new Date()) + File.separator + filename + "_" + sdf_time.format(new Date()) + ".log";
                File newFile = new File(newFileName);
                FileUtils.copyFile(file, newFile);
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), "utf8");
                writer.close();
                //压缩日志文件
                new Thread(() -> {
                    try {
                        File f = ZipUtil.zipFile(newFile);
                        if (f.exists()) {
                            newFile.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        LogUtil.flush();
                    }
                }).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void wirte(List<Log> logs, String filename) {
        try {
            checkFileSize(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            OutputStreamWriter writer = getWriter(filename);
            StringBuilder sb = new StringBuilder();
            for (Log log:logs ) {
                if (!hasErrorLog.get()&&log.getLevel()>Log.warn){
                    hasErrorLog.set(true);
                }
                switch (log.getLevel()) {
                    case Log.trace:
                        sb.append("[TRACE]");
                        break;
                    case Log.debug:
                        sb.append("[DEBUG]");
                        break;
                    case Log.info:
                        sb.append("[INFO]");
                        break;
                    case Log.warn:
                        sb.append("[WARN]");
                        break;
                    case Log.error:
                        sb.append("[ERROR]");
                        break;
                }
                sb.append(log.getMsg());
                sb.append(System.getProperty("line.separator"));
            }
            writer.write(sb.toString());
            writer.flush();
            if (sendToConsole) {
                System.out.print(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void flush() {
        List<Log> logs = threadLogs.get();
        if (logs==null||logs.size() == 0) {
            return;
        }
        wirte(logs, "info");
        if (hasErrorLog.get()) {
            wirte(logs, "error");
        }
        threadLogs.set(null);
        autoFlush.set(null);
    }

    public static synchronized void flushLine() {
        List<Log> logs = threadLogs.get();
        if (logs==null) {
            return;
        }
        logs.add(new Log(Log.no,""));
        flush();
    }

    public static void msg(Log log) {
        check_init();
        threadLogs.get().add(log);
        checkFlush();
    }


    private static void checkFlush(){
        if(autoFlush.get()==null||autoFlush.get()){
            flush();
        }
    }



    public static String getExceptionStackTraceToString(Throwable t) {
        if (t==null){
            return "";
        }
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }


}
