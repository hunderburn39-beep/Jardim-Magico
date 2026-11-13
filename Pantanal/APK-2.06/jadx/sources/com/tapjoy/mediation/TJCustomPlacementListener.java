package com.tapjoy.mediation;

/* JADX INFO: loaded from: classes.dex */
public interface TJCustomPlacementListener {
    void onCustomPlacementContentDismiss();

    void onCustomPlacementContentShown();

    void onCustomPlacementFailure(Error error);

    void onCustomPlacementLoad();

    void onCustomPlacementReward(String str, int i);
}
