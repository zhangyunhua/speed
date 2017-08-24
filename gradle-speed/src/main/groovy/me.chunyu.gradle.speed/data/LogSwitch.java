package me.chunyu.gradle.speed.data;

/**
 * Created by zhangyunhua on 17/8/22.
 */

public class LogSwitch {

    public static enum LogMode {
        FILE, CONSOLE, ALL
    }
    public String logPath;
    public LogMode logMode;
    public boolean isSimple;
    public String startExpression;
    public String endExpression;
    public long speedThreshold;





}
