package comp1140.ass2;

import java.util.Arrays;

public class Road extends GamePiece{
    Coordinate coord1;
    Coordinate coord2;

    public Road(Player player, Coordinate coord1, Coordinate coord2) {
        super(player);
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

    @Override
    public String toString() {
        String ind = "";

        if (coord1.index < 10)
            ind += "0" + coord1.index;
        else
            ind += coord1.index;

        if (coord2.index < 10)
            ind += "0" + coord2.index;
        else
            ind += coord2.index;

        return "R" + ind;
    }
}
