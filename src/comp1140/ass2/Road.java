package comp1140.ass2;

public class Road extends GamePiece{
    Coordinate coord1;
    Coordinate coord2;

    Road(Player player, Coordinate coord1, Coordinate coord2) {
        super(player);
        this.coord1 = coord1;
        this.coord2 = coord2;
    }
}
