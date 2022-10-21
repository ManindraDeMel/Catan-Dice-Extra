package comp1140.ass2.gui.backend;

import static comp1140.ass2.gui.backend.Constants.hmKnight;
/**
 * This class stores all the meta-data (eg. position, start pixels, orientation on tile, etc) of a Road structure
 *
 * Used to get the tile center coordinate in which the road is in
 * Also used to get the orientation of the road in the tile
 *
 * Authored by Arjun Raj, u7526852
 */
public class RoadStartID {
    int orientation;
    double startX;
    double startY;
    BoardTilePair boardTilePair;

    RoadStartID(int knightID, int orientation){
        boardTilePair = hmKnight.get(knightID);
        this.orientation = orientation;
        startX = boardTilePair.getStartX();
        startY = boardTilePair.getStartY();
    }

    public int getOrientation() {
        return orientation;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }
}
