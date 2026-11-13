package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcelable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class dj {
    private static final dj qJ = new dj();
    public static final String qK = qJ.qL;
    private final Object li = new Object();
    private BigInteger qN = BigInteger.ONE;
    private final HashSet<di> qO = new HashSet<>();
    private final HashMap<String, dm> qP = new HashMap<>();
    public final String qL = br();
    private final dk qM = new dk(this.qL);

    private dj() {
    }

    public static Bundle a(dl dlVar, String str) {
        return qJ.b(dlVar, str);
    }

    public static void b(HashSet<di> hashSet) {
        qJ.c(hashSet);
    }

    public static dj bq() {
        return qJ;
    }

    private static String br() {
        UUID uuidRandomUUID = UUID.randomUUID();
        byte[] byteArray = BigInteger.valueOf(uuidRandomUUID.getLeastSignificantBits()).toByteArray();
        byte[] byteArray2 = BigInteger.valueOf(uuidRandomUUID.getMostSignificantBits()).toByteArray();
        String string = new BigInteger(1, byteArray).toString();
        for (int i = 0; i < 2; i++) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(byteArray);
                messageDigest.update(byteArray2);
                byte[] bArr = new byte[8];
                System.arraycopy(messageDigest.digest(), 0, bArr, 0, 8);
                string = new BigInteger(1, bArr).toString();
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return string;
    }

    public static String bs() {
        return qJ.bt();
    }

    public static dk bu() {
        return qJ.bv();
    }

    public void a(di diVar) {
        synchronized (this.li) {
            this.qO.add(diVar);
        }
    }

    public void a(String str, dm dmVar) {
        synchronized (this.li) {
            this.qP.put(str, dmVar);
        }
    }

    public Bundle b(dl dlVar, String str) {
        Bundle bundle;
        synchronized (this.li) {
            bundle = new Bundle();
            bundle.putBundle("app", this.qM.q(str));
            Bundle bundle2 = new Bundle();
            for (String str2 : this.qP.keySet()) {
                bundle2.putBundle(str2, this.qP.get(str2).toBundle());
            }
            bundle.putBundle("slots", bundle2);
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            Iterator<di> it = this.qO.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toBundle());
            }
            bundle.putParcelableArrayList("ads", arrayList);
            dlVar.a(this.qO);
            this.qO.clear();
        }
        return bundle;
    }

    public String bt() {
        String string;
        synchronized (this.li) {
            string = this.qN.toString();
            this.qN = this.qN.add(BigInteger.ONE);
        }
        return string;
    }

    public dk bv() {
        dk dkVar;
        synchronized (this.li) {
            dkVar = this.qM;
        }
        return dkVar;
    }

    public void c(HashSet<di> hashSet) {
        synchronized (this.li) {
            this.qO.addAll(hashSet);
        }
    }
}
