package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.internal.constants.PlatformType;
import com.google.android.gms.internal.fo;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public final class GameInstanceRef extends b implements GameInstance {
    GameInstanceRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
    }

    public String getApplicationId() {
        return getString("external_game_id");
    }

    public String getDisplayName() {
        return getString("instance_display_name");
    }

    public String getPackageName() {
        return getString("package_name");
    }

    public boolean hi() {
        return getInteger("real_time_support") > 0;
    }

    public boolean hj() {
        return getInteger("turn_based_support") > 0;
    }

    public int hk() {
        return getInteger("platform_type");
    }

    public boolean hl() {
        return getInteger("piracy_check") > 0;
    }

    public boolean hm() {
        return getInteger(TapjoyConstants.TJC_INSTALLED) > 0;
    }

    public String toString() {
        return fo.e(this).a("ApplicationId", getApplicationId()).a("DisplayName", getDisplayName()).a("SupportsRealTime", Boolean.valueOf(hi())).a("SupportsTurnBased", Boolean.valueOf(hj())).a("PlatformType", PlatformType.bd(hk())).a("PackageName", getPackageName()).a("PiracyCheckEnabled", Boolean.valueOf(hl())).a("Installed", Boolean.valueOf(hm())).toString();
    }
}
