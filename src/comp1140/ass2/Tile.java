package comp1140.ass2;

public class Tile extends GamePiece{
    public Boolean used;
    public Resource resourceType;
    public Coordinate[] location = new Coordinate[6];
    Tile(Player player) {
        super(player);
    }
}
