package com.tapjoy.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public final class bk {
    public static String a(File file, Charset charset) {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            return cz.a(new InputStreamReader(fileInputStream, charset));
        } finally {
            da.a(fileInputStream);
        }
    }

    public static void a(File file, String str) {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            a(fileOutputStream, str);
        } finally {
            da.a(fileOutputStream);
        }
    }

    public static void a(OutputStream outputStream, String str) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, ap.c);
        outputStreamWriter.write(str);
        outputStreamWriter.flush();
    }

    public static String a(File file) {
        try {
            return a(file, ap.c);
        } catch (IOException e) {
            return null;
        }
    }
}
