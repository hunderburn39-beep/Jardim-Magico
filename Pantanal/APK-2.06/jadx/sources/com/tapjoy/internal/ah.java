package com.tapjoy.internal;

import android.view.ViewGroup;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class ah {
    public static Iterable a(ViewGroup viewGroup) {
        final a aVar = new a(viewGroup);
        return new Iterable() { // from class: com.tapjoy.internal.ah.1
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return aVar;
            }
        };
    }

    static class a implements Iterator {
        private final ViewGroup a;
        private int b;
        private int c = 0;

        public a(ViewGroup viewGroup) {
            this.a = viewGroup;
            this.b = viewGroup.getChildCount();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.c < this.b;
        }

        @Override // java.util.Iterator
        public final void remove() {
            this.a.removeViewAt(this.c - 1);
        }

        @Override // java.util.Iterator
        public final /* synthetic */ Object next() {
            ViewGroup viewGroup = this.a;
            int i = this.c;
            this.c = i + 1;
            return viewGroup.getChildAt(i);
        }
    }
}
