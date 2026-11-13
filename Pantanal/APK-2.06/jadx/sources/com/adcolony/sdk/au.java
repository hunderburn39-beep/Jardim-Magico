package com.adcolony.sdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import com.adcolony.adcolonysdk.BuildConfig;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.CRC32;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
class au {
    static final int a = 128;
    static ExecutorService b = Executors.newSingleThreadExecutor();

    au() {
    }

    static boolean a(String str) {
        if (com.adcolony.sdk.a.l == null) {
            return false;
        }
        try {
            com.adcolony.sdk.a.l.getApplication().getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static String a() {
        if (com.adcolony.sdk.a.l == null) {
            return BuildConfig.VERSION_NAME;
        }
        try {
            return com.adcolony.sdk.a.l.getPackageManager().getPackageInfo(com.adcolony.sdk.a.l.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            u.h.b("Failed to retrieve package info.");
            return BuildConfig.VERSION_NAME;
        }
    }

    static int b(String str) {
        CRC32 crc32 = new CRC32();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            crc32.update(str.charAt(i));
        }
        return (int) crc32.getValue();
    }

    static String c(String str) {
        try {
            return bb.a(str);
        } catch (Exception e) {
            return null;
        }
    }

    static String b() {
        return UUID.randomUUID().toString();
    }

    static JSONArray a(int i) {
        JSONArray jSONArrayB = s.b();
        for (int i2 = 0; i2 < i; i2++) {
            s.a(jSONArrayB, b());
        }
        return jSONArrayB;
    }

    static boolean a(Runnable runnable) {
        if (com.adcolony.sdk.a.l == null) {
            return false;
        }
        com.adcolony.sdk.a.l.runOnUiThread(runnable);
        return true;
    }

    static double c() {
        return System.currentTimeMillis() / 1000.0d;
    }

    static boolean d(String str) {
        if (str != null && str.length() < 128) {
            return true;
        }
        u.e.b("String must be non-null and the max length is 128 characters.");
        return false;
    }

    static boolean a(AudioManager audioManager) {
        if (audioManager != null) {
            return audioManager.getStreamVolume(3) > 0;
        }
        u.h.b("is_audio_enabled() called with a null AudioManager");
        return false;
    }

    static AudioManager a(Context context) {
        return (AudioManager) context.getSystemService("audio");
    }

    static void e(String str) {
        File[] fileArrListFiles = new File(str).listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    u.b.a(">").b(file.getAbsolutePath());
                    e(file.getAbsolutePath());
                } else {
                    u.b.b(file.getAbsolutePath());
                }
            }
        }
    }

    static String a(double d, int i) {
        StringBuilder sb = new StringBuilder();
        a(d, i, sb);
        return sb.toString();
    }

    static void a(double d, int i, StringBuilder sb) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            sb.append(d);
            return;
        }
        if (d < 0.0d) {
            d = -d;
            sb.append('-');
        }
        if (i == 0) {
            sb.append(Math.round(d));
            return;
        }
        long jPow = (long) Math.pow(10.0d, i);
        long jRound = Math.round(jPow * d);
        sb.append(jRound / jPow);
        sb.append('.');
        long j = jRound % jPow;
        if (j == 0) {
            for (int i2 = 0; i2 < i; i2++) {
                sb.append('0');
            }
            return;
        }
        for (long j2 = j * 10; j2 < jPow; j2 *= 10) {
            sb.append('0');
        }
        sb.append(j);
    }

    static String f(String str) {
        return str == null ? "" : URLDecoder.decode(str);
    }

    static String d() {
        return com.adcolony.sdk.a.l == null ? "" : com.adcolony.sdk.a.l.getPackageName();
    }

    static String a(Exception exc) {
        StringWriter stringWriter = new StringWriter();
        exc.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    static int g(String str) {
        try {
            return (int) Long.parseLong(str, 16);
        } catch (NumberFormatException e) {
            u.f.b("Unable to parse '" + str + "' as a color.");
            return -16777216;
        }
    }

    static int e() {
        int identifier;
        if (com.adcolony.sdk.a.l != null && (identifier = com.adcolony.sdk.a.l.getResources().getIdentifier("status_bar_height", "dimen", TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE)) > 0) {
            return com.adcolony.sdk.a.l.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    static boolean f() {
        return com.adcolony.sdk.a.l != null && Build.VERSION.SDK_INT >= 24 && com.adcolony.sdk.a.l.isInMultiWindowMode();
    }

    static boolean a(String str, File file) {
        boolean zEquals = false;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[8192];
                while (true) {
                    try {
                        try {
                            int i = fileInputStream.read(bArr);
                            if (i <= 0) {
                                break;
                            }
                            messageDigest.update(bArr, 0, i);
                        } catch (Throwable th) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                                u.h.b("Exception on closing MD5 input stream");
                            }
                            throw th;
                        }
                    } catch (IOException e2) {
                        throw new RuntimeException("Unable to process file for MD5", e2);
                    }
                }
                zEquals = str.equals(String.format("%40s", new BigInteger(1, messageDigest.digest()).toString(16)).replace(' ', '0'));
                try {
                    fileInputStream.close();
                } catch (IOException e3) {
                    u.h.b("Exception on closing MD5 input stream");
                }
            } catch (FileNotFoundException e4) {
                u.h.b("Exception while getting FileInputStream");
            }
        } catch (NoSuchAlgorithmException e5) {
            u.h.b("Exception while getting Digest");
        }
        return zEquals;
    }

    static class a {
        long a = System.currentTimeMillis();

        a() {
        }

        void a() {
            this.a = System.currentTimeMillis();
        }

        double b() {
            return (System.currentTimeMillis() - this.a) / 1000.0d;
        }

        public String toString() {
            return au.a(b(), 2);
        }
    }

    static class b {
        double a;
        double b = System.currentTimeMillis();

        b(double d) {
            a(d);
        }

        void a() {
            a(this.a);
        }

        void a(double d) {
            this.a = d;
            this.b = (System.currentTimeMillis() / 1000.0d) + this.a;
        }

        boolean b() {
            return c() == 0.0d;
        }

        double c() {
            double dCurrentTimeMillis = this.b - (System.currentTimeMillis() / 1000.0d);
            if (dCurrentTimeMillis <= 0.0d) {
                return 0.0d;
            }
            return dCurrentTimeMillis;
        }

        public String toString() {
            return au.a(c(), 2);
        }
    }
}
