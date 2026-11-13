package com.adcolony.sdk;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
class bc {
    private static final String a = "INSTALLATION";
    private static String b = null;

    bc() {
    }

    public static synchronized String a(Context context) {
        if (b == null) {
            File file = new File(context.getFilesDir(), a);
            try {
                if (!file.exists()) {
                    b(file);
                }
                b = a(file);
            } catch (Exception e) {
                throw new az(e.toString());
            }
        }
        return b;
    }

    private static String a(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        byte[] bArr = new byte[(int) randomAccessFile.length()];
        randomAccessFile.readFully(bArr);
        randomAccessFile.close();
        return new String(bArr);
    }

    private static void b(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(UUID.randomUUID().toString().getBytes());
        fileOutputStream.close();
    }
}
