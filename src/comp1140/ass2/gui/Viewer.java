package comp1140.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField playerTextField;
    private TextField boardTextField;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final Group board = new Group();
    private final Group structures = new Group();

    private static final double HEX_WIDTH = 140;
    private static final double HEX_HEIGHT = (int) (HEX_WIDTH * Math.sqrt(3) / 2);

    private static final int MARGIN_X = (int) (HEX_HEIGHT * 0.5);
    private static final int BOARD_X = (int) (HEX_HEIGHT * 0.75);
    private static final int MARGIN_Y = (int) (HEX_HEIGHT * 0.5);

    private static final int BOARD_Y = MARGIN_Y;

    private static final Color[] playersColour = new Color[]{Color.ORANGE, Color.RED};

    // Maps the Road index to a class that contains centre coordinates of the tile the city is in and other
    // info to create the road
    private final HashMap<String, RoadStartID> hmRoads = new HashMap<>() {{

        // Row 1
        put("0307", new RoadStartID(0, 0));
        put("0003", new RoadStartID(0, 1));
        put("0004", new RoadStartID(0, 2));
        put("0408", new RoadStartID(0, 3));
        put("0812", new RoadStartID(0, 4));
        put("0712", new RoadStartID(0, 5));

        put("0104", new RoadStartID(1, 1));
        put("0105", new RoadStartID(1, 2));
        put("0509", new RoadStartID(1, 3));
        put("0913", new RoadStartID(1, 4));
        put("0813", new RoadStartID(1, 5));

        put("0205", new RoadStartID(2, 1));
        put("0206", new RoadStartID(2, 2));
        put("0610", new RoadStartID(2, 3));
        put("1014", new RoadStartID(2, 4));
        put("0914", new RoadStartID(2, 5));

        put("1116", new RoadStartID(3, 0));
        put("0711", new RoadStartID(3, 1));
        put("1217", new RoadStartID(3, 3));
        put("1722", new RoadStartID(3, 4));
        put("1622", new RoadStartID(3, 5));

        put("1318", new RoadStartID(4, 3));
        put("1823", new RoadStartID(4, 4));
        put("1723", new RoadStartID(4, 5));

        put("1419", new RoadStartID(5, 3));
        put("1924", new RoadStartID(5, 4));
        put("1824", new RoadStartID(5, 5));

        put("1015", new RoadStartID(6, 2));
        put("1520", new RoadStartID(6, 3));
        put("2025", new RoadStartID(6, 4));
        put("1925", new RoadStartID(6, 5));

        put("2127", new RoadStartID(7, 0));
        put("1621", new RoadStartID(7, 1));
        put("2228", new RoadStartID(7, 3));
        put("2833", new RoadStartID(7, 4));
        put("2733", new RoadStartID(7, 5));

        put("2329", new RoadStartID(8, 3));
        put("2934", new RoadStartID(8, 4));
        put("2834", new RoadStartID(8, 5));

        put("2430", new RoadStartID(9, 3));
        put("3035", new RoadStartID(9, 4));
        put("2935", new RoadStartID(9, 5));

        put("2531", new RoadStartID(11, 3));
        put("3136", new RoadStartID(11, 4));
        put("3036", new RoadStartID(11, 5));

        put("2026", new RoadStartID(12, 2));
        put("2632", new RoadStartID(12, 3));
        put("3237", new RoadStartID(12, 4));
        put("3137", new RoadStartID(12, 5));

        put("3338", new RoadStartID(13, 0));
        put("3439", new RoadStartID(13, 3));
        put("3943", new RoadStartID(13, 4));
        put("3843", new RoadStartID(13, 5));

        put("3540", new RoadStartID(14, 3));
        put("4044", new RoadStartID(14, 4));
        put("3944", new RoadStartID(14, 5));

        put("3641", new RoadStartID(15, 3));
        put("4145", new RoadStartID(15, 4));
        put("4045", new RoadStartID(15, 5));

        put("3742", new RoadStartID(16, 3));
        put("4246", new RoadStartID(16, 4));
        put("4146", new RoadStartID(16, 5));

        put("4347", new RoadStartID(17, 0));
        put("4448", new RoadStartID(17, 3));
        put("4851", new RoadStartID(17, 4));
        put("4751", new RoadStartID(17, 5));

        put("4549", new RoadStartID(18, 3));
        put("4952", new RoadStartID(18, 4));
        put("4852", new RoadStartID(18, 5));

        put("4650", new RoadStartID(19, 3));
        put("5043", new RoadStartID(19, 4));
        put("4953", new RoadStartID(19, 5));
    }};

    // Maps the Settlement index to a class that contains centre coordinates of the tile the settlement is in
    private final HashMap<String, SettlementStartID> hmSettlements = new HashMap<>() {{

        put("00", new SettlementStartID(0, 0));
        put("02", new SettlementStartID(2, 0));

        put("08", new SettlementStartID(4, 0));
        put("09", new SettlementStartID(5, 0));

        put("16", new SettlementStartID(7, 0));
        put("20", new SettlementStartID(12, 0));

        put("33", new SettlementStartID(7, 1));
        put("37", new SettlementStartID(12, 1));

        put("44", new SettlementStartID(14, 1));
        put("45", new SettlementStartID(15, 1));

        put("51", new SettlementStartID(17, 1));
        put("53", new SettlementStartID(19, 1));
    }};

    // Maps the City index to a class that contains centre coordinates of the tile the city is in
    private final HashMap<String, CityStartID> hmCities = new HashMap<>() {{

        put("01", new CityStartID(1, 0));

        put("07", new CityStartID(3, 0));
        put("10", new CityStartID(6, 0));

        put("17", new CityStartID(8, 0));
        put("18", new CityStartID(9, 0));
        put("19", new CityStartID(11, 0));

        put("34", new CityStartID(8, 1));
        put("35", new CityStartID(9, 1));
        put("36", new CityStartID(11, 1));

        put("43", new CityStartID(13, 1));
        put("46", new CityStartID(16, 1));

        put("52", new CityStartID(18, 1));
    }};

    // Maps the Knight index to a class that contains centre coordinates of the tile the Knight is in
    private final HashMap<Integer, BoardTilePair> hmKnight = new HashMap<>() {{

        // Row 1
        put(0, new BoardTilePair(1, 0));
        put(1, new BoardTilePair(2, 0));
        put(2, new BoardTilePair(3, 0));

        // Row 2
        put(3, new BoardTilePair(1, 1));
        put(4, new BoardTilePair(2, 1));
        put(5, new BoardTilePair(3, 1));
        put(6, new BoardTilePair(4, 1));

        // Row 3
        put(7, new BoardTilePair(0, 2));
        put(8, new BoardTilePair(1, 2));
        put(9, new BoardTilePair(2, 2));
        put(10, new BoardTilePair(2, 2));
        put(11, new BoardTilePair(3, 2));
        put(12, new BoardTilePair(4, 2));

        // Row 4
        put(13, new BoardTilePair(1, 3));
        put(14, new BoardTilePair(2, 3));
        put(15, new BoardTilePair(3, 3));
        put(16, new BoardTilePair(4, 3));

        // Row 5
        put(17, new BoardTilePair(1, 4));
        put(18, new BoardTilePair(2, 4));
        put(19, new BoardTilePair(3, 4));
    }};

    // Used to create a tile shape
    class Hexagon extends Polygon {
        double startX, startY;

        Hexagon(double startX, double startY) {
            this.startX = startX;
            this.startY = startY;
            this.setLayoutX(startX);
            this.setLayoutY(startY);

            this.addHexagonPoints();

            Color fillColour;
            Color strokeColour;
            fillColour = Color.DARKBLUE;
            strokeColour = Color.LIGHTBLUE;

            this.setStroke(strokeColour);
            this.setStrokeWidth(2.0);
            this.setStrokeType(StrokeType.INSIDE);
            this.setFill(fillColour);
        }

        /**
         * Add the points necessary for each hexagon in the game.
         */
        private void addHexagonPoints() {
            double sideLength = HEX_WIDTH * 3 / 5;
            double bearing = 0;
            for (int i = 0; i < 6; i++) {
                double y = Math.cos(Math.PI / 18 * bearing) * sideLength;
                double x = Math.sin(Math.PI / 18 * bearing) * sideLength;
                this.getPoints().add(x);
                this.getPoints().add(y);
                bearing += 6;
            }
        }
    }

    // Used to create a Knight's Head shape
    class KnightHead extends Circle{
        double startX, startY;
        KnightHead(double startX, double startY, boolean isBuilt, char player) {
            this.startX = startX;
            this.startY = startY;

            double centerX = startX;
            double centerY = startY - HEX_HEIGHT/5;
            double radius = HEX_HEIGHT/15;

            this.setLayoutX(startX);
            this.setLayoutY(centerY);
            this.setRadius(radius);

            Color fillColour;
            Color strokeColour;
            if (isBuilt == false) {
                fillColour = Color.DARKGRAY;
                strokeColour = Color.LIGHTGRAY;
            }
            else {
                strokeColour = Color.BLUE;
                if (player == 'W')
                    fillColour = playersColour[0];
                else
                    fillColour = playersColour[1];
            }

            this.setStroke(strokeColour);
            this.setStrokeWidth(2.0);
            this.setStrokeType(StrokeType.INSIDE);
            this.setFill(fillColour);
        }
    }

    // Used to create a Knight's Body shape
    class KnightBody extends Rectangle{

        double startX, startY;
        KnightBody(double startX, double startY, boolean isBuilt, char player) {
            this.startX = startX;
            this.startY = startY;

            double centerX = startX;
            double centerY = startY - HEX_HEIGHT/5;
            double radius = HEX_HEIGHT/15;

            double width = radius*2.3;
            double height = radius*3.5;

            this.setX(centerX - width/2);
            this.setY(centerY + radius*0.7);
            this.setWidth(width);
            this.setHeight(height);

            Color fillColour;
            Color strokeColour;
            if (isBuilt == false) {
                fillColour = Color.DARKGRAY;
                strokeColour = Color.LIGHTGRAY;
            }
            else {
                strokeColour = Color.BLUE;
                if (player == 'W')
                    fillColour = playersColour[0];
                else
                    fillColour = playersColour[1];
            }

            this.setStroke(strokeColour);
            this.setStrokeWidth(2.0);
            this.setStrokeType(StrokeType.INSIDE);
            this.setFill(fillColour);
        }
    }

    // Used to create a Knight shape
    class Knight {
        KnightHead knightHead;
        KnightBody knightBody;
        ResourceCircle resourceCircle;
        Boolean isUsed;
        int id;
        Knight(double startX, double startY, int id, boolean isBuilt, char player){
            this.id = id;
            this.knightHead = new KnightHead(startX, startY, isBuilt, player);
            this.knightBody = new KnightBody(startX, startY, isBuilt, player);
            this.resourceCircle = new ResourceCircle(startX, startY, true);
            this.isUsed = true;
        }

        Knight(double startX, double startY, int id, boolean isBuilt, char player, Boolean isUsed){
            this.id = id;
            this.knightHead = new KnightHead(startX, startY, isBuilt, player);
            this.knightBody = new KnightBody(startX, startY, isBuilt, player);
            this.resourceCircle = new ResourceCircle(startX, startY, isUsed);
            this.isUsed = isUsed;
        }

    }

    // Used to create the resource circle shape under the Knight
    class ResourceCircle extends Circle{
        double startX, startY;
        ResourceCircle(double startX, double startY, Boolean isUsed){
            double centerX = startX;
            double centerY = startY + HEX_HEIGHT/5;
            double radius = HEX_HEIGHT/8;

            this.setCenterX(centerX);
            this.setCenterY(centerY);
            this.setRadius(radius);

            if (isUsed){
                this.setFill(Color.WHITE);
            }
            else {
                this.setFill(Color.DARKGRAY);
            }

            this.setStrokeWidth(2);
            this.setStrokeMiterLimit(10);
            this.setStrokeType(StrokeType.INSIDE);
            this.setStroke(Color.valueOf("0x000000"));
        }
    }

    // Used to create road shape
    class Road extends Rectangle{
        double height, width, startX, startY;
        int orientation;
        String id;

        Road(double startX, double startY, boolean isBuilt, String id, int orientation, char player){
            this.startX = startX;
            this.startY = startY;
            this.orientation = orientation;
            this.id = id;

            double sideLength = HEX_WIDTH * 3 / 5;
            this.width = HEX_WIDTH/8;
            this.height = width*2.5;

            double centerX = startX-HEX_WIDTH/2 - width/2;
            double centerY = startY - (sideLength - height)/2;

            this.setX(centerX);
            this.setY(centerY);
            this.setWidth(this.width);
            this.setHeight(this.height);

            Rotate rotate = new Rotate();
            rotate.setPivotX(startX);
            rotate.setPivotY(startY);
            this.getTransforms().addAll(rotate);
            rotate.setAngle(30*this.orientation);
            this.getTransforms().add(rotate);

            Color fillColour;
            Color strokeColour;
            if (isBuilt == false) {
                fillColour = Color.DARKGRAY;
                strokeColour = Color.LIGHTGRAY;
            }
            else {
                strokeColour = Color.BLUE;
                if (player == 'W')
                    fillColour = playersColour[0];
                else
                    fillColour = playersColour[1];
            }

            this.setStroke(strokeColour);
            this.setStrokeWidth(2.0);
            this.setStrokeType(StrokeType.INSIDE);
            this.setFill(fillColour);
        }
    }

    // Used to create Settlement shape
    class Settlement extends Polygon{
        String id;
        double centerX;
        double centerY;

        Settlement (double startX, double startY, boolean isBuilt, String id, int bottom, char player){
            this.id = id;

            double sideLength = HEX_WIDTH * 3 / 5;
            int scale = 7;

            Double[] coordinates = new Double[]{
                    0.0, 0.0,
                    HEX_WIDTH/scale, 0.0,
                    HEX_WIDTH/scale, -HEX_WIDTH/(scale+1),
                    HEX_WIDTH/(scale*2), -1.3*(HEX_WIDTH/(scale+1)),
                    0.0, -HEX_WIDTH/(scale+1)
            };

            this.getPoints().addAll(coordinates);

            if(bottom == 1){
                // down
                this.centerX = startX - (HEX_WIDTH/scale)/2;
                this.centerY = startY + HEX_WIDTH/2 + (HEX_WIDTH/(scale+1));
            }else {
                // up
                this.centerX = startX - (HEX_WIDTH/scale)/2;
                this.centerY = startY - HEX_HEIGHT/2 - (HEX_WIDTH/(scale+1))/2;
            }

            this.setLayoutX(this.centerX);
            this.setLayoutY(this.centerY);

            Color fillColour;
            Color strokeColour;
            if (isBuilt == false) {
                fillColour = Color.DARKGRAY;
                strokeColour = Color.LIGHTGRAY;
            }
            else {
                strokeColour = Color.BLUE;
                if (player == 'W')
                    fillColour = playersColour[0];
                else
                    fillColour = playersColour[1];
            }

            this.setStroke(strokeColour);
            this.setStrokeWidth(2.0);
            this.setStrokeType(StrokeType.INSIDE);
            this.setFill(fillColour);
        }
    }

    // Used to create the right half of a City shape
    class CityRight extends Polygon {
        CityRight (double startX, double startY, boolean isBuilt, int bottom, char player){

            double sideLength = HEX_WIDTH * 3 / 5;
            int scale = 7;
            double centerX;
            double centerY;
            Double[] coordinates = new Double[]{
                    0.0, 0.0,
                    HEX_WIDTH/scale, 0.0,
                    HEX_WIDTH/scale, -HEX_WIDTH/(scale-1),
                    HEX_WIDTH/(scale*2), -1.3*(HEX_WIDTH/(scale-1)),
                    0.0, -HEX_WIDTH/(scale-1)
            };

            this.getPoints().addAll(coordinates);

            if(bottom == 1){
                // down
                centerX = startX;
                centerY = startY + HEX_WIDTH/2 + (HEX_WIDTH/(scale+1));
            }else {
                // up
                centerX = startX;
                centerY = startY - HEX_HEIGHT/2 - (HEX_WIDTH/(scale+1))/2;
            }

            this.setLayoutX(centerX);
            this.setLayoutY(centerY);

            Color fillColour;
            Color strokeColour;
            if (isBuilt == false) {
                fillColour = Color.DARKGRAY;
                strokeColour = Color.LIGHTGRAY;
            }
            else {
                strokeColour = Color.BLUE;
                if (player == 'W')
                    fillColour = playersColour[0];
                else
                    fillColour = playersColour[1];
            }

            this.setStroke(strokeColour);
            this.setStrokeWidth(2.0);
            this.setStrokeType(StrokeType.INSIDE);
            this.setFill(fillColour);
        }
    }

    // Used to create the left half of a City shape
    class CityLeft extends Polygon {
        CityLeft (double startX, double startY, boolean isBuilt, int bottom, char player){

            double sideLength = HEX_WIDTH * 3 / 5;
            int scale = 7;
            double centerX;
            double centerY;
            Double[] coordinates = new Double[]{
                    0.0, 0.0,
                    HEX_WIDTH/scale, 0.0,
                    HEX_WIDTH/scale, -HEX_WIDTH/(scale+1),
                    HEX_WIDTH/(scale*2), -1.3*(HEX_WIDTH/(scale+1)),
                    0.0, -HEX_WIDTH/(scale+1)
            };

            this.getPoints().addAll(coordinates);

            if(bottom == 1){
                // down
                centerX = startX - (HEX_WIDTH/scale);
                centerY = startY + HEX_WIDTH/2 + (HEX_WIDTH/(scale+1));
            }else {
                // up
                centerX = startX - (HEX_WIDTH/scale);
                centerY = startY - HEX_HEIGHT/2 - (HEX_WIDTH/(scale+1))/2;
            }

            this.setLayoutX(centerX);
            this.setLayoutY(centerY);

            Color fillColour;
            Color strokeColour;
            if (isBuilt == false) {
                fillColour = Color.DARKGRAY;
                strokeColour = Color.LIGHTGRAY;
            }
            else {
                strokeColour = Color.BLUE;
                if (player == 'W')
                    fillColour = playersColour[0];
                else
                    fillColour = playersColour[1];
            }

            this.setStroke(strokeColour);
            this.setStrokeWidth(2.0);
            this.setStrokeType(StrokeType.INSIDE);
            this.setFill(fillColour);
        }
    }

    // Used to create a City shape
    class City{
        CityLeft cityLeft;
        CityRight cityRight;
        String id;

        City (double startX, double startY, boolean isBuilt, String id, int bottom, char player){
            this.id = id;
            cityLeft = new CityLeft(startX, startY, isBuilt, bottom, player);
            cityRight = new CityRight(startX, startY, isBuilt, bottom, player);
        }
    }

    // Used to create a Castle shape
    class Castle extends Polygon{
        int id;
        double centerX;
        double centerY;

        Castle (boolean isBuilt, int id, char player){
            double scale = 3.0;
            double right = 4.65;
            double top = 4.4;
            this.id = id;

            switch (this.id){
                // Top Right
                case 0 -> {
                    centerX = HEX_WIDTH * right;
                    centerY = 0;
                }
                // Bottom Right
                case 1 -> {
                    centerX = HEX_WIDTH * right;
                    centerY = HEX_HEIGHT * top;
                }
                // Bottom Left
                case 2 -> {
                    centerX = 0;
                    centerY = HEX_HEIGHT * top;
                }
                // Top Left
                case 3 -> {
                    centerX = 0;
                    centerY = 0;
                }
            }

            Double[] coordinates = new Double[]{
                    0.0, 0.0,
                    HEX_WIDTH/scale, 0.0,
                    HEX_WIDTH/scale, -HEX_WIDTH/(scale+1),
                    (HEX_WIDTH/scale)*0.75, -HEX_WIDTH/(scale+1),
                    (HEX_WIDTH/scale)*0.75, -HEX_WIDTH/(scale-0.2),
                    HEX_WIDTH/(scale*2), -(HEX_WIDTH/(scale-0.75)), // top nose coordinate
                    HEX_WIDTH/scale - (HEX_WIDTH/scale)*0.75, -HEX_WIDTH/(scale-0.2),
                    HEX_WIDTH/scale - (HEX_WIDTH/scale)*0.75, -HEX_WIDTH/(scale+1),
                    0.0, -HEX_WIDTH/(scale+1)
            };

            this.getPoints().addAll(coordinates);

            this.setLayoutX(centerX);
            this.setLayoutY(centerY);

            Color fillColour;
            Color strokeColour;
            if (isBuilt == false) {
                fillColour = Color.DARKGRAY;
                strokeColour = Color.LIGHTGRAY;
            }
            else {
                strokeColour = Color.BLUE;
                if (player == 'W')
                    fillColour = playersColour[0];
                else
                    fillColour = playersColour[1];
            }

            this.setStroke(strokeColour);
            this.setStrokeWidth(2.0);
            this.setStrokeType(StrokeType.INSIDE);
            this.setFill(fillColour);
        }
    }

    // Used for Tile Indexing according to Knight Indexing
    class BoardTilePair{
        int x;
        int y;
        double startX;
        double startY;
        public BoardTilePair(int x, int y){
            this.x = x;
            this.y = y;

            double xOffset = y%2==0 ? 0.5 : 0;
            this.startX = (x+xOffset) * HEX_WIDTH;
            this.startY = y * HEX_HEIGHT;
        }

        public double getStartX() {
            return startX;
        }

        public double getStartY() {
            return startY;
        }
    }

    // Used to get the tile center coordinate in which the road is in
    // Also used to get the orientation of the road in the tile
    class RoadStartID {
        int orientation;
        double startX;
        double startY;
        BoardTilePair boardTilePair;

        RoadStartID(int knightID, int orientation){
            boardTilePair = hmKnight.get(knightID);
            this.orientation = orientation;
            startX = boardTilePair.getStartX();
            startY = boardTilePair.getStartY();
        }

        public int getOrientation() {
            return orientation;
        }

        public double getStartX() {
            return startX;
        }

        public double getStartY() {
            return startY;
        }
    }

    // Used to get the coordinates of the center of the tile the knight is in
    String getStartKnight(int id){
        String start = "";

        if (id == 9){
            start += Double.toString(hmKnight.get(id).getStartX() - HEX_HEIGHT/7.5);
        } else if (id == 10) {
            start += Double.toString(hmKnight.get(id).getStartX() + HEX_HEIGHT/7.5);
        }
        else {
            start += Double.toString(hmKnight.get(id).getStartX());
        }

        start += "\n" + Double.toString(hmKnight.get(id).getStartY());

        return start;
    }

    // Used to get the coordinates of the center of the tile the Settlement is in with other info
    class SettlementStartID {
        int isBottom;
        double startX;
        double startY;
        BoardTilePair boardTilePair;

        public SettlementStartID(int knightID, int isBottom){
            boardTilePair = hmKnight.get(knightID);
            this.isBottom = isBottom;
            startX = boardTilePair.getStartX();
            startY = boardTilePair.getStartY();
        }

        public int getIsBottom() {
            return isBottom;
        }

        public double getStartX() {
            return startX;
        }

        public double getStartY() {
            return startY;
        }
    }

    // Used to get the coordinates of the center of the tile the City is in with other info
    class CityStartID {
        int isBottom;
        double startX;
        double startY;
        BoardTilePair boardTilePair;

        public CityStartID(int knightID, int isBottom){
            boardTilePair = hmKnight.get(knightID);
            this.isBottom = isBottom;
            startX = boardTilePair.getStartX();
            startY = boardTilePair.getStartY();
        }

        public int getIsBottom() {
            return isBottom;
        }

        public double getStartX() {
            return startX;
        }

        public double getStartY() {
            return startY;
        }
    }

    // Creates the tiles
    private void makeBoard() {
        this.board.setLayoutX(BOARD_X + MARGIN_X);
        this.board.setLayoutY(BOARD_Y + MARGIN_Y);
        this.board.getChildren().clear();

        this.structures.setLayoutX(BOARD_X + MARGIN_X);
        this.structures.setLayoutY(BOARD_Y + MARGIN_Y);
        this.structures.getChildren().clear();

        for (int y = 0; y < 5; y++) {
            for (int x= 0; x < 5; x++) {

            if (((y==0 || y==4) && (x==0 || x==4)) || (y==1 && x==0) || (y==3 && x==0)){
                continue;
            }
                double xOffset = y%2==0 ? 0.5 : 0;
                double startX = (x+xOffset) * HEX_WIDTH;
                double startY = y * HEX_HEIGHT;

                Hexagon hexagon = new Hexagon(startX, startY);
                this.board.getChildren().add(hexagon);
            }
        }
        this.board.toBack();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Show the state of a (single player's) board in the window.
     *
     * @param boardState The string representation of the board state.
     */
    void displayState(String boardState) {
        // FIXME Task 6: implement the state viewer
        /////////////////////////////////////////////

        // Getting strings of different sections
        String turn = boardState.substring(0, boardState.indexOf('W', 1));
        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));
//        String wScore = boardState.substring();

        System.out.println("BoardState: " + boardState);
        System.out.println("Turn: " + turn);
        System.out.println("PlayerW: " + playerW);
        System.out.println("PlayerX: " + playerX);
//        System.out.println("wScore: " + wScore);

        // Extracting strings of placements of each player
        String placementsW = playerW.substring(1);
        String placementsX = playerX.substring(1);

        // Arrays to store the instance of each game piece
        // index 0: castles
        // index 1: knights
        // index 2: roads
        // index 3: settlement
        // index 4: city
        ArrayList<ArrayList<Object>> placementsArrW = new ArrayList<ArrayList<Object>>();
        for (int i = 0; i < 5; i++) {
            placementsArrW.add(new ArrayList<>());
        }

        ArrayList<ArrayList<Object>> placementsArrX = new ArrayList<ArrayList<Object>>();
        for (int i = 0; i < 5; i++) {
            placementsArrX.add(new ArrayList<>());
        }

        while (placementsW.length() > 0) {
            String structureType = placementsW.substring(0, 1);

            switch (structureType) {
                case "C" -> {
                    placementsArrW.get(0).add(new Castle(true, Integer.parseInt(placementsW.substring(1, 2)), 'W'));
                    placementsW = placementsW.substring(2);
                }
                case "K" -> {
                    int id = Integer.parseInt(placementsW.substring(1, 3));
                    double startX = Double.parseDouble(getStartKnight(id).substring(0, getStartKnight(id).indexOf("\n")));
                    double startY = Double.parseDouble(getStartKnight(id).substring(getStartKnight(id).indexOf("\n")) + 1);

                    placementsArrW.get(1).add(new Knight(startX, startY, id, true, 'W'));

                    placementsW = placementsW.substring(3);
                }
                case "J" -> {
                    int id = Integer.parseInt(placementsW.substring(1, 3));
                    double startX = Double.parseDouble(getStartKnight(id).substring(0, getStartKnight(id).indexOf("\n")));
                    double startY = Double.parseDouble(getStartKnight(id).substring(getStartKnight(id).indexOf("\n")) + 1);

                    placementsArrW.get(1).add(new Knight(startX, startY, id, true, 'W', false));

                    placementsW = placementsW.substring(3);
                }
                case "R" -> {
                    RoadStartID roadCurr = hmRoads.get(placementsW.substring(1, 5));

                    int orient = roadCurr.getOrientation();
                    double startX = roadCurr.getStartX();
                    double startY = roadCurr.getStartY();

                    placementsArrW.get(2).add(new Road(startX, startY, true, placementsW.substring(1, 5), orient, 'W'));

                    placementsW = placementsW.substring(5);
                }

                case "S" -> {
                    SettlementStartID settleCurr = hmSettlements.get(placementsW.substring(1, 3));

                    int isBottom = settleCurr.getIsBottom();
                    double startX = settleCurr.getStartX();
                    double startY = settleCurr.getStartY();

                    placementsArrW.get(3).add(new Settlement(startX, startY, true, placementsW.substring(1, 3), isBottom, 'W'));

                    placementsW = placementsW.substring(3);
                }

                case "T" -> {
                    String cityID = placementsW.substring(1, 3);
                    CityStartID cityCurr = hmCities.get(cityID);


                    int isBottom = cityCurr.getIsBottom();
                    double startX = cityCurr.getStartX();
                    double startY = cityCurr.getStartY();

                    placementsArrW.get(4).add(new City(startX, startY, true, cityID, isBottom, 'W'));

                    placementsW = placementsW.substring(3);
                }
            }
        }

        while (placementsX.length() > 0) {
            String structureType = placementsX.substring(0, 1);

            switch (structureType) {
                case "C" -> {
                    placementsArrX.get(0).add(new Castle(true, Integer.parseInt(placementsX.substring(1, 2)), 'X'));
                    placementsX = placementsX.substring(2);
                }
                case "K" -> {
                    int id = Integer.parseInt(placementsX.substring(1, 3));
                    double startX = Double.parseDouble(getStartKnight(id).substring(0, getStartKnight(id).indexOf("\n")));
                    double startY = Double.parseDouble(getStartKnight(id).substring(getStartKnight(id).indexOf("\n")) + 1);

                    placementsArrX.get(1).add(new Knight(startX, startY, id, true, 'X'));

                    placementsX = placementsX.substring(3);
                }
                case "J" -> {
                    int id = Integer.parseInt(placementsX.substring(1, 3));
                    double startX = Double.parseDouble(getStartKnight(id).substring(0, getStartKnight(id).indexOf("\n")));
                    double startY = Double.parseDouble(getStartKnight(id).substring(getStartKnight(id).indexOf("\n")) + 1);

                    placementsArrX.get(1).add(new Knight(startX, startY, id, true, 'X', false));

                    placementsX = placementsX.substring(3);
                }
                case "R" -> {
                    RoadStartID roadCurr = hmRoads.get(placementsX.substring(1, 5));

                    int orient = roadCurr.getOrientation();
                    double startX = roadCurr.getStartX();
                    double startY = roadCurr.getStartY();

                    placementsArrX.get(2).add(new Road(startX, startY, true, placementsX.substring(1, 5), orient, 'X'));

                    placementsX = placementsX.substring(5);
                }

                case "S" -> {
                    SettlementStartID settleCurr = hmSettlements.get(placementsX.substring(1, 3));

                    int isBottom = settleCurr.getIsBottom();
                    double startX = settleCurr.getStartX();
                    double startY = settleCurr.getStartY();

                    placementsArrX.get(3).add(new Settlement(startX, startY, true, placementsX.substring(1, 3), isBottom, 'X'));

                    placementsX = placementsX.substring(3);
                }

                case "T" -> {
                    String cityID = placementsX.substring(1, 3);
                    CityStartID cityCurr = hmCities.get(cityID);


                    int isBottom = cityCurr.getIsBottom();
                    double startX = cityCurr.getStartX();
                    double startY = cityCurr.getStartY();

                    placementsArrX.get(4).add(new City(startX, startY, true, cityID, isBottom, 'X'));

                    placementsX = placementsX.substring(3);
                }
            }
        }

        //Adding Castles to Board
        for (int i = 0; i < 4; i++) {
            Castle toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            for (int j = 0; j < placementsArrW.get(0).size(); j++){
                var castle = (Castle) placementsArrW.get(0).get(j);
                if (castle.id == i){
                    ownsW = true;
                    toAdd = (Castle) placementsArrW.get(0).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(0).size(); j++){
                var castle = (Castle) placementsArrX.get(0).get(j);
                if (castle.id == i){
                    ownsX = true;
                    toAdd = (Castle) placementsArrX.get(0).get(j);
                    break;
                }
            }

            if (!(ownsX || ownsW))
                toAdd = new Castle(false, i, '-');

            this.structures.getChildren().add(toAdd);
            System.out.println(toAdd.id);
        }


        // Adding Knights
        for (Map.Entry<Integer,BoardTilePair> mapElement : hmKnight.entrySet()) {
            Knight toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            int key = mapElement.getKey();

            for (int j = 0; j < placementsArrW.get(1).size(); j++){
                var knight = (Knight) placementsArrW.get(1).get(j);
                if (knight.id == key){
                    ownsW = true;
                    toAdd = (Knight) placementsArrW.get(1).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(1).size(); j++){
                var knight = (Knight) placementsArrX.get(1).get(j);
                if (knight.id == key){
                    ownsX = true;
                    toAdd = (Knight) placementsArrX.get(1).get(j);
                    break;
                }
            }

            if (!(ownsW || ownsX)){
                double startX = Double.parseDouble(getStartKnight(key).substring(0, getStartKnight(key).indexOf("\n")));
                double startY = Double.parseDouble(getStartKnight(key).substring(getStartKnight(key).indexOf("\n")) + 1);

                toAdd = new Knight(startX, startY, key,false, '-', false);
            }


            this.structures.getChildren().add(toAdd.knightHead);
            this.structures.getChildren().add(toAdd.knightBody);
            this.structures.getChildren().add(toAdd.resourceCircle);
        }

        // Adding Roads
        for (Map.Entry<String, RoadStartID> mapElement : hmRoads.entrySet()) {
            Road toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            String key = mapElement.getKey();

            for (int j = 0; j < placementsArrW.get(2).size(); j++) {
                var road = (Road) placementsArrW.get(2).get(j);
                if (road.id.equals(key)) {
                    ownsW = true;
                    toAdd = (Road) placementsArrW.get(2).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(2).size(); j++) {
                var road = (Road) placementsArrX.get(2).get(j);
                if (road.id.equals(key)) {
                    ownsX = true;
                    toAdd = (Road) placementsArrX.get(2).get(j);
                    break;
                }
            }

            if (!(ownsW || ownsX)) {
                double startX = hmRoads.get(key).getStartX();
                double startY = hmRoads.get(key).getStartY();
                int orientation = hmRoads.get(key).getOrientation();

                toAdd = new Road(startX, startY, false, key, orientation, '-');
            }

            this.structures.getChildren().add(toAdd);
        }

        // Adding Settlements
        for (Map.Entry<String, SettlementStartID> mapElement : hmSettlements.entrySet()) {
            Settlement toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            String key = mapElement.getKey();

            for (int j = 0; j < placementsArrW.get(3).size(); j++) {
                var settlement = (Settlement) placementsArrW.get(3).get(j);
                if (settlement.id.equals(key)) {
                    ownsW = true;
                    toAdd = (Settlement) placementsArrW.get(3).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(3).size(); j++) {
                var settlement = (Settlement) placementsArrX.get(3).get(j);
                if (settlement.id.equals(key)) {
                    ownsX = true;
                    toAdd = (Settlement) placementsArrX.get(3).get(j);
                    break;
                }
            }

            if (!(ownsW || ownsX)) {
                double startX = hmSettlements.get(key).getStartX();
                double startY = hmSettlements.get(key).getStartY();
                int isBottom = hmSettlements.get(key).getIsBottom();

                toAdd = new Settlement(startX, startY, false, key, isBottom, '-');
            }

            this.structures.getChildren().add(toAdd);
        }

        // Adding Cities
        for (Map.Entry<String, CityStartID> mapElement : hmCities.entrySet()) {
            City toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            String key = mapElement.getKey();

            for (int j = 0; j < placementsArrW.get(4).size(); j++) {
                var city = (City) placementsArrW.get(4).get(j);
                if (city.id.equals(key)) {
                    ownsW = true;
                    toAdd = (City) placementsArrW.get(4).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(4).size(); j++) {
                var city = (City) placementsArrX.get(4).get(j);
                if (city.id.equals(key)) {
                    ownsX = true;
                    toAdd = (City) placementsArrX.get(4).get(j);
                    break;
                }
            }

            if (!(ownsW || ownsX)) {
                double startX = hmCities.get(key).getStartX();
                double startY = hmCities.get(key).getStartY();
                int isBottom = hmCities.get(key).getIsBottom();

                toAdd = new City(startX, startY, false, key, isBottom, '-');
            }

            this.structures.getChildren().add(toAdd.cityRight);
            this.structures.getChildren().add(toAdd.cityLeft);
        }

        System.out.println(this.structures.getChildren());

        System.out.println();


        /////////////////////////////////////////////
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Board State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(500);
        Button button = new Button("Show");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel, boardTextField, button);
        hb.setSpacing(10);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Board State Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);
        this.root.getChildren().add(this.board);
        this.root.getChildren().add(this.structures);

        this.makeBoard();
        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

//###################################################################
