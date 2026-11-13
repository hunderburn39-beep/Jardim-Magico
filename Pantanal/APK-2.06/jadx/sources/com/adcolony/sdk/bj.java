package com.adcolony.sdk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.drive.DriveFile;
import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
abstract class bj {
    static cl a = null;
    AdColonyPubServicesOverlayActivity b = null;

    abstract boolean a();

    bj() {
    }

    static synchronized cl b() {
        if (a == null) {
            a = new cl();
        }
        return a;
    }

    void a(AdColonyPubServicesOverlayActivity adColonyPubServicesOverlayActivity) {
        this.b = adColonyPubServicesOverlayActivity;
        bv.aN().aE().b();
        if (adColonyPubServicesOverlayActivity == null) {
            bv.aN().aC().b();
        }
    }

    AdColonyPubServicesOverlayActivity c() {
        return this.b;
    }

    private Boolean f() {
        return Boolean.valueOf(bv.aN().ap());
    }

    void d() {
        if (this.b != null) {
            this.b.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.bj.1
                @Override // java.lang.Runnable
                public void run() {
                    bj.this.b.g();
                    bj.this.a(null);
                }
            });
        }
    }

    boolean e() {
        return (!ck.f() || f().booleanValue() || this.b == null) ? false : true;
    }

    void a(String str, boolean z, boolean z2) {
        if (ck.f() && !f().booleanValue() && !e()) {
            a(str, z, z2, -2, null);
        }
    }

    void a(String str, boolean z, boolean z2, int i, String str2) {
        if (bv.aN().D() && ck.f() && !f().booleanValue()) {
            if (a()) {
                String strK = bv.aN().k(str);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(strK));
                if (!e()) {
                    bv.aN().l().startActivity(intent);
                    return;
                }
                return;
            }
            Intent intent2 = new Intent(bv.aN().l(), (Class<?>) AdColonyPubServicesOverlayActivity.class);
            intent2.addFlags(67108864);
            intent2.addFlags(DriveFile.MODE_WRITE_ONLY);
            intent2.addFlags(DriveFile.MODE_READ_ONLY);
            Bundle bundle = new Bundle();
            bundle.putString("catalogPage", str);
            bundle.putBoolean(TJAdUnitConstants.String.CLOSE, z);
            bundle.putBoolean("openedFromToast", z2);
            bundle.putInt("display_type", i);
            bundle.putString("source_id", str2);
            intent2.putExtras(bundle);
            if (!e()) {
                bv.aN().l().startActivity(intent2);
            }
        }
    }

    void a(final int i, final List<Object> list, final boolean z) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bj.2
            @Override // java.lang.Runnable
            public void run() {
                List arrayList;
                if (list == null) {
                    arrayList = new ArrayList();
                } else {
                    arrayList = list;
                }
                if (cl.b().e()) {
                    if (cl.b().c() != null) {
                        cl.b().c().injectCallbackResult(i, ck.a((List<Object>) arrayList));
                    }
                } else if (bv.aN().aC().n().booleanValue()) {
                    bv.aN().aC().a(i, ck.a((List<Object>) arrayList));
                }
                if (z) {
                    bj.this.d();
                }
            }
        });
    }

    void a(int i, List<Object> list) {
        a(i, list, false);
    }
}
