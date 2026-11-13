package com.google.android.gms.tagmanager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ao extends aj {
    private static final String ID = com.google.android.gms.internal.a.HASH.toString();
    private static final String XQ = com.google.android.gms.internal.b.ARG0.toString();
    private static final String XW = com.google.android.gms.internal.b.ALGORITHM.toString();
    private static final String XS = com.google.android.gms.internal.b.INPUT_FORMAT.toString();

    public ao() {
        super(ID, XQ);
    }

    private byte[] c(String str, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        byte[] bArrBm;
        com.google.android.gms.internal.d.a aVar = map.get(XQ);
        if (aVar == null || aVar == dh.lT()) {
            return dh.lT();
        }
        String strJ = dh.j(aVar);
        com.google.android.gms.internal.d.a aVar2 = map.get(XW);
        String strJ2 = aVar2 == null ? "MD5" : dh.j(aVar2);
        com.google.android.gms.internal.d.a aVar3 = map.get(XS);
        String strJ3 = aVar3 == null ? "text" : dh.j(aVar3);
        if ("text".equals(strJ3)) {
            bArrBm = strJ.getBytes();
        } else {
            if (!"base16".equals(strJ3)) {
                bh.w("Hash: unknown input format: " + strJ3);
                return dh.lT();
            }
            bArrBm = j.bm(strJ);
        }
        try {
            return dh.r(j.d(c(strJ2, bArrBm)));
        } catch (NoSuchAlgorithmException e) {
            bh.w("Hash: unknown algorithm: " + strJ2);
            return dh.lT();
        }
    }
}
