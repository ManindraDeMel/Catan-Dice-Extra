package comp1140.ass2.gui.backend;

/**
 * Used to create a City shape
 */
public class CityShape {
    public CityLeft cityLeft;
    public CityRight cityRight;
    public String id;

    public CityShape(double startX, double startY, boolean isBuilt, String id, int bottom, char player){
        this.id = id;
        cityLeft = new CityLeft(startX, startY, isBuilt, bottom, player, this);
        cityRight = new CityRight(startX, startY, isBuilt, bottom, player, this);
    }
}
