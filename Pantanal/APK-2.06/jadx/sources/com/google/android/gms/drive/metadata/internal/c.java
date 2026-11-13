package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import com.google.android.gms.internal.gv;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static Map<String, MetadataField<?>> FP = new HashMap();

    static {
        b(gs.FR);
        b(gs.Go);
        b(gs.Gh);
        b(gs.Gm);
        b(gs.Gp);
        b(gs.Gb);
        b(gs.Gc);
        b(gs.FZ);
        b(gs.Ge);
        b(gs.Gk);
        b(gs.FS);
        b(gs.Gj);
        b(gs.FT);
        b(gs.Ga);
        b(gs.FU);
        b(gs.FV);
        b(gs.FW);
        b(gs.Gg);
        b(gs.Gd);
        b(gs.Gi);
        b(gs.Gl);
        b(gs.Gq);
        b(gs.Gr);
        b(gs.FY);
        b(gs.FX);
        b(gs.Gn);
        b(gs.Gf);
        b(gt.Gs);
        b(gt.Gu);
        b(gt.Gv);
        b(gt.Gw);
        b(gt.Gt);
        b(gv.Gy);
        b(gv.Gz);
    }

    public static MetadataField<?> ax(String str) {
        return FP.get(str);
    }

    private static void b(MetadataField<?> metadataField) {
        if (FP.containsKey(metadataField.getName())) {
            throw new IllegalArgumentException("Duplicate field name registered: " + metadataField.getName());
        }
        FP.put(metadataField.getName(), metadataField);
    }

    public static Collection<MetadataField<?>> fS() {
        return Collections.unmodifiableCollection(FP.values());
    }
}
