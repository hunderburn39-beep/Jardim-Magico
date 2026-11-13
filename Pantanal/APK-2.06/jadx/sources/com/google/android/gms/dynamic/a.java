package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public abstract class a<T extends LifecycleDelegate> {
    private T Hj;
    private Bundle Hk;
    private LinkedList<InterfaceC0040a> Hl;
    private final f<T> Hm = (f<T>) new f<T>() { // from class: com.google.android.gms.dynamic.a.1
        @Override // com.google.android.gms.dynamic.f
        public void a(T t) {
            a.this.Hj = t;
            Iterator it = a.this.Hl.iterator();
            while (it.hasNext()) {
                ((InterfaceC0040a) it.next()).b(a.this.Hj);
            }
            a.this.Hl.clear();
            a.this.Hk = null;
        }
    };

    /* JADX INFO: renamed from: com.google.android.gms.dynamic.a$a, reason: collision with other inner class name */
    private interface InterfaceC0040a {
        void b(LifecycleDelegate lifecycleDelegate);

        int getState();
    }

    private void a(Bundle bundle, InterfaceC0040a interfaceC0040a) {
        if (this.Hj != null) {
            interfaceC0040a.b(this.Hj);
            return;
        }
        if (this.Hl == null) {
            this.Hl = new LinkedList<>();
        }
        this.Hl.add(interfaceC0040a);
        if (bundle != null) {
            if (this.Hk == null) {
                this.Hk = (Bundle) bundle.clone();
            } else {
                this.Hk.putAll(bundle);
            }
        }
        a(this.Hm);
    }

    private void aR(int i) {
        while (!this.Hl.isEmpty() && this.Hl.getLast().getState() >= i) {
            this.Hl.removeLast();
        }
    }

    public static void b(FrameLayout frameLayout) {
        final Context context = frameLayout.getContext();
        final int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        String strC = GooglePlayServicesUtil.c(context, iIsGooglePlayServicesAvailable);
        String strD = GooglePlayServicesUtil.d(context, iIsGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(strC);
        linearLayout.addView(textView);
        if (strD != null) {
            Button button = new Button(context);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(strD);
            linearLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.gms.dynamic.a.5
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    context.startActivity(GooglePlayServicesUtil.b(context, iIsGooglePlayServicesAvailable));
                }
            });
        }
    }

    protected void a(FrameLayout frameLayout) {
        b(frameLayout);
    }

    protected abstract void a(f<T> fVar);

    public T fW() {
        return this.Hj;
    }

    public void onCreate(final Bundle savedInstanceState) {
        a(savedInstanceState, new InterfaceC0040a() { // from class: com.google.android.gms.dynamic.a.3
            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.Hj.onCreate(savedInstanceState);
            }

            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public int getState() {
                return 1;
            }
        });
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        a(savedInstanceState, new InterfaceC0040a() { // from class: com.google.android.gms.dynamic.a.4
            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public void b(LifecycleDelegate lifecycleDelegate) {
                frameLayout.removeAllViews();
                frameLayout.addView(a.this.Hj.onCreateView(inflater, container, savedInstanceState));
            }

            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public int getState() {
                return 2;
            }
        });
        if (this.Hj == null) {
            a(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.Hj != null) {
            this.Hj.onDestroy();
        } else {
            aR(1);
        }
    }

    public void onDestroyView() {
        if (this.Hj != null) {
            this.Hj.onDestroyView();
        } else {
            aR(2);
        }
    }

    public void onInflate(final Activity activity, final Bundle attrs, final Bundle savedInstanceState) {
        a(savedInstanceState, new InterfaceC0040a() { // from class: com.google.android.gms.dynamic.a.2
            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.Hj.onInflate(activity, attrs, savedInstanceState);
            }

            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public int getState() {
                return 0;
            }
        });
    }

    public void onLowMemory() {
        if (this.Hj != null) {
            this.Hj.onLowMemory();
        }
    }

    public void onPause() {
        if (this.Hj != null) {
            this.Hj.onPause();
        } else {
            aR(5);
        }
    }

    public void onResume() {
        a((Bundle) null, new InterfaceC0040a() { // from class: com.google.android.gms.dynamic.a.7
            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.Hj.onResume();
            }

            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public int getState() {
                return 5;
            }
        });
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.Hj != null) {
            this.Hj.onSaveInstanceState(outState);
        } else if (this.Hk != null) {
            outState.putAll(this.Hk);
        }
    }

    public void onStart() {
        a((Bundle) null, new InterfaceC0040a() { // from class: com.google.android.gms.dynamic.a.6
            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.Hj.onStart();
            }

            @Override // com.google.android.gms.dynamic.a.InterfaceC0040a
            public int getState() {
                return 4;
            }
        });
    }

    public void onStop() {
        if (this.Hj != null) {
            this.Hj.onStop();
        } else {
            aR(4);
        }
    }
}
