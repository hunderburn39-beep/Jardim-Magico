package com.tapjoy.mraid.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.mraid.controller.Display;

/* JADX INFO: loaded from: classes.dex */
public class ConfigBroadcastReceiver extends BroadcastReceiver {
    private Display a;
    private int b;

    public ConfigBroadcastReceiver(Display mraidDisplayController) {
        this.a = mraidDisplayController;
        this.b = this.a.getOrientation();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int orientation;
        if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED") && (orientation = this.a.getOrientation()) != this.b) {
            this.b = orientation;
            this.a.onOrientationChanged(this.b);
        }
    }
}
