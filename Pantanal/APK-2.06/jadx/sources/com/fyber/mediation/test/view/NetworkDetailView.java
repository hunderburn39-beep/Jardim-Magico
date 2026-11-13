package com.fyber.mediation.test.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.fyber.ads.banners.BannerSize;
import com.fyber.ads.banners.mediation.BannerEventListener;
import com.fyber.ads.banners.mediation.BannerMediationAdapter;
import com.fyber.ads.banners.mediation.BannerWrapper;
import com.fyber.ads.interstitials.InterstitialAd;
import com.fyber.ads.videos.mediation.TPNVideoEvent;
import com.fyber.ads.videos.mediation.TPNVideoValidationResult;
import com.fyber.ads.videos.mediation.a;
import com.fyber.ads.videos.mediation.b;
import com.fyber.mediation.MediationAdapter;
import com.fyber.mediation.test.data.MediationStatus;
import com.fyber.mediation.test.data.NetworkStatus;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.internal.Utils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class NetworkDetailView extends LinearLayout {
    RadioGroup adFormatSelect;
    private final MediationAdapter adapter;
    private List<BannerSize> allSizes;
    private final BannerMediationAdapter bannerAdapter;
    private FrameLayout bannerFrame;
    private Button btnBannerHide;
    private Button btnBannerShow;
    Button btnFetch;
    Button btnShow;
    private AtomicReference<BannerDisplay> currentBannerDisplay;
    private final a incentivizedFetchCallback;
    private final b incentivizedShowCallback;
    private com.fyber.ads.interstitials.b.a interstitialAd;
    LogView logView;
    private final MediationStatus mediationStatus;
    private final NetworkStatus networkStatus;
    private RadioButton toggleBanner;
    RadioButton toggleIncentivized;
    RadioButton toggleInterstitial;

    public NetworkDetailView(Context context, MediationStatus mediationStatus, NetworkStatus networkStatus) {
        super(context);
        this.allSizes = Arrays.asList(BannerSize.Builder.newBuilder().withHeight(50).withWidth(320).build(), BannerSize.Builder.newBuilder().withHeight(90).withWidth(320).build(), BannerSize.Builder.newBuilder().withHeight(100).withWidth(320).build(), BannerSize.Builder.newBuilder().withHeight(60).withWidth(468).build(), BannerSize.Builder.newBuilder().withHeight(90).withWidth(728).build(), BannerSize.SMART_SIZE, BannerSize.FIXED_HEIGHT_50, BannerSize.FIXED_HEIGHT_90, BannerSize.FIXED_SIZE_320_50);
        this.currentBannerDisplay = new AtomicReference<>(null);
        this.mediationStatus = mediationStatus;
        this.networkStatus = networkStatus;
        this.adapter = networkStatus.getNetworkAdapter();
        this.bannerAdapter = locateBannerAdapter();
        this.interstitialAd = new com.fyber.ads.interstitials.b.a(networkStatus.getName(), "random-ad-id", "random-request-id");
        this.incentivizedFetchCallback = new a() { // from class: com.fyber.mediation.test.view.NetworkDetailView.1
            @Override // com.fyber.ads.videos.mediation.a
            public void a(String s, String s1, TPNVideoValidationResult tpnVideoValidationResult, Map<String, String> map) {
                Log.d("MediationTestActivity", "Got fetchcallback: " + s + " - " + s1 + ": " + tpnVideoValidationResult + " - " + map);
                if (NetworkDetailView.this.logView != null) {
                    NetworkDetailView.this.logView.addMessage("Incentivized fetch callback: " + tpnVideoValidationResult);
                }
            }
        };
        this.incentivizedShowCallback = new b() { // from class: com.fyber.mediation.test.view.NetworkDetailView.2
            @Override // com.fyber.ads.videos.mediation.b
            public void a(String s, String s1, TPNVideoEvent tpnVideoEvent, Map<String, String> map) {
                Log.d("MediationTestActivity", "Got show callback: " + s + " - " + s1 + ": " + tpnVideoEvent + " - " + map);
                if (NetworkDetailView.this.logView != null) {
                    NetworkDetailView.this.logView.addMessage("Incentivized show callback: " + tpnVideoEvent);
                }
            }
        };
        setBackgroundColor(-1);
        setOrientation(1);
        constructChildren();
    }

    private void constructChildren() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        TextView title = new TextView(getContext());
        title.setText(this.networkStatus.getName());
        title.setTextColor(-16777216);
        title.setTextSize(24.0f);
        linearLayout.addView(title);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(-1, -2);
        titleParams.setMargins(0, 0, 0, Utils.dpToPx(getContext(), 12));
        addView(buildStatusNotifications(), new LinearLayout.LayoutParams(-1, -2));
        this.adFormatSelect = new RadioGroup(getContext());
        this.adFormatSelect.setOrientation(1);
        boolean interstitialEnabled = (this.adapter == null || this.adapter.getInterstitialMediationAdapter() == null) ? false : true;
        this.toggleInterstitial = new RadioButton(getContext());
        this.toggleInterstitial.setText("Interstitial");
        this.toggleInterstitial.setEnabled(interstitialEnabled);
        this.toggleInterstitial.setTextColor(interstitialEnabled ? -16777216 : -7829368);
        boolean incentivizedEnabled = (this.adapter == null || this.adapter.getVideoMediationAdapter() == null) ? false : true;
        this.toggleIncentivized = new RadioButton(getContext());
        this.toggleIncentivized.setText("Rewarded");
        this.toggleIncentivized.setEnabled(incentivizedEnabled);
        this.toggleIncentivized.setTextColor(incentivizedEnabled ? -16777216 : -7829368);
        boolean bannerEnabled = this.bannerAdapter != null;
        this.toggleBanner = new RadioButton(getContext());
        this.toggleBanner.setText("Banner");
        this.toggleBanner.setEnabled(bannerEnabled);
        this.toggleBanner.setTextColor(bannerEnabled ? -16777216 : -7829368);
        this.adFormatSelect.addView(this.toggleInterstitial);
        this.adFormatSelect.addView(this.toggleIncentivized);
        this.adFormatSelect.addView(this.toggleBanner);
        linearLayout.addView(this.adFormatSelect);
        final LinearLayout actionContainer = new LinearLayout(getContext());
        actionContainer.setOrientation(0);
        this.btnFetch = new Button(getContext());
        this.btnFetch.setText("Fetch");
        this.btnFetch.setOnClickListener(new View.OnClickListener() { // from class: com.fyber.mediation.test.view.NetworkDetailView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (NetworkDetailView.this.toggleInterstitial.isChecked()) {
                    NetworkDetailView.this.fetchInterstitial();
                } else if (NetworkDetailView.this.toggleIncentivized.isChecked()) {
                    NetworkDetailView.this.fetchIncentivized();
                }
            }
        });
        this.btnShow = new Button(getContext());
        this.btnShow.setText("Show");
        this.btnShow.setOnClickListener(new View.OnClickListener() { // from class: com.fyber.mediation.test.view.NetworkDetailView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (NetworkDetailView.this.toggleInterstitial.isChecked()) {
                    NetworkDetailView.this.showInterstitial();
                } else if (NetworkDetailView.this.toggleIncentivized.isChecked()) {
                    NetworkDetailView.this.showIncentivized();
                }
            }
        });
        final LinearLayout btnActionContainer = new LinearLayout(getContext());
        actionContainer.setOrientation(0);
        this.btnBannerShow = new Button(getContext());
        this.btnBannerShow.setText("Show");
        this.btnBannerShow.setOnClickListener(new View.OnClickListener() { // from class: com.fyber.mediation.test.view.NetworkDetailView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NetworkDetailView.this.showBanner();
            }
        });
        this.btnBannerHide = new Button(getContext());
        this.btnBannerHide.setText("Hide");
        this.btnBannerHide.setOnClickListener(new View.OnClickListener() { // from class: com.fyber.mediation.test.view.NetworkDetailView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NetworkDetailView.this.hideBanner();
            }
        });
        LinearLayout.LayoutParams weightParams = new LinearLayout.LayoutParams(0, -2);
        weightParams.weight = 1.0f;
        actionContainer.addView(this.btnFetch, weightParams);
        actionContainer.addView(this.btnShow, weightParams);
        btnActionContainer.addView(this.btnBannerShow, weightParams);
        btnActionContainer.addView(this.btnBannerHide, weightParams);
        actionContainer.setVisibility(8);
        btnActionContainer.setVisibility(8);
        linearLayout.addView(actionContainer);
        linearLayout.addView(btnActionContainer);
        this.bannerFrame = new FrameLayout(getContext());
        linearLayout.addView(this.bannerFrame, new LinearLayout.LayoutParams(-1, -2));
        this.adFormatSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.fyber.mediation.test.view.NetworkDetailView.7
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                boolean isBanner = NetworkDetailView.this.toggleBanner.isChecked();
                actionContainer.setVisibility(isBanner ? 8 : 0);
                btnActionContainer.setVisibility(isBanner ? 0 : 8);
            }
        });
        for (RadioButton b : Arrays.asList(this.toggleInterstitial, this.toggleIncentivized, this.toggleBanner)) {
            if (b.isEnabled()) {
                b.setChecked(true);
                break;
            }
        }
        this.logView = new LogView(getContext());
        addView(linearLayout, new LinearLayout.LayoutParams(-1, -2, BitmapDescriptorFactory.HUE_RED));
        addView(this.logView, new LinearLayout.LayoutParams(-1, 0, 1.0f));
    }

    private LinearLayout buildStatusNotifications() {
        LinearLayout list = new LinearLayout(getContext());
        list.setOrientation(1);
        LinearLayout.LayoutParams blockParams = new LinearLayout.LayoutParams(-1, -2);
        TextView adapterPresentText = generateStatusLine();
        if (this.networkStatus.getLocalStatus() == 1) {
            adapterPresentText.setText("SDK Available");
            adapterPresentText.setBackgroundColor(-16711936);
        } else {
            adapterPresentText.setText("SDK Missing");
            adapterPresentText.setBackgroundColor(SupportMenu.CATEGORY_MASK);
        }
        list.addView(adapterPresentText, blockParams);
        if (this.mediationStatus.givenConfigs) {
            TextView configurationPresentText = generateStatusLine();
            if (this.networkStatus.getConfigs() != null) {
                configurationPresentText.setText("Configuration present");
                configurationPresentText.setBackgroundColor(-16711936);
            } else {
                configurationPresentText.setText("Configuration not present");
                configurationPresentText.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            }
            list.addView(configurationPresentText, blockParams);
        }
        TextView adapterStartedText = generateStatusLine();
        if (this.networkStatus.getAdapterStatus() == 1) {
            adapterStartedText.setText("SDK Started Successfully");
            adapterStartedText.setBackgroundColor(-16711936);
        } else {
            adapterStartedText.setText("SDK Failed to start");
            adapterStartedText.setBackgroundColor(SupportMenu.CATEGORY_MASK);
        }
        list.addView(adapterStartedText, blockParams);
        return list;
    }

    private TextView generateStatusLine() {
        int padding = Utils.dpToPx(getContext(), 6);
        TextView view = new TextView(getContext());
        view.setTextSize(16.0f);
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setPadding(padding, padding, padding, padding);
        view.setTextColor(-1);
        return view;
    }

    private BannerMediationAdapter locateBannerAdapter() {
        if (this.adapter == null) {
            return null;
        }
        try {
            this.adapter.getClass().getMethod("getBannerMediationAdapter", new Class[0]);
            try {
                return this.adapter.getBannerMediationAdapter();
            } catch (AbstractMethodError e) {
                Log.d("TestActivity", "AbstractMethodError - could not get banner adapter");
                return null;
            }
        } catch (NoSuchMethodException e2) {
            Log.d("TestActivity", "NoSuchMethodError - could not get banner adapter");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchInterstitial() {
        final Future<InterstitialAd> fetchFuture = this.adapter.getInterstitialMediationAdapter().a(getContext(), this.interstitialAd);
        ExecutorPool.getInstance().execute(new Runnable() { // from class: com.fyber.mediation.test.view.NetworkDetailView.8
            @Override // java.lang.Runnable
            public void run() {
                InterstitialAd ad = null;
                Exception err = null;
                try {
                    ad = (InterstitialAd) fetchFuture.get();
                } catch (Exception e) {
                    err = null;
                }
                if (ad != null) {
                    NetworkDetailView.this.logView.addMessage("InterstitialAd fetched successfully");
                } else {
                    NetworkDetailView.this.logView.addMessage("InterstitialAd failed to fetch: " + (err == null ? "No ad returned" : err.getMessage()));
                }
            }
        });
        this.logView.addMessage("Fetching Interstitial, ad currently" + (fetchFuture.isDone() ? "  available" : " not available"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInterstitial() {
        this.adapter.getInterstitialMediationAdapter().a((Activity) getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchIncentivized() {
        this.logView.addMessage("Fetching Rewarded Video");
        this.adapter.getVideoMediationAdapter().a(getContext(), this.incentivizedFetchCallback, new HashMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showIncentivized() {
        this.logView.addMessage("Showing Rewarded Video");
        this.adapter.getVideoMediationAdapter().a((Activity) getContext(), this.incentivizedShowCallback, new HashMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBanner() {
        BannerDisplay bannerDisplay = new BannerDisplay();
        if (!this.currentBannerDisplay.compareAndSet(null, bannerDisplay)) {
            this.logView.addMessage("Banner display in progress, aborting display attempt");
            return;
        }
        this.logView.addMessage("Fetching Banner");
        Future<BannerWrapper> future = this.bannerAdapter.a(getContext(), this.allSizes);
        ExecutorPool.getInstance().execute(new AnonymousClass9(bannerDisplay, future));
    }

    /* JADX INFO: renamed from: com.fyber.mediation.test.view.NetworkDetailView$9, reason: invalid class name */
    class AnonymousClass9 implements Runnable {
        final /* synthetic */ BannerDisplay val$bannerDisplay;
        final /* synthetic */ Future val$future;

        AnonymousClass9(BannerDisplay bannerDisplay, Future future) {
            this.val$bannerDisplay = bannerDisplay;
            this.val$future = future;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.val$bannerDisplay.cancelled) {
                BannerWrapper wrapper = null;
                try {
                    wrapper = (BannerWrapper) this.val$future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e2) {
                    e2.printStackTrace();
                }
                this.val$bannerDisplay.bannerWrapper = wrapper;
                if (wrapper == null || wrapper.getView() == null) {
                    NetworkDetailView.this.logView.addMessage("Banner failed to fill");
                    NetworkDetailView.this.hideBanner();
                } else if (this.val$bannerDisplay.cancelled) {
                    NetworkDetailView.this.logView.addMessage("Banner loaded, but was cancelled. Not displaying.");
                } else {
                    this.val$bannerDisplay.bannerWrapper.setBannerEventListener(new BannerEventListener() { // from class: com.fyber.mediation.test.view.NetworkDetailView.9.1
                        @Override // com.fyber.ads.banners.mediation.BannerEventListener
                        public void onBannerLoaded(View view) {
                            NetworkDetailView.this.post(new Runnable() { // from class: com.fyber.mediation.test.view.NetworkDetailView.9.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    NetworkDetailView.this.logView.addMessage("Banner Loaded");
                                }
                            });
                        }

                        @Override // com.fyber.ads.banners.mediation.BannerEventListener
                        public void onBannerClick(View view) {
                            NetworkDetailView.this.post(new Runnable() { // from class: com.fyber.mediation.test.view.NetworkDetailView.9.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    NetworkDetailView.this.logView.addMessage("Banner Clicked");
                                }
                            });
                        }

                        @Override // com.fyber.ads.banners.mediation.BannerEventListener
                        public void onBannerError(View view, final String s) {
                            NetworkDetailView.this.post(new Runnable() { // from class: com.fyber.mediation.test.view.NetworkDetailView.9.1.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    NetworkDetailView.this.logView.addMessage("Banner error: " + s);
                                    NetworkDetailView.this.hideBanner();
                                }
                            });
                        }

                        @Override // com.fyber.ads.banners.mediation.BannerEventListener
                        public void onBannerLeftApplication(View view) {
                        }
                    });
                    NetworkDetailView.this.post(new Runnable() { // from class: com.fyber.mediation.test.view.NetworkDetailView.9.2
                        @Override // java.lang.Runnable
                        public void run() {
                            NetworkDetailView.this.bannerFrame.removeAllViews();
                            if (!AnonymousClass9.this.val$bannerDisplay.cancelled) {
                                NetworkDetailView.this.bannerFrame.addView(AnonymousClass9.this.val$bannerDisplay.bannerWrapper.getView());
                            } else {
                                NetworkDetailView.this.logView.addMessage("Banner loaded, but was cancelled. Aborting display.");
                            }
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideBanner() {
        post(new Runnable() { // from class: com.fyber.mediation.test.view.NetworkDetailView.10
            @Override // java.lang.Runnable
            public void run() {
                BannerDisplay bannerDisplay = (BannerDisplay) NetworkDetailView.this.currentBannerDisplay.get();
                if (bannerDisplay != null) {
                    NetworkDetailView.this.logView.addMessage("Destroying Banner");
                    bannerDisplay.cancelled = true;
                    if (bannerDisplay.bannerWrapper != null) {
                        bannerDisplay.bannerWrapper.destroy();
                    }
                    NetworkDetailView.this.bannerFrame.removeAllViews();
                    NetworkDetailView.this.currentBannerDisplay.set(null);
                    return;
                }
                NetworkDetailView.this.logView.addMessage("No banner to destroy");
            }
        });
    }

    private static class BannerDisplay {
        public BannerWrapper bannerWrapper;
        public boolean cancelled;
        public Future<BannerWrapper> future;

        private BannerDisplay() {
            this.cancelled = false;
        }
    }
}
