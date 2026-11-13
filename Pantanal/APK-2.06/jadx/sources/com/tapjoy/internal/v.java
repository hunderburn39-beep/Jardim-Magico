package com.tapjoy.internal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class v implements bh {
    public static final v a = new v();

    @Override // com.tapjoy.internal.bj
    public final /* synthetic */ void a(OutputStream outputStream, Object obj) {
        if (((Bitmap) obj).compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
        } else {
            throw new RuntimeException();
        }
    }

    private v() {
    }

    @Override // com.tapjoy.internal.bi
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Bitmap b(final InputStream inputStream) {
        try {
            return (Bitmap) ad.a(new bf() { // from class: com.tapjoy.internal.v.1
                @Override // com.tapjoy.internal.bf, java.util.concurrent.Callable
                public final /* synthetic */ Object call() {
                    if (inputStream instanceof bg) {
                        return BitmapFactory.decodeStream(inputStream);
                    }
                    return BitmapFactory.decodeStream(new bg(inputStream));
                }
            });
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
