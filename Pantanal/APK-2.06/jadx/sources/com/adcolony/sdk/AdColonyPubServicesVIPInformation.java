package com.adcolony.sdk;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyPubServicesVIPInformation {
    private Map<String, Object> a;
    private final String b = "rank_name";
    private final String c = "rank_level";
    private final String d = "next_rank_name";
    private final String e = "next_rank_level";
    private final String f = "rank_percent_progress_micro";
    private final String g = "bonus_per_currency_unit_micro";
    private final String h = "bonus_per_product_unit_micro";
    private final String i = "next_bonus_per_currency_unit_micro";
    private final String j = "total_achievement_count";
    private final String k = "pending_achievement_redemption_count";
    private final String l = "pending_vip_redemption_count";
    private final String m = "next_bonus_per_product_unit_micro";

    AdColonyPubServicesVIPInformation(Map<String, Object> userInfo) {
        this.a = userInfo;
    }

    public String rankName() {
        if (!this.a.containsKey("rank_name") || this.a.get("rank_name") == null) {
            return "";
        }
        return (String) this.a.get("rank_name");
    }

    public int rankLevel() {
        if (!this.a.containsKey("rank_level") || this.a.get("rank_level") == null) {
            return 0;
        }
        return ((Integer) this.a.get("rank_level")).intValue();
    }

    public String nextRankName() {
        if (!this.a.containsKey("next_rank_name") || this.a.get("next_rank_name") == null) {
            return "";
        }
        return (String) this.a.get("next_rank_name");
    }

    public int nextRankLevel() {
        if (!this.a.containsKey("next_rank_level") || this.a.get("next_rank_level") == null) {
            return 0;
        }
        return ((Integer) this.a.get("next_rank_level")).intValue();
    }

    public float rankPercentProgress() {
        if (!this.a.containsKey("rank_percent_progress_micro") || this.a.get("rank_percent_progress_micro") == null) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        return (float) ck.a(this.a.get("rank_percent_progress_micro"));
    }

    public float bonusPerCurrencyLocaleUnit() {
        if (!this.a.containsKey("bonus_per_currency_unit_micro") || this.a.get("bonus_per_currency_unit_micro") == null) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        return (float) ck.a(this.a.get("bonus_per_currency_unit_micro"));
    }

    public float bonusPerProductUnit() {
        if (!this.a.containsKey("bonus_per_product_unit_micro") || this.a.get("bonus_per_product_unit_micro") == null) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        return (float) ck.a(this.a.get("bonus_per_product_unit_micro"));
    }

    public float nextBonusPerCurrencyLocaleUnit() {
        if (!this.a.containsKey("next_bonus_per_currency_unit_micro") || this.a.get("next_bonus_per_currency_unit_micro") == null) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        return (float) ck.a(this.a.get("next_bonus_per_currency_unit_micro"));
    }

    public float nextBonusPerProductUnit() {
        if (!this.a.containsKey("next_bonus_per_product_unit_micro") || this.a.get("next_bonus_per_product_unit_micro") == null) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        return (float) ck.a(this.a.get("next_bonus_per_product_unit_micro"));
    }

    public int getPendingAchievementRedemptionCount() {
        if (!this.a.containsKey("pending_achievement_redemption_count") || this.a.get("pending_achievement_redemption_count") == null) {
            return 0;
        }
        return ((Integer) this.a.get("pending_achievement_redemption_count")).intValue();
    }

    public int getTotalAchievementCount() {
        if (!this.a.containsKey("total_achievement_count") || this.a.get("total_achievement_count") == null) {
            return 0;
        }
        return ((Integer) this.a.get("total_achievement_count")).intValue();
    }

    public Map<String, Object> getData() {
        return this.a;
    }

    public String toJson() {
        return ck.a(this.a);
    }
}
