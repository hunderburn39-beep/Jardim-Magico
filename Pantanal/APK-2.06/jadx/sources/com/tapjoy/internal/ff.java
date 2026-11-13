package com.tapjoy.internal;

import android.os.SystemClock;
import android.support.v4.media.TransportMediator;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/* JADX INFO: loaded from: classes.dex */
public final class ff {
    public long a;
    public long b;
    public long c;

    public final boolean a(String str, int i) throws Throwable {
        DatagramSocket datagramSocket;
        DatagramSocket datagramSocket2 = null;
        try {
            datagramSocket = new DatagramSocket();
            try {
                datagramSocket.setSoTimeout(i);
                byte[] bArr = new byte[48];
                DatagramPacket datagramPacket = new DatagramPacket(bArr, 48, InetAddress.getByName(str), 123);
                bArr[0] = 27;
                long jCurrentTimeMillis = System.currentTimeMillis();
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                long j = jCurrentTimeMillis / 1000;
                long j2 = jCurrentTimeMillis - (1000 * j);
                long j3 = j + 2208988800L;
                bArr[40] = (byte) (j3 >> 24);
                bArr[41] = (byte) (j3 >> 16);
                bArr[42] = (byte) (j3 >> 8);
                bArr[43] = (byte) (j3 >> 0);
                long j4 = (4294967296L * j2) / 1000;
                bArr[44] = (byte) (j4 >> 24);
                bArr[45] = (byte) (j4 >> 16);
                bArr[46] = (byte) (j4 >> 8);
                bArr[47] = (byte) (Math.random() * 255.0d);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(new DatagramPacket(bArr, 48));
                long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                long j5 = jCurrentTimeMillis + (jElapsedRealtime2 - jElapsedRealtime);
                long jB = b(bArr, 24);
                long jB2 = b(bArr, 32);
                long jB3 = b(bArr, 40);
                this.a = (((jB3 - j5) + (jB2 - jB)) / 2) + j5;
                this.b = jElapsedRealtime2;
                this.c = (jElapsedRealtime2 - jElapsedRealtime) - (jB3 - jB2);
                datagramSocket.close();
                return true;
            } catch (Exception e) {
                if (datagramSocket != null) {
                    datagramSocket.close();
                }
                return false;
            } catch (Throwable th) {
                datagramSocket2 = datagramSocket;
                th = th;
                if (datagramSocket2 != null) {
                    datagramSocket2.close();
                }
                throw th;
            }
        } catch (Exception e2) {
            datagramSocket = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static long a(byte[] bArr, int i) {
        int i2 = bArr[i];
        int i3 = bArr[i + 1];
        int i4 = bArr[i + 2];
        int i5 = bArr[i + 3];
        if ((i2 & 128) == 128) {
            i2 = (i2 & TransportMediator.KEYCODE_MEDIA_PAUSE) + 128;
        }
        if ((i3 & 128) == 128) {
            i3 = (i3 & TransportMediator.KEYCODE_MEDIA_PAUSE) + 128;
        }
        if ((i4 & 128) == 128) {
            i4 = (i4 & TransportMediator.KEYCODE_MEDIA_PAUSE) + 128;
        }
        if ((i5 & 128) == 128) {
            i5 = (i5 & TransportMediator.KEYCODE_MEDIA_PAUSE) + 128;
        }
        return ((long) i5) + (((long) i3) << 16) + (((long) i2) << 24) + (((long) i4) << 8);
    }

    private static long b(byte[] bArr, int i) {
        return ((a(bArr, i) - 2208988800L) * 1000) + ((a(bArr, i + 4) * 1000) / 4294967296L);
    }
}
