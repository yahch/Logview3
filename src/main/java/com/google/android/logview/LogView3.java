package com.google.android.logview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.google.android.logview.utils.Base64;
import com.google.android.logview.utils.HexDumpEncoder;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public class LogView3 extends ListView {

    private static final String TAG = "LogView3Printer";

    private final HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();

    private boolean outputEnable;

    private boolean logCatEnable;

    public void setLogCatEnable(boolean logCatEnable) {
        this.logCatEnable = logCatEnable;
    }

    public void setOutputEnable(boolean outputEnable) {
        this.outputEnable = outputEnable;
    }

    public boolean isOutputEnable() {
        return outputEnable;
    }

    private LogView3Adapter logView3Adapter;

    private CopyOnWriteArrayList<Log> logList;

    public LogView3(Context context) {
        super(context);
        init();
    }

    public LogView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LogView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.logCatEnable = false;
        this.outputEnable = true;
        this.setDivider(null);
        this.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        this.logList = new CopyOnWriteArrayList<>();
        this.logView3Adapter = new LogView3Adapter(getContext(), R.layout.layout_logview3, this.logList);
        this.setAdapter(this.logView3Adapter);
        this.logView3Adapter.notifyDataSetChanged();
    }

    public void append(Log log) {
        if (!outputEnable) return;
        if (this.logList == null) this.logList = new CopyOnWriteArrayList<>();
        if (this.logList.size() > 10000) {
            this.logList.clear();
        }
        this.logList.add(log);
        this.logView3Adapter.notifyDataSetChanged();
    }

    public void append(LogType logType, String logText) {
        append(new Log(logText, logType, System.currentTimeMillis()));
    }

    // DBG, OK, IFO, WRN, ERR
    public void debug(String text) {
        if (logCatEnable) {
            android.util.Log.d(TAG, "debug: " + text);
        }
        append(LogType.DBG, text);
    }

    public void dumpHex(byte[] hexbytes) {
        String hexString = hexDumpEncoder.encode(hexbytes);
        if (logCatEnable) {
            android.util.Log.d(TAG, "dump: " + hexString);
        }
        append(LogType.DBG, hexString);
    }

    public void dumpBase64(byte[] hexbytes) {
        String hexString = "";
        try {
            hexString = Base64.encode(hexbytes, StandardCharsets.UTF_8.name());
        } catch (Exception e) {

        }
        if (logCatEnable) {
            android.util.Log.d(TAG, "dump: " + hexString);
        }
        append(LogType.DBG, hexString);
    }

    public void success(String text) {
        if (logCatEnable) {
            android.util.Log.d(TAG, "success: " + text);
        }
        append(LogType.OK, text);
    }

    public void info(String text) {
        if (logCatEnable) {
            android.util.Log.i(TAG, "info: " + text);
        }
        append(LogType.IFO, text);
    }

    public void warn(String text) {
        if (logCatEnable) {
            android.util.Log.w(TAG, "warn: " + text);
        }
        append(LogType.WRN, text);
    }

    public void error(String text) {
        if (logCatEnable) {
            android.util.Log.e(TAG, "error: " + text);
        }
        append(LogType.ERR, text);
    }

    public void error(String text, Throwable throwable) {
        if (logCatEnable) {
            android.util.Log.e(TAG, "error: " + text, throwable);
        }
        append(LogType.ERR, text);
    }

}
