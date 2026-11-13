package org.anddev.andengine.entity.layer.tiled.tmx;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.zip.GZIPInputStream;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import org.anddev.andengine.collision.RectangularShapeCollisionChecker;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.anddev.andengine.entity.shape.RectangularShape;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.util.Base64InputStream;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.SAXUtils;
import org.anddev.andengine.util.StreamUtils;
import org.xml.sax.Attributes;

/* JADX INFO: loaded from: classes.dex */
public class TMXLayer extends RectangularShape implements TMXConstants {
    private final float[] mCullingVertices;
    private final int mGlobalTileIDsExpected;
    private final String mName;
    private final TMXProperties<TMXLayerProperty> mTMXLayerProperties;
    private final TMXTiledMap mTMXTiledMap;
    private final TMXTile[][] mTMXTiles;
    private final int mTileColumns;
    private final int mTileRows;
    private int mTilesAdded;

    public TMXLayer(TMXTiledMap pTMXTiledMap, Attributes pAttributes) {
        super(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, null);
        this.mCullingVertices = new float[8];
        this.mTMXLayerProperties = new TMXProperties<>();
        this.mTMXTiledMap = pTMXTiledMap;
        this.mName = pAttributes.getValue("", "name");
        this.mTileColumns = SAXUtils.getIntAttributeOrThrow(pAttributes, "width");
        this.mTileRows = SAXUtils.getIntAttributeOrThrow(pAttributes, "height");
        this.mTMXTiles = (TMXTile[][]) Array.newInstance((Class<?>) TMXTile.class, this.mTileRows, this.mTileColumns);
        this.mWidth = pTMXTiledMap.getTileWidth() * this.mTileColumns;
        float width = this.mWidth;
        this.mBaseWidth = width;
        this.mHeight = pTMXTiledMap.getTileHeight() * this.mTileRows;
        float height = this.mHeight;
        this.mBaseHeight = height;
        this.mRotationCenterX = width * 0.5f;
        this.mRotationCenterY = height * 0.5f;
        this.mScaleCenterX = this.mRotationCenterX;
        this.mScaleCenterY = this.mRotationCenterY;
        this.mGlobalTileIDsExpected = this.mTileColumns * this.mTileRows;
        setVisible(SAXUtils.getIntAttribute(pAttributes, "visible", 1) == 1);
        setAlpha(SAXUtils.getFloatAttribute(pAttributes, TMXConstants.TAG_LAYER_ATTRIBUTE_OPACITY, 1.0f));
    }

    public String getName() {
        return this.mName;
    }

    public int getTileColumns() {
        return this.mTileColumns;
    }

    public int getTileRows() {
        return this.mTileRows;
    }

    public TMXTile[][] getTMXTiles() {
        return this.mTMXTiles;
    }

    public TMXTile getTMXTile(int pTileColumn, int pTileRow) throws ArrayIndexOutOfBoundsException {
        return this.mTMXTiles[pTileRow][pTileColumn];
    }

    public TMXTile getTMXTileAt(float pX, float pY) {
        int tileRow;
        float[] localCoords = convertSceneToLocalCoordinates(pX, pY);
        TMXTiledMap tmxTiledMap = this.mTMXTiledMap;
        int tileColumn = (int) (localCoords[0] / tmxTiledMap.getTileWidth());
        if (tileColumn < 0 || tileColumn > this.mTileColumns - 1 || (tileRow = (int) (localCoords[1] / tmxTiledMap.getTileWidth())) < 0 || tileRow > this.mTileRows - 1) {
            return null;
        }
        return this.mTMXTiles[tileRow][tileColumn];
    }

    public void addTMXLayerProperty(TMXLayerProperty pTMXLayerProperty) {
        this.mTMXLayerProperties.add(pTMXLayerProperty);
    }

    public TMXProperties<TMXLayerProperty> getTMXLayerProperties() {
        return this.mTMXLayerProperties;
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    @Deprecated
    public void setRotation(float pRotation) {
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onUpdateVertexBuffer() {
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onInitDraw(GL10 pGL) {
        super.onInitDraw(pGL);
        GLHelper.enableTextures(pGL);
        GLHelper.enableTexCoordArray(pGL);
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onApplyVertices(GL10 pGL) {
        if (GLHelper.EXTENSIONS_VERTEXBUFFEROBJECTS) {
            GL11 gl11 = (GL11) pGL;
            this.mTMXTiledMap.getSharedVertexBuffer().selectOnHardware(gl11);
            GLHelper.vertexZeroPointer(gl11);
            return;
        }
        GLHelper.vertexPointer(pGL, this.mTMXTiledMap.getSharedVertexBuffer().getFloatBuffer());
    }

    @Override // org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape
    protected void drawVertices(GL10 pGL, Camera pCamera) {
        TMXTile[][] tmxTiles = this.mTMXTiles;
        int tileColumns = this.mTileColumns;
        int tileRows = this.mTileRows;
        int tileWidth = this.mTMXTiledMap.getTileWidth();
        int tileHeight = this.mTMXTiledMap.getTileHeight();
        float scaledTileWidth = tileWidth * this.mScaleX;
        float scaledTileHeight = tileHeight * this.mScaleY;
        float[] cullingVertices = this.mCullingVertices;
        RectangularShapeCollisionChecker.fillVertices(this, cullingVertices);
        float layerMinX = cullingVertices[0];
        float layerMinY = cullingVertices[1];
        float cameraMinX = pCamera.getMinX();
        float cameraMinY = pCamera.getMinY();
        float cameraWidth = pCamera.getWidth();
        float cameraHeight = pCamera.getHeight();
        float firstColumnRaw = (cameraMinX - layerMinX) / scaledTileWidth;
        int firstColumn = MathUtils.bringToBounds(0, tileColumns - 1, (int) Math.floor(firstColumnRaw));
        int lastColumn = MathUtils.bringToBounds(0, tileColumns - 1, (int) Math.ceil((cameraWidth / scaledTileWidth) + firstColumnRaw));
        float firstRowRaw = (cameraMinY - layerMinY) / scaledTileHeight;
        int firstRow = MathUtils.bringToBounds(0, tileRows - 1, (int) Math.floor(firstRowRaw));
        int lastRow = MathUtils.bringToBounds(0, tileRows - 1, (int) Math.floor((cameraHeight / scaledTileHeight) + firstRowRaw));
        int visibleTilesTotalWidth = ((lastColumn - firstColumn) + 1) * tileWidth;
        pGL.glTranslatef(firstColumn * tileWidth, firstRow * tileHeight, BitmapDescriptorFactory.HUE_RED);
        for (int row = firstRow; row <= lastRow; row++) {
            TMXTile[] tmxTileRow = tmxTiles[row];
            for (int column = firstColumn; column <= lastColumn; column++) {
                TextureRegion textureRegion = tmxTileRow[column].mTextureRegion;
                if (textureRegion != null) {
                    textureRegion.onApply(pGL);
                    pGL.glDrawArrays(5, 0, 4);
                }
                pGL.glTranslatef(tileWidth, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            }
            pGL.glTranslatef(-visibleTilesTotalWidth, tileHeight, BitmapDescriptorFactory.HUE_RED);
        }
        pGL.glLoadIdentity();
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedUpdate(float pSecondsElapsed) {
    }

    void initializeTMXTileFromXML(Attributes pAttributes, TMXLoader.ITMXTilePropertiesListener pTMXTilePropertyListener) {
        addTileByGlobalTileID(SAXUtils.getIntAttributeOrThrow(pAttributes, TMXConstants.TAG_TILE_ATTRIBUTE_GID), pTMXTilePropertyListener);
    }

    void initializeTMXTilesFromDataString(String pDataString, String pDataEncoding, String pDataCompression, TMXLoader.ITMXTilePropertiesListener pTMXTilePropertyListener) throws Throwable {
        InputStream in;
        DataInputStream dataIn = null;
        try {
            InputStream in2 = new ByteArrayInputStream(pDataString.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET));
            InputStream in3 = (pDataEncoding == null || !pDataEncoding.equals(TMXConstants.TAG_DATA_ATTRIBUTE_ENCODING_VALUE_BASE64)) ? in2 : new Base64InputStream(in2, 0);
            if (pDataCompression == null) {
                in = in3;
            } else if (pDataCompression.equals("gzip")) {
                in = new GZIPInputStream(in3);
            } else {
                throw new IllegalArgumentException("Supplied compression '" + pDataCompression + "' is not supported yet.");
            }
            DataInputStream dataIn2 = new DataInputStream(in);
            while (this.mTilesAdded < this.mGlobalTileIDsExpected) {
                try {
                    int globalTileID = readGlobalTileID(dataIn2);
                    addTileByGlobalTileID(globalTileID, pTMXTilePropertyListener);
                } catch (Throwable th) {
                    th = th;
                    dataIn = dataIn2;
                    StreamUtils.close(dataIn);
                    throw th;
                }
            }
            StreamUtils.close(dataIn2);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void addTileByGlobalTileID(int pGlobalTileID, TMXLoader.ITMXTilePropertiesListener pTMXTilePropertyListener) {
        TextureRegion tmxTileTextureRegion;
        TMXProperties<TMXTileProperty> tmxTileProperties;
        TMXTiledMap tmxTiledMap = this.mTMXTiledMap;
        int tilesHorizontal = this.mTileColumns;
        int column = this.mTilesAdded % tilesHorizontal;
        int row = this.mTilesAdded / tilesHorizontal;
        TMXTile[][] tmxTiles = this.mTMXTiles;
        if (pGlobalTileID == 0) {
            tmxTileTextureRegion = null;
        } else {
            tmxTileTextureRegion = tmxTiledMap.getTextureRegionFromGlobalTileID(pGlobalTileID);
        }
        TMXTile tmxTile = new TMXTile(pGlobalTileID, column, row, this.mTMXTiledMap.getTileWidth(), this.mTMXTiledMap.getTileHeight(), tmxTileTextureRegion);
        tmxTiles[row][column] = tmxTile;
        if (pGlobalTileID != 0 && pTMXTilePropertyListener != null && (tmxTileProperties = tmxTiledMap.getTMXTileProperties(pGlobalTileID)) != null) {
            pTMXTilePropertyListener.onTMXTileWithPropertiesCreated(tmxTiledMap, this, tmxTile, tmxTileProperties);
        }
        this.mTilesAdded++;
    }

    private int readGlobalTileID(DataInputStream pDataIn) throws IOException {
        int lowestByte = pDataIn.read();
        int secondLowestByte = pDataIn.read();
        int secondHighestByte = pDataIn.read();
        int highestByte = pDataIn.read();
        if (lowestByte < 0 || secondLowestByte < 0 || secondHighestByte < 0 || highestByte < 0) {
            throw new IllegalArgumentException("Couldn't read global Tile ID.");
        }
        return (secondLowestByte << 8) | lowestByte | (secondHighestByte << 16) | (highestByte << 24);
    }
}
