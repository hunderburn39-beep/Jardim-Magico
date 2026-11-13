package org.anddev.andengine.level;

import java.util.HashMap;
import org.anddev.andengine.level.util.constants.LevelConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: loaded from: classes.dex */
public class LevelParser extends DefaultHandler implements LevelConstants {
    private final LevelLoader.IEntityLoader mDefaultEntityLoader;
    private final HashMap<String, LevelLoader.IEntityLoader> mEntityLoaders;

    public LevelParser(LevelLoader.IEntityLoader pDefaultEntityLoader, HashMap<String, LevelLoader.IEntityLoader> pEntityLoaders) {
        this.mDefaultEntityLoader = pDefaultEntityLoader;
        this.mEntityLoaders = pEntityLoaders;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String pUri, String pLocalName, String pQualifiedName, Attributes pAttributes) throws SAXException {
        LevelLoader.IEntityLoader entityLoader = this.mEntityLoaders.get(pLocalName);
        if (entityLoader != null) {
            entityLoader.onLoadEntity(pLocalName, pAttributes);
        } else {
            if (this.mDefaultEntityLoader != null) {
                this.mDefaultEntityLoader.onLoadEntity(pLocalName, pAttributes);
                return;
            }
            throw new IllegalArgumentException("Unexpected tag: '" + pLocalName + "'.");
        }
    }
}
