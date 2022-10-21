package comp1140.ass2.gui.backend;

import comp1140.ass2.gui.Game;

/**
 * Used to create a Knight shape
 *
 * Authored by Arjun Raj, u7526852
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
        this.knightBody = new KnightBody(startX, startY, isBuilt, player, this);
        this.resourceCircle = new ResourceCircle(startX, startY, true, id, this);
        this.isUsed = true;
    }

    public KnightShape(double startX, double startY, int id, boolean isBuilt, char player, Boolean isUsed){
        this.id = id;
        this.knightHead = new KnightHead(startX, startY, isBuilt, player);
        this.knightBody = new KnightBody(startX, startY, isBuilt, player, this);
        this.resourceCircle = new ResourceCircle(startX, startY, isUsed, id, this);
        this.isUsed = isUsed;
    }

    public void clickUpdate(){
    }
}
