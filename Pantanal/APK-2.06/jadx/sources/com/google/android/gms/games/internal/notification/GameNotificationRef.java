package com.google.android.gms.games.internal.notification;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.internal.fo;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
public final class GameNotificationRef extends b implements GameNotification {
    GameNotificationRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
    }

    public long getId() {
        return getLong("_id");
    }

    public String getText() {
        return getString("text");
    }

    public String getTitle() {
        return getString("title");
    }

    public int getType() {
        return getInteger("type");
    }

    public String hp() {
        return getString(TapjoyConstants.TJC_NOTIFICATION_ID);
    }

    public String hq() {
        return getString("ticker");
    }

    public String hr() {
        return getString("coalesced_text");
    }

    public boolean hs() {
        return getInteger("acknowledged") > 0;
    }

    public boolean ht() {
        return getInteger("alert_level") == 0;
    }

    public String toString() {
        return fo.e(this).a("Id", Long.valueOf(getId())).a("NotificationId", hp()).a("Type", Integer.valueOf(getType())).a("Title", getTitle()).a("Ticker", hq()).a("Text", getText()).a("CoalescedText", hr()).a("isAcknowledged", Boolean.valueOf(hs())).a("isSilent", Boolean.valueOf(ht())).toString();
    }
}
