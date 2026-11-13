package com.adcolony.sdk;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: classes.dex */
class t {
    URL a;

    public t(URL url) {
        this.a = url;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x005b  */
    /* JADX WARN: Code duplicated, block: B:25:0x0060  */
    public int a(String str) throws Throwable {
        GZIPOutputStream gZIPOutputStream;
        HttpURLConnection httpURLConnection;
        boolean z;
        DataOutputStream dataOutputStream;
        HttpURLConnection httpURLConnection2;
        DataOutputStream dataOutputStream2 = null;
        dataOutputStream2 = null;
        gZIPOutputStream = null;
        dataOutputStream2 = null;
        GZIPOutputStream gZIPOutputStream2 = null;
        boolean z2 = false;
        try {
            HttpURLConnection httpURLConnection3 = (HttpURLConnection) this.a.openConnection();
            try {
                httpURLConnection3.setRequestMethod("POST");
                httpURLConnection3.setRequestProperty("Content-Encoding", "gzip");
                httpURLConnection3.setRequestProperty("Content-Type", "application/json");
                httpURLConnection3.setDoInput(true);
                gZIPOutputStream = new GZIPOutputStream(httpURLConnection3.getOutputStream());
                try {
                    DataOutputStream dataOutputStream3 = new DataOutputStream(gZIPOutputStream);
                    try {
                        dataOutputStream3.writeBytes(str);
                        dataOutputStream3.close();
                        try {
                            int responseCode = httpURLConnection3.getResponseCode();
                            if (dataOutputStream3 != null) {
                            }
                            if (gZIPOutputStream != null) {
                                gZIPOutputStream.close();
                            }
                            if (httpURLConnection3 != null) {
                                httpURLConnection3.disconnect();
                            }
                            return responseCode;
                        } catch (IOException e) {
                            gZIPOutputStream2 = gZIPOutputStream;
                            httpURLConnection2 = httpURLConnection3;
                            e = e;
                            z = true;
                            dataOutputStream = dataOutputStream3;
                            try {
                                throw e;
                            } catch (Throwable th) {
                                th = th;
                                gZIPOutputStream = gZIPOutputStream2;
                                dataOutputStream2 = dataOutputStream;
                                boolean z3 = z;
                                httpURLConnection = httpURLConnection2;
                                z2 = z3;
                                if (dataOutputStream2 != null && !z2) {
                                    dataOutputStream2.close();
                                }
                                if (gZIPOutputStream != null) {
                                    gZIPOutputStream.close();
                                }
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            z2 = true;
                            dataOutputStream2 = dataOutputStream3;
                            httpURLConnection = httpURLConnection3;
                            th = th2;
                            if (dataOutputStream2 != null) {
                                dataOutputStream2.close();
                            }
                            if (gZIPOutputStream != null) {
                                gZIPOutputStream.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            throw th;
                        }
                    } catch (IOException e2) {
                        dataOutputStream = dataOutputStream3;
                        gZIPOutputStream2 = gZIPOutputStream;
                        z = false;
                        httpURLConnection2 = httpURLConnection3;
                        e = e2;
                    } catch (Throwable th3) {
                        dataOutputStream2 = dataOutputStream3;
                        httpURLConnection = httpURLConnection3;
                        th = th3;
                    }
                } catch (IOException e3) {
                    dataOutputStream = null;
                    gZIPOutputStream2 = gZIPOutputStream;
                    z = false;
                    httpURLConnection2 = httpURLConnection3;
                    e = e3;
                } catch (Throwable th4) {
                    httpURLConnection = httpURLConnection3;
                    th = th4;
                }
            } catch (IOException e4) {
                dataOutputStream = null;
                httpURLConnection2 = httpURLConnection3;
                e = e4;
                z = false;
            } catch (Throwable th5) {
                gZIPOutputStream = null;
                httpURLConnection = httpURLConnection3;
                th = th5;
            }
        } catch (IOException e5) {
            e = e5;
            z = false;
            dataOutputStream = null;
            httpURLConnection2 = null;
        } catch (Throwable th6) {
            th = th6;
            gZIPOutputStream = null;
            httpURLConnection = null;
        }
    }
}
