package org.anddev.andengine.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.Scanner;

/* JADX INFO: loaded from: classes.dex */
public class StreamUtils {
    public static final int IO_BUFFER_SIZE = 8192;

    public static final String readFully(InputStream pInputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(pInputStream);
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        return sb.toString();
    }

    public static byte[] streamToBytes(InputStream pInputStream) throws IOException {
        return streamToBytes(pInputStream, -1);
    }

    public static byte[] streamToBytes(InputStream pInputStream, int pReadLimit) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(pReadLimit == -1 ? 8192 : pReadLimit);
        copy(pInputStream, os, pReadLimit);
        return os.toByteArray();
    }

    public static void copy(InputStream pInputStream, OutputStream pOutputStream) throws IOException {
        copy(pInputStream, pOutputStream, -1L);
    }

    public static void copy(InputStream pInputStream, byte[] pData) throws IOException {
        int dataOffset = 0;
        byte[] buf = new byte[8192];
        while (true) {
            int read = pInputStream.read(buf);
            if (read != -1) {
                System.arraycopy(buf, 0, pData, dataOffset, read);
                dataOffset += read;
            } else {
                return;
            }
        }
    }

    public static void copy(InputStream pInputStream, ByteBuffer pByteBuffer) throws IOException {
        byte[] buf = new byte[8192];
        while (true) {
            int read = pInputStream.read(buf);
            if (read != -1) {
                pByteBuffer.put(buf, 0, read);
            } else {
                return;
            }
        }
    }

    public static void copy(InputStream pInputStream, OutputStream pOutputStream, long pByteLimit) throws IOException {
        if (pByteLimit < 0) {
            byte[] buf = new byte[8192];
            while (true) {
                int read = pInputStream.read(buf);
                if (read == -1) {
                    break;
                } else {
                    pOutputStream.write(buf, 0, read);
                }
            }
        } else {
            byte[] buf2 = new byte[8192];
            int bufferReadLimit = Math.min((int) pByteLimit, 8192);
            long pBytesLeftToRead = pByteLimit;
            while (true) {
                int read2 = pInputStream.read(buf2, 0, bufferReadLimit);
                if (read2 != -1) {
                    if (pBytesLeftToRead > read2) {
                        pOutputStream.write(buf2, 0, read2);
                        pBytesLeftToRead -= (long) read2;
                    } else {
                        pOutputStream.write(buf2, 0, (int) pBytesLeftToRead);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        pOutputStream.flush();
    }

    public static boolean copyAndClose(InputStream pInputStream, OutputStream pOutputStream) {
        try {
            copy(pInputStream, pOutputStream, -1L);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            close(pInputStream);
            close(pOutputStream);
        }
    }

    public static void close(Closeable pCloseable) {
        if (pCloseable != null) {
            try {
                pCloseable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void flushCloseStream(OutputStream pOutputStream) {
        if (pOutputStream != null) {
            try {
                pOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(pOutputStream);
            }
        }
    }

    public static void flushCloseWriter(Writer pWriter) {
        if (pWriter != null) {
            try {
                pWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(pWriter);
            }
        }
    }
}
