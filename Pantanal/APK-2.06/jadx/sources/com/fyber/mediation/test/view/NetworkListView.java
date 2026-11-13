package com.fyber.mediation.test.view;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.TransportMediator;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.fyber.mediation.test.data.MediationStatus;
import com.fyber.mediation.test.data.NetworkStatus;
import com.heyzap.internal.Utils;
import com.heyzap.sdk.ads.MediationTestActivity;

/* JADX INFO: loaded from: classes.dex */
public class NetworkListView extends ScrollView {
    LinearLayout list;
    MediationStatus status;
    MediationTestActivity testActivity;

    public NetworkListView(Context context) {
        super(context);
        this.status = null;
        this.testActivity = null;
        this.list = new LinearLayout(context);
        this.list.setOrientation(1);
        setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1, -2);
        addView(this.list, lp);
    }

    public void setStatus(final MediationTestActivity testActivity, MediationStatus mediationStatus) {
        this.list.removeAllViews();
        this.status = mediationStatus;
        this.testActivity = testActivity;
        for (final NetworkStatus networkStatus : mediationStatus.networkStatusList) {
            if (networkStatus.listInTestActivity() || networkStatus.getAdapterStatus() != 0 || networkStatus.getRemoteStatus() != 0 || networkStatus.getLocalStatus() != 0) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                frameLayout.setBackgroundColor(-5592406);
                frameLayout.setPadding(0, 0, 0, 1);
                LinearLayout li = new LinearLayout(getContext());
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1, -2);
                lp.setMargins(0, 0, 0, 1);
                li.setLayoutParams(lp);
                li.setBackgroundColor(-1);
                TextView statusMark = new TextView(getContext());
                statusMark.setText((networkStatus.getLocalStatus() == 1 && networkStatus.getRemoteStatus() == 1) ? "on" : "off");
                statusMark.setBackgroundColor(networkStatus.getNetworkStatus() ? -16711936 : SupportMenu.CATEGORY_MASK);
                statusMark.setTextColor(-16777216);
                statusMark.getBackground().setAlpha(networkStatus.getNetworkStatus() ? TransportMediator.KEYCODE_MEDIA_RECORD : 180);
                statusMark.setTextSize(12.0f);
                statusMark.setTypeface(null, 1);
                statusMark.setGravity(17);
                li.addView(statusMark, new LinearLayout.LayoutParams(Utils.dpToPx(getContext(), 30), -1));
                TextView nameView = new TextView(getContext());
                nameView.setText(networkStatus.getName());
                Log.v("MediationTestActivity", "adding network named: " + networkStatus.getName());
                nameView.setTextSize(16.0f);
                nameView.setTextColor(-16777216);
                int px = (int) TypedValue.applyDimension(1, 14.0f, getContext().getResources().getDisplayMetrics());
                nameView.setPadding(px, px, px, px);
                li.addView(nameView);
                frameLayout.addView(li);
                this.list.addView(frameLayout);
                frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.fyber.mediation.test.view.NetworkListView.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        testActivity.showNetworkDetails(networkStatus);
                    }
                });
            }
        }
        this.status = mediationStatus;
    }
}
