package org.anddev.andengine.entity;

import java.util.Comparator;
import java.util.List;
import org.anddev.andengine.util.sort.InsertionSorter;

/* JADX INFO: loaded from: classes.dex */
public class ZIndexSorter extends InsertionSorter<IEntity> {
    private static ZIndexSorter INSTANCE;
    private final Comparator<IEntity> mZIndexComparator = new Comparator<IEntity>() { // from class: org.anddev.andengine.entity.ZIndexSorter.1
        @Override // java.util.Comparator
        public int compare(IEntity pEntityA, IEntity pEntityB) {
            return pEntityA.getZIndex() - pEntityB.getZIndex();
        }
    };

    private ZIndexSorter() {
    }

    public static ZIndexSorter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ZIndexSorter();
        }
        return INSTANCE;
    }

    public void sort(IEntity[] pEntities) {
        sort(pEntities, this.mZIndexComparator);
    }

    public void sort(IEntity[] pEntities, int pStart, int pEnd) {
        sort(pEntities, pStart, pEnd, this.mZIndexComparator);
    }

    public void sort(List<IEntity> pEntities) {
        sort(pEntities, this.mZIndexComparator);
    }

    public void sort(List<IEntity> pEntities, int pStart, int pEnd) {
        sort(pEntities, pStart, pEnd, this.mZIndexComparator);
    }
}
