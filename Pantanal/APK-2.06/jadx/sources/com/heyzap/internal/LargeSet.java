package com.heyzap.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class LargeSet<T> {
    private Set<T> backingSet;
    private boolean whitelist;

    public LargeSet(Collection<T> from) {
        this.backingSet = new HashSet();
        this.whitelist = true;
        this.whitelist = true;
        this.backingSet.addAll(from);
    }

    public LargeSet(Collection<T> from, boolean whitelist) {
        this.backingSet = new HashSet();
        this.whitelist = true;
        this.whitelist = whitelist;
        this.backingSet.addAll(from);
    }

    public LargeSet(LargeSet<T> from) {
        this.backingSet = new HashSet();
        this.whitelist = true;
        this.backingSet = new HashSet(from.getBackingSet());
        this.whitelist = from.isWhitelist();
    }

    public LargeSet() {
        this.backingSet = new HashSet();
        this.whitelist = true;
    }

    public boolean add(T object) {
        return this.whitelist ? this.backingSet.add(object) : this.backingSet.remove(object);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean addAll(Collection collection) {
        if (collection instanceof LargeSet) {
            LargeSet other = (LargeSet) collection;
            if (this.whitelist) {
                if (other.isWhitelist()) {
                    return this.backingSet.addAll(other.getBackingSet());
                }
                Set<T> tmpBack = new HashSet<>(other.getBackingSet());
                tmpBack.removeAll(this.backingSet);
                int oldSize = size();
                this.whitelist = false;
                this.backingSet = tmpBack;
                return oldSize != size();
            }
            if (other.isWhitelist()) {
                return this.backingSet.removeAll(other.getBackingSet());
            }
            return this.backingSet.retainAll(other.getBackingSet());
        }
        if (this.whitelist) {
            return this.backingSet.addAll(collection);
        }
        return this.backingSet.removeAll(collection);
    }

    public boolean addAll() {
        int oldSize = size();
        this.whitelist = false;
        this.backingSet.clear();
        return size() != oldSize;
    }

    public void clear() {
        this.backingSet.clear();
        this.whitelist = true;
    }

    public boolean contains(Object object) {
        return (!this.whitelist) ^ this.backingSet.contains(object);
    }

    public boolean isEmpty() {
        return this.whitelist && this.backingSet.isEmpty();
    }

    public Iterator iterator() {
        if (this.whitelist) {
            return this.backingSet.iterator();
        }
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object object) {
        return this.whitelist ? this.backingSet.remove(object) : this.backingSet.add(object);
    }

    public int size() {
        return this.whitelist ? this.backingSet.size() : (-1) - this.backingSet.size();
    }

    public Object[] toArray() {
        if (this.whitelist) {
            return this.backingSet.toArray();
        }
        throw new UnsupportedOperationException();
    }

    public T[] toArray(Object[] objArr) {
        if (this.whitelist) {
            return (T[]) this.backingSet.toArray(objArr);
        }
        throw new UnsupportedOperationException();
    }

    public Set<T> intersect(Set<T> domain) {
        HashSet<T> intersection = new HashSet<>();
        for (T el : domain) {
            if (contains(el)) {
                intersection.add(el);
            }
        }
        return intersection;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainAll(Collection collection) {
        if (collection instanceof LargeSet) {
            LargeSet other = (LargeSet) collection;
            if (other.isWhitelist()) {
                Set<T> tmpBacking = new HashSet<>(other.getBackingSet());
                if (this.whitelist) {
                    tmpBacking.retainAll(this.backingSet);
                } else {
                    tmpBacking.removeAll(this.backingSet);
                }
                this.whitelist = true;
                int oldSize = size();
                this.backingSet = tmpBacking;
                return size() != oldSize;
            }
            if (this.whitelist) {
                return this.backingSet.removeAll(other.getBackingSet());
            }
            return this.backingSet.addAll(other.getBackingSet());
        }
        HashSet hashSet = new HashSet();
        for (Object o : collection) {
            if (contains(o)) {
                hashSet.add(o);
            }
        }
        this.whitelist = true;
        int oldSize2 = size();
        this.backingSet = hashSet;
        return size() != oldSize2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean removeAll(Collection collection) {
        if (collection instanceof LargeSet) {
            LargeSet other = (LargeSet) collection;
            if (this.whitelist) {
                if (other.isWhitelist()) {
                    return this.backingSet.removeAll(other.getBackingSet());
                }
                return this.backingSet.addAll(other.getBackingSet());
            }
            if (other.isWhitelist()) {
                return this.backingSet.addAll(other.getBackingSet());
            }
            return this.backingSet.removeAll(other.getBackingSet());
        }
        if (this.whitelist) {
            return this.backingSet.removeAll(collection);
        }
        return this.backingSet.addAll(collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean containsAll(Collection collection) {
        if (collection instanceof LargeSet) {
            LargeSet other = (LargeSet) collection;
            if (this.whitelist) {
                if (other.isWhitelist()) {
                    return this.backingSet.containsAll(other.getBackingSet());
                }
                return false;
            }
            if (other.isWhitelist()) {
                for (Object o : other.getBackingSet()) {
                    if (this.backingSet.contains(o)) {
                        return false;
                    }
                }
                return true;
            }
            return other.getBackingSet().containsAll(this.backingSet);
        }
        if (this.whitelist) {
            return this.backingSet.containsAll(collection);
        }
        for (Object o2 : collection) {
            if (this.backingSet.contains(o2)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LargeSet<?> largeSet = (LargeSet) o;
        if (this.whitelist != largeSet.whitelist) {
            return false;
        }
        if (this.backingSet != null) {
            if (this.backingSet.equals(largeSet.backingSet)) {
                return true;
            }
        } else if (largeSet.backingSet == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.backingSet != null ? this.backingSet.hashCode() : 0;
        return (result * 31) + (this.whitelist ? 1 : 0);
    }

    public String toString() {
        return "LargeSet{backingSet=" + this.backingSet + ", whitelist=" + this.whitelist + '}';
    }

    public Set<T> getBackingSet() {
        return this.backingSet;
    }

    public boolean isWhitelist() {
        return this.whitelist;
    }

    public static <T> LargeSet<T> ofEverything() {
        LargeSet<T> largeSet = new LargeSet<>();
        largeSet.addAll();
        return largeSet;
    }

    public static <T> LargeSet<T> of(T... values) {
        return new LargeSet<>(Arrays.asList(values));
    }

    public static <T> LargeSet<T> ofNot(T... values) {
        return new LargeSet<>(Arrays.asList(values), false);
    }

    public static <T> LargeSet<T> ofNotCollection(Collection<T> values) {
        return new LargeSet<>(values, false);
    }
}
