package com.fyber.user;

/* JADX INFO: loaded from: classes.dex */
public enum UserEthnicity {
    asian("asian"),
    black("black"),
    hispanic("hispanic"),
    indian("indian"),
    middle_eastern("middle eastern"),
    native_american("native american"),
    pacific_islander("pacific islander"),
    white("white"),
    other("other");

    public final String ethnicity;

    UserEthnicity(String str) {
        this.ethnicity = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.ethnicity;
    }
}
