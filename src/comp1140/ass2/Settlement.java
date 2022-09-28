package comp1140.ass2;

import java.util.Set;

public class Settlement extends GamePiece{
    public SettlementState state;
    public Boolean isCityable;
    public Boolean isCity;
    public Boolean isBuilt;
    public Coordinate coord;
    public int intersectionIndex;

    public Settlement(Player player, Coordinate coord, Boolean cityable, int index) {
        super(player);
        this.coord = coord;
        this.isCityable=cityable;
        this.intersectionIndex =index;
    }

    @Override
    public String toString() {
        if (isCityable)
            return "T" + coord.index;
        else
            return "S" + coord.index;
    }
}
