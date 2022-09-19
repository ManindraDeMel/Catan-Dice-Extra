package comp1140.ass2;

public class Road extends GamePiece{
    Coordinate[] location = new Coordinate[2];
    Road(Player player, Coordinate[] location) {
        super(player);
        this.location = location;
    }
}
