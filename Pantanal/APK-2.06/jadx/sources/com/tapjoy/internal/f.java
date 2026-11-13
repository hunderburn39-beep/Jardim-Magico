package com.tapjoy.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.TapjoyConstants;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class f extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String strA = a(intent);
        if (strA != null) {
            a(context, "install_referrer", strA);
        }
    }

    public static String a(Intent intent) {
        if ("com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
            return intent.getStringExtra(TapjoyConstants.TJC_REFERRER);
        }
        return null;
    }

    private static boolean a(Context context, String str, String str2) {
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        try {
            fileOutputStreamOpenFileOutput = context.openFileOutput(str, 0);
            bk.a(fileOutputStreamOpenFileOutput, str2);
            fileOutputStreamOpenFileOutput.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e2) {
            da.a(fileOutputStreamOpenFileOutput);
            context.deleteFile("install_referrer");
            return false;
        }
    }
}
