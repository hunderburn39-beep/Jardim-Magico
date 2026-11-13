package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tapjoy.TJAdUnitConstants;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    static GoogleCloudMessaging Nq;
    private PendingIntent Nr;
    final BlockingQueue<Intent> Ns = new LinkedBlockingQueue();
    private Handler Nt = new Handler(Looper.getMainLooper()) { // from class: com.google.android.gms.gcm.GoogleCloudMessaging.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            GoogleCloudMessaging.this.Ns.add((Intent) msg.obj);
        }
    };
    private Messenger Nu = new Messenger(this.Nt);
    private Context kI;

    private void a(String str, String str2, long j, int i, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        }
        if (str == null) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        intent.putExtras(bundle);
        c(intent);
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
        intent.putExtra("google.to", str);
        intent.putExtra("google.message_id", str2);
        intent.putExtra("google.ttl", Long.toString(j));
        intent.putExtra("google.delay", Integer.toString(i));
        this.kI.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    private void c(String... strArr) {
        String strD = d(strArr);
        Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
        intent.putExtra("google.messenger", this.Nu);
        c(intent);
        intent.putExtra("sender", strD);
        this.kI.startService(intent);
    }

    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        if (Nq == null) {
            Nq = new GoogleCloudMessaging();
            Nq.kI = context;
        }
        return Nq;
    }

    private void hL() {
        Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
        this.Ns.clear();
        intent.putExtra("google.messenger", this.Nu);
        c(intent);
        this.kI.startService(intent);
    }

    synchronized void c(Intent intent) {
        if (this.Nr == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.Nr = PendingIntent.getBroadcast(this.kI, 0, intent2, 0);
        }
        intent.putExtra("app", this.Nr);
    }

    public void close() {
        hM();
    }

    String d(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder sb = new StringBuilder(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            sb.append(',').append(strArr[i]);
        }
        return sb.toString();
    }

    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra == null ? MESSAGE_TYPE_MESSAGE : stringExtra;
    }

    synchronized void hM() {
        if (this.Nr != null) {
            this.Nr.cancel();
            this.Nr = null;
        }
    }

    public String register(String... senderIds) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        }
        this.Ns.clear();
        c(senderIds);
        try {
            Intent intentPoll = this.Ns.poll(5000L, TimeUnit.MILLISECONDS);
            if (intentPoll == null) {
                throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
            }
            String stringExtra = intentPoll.getStringExtra("registration_id");
            if (stringExtra != null) {
                return stringExtra;
            }
            intentPoll.getStringExtra(TJAdUnitConstants.String.VIDEO_ERROR);
            String stringExtra2 = intentPoll.getStringExtra(TJAdUnitConstants.String.VIDEO_ERROR);
            if (stringExtra2 != null) {
                throw new IOException(stringExtra2);
            }
            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void send(String to, String msgId, long timeToLive, Bundle data) throws IOException {
        a(to, msgId, timeToLive, -1, data);
    }

    public void send(String to, String msgId, Bundle data) throws IOException {
        send(to, msgId, -1L, data);
    }

    public void unregister() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        }
        hL();
        try {
            Intent intentPoll = this.Ns.poll(5000L, TimeUnit.MILLISECONDS);
            if (intentPoll == null) {
                throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
            }
            if (intentPoll.getStringExtra("unregistered") != null) {
                return;
            }
            String stringExtra = intentPoll.getStringExtra(TJAdUnitConstants.String.VIDEO_ERROR);
            if (stringExtra == null) {
                throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
            }
            throw new IOException(stringExtra);
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        }
    }
}
