package com.tapjoy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.internal.fw;

/* JADX INFO: loaded from: classes.dex */
public class GCMReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        boolean zA = fw.b(context).a(intent);
        if (isOrderedBroadcast()) {
            setResult(-1, null, null);
            if (zA) {
                abortBroadcast();
            }
        }
    }
}
