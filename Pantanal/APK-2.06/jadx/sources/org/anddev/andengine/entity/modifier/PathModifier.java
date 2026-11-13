package org.anddev.andengine.entity.modifier;

import android.util.FloatMath;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.SequenceModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class PathModifier extends EntityModifier {
    private final Path mPath;
    private IPathModifierListener mPathModifierListener;
    private final SequenceModifier<IEntity> mSequenceModifier;

    public interface IPathModifierListener {
        void onPathFinished(PathModifier pathModifier, IEntity iEntity);

        void onPathStarted(PathModifier pathModifier, IEntity iEntity);

        void onPathWaypointFinished(PathModifier pathModifier, IEntity iEntity, int i);

        void onPathWaypointStarted(PathModifier pathModifier, IEntity iEntity, int i);
    }

    public PathModifier(float pDuration, Path pPath) {
        this(pDuration, pPath, null, null, IEaseFunction.DEFAULT);
    }

    public PathModifier(float pDuration, Path pPath, IEaseFunction pEaseFunction) {
        this(pDuration, pPath, null, null, pEaseFunction);
    }

    public PathModifier(float pDuration, Path pPath, IEntityModifier.IEntityModifierListener pEntityModiferListener) {
        this(pDuration, pPath, pEntityModiferListener, null, IEaseFunction.DEFAULT);
    }

    public PathModifier(float pDuration, Path pPath, IPathModifierListener pPathModifierListener) {
        this(pDuration, pPath, null, pPathModifierListener, IEaseFunction.DEFAULT);
    }

    public PathModifier(float pDuration, Path pPath, IPathModifierListener pPathModifierListener, IEaseFunction pEaseFunction) {
        this(pDuration, pPath, null, pPathModifierListener, pEaseFunction);
    }

    public PathModifier(float pDuration, Path pPath, IEntityModifier.IEntityModifierListener pEntityModiferListener, IEaseFunction pEaseFunction) {
        this(pDuration, pPath, pEntityModiferListener, null, pEaseFunction);
    }

    public PathModifier(float pDuration, Path pPath, IEntityModifier.IEntityModifierListener pEntityModiferListener, IPathModifierListener pPathModifierListener) throws IllegalArgumentException {
        this(pDuration, pPath, pEntityModiferListener, pPathModifierListener, IEaseFunction.DEFAULT);
    }

    public PathModifier(float pDuration, Path pPath, IEntityModifier.IEntityModifierListener pEntityModiferListener, IPathModifierListener pPathModifierListener, IEaseFunction pEaseFunction) throws IllegalArgumentException {
        super(pEntityModiferListener);
        int pathSize = pPath.getSize();
        if (pathSize < 2) {
            throw new IllegalArgumentException("Path needs at least 2 waypoints!");
        }
        this.mPath = pPath;
        this.mPathModifierListener = pPathModifierListener;
        MoveModifier[] moveModifiers = new MoveModifier[pathSize - 1];
        float[] coordinatesX = pPath.getCoordinatesX();
        float[] coordinatesY = pPath.getCoordinatesY();
        float velocity = pPath.getLength() / pDuration;
        int modifierCount = moveModifiers.length;
        for (int i = 0; i < modifierCount; i++) {
            float duration = pPath.getSegmentLength(i) / velocity;
            moveModifiers[i] = new MoveModifier(duration, coordinatesX[i], coordinatesX[i + 1], coordinatesY[i], coordinatesY[i + 1], null, pEaseFunction);
        }
        this.mSequenceModifier = new SequenceModifier<>(new SequenceModifier.ISubSequenceModifierListener<IEntity>() { // from class: org.anddev.andengine.entity.modifier.PathModifier.1
            @Override // org.anddev.andengine.util.modifier.SequenceModifier.ISubSequenceModifierListener
            public void onSubSequenceStarted(IModifier<IEntity> pModifier, IEntity pEntity, int pIndex) {
                if (PathModifier.this.mPathModifierListener != null) {
                    PathModifier.this.mPathModifierListener.onPathWaypointStarted(PathModifier.this, pEntity, pIndex);
                }
            }

            @Override // org.anddev.andengine.util.modifier.SequenceModifier.ISubSequenceModifierListener
            public void onSubSequenceFinished(IModifier<IEntity> pEntityModifier, IEntity pEntity, int pIndex) {
                if (PathModifier.this.mPathModifierListener != null) {
                    PathModifier.this.mPathModifierListener.onPathWaypointFinished(PathModifier.this, pEntity, pIndex);
                }
            }
        }, new IEntityModifier.IEntityModifierListener() { // from class: org.anddev.andengine.entity.modifier.PathModifier.2
            @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pEntity) {
                PathModifier.this.onModifierStarted(pEntity);
                if (PathModifier.this.mPathModifierListener != null) {
                    PathModifier.this.mPathModifierListener.onPathStarted(PathModifier.this, pEntity);
                }
            }

            @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
            public void onModifierFinished(IModifier<IEntity> pEntityModifier, IEntity pEntity) {
                PathModifier.this.onModifierFinished(pEntity);
                if (PathModifier.this.mPathModifierListener != null) {
                    PathModifier.this.mPathModifierListener.onPathFinished(PathModifier.this, pEntity);
                }
            }
        }, moveModifiers);
    }

    protected PathModifier(PathModifier pPathModifier) throws IModifier.DeepCopyNotSupportedException {
        this.mPath = pPathModifier.mPath.deepCopy();
        this.mSequenceModifier = pPathModifier.mSequenceModifier.deepCopy();
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new PathModifier(this);
    }

    public Path getPath() {
        return this.mPath;
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier
    public boolean isFinished() {
        return this.mSequenceModifier.isFinished();
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public float getSecondsElapsed() {
        return this.mSequenceModifier.getSecondsElapsed();
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public float getDuration() {
        return this.mSequenceModifier.getDuration();
    }

    public IPathModifierListener getPathModifierListener() {
        return this.mPathModifierListener;
    }

    public void setPathModifierListener(IPathModifierListener pPathModifierListener) {
        this.mPathModifierListener = pPathModifierListener;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public void reset() {
        this.mSequenceModifier.reset();
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public float onUpdate(float pSecondsElapsed, IEntity pEntity) {
        return this.mSequenceModifier.onUpdate(pSecondsElapsed, pEntity);
    }

    public static class Path {
        private final float[] mCoordinatesX;
        private final float[] mCoordinatesY;
        private int mIndex;
        private float mLength;
        private boolean mLengthChanged;

        public Path(int pLength) {
            this.mLengthChanged = false;
            this.mCoordinatesX = new float[pLength];
            this.mCoordinatesY = new float[pLength];
            this.mIndex = 0;
            this.mLengthChanged = false;
        }

        public Path(float[] pCoordinatesX, float[] pCoordinatesY) throws IllegalArgumentException {
            this.mLengthChanged = false;
            if (pCoordinatesX.length != pCoordinatesY.length) {
                throw new IllegalArgumentException("Coordinate-Arrays must have the same length.");
            }
            this.mCoordinatesX = pCoordinatesX;
            this.mCoordinatesY = pCoordinatesY;
            this.mIndex = pCoordinatesX.length;
            this.mLengthChanged = true;
        }

        public Path(Path pPath) {
            this.mLengthChanged = false;
            int size = pPath.getSize();
            this.mCoordinatesX = new float[size];
            this.mCoordinatesY = new float[size];
            System.arraycopy(pPath.mCoordinatesX, 0, this.mCoordinatesX, 0, size);
            System.arraycopy(pPath.mCoordinatesY, 0, this.mCoordinatesY, 0, size);
            this.mIndex = pPath.mIndex;
            this.mLengthChanged = pPath.mLengthChanged;
            this.mLength = pPath.mLength;
        }

        public Path deepCopy() {
            return new Path(this);
        }

        public Path to(float pX, float pY) {
            this.mCoordinatesX[this.mIndex] = pX;
            this.mCoordinatesY[this.mIndex] = pY;
            this.mIndex++;
            this.mLengthChanged = true;
            return this;
        }

        public float[] getCoordinatesX() {
            return this.mCoordinatesX;
        }

        public float[] getCoordinatesY() {
            return this.mCoordinatesY;
        }

        public int getSize() {
            return this.mCoordinatesX.length;
        }

        public float getLength() {
            if (this.mLengthChanged) {
                updateLength();
            }
            return this.mLength;
        }

        public float getSegmentLength(int pSegmentIndex) {
            float[] coordinatesX = this.mCoordinatesX;
            float[] coordinatesY = this.mCoordinatesY;
            int nextSegmentIndex = pSegmentIndex + 1;
            float dx = coordinatesX[pSegmentIndex] - coordinatesX[nextSegmentIndex];
            float dy = coordinatesY[pSegmentIndex] - coordinatesY[nextSegmentIndex];
            return FloatMath.sqrt((dx * dx) + (dy * dy));
        }

        private void updateLength() {
            float length = BitmapDescriptorFactory.HUE_RED;
            for (int i = this.mIndex - 2; i >= 0; i--) {
                length += getSegmentLength(i);
            }
            this.mLength = length;
        }
    }
}
