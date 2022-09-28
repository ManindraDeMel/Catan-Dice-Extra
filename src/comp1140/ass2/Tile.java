package comp1140.ass2;

public class Tile extends GamePiece{
    int ID;
    public Boolean used;
    public Resource resourceType;
    public Coordinate[] location;
    public int tileIndex;
    public Tile(Player player, Coordinate[] coords, int index) {
        super(player);
        this.location = coords;
        this.tileIndex=index;
    }


}
