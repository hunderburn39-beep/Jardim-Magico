package com.tapjoy.internal;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class l extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        forward(context, intent);
    }

    public final int forward(Context context, Intent intent) {
        try {
            Bundle bundle = context.getPackageManager().getReceiverInfo(new ComponentName(context, getClass()), 128).metaData;
            if (bundle == null) {
                return 0;
            }
            int i = 0;
            for (String str : bundle.keySet()) {
                try {
                    String string = bundle.getString(str);
                    if (string != null) {
                        try {
                            Object objNewInstance = Class.forName(string).newInstance();
                            if (objNewInstance instanceof BroadcastReceiver) {
                                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) objNewInstance;
                                Intent intent2 = new Intent(intent);
                                intent2.setComponent(new ComponentName(context, string));
                                broadcastReceiver.onReceive(context, intent2);
                                i++;
                            }
                        } catch (ClassNotFoundException e) {
                        } catch (IllegalAccessException e2) {
                        } catch (InstantiationException e3) {
                        }
                    } else {
                        new Object[1][0] = str;
                    }
                } catch (PackageManager.NameNotFoundException e4) {
                    return i;
                }
            }
            return i;
        } catch (PackageManager.NameNotFoundException e5) {
            return 0;
        }
    }
}
