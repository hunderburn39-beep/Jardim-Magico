package org.anddev.andengine.util.path;

/* JADX INFO: loaded from: classes.dex */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(1, 1);

    private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$util$path$Direction;
    private final int mDeltaX;
    private final int mDeltaY;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static Direction[] valuesCustom() {
        Direction[] directionArrValuesCustom = values();
        int length = directionArrValuesCustom.length;
        Direction[] directionArr = new Direction[length];
        System.arraycopy(directionArrValuesCustom, 0, directionArr, 0, length);
        return directionArr;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$util$path$Direction() {
        int[] iArr = $SWITCH_TABLE$org$anddev$andengine$util$path$Direction;
        if (iArr == null) {
            iArr = new int[valuesCustom().length];
            try {
                iArr[DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[DOWN_LEFT.ordinal()] = 7;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[DOWN_RIGHT.ordinal()] = 8;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[UP.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[UP_LEFT.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[UP_RIGHT.ordinal()] = 6;
            } catch (NoSuchFieldError e8) {
            }
            $SWITCH_TABLE$org$anddev$andengine$util$path$Direction = iArr;
        }
        return iArr;
    }

    Direction(int pDeltaX, int pDeltaY) {
        this.mDeltaX = pDeltaX;
        this.mDeltaY = pDeltaY;
    }

    public static Direction fromDelta(int pDeltaX, int pDeltaY) {
        switch (pDeltaX) {
            case -1:
                switch (pDeltaY) {
                    case -1:
                        return UP_LEFT;
                    case 0:
                        return LEFT;
                    case 1:
                        return DOWN_LEFT;
                }
            case 0:
                switch (pDeltaY) {
                    case -1:
                        return UP;
                    case 1:
                        return DOWN;
                }
            case 1:
                switch (pDeltaY) {
                    case -1:
                        return UP_RIGHT;
                    case 0:
                        return RIGHT;
                    case 1:
                        return DOWN_RIGHT;
                }
        }
        throw new IllegalArgumentException("Unexpected deltaX: '" + pDeltaX + "' deltaY: '" + pDeltaY + "'.");
    }

    public boolean isHorizontal() {
        return this.mDeltaY == 0;
    }

    public boolean isVertical() {
        return this.mDeltaX == 0;
    }

    public int getDeltaX() {
        return this.mDeltaX;
    }

    public int getDeltaY() {
        return this.mDeltaY;
    }

    public Direction rotateLeft() {
        switch ($SWITCH_TABLE$org$anddev$andengine$util$path$Direction()[ordinal()]) {
            case 1:
                return LEFT;
            case 2:
                return UP;
            case 3:
                return DOWN;
            case 4:
                return UP;
            case 5:
                return DOWN_LEFT;
            case 6:
                return UP_LEFT;
            case 7:
                return DOWN_RIGHT;
            case 8:
                return UP_RIGHT;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Direction rotateRight() {
        switch ($SWITCH_TABLE$org$anddev$andengine$util$path$Direction()[ordinal()]) {
            case 1:
                return RIGHT;
            case 2:
                return LEFT;
            case 3:
                return UP;
            case 4:
                return DOWN;
            case 5:
                return UP_RIGHT;
            case 6:
                return DOWN_RIGHT;
            case 7:
                return UP_LEFT;
            case 8:
                return DOWN_LEFT;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Direction opposite() {
        switch ($SWITCH_TABLE$org$anddev$andengine$util$path$Direction()[ordinal()]) {
            case 1:
                return DOWN;
            case 2:
                return UP;
            case 3:
                return RIGHT;
            case 4:
                return LEFT;
            case 5:
                return DOWN_RIGHT;
            case 6:
                return DOWN_LEFT;
            case 7:
                return UP_RIGHT;
            case 8:
                return UP_LEFT;
            default:
                throw new IllegalArgumentException();
        }
    }
}
