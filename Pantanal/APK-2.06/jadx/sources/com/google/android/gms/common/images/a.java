package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.internal.ex;
import com.google.android.gms.internal.ey;
import com.google.android.gms.internal.ez;
import com.google.android.gms.internal.fa;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    final C0034a Cm;
    protected int Co;
    protected int Cr;
    protected int Cn = 0;
    private boolean Cp = true;
    private boolean Cq = false;

    /* JADX INFO: renamed from: com.google.android.gms.common.images.a$a, reason: collision with other inner class name */
    static final class C0034a {
        public final Uri uri;

        public C0034a(Uri uri) {
            this.uri = uri;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0034a)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return fo.equal(((C0034a) obj).uri, this.uri);
        }

        public int hashCode() {
            return fo.hashCode(this.uri);
        }
    }

    public static final class b extends a {
        private WeakReference<ImageView> Cs;

        public b(ImageView imageView, int i) {
            super(null, i);
            fb.d(imageView);
            this.Cs = new WeakReference<>(imageView);
        }

        public b(ImageView imageView, Uri uri) {
            super(uri, 0);
            fb.d(imageView);
            this.Cs = new WeakReference<>(imageView);
        }

        private void a(ImageView imageView, Drawable drawable, boolean z, boolean z2, boolean z3) {
            boolean z4 = (z2 || z3) ? false : true;
            if (z4 && (imageView instanceof ez)) {
                int iEB = ((ez) imageView).eB();
                if (this.Co != 0 && iEB == this.Co) {
                    return;
                }
            }
            boolean zB = b(z, z2);
            Drawable drawableA = zB ? a(imageView.getDrawable(), drawable) : drawable;
            imageView.setImageDrawable(drawableA);
            if (imageView instanceof ez) {
                ez ezVar = (ez) imageView;
                ezVar.e(z3 ? this.Cm.uri : null);
                ezVar.L(z4 ? this.Co : 0);
            }
            if (zB) {
                ((ex) drawableA).startTransition(250);
            }
        }

        @Override // com.google.android.gms.common.images.a
        protected void a(Drawable drawable, boolean z, boolean z2, boolean z3) {
            ImageView imageView = this.Cs.get();
            if (imageView != null) {
                a(imageView, drawable, z, z2, z3);
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            ImageView imageView = this.Cs.get();
            ImageView imageView2 = ((b) obj).Cs.get();
            return (imageView2 == null || imageView == null || !fo.equal(imageView2, imageView)) ? false : true;
        }

        public int hashCode() {
            return 0;
        }
    }

    public static final class c extends a {
        private WeakReference<ImageManager.OnImageLoadedListener> Ct;

        public c(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            fb.d(onImageLoadedListener);
            this.Ct = new WeakReference<>(onImageLoadedListener);
        }

        @Override // com.google.android.gms.common.images.a
        protected void a(Drawable drawable, boolean z, boolean z2, boolean z3) {
            ImageManager.OnImageLoadedListener onImageLoadedListener;
            if (z2 || (onImageLoadedListener = this.Ct.get()) == null) {
                return;
            }
            onImageLoadedListener.onImageLoaded(this.Cm.uri, drawable, z3);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof c)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            c cVar = (c) obj;
            ImageManager.OnImageLoadedListener onImageLoadedListener = this.Ct.get();
            ImageManager.OnImageLoadedListener onImageLoadedListener2 = cVar.Ct.get();
            return onImageLoadedListener2 != null && onImageLoadedListener != null && fo.equal(onImageLoadedListener2, onImageLoadedListener) && fo.equal(cVar.Cm, this.Cm);
        }

        public int hashCode() {
            return fo.hashCode(this.Cm);
        }
    }

    public a(Uri uri, int i) {
        this.Co = 0;
        this.Cm = new C0034a(uri);
        this.Co = i;
    }

    private Drawable a(Context context, fa faVar, int i) {
        Resources resources = context.getResources();
        if (this.Cr <= 0) {
            return resources.getDrawable(i);
        }
        fa.a aVar = new fa.a(i, this.Cr);
        Drawable drawable = faVar.get(aVar);
        if (drawable != null) {
            return drawable;
        }
        Drawable drawable2 = resources.getDrawable(i);
        if ((this.Cr & 1) != 0) {
            drawable2 = a(resources, drawable2);
        }
        faVar.put(aVar, drawable2);
        return drawable2;
    }

    public void J(int i) {
        this.Co = i;
    }

    protected Drawable a(Resources resources, Drawable drawable) {
        return ey.a(resources, drawable);
    }

    protected ex a(Drawable drawable, Drawable drawable2) {
        if (drawable == null) {
            drawable = null;
        } else if (drawable instanceof ex) {
            drawable = ((ex) drawable).ez();
        }
        return new ex(drawable, drawable2);
    }

    void a(Context context, Bitmap bitmap, boolean z) {
        fb.d(bitmap);
        if ((this.Cr & 1) != 0) {
            bitmap = ey.a(bitmap);
        }
        a(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    void a(Context context, fa faVar) {
        a(this.Cn != 0 ? a(context, faVar, this.Cn) : null, false, true, false);
    }

    void a(Context context, fa faVar, boolean z) {
        a(this.Co != 0 ? a(context, faVar, this.Co) : null, z, false, false);
    }

    protected abstract void a(Drawable drawable, boolean z, boolean z2, boolean z3);

    protected boolean b(boolean z, boolean z2) {
        return this.Cp && !z2 && (!z || this.Cq);
    }
}
