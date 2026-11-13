package org.anddev.andengine.util;

import org.anddev.andengine.util.constants.DataConstants;

/* JADX INFO: loaded from: classes.dex */
public class DataUtils implements DataConstants {
    public static int unsignedByteToInt(byte bByte) {
        return bByte & 255;
    }
}
