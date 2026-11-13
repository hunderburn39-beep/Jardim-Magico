package com.tapjoy.internal;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class p {
    public static boolean a(SharedPreferences sharedPreferences, String str, boolean z) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putBoolean(str, z);
        return editorEdit.commit();
    }

    public static boolean a(SharedPreferences sharedPreferences, String str, int i) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putInt(str, i);
        return editorEdit.commit();
    }

    public static boolean a(SharedPreferences sharedPreferences, String str, String str2) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putString(str, str2);
        return editorEdit.commit();
    }
}
