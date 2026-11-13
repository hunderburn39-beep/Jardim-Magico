package com.tapjoy.internal;

import android.util.Log;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class ac {
    public static void a(String str, String str2, Object... objArr) {
        a(6, str, str2, objArr);
    }

    public static void a(int i, String str, String str2, Throwable th) {
        if (str2 != null) {
            Log.println(i, str, str2);
        }
        if (th != null) {
            Log.println(i, str, Log.getStackTraceString(th));
        }
    }

    public static void a(int i, String str, String str2, Object... objArr) {
        Throwable th;
        hj hjVar;
        if (objArr == null || objArr.length == 0) {
            th = null;
        } else {
            Object obj = objArr[objArr.length - 1];
            th = obj instanceof Throwable ? (Throwable) obj : null;
        }
        if (str2 == null) {
            hjVar = new hj(null, objArr, th);
        } else if (objArr == null) {
            hjVar = new hj(str2);
        } else {
            StringBuffer stringBuffer = new StringBuffer(str2.length() + 50);
            int i2 = 0;
            int i3 = 0;
            while (i2 < objArr.length) {
                int iIndexOf = str2.indexOf("{}", i3);
                if (iIndexOf == -1) {
                    if (i3 == 0) {
                        hjVar = new hj(str2, objArr, th);
                    } else {
                        stringBuffer.append(str2.substring(i3, str2.length()));
                        hjVar = new hj(stringBuffer.toString(), objArr, th);
                    }
                } else {
                    if (iIndexOf != 0 && str2.charAt(iIndexOf + (-1)) == '\\') {
                        if (!(iIndexOf >= 2 && str2.charAt(iIndexOf + (-2)) == '\\')) {
                            i2--;
                            stringBuffer.append(str2.substring(i3, iIndexOf - 1));
                            stringBuffer.append('{');
                            i3 = iIndexOf + 1;
                        } else {
                            stringBuffer.append(str2.substring(i3, iIndexOf - 1));
                            hk.a(stringBuffer, objArr[i2], new HashMap());
                            i3 = iIndexOf + 2;
                        }
                    } else {
                        stringBuffer.append(str2.substring(i3, iIndexOf));
                        hk.a(stringBuffer, objArr[i2], new HashMap());
                        i3 = iIndexOf + 2;
                    }
                    i2++;
                }
            }
            stringBuffer.append(str2.substring(i3, str2.length()));
            if (i2 < objArr.length - 1) {
                hjVar = new hj(stringBuffer.toString(), objArr, th);
            } else {
                hjVar = new hj(stringBuffer.toString(), objArr, null);
            }
        }
        a(i, str, hjVar.b, hjVar.c);
    }
}
