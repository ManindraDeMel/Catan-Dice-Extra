package comp1140.ass2;

import java.util.Arrays;

public class Road extends GamePiece{
    Coordinate[] location = new Coordinate[2];
    Road(Player player, Coordinate[] location) {
        super(player);
        this.location = location;
    }

    @Override
    public String toString() {
        return "R" +
                location[0].y +
                '}';
    }
}
