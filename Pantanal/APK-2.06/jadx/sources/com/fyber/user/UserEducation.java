package com.fyber.user;

/* JADX INFO: loaded from: classes.dex */
public enum UserEducation {
    other("other"),
    none("none"),
    high_school("high school"),
    in_college("in college"),
    some_college("some college"),
    associates("associates"),
    bachelors("bachelors"),
    masters("masters"),
    doctorate("doctorate");

    public final String education;

    UserEducation(String str) {
        this.education = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.education;
    }
}
