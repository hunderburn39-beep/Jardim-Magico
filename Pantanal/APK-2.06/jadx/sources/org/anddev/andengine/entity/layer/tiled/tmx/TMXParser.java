package org.anddev.andengine.entity.layer.tiled.tmx;

import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXParseException;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TSXLoadException;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.SAXUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: loaded from: classes.dex */
public class TMXParser extends DefaultHandler implements TMXConstants {
    private final Context mContext;
    private String mDataCompression;
    private String mDataEncoding;
    private boolean mInData;
    private boolean mInImage;
    private boolean mInLayer;
    private boolean mInMap;
    private boolean mInObject;
    private boolean mInObjectGroup;
    private boolean mInProperties;
    private boolean mInProperty;
    private boolean mInTile;
    private boolean mInTileset;
    private int mLastTileSetTileID;
    private final StringBuilder mStringBuilder = new StringBuilder();
    private final TMXLoader.ITMXTilePropertiesListener mTMXTilePropertyListener;
    private TMXTiledMap mTMXTiledMap;
    private final TextureManager mTextureManager;
    private final TextureOptions mTextureOptions;

    public TMXParser(Context pContext, TextureManager pTextureManager, TextureOptions pTextureOptions, TMXLoader.ITMXTilePropertiesListener pTMXTilePropertyListener) {
        this.mContext = pContext;
        this.mTextureManager = pTextureManager;
        this.mTextureOptions = pTextureOptions;
        this.mTMXTilePropertyListener = pTMXTilePropertyListener;
    }

    TMXTiledMap getTMXTiledMap() {
        return this.mTMXTiledMap;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String pUri, String pLocalName, String pQualifiedName, Attributes pAttributes) throws SAXException {
        TMXTileSet tmxTileSet;
        if (pLocalName.equals(TMXConstants.TAG_MAP)) {
            this.mInMap = true;
            this.mTMXTiledMap = new TMXTiledMap(pAttributes);
            return;
        }
        if (pLocalName.equals(TMXConstants.TAG_TILESET)) {
            this.mInTileset = true;
            String tsxTileSetSource = pAttributes.getValue("", "source");
            if (tsxTileSetSource == null) {
                tmxTileSet = new TMXTileSet(pAttributes, this.mTextureOptions);
            } else {
                try {
                    int firstGlobalTileID = SAXUtils.getIntAttribute(pAttributes, TMXConstants.TAG_TILESET_ATTRIBUTE_FIRSTGID, 1);
                    tmxTileSet = new TSXLoader(this.mContext, this.mTextureManager, this.mTextureOptions).loadFromAsset(this.mContext, firstGlobalTileID, tsxTileSetSource);
                } catch (TSXLoadException e) {
                    throw new TMXParseException("Failed to load TMXTileSet from source: " + tsxTileSetSource, e);
                }
            }
            this.mTMXTiledMap.addTMXTileSet(tmxTileSet);
            return;
        }
        if (pLocalName.equals(TMXConstants.TAG_IMAGE)) {
            this.mInImage = true;
            ArrayList<TMXTileSet> tmxTileSets = this.mTMXTiledMap.getTMXTileSets();
            tmxTileSets.get(tmxTileSets.size() - 1).setImageSource(this.mContext, this.mTextureManager, pAttributes);
            return;
        }
        if (pLocalName.equals(TMXConstants.TAG_TILE)) {
            this.mInTile = true;
            if (this.mInTileset) {
                this.mLastTileSetTileID = SAXUtils.getIntAttributeOrThrow(pAttributes, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
                return;
            } else {
                if (this.mInData) {
                    ArrayList<TMXLayer> tmxLayers = this.mTMXTiledMap.getTMXLayers();
                    tmxLayers.get(tmxLayers.size() - 1).initializeTMXTileFromXML(pAttributes, this.mTMXTilePropertyListener);
                    return;
                }
                return;
            }
        }
        if (pLocalName.equals(TMXConstants.TAG_PROPERTIES)) {
            this.mInProperties = true;
            return;
        }
        if (this.mInProperties && pLocalName.equals(TMXConstants.TAG_PROPERTY)) {
            this.mInProperty = true;
            if (this.mInTile) {
                ArrayList<TMXTileSet> tmxTileSets2 = this.mTMXTiledMap.getTMXTileSets();
                TMXTileSet lastTMXTileSet = tmxTileSets2.get(tmxTileSets2.size() - 1);
                lastTMXTileSet.addTMXTileProperty(this.mLastTileSetTileID, new TMXTileProperty(pAttributes));
                return;
            }
            if (this.mInLayer) {
                ArrayList<TMXLayer> tmxLayers2 = this.mTMXTiledMap.getTMXLayers();
                TMXLayer lastTMXLayer = tmxLayers2.get(tmxLayers2.size() - 1);
                lastTMXLayer.addTMXLayerProperty(new TMXLayerProperty(pAttributes));
                return;
            }
            if (this.mInObject) {
                ArrayList<TMXObjectGroup> tmxObjectGroups = this.mTMXTiledMap.getTMXObjectGroups();
                TMXObjectGroup lastTMXObjectGroup = tmxObjectGroups.get(tmxObjectGroups.size() - 1);
                ArrayList<TMXObject> tmxObjects = lastTMXObjectGroup.getTMXObjects();
                TMXObject lastTMXObject = tmxObjects.get(tmxObjects.size() - 1);
                lastTMXObject.addTMXObjectProperty(new TMXObjectProperty(pAttributes));
                return;
            }
            if (this.mInObjectGroup) {
                ArrayList<TMXObjectGroup> tmxObjectGroups2 = this.mTMXTiledMap.getTMXObjectGroups();
                TMXObjectGroup lastTMXObjectGroup2 = tmxObjectGroups2.get(tmxObjectGroups2.size() - 1);
                lastTMXObjectGroup2.addTMXObjectGroupProperty(new TMXObjectGroupProperty(pAttributes));
                return;
            } else {
                if (this.mInMap) {
                    this.mTMXTiledMap.addTMXTiledMapProperty(new TMXTiledMapProperty(pAttributes));
                    return;
                }
                return;
            }
        }
        if (pLocalName.equals(TMXConstants.TAG_LAYER)) {
            this.mInLayer = true;
            this.mTMXTiledMap.addTMXLayer(new TMXLayer(this.mTMXTiledMap, pAttributes));
            return;
        }
        if (pLocalName.equals("data")) {
            this.mInData = true;
            this.mDataEncoding = pAttributes.getValue("", TMXConstants.TAG_DATA_ATTRIBUTE_ENCODING);
            this.mDataCompression = pAttributes.getValue("", TMXConstants.TAG_DATA_ATTRIBUTE_COMPRESSION);
        } else if (pLocalName.equals(TMXConstants.TAG_OBJECTGROUP)) {
            this.mInObjectGroup = true;
            this.mTMXTiledMap.addTMXObjectGroup(new TMXObjectGroup(pAttributes));
        } else {
            if (pLocalName.equals(TMXConstants.TAG_OBJECT)) {
                this.mInObject = true;
                ArrayList<TMXObjectGroup> tmxObjectGroups3 = this.mTMXTiledMap.getTMXObjectGroups();
                tmxObjectGroups3.get(tmxObjectGroups3.size() - 1).addTMXObject(new TMXObject(pAttributes));
                return;
            }
            throw new TMXParseException("Unexpected start tag: '" + pLocalName + "'.");
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] pCharacters, int pStart, int pLength) throws SAXException {
        this.mStringBuilder.append(pCharacters, pStart, pLength);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String pUri, String pLocalName, String pQualifiedName) throws Throwable {
        if (pLocalName.equals(TMXConstants.TAG_MAP)) {
            this.mInMap = false;
        } else if (pLocalName.equals(TMXConstants.TAG_TILESET)) {
            this.mInTileset = false;
        } else if (pLocalName.equals(TMXConstants.TAG_IMAGE)) {
            this.mInImage = false;
        } else if (pLocalName.equals(TMXConstants.TAG_TILE)) {
            this.mInTile = false;
        } else if (pLocalName.equals(TMXConstants.TAG_PROPERTIES)) {
            this.mInProperties = false;
        } else if (pLocalName.equals(TMXConstants.TAG_PROPERTY)) {
            this.mInProperty = false;
        } else if (pLocalName.equals(TMXConstants.TAG_LAYER)) {
            this.mInLayer = false;
        } else if (pLocalName.equals("data")) {
            boolean binarySaved = (this.mDataCompression == null || this.mDataEncoding == null) ? false : true;
            if (binarySaved) {
                ArrayList<TMXLayer> tmxLayers = this.mTMXTiledMap.getTMXLayers();
                try {
                    tmxLayers.get(tmxLayers.size() - 1).initializeTMXTilesFromDataString(this.mStringBuilder.toString().trim(), this.mDataEncoding, this.mDataCompression, this.mTMXTilePropertyListener);
                } catch (IOException e) {
                    Debug.e(e);
                }
                this.mDataCompression = null;
                this.mDataEncoding = null;
            }
            this.mInData = false;
        } else if (pLocalName.equals(TMXConstants.TAG_OBJECTGROUP)) {
            this.mInObjectGroup = false;
        } else if (pLocalName.equals(TMXConstants.TAG_OBJECT)) {
            this.mInObject = false;
        } else {
            throw new TMXParseException("Unexpected end tag: '" + pLocalName + "'.");
        }
        this.mStringBuilder.setLength(0);
    }
}
