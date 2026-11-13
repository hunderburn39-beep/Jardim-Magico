package com.google.android.gms.internal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class dr {
    private final Context mContext;
    private int mState;
    private String rh;
    private final float ri;
    private float rj;
    private float rk;
    private float rl;

    public dr(Context context) {
        this.mState = 0;
        this.mContext = context;
        this.ri = context.getResources().getDisplayMetrics().density;
    }

    public dr(Context context, String str) {
        this(context);
        this.rh = str;
    }

    private void a(int i, float f, float f2) {
        if (i == 0) {
            this.mState = 0;
            this.rj = f;
            this.rk = f2;
            this.rl = f2;
            return;
        }
        if (this.mState != -1) {
            if (i != 2) {
                if (i == 1 && this.mState == 4) {
                    showDialog();
                    return;
                }
                return;
            }
            if (f2 > this.rk) {
                this.rk = f2;
            } else if (f2 < this.rl) {
                this.rl = f2;
            }
            if (this.rk - this.rl > 30.0f * this.ri) {
                this.mState = -1;
                return;
            }
            if (this.mState == 0 || this.mState == 2) {
                if (f - this.rj >= 50.0f * this.ri) {
                    this.rj = f;
                    this.mState++;
                }
            } else if ((this.mState == 1 || this.mState == 3) && f - this.rj <= (-50.0f) * this.ri) {
                this.rj = f;
                this.mState++;
            }
            if (this.mState == 1 || this.mState == 3) {
                if (f > this.rj) {
                    this.rj = f;
                }
            } else {
                if (this.mState != 2 || f >= this.rj) {
                    return;
                }
                this.rj = f;
            }
        }
    }

    private void showDialog() {
        final String strTrim;
        if (TextUtils.isEmpty(this.rh)) {
            strTrim = "No debug information";
        } else {
            Uri uriBuild = new Uri.Builder().encodedQuery(this.rh).build();
            StringBuilder sb = new StringBuilder();
            Map<String, String> mapB = dq.b(uriBuild);
            for (String str : mapB.keySet()) {
                sb.append(str).append(" = ").append(mapB.get(str)).append("\n\n");
            }
            strTrim = sb.toString().trim();
            if (TextUtils.isEmpty(strTrim)) {
                strTrim = "No debug information";
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setMessage(strTrim);
        builder.setTitle("Ad Information");
        builder.setPositiveButton("Share", new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.dr.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dr.this.mContext.startActivity(Intent.createChooser(new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", strTrim), "Share via"));
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.dr.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    public void c(MotionEvent motionEvent) {
        int historySize = motionEvent.getHistorySize();
        for (int i = 0; i < historySize; i++) {
            a(motionEvent.getActionMasked(), motionEvent.getHistoricalX(0, i), motionEvent.getHistoricalY(0, i));
        }
        a(motionEvent.getActionMasked(), motionEvent.getX(), motionEvent.getY());
    }

    public void t(String str) {
        this.rh = str;
    }
}
