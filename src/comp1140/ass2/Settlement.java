package comp1140.ass2;

import java.util.Set;

public class Settlement extends GamePiece{
    public SettlementState state;
    public Boolean isCityable;
    public Boolean isCity;
    public Boolean isBuilt;
    public Coordinate location;
    public int intersectionIndex;

    Settlement(Player player, Coordinate coord, Boolean cityable, int index) {
        super(player);
        this.location = coord;
        this.isCityable=cityable;
        this.intersectionIndex =index;
    }

}
