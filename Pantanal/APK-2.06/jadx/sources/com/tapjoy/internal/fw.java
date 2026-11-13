package com.tapjoy.internal;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.ExploreByTouchHelper;
import android.text.Html;
import android.text.Spanned;
import com.google.android.gms.drive.DriveFile;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyReceiver;

/* JADX INFO: loaded from: classes.dex */
public final class fw extends r {
    private static fw c;

    public static synchronized fw b(Context context) {
        if (c == null) {
            c = new fw(context);
        }
        return c;
    }

    private fw(Context context) {
        super(context, new t() { // from class: com.tapjoy.internal.fw.1
            @Override // com.tapjoy.internal.t
            public final String a(Context context2) {
                return ge.a(context2).b.getString("gcm.senderIds", "");
            }

            @Override // com.tapjoy.internal.t
            public final void a(Context context2, String str) {
                p.a(ge.a(context2).b, "gcm.senderIds", str);
            }

            @Override // com.tapjoy.internal.t
            public final String b(Context context2) {
                return ge.a(context2).b.getString("gcm.regId", "");
            }

            @Override // com.tapjoy.internal.t
            public final void b(Context context2, String str) {
                p.a(ge.a(context2).b, "gcm.regId", str);
            }

            @Override // com.tapjoy.internal.t
            public final boolean c(Context context2) {
                return ge.a(context2).b.getBoolean("gcm.stale", true);
            }

            @Override // com.tapjoy.internal.t
            public final void a(Context context2, boolean z) {
                p.a(ge.a(context2).b, "gcm.stale", z);
            }

            @Override // com.tapjoy.internal.t
            public final int d(Context context2) {
                return ge.a(context2).b.getInt("gcm.appVersion", ExploreByTouchHelper.INVALID_ID);
            }

            @Override // com.tapjoy.internal.t
            public final void a(Context context2, int i) {
                p.a(ge.a(context2).b, "gcm.appVersion", i);
            }

            @Override // com.tapjoy.internal.t
            public final boolean e(Context context2) {
                return ge.a(context2).b.getBoolean("gcm.onServer", false);
            }

            @Override // com.tapjoy.internal.t
            public final void b(Context context2, boolean z) {
                ge.a(context2).a(z);
            }

            @Override // com.tapjoy.internal.t
            public final long f(Context context2) {
                return ge.a(context2).b.getLong("gcm.onServerExpirationTime", 0L);
            }

            @Override // com.tapjoy.internal.t
            public final void a(Context context2, long j) {
                SharedPreferences.Editor editorEdit = ge.a(context2).b.edit();
                editorEdit.putLong("gcm.onServerExpirationTime", j);
                editorEdit.commit();
            }

            @Override // com.tapjoy.internal.t
            public final int g(Context context2) {
                return ge.a(context2).b.getInt("gcm.backoff", 0);
            }

            @Override // com.tapjoy.internal.t
            public final void b(Context context2, int i) {
                p.a(ge.a(context2).b, "gcm.backoff", i);
            }
        });
    }

    final void e(String str) {
        if (str == null || str.length() <= 0) {
            return;
        }
        super.a(this.a);
        super.a(new String[]{str}[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.s
    public final void a(Context context, String str) {
        new Object[1][0] = str;
        fu.a(context).a(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.s
    public final void b(String str) {
        new Object[1][0] = str;
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.s
    public final boolean a(Context context, Intent intent) {
        boolean z;
        Object[] objArr = {intent, intent.getExtras()};
        String stringExtra = intent.getStringExtra("fiverocks");
        if (stringExtra != null) {
            if (!fx.a(context).b.o) {
                String stringExtra2 = intent.getStringExtra("title");
                String stringExtra3 = intent.getStringExtra(TJAdUnitConstants.String.MESSAGE);
                if (stringExtra3 != null) {
                    Bundle extras = intent.getExtras();
                    Object obj = extras.get("rich");
                    Object obj2 = extras.get("sound");
                    Object obj3 = extras.get("important");
                    String string = extras.getString("payload");
                    Object obj4 = extras.get("always");
                    boolean z2 = "true".equals(obj4) || Boolean.TRUE.equals(obj4);
                    Object obj5 = extras.get("repeatable");
                    boolean z3 = "true".equals(obj5) || Boolean.TRUE.equals(obj5);
                    String string2 = extras.getString("placement");
                    int iB = b(extras.get("nid"));
                    if (z2 || !fu.a(context).d()) {
                        String strA = cr.a(stringExtra2);
                        boolean zA = a(obj);
                        boolean zA2 = a(obj2);
                        a(obj3);
                        Notification notificationA = a(context, stringExtra, strA, stringExtra3, zA, zA2, string, string2, iB);
                        fu fuVarA = fu.a(context);
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        fuVarA.b(context);
                        if (fuVarA.f.a(stringExtra, jCurrentTimeMillis, z3)) {
                            ft ftVar = fuVarA.g;
                            gl.c.a aVarA = ftVar.a(gl.i.APP, "push_show");
                            aVarA.a(gl.p.k().c(stringExtra));
                            ftVar.a(aVarA);
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            ((NotificationManager) context.getSystemService("notification")).notify(iB, notificationA);
                        }
                    }
                }
                return true;
            }
            ft ftVar2 = fu.a(context).g;
            gl.c.a aVarA2 = ftVar2.a(gl.i.APP, "push_ignore");
            aVarA2.a(gl.p.k().c(stringExtra));
            ftVar2.a(aVarA2);
            return true;
        }
        return false;
    }

    private static boolean a(Object obj) {
        return Boolean.TRUE.equals(obj) || "true".equals(obj);
    }

    private static int b(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        if (obj instanceof String) {
            try {
                return Integer.parseInt((String) obj);
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.s
    public final void a(int i) {
        new Object[1][0] = Integer.valueOf(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.s
    public final boolean c(String str) {
        new Object[1][0] = str;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.s
    public final boolean d(String str) {
        new Object[1][0] = str;
        return false;
    }

    private static int a(Bundle bundle, String str, Context context) {
        if (bundle != null) {
            Object obj = bundle.get(str);
            if (obj instanceof Integer) {
                int iIntValue = ((Integer) obj).intValue();
                try {
                    if ("drawable".equals(context.getResources().getResourceTypeName(iIntValue))) {
                        return iIntValue;
                    }
                } catch (Resources.NotFoundException e) {
                }
            }
            if (obj != null) {
                Object[] objArr = {str};
                if (fr.a) {
                    ac.a(4, "Tapjoy", "meta-data of {} invalid", objArr);
                }
            }
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r8v2, types: [android.text.Spanned] */
    private static Notification a(Context context, String str, String str2, String str3, boolean z, boolean z2, String str4, String str5, int i) {
        Spanned spannedFromHtml;
        CharSequence applicationLabel;
        Intent intent = new Intent(context.getApplicationContext(), (Class<?>) TapjoyReceiver.class);
        intent.setAction("com.tapjoy.PUSH_CLICK");
        intent.putExtra("com.tapjoy.PUSH_ID", str);
        if (str4 != null) {
            intent.putExtra(Tapjoy.INTENT_EXTRA_PUSH_PAYLOAD, str4);
        }
        if (str5 != null) {
            intent.putExtra("com.tapjoy.PUSH_PLACEMENT", str5);
        }
        int i2 = 134217728;
        if (Build.VERSION.SDK_INT == 19) {
            i2 = DriveFile.MODE_READ_ONLY;
        }
        PendingIntent broadcast = PendingIntent.getBroadcast(context.getApplicationContext(), i, intent, i2);
        if (broadcast == null) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128);
            int length = str2.length();
            CharSequence charSequence = str2;
            if (length == 0) {
                applicationLabel = packageManager.getApplicationLabel(applicationInfo);
            } else if (z) {
                spannedFromHtml = Html.fromHtml(str2);
            }
            if (z) {
                charSequence = spannedFromHtml;
                charSequence = applicationLabel;
                str3 = Html.fromHtml(str3);
            }
            charSequence = spannedFromHtml;
            charSequence = applicationLabel;
            int iA = a(applicationInfo.metaData, "com.tapjoy.notification.icon", context);
            if (iA == 0) {
                iA = applicationInfo.icon != 0 ? applicationInfo.icon : R.drawable.sym_def_app_icon;
            }
            int iA2 = a(applicationInfo.metaData, "com.tapjoy.notification.icon.large", context);
            Bitmap bitmapDecodeResource = iA2 != 0 ? BitmapFactory.decodeResource(context.getResources(), iA2) : null;
            a.d dVar = new a.d(context);
            dVar.r.icon = iA;
            dVar.r.tickerText = charSequence;
            dVar.b = charSequence;
            dVar.c = str3;
            dVar.d = broadcast;
            dVar.r.flags |= 16;
            a.c cVar = new a.c();
            cVar.e = charSequence;
            cVar.a = str3;
            a.d dVarA = dVar.a(cVar);
            if (z2) {
                dVarA.r.defaults = 1;
            }
            if (bitmapDecodeResource != null) {
                dVarA.g = bitmapDecodeResource;
            }
            return a.a.a(dVarA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
