package comp1140.ass2;

import java.util.Set;

public class Settlement extends GamePiece{
    public SettlementState state;
    public Boolean isCityable; // can find with location on board
    public Boolean isCity;
    public Coordinate[] location;

    Settlement(Player player, Boolean isCity, Coordinate[] location) {
        super(player);
        this.isCity = isCity;
        this.location = location;
    }
}
