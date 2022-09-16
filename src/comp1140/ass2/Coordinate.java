package comp1140.ass2;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int CoordinateToIndex(Coordinate coord, Coordinate[] coords) {
        for (int x=0; x<= coords.length; x++) {
            if (coords[x]==coord) {
                break;
            }
        }
        return x;
    }
}
