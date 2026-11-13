package org.anddev.andengine.entity.layer.tiled.tmx;

import java.util.ArrayList;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.anddev.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

/* JADX INFO: loaded from: classes.dex */
public class TMXObjectGroup implements TMXConstants {
    private final int mHeight;
    private final String mName;
    private final int mWidth;
    private final ArrayList<TMXObject> mTMXObjects = new ArrayList<>();
    private final TMXProperties<TMXObjectGroupProperty> mTMXObjectGroupProperties = new TMXProperties<>();

    public TMXObjectGroup(Attributes pAttributes) {
        this.mName = pAttributes.getValue("", "name");
        this.mWidth = SAXUtils.getIntAttributeOrThrow(pAttributes, "width");
        this.mHeight = SAXUtils.getIntAttributeOrThrow(pAttributes, "height");
    }

    public String getName() {
        return this.mName;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    void addTMXObject(TMXObject pTMXObject) {
        this.mTMXObjects.add(pTMXObject);
    }

    public ArrayList<TMXObject> getTMXObjects() {
        return this.mTMXObjects;
    }

    public void addTMXObjectGroupProperty(TMXObjectGroupProperty pTMXObjectGroupProperty) {
        this.mTMXObjectGroupProperties.add(pTMXObjectGroupProperty);
    }

    public TMXProperties<TMXObjectGroupProperty> getTMXObjectGroupProperties() {
        return this.mTMXObjectGroupProperties;
    }
}
