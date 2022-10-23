package comp1140.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Coordinate {
    public int x;
    public int y;
    public int index;
    /**
     * Coordinate:
     * Has fields:
     * x - the x coordinate
     * y - the y coordinate
     * index - its position in the provided location indexing system,
     * left empty in the constructor as it is not always possible to provide,
     * ie when creating the 6 coordinate array of a tile
     * Authored by Stephen Burg - u7146285 based on ideas created collaboratively with the group
     */
    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinate(int index) {
        this.index = index;
    }
    /**
     * Sets the index when possible
     * Authored by Stephen Burg - u7146285
     */
    public void setIndex(int index) {
        this.index = index;
    }
    /**
     * Takes two coordinates, returns True is they are eaual both in y and x
     * otherwise returns false
     * Authored by Stephen Burg - u7146285
     */
    public static boolean CheckEquals(Coordinate coord1, Coordinate coord2) {
        int y1= coord1.y;
        int x1= coord1.x;
        int y2= coord2.y;
        int x2= coord2.x;
        if (x2==x1&&y2==y1) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Takes two coordinates, returns True if they are adjacent
     * (ie a road could be built between them)
     * otherwise returns false
     * Authored by Stephen Burg - u7146285
     */

    public static boolean checkAdjacent(Coordinate coord1, Coordinate coord2) {
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

    private static int distance(Coordinate coord1, Coordinate coord2, Coordinate previousCoord, int acc) {
        List<Coordinate> neighbours = Board.neighbours.get(coord1);
        if (neighbours.stream().filter(coord -> CheckEquals(coord1, coord)).collect(Collectors.toList()).size() == 0) { // coord2 not found in this neighbourhood
            for (Coordinate coord : neighbours) {
                if (!CheckEquals(previousCoord, coord)) {
                    distance(coord,coord2, coord, acc + 1);
                }
            }
        }
        return acc;
    }

    public static int distance(Coordinate coord1, Coordinate coord2) {
        List<Coordinate> neighbours = Board.neighbours.get(coord1);
        if (neighbours.stream().filter(coord -> CheckEquals(coord1, coord)).collect(Collectors.toList()).size() == 0) { // coord2 not found in this neighbourhood
            for (Coordinate coord : neighbours) {
                distance(coord, coord2, coord1, 2);
            }
        }
        return 1;
    }
}
