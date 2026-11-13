package org.anddev.andengine.entity.layer.tiled.tmx;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TSXLoadException;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes.dex */
public class TSXLoader {
    private final Context mContext;
    private final TextureManager mTextureManager;
    private final TextureOptions mTextureOptions;

    public TSXLoader(Context pContext, TextureManager pTextureManager, TextureOptions pTextureOptions) {
        this.mContext = pContext;
        this.mTextureManager = pTextureManager;
        this.mTextureOptions = pTextureOptions;
    }

    public TMXTileSet loadFromAsset(Context pContext, int pFirstGlobalTileID, String pAssetPath) throws TSXLoadException {
        try {
            return load(pFirstGlobalTileID, pContext.getAssets().open(pAssetPath));
        } catch (IOException e) {
            throw new TSXLoadException("Could not load TMXTileSet from asset: " + pAssetPath, e);
        }
    }

    private TMXTileSet load(int pFirstGlobalTileID, InputStream pInputStream) throws TSXLoadException {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            TSXParser tsxParser = new TSXParser(this.mContext, this.mTextureManager, this.mTextureOptions, pFirstGlobalTileID);
            xr.setContentHandler(tsxParser);
            xr.parse(new InputSource(new BufferedInputStream(pInputStream)));
            return tsxParser.getTMXTileSet();
        } catch (IOException e) {
            throw new TSXLoadException(e);
        } catch (ParserConfigurationException e2) {
            return null;
        } catch (SAXException e3) {
            throw new TSXLoadException(e3);
        }
    }
}
