package comp1140.ass2;

import java.util.Set;

public class Settlement extends GamePiece{
    public SettlementState state;
    public Boolean isCityable;
    public Boolean isCity;
    public Boolean isBuilt;
    public Coordinate coord;
    public int intersectionIndex;
    /**
     * Settlement:
     * Has fields:
     * player - its owner if built
     * coord - its coordinate on the grid
     * cityable - whether it is upgradeable into a city
     * isBuilt - whether it is built, always instantiated false (redundant maybe as if it has an owner it is built)
     * isCity - whether it is a city, always instantiated false
     * index - its single integer index in the position sytstem provided
     * Authored by Stephen Burg - u7146285 based on ideas created collaboratively with the group
     */
    public Settlement(Player player, Coordinate coord, Boolean cityable, int index) {
        super(player);
        this.coord = coord;
        this.isCityable=cityable;
        this.isBuilt = false;
        this.isCity = false;
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
