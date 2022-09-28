package comp1140.ass2;

public class Tile extends GamePiece{
    public Boolean used;
    public TileType tileType;
    public Coordinate[] location;
    public int tileIndex;
    public Tile(Player player, Coordinate[] coords, int index, boolean used, TileType tileType) {
        super(player);
        this.location = coords;
        this.tileIndex=index;
        this.used=used;
        this.tileType=tileType;
    }

}
