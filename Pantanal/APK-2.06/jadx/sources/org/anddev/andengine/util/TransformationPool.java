package org.anddev.andengine.util;

import org.anddev.andengine.util.pool.GenericPool;

/* JADX INFO: loaded from: classes.dex */
public class TransformationPool {
    private static final GenericPool<Transformation> POOL = new GenericPool<Transformation>() { // from class: org.anddev.andengine.util.TransformationPool.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.anddev.andengine.util.pool.GenericPool
        public Transformation onAllocatePoolItem() {
            return new Transformation();
        }
    };

    public static Transformation obtain() {
        return POOL.obtainPoolItem();
    }

    public static void recycle(Transformation pTransformation) {
        pTransformation.setToIdentity();
        POOL.recyclePoolItem(pTransformation);
    }
}
