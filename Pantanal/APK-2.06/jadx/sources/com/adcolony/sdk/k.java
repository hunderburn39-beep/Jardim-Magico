package com.adcolony.sdk;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.tapjoy.TJAdUnitConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class k {
    static AlertDialog d;
    z a;
    AlertDialog b;
    boolean c;

    k() {
        a.a("Alert.show", new ac() { // from class: com.adcolony.sdk.k.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (a.l == null) {
                    u.g.b("Null Activity reference, can't build AlertDialog.");
                } else if (s.d(zVar.b, "on_resume")) {
                    k.this.a = zVar;
                } else {
                    k.this.a(zVar);
                }
            }
        });
    }

    void a(final z zVar) {
        final AlertDialog.Builder builder = a.m.m.n() >= 21 ? new AlertDialog.Builder(a.l, R.style.Theme.Material.Dialog.Alert) : new AlertDialog.Builder(a.l, R.style.Theme.DeviceDefault.Dialog);
        String strB = s.b(zVar.b, TJAdUnitConstants.String.MESSAGE);
        String strB2 = s.b(zVar.b, "title");
        String strB3 = s.b(zVar.b, "positive");
        String strB4 = s.b(zVar.b, "negative");
        builder.setMessage(strB);
        builder.setTitle(strB2);
        builder.setPositiveButton(strB3, new DialogInterface.OnClickListener() { // from class: com.adcolony.sdk.k.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                k.this.b = null;
                dialog.dismiss();
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, "positive", true);
                k.this.c = false;
                zVar.a(jSONObjectA).b();
            }
        });
        if (!strB4.equals("")) {
            builder.setNegativeButton(strB4, new DialogInterface.OnClickListener() { // from class: com.adcolony.sdk.k.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    k.this.b = null;
                    dialog.dismiss();
                    JSONObject jSONObjectA = s.a();
                    s.a(jSONObjectA, "positive", false);
                    k.this.c = false;
                    zVar.a(jSONObjectA).b();
                }
            });
        }
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.adcolony.sdk.k.4
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                k.this.b = null;
                k.this.c = false;
            }
        });
        a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.k.5
            @Override // java.lang.Runnable
            public void run() {
                k.this.c = true;
                k.this.b = builder.show();
            }
        });
    }

    void a() {
        if (this.a != null) {
            a(this.a);
            this.a = null;
        }
    }
}
