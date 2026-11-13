package com.google.android.gms.analytics;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
class h implements m {
    private static final Object sf = new Object();
    private static h st;
    private final Context mContext;
    private String su;
    private boolean sv = false;
    private final Object sw = new Object();

    protected h(Context context) {
        this.mContext = context;
        ce();
    }

    private boolean D(String str) {
        try {
            aa.y("Storing clientId.");
            FileOutputStream fileOutputStreamOpenFileOutput = this.mContext.openFileOutput("gaClientId", 0);
            fileOutputStreamOpenFileOutput.write(str.getBytes());
            fileOutputStreamOpenFileOutput.close();
            return true;
        } catch (FileNotFoundException e) {
            aa.w("Error creating clientId file.");
            return false;
        } catch (IOException e2) {
            aa.w("Error writing to clientId file.");
            return false;
        }
    }

    public static h cb() {
        h hVar;
        synchronized (sf) {
            hVar = st;
        }
        return hVar;
    }

    private String cc() {
        if (!this.sv) {
            synchronized (this.sw) {
                if (!this.sv) {
                    aa.y("Waiting for clientId to load");
                    do {
                        try {
                            this.sw.wait();
                        } catch (InterruptedException e) {
                            aa.w("Exception while waiting for clientId: " + e);
                        }
                    } while (!this.sv);
                }
            }
        }
        aa.y("Loaded clientId");
        return this.su;
    }

    private void ce() {
        new Thread("client_id_fetcher") { // from class: com.google.android.gms.analytics.h.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (h.this.sw) {
                    h.this.su = h.this.cf();
                    h.this.sv = true;
                    h.this.sw.notifyAll();
                }
            }
        }.start();
    }

    public static void n(Context context) {
        synchronized (sf) {
            if (st == null) {
                st = new h(context);
            }
        }
    }

    public boolean C(String str) {
        return "&cid".equals(str);
    }

    protected String cd() {
        String lowerCase = UUID.randomUUID().toString().toLowerCase();
        try {
            return !D(lowerCase) ? "0" : lowerCase;
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x002b  */
    String cf() {
        String str = null;
        try {
            FileInputStream fileInputStreamOpenFileInput = this.mContext.openFileInput("gaClientId");
            byte[] bArr = new byte[128];
            int i = fileInputStreamOpenFileInput.read(bArr, 0, 128);
            if (fileInputStreamOpenFileInput.available() > 0) {
                aa.w("clientId file seems corrupted, deleting it.");
                fileInputStreamOpenFileInput.close();
                this.mContext.deleteFile("gaClientId");
            } else if (i <= 0) {
                aa.w("clientId file seems empty, deleting it.");
                fileInputStreamOpenFileInput.close();
                this.mContext.deleteFile("gaClientId");
            } else {
                String str2 = new String(bArr, 0, i);
                try {
                    fileInputStreamOpenFileInput.close();
                    str = str2;
                } catch (FileNotFoundException e) {
                    str = str2;
                } catch (IOException e2) {
                    str = str2;
                    aa.w("Error reading clientId file, deleting it.");
                    this.mContext.deleteFile("gaClientId");
                    if (str == null) {
                        return cd();
                    }
                    return str;
                }
            }
        } catch (FileNotFoundException e3) {
        } catch (IOException e4) {
        }
        if (str == null) {
            return cd();
        }
        return str;
    }

    @Override // com.google.android.gms.analytics.m
    public String getValue(String field) {
        if ("&cid".equals(field)) {
            return cc();
        }
        return null;
    }
}
