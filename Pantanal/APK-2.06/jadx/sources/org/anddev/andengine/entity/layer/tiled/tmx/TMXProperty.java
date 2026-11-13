package org.anddev.andengine.entity.layer.tiled.tmx;

import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.xml.sax.Attributes;

/* JADX INFO: loaded from: classes.dex */
public class TMXProperty implements TMXConstants {
    private final String mName;
    private final String mValue;

    public TMXProperty(Attributes pAttributes) {
        this.mName = pAttributes.getValue("", "name");
        this.mValue = pAttributes.getValue("", TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE);
    }

    public String getName() {
        return this.mName;
    }

    public String getValue() {
        return this.mValue;
    }

    public String toString() {
        return String.valueOf(this.mName) + "='" + this.mValue + "'";
    }
}
