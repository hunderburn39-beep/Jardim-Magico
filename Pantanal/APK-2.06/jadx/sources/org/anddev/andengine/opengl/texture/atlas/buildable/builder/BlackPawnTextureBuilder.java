package org.anddev.andengine.opengl.texture.atlas.buildable.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.anddev.andengine.opengl.texture.atlas.ITextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlas;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;

/* JADX INFO: loaded from: classes.dex */
public class BlackPawnTextureBuilder<T extends ITextureAtlasSource, A extends ITextureAtlas<T>> implements ITextureBuilder<T, A> {
    private static final Comparator<BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback<?>> TEXTURESOURCE_COMPARATOR = new Comparator<BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback<?>>() { // from class: org.anddev.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureBuilder.1
        @Override // java.util.Comparator
        public int compare(BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback<?> pTextureAtlasSourceWithWithLocationCallbackA, BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback<?> pTextureAtlasSourceWithWithLocationCallbackB) {
            int deltaWidth = pTextureAtlasSourceWithWithLocationCallbackB.getTextureAtlasSource().getWidth() - pTextureAtlasSourceWithWithLocationCallbackA.getTextureAtlasSource().getWidth();
            return deltaWidth != 0 ? deltaWidth : pTextureAtlasSourceWithWithLocationCallbackB.getTextureAtlasSource().getHeight() - pTextureAtlasSourceWithWithLocationCallbackA.getTextureAtlasSource().getHeight();
        }
    };
    private final int mTextureAtlasSourceSpacing;

    public BlackPawnTextureBuilder(int pTextureAtlasSourceSpacing) {
        this.mTextureAtlasSourceSpacing = pTextureAtlasSourceSpacing;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.anddev.andengine.opengl.texture.atlas.buildable.builder.ITextureBuilder
    public void pack(A a, ArrayList<BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback<T>> arrayList) throws ITextureBuilder.TextureAtlasSourcePackingException {
        Collections.sort(arrayList, TEXTURESOURCE_COMPARATOR);
        Node node = new Node(new Rect(0, 0, a.getWidth(), a.getHeight()));
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback<T> textureAtlasSourceWithWithLocationCallback = arrayList.get(i);
            ITextureAtlasSource textureAtlasSource = textureAtlasSourceWithWithLocationCallback.getTextureAtlasSource();
            Node nodeInsert = node.insert(textureAtlasSource, a.getWidth(), a.getHeight(), this.mTextureAtlasSourceSpacing);
            if (nodeInsert == null) {
                throw new ITextureBuilder.TextureAtlasSourcePackingException("Could not pack: " + textureAtlasSource.toString());
            }
            a.addTextureAtlasSource(textureAtlasSource, nodeInsert.mRect.mLeft, nodeInsert.mRect.mTop);
            textureAtlasSourceWithWithLocationCallback.getCallback().onCallback(textureAtlasSource);
        }
    }

    protected static class Rect {
        private final int mHeight;
        private final int mLeft;
        private final int mTop;
        private final int mWidth;

        public Rect(int pLeft, int pTop, int pWidth, int pHeight) {
            this.mLeft = pLeft;
            this.mTop = pTop;
            this.mWidth = pWidth;
            this.mHeight = pHeight;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getLeft() {
            return this.mLeft;
        }

        public int getTop() {
            return this.mTop;
        }

        public int getRight() {
            return this.mLeft + this.mWidth;
        }

        public int getBottom() {
            return this.mTop + this.mHeight;
        }

        public String toString() {
            return "@: " + this.mLeft + "/" + this.mTop + " * " + this.mWidth + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + this.mHeight;
        }
    }

    protected static class Node {
        private Node mChildA;
        private Node mChildB;
        private final Rect mRect;
        private ITextureAtlasSource mTextureAtlasSource;

        public Node(int pLeft, int pTop, int pWidth, int pHeight) {
            this(new Rect(pLeft, pTop, pWidth, pHeight));
        }

        public Node(Rect pRect) {
            this.mRect = pRect;
        }

        public Rect getRect() {
            return this.mRect;
        }

        public Node getChildA() {
            return this.mChildA;
        }

        public Node getChildB() {
            return this.mChildB;
        }

        public Node insert(ITextureAtlasSource pTextureAtlasSource, int pTextureWidth, int pTextureHeight, int pTextureSpacing) throws IllegalArgumentException {
            if (this.mChildA != null && this.mChildB != null) {
                Node newNode = this.mChildA.insert(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureSpacing);
                return newNode != null ? newNode : this.mChildB.insert(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureSpacing);
            }
            if (this.mTextureAtlasSource != null) {
                return null;
            }
            int textureSourceWidth = pTextureAtlasSource.getWidth();
            int textureSourceHeight = pTextureAtlasSource.getHeight();
            int rectWidth = this.mRect.getWidth();
            int rectHeight = this.mRect.getHeight();
            if (textureSourceWidth > rectWidth || textureSourceHeight > rectHeight) {
                return null;
            }
            int textureSourceWidthWithSpacing = textureSourceWidth + pTextureSpacing;
            int textureSourceHeightWithSpacing = textureSourceHeight + pTextureSpacing;
            int rectLeft = this.mRect.getLeft();
            int rectTop = this.mRect.getTop();
            boolean fitToBottomWithoutSpacing = textureSourceHeight == rectHeight && rectTop + textureSourceHeight == pTextureHeight;
            boolean fitToRightWithoutSpacing = textureSourceWidth == rectWidth && rectLeft + textureSourceWidth == pTextureWidth;
            if (textureSourceWidthWithSpacing == rectWidth) {
                if (textureSourceHeightWithSpacing == rectHeight) {
                    this.mTextureAtlasSource = pTextureAtlasSource;
                    return this;
                }
                if (fitToBottomWithoutSpacing) {
                    this.mTextureAtlasSource = pTextureAtlasSource;
                    return this;
                }
            }
            if (fitToRightWithoutSpacing) {
                if (textureSourceHeightWithSpacing == rectHeight) {
                    this.mTextureAtlasSource = pTextureAtlasSource;
                    return this;
                }
                if (fitToBottomWithoutSpacing) {
                    this.mTextureAtlasSource = pTextureAtlasSource;
                    return this;
                }
                if (textureSourceHeightWithSpacing > rectHeight) {
                    return null;
                }
                return createChildren(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureSpacing, rectWidth - textureSourceWidth, rectHeight - textureSourceHeightWithSpacing);
            }
            if (fitToBottomWithoutSpacing) {
                if (textureSourceWidthWithSpacing == rectWidth) {
                    this.mTextureAtlasSource = pTextureAtlasSource;
                    return this;
                }
                if (textureSourceWidthWithSpacing > rectWidth) {
                    return null;
                }
                return createChildren(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureSpacing, rectWidth - textureSourceWidthWithSpacing, rectHeight - textureSourceHeight);
            }
            if (textureSourceWidthWithSpacing > rectWidth || textureSourceHeightWithSpacing > rectHeight) {
                return null;
            }
            return createChildren(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureSpacing, rectWidth - textureSourceWidthWithSpacing, rectHeight - textureSourceHeightWithSpacing);
        }

        private Node createChildren(ITextureAtlasSource pTextureAtlasSource, int pTextureWidth, int pTextureHeight, int pTextureSpacing, int pDeltaWidth, int pDeltaHeight) {
            Rect rect = this.mRect;
            if (pDeltaWidth >= pDeltaHeight) {
                this.mChildA = new Node(rect.getLeft(), rect.getTop(), pTextureAtlasSource.getWidth() + pTextureSpacing, rect.getHeight());
                this.mChildB = new Node(rect.getLeft() + pTextureAtlasSource.getWidth() + pTextureSpacing, rect.getTop(), rect.getWidth() - (pTextureAtlasSource.getWidth() + pTextureSpacing), rect.getHeight());
            } else {
                this.mChildA = new Node(rect.getLeft(), rect.getTop(), rect.getWidth(), pTextureAtlasSource.getHeight() + pTextureSpacing);
                this.mChildB = new Node(rect.getLeft(), rect.getTop() + pTextureAtlasSource.getHeight() + pTextureSpacing, rect.getWidth(), rect.getHeight() - (pTextureAtlasSource.getHeight() + pTextureSpacing));
            }
            return this.mChildA.insert(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureSpacing);
        }
    }
}
