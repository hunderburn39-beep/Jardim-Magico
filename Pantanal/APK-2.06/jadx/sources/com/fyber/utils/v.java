package com.fyber.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.UUID;

/* JADX INFO: compiled from: UserId.java */
/* JADX INFO: loaded from: classes.dex */
public final class v {
    public static synchronized String a(Context context) {
        String string;
        SharedPreferences sharedPreferences = context.getSharedPreferences("FyberPreferences", 0);
        string = sharedPreferences.getString("STATE_GENERATED_USERID_KEY", null);
        if (string == null) {
            String string2 = UUID.randomUUID().toString();
            string = q.a(string2);
            if (string == null || string.equals("nosha1")) {
                string = string2;
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putString("STATE_GENERATED_USERID_KEY", string);
            editorEdit.commit();
        }
        return string;
    }
}
