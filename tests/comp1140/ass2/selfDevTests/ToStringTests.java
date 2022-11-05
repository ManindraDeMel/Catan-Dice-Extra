package comp1140.ass2.selfDevTests;

import comp1140.ass2.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.w3c.dom.html.HTMLFieldSetElement;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
public class ToStringTests {

    private ArrayList<ArrayList<GamePiece>> structuresTest = new ArrayList<ArrayList<GamePiece>>(); // Stores the structures built
    private ArrayList<ArrayList<String>> expString = new ArrayList<ArrayList<String>>(); // Stores the expected strings for structures built

    // Constructor
    public ToStringTests(){
        Player playerTest = new Player('-', "-");

        // Arraylist initialisation
        for (int i = 0; i < 5; i++) {
            structuresTest.add(new ArrayList<GamePiece>());
            expString.add(new ArrayList<String>());
        }

        // Adding Castles
        structuresTest.get(0).add(new Castle(new Player(""), 0 ));
        expString.get(0).add("C0");

        structuresTest.get(0).add(new Castle(new Player(""), 3 ));
        expString.get(0).add("C3");


        // Adding Knights Unused
        Coordinate[] coord = new Coordinate[]{new Coordinate(0), new Coordinate(0)};
        int index = 0;
        structuresTest.get(1).add(new Tile(playerTest, coord, index, false, TileType.timber));
        expString.get(1).add("J00");

        index = 9;
        structuresTest.get(1).add(new Tile(playerTest, coord, index, false, TileType.timber));
        expString.get(1).add("J09");

        index = 18;
        structuresTest.get(1).add(new Tile(playerTest, coord, index, false, TileType.timber));
        expString.get(1).add("J18");

        // Adding Knights Used
        index = 1;
        structuresTest.get(1).add(new Tile(playerTest, coord, index, true, TileType.timber));
        expString.get(1).add("K01");

        index = 10;
        structuresTest.get(1).add(new Tile(playerTest, coord, index, true, TileType.timber));
        expString.get(1).add("K10");

        index = 17;
        structuresTest.get(1).add(new Tile(playerTest, coord, index, true, TileType.timber));
        expString.get(1).add("K17");


        // Adding Roads
        Coordinate coord1 = new Coordinate(0);
        Coordinate coord2 = new Coordinate(4);
        structuresTest.get(2).add(new Road(playerTest, coord1, coord2));
        expString.get(2).add("R0004");

        coord1 = new Coordinate(35);
        coord2 = new Coordinate(40);
        structuresTest.get(2).add(new Road(playerTest, coord1, coord2));
        expString.get(2).add("R3540");

        coord1 = new Coordinate(2);
        coord2 = new Coordinate(5);
        structuresTest.get(2).add(new Road(playerTest, coord1, coord2));
        expString.get(2).add("R0205");

        coord1 = new Coordinate(21);
        coord2 = new Coordinate(27);
        structuresTest.get(2).add(new Road(playerTest, coord1, coord2));
        expString.get(2).add("R2127");


        // Adding Settlements
        coord1 = new Coordinate(51);
        structuresTest.get(3).add(new Settlement(playerTest, coord1, false, 51));
        expString.get(3).add("S51");

        coord1 = new Coordinate(44);
        structuresTest.get(3).add(new Settlement(playerTest, coord1, false, 44));
        expString.get(3).add("S44");


        // Adding Cities
//        coord1 = new Coordinate(35);
//        structuresTest.get(4).add(new Settlement(playerTest, coord1, true, 35));
//        expString.get(4).add("T35");
//
//        coord1 = new Coordinate(34);
//        structuresTest.get(4).add(new Settlement(playerTest, coord1, true, 34));
//        expString.get(4).add("T34");
    }

    @Test
    public void testCastleToString() {
        ArrayList<GamePiece> castles = structuresTest.get(0);
        ArrayList<String> exp = expString.get(0);

        for (int i = 0; i < castles.size(); i++) {
            Castle piece = (Castle) castles.get(i);
            Assertions.assertEquals(exp.get(i), piece.toString());
        }
    }

    @Test
    public void testKnightToString() {
        ArrayList<GamePiece> knights = structuresTest.get(1);
        ArrayList<String> exp = expString.get(1);

        for (int i = 0; i < knights.size(); i++) {
            Tile piece = (Tile) knights.get(i);
            Assertions.assertEquals(exp.get(i), piece.toString());
        }
    }


    @Test
    public void testRoadToString() {
        ArrayList<GamePiece> roads = structuresTest.get(2);
        ArrayList<String> exp = expString.get(2);

        for (int i = 0; i < roads.size(); i++) {
            Road piece = (Road) roads.get(i);
            Assertions.assertEquals( exp.get(i), piece.toString());
        }
    }

    @Test
    public void testSettlementToString() {
        ArrayList<GamePiece> settlements = structuresTest.get(3);
        ArrayList<String> exp = expString.get(3);

        for (int i = 0; i < settlements.size(); i++) {
            Settlement piece = (Settlement) settlements.get(i);
            Assertions.assertEquals(exp.get(i), piece.toString());
        }
    }

    @Test
    public void testCityToString() {
        ArrayList<GamePiece> cities = structuresTest.get(4);
        ArrayList<String> exp = expString.get(4);

        for (int i = 0; i < cities.size(); i++) {
            Settlement piece = (Settlement) cities.get(i);
            Assertions.assertEquals(exp.get(i), piece.toString());
        }
    }

//    @Test
//    public void playerToString() {
//    }
}
