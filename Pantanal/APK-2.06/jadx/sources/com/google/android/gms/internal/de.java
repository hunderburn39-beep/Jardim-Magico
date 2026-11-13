package com.google.android.gms.internal;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class de {
    private dz lC;
    private String pI;
    private String pJ;
    private final Object li = new Object();
    private int oS = -2;
    public final bb pK = new bb() { // from class: com.google.android.gms.internal.de.1
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            synchronized (de.this.li) {
                dw.z("Invalid " + map.get("type") + " request error: " + map.get("errors"));
                de.this.oS = 1;
                de.this.li.notify();
            }
        }
    };
    public final bb pL = new bb() { // from class: com.google.android.gms.internal.de.2
        @Override // com.google.android.gms.internal.bb
        public void b(dz dzVar, Map<String, String> map) {
            synchronized (de.this.li) {
                String strReplaceAll = map.get("url");
                if (strReplaceAll == null) {
                    dw.z("URL missing in loadAdUrl GMSG.");
                    return;
                }
                if (strReplaceAll.contains("%40mediation_adapters%40")) {
                    strReplaceAll = strReplaceAll.replaceAll("%40mediation_adapters%40", dn.b(dzVar.getContext(), map.get("check_adapters"), de.this.pI));
                    dw.y("Ad request URL modified to " + strReplaceAll);
                }
                de.this.pJ = strReplaceAll;
                de.this.li.notify();
            }
        }
    };

    public de(String str) {
        this.pI = str;
    }

    public void b(dz dzVar) {
        synchronized (this.li) {
            this.lC = dzVar;
        }
    }

    public String bj() {
        String str;
        synchronized (this.li) {
            while (this.pJ == null && this.oS == -2) {
                try {
                    this.li.wait();
                } catch (InterruptedException e) {
                    dw.z("Ad request service was interrupted.");
                    str = null;
                }
            }
            str = this.pJ;
        }
        return str;
    }

    public int getErrorCode() {
        int i;
        synchronized (this.li) {
            i = this.oS;
        }
        return i;
    }
}
