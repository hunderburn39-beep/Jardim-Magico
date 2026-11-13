package com.adcolony.sdk;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyReward {
    private int a;
    private String b;
    private String c;
    private boolean d;

    AdColonyReward(z message) {
        this.a = s.c(message.b, "reward_amount");
        this.b = s.b(message.b, "reward_name");
        this.d = s.d(message.b, "success");
        this.c = s.b(message.b, "zone_id");
    }

    public int getRewardAmount() {
        return this.a;
    }

    public String getRewardName() {
        return this.b;
    }

    public String getZoneID() {
        return this.c;
    }

    public boolean success() {
        return this.d;
    }
}
