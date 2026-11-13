package com.adcolony.sdk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.gcm.GcmListenerService;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyPubServicesPushGcmListenerService extends GcmListenerService {
    private static final String a = "AdColonyPubServicesPushGcmListenerService";
    private NotificationManagerCompat b;

    public void onMessageReceived(String from, Bundle bundle) {
        bw.b(a, "OnMessageReceived", true);
        a(from, bundle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void a(String str, Bundle bundle) {
        String str2;
        String string;
        String str3;
        String string2;
        String string3;
        String str4;
        int i;
        int iAo;
        bw.b(a, "gcm notification bundle=" + bundle.toString(), true);
        bundle.putBoolean("receivedInForeground", true);
        a(bundle);
        this.b = NotificationManagerCompat.from(this);
        Bundle bundle2 = bundle.getBundle("notification");
        if (bundle2 != null) {
            string3 = bundle2.getString("title");
            string2 = bundle2.getString("body");
            String string4 = bundle2.getString("color");
            string = bundle2.getString("sound");
            String string5 = bundle2.getString("icon");
            str3 = string4;
            str2 = string5;
        } else {
            str2 = null;
            string = null;
            str3 = null;
            string2 = null;
            string3 = null;
        }
        int color = str3 != null ? Color.parseColor(str3) : -1;
        if (bv.aN().j() != null) {
            String packageName = bv.aN().j().getPackageName();
            if (packageName != null) {
                int identifier = string != null ? bv.aN().j().getResources().getIdentifier(string, "raw", packageName) : 0;
                if (str2 != null) {
                    iAo = bv.aN().j().getResources().getIdentifier(str2, "drawable", packageName);
                    i = identifier;
                    str4 = packageName;
                } else {
                    i = identifier;
                    str4 = packageName;
                    iAo = -1;
                }
            } else {
                str4 = packageName;
                i = 0;
                iAo = -1;
            }
        } else {
            str4 = null;
            i = 0;
            iAo = -1;
        }
        if (iAo == -1) {
            try {
                iAo = bv.aN().ao();
            } catch (Exception e) {
                bw.a(a, "Received Push Message but could not render it in the status bar: error Message =" + e.getMessage(), e, false);
                return;
            }
        }
        Intent intent = new Intent((Context) this, (Class<?>) MessagingLaunchActivity.class);
        intent.addFlags(67108864);
        intent.addFlags(DriveFile.MODE_WRITE_ONLY);
        intent.addFlags(134217728);
        intent.putExtras(bundle);
        NotificationCompat.Builder contentIntent = new NotificationCompat.Builder(this).setAutoCancel(true).setSmallIcon(iAo).setContentIntent(PendingIntent.getActivity(this, 0, intent, 134217728));
        if (string3 != null) {
            contentIntent.setContentTitle(string3);
        }
        if (string2 != null) {
            contentIntent.setStyle(new NotificationCompat.BigTextStyle().bigText(string2)).setContentText(string2);
        }
        if (i != 0 && str4 != null) {
            contentIntent.setSound(Uri.parse("android.resource://" + str4 + "/" + i));
        }
        if (color != -1) {
            contentIntent.setColor(color);
        }
        contentIntent.setDefaults(1);
        this.b.notify(bv.aN().aJ(), contentIntent.build());
        bv.aN().aK();
    }

    private void a(Bundle bundle) {
        AdColonyPubServicesPushNotification adColonyPubServicesPushNotification = new AdColonyPubServicesPushNotification();
        adColonyPubServicesPushNotification.a(System.currentTimeMillis());
        Object obj = bundle.get("adColonyPubServices");
        if (obj != null) {
            adColonyPubServicesPushNotification.b(true);
            adColonyPubServicesPushNotification.a(obj.toString());
            adColonyPubServicesPushNotification.a(true);
            bv.aN().aL().b(adColonyPubServicesPushNotification);
        }
    }
}
