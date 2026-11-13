package org.anddev.andengine.entity.layer.tiled.tmx;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes.dex */
public class TMXLoader {
    private final Context mContext;
    private final ITMXTilePropertiesListener mTMXTilePropertyListener;
    private final TextureManager mTextureManager;
    private final TextureOptions mTextureOptions;

    public interface ITMXTilePropertiesListener {
        void onTMXTileWithPropertiesCreated(TMXTiledMap tMXTiledMap, TMXLayer tMXLayer, TMXTile tMXTile, TMXProperties<TMXTileProperty> tMXProperties);
    }

    public TMXLoader(Context pContext, TextureManager pTextureManager) {
        this(pContext, pTextureManager, TextureOptions.DEFAULT);
    }

    public TMXLoader(Context pContext, TextureManager pTextureManager, TextureOptions pTextureOptions) {
        this(pContext, pTextureManager, pTextureOptions, null);
    }

    public TMXLoader(Context pContext, TextureManager pTextureManager, ITMXTilePropertiesListener pTMXTilePropertyListener) {
        this(pContext, pTextureManager, TextureOptions.DEFAULT, pTMXTilePropertyListener);
    }

    public TMXLoader(Context pContext, TextureManager pTextureManager, TextureOptions pTextureOptions, ITMXTilePropertiesListener pTMXTilePropertyListener) {
        this.mContext = pContext;
        this.mTextureManager = pTextureManager;
        this.mTextureOptions = pTextureOptions;
        this.mTMXTilePropertyListener = pTMXTilePropertyListener;
    }

    public TMXTiledMap loadFromAsset(Context pContext, String pAssetPath) throws TMXLoadException {
        try {
            return load(pContext.getAssets().open(pAssetPath));
        } catch (IOException e) {
            throw new TMXLoadException("Could not load TMXTiledMap from asset: " + pAssetPath, e);
        }
    }

    public TMXTiledMap load(InputStream pInputStream) throws TMXLoadException {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            TMXParser tmxParser = new TMXParser(this.mContext, this.mTextureManager, this.mTextureOptions, this.mTMXTilePropertyListener);
            xr.setContentHandler(tmxParser);
            xr.parse(new InputSource(new BufferedInputStream(pInputStream)));
            return tmxParser.getTMXTiledMap();
        } catch (IOException e) {
            throw new TMXLoadException(e);
        } catch (ParserConfigurationException e2) {
            return null;
        } catch (SAXException e3) {
            throw new TMXLoadException(e3);
        }
    }
}
