package com.chartboost.sdk.impl;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class am {
    final af a;
    final ak b;
    final com.chartboost.sdk.Libraries.g c;
    private final Map<String, com.chartboost.sdk.Libraries.i.a> d = new HashMap();

    public interface b {
        void a(com.chartboost.sdk.Libraries.i.a aVar, Bundle bundle);
    }

    public am(af afVar, ak akVar, com.chartboost.sdk.Libraries.g gVar) {
        this.a = afVar;
        this.b = akVar;
        this.c = gVar;
    }

    public void a(String str, String str2, b bVar, ImageView imageView, Bundle bundle) {
        com.chartboost.sdk.Libraries.i.a aVarA = a(str2);
        if (aVarA != null) {
            if (imageView != null) {
                imageView.setImageBitmap(aVarA.a());
            }
            if (bVar != null) {
                bVar.a(aVarA, bundle);
                return;
            }
            return;
        }
        if (str == null && bVar != null) {
            bVar.a(null, bundle);
        }
        aa.a().execute(new a(imageView, bVar, str2, bundle, str, this.d));
    }

    com.chartboost.sdk.Libraries.i.a a(String str) {
        if (b(str)) {
            if (this.d.containsKey(str)) {
                return this.d.get(str);
            }
            com.chartboost.sdk.Libraries.i.a aVar = new com.chartboost.sdk.Libraries.i.a(str, new File(this.c.c(), String.format("%s%s", str, ".png")), this.c);
            this.d.put(str, aVar);
            return aVar;
        }
        if (this.d.containsKey(str)) {
            this.d.remove(str);
        }
        return null;
    }

    private class a implements Runnable {
        final Map<String, com.chartboost.sdk.Libraries.i.a> a;
        final String b;
        final WeakReference<ImageView> c;
        final b d;
        String e;
        final Bundle f;

        public a(ImageView imageView, b bVar, String str, Bundle bundle, String str2, Map<String, com.chartboost.sdk.Libraries.i.a> map) {
            this.a = map;
            this.c = new WeakReference<>(imageView);
            c cVar = new c(this);
            if (imageView != null) {
                imageView.setImageDrawable(cVar);
            }
            this.e = str;
            this.d = bVar;
            this.f = bundle;
            this.b = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (am.this.b(this.e)) {
                    a();
                } else {
                    ab abVar = new ab() { // from class: com.chartboost.sdk.impl.am.a.1
                        @Override // com.chartboost.sdk.impl.ab
                        public void a(ad adVar) {
                            CBLogging.b("CBWebImageCache", "Error downloading the bitmap image from the server");
                            if (adVar != null && !TextUtils.isEmpty(adVar.a())) {
                                CBLogging.b("CBWebImageCache", adVar.a());
                            }
                            if (adVar != null && adVar.a != null) {
                                CBLogging.b("CBWebImageCache", "Error status Code: " + adVar.a.b());
                            }
                        }
                    };
                    CBLogging.a("CBWebImageCache", "downloading image to cache... " + this.b);
                    am.this.a.a(new ac<String>(ac.a.GET, this.b, abVar, null) { // from class: com.chartboost.sdk.impl.am.a.2
                        @Override // com.chartboost.sdk.impl.ac
                        public ae<String> a(ah ahVar) {
                            try {
                                byte[] bArrA = ahVar.a();
                                if (bArrA.length > 0) {
                                    String strB = com.chartboost.sdk.Libraries.b.b(com.chartboost.sdk.Libraries.b.a(bArrA));
                                    if (TextUtils.isEmpty(strB)) {
                                        strB = "";
                                    }
                                    if (!strB.equals(a.this.e)) {
                                        a.this.e = strB;
                                        CBLogging.b("CBWebImageCache:ImageDownloader", "Error: checksum did not match while downloading from " + a.this.b);
                                    }
                                    am.this.c.a(am.this.c.c(), String.format("%s%s", a.this.e, ".png"), bArrA);
                                    am.this.a(a.this.e);
                                    return ae.b();
                                }
                                return ae.a((Exception) new RuntimeException("Bitmap response data is empty, unable to download the bitmap"));
                            } catch (Exception e) {
                                com.chartboost.sdk.Tracking.a.a(a.class, "parseServerResponse", e);
                                return ae.a((Exception) new RuntimeException("Bitmap response data is empty, unable to download the bitmap"));
                            }
                        }

                        @Override // com.chartboost.sdk.impl.ac
                        public void a(String str) {
                            a.this.a();
                        }

                        @Override // com.chartboost.sdk.impl.ac
                        public aq a() {
                            return new aq(am.this.b.a(), null, null);
                        }
                    });
                }
            } catch (Exception e) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "run", e);
            }
        }

        public void a() {
            ImageView imageView;
            final com.chartboost.sdk.Libraries.i.a aVar = this.a.get(this.e);
            if (aVar != null && (imageView = this.c.get()) != null && this == am.a(imageView)) {
                aVar.b();
            }
            CBUtility.c().post(new Runnable() { // from class: com.chartboost.sdk.impl.am.a.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ImageView imageView2 = a.this.c.get();
                        if (imageView2 != null) {
                            a aVarA = am.a(imageView2);
                            if (aVar != null && a.this == aVarA) {
                                imageView2.setImageBitmap(aVar.a());
                            }
                        }
                        if (a.this.d != null) {
                            a.this.d.a(aVar, a.this.f);
                        }
                    } catch (Exception e) {
                        com.chartboost.sdk.Tracking.a.a(a.class, "finishOnUiThread Runnable.run", e);
                    }
                }
            });
        }
    }

    static a a(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof c) {
                return ((c) drawable).a();
            }
        }
        return null;
    }

    static class c extends BitmapDrawable {
        private final WeakReference<a> a;

        public c(a aVar) {
            this.a = new WeakReference<>(aVar);
        }

        public a a() {
            return this.a.get();
        }
    }

    boolean b(String str) {
        return this.c.b(String.format("%s%s", str, ".png"));
    }
}
