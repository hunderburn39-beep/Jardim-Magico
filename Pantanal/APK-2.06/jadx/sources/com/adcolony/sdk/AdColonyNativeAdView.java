package com.adcolony.sdk;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyNativeAdView extends ax {
    EngagementButton p;
    boolean q;
    String r;
    String s;
    String t;

    @Override // com.adcolony.sdk.ax
    public /* bridge */ /* synthetic */ boolean destroy() {
        return super.destroy();
    }

    @Override // com.adcolony.sdk.ax
    public /* bridge */ /* synthetic */ String getZoneID() {
        return super.getZoneID();
    }

    @Override // com.adcolony.sdk.ax
    public /* bridge */ /* synthetic */ boolean pause() {
        return super.pause();
    }

    @Override // com.adcolony.sdk.ax
    public /* bridge */ /* synthetic */ boolean resume() {
        return super.resume();
    }

    @Override // com.adcolony.sdk.ax
    public /* bridge */ /* synthetic */ boolean setMuted(boolean z) {
        return super.setMuted(z);
    }

    @Override // com.adcolony.sdk.ax
    public /* bridge */ /* synthetic */ boolean setVolume(float f) {
        return super.setVolume(f);
    }

    AdColonyNativeAdView(Context context, z message, e listener) {
        super(context, message, listener);
        this.h = true;
        this.q = s.d(message.b, "engagement_enabled");
        this.r = s.b(message.b, "engagement_click_action");
        this.s = s.b(message.b, "engagement_click_action_type");
        this.t = s.b(message.b, "engagement_text");
        if (this.q) {
            this.p = new EngagementButton(context);
            this.p.setText(this.t);
            this.p.setOnClickListener(new View.OnClickListener() { // from class: com.adcolony.sdk.AdColonyNativeAdView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AdColonyNativeAdView.this.g) {
                        u.e.b("Ignoring engagement click as view has been destroyed.");
                        return;
                    }
                    JSONObject jSONObjectA = s.a();
                    s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, AdColonyNativeAdView.this.d);
                    new z("AdSession.on_native_engagement", AdColonyNativeAdView.this.a.l, jSONObjectA).b();
                }
            });
        }
    }

    public String getAdvertiserName() {
        if (!this.g) {
            return this.k;
        }
        u.e.b("Ignoring call to getAdvertiserName() as view has been destroyed");
        return "";
    }

    public ImageView getIcon() {
        if (this.j == null) {
            return null;
        }
        if (this.g) {
            u.e.b("Ignoring call to getIcon() as view has been destroyed");
            return null;
        }
        return this.j;
    }

    public String getTitle() {
        if (!this.g) {
            return this.l;
        }
        u.e.b("Ignoring call to getTitle() as view has been destroyed");
        return "";
    }

    public String getDescription() {
        if (!this.g) {
            return this.m;
        }
        u.e.b("Ignoring call to getDescription() as view has been destroyed");
        return "";
    }

    public boolean isEngagementEnabled() {
        if (!this.g) {
            return this.q;
        }
        u.e.b("Ignoring call to isEngagementEnabled() as view has been destroyed");
        return false;
    }

    public EngagementButton getEngagementButton() {
        if (!this.g) {
            return this.p;
        }
        u.e.b("Ignoring call to getEngagementButton() as view has been destroyed");
        return null;
    }

    public class EngagementButton extends Button {
        boolean a;
        View.OnClickListener b;

        EngagementButton(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setOnClickListener(View.OnClickListener listener) {
            if (!this.a) {
                super.setOnClickListener(listener);
                this.b = listener;
                this.a = true;
            }
        }

        public View.OnClickListener getOnClickListener() {
            return this.b;
        }
    }
}
