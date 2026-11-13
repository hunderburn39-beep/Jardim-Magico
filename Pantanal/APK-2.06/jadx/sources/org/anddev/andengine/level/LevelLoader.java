package org.anddev.andengine.level;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.anddev.andengine.level.util.constants.LevelConstants;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.StreamUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes.dex */
public class LevelLoader implements LevelConstants {
    private String mAssetBasePath;
    private IEntityLoader mDefaultEntityLoader;
    private final HashMap<String, IEntityLoader> mEntityLoaders;

    public interface IEntityLoader {
        void onLoadEntity(String str, Attributes attributes);
    }

    public LevelLoader() {
        this("");
    }

    public LevelLoader(String pAssetBasePath) {
        this.mEntityLoaders = new HashMap<>();
        setAssetBasePath(pAssetBasePath);
    }

    public IEntityLoader getDefaultEntityLoader() {
        return this.mDefaultEntityLoader;
    }

    public void setDefaultEntityLoader(IEntityLoader pDefaultEntityLoader) {
        this.mDefaultEntityLoader = pDefaultEntityLoader;
    }

    public void setAssetBasePath(String pAssetBasePath) {
        if (pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            this.mAssetBasePath = pAssetBasePath;
            return;
        }
        throw new IllegalStateException("pAssetBasePath must end with '/' or be lenght zero.");
    }

    protected void onAfterLoadLevel() {
    }

    protected void onBeforeLoadLevel() {
    }

    public void registerEntityLoader(String pEntityName, IEntityLoader pEntityLoader) {
        this.mEntityLoaders.put(pEntityName, pEntityLoader);
    }

    public void registerEntityLoader(String[] pEntityNames, IEntityLoader pEntityLoader) {
        HashMap<String, IEntityLoader> entityLoaders = this.mEntityLoaders;
        for (int i = pEntityNames.length - 1; i >= 0; i--) {
            entityLoaders.put(pEntityNames[i], pEntityLoader);
        }
    }

    public void loadLevelFromAsset(Context pContext, String pAssetPath) throws IOException {
        loadLevelFromStream(pContext.getAssets().open(String.valueOf(this.mAssetBasePath) + pAssetPath));
    }

    public void loadLevelFromResource(Context pContext, int pRawResourceID) throws IOException {
        loadLevelFromStream(pContext.getResources().openRawResource(pRawResourceID));
    }

    public void loadLevelFromStream(InputStream pInputStream) throws IOException {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            onBeforeLoadLevel();
            LevelParser levelParser = new LevelParser(this.mDefaultEntityLoader, this.mEntityLoaders);
            xr.setContentHandler(levelParser);
            xr.parse(new InputSource(new BufferedInputStream(pInputStream)));
            onAfterLoadLevel();
        } catch (ParserConfigurationException pe) {
            Debug.e(pe);
        } catch (SAXException se) {
            Debug.e(se);
        } finally {
            StreamUtils.close(pInputStream);
        }
    }
}
