package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.mraid.view.MraidView;
import java.util.Map;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class be implements bb {
    private static int a(DisplayMetrics displayMetrics, Map<String, String> map, String str, int i) {
        String str2 = map.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            return dv.a(displayMetrics, Integer.parseInt(str2));
        } catch (NumberFormatException e) {
            dw.z("Could not parse " + str + " in a video GMSG: " + str2);
            return i;
        }
    }

    @Override // com.google.android.gms.internal.bb
    public void b(dz dzVar, Map<String, String> map) {
        String str = map.get(MraidView.ACTION_KEY);
        if (str == null) {
            dw.z("Action missing from video GMSG.");
            return;
        }
        cc ccVarBH = dzVar.bH();
        if (ccVarBH == null) {
            dw.z("Could not get ad overlay for a video GMSG.");
            return;
        }
        boolean zEqualsIgnoreCase = "new".equalsIgnoreCase(str);
        boolean zEqualsIgnoreCase2 = "position".equalsIgnoreCase(str);
        if (zEqualsIgnoreCase || zEqualsIgnoreCase2) {
            DisplayMetrics displayMetrics = dzVar.getContext().getResources().getDisplayMetrics();
            int iA = a(displayMetrics, map, TMXConstants.TAG_OBJECT_ATTRIBUTE_X, 0);
            int iA2 = a(displayMetrics, map, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y, 0);
            int iA3 = a(displayMetrics, map, "w", -1);
            int iA4 = a(displayMetrics, map, "h", -1);
            if (zEqualsIgnoreCase && ccVarBH.aK() == null) {
                ccVarBH.c(iA, iA2, iA3, iA4);
                return;
            } else {
                ccVarBH.b(iA, iA2, iA3, iA4);
                return;
            }
        }
        cg cgVarAK = ccVarBH.aK();
        if (cgVarAK == null) {
            cg.a(dzVar, "no_video_view", (String) null);
            return;
        }
        if ("click".equalsIgnoreCase(str)) {
            DisplayMetrics displayMetrics2 = dzVar.getContext().getResources().getDisplayMetrics();
            int iA5 = a(displayMetrics2, map, TMXConstants.TAG_OBJECT_ATTRIBUTE_X, 0);
            int iA6 = a(displayMetrics2, map, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y, 0);
            long jUptimeMillis = SystemClock.uptimeMillis();
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, iA5, iA6, 0);
            cgVarAK.b(motionEventObtain);
            motionEventObtain.recycle();
            return;
        }
        if ("controls".equalsIgnoreCase(str)) {
            String str2 = map.get(TJAdUnitConstants.String.ENABLED);
            if (str2 == null) {
                dw.z("Enabled parameter missing from controls video GMSG.");
                return;
            } else {
                cgVarAK.k(Boolean.parseBoolean(str2));
                return;
            }
        }
        if (TJAdUnitConstants.String.VIDEO_CURRENT_TIME.equalsIgnoreCase(str)) {
            String str3 = map.get("time");
            if (str3 == null) {
                dw.z("Time parameter missing from currentTime video GMSG.");
                return;
            }
            try {
                cgVarAK.seekTo((int) (Float.parseFloat(str3) * 1000.0f));
                return;
            } catch (NumberFormatException e) {
                dw.z("Could not parse time parameter from currentTime video GMSG: " + str3);
                return;
            }
        }
        if ("hide".equalsIgnoreCase(str)) {
            cgVarAK.setVisibility(4);
            return;
        }
        if ("load".equalsIgnoreCase(str)) {
            cgVarAK.aU();
            return;
        }
        if ("pause".equalsIgnoreCase(str)) {
            cgVarAK.pause();
            return;
        }
        if ("play".equalsIgnoreCase(str)) {
            cgVarAK.play();
            return;
        }
        if ("show".equalsIgnoreCase(str)) {
            cgVarAK.setVisibility(0);
        } else if ("src".equalsIgnoreCase(str)) {
            cgVarAK.o(map.get("src"));
        } else {
            dw.z("Unknown video action: " + str);
        }
    }
}
