package com.tapjoy.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJPlacementManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class em extends fo implements Observer {
    private boolean d;
    private final Map b = new HashMap();
    private final eg c = new eg();
    private final et e = new et() { // from class: com.tapjoy.internal.em.1
        @Override // com.tapjoy.internal.et
        protected final boolean a() {
            return super.a() && !gf.c();
        }

        @Override // com.tapjoy.internal.et
        protected final /* bridge */ /* synthetic */ String a(Object obj) {
            return "AppLaunch";
        }

        @Override // com.tapjoy.internal.et
        protected final /* synthetic */ TJPlacement a(Context context, TJPlacementListener tJPlacementListener, Object obj) {
            return TJPlacementManager.createPlacement(context, "AppLaunch", true, tJPlacementListener);
        }
    };

    static {
        fo.a = new em();
    }

    public static void a() {
    }

    private em() {
    }

    @Override // java.util.Observer
    public final void update(Observable observable, Object data) {
        eo.a aVar = eo.d;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:12:0x001a  */
    /* JADX WARN: Code duplicated, block: B:8:0x000e  */
    @Override // com.tapjoy.internal.fo
    public final void a(Activity activity) {
        boolean z;
        if (activity == null) {
            if (!this.d && this.c.a()) {
                this.e.c(null);
            }
        } else {
            int taskId = activity.getTaskId();
            if (taskId == -1) {
                z = false;
            } else {
                Intent intent = activity.getIntent();
                Set<String> categories = intent.getCategories();
                if (categories != null && categories.contains("android.intent.category.LAUNCHER") && "android.intent.action.MAIN".equals(intent.getAction())) {
                    Integer num = (Integer) this.b.put(intent.getComponent().getClassName(), Integer.valueOf(taskId));
                    z = num == null || num.intValue() != taskId;
                } else {
                    z = false;
                }
            }
            if (z) {
                this.e.c(null);
            } else if (!this.d) {
                this.e.c(null);
            }
        }
        this.d = true;
    }
}
