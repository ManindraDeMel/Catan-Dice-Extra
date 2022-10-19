package comp1140.ass2;


public class Road extends GamePiece implements Comparable<Road>{
    Coordinate coord1;
    Coordinate coord2;
    /**
     * Road:
     * Has fields:
     * player - its owner if built
     * coord1 - the coordinate of the start (smaller indexed end) of the road
     * coord2 - the coordinate of the end (larger indexed end) of the road
     * Authored by Stephen Burg - u7146285 based on ideas created collaboratively with the group
     */
    public Road(Player player, Coordinate coord1, Coordinate coord2) {
        super(player);
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

    public void roadOrder() {
        Coordinate coord1 = this.coord1;
        Coordinate coord2 = this.coord2;
        if (coord1.index>coord2.index) {
            this.coord1 = coord2;
            this.coord2 = coord1;
        }
        return;
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

    @Override
    public int compareTo(Road r) {
        return (coord1 == r.coord1) ? (coord2 == r.coord2) ? 0 : coord2.index < r.coord2.index ? -1 : 1 : coord1.index < r.coord1.index ? -1 : 1;
    }
}
