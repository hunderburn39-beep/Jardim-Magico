package com.tapjoy.mraid.util;

/* JADX INFO: loaded from: classes.dex */
public enum TransitionStringEnum {
    DEFAULT("default"),
    DISSOLVE("dissolve"),
    FADE("fade"),
    ROLL("roll"),
    SLIDE("slide"),
    ZOOM("zoom"),
    NONE("none");

    private String a;

    TransitionStringEnum(String text) {
        this.a = text;
    }

    public final String getText() {
        return this.a;
    }

    public static TransitionStringEnum fromString(String text) {
        if (text != null) {
            for (TransitionStringEnum transitionStringEnum : values()) {
                if (text.equalsIgnoreCase(transitionStringEnum.a)) {
                    return transitionStringEnum;
                }
            }
        }
        return null;
    }
}
