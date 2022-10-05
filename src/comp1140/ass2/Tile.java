package comp1140.ass2;

public class Tile extends GamePiece{
    int ID;
    public Boolean used;
    public TileType tileType;
    public Coordinate[] location;
    public int tileIndex;
    /**
     * Tile:
     * Has fields:
     * player - its owner if a knight is built
     * location - an array of the 6 coordinates of the corners of the tile.
     * tileIndex - its single integer index in the position sytstem provided
     * used - whether someone has used a Knight(s) on the tile or not
     * tileType - the type of tile / resource on the tile or desert
     * Authored by Stephen Burg - u7146285 based on ideas created collaboratively with the group
     */
    public Tile(Player player, Coordinate[] coords, int index, boolean used, TileType tileType) {
        super(player);
        this.location = coords;
        this.tileIndex=index;
        this.used=used;
        this.tileType=tileType;
    }

    @Override
    public String toString() {
        String ind = "";
        if (tileIndex < 10)
            ind += "0" + tileIndex;
        else
            ind += tileIndex;

        if (used)
            return "K" + ind;
        else {
            return "J" + ind;
        }
    }
}
