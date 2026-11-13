package com.tapjoy.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import com.tapjoy.TJContentActivity;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class fz extends gb {
    public static fz a;
    final fu b;
    final String c;
    final gq d;
    e e;
    long f;
    boolean g = false;
    private boolean l;
    private Context m;

    public fz(fu fuVar, String str, gq gqVar, Context context) {
        this.b = fuVar;
        this.c = str;
        this.d = gqVar;
        this.m = context;
    }

    @Override // com.tapjoy.internal.gb
    public final void a() throws IOException {
        gq gqVar = this.d;
        if (gqVar.a != null) {
            gqVar.a.a();
        }
        if (gqVar.b != null) {
            gqVar.b.a();
        }
        gqVar.c.a();
        if (gqVar.e != null) {
            gqVar.e.a();
        }
        if (gqVar.f != null) {
            gqVar.f.a();
        }
        if (gqVar.m == null || gqVar.m.a == null) {
            return;
        }
        gqVar.m.a.a();
    }

    @Override // com.tapjoy.internal.gb
    public final boolean b() {
        gq gqVar = this.d;
        return (gqVar.c == null || gqVar.c.b == null || (gqVar.m != null && gqVar.m.a != null && gqVar.m.a.b == null) || ((gqVar.b == null || gqVar.f == null || gqVar.b.b == null || gqVar.f.b == null) && (gqVar.a == null || gqVar.e == null || gqVar.a.b == null || gqVar.e.b == null))) ? false : true;
    }

    @Override // com.tapjoy.internal.gb
    public final void a(final fv fvVar, final eq eqVar) {
        Activity activityA = c.a(this.m);
        if (activityA != null && !activityA.isFinishing()) {
            try {
                a(activityA, fvVar, eqVar);
                new Object[1][0] = this.c;
                return;
            } catch (WindowManager.BadTokenException e) {
            }
        }
        Activity activityA2 = fm.a();
        boolean z = (activityA2 == null || (activityA2.getWindow().getAttributes().flags & 1024) == 0) ? false : true;
        try {
            TJContentActivity.start(fu.a().e, new TJContentActivity.AbstractContentProducer() { // from class: com.tapjoy.internal.fz.1
                @Override // com.tapjoy.TJContentActivity.ContentProducer
                public final void show(Activity activity) {
                    try {
                        fz.this.a(activity, fvVar, eqVar);
                    } catch (WindowManager.BadTokenException e2) {
                        fr.b("Failed to show the content for \"{}\" caused by invalid activity", fz.this.c);
                        fvVar.a(fz.this.c, fz.this.k, null);
                    }
                }

                @Override // com.tapjoy.TJContentActivity.AbstractContentProducer, com.tapjoy.TJContentActivity.ContentProducer
                public final void dismiss(Activity activity) {
                    fz.this.c();
                }
            }, z);
            new Object[1][0] = this.c;
        } catch (ActivityNotFoundException e2) {
            if (activityA2 != null && !activityA2.isFinishing()) {
                try {
                    a(activityA2, fvVar, eqVar);
                    new Object[1][0] = this.c;
                    return;
                } catch (WindowManager.BadTokenException e3) {
                    fr.b("Failed to show the content for \"{}\" caused by no registration of TJContentActivity", this.c);
                    fvVar.a(this.c, this.k, null);
                }
            }
            fr.b("Failed to show the content for \"{}\" caused by no registration of TJContentActivity", this.c);
            fvVar.a(this.c, this.k, null);
        }
    }

    final void a(final Activity activity, final fv fvVar, eq eqVar) {
        boolean z;
        cq.a(!this.l);
        this.l = true;
        a = this;
        this.e = new e(activity);
        this.e.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.tapjoy.internal.fz.2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialog) {
                fvVar.d(fz.this.c);
            }
        });
        this.e.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.tapjoy.internal.fz.3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialog) {
                String string;
                fz.a = null;
                gb.a(activity, fz.this.d.g);
                fz.this.b.a(fz.this.d.k, SystemClock.elapsedRealtime() - fz.this.f);
                if (!fz.this.i) {
                    fvVar.a(fz.this.c, fz.this.k, fz.this.d.h);
                }
                if (fz.this.g && fz.this.d.k != null && fz.this.d.k.containsKey("action_id") && (string = fz.this.d.k.get("action_id").toString()) != null && string.length() > 0) {
                    fu fuVar = fz.this.b;
                    if (fuVar.b != null) {
                        gd gdVar = fuVar.b;
                        String strA = gd.a();
                        String strA2 = gdVar.b.a();
                        String strA3 = gdVar.a.a();
                        if (strA3 == null || !strA.equals(strA3)) {
                            gdVar.a.a(strA);
                            strA2 = "";
                        }
                        if (strA2.length() == 0) {
                            strA2 = string;
                        } else if (!strA2.contains(string)) {
                            strA2 = strA2.concat("," + string);
                        }
                        gdVar.b.a(strA2);
                    }
                }
                if (activity instanceof TJContentActivity) {
                    activity.finish();
                }
            }
        });
        this.e.setCanceledOnTouchOutside(false);
        hg hgVar = new hg(activity, this.d, new hh(activity, this.d, new hh.a() { // from class: com.tapjoy.internal.fz.4
            @Override // com.tapjoy.internal.hh.a
            public final void a() {
                fz.this.e.cancel();
            }

            @Override // com.tapjoy.internal.hh.a
            public final void a(go goVar) {
                fz.this.b.a(fz.this.d.k, goVar.b);
                gb.a(activity, goVar.d);
                if (!cr.c(goVar.e)) {
                    fz.this.j.a(activity, goVar.e, cr.b(goVar.f));
                    fz.this.i = true;
                }
                fvVar.a(fz.this.c, goVar.g);
                if (goVar.c) {
                    fz.this.e.dismiss();
                }
            }

            @Override // com.tapjoy.internal.hh.a
            public final void b() {
                fz.this.g = !fz.this.g;
            }
        }));
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.addView(hgVar, new FrameLayout.LayoutParams(-2, -2, 17));
        this.e.setContentView(frameLayout);
        if (Boolean.FALSE.booleanValue()) {
            Window window = this.e.getWindow();
            if (Build.VERSION.SDK_INT != 16 || !"4.1.2".equals(Build.VERSION.RELEASE)) {
                z = true;
            } else if (Boolean.FALSE.equals(a(window.getContext()))) {
                z = false;
            } else {
                window.setFlags(16777216, 16777216);
                z = true;
            }
            if (z) {
                int i = ai.a.b;
                aj ajVar = new aj();
                switch (ai.AnonymousClass1.a[i - 1]) {
                    case 1:
                        al alVar = new al();
                        alVar.a = false;
                        alVar.b = 60.0f;
                        ajVar.a(alVar.a()).a(new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f)).a(new am().a(1.0f).b(0.3f).a());
                        break;
                    case 2:
                        al alVar2 = new al();
                        alVar2.a = false;
                        alVar2.b = -60.0f;
                        ajVar.a(alVar2.a()).a(new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f)).a(new am().a(-0.4f).b(0.3f).a());
                        break;
                    case 3:
                        al alVar3 = new al();
                        alVar3.a = true;
                        alVar3.b = -60.0f;
                        ajVar.a(alVar3.a()).a(new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f)).a(new am().a(0.3f).b(1.0f).a());
                        break;
                    case 4:
                        al alVar4 = new al();
                        alVar4.a = true;
                        alVar4.b = 60.0f;
                        ajVar.a(alVar4.a()).a(new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f)).a(new am().a(0.3f).b(-0.4f).a());
                        break;
                }
                hgVar.startAnimation(ajVar.b().a());
            }
        }
        try {
            this.e.show();
            this.e.getWindow().setLayout(-1, -1);
            if ((activity.getWindow().getAttributes().flags & 1024) != 0) {
                this.e.getWindow().setFlags(1024, 1024);
            }
            this.f = SystemClock.elapsedRealtime();
            this.b.a(this.d.k);
            eqVar.a();
            fvVar.c(this.c);
        } catch (WindowManager.BadTokenException e) {
            throw e;
        }
    }

    public final void c() {
        if (this.e != null) {
            this.e.dismiss();
        }
    }

    private static Boolean a(Context context) {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                Object obj = bundle.get("tapjoy:hardwareAccelerated");
                if (obj instanceof Boolean) {
                    return (Boolean) obj;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }
}
