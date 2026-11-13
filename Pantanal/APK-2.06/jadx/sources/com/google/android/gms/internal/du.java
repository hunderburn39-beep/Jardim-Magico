package com.google.android.gms.internal;

import android.content.Context;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public final class du extends Cdo {
    private final String lh;
    private final Context mContext;
    private final String ro;

    public du(Context context, String str, String str2) {
        this.mContext = context;
        this.lh = str;
        this.ro = str2;
    }

    @Override // com.google.android.gms.internal.Cdo
    public void aY() {
        try {
            dw.y("Pinging URL: " + this.ro);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.ro).openConnection();
            try {
                dq.a(this.mContext, this.lh, true, httpURLConnection);
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode < 200 || responseCode >= 300) {
                    dw.z("Received non-success response code " + responseCode + " from pinging URL: " + this.ro);
                }
            } finally {
                httpURLConnection.disconnect();
            }
        } catch (IOException e) {
            dw.z("Error while pinging URL: " + this.ro + ". " + e.getMessage());
        } catch (IndexOutOfBoundsException e2) {
            dw.z("Error while parsing ping URL: " + this.ro + ". " + e2.getMessage());
        }
    }

    @Override // com.google.android.gms.internal.Cdo
    public void onStop() {
    }
}
