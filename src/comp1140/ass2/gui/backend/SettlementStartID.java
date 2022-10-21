package comp1140.ass2.gui.backend;

import static comp1140.ass2.gui.backend.Constants.hmKnight;

/**
 * This class stores all the meta-data (eg. position, start pixels, etc) of a Settlement structure
 *
 * Used to get the coordinates of the center of the tile the Settlement is in with other info
 *
 * Authored by Arjun Raj, u7526852
 */
public class SettlementStartID {
    int isBottom;
    double startX;
    double startY;
    BoardTilePair boardTilePair;

    public SettlementStartID(int knightID, int isBottom){
        boardTilePair = hmKnight.get(knightID);
        this.isBottom = isBottom;
        startX = boardTilePair.getStartX();
        startY = boardTilePair.getStartY();
    }

    public int getIsBottom() {
        return isBottom;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }
}
