package com.google.android.gms.wallet.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.dynamic.f;
import com.google.android.gms.internal.iz;
import com.google.android.gms.internal.ja;
import com.google.android.gms.internal.jh;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.tapjoy.TJAdUnitConstants;

/* JADX INFO: loaded from: classes.dex */
public final class WalletFragment extends Fragment {
    private WalletFragmentInitParams acA;
    private MaskedWalletRequest acB;
    private Boolean acC;
    private b acH;
    private WalletFragmentOptions acz;
    private boolean mCreated = false;
    private final com.google.android.gms.dynamic.b acI = com.google.android.gms.dynamic.b.a(this);
    private final c acJ = new c();
    private a acK = new a(this);
    private final Fragment Hv = this;

    public interface OnStateChangedListener {
        void onStateChanged(WalletFragment walletFragment, int i, int i2, Bundle bundle);
    }

    static class a extends ja.a {
        private OnStateChangedListener acL;
        private final WalletFragment acM;

        a(WalletFragment walletFragment) {
            this.acM = walletFragment;
        }

        @Override // com.google.android.gms.internal.ja
        public void a(int i, int i2, Bundle bundle) {
            if (this.acL != null) {
                this.acL.onStateChanged(this.acM, i, i2, bundle);
            }
        }

        public void a(OnStateChangedListener onStateChangedListener) {
            this.acL = onStateChangedListener;
        }
    }

    private static class b implements LifecycleDelegate {
        private final iz acF;

        private b(iz izVar) {
            this.acF = izVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getState() {
            try {
                return this.acF.getState();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(WalletFragmentInitParams startParams) {
            try {
                this.acF.initialize(startParams);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            try {
                this.acF.onActivityResult(requestCode, resultCode, data);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEnabled(boolean enabled) {
            try {
                this.acF.setEnabled(enabled);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateMaskedWalletRequest(MaskedWalletRequest request) {
            try {
                this.acF.updateMaskedWalletRequest(request);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onCreate(Bundle savedInstanceState) {
            try {
                this.acF.onCreate(savedInstanceState);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            try {
                return (View) e.d(this.acF.onCreateView(e.h(inflater), e.h(container), savedInstanceState));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onDestroy() {
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onDestroyView() {
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
            try {
                this.acF.a(e.h(activity), (WalletFragmentOptions) attrs.getParcelable("extraWalletFragmentOptions"), savedInstanceState);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onLowMemory() {
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onPause() {
            try {
                this.acF.onPause();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onResume() {
            try {
                this.acF.onResume();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onSaveInstanceState(Bundle outState) {
            try {
                this.acF.onSaveInstanceState(outState);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onStart() {
            try {
                this.acF.onStart();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // com.google.android.gms.dynamic.LifecycleDelegate
        public void onStop() {
            try {
                this.acF.onStop();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class c extends com.google.android.gms.dynamic.a<b> implements View.OnClickListener {
        private c() {
        }

        @Override // com.google.android.gms.dynamic.a
        protected void a(FrameLayout frameLayout) {
            WalletFragmentStyle fragmentStyle;
            Button button = new Button(WalletFragment.this.Hv.getActivity());
            button.setText(R.string.wallet_buy_button_place_holder);
            int iA = -1;
            int iA2 = -2;
            if (WalletFragment.this.acz != null && (fragmentStyle = WalletFragment.this.acz.getFragmentStyle()) != null) {
                DisplayMetrics displayMetrics = WalletFragment.this.Hv.getResources().getDisplayMetrics();
                iA = fragmentStyle.a("buyButtonWidth", displayMetrics, -1);
                iA2 = fragmentStyle.a("buyButtonHeight", displayMetrics, -2);
            }
            button.setLayoutParams(new ViewGroup.LayoutParams(iA, iA2));
            button.setOnClickListener(this);
            frameLayout.addView(button);
        }

        @Override // com.google.android.gms.dynamic.a
        protected void a(f<b> fVar) {
            Activity activity = WalletFragment.this.Hv.getActivity();
            if (WalletFragment.this.acH == null && WalletFragment.this.mCreated && activity != null) {
                try {
                    iz izVarA = jh.a(activity, WalletFragment.this.acI, WalletFragment.this.acz, WalletFragment.this.acK);
                    WalletFragment.this.acH = new b(izVarA);
                    WalletFragment.this.acz = null;
                    fVar.a(WalletFragment.this.acH);
                    if (WalletFragment.this.acA != null) {
                        WalletFragment.this.acH.initialize(WalletFragment.this.acA);
                        WalletFragment.this.acA = null;
                    }
                    if (WalletFragment.this.acB != null) {
                        WalletFragment.this.acH.updateMaskedWalletRequest(WalletFragment.this.acB);
                        WalletFragment.this.acB = null;
                    }
                    if (WalletFragment.this.acC != null) {
                        WalletFragment.this.acH.setEnabled(WalletFragment.this.acC.booleanValue());
                        WalletFragment.this.acC = null;
                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Activity activity = WalletFragment.this.Hv.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity), activity, -1);
        }
    }

    public static WalletFragment newInstance(WalletFragmentOptions options) {
        WalletFragment walletFragment = new WalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", options);
        walletFragment.Hv.setArguments(bundle);
        return walletFragment;
    }

    public int getState() {
        if (this.acH != null) {
            return this.acH.getState();
        }
        return 0;
    }

    public void initialize(WalletFragmentInitParams initParams) {
        if (this.acH != null) {
            this.acH.initialize(initParams);
            this.acA = null;
        } else {
            if (this.acA != null) {
                Log.w("WalletFragment", "initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
                return;
            }
            this.acA = initParams;
            if (this.acB != null) {
                Log.w("WalletFragment", "updateMaskedWallet() was called before initialize()");
            }
        }
    }

    @Override // android.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.acH != null) {
            this.acH.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        WalletFragmentOptions walletFragmentOptions;
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams) savedInstanceState.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.acA != null) {
                    Log.w("WalletFragment", "initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
                }
                this.acA = walletFragmentInitParams;
            }
            if (this.acB == null) {
                this.acB = (MaskedWalletRequest) savedInstanceState.getParcelable("maskedWalletRequest");
            }
            if (savedInstanceState.containsKey("walletFragmentOptions")) {
                this.acz = (WalletFragmentOptions) savedInstanceState.getParcelable("walletFragmentOptions");
            }
            if (savedInstanceState.containsKey(TJAdUnitConstants.String.ENABLED)) {
                this.acC = Boolean.valueOf(savedInstanceState.getBoolean(TJAdUnitConstants.String.ENABLED));
            }
        } else if (this.Hv.getArguments() != null && (walletFragmentOptions = (WalletFragmentOptions) this.Hv.getArguments().getParcelable("extraWalletFragmentOptions")) != null) {
            walletFragmentOptions.I(this.Hv.getActivity());
            this.acz = walletFragmentOptions;
        }
        this.mCreated = true;
        this.acJ.onCreate(savedInstanceState);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return this.acJ.onCreateView(inflater, container, savedInstanceState);
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mCreated = false;
    }

    @Override // android.app.Fragment
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        if (this.acz == null) {
            this.acz = WalletFragmentOptions.a(activity, attrs);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("attrKeyWalletFragmentOptions", this.acz);
        this.acJ.onInflate(activity, bundle, savedInstanceState);
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        this.acJ.onPause();
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        this.acJ.onResume();
        FragmentManager fragmentManager = this.Hv.getActivity().getFragmentManager();
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(GooglePlayServicesUtil.GMS_ERROR_DIALOG);
        if (fragmentFindFragmentByTag != null) {
            fragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.Hv.getActivity()), this.Hv.getActivity(), -1);
        }
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.acJ.onSaveInstanceState(outState);
        if (this.acA != null) {
            outState.putParcelable("walletFragmentInitParams", this.acA);
            this.acA = null;
        }
        if (this.acB != null) {
            outState.putParcelable("maskedWalletRequest", this.acB);
            this.acB = null;
        }
        if (this.acz != null) {
            outState.putParcelable("walletFragmentOptions", this.acz);
            this.acz = null;
        }
        if (this.acC != null) {
            outState.putBoolean(TJAdUnitConstants.String.ENABLED, this.acC.booleanValue());
            this.acC = null;
        }
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        this.acJ.onStart();
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.acJ.onStop();
    }

    public void setEnabled(boolean enabled) {
        if (this.acH == null) {
            this.acC = Boolean.valueOf(enabled);
        } else {
            this.acH.setEnabled(enabled);
            this.acC = null;
        }
    }

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        this.acK.a(listener);
    }

    public void updateMaskedWalletRequest(MaskedWalletRequest request) {
        if (this.acH == null) {
            this.acB = request;
        } else {
            this.acH.updateMaskedWalletRequest(request);
            this.acB = null;
        }
    }
}
