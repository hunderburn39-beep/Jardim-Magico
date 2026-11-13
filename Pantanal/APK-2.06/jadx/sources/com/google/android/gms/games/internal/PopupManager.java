package com.google.android.gms.games.internal;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import com.google.android.gms.internal.gr;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class PopupManager {
    protected GamesClientImpl JK;
    protected PopupLocationInfo JL;

    public static final class PopupLocationInfo {
        public IBinder JM;
        public int JN;
        public int bottom;
        public int gravity;
        public int left;
        public int right;
        public int top;

        private PopupLocationInfo(int gravity, IBinder windowToken) {
            this.JN = -1;
            this.left = 0;
            this.top = 0;
            this.right = 0;
            this.bottom = 0;
            this.gravity = gravity;
            this.JM = windowToken;
        }

        public Bundle gV() {
            Bundle bundle = new Bundle();
            bundle.putInt("popupLocationInfo.gravity", this.gravity);
            bundle.putInt("popupLocationInfo.displayId", this.JN);
            bundle.putInt("popupLocationInfo.left", this.left);
            bundle.putInt("popupLocationInfo.top", this.top);
            bundle.putInt("popupLocationInfo.right", this.right);
            bundle.putInt("popupLocationInfo.bottom", this.bottom);
            return bundle;
        }
    }

    private static final class PopupManagerHCMR1 extends PopupManager implements View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
        private boolean Iz;
        private WeakReference<View> JO;

        protected PopupManagerHCMR1(GamesClientImpl gamesClientImpl, int gravity) {
            super(gamesClientImpl, gravity);
            this.Iz = false;
        }

        private void h(View view) {
            Display display;
            int displayId = -1;
            if (gr.fz() && (display = view.getDisplay()) != null) {
                displayId = display.getDisplayId();
            }
            IBinder windowToken = view.getWindowToken();
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int width = view.getWidth();
            int height = view.getHeight();
            this.JL.JN = displayId;
            this.JL.JM = windowToken;
            this.JL.left = iArr[0];
            this.JL.top = iArr[1];
            this.JL.right = iArr[0] + width;
            this.JL.bottom = iArr[1] + height;
            if (this.Iz) {
                gS();
                this.Iz = false;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.games.internal.PopupManager
        protected void bc(int i) {
            this.JL = new PopupLocationInfo(i, null);
        }

        @Override // com.google.android.gms.games.internal.PopupManager
        public void g(View view) {
            this.JK.gF();
            if (this.JO != null) {
                View decorView = this.JO.get();
                Context context = this.JK.getContext();
                if (decorView == null && (context instanceof Activity)) {
                    decorView = ((Activity) context).getWindow().getDecorView();
                }
                if (decorView != null) {
                    decorView.removeOnAttachStateChangeListener(this);
                    ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
                    if (gr.fy()) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                    } else {
                        viewTreeObserver.removeGlobalOnLayoutListener(this);
                    }
                }
            }
            this.JO = null;
            Context context2 = this.JK.getContext();
            if (view == null && (context2 instanceof Activity)) {
                View viewFindViewById = ((Activity) context2).findViewById(R.id.content);
                if (viewFindViewById == null) {
                    viewFindViewById = ((Activity) context2).getWindow().getDecorView();
                }
                GamesLog.g("PopupManager", "You have not specified a View to use as content view for popups. Falling back to the Activity content view which may not work properly in future versions of the API. Use setViewForPopups() to set your content view.");
                view = viewFindViewById;
            }
            if (view == null) {
                GamesLog.h("PopupManager", "No content view usable to display popups. Popups will not be displayed in response to this client's calls. Use setViewForPopups() to set your content view.");
                return;
            }
            h(view);
            this.JO = new WeakReference<>(view);
            view.addOnAttachStateChangeListener(this);
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        @Override // com.google.android.gms.games.internal.PopupManager
        public void gS() {
            if (this.JL.JM != null) {
                super.gS();
            } else {
                this.Iz = this.JO != null;
            }
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            View view;
            if (this.JO == null || (view = this.JO.get()) == null) {
                return;
            }
            h(view);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View v) {
            h(v);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View v) {
            this.JK.gF();
            v.removeOnAttachStateChangeListener(this);
        }
    }

    private PopupManager(GamesClientImpl gamesClientImpl, int gravity) {
        this.JK = gamesClientImpl;
        bc(gravity);
    }

    public static PopupManager a(GamesClientImpl gamesClientImpl, int i) {
        return gr.fv() ? new PopupManagerHCMR1(gamesClientImpl, i) : new PopupManager(gamesClientImpl, i);
    }

    protected void bc(int i) {
        this.JL = new PopupLocationInfo(i, new Binder());
    }

    public void g(View view) {
    }

    public void gS() {
        this.JK.a(this.JL.JM, this.JL.gV());
    }

    public Bundle gT() {
        return this.JL.gV();
    }

    public IBinder gU() {
        return this.JL.JM;
    }

    public void setGravity(int gravity) {
        this.JL.gravity = gravity;
    }
}
