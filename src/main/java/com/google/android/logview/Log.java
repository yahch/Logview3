package com.google.android.logview;

public class Log {

    public String logText;

    public LogType logType;

    public long logTime;

    public Log() {
    }

    public Log(String logText, LogType logType, long logTime) {
        this.logText = logText;
        this.logType = logType;
        this.logTime = logTime;
    }
}
