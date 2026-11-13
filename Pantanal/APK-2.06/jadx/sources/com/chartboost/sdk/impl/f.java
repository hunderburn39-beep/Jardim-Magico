package com.chartboost.sdk.impl;

import android.os.Handler;
import com.chartboost.sdk.Model.CBError;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class f implements Runnable {
    final com.chartboost.sdk.Model.a a;
    final JSONObject b;
    private final File d;
    private final Map<String, String> e;
    private final Handler f;
    private String g = null;
    CBError.CBImpressionError c = null;

    f(com.chartboost.sdk.Model.a aVar, JSONObject jSONObject, File file, Map<String, String> map, Handler handler) {
        this.a = aVar;
        this.b = jSONObject;
        this.d = file;
        this.e = map;
        this.f = handler;
    }

    /* JADX WARN: Code duplicated, block: B:105:0x00c3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:125:? A[Catch: Throwable -> 0x00b9, all -> 0x0155, SYNTHETIC, TRY_ENTER, TRY_LEAVE, TryCatch #9 {all -> 0x0155, blocks: (B:16:0x0042, B:71:0x0135, B:85:0x0159, B:82:0x0151, B:41:0x00b5, B:88:0x0163, B:87:0x015e, B:42:0x00b8), top: B:107:0x0042 }] */
    /* JADX WARN: Code duplicated, block: B:128:? A[Catch: OutOfMemoryError -> 0x00c7, Exception -> 0x0145, SYNTHETIC, TRY_ENTER, TRY_LEAVE, TryCatch #14 {Exception -> 0x0145, OutOfMemoryError -> 0x00c7, blocks: (B:14:0x003a, B:74:0x013c, B:90:0x0168, B:77:0x0141, B:49:0x00c3, B:93:0x0173, B:92:0x016e, B:50:0x00c6), top: B:112:0x003a, inners: #3, #8 }] */
    /* JADX WARN: Code duplicated, block: B:40:0x00b3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:48:0x00c1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:55:0x00da  */
    /* JADX WARN: Code duplicated, block: B:88:0x0163 A[Catch: Throwable -> 0x00b9, all -> 0x0155, TRY_LEAVE, TryCatch #9 {all -> 0x0155, blocks: (B:16:0x0042, B:71:0x0135, B:85:0x0159, B:82:0x0151, B:41:0x00b5, B:88:0x0163, B:87:0x015e, B:42:0x00b8), top: B:107:0x0042 }] */
    /* JADX WARN: Code duplicated, block: B:93:0x0173 A[Catch: OutOfMemoryError -> 0x00c7, Exception -> 0x0145, TRY_LEAVE, TryCatch #14 {Exception -> 0x0145, OutOfMemoryError -> 0x00c7, blocks: (B:14:0x003a, B:74:0x013c, B:90:0x0168, B:77:0x0141, B:49:0x00c3, B:93:0x0173, B:92:0x016e, B:50:0x00c6), top: B:112:0x003a, inners: #3, #8 }] */
    /* JADX WARN: Code duplicated, block: B:97:0x00b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    public void run() throws Throwable {
        Throwable th;
        Throwable th2;
        Throwable th3;
        Throwable th4;
        if (this.c != null) {
            try {
                this.a.q().a(this.a, this.c);
                return;
            } catch (Exception e) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "run[post error]", e);
                return;
            }
        }
        if (this.g != null) {
            try {
                this.a.q().a(this.a, this.g);
                return;
            } catch (Exception e2) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "run[post success]", e2);
                return;
            }
        }
        try {
            FileReader fileReader = new FileReader(this.d);
            Throwable th5 = null;
            try {
                try {
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    Throwable th6 = null;
                    try {
                        Set<Map.Entry<String, String>> setEntrySet = this.e.entrySet();
                        Iterator<Map.Entry<String, String>> it = setEntrySet.iterator();
                        int length = 0;
                        while (it.hasNext()) {
                            length = (it.next().getValue().length() * 3) + length;
                        }
                        StringBuilder sb = new StringBuilder(((int) this.d.length()) + length);
                        StringBuilder sb2 = new StringBuilder(2048);
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            int iIndexOf = line.indexOf("{{");
                            int iIndexOf2 = line.indexOf("{%");
                            int iMax = (iIndexOf == -1 || iIndexOf2 == -1) ? Math.max(iIndexOf, iIndexOf2) : Math.min(iIndexOf, iIndexOf2);
                            if (iMax == -1) {
                                sb.append(line);
                            } else {
                                sb2.setLength(0);
                                sb2.append(line);
                                for (Map.Entry<String, String> entry : setEntrySet) {
                                    String key = entry.getKey();
                                    String value = entry.getValue();
                                    int length2 = key.length();
                                    while (true) {
                                        iMax = sb2.indexOf(key, iMax);
                                        if (-1 != iMax) {
                                            sb2.replace(iMax, iMax + length2, value);
                                            iMax += value.length();
                                        }
                                    }
                                }
                                sb.append((CharSequence) sb2);
                            }
                            sb.append("\n");
                        }
                        this.g = sb.toString();
                        if (bufferedReader != null) {
                            if (0 != 0) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th7) {
                                    th6.addSuppressed(th7);
                                }
                            } else {
                                bufferedReader.close();
                            }
                        }
                        if (fileReader != null) {
                            if (0 != 0) {
                                try {
                                    fileReader.close();
                                } catch (Throwable th8) {
                                    th5.addSuppressed(th8);
                                }
                            } else {
                                fileReader.close();
                            }
                        }
                        if (this.g == null) {
                            this.c = CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW;
                        }
                        this.f.post(this);
                    } catch (Throwable th9) {
                        try {
                            throw th9;
                        } catch (Throwable th10) {
                            th3 = th9;
                            th4 = th10;
                            if (bufferedReader != null) {
                                throw th4;
                            }
                            if (th3 == null) {
                                bufferedReader.close();
                                throw th4;
                            }
                            bufferedReader.close();
                            throw th4;
                        }
                    }
                } catch (Throwable th11) {
                    th2 = th11;
                    th = null;
                    if (fileReader != null) {
                        throw th2;
                    }
                    if (th == null) {
                        fileReader.close();
                        throw th2;
                    }
                    try {
                        fileReader.close();
                        throw th2;
                    } catch (Throwable th12) {
                        th.addSuppressed(th12);
                        throw th2;
                    }
                    if (this.g == null) {
                        this.c = CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW;
                    }
                    this.f.post(this);
                }
            } catch (Throwable th13) {
                try {
                    throw th13;
                } catch (Throwable th14) {
                    th = th13;
                    th2 = th14;
                    if (fileReader != null) {
                        throw th2;
                    }
                    if (th == null) {
                        fileReader.close();
                        throw th2;
                    }
                    fileReader.close();
                    throw th2;
                    if (this.g == null) {
                        this.c = CBError.CBImpressionError.ERROR_LOADING_WEB_VIEW;
                    }
                    this.f.post(this);
                }
            }
        } catch (Exception e3) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "run[format html]", e3);
        } catch (OutOfMemoryError e4) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "run[oom]", new Exception(e4));
        }
    }
}
