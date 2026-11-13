package com.chartboost.sdk.impl;

import android.os.Handler;
import com.heyzap.http.AsyncHttpClient;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
class ar extends Thread {
    private final as a;
    private final PriorityBlockingQueue<ac<?>> b;
    private final Handler c = g.a().a;
    private volatile boolean d = false;

    public ar(as asVar, PriorityBlockingQueue<ac<?>> priorityBlockingQueue) {
        this.a = asVar;
        this.b = priorityBlockingQueue;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws Throwable {
        ah ahVarA;
        ae<?> aeVarA;
        g.a().b(10);
        while (!this.d) {
            try {
                ac<?> acVarTake = this.b.take();
                if (acVarTake.e.compareAndSet(0, 1)) {
                    acVarTake.g = System.nanoTime();
                    try {
                        ahVarA = a(acVarTake);
                        try {
                            try {
                                int iB = ahVarA.b();
                                if (iB >= 200 && iB < 300) {
                                    aeVarA = acVarTake.a(ahVarA);
                                    ahVarA = null;
                                } else if (iB == 401 || iB == 403) {
                                    aeVarA = ae.a((Exception) new ao());
                                } else {
                                    aeVarA = ae.a((Exception) new ax());
                                }
                                a(acVarTake, ahVarA, aeVarA);
                            } catch (Exception e) {
                                e = e;
                                a(acVarTake, ahVarA, ae.a(e));
                            }
                        } catch (Throwable th) {
                            th = th;
                            a(acVarTake, ahVarA, null);
                            throw th;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        ahVarA = null;
                    } catch (Throwable th2) {
                        th = th2;
                        ahVarA = null;
                    }
                }
            } catch (InterruptedException e3) {
            }
        }
    }

    private ah a(ac<?> acVar) throws IOException {
        int i = AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT;
        int i2 = 0;
        while (true) {
            try {
                return a(acVar, i);
            } catch (MalformedURLException e) {
                throw e;
            } catch (SocketTimeoutException e2) {
                if (i2 < 1) {
                    i *= 2;
                    i2++;
                } else {
                    throw e2;
                }
            } catch (IOException e3) {
                throw new av(e3);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:127:0x009a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:131:0x013d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:141:0x014e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:149:? A[Catch: all -> 0x003b, SYNTHETIC, TRY_ENTER, TryCatch #17 {all -> 0x003b, blocks: (B:4:0x001d, B:5:0x0025, B:7:0x002b, B:12:0x0040, B:14:0x004f, B:16:0x0053, B:18:0x005b, B:19:0x0062, B:24:0x0075, B:31:0x008c, B:30:0x0088, B:39:0x009a, B:43:0x00a3, B:42:0x009f, B:40:0x009d, B:25:0x0078, B:27:0x007f, B:28:0x0086, B:44:0x00a7, B:46:0x00ad, B:48:0x00b1, B:58:0x00f1, B:93:0x0160, B:92:0x015c, B:59:0x00f4, B:61:0x00fc, B:63:0x0102, B:64:0x012e, B:97:0x016d, B:98:0x0199, B:104:0x01ae, B:74:0x013d, B:96:0x0169, B:95:0x0165, B:75:0x0140, B:103:0x01ab, B:113:0x01c4, B:114:0x01c7, B:115:0x01c8, B:99:0x019a, B:101:0x01a0, B:109:0x01bd, B:108:0x01b8), top: B:143:0x001d, inners: #0, #1, #4, #6, #9 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x0098 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:43:0x00a3 A[Catch: all -> 0x003b, TryCatch #17 {all -> 0x003b, blocks: (B:4:0x001d, B:5:0x0025, B:7:0x002b, B:12:0x0040, B:14:0x004f, B:16:0x0053, B:18:0x005b, B:19:0x0062, B:24:0x0075, B:31:0x008c, B:30:0x0088, B:39:0x009a, B:43:0x00a3, B:42:0x009f, B:40:0x009d, B:25:0x0078, B:27:0x007f, B:28:0x0086, B:44:0x00a7, B:46:0x00ad, B:48:0x00b1, B:58:0x00f1, B:93:0x0160, B:92:0x015c, B:59:0x00f4, B:61:0x00fc, B:63:0x0102, B:64:0x012e, B:97:0x016d, B:98:0x0199, B:104:0x01ae, B:74:0x013d, B:96:0x0169, B:95:0x0165, B:75:0x0140, B:103:0x01ab, B:113:0x01c4, B:114:0x01c7, B:115:0x01c8, B:99:0x019a, B:101:0x01a0, B:109:0x01bd, B:108:0x01b8), top: B:143:0x001d, inners: #0, #1, #4, #6, #9 }] */
    /* JADX WARN: Code duplicated, block: B:73:0x013b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:84:0x014c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:89:0x0157 A[Catch: Throwable -> 0x0134, all -> 0x0145, TRY_LEAVE, TryCatch #3 {all -> 0x0145, blocks: (B:50:0x00dd, B:55:0x00ea, B:76:0x0141, B:66:0x0130, B:85:0x014e, B:89:0x0157, B:88:0x0153, B:86:0x0151), top: B:126:0x00dd }] */
    /* JADX WARN: Code duplicated, block: B:96:0x0169 A[Catch: all -> 0x003b, TryCatch #17 {all -> 0x003b, blocks: (B:4:0x001d, B:5:0x0025, B:7:0x002b, B:12:0x0040, B:14:0x004f, B:16:0x0053, B:18:0x005b, B:19:0x0062, B:24:0x0075, B:31:0x008c, B:30:0x0088, B:39:0x009a, B:43:0x00a3, B:42:0x009f, B:40:0x009d, B:25:0x0078, B:27:0x007f, B:28:0x0086, B:44:0x00a7, B:46:0x00ad, B:48:0x00b1, B:58:0x00f1, B:93:0x0160, B:92:0x015c, B:59:0x00f4, B:61:0x00fc, B:63:0x0102, B:64:0x012e, B:97:0x016d, B:98:0x0199, B:104:0x01ae, B:74:0x013d, B:96:0x0169, B:95:0x0165, B:75:0x0140, B:103:0x01ab, B:113:0x01c4, B:114:0x01c7, B:115:0x01c8, B:99:0x019a, B:101:0x01a0, B:109:0x01bd, B:108:0x01b8), top: B:143:0x001d, inners: #0, #1, #4, #6, #9 }] */
    /* JADX WARN: Multi-variable type inference failed */
    private ah a(ac<?> acVar, int i) throws IOException {
        byte[] bArrB;
        InputStream errorStream;
        Throwable th;
        Throwable th2;
        Throwable th3;
        Throwable th4 = null;
        Object[] objArr = 0;
        aq aqVarA = acVar.a();
        Map<String, String> map = aqVarA.a;
        HttpURLConnection httpURLConnectionA = this.a.a(acVar);
        httpURLConnectionA.setConnectTimeout(i);
        httpURLConnectionA.setReadTimeout(i);
        httpURLConnectionA.setUseCaches(false);
        httpURLConnectionA.setDoInput(true);
        if (map != null) {
            try {
                for (String str : map.keySet()) {
                    httpURLConnectionA.addRequestProperty(str, map.get(str));
                }
            } catch (Throwable th5) {
                httpURLConnectionA.disconnect();
                throw th5;
            }
        }
        httpURLConnectionA.setRequestMethod(acVar.b.toString());
        if (acVar.b == ac.a.POST && aqVarA.b != null) {
            httpURLConnectionA.setDoOutput(true);
            if (aqVarA.c != null) {
                httpURLConnectionA.addRequestProperty("Content-Type", aqVarA.c);
            }
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnectionA.getOutputStream());
            Throwable th6 = null;
            try {
                dataOutputStream.write(aqVarA.b);
                if (dataOutputStream != null) {
                    if (0 != 0) {
                        try {
                            dataOutputStream.close();
                        } catch (Throwable th7) {
                            th6.addSuppressed(th7);
                        }
                    } else {
                        dataOutputStream.close();
                    }
                }
            } catch (Throwable th8) {
                try {
                    throw th8;
                } catch (Throwable th9) {
                    th2 = th8;
                    th3 = th9;
                    if (dataOutputStream != null) {
                        throw th3;
                    }
                    if (th2 == null) {
                        dataOutputStream.close();
                        throw th3;
                    }
                    dataOutputStream.close();
                    throw th3;
                }
            }
        }
        int responseCode = httpURLConnectionA.getResponseCode();
        if (responseCode == -1) {
            throw new av("Could not retrieve response code from HttpUrlConnection.");
        }
        if (a(responseCode)) {
            try {
                if (acVar.f != null) {
                    File file = new File(acVar.f.getParentFile(), acVar.f.getName() + ".tmp");
                    bArrB = new byte[0];
                    InputStream inputStream = httpURLConnectionA.getInputStream();
                    Throwable th10 = null;
                    try {
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            Throwable th11 = null;
                            try {
                                br.a(inputStream, fileOutputStream);
                                if (fileOutputStream != null) {
                                    if (0 != 0) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Throwable th12) {
                                            th11.addSuppressed(th12);
                                        }
                                    } else {
                                        fileOutputStream.close();
                                    }
                                }
                                if (inputStream != null) {
                                    if (0 != 0) {
                                        try {
                                            inputStream.close();
                                        } catch (Throwable th13) {
                                            th10.addSuppressed(th13);
                                        }
                                    } else {
                                        inputStream.close();
                                    }
                                }
                                if (!file.renameTo(acVar.f)) {
                                    if (file.delete()) {
                                        throw new IOException("Unable to move " + file.getAbsolutePath() + " to " + acVar.f.getAbsolutePath());
                                    }
                                    throw new IOException("Unable to delete " + file.getAbsolutePath() + " after failing to rename to " + acVar.f.getAbsolutePath());
                                }
                            } catch (Throwable th14) {
                                th = th14;
                                try {
                                    throw th;
                                } catch (Throwable th15) {
                                    th = th15;
                                    if (fileOutputStream != null) {
                                        if (th != null) {
                                            fileOutputStream.close();
                                        } else {
                                            fileOutputStream.close();
                                        }
                                    }
                                    throw th;
                                }
                            }
                        } catch (Throwable th16) {
                            th = th16;
                            if (inputStream != null) {
                                if (th4 != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Throwable th17) {
                                        th4.addSuppressed(th17);
                                    }
                                } else {
                                    inputStream.close();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th18) {
                        try {
                            throw th18;
                        } catch (Throwable th19) {
                            th4 = th18;
                            th = th19;
                            if (inputStream != null) {
                                if (th4 != null) {
                                    inputStream.close();
                                } else {
                                    inputStream.close();
                                }
                            }
                            throw th;
                        }
                    }
                } else {
                    try {
                        errorStream = httpURLConnectionA.getInputStream();
                    } catch (IOException e) {
                        errorStream = httpURLConnectionA.getErrorStream();
                    }
                    bArrB = errorStream != null ? br.b(new BufferedInputStream(errorStream)) : new byte[0];
                    if (errorStream != null) {
                        errorStream.close();
                    }
                }
            } catch (Throwable th20) {
                if (0 != 0) {
                    (objArr == true ? 1 : 0).close();
                }
                throw th20;
            }
        } else {
            bArrB = new byte[0];
        }
        au auVar = new au(responseCode, bArrB);
        httpURLConnectionA.disconnect();
        return auVar;
    }

    private static boolean a(int i) {
        return ((100 <= i && i < 200) || i == 204 || i == 304) ? false : true;
    }

    private <T> void a(ac<T> acVar, ah ahVar, ae<T> aeVar) {
        a aVar = new a(acVar, ahVar, aeVar);
        switch (acVar.h) {
            case 0:
                this.c.post(aVar);
                break;
            case 1:
                aa.a().execute(aVar);
                break;
        }
    }

    private class a<T> implements Runnable {
        private final ac<T> b;
        private final ah c;
        private final ae<T> d;

        public a(ac<T> acVar, ah ahVar, ae<T> aeVar) {
            this.b = acVar;
            this.c = ahVar;
            this.d = aeVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (this.d.a()) {
                    this.b.a(this.d.a);
                } else {
                    this.b.a(new ad(this.c, this.d.b));
                }
            } catch (Exception e) {
                com.chartboost.sdk.Tracking.a.a(a.class, "run", e);
            }
        }
    }
}
