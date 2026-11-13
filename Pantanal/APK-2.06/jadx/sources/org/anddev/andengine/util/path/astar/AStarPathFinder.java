package org.anddev.andengine.util.path.astar;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import org.anddev.andengine.util.path.IPathFinder;
import org.anddev.andengine.util.path.ITiledMap;
import org.anddev.andengine.util.path.Path;

/* JADX INFO: loaded from: classes.dex */
public class AStarPathFinder<T> implements IPathFinder<T> {
    private final IAStarHeuristic<T> mAStarHeuristic;
    private final boolean mAllowDiagonalMovement;
    private final int mMaxSearchDepth;
    private final Node[][] mNodes;
    private final ArrayList<Node> mOpenNodes;
    private final ITiledMap<T> mTiledMap;
    private final ArrayList<Node> mVisitedNodes;

    public AStarPathFinder(ITiledMap<T> pTiledMap, int pMaxSearchDepth, boolean pAllowDiagonalMovement) {
        this(pTiledMap, pMaxSearchDepth, pAllowDiagonalMovement, new EuclideanHeuristic());
    }

    public AStarPathFinder(ITiledMap<T> pTiledMap, int pMaxSearchDepth, boolean pAllowDiagonalMovement, IAStarHeuristic<T> pAStarHeuristic) {
        this.mVisitedNodes = new ArrayList<>();
        this.mOpenNodes = new ArrayList<>();
        this.mAStarHeuristic = pAStarHeuristic;
        this.mTiledMap = pTiledMap;
        this.mMaxSearchDepth = pMaxSearchDepth;
        this.mAllowDiagonalMovement = pAllowDiagonalMovement;
        this.mNodes = (Node[][]) Array.newInstance((Class<?>) Node.class, pTiledMap.getTileRows(), pTiledMap.getTileColumns());
        Node[][] nodes = this.mNodes;
        for (int x = pTiledMap.getTileColumns() - 1; x >= 0; x--) {
            for (int y = pTiledMap.getTileRows() - 1; y >= 0; y--) {
                nodes[y][x] = new Node(x, y);
            }
        }
    }

    @Override // org.anddev.andengine.util.path.IPathFinder
    public Path findPath(T pEntity, int pMaxCost, int pFromTileColumn, int pFromTileRow, int pToTileColumn, int pToTileRow) {
        Node current;
        ITiledMap<T> tiledMap = this.mTiledMap;
        if (tiledMap.isTileBlocked(pEntity, pToTileColumn, pToTileRow)) {
            return null;
        }
        ArrayList<Node> openNodes = this.mOpenNodes;
        ArrayList<Node> visitedNodes = this.mVisitedNodes;
        Node[][] nodes = this.mNodes;
        Node fromNode = nodes[pFromTileRow][pFromTileColumn];
        Node toNode = nodes[pToTileRow][pToTileColumn];
        IAStarHeuristic<T> aStarHeuristic = this.mAStarHeuristic;
        boolean allowDiagonalMovement = this.mAllowDiagonalMovement;
        int maxSearchDepth = this.mMaxSearchDepth;
        fromNode.mCost = BitmapDescriptorFactory.HUE_RED;
        fromNode.mDepth = 0;
        toNode.mParent = null;
        visitedNodes.clear();
        openNodes.clear();
        openNodes.add(fromNode);
        int currentDepth = 0;
        while (currentDepth < maxSearchDepth && !openNodes.isEmpty() && (current = openNodes.remove(0)) != toNode) {
            visitedNodes.add(current);
            for (int dX = -1; dX <= 1; dX++) {
                for (int dY = -1; dY <= 1; dY++) {
                    if ((dX != 0 || dY != 0) && (allowDiagonalMovement || dX == 0 || dY == 0)) {
                        int neighborTileColumn = dX + current.mTileColumn;
                        int neighborTileRow = dY + current.mTileRow;
                        if (!isTileBlocked(pEntity, pFromTileColumn, pFromTileRow, neighborTileColumn, neighborTileRow)) {
                            float neighborCost = current.mCost + tiledMap.getStepCost(pEntity, current.mTileColumn, current.mTileRow, neighborTileColumn, neighborTileRow);
                            Node neighbor = nodes[neighborTileRow][neighborTileColumn];
                            tiledMap.onTileVisitedByPathFinder(neighborTileColumn, neighborTileRow);
                            if (neighborCost < neighbor.mCost) {
                                if (openNodes.contains(neighbor)) {
                                    openNodes.remove(neighbor);
                                }
                                if (visitedNodes.contains(neighbor)) {
                                    visitedNodes.remove(neighbor);
                                }
                            }
                            if (!openNodes.contains(neighbor) && !visitedNodes.contains(neighbor)) {
                                neighbor.mCost = neighborCost;
                                if (neighbor.mCost <= pMaxCost) {
                                    neighbor.mExpectedRestCost = aStarHeuristic.getExpectedRestCost(tiledMap, pEntity, neighborTileColumn, neighborTileRow, pToTileColumn, pToTileRow);
                                    currentDepth = Math.max(currentDepth, neighbor.setParent(current));
                                    openNodes.add(neighbor);
                                    Collections.sort(openNodes);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (toNode.mParent == null) {
            return null;
        }
        Path path = new Path();
        for (Node tmp = nodes[pToTileRow][pToTileColumn]; tmp != fromNode; tmp = tmp.mParent) {
            path.prepend(tmp.mTileColumn, tmp.mTileRow);
        }
        path.prepend(pFromTileColumn, pFromTileRow);
        return path;
    }

    protected boolean isTileBlocked(T pEntity, int pFromTileColumn, int pFromTileRow, int pToTileColumn, int pToTileRow) {
        if (pToTileColumn < 0 || pToTileRow < 0 || pToTileColumn >= this.mTiledMap.getTileColumns() || pToTileRow >= this.mTiledMap.getTileRows()) {
            return true;
        }
        if (pFromTileColumn == pToTileColumn && pFromTileRow == pToTileRow) {
            return true;
        }
        return this.mTiledMap.isTileBlocked(pEntity, pToTileColumn, pToTileRow);
    }

    private static class Node implements Comparable<Node> {
        float mCost;
        int mDepth;
        float mExpectedRestCost;
        Node mParent;
        final int mTileColumn;
        final int mTileRow;

        public Node(int pTileColumn, int pTileRow) {
            this.mTileColumn = pTileColumn;
            this.mTileRow = pTileRow;
        }

        public int setParent(Node parent) {
            this.mDepth = parent.mDepth + 1;
            this.mParent = parent;
            return this.mDepth;
        }

        @Override // java.lang.Comparable
        public int compareTo(Node pOther) {
            float totalCost = this.mExpectedRestCost + this.mCost;
            float totalCostOther = pOther.mExpectedRestCost + pOther.mCost;
            if (totalCost < totalCostOther) {
                return -1;
            }
            if (totalCost > totalCostOther) {
                return 1;
            }
            return 0;
        }
    }
}
