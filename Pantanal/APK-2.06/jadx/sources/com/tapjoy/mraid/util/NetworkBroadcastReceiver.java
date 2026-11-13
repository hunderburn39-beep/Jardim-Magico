package com.tapjoy.mraid.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.mraid.controller.Network;

/* JADX INFO: loaded from: classes.dex */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private Network a;

    public NetworkBroadcastReceiver(Network mraidNetworkController) {
        this.a = mraidNetworkController;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            this.a.onConnectionChanged();
        }
    }
}
