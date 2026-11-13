package com.chartboost.sdk.impl;

import java.io.Serializable;
import java.io.Writer;

/* JADX INFO: loaded from: classes.dex */
public class bt extends Writer implements Serializable {
    private final StringBuilder a;

    public bt() {
        this.a = new StringBuilder();
    }

    public bt(int i) {
        this.a = new StringBuilder(i);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char value) {
        this.a.append(value);
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence value) {
        this.a.append(value);
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence value, int start, int end) {
        this.a.append(value, start, end);
        return this;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    @Override // java.io.Writer
    public void write(String value) {
        if (value != null) {
            this.a.append(value);
        }
    }

    @Override // java.io.Writer
    public void write(char[] value, int offset, int length) {
        if (value != null) {
            this.a.append(value, offset, length);
        }
    }

    public String toString() {
        return this.a.toString();
    }
}
