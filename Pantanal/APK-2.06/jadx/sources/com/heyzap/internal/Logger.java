package com.heyzap.internal;

import android.content.Context;
import android.util.Log;
import com.fyber.utils.FyberLogger;
import java.io.PrintWriter;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public class Logger {
    static boolean ONLY_TEMP_LOG = false;
    static boolean ENABLED = false;
    static String TAG = Analytics.LOG_TAG;

    public static void init(final Context context) {
        new Thread(new Runnable() { // from class: com.heyzap.internal.Logger.1
            @Override // java.lang.Runnable
            public void run() {
                if (Utils.packageIsInstalled(Constants.SNAKE_PACKAGE, context)) {
                    Logger.ENABLED = true;
                    FyberLogger.enableLogging(true);
                }
            }
        }).start();
    }

    public static void verbose(String s) {
        if (ENABLED) {
            if (s != null) {
                Log.v(TAG, s);
            } else {
                Log.v(TAG, "NULL");
            }
        }
    }

    public static void debug(Object s) {
        if (ENABLED) {
            if (s != null) {
                debug(s.toString());
            } else {
                debug("NULL");
            }
        }
    }

    public static void debug(String msg, Throwable e) {
        if (ENABLED) {
            Log.d(TAG, msg, e);
        }
    }

    public static void debug(String s) {
        if (ENABLED) {
            if (s != null) {
                Log.d(TAG, s);
            } else {
                debug("NULL");
            }
        }
    }

    public static void warn(String s) {
        if (ENABLED) {
            Log.w(TAG, s);
        }
    }

    public static void error(String msg, Throwable e) {
        if (ENABLED) {
            Log.e(TAG, msg, e);
        }
    }

    public static void info(String s) {
        if (ENABLED) {
            if (s != null) {
                Log.i(TAG, s);
            } else {
                debug("NULL");
            }
        }
    }

    public static void info(Object o) {
        if (ENABLED) {
            if (o != null) {
                info(o.toString());
            } else {
                info("NULL");
            }
        }
    }

    public static void trace(Object s) {
        if (ENABLED) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("Stack Trace: %s\n", String.valueOf(s)));
            StackTraceElement[] trace = new RuntimeException().getStackTrace();
            for (int i = 1; i < trace.length; i++) {
                StackTraceElement el = trace[i];
                builder.append(String.format("\t%s:%d in %s.%s\n", el.getFileName(), Integer.valueOf(el.getLineNumber()), el.getClassName(), el.getMethodName()));
            }
            debug(builder.toString());
        }
    }

    public static void trace(String message, Throwable t) {
        if (ENABLED) {
            error(message);
            trace(t);
        }
    }

    public static void trace(Throwable t) {
        if (ENABLED && t != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            String trace = sw.toString();
            error(trace);
        }
    }

    @Deprecated
    public static void log(Object s) {
        debug(s);
    }

    @Deprecated
    public static void log(String s) {
        debug(s);
    }

    public static void format(String format, Object... args) {
        debug(String.format(format, args));
    }

    public static void t(Object... os) {
        if (ENABLED) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < os.length; i++) {
                builder.append(String.valueOf(os[i]));
                if (i + 1 < os.length) {
                    builder.append(", ");
                }
            }
            log(builder.toString());
        }
    }

    @Deprecated
    public static void log(Object... os) {
        if (ENABLED && !ONLY_TEMP_LOG) {
            if (os == null) {
                log("null arguments");
                return;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < os.length; i++) {
                builder.append(String.valueOf(os[i]));
                if (i + 1 < os.length) {
                    builder.append(", ");
                }
            }
            log(builder.toString());
        }
    }

    public static void trace() {
        trace("");
    }

    public static void error(String msg) {
        if (ENABLED) {
            Log.e(TAG, msg);
        }
    }

    public static void setDebugLogging(boolean enabled) {
        FyberLogger.enableLogging(enabled);
        ENABLED = enabled;
    }
}
