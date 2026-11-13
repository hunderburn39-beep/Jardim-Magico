package com.tapjoy.mraid.util;

import com.tapjoy.TJAdUnitConstants;

/* JADX INFO: loaded from: classes.dex */
public enum NavigationStringEnum {
    NONE("none"),
    CLOSE(TJAdUnitConstants.String.CLOSE),
    BACK("back"),
    FORWARD("forward"),
    REFRESH("refresh");

    private String a;

    NavigationStringEnum(String text) {
        this.a = text;
    }

    public final String getText() {
        return this.a;
    }

    public static NavigationStringEnum fromString(String text) {
        if (text != null) {
            for (NavigationStringEnum navigationStringEnum : values()) {
                if (text.equalsIgnoreCase(navigationStringEnum.a)) {
                    return navigationStringEnum;
                }
            }
        }
        return null;
    }
}
