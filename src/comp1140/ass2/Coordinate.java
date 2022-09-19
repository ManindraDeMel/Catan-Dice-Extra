package comp1140.ass2;

public class Coordinate {
    public int x;
    public int y;
    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static boolean CheckAdjacent(Coordinate coord1, Coordinate coord2) {
        int y1= coord1.y;
        int x1= coord1.x;
        int y2= coord2.y;
        int x2= coord2.x;
        if (y1>y2) {
            y1= coord2.y;
            x1= coord2.x;
            y2= coord1.y;
            x2= coord1.x;
        }
        if (y1==y2) {
            if (x1+1==x2||x1-1==x2) {
                return true;
            }
        } else if (y1+1==y2) {
            if ((y1<2)&&(x1%2==0)&&(x1+1==x2)) {
                return true;
            } else if ((y1==2)&&(x1==x2)&&((x1%2==0))) {
                return true;
            } else if ((y1>2)&&(x1%2==1)&&(x1-1==x2)) {
                return true;
            }
        }
        return false;
    }

}
