package comp1140.ass2;

import java.util.Set;

public class Settlement extends GamePiece{
    public SettlementState state;
    public Boolean isCityable;
    public Boolean isCity;
    public Boolean isBuilt;
    public Coordinate location;

    Settlement(Player player, Coordinate coord, Boolean cityable) {
        super(player);
        this.location = coord;
        this.isCityable=cityable;
    }
}
