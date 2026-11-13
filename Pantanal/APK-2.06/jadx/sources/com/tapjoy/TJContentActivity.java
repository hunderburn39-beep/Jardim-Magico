package com.tapjoy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tapjoy.internal.fi;
import com.tapjoy.internal.fu;

/* JADX INFO: loaded from: classes.dex */
public class TJContentActivity extends Activity {
    private static volatile ContentProducer a;
    private ContentProducer b;
    private boolean c = false;

    public interface ContentProducer {
        void dismiss(Activity activity);

        void onActivityResult(Activity activity, int i, int i2, Intent intent);

        void show(Activity activity);
    }

    public static abstract class AbstractContentProducer implements ContentProducer {
        @Override // com.tapjoy.TJContentActivity.ContentProducer
        public void dismiss(Activity activity) {
        }

        @Override // com.tapjoy.TJContentActivity.ContentProducer
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        }
    }

    public static void start(Context context, ContentProducer producer, boolean fullscreen) {
        Intent intent = new Intent(context, (Class<?>) TJContentActivity.class);
        intent.setFlags(276889600);
        intent.putExtra("com.tapjoy.internal.content.producer.id", toIdentityString(producer));
        intent.putExtra("com.tapjoy.internal.content.fullscreen", fullscreen);
        synchronized (TJContentActivity.class) {
            a = producer;
            context.startActivity(intent);
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        if (!a(getIntent())) {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.b != null) {
            this.b.dismiss(this);
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        if (fu.a().n) {
            this.c = true;
            fi.a(this);
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        if (this.c) {
            this.c = false;
            fi.b(this);
        }
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.b.onActivityResult(this, requestCode, resultCode, data);
    }

    private boolean a(Intent intent) {
        boolean z = false;
        String stringExtra = intent.getStringExtra("com.tapjoy.internal.content.producer.id");
        if (stringExtra != null) {
            synchronized (TJContentActivity.class) {
                if (a != null && stringExtra.equals(toIdentityString(a))) {
                    this.b = a;
                    a = null;
                    if (intent.getBooleanExtra("com.tapjoy.internal.content.fullscreen", false)) {
                        getWindow().setFlags(1024, 1024);
                    }
                    this.b.show(this);
                    z = true;
                }
            }
        }
        return z;
    }

    public static String toIdentityString(Object object) {
        return object == null ? "null" : object.getClass().getName() + System.identityHashCode(object);
    }
}
