package com.tapjoy;

import android.content.Context;
import android.content.SharedPreferences;
import com.tapjoy.internal.cr;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyAppSettings {
    public static final String TAG = TapjoyAppSettings.class.getSimpleName();
    private static TapjoyAppSettings b;
    String a;
    private Context c;
    private SharedPreferences d;

    private TapjoyAppSettings(Context applicationContext) {
        this.c = applicationContext;
        this.d = this.c.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0);
        this.a = this.d.getString(TapjoyConstants.PREF_LOG_LEVEL, null);
        if (cr.c(this.a)) {
            return;
        }
        TapjoyLog.d(TAG, "restoreLoggingLevel from sharedPref -- loggingLevel=" + this.a);
        TapjoyLog.a(this.a, true);
    }

    public static TapjoyAppSettings getInstance() {
        return b;
    }

    public static void init(Context applicationContext) {
        TapjoyLog.d(TAG, "initializing app settings");
        b = new TapjoyAppSettings(applicationContext);
    }

    public void saveLoggingLevel(String level) {
        if (cr.c(level)) {
            TapjoyLog.d(TAG, "saveLoggingLevel -- server logging level is NULL or Empty string");
            return;
        }
        TapjoyLog.d(TAG, "saveLoggingLevel -- currentLevel=" + this.a + ";newLevel=" + level);
        if (cr.c(this.a) || !this.a.equals(level)) {
            SharedPreferences.Editor editorEdit = this.d.edit();
            editorEdit.putString(TapjoyConstants.PREF_LOG_LEVEL, level);
            editorEdit.commit();
            this.a = level;
            TapjoyLog.a(this.a, true);
        }
        TapjoyLog.i(TAG, "Tapjoy remote device debugging set to '" + level + "'. The SDK Debug-setting is: " + (TapjoyLog.isLoggingEnabled() ? "'Enabled'" : "'Disabled'"));
    }

    public void clearLoggingLevel() {
        SharedPreferences.Editor editorEdit = this.d.edit();
        editorEdit.remove(TapjoyConstants.PREF_LOG_LEVEL);
        editorEdit.commit();
        this.a = null;
        boolean zIsLoggingEnabled = TapjoyLog.isLoggingEnabled();
        TapjoyLog.i(TAG, "Tapjoy remote device debugging 'Disabled'. The SDK Debug-setting is: " + (zIsLoggingEnabled ? "'Enabled'" : "'Disabled'"));
        TapjoyLog.setDebugEnabled(zIsLoggingEnabled);
    }

    public void saveConnectResultAndParams(String result, String paramsHash, long expires) {
        if (!cr.c(result) && !cr.c(paramsHash)) {
            SharedPreferences.Editor editorEdit = this.d.edit();
            editorEdit.putString(TapjoyConstants.PREF_LAST_CONNECT_RESULT, result);
            editorEdit.putString(TapjoyConstants.PREF_LAST_CONNECT_PARAMS_HASH, paramsHash);
            if (expires >= 0) {
                editorEdit.putLong(TapjoyConstants.PREF_LAST_CONNECT_RESULT_EXPIRES, expires);
            } else {
                editorEdit.remove(TapjoyConstants.PREF_LAST_CONNECT_RESULT_EXPIRES);
            }
            TapjoyLog.i(TAG, "Stored connect result");
            editorEdit.commit();
        }
    }

    public void removeConnectResult() {
        if (this.d.getString(TapjoyConstants.PREF_LAST_CONNECT_PARAMS_HASH, null) != null) {
            SharedPreferences.Editor editorEdit = this.d.edit();
            editorEdit.remove(TapjoyConstants.PREF_LAST_CONNECT_RESULT);
            editorEdit.remove(TapjoyConstants.PREF_LAST_CONNECT_PARAMS_HASH);
            editorEdit.remove(TapjoyConstants.PREF_LAST_CONNECT_RESULT_EXPIRES);
            TapjoyLog.i(TAG, "Removed connect result");
            editorEdit.commit();
        }
    }

    public String getConnectResult(String paramsHash, long currentTimeMillis) {
        String string = this.d.getString(TapjoyConstants.PREF_LAST_CONNECT_RESULT, null);
        if (cr.c(string) || cr.c(paramsHash) || !paramsHash.equals(this.d.getString(TapjoyConstants.PREF_LAST_CONNECT_PARAMS_HASH, null))) {
            return null;
        }
        long j = this.d.getLong(TapjoyConstants.PREF_LAST_CONNECT_RESULT_EXPIRES, -1L);
        if (j < 0 || j >= currentTimeMillis) {
            return string;
        }
        return null;
    }
}
