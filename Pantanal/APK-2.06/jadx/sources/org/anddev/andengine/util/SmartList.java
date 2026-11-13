package org.anddev.andengine.util;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SmartList<T> extends ArrayList<T> {
    private static final long serialVersionUID = -8335986399182700102L;

    public SmartList() {
    }

    public SmartList(int pCapacity) {
        super(pCapacity);
    }

    public boolean remove(T pItem, ParameterCallable<T> pParameterCallable) {
        boolean removed = remove(pItem);
        if (removed) {
            pParameterCallable.call(pItem);
        }
        return removed;
    }

    public T remove(IMatcher<T> pMatcher) {
        for (int i = 0; i < size(); i++) {
            if (pMatcher.matches(get(i))) {
                return remove(i);
            }
        }
        return null;
    }

    public T remove(IMatcher<T> pMatcher, ParameterCallable<T> pParameterCallable) {
        for (int i = size() - 1; i >= 0; i--) {
            if (pMatcher.matches(get(i))) {
                T removed = remove(i);
                pParameterCallable.call(removed);
                return removed;
            }
        }
        return null;
    }

    public boolean removeAll(IMatcher<T> pMatcher) {
        boolean result = false;
        for (int i = size() - 1; i >= 0; i--) {
            if (pMatcher.matches(get(i))) {
                remove(i);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAll(IMatcher<T> pMatcher, ParameterCallable<T> pParameterCallable) {
        boolean result = false;
        for (int i = size() - 1; i >= 0; i--) {
            if (pMatcher.matches(get(i))) {
                T removed = remove(i);
                pParameterCallable.call(removed);
                result = true;
            }
        }
        return result;
    }

    public void clear(ParameterCallable<T> pParameterCallable) {
        for (int i = size() - 1; i >= 0; i--) {
            T removed = remove(i);
            pParameterCallable.call(removed);
        }
    }

    public T find(IMatcher<T> pMatcher) {
        for (int i = size() - 1; i >= 0; i--) {
            T item = get(i);
            if (pMatcher.matches(item)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<T> query(IMatcher<T> pMatcher) {
        return (ArrayList) query(pMatcher, new ArrayList());
    }

    public <L extends List<T>> L query(IMatcher<T> pMatcher, L pResult) {
        int size = size();
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (pMatcher.matches(item)) {
                pResult.add(item);
            }
        }
        return pResult;
    }

    public <S extends T> ArrayList<S> queryForSubclass(IMatcher<T> pMatcher) {
        return (ArrayList) queryForSubclass(pMatcher, new ArrayList());
    }

    public <L extends List<S>, S extends T> L queryForSubclass(IMatcher<T> pMatcher, L pResult) {
        int size = size();
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (pMatcher.matches(item)) {
                pResult.add(item);
            }
        }
        return pResult;
    }

    public void call(ParameterCallable<T> pParameterCallable) {
        for (int i = size() - 1; i >= 0; i--) {
            T item = get(i);
            pParameterCallable.call(item);
        }
    }

    public void call(IMatcher<T> pMatcher, ParameterCallable<T> pParameterCallable) {
        for (int i = size() - 1; i >= 0; i--) {
            T item = get(i);
            if (pMatcher.matches(item)) {
                pParameterCallable.call(item);
            }
        }
    }
}
