package com.fyber.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: FyberPersistence.java */
/* JADX INFO: loaded from: classes.dex */
public final class f {
    /* JADX WARN: Code duplicated, block: B:38:0x0175  */
    /* JADX WARN: Code duplicated, block: B:40:0x0186  */
    public static void a(Context context) {
        File file;
        SharedPreferences sharedPreferences = context.getSharedPreferences("migration", 0);
        if (!sharedPreferences.getBoolean("successfulMigration", false)) {
            a(context, "ConfigurationRequester", "MediationConfigurationNetworkOperation");
            a(context, "SPStatistics", "FyberStatistics");
            a(context, "SponsorPayPublisherState", "FyberPreferences");
            a(context, "SponsorPayCookiePrefsFile", "FyberCookiePrefsFile");
            a(context, "SponsorPayAdvertiserState", "FyberPreferences");
            File file2 = new File(context.getFilesDir(), "FyberCacheStorage");
            File file3 = new File(context.getFilesDir(), "SPCacheStorage");
            if (file3.exists() && file3.renameTo(file2)) {
                FyberLogger.i("FyberPersistence", "File:" + file3.getName() + " has been successfully renamed.");
            }
            FyberLogger.d("FyberPersistence", "Checking previous cache directory");
            if ("mounted".equals(Environment.getExternalStorageState())) {
                if (context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                    FyberLogger.d("FyberPersistence", "External write storage permission granted");
                    File file4 = new File(Environment.getExternalStorageDirectory(), "VideoCache" + File.separator + context.getPackageName().hashCode());
                    if (file4.exists()) {
                        FyberLogger.d("FyberPersistence", "Application cache directory exists, deleting...");
                        a(file4);
                        file4.delete();
                    }
                    File[] fileArrListFiles = file4.getParentFile().listFiles();
                    if (fileArrListFiles != null && fileArrListFiles.length == 0) {
                        FyberLogger.d("FyberPersistence", "Cache directory empty, deleting...");
                        file4.getParentFile().delete();
                    }
                } else {
                    file = new File(context.getFilesDir(), "VideoCache");
                    if (file.exists()) {
                        FyberLogger.d("FyberPersistence", "Internal cache directory exists, deleting...");
                        a(file);
                        file.delete();
                    }
                }
            } else {
                file = new File(context.getFilesDir(), "VideoCache");
                if (file.exists()) {
                    FyberLogger.d("FyberPersistence", "Internal cache directory exists, deleting...");
                    a(file);
                    file.delete();
                }
            }
            sharedPreferences.edit().putBoolean("successfulMigration", true).commit();
        }
        if (sharedPreferences.getBoolean("protocolMigration", false)) {
            return;
        }
        File file5 = new File(context.getFilesDir().getAbsolutePath(), "FyberCacheStorage");
        try {
            if (file5.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file5);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    context.getSharedPreferences("FyberCacheStorage", 0).edit().putString("FyberCacheStorage", com.fyber.cache.internal.f.a((Collection<com.fyber.cache.internal.c>) ((HashMap) objectInputStream.readObject()).values())).apply();
                } catch (IOException | ClassNotFoundException e) {
                    FyberLogger.d("FyberPersistence", "There was an exception migrating the old cache information - " + e.getMessage());
                }
                objectInputStream.close();
                fileInputStream.close();
                FyberLogger.d("FyberPersistence", "Removing old cache file info - " + (file5.delete() ? "successful" : "unsuccessful"));
            }
        } catch (IOException e2) {
            FyberLogger.d("FyberPersistence", "There was an exception migrating the old cache information - " + e2.getMessage());
        }
        sharedPreferences.edit().putBoolean("protocolMigration", true).commit();
    }

    private static SharedPreferences a(Context context, String str, String str2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        Map<String, ?> all = sharedPreferences.getAll();
        SharedPreferences sharedPreferences2 = context.getSharedPreferences(str2, 0);
        if (!all.isEmpty()) {
            SharedPreferences.Editor editorEdit = sharedPreferences2.edit();
            for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                Object value = entry.getValue();
                String key = entry.getKey();
                String strReplace = key.contains("SponsorPayAdvertiserState") ? key.replace("SponsorPayAdvertiserState", "AdvertiserAnswerReceived") : key;
                if (value instanceof String) {
                    editorEdit.putString(strReplace, (String) value);
                } else if (value instanceof Integer) {
                    editorEdit.putInt(strReplace, ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    editorEdit.putLong(strReplace, ((Long) value).longValue());
                } else if (value instanceof Float) {
                    editorEdit.putFloat(strReplace, ((Float) value).floatValue());
                } else if (value instanceof Boolean) {
                    editorEdit.putBoolean(strReplace, ((Boolean) value).booleanValue());
                }
            }
            editorEdit.commit();
            sharedPreferences.edit().clear().commit();
        }
        File file = new File(context.getApplicationInfo().dataDir + File.separator + "shared_prefs" + File.separator + str + ".xml");
        if (file.exists() && file.delete()) {
            FyberLogger.i("FyberPersistence", "SharedPreferences " + str + ", have been deleted.");
        }
        return sharedPreferences2;
    }

    private static void a(File file) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                file2.delete();
            }
        }
    }
}
