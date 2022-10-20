package comp1140.ass2.gui.backend;

/**
 * Used to create a Knight shape
 */
public class KnightShape {
    public KnightHead knightHead;
    public KnightBody knightBody;
    public ResourceCircle resourceCircle;
    Boolean isUsed;
    public int id;
    public KnightShape(double startX, double startY, int id, boolean isBuilt, char player){
        this.id = id;
        this.knightHead = new KnightHead(startX, startY, isBuilt, player);
        this.knightBody = new KnightBody(startX, startY, isBuilt, player);
        this.resourceCircle = new ResourceCircle(startX, startY, true);
        this.isUsed = true;
    }

    public KnightShape(double startX, double startY, int id, boolean isBuilt, char player, Boolean isUsed){
        this.id = id;
        this.knightHead = new KnightHead(startX, startY, isBuilt, player);
        this.knightBody = new KnightBody(startX, startY, isBuilt, player);
        this.resourceCircle = new ResourceCircle(startX, startY, isUsed);
        this.isUsed = isUsed;
    }

}
