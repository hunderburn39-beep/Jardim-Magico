package com.tapjoy.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class dq extends IOException {
    public du a;

    public dq(String str) {
        super(str);
        this.a = null;
    }

    static dq a() {
        return new dq("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static dq b() {
        return new dq("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static dq c() {
        return new dq("CodedInputStream encountered a malformed varint.");
    }

    static dq d() {
        return new dq("Protocol message contained an invalid tag (zero).");
    }

    static dq e() {
        return new dq("Protocol message end-group tag did not match expected tag.");
    }

    static dq f() {
        return new dq("Protocol message tag had invalid wire type.");
    }

    static dq g() {
        return new dq("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    static dq h() {
        return new dq("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }
}
