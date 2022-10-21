package comp1140.ass2.gui.backend;

import static comp1140.ass2.gui.backend.Constants.HEX_HEIGHT;
import static comp1140.ass2.gui.backend.Constants.HEX_WIDTH;

/**
 * Coordinate class for tiles.
 * It stores the center pixel locations of the tiles
 * Used for Tile Indexing according to Knight Indexing
 */
public class BoardTilePair{
    int x;
    int y;
    double startX;
    double startY;
    public BoardTilePair(int x, int y){
        this.x = x;
        this.y = y;

        double xOffset = y%2==0 ? 0.5 : 0;
        this.startX = (x+xOffset) * HEX_WIDTH;
        this.startY = y * HEX_HEIGHT;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }
}