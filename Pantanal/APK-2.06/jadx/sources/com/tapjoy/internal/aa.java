package com.tapjoy.internal;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class aa {
    public static String a(File file, File file2) {
        String string = null;
        String strB = file.exists() ? cr.b(bk.a(file)) : null;
        if (file2 != null && file2.exists()) {
            string = cr.b(bk.a(file2));
        }
        Object[] objArr = {file, file2, strB, string};
        if (strB == null) {
            if (string == null) {
                string = UUID.randomUUID().toString();
                if (file2 != null) {
                    try {
                        bk.a(file2, string);
                    } catch (IOException e) {
                    }
                }
            }
            try {
                bk.a(file, string);
            } catch (IOException e2) {
            }
            if (!string.equals(bk.a(file))) {
            }
            return string;
        }
        if (string == null && file2 != null) {
            try {
                bk.a(file2, strB);
                return strB;
            } catch (IOException e3) {
            }
        }
        return strB;
    }
}
