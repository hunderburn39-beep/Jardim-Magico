package com.chartboost.sdk.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.google.android.gms.drive.DriveFile;
import com.heyzap.http.AsyncHttpClient;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class al {
    final ai a;
    private final a b;
    private com.chartboost.sdk.Model.a c;

    public interface a {
        void a(com.chartboost.sdk.Model.a aVar, boolean z, String str, CBError.CBClickError cBClickError, com.chartboost.sdk.c.a aVar2);
    }

    public al(a aVar, ai aiVar) {
        this.a = aiVar;
        this.b = aVar;
    }

    public a a() {
        return this.b;
    }

    public void a(com.chartboost.sdk.Model.a aVar, final String str, final Activity activity, final com.chartboost.sdk.c.a aVar2) {
        this.c = aVar;
        try {
            String scheme = new URI(str).getScheme();
            if (scheme == null) {
                if (this.b != null) {
                    this.b.a(aVar, false, str, CBError.CBClickError.URI_INVALID, aVar2);
                }
            } else if (!scheme.equals("http") && !scheme.equals("https")) {
                a(str, activity, aVar2);
            } else {
                aa.a().execute(new Runnable() { // from class: com.chartboost.sdk.impl.al.1
                    /* JADX WARN: Code duplicated, block: B:27:0x0063  */
                    @Override // java.lang.Runnable
                    public void run() throws Throwable {
                        String str2;
                        try {
                            String str3 = str;
                            if (al.this.a.b()) {
                                HttpURLConnection httpURLConnection = null;
                                try {
                                    try {
                                        HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
                                        try {
                                            httpURLConnection2.setInstanceFollowRedirects(false);
                                            httpURLConnection2.setConnectTimeout(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
                                            httpURLConnection2.setReadTimeout(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
                                            String headerField = httpURLConnection2.getHeaderField("Location");
                                            if (headerField == null) {
                                                headerField = str3;
                                            }
                                            if (httpURLConnection2 != null) {
                                                httpURLConnection2.disconnect();
                                                str2 = headerField;
                                            } else {
                                                str2 = headerField;
                                            }
                                        } catch (Exception e) {
                                            httpURLConnection = httpURLConnection2;
                                            e = e;
                                            CBLogging.b("CBURLOpener", "Exception raised while opening a HTTP Conection", e);
                                            if (httpURLConnection != null) {
                                                httpURLConnection.disconnect();
                                                str2 = str3;
                                            } else {
                                                str2 = str3;
                                            }
                                        } catch (Throwable th) {
                                            httpURLConnection = httpURLConnection2;
                                            th = th;
                                            if (httpURLConnection != null) {
                                                httpURLConnection.disconnect();
                                            }
                                            throw th;
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            } else {
                                str2 = str3;
                            }
                            a(str2);
                        } catch (Exception e3) {
                            com.chartboost.sdk.Tracking.a.a(al.class, "open followTask", e3);
                        }
                    }

                    public void a(final String str2) {
                        Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.impl.al.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    al.this.a(str2, activity, aVar2);
                                } catch (Exception e) {
                                    com.chartboost.sdk.Tracking.a.a(al.class, "open openOnUiThread Runnable.run", e);
                                }
                            }
                        };
                        if (activity != null) {
                            activity.runOnUiThread(runnable);
                        } else {
                            CBUtility.c().post(runnable);
                        }
                    }
                });
            }
        } catch (URISyntaxException e) {
            if (this.b != null) {
                this.b.a(aVar, false, str, CBError.CBClickError.URI_INVALID, aVar2);
            }
        }
    }

    void a(String str, Context context, com.chartboost.sdk.c.a aVar) {
        String str2;
        if (this.c != null && this.c.a()) {
            this.c.d = com.chartboost.sdk.Model.a.e.NONE;
        }
        if (context == null) {
            context = com.chartboost.sdk.i.m;
        }
        if (context == null) {
            if (this.b != null) {
                this.b.a(this.c, false, str, CBError.CBClickError.NO_HOST_ACTIVITY, aVar);
                return;
            }
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            if (!(context instanceof Activity)) {
                intent.addFlags(DriveFile.MODE_READ_ONLY);
            }
            intent.setData(Uri.parse(str));
            context.startActivity(intent);
            str2 = str;
        } catch (Exception e) {
            if (str.startsWith("market://")) {
                try {
                    str = "http://market.android.com/" + str.substring(9);
                    Intent intent2 = new Intent("android.intent.action.VIEW");
                    if (!(context instanceof Activity)) {
                        intent2.addFlags(DriveFile.MODE_READ_ONLY);
                    }
                    intent2.setData(Uri.parse(str));
                    context.startActivity(intent2);
                    str2 = str;
                } catch (Exception e2) {
                    String str3 = str;
                    CBLogging.b("CBURLOpener", "Exception raised openeing an inavld playstore URL", e2);
                    if (this.b != null) {
                        this.b.a(this.c, false, str3, CBError.CBClickError.URI_UNRECOGNIZED, aVar);
                        return;
                    }
                    return;
                }
            } else {
                if (this.b != null) {
                    this.b.a(this.c, false, str, CBError.CBClickError.URI_UNRECOGNIZED, aVar);
                }
                str2 = str;
            }
        }
        if (this.b != null) {
            this.b.a(this.c, true, str2, null, aVar);
        }
    }

    public static boolean a(String str) {
        try {
            Context context = com.chartboost.sdk.i.m;
            Intent intent = new Intent("android.intent.action.VIEW");
            if (!(context instanceof Activity)) {
                intent.addFlags(DriveFile.MODE_READ_ONLY);
            }
            intent.setData(Uri.parse(str));
            return context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
        } catch (Exception e) {
            CBLogging.b("CBURLOpener", "Cannot open URL", e);
            com.chartboost.sdk.Tracking.a.a(al.class, "canOpenURL", e);
            return false;
        }
    }
}
