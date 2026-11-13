package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class k extends j {

    class a {
        private String ka;
        private boolean kb;

        public a(String str, boolean z) {
            this.ka = str;
            this.kb = z;
        }

        public String getId() {
            return this.ka;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.kb;
        }
    }

    private k(Context context, n nVar, o oVar) {
        super(context, nVar, oVar);
    }

    public static k a(String str, Context context) {
        e eVar = new e();
        a(str, context, eVar);
        return new k(context, eVar, new q(239));
    }

    @Override // com.google.android.gms.internal.j, com.google.android.gms.internal.i
    protected void b(Context context) {
        super.b(context);
        try {
            try {
                a aVarF = f(context);
                a(28, aVarF.isLimitAdTrackingEnabled() ? 1L : 0L);
                String id = aVarF.getId();
                if (id != null) {
                    a(30, id);
                }
            } catch (GooglePlayServicesNotAvailableException e) {
            } catch (IOException e2) {
                a(28, 1L);
            }
        } catch (IOException e3) {
        }
    }

    a f(Context context) throws GooglePlayServicesNotAvailableException, IOException {
        String strA;
        int i = 0;
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            String id = advertisingIdInfo.getId();
            if (id == null || !id.matches("^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")) {
                strA = id;
            } else {
                byte[] bArr = new byte[16];
                int i2 = 0;
                while (i < id.length()) {
                    if (i == 8 || i == 13 || i == 18 || i == 23) {
                        i++;
                    }
                    bArr[i2] = (byte) ((Character.digit(id.charAt(i), 16) << 4) + Character.digit(id.charAt(i + 1), 16));
                    i2++;
                    i += 2;
                }
                strA = this.jP.a(bArr, true);
            }
            return new a(strA, advertisingIdInfo.isLimitAdTrackingEnabled());
        } catch (GooglePlayServicesRepairableException e) {
            throw new IOException(e);
        }
    }
}
