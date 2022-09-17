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

//    private static final double HEX_HEIGHT = 150;
//    private static final double HEX_WIDTH = (int) (HEX_HEIGHT * Math.sqrt(3) / 2);
    private static final double HEX_WIDTH = 140;
    private static final double HEX_HEIGHT = (int) (HEX_WIDTH * Math.sqrt(3) / 2);

    private static final int MARGIN_X = (int) (HEX_HEIGHT * 0.5);
    private static final int BOARD_X = (int) (HEX_HEIGHT * 0.75);
    private static final int MARGIN_Y = (int) (HEX_HEIGHT * 0.5);

    private static final int BOARD_Y = MARGIN_Y;

    private static final Color[] playersColour = new Color[]{Color.ORANGE, Color.RED};
//    private static final int BOARD_WIDTH = PenguinsPoolParty.BOARD_WIDTH * (int) HEX_WIDTH;
//    private static final int BOARD_HEIGHT = (int) (PenguinsPoolParty.BOARD_HEIGHT * HEX_HEIGHT);

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
//            if (iceBlock == null) {
//                fillColour = Color.DARKBLUE;
//                strokeColour = Color.LIGHTBLUE;
//            }
//            else {
//                strokeColour = Color.BLUE;
//                if (iceBlock instanceof DraggableIceBlock)
//                    fillColour = Color.ALICEBLUE;
//                else
//                    fillColour = SOLUTION_COLOURS[iceBlock.ice.getId() - 'A'];
//            }
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
                if (player == 'w')
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
                if (player == 'w')
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

    class Knight {
        KnightHead knightHead;
        KnightBody knightBody;
        Knight(double startX, double startY, boolean isBuilt, char player){
            this.knightHead = new KnightHead(startX, startY, isBuilt, player);
            this.knightBody = new KnightBody(startX, startY, isBuilt, player);
        }
    }

    class ResourceCircle extends Circle{
        double startX, startY;
        ResourceCircle(double startX, double startY){
            double centerX = startX;
            double centerY = startY + HEX_HEIGHT/5;
            double radius = HEX_HEIGHT/8;

            this.setCenterX(centerX);
            this.setCenterY(centerY);
            this.setRadius(radius);

            this.setFill(Color.WHITE);
            this.setStrokeWidth(2);
            this.setStrokeMiterLimit(10);
            this.setStrokeType(StrokeType.INSIDE);
            this.setStroke(Color.valueOf("0x000000"));
        }
    }

    class Road extends Rectangle{
        double height, width, startX, startY;
        int id;

        Road(double startX, double startY, boolean isBuilt, int id, char player){
            this.startX = startX;
            this.startY = startY;
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
            rotate.setAngle(30*this.id);
            this.getTransforms().add(rotate);

            Color fillColour;
            Color strokeColour;
            if (isBuilt == false) {
                fillColour = Color.DARKGRAY;
                strokeColour = Color.LIGHTGRAY;
            }
            else {
                strokeColour = Color.BLUE;
                if (player == 'w')
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

    class Settlement extends Polygon{
        Settlement (double startX, double startY, boolean isBuilt, int id, char player){

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

            if(id == 0){
                // down
                centerX = startX - (HEX_WIDTH/scale)/2;
                centerY = startY + HEX_WIDTH/2 + (HEX_WIDTH/(scale+1));
            }else {
                // up
                centerX = startX - (HEX_WIDTH/scale)/2;
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
                if (player == 'w')
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

    class CityRight extends Polygon {
        CityRight (double startX, double startY, boolean isBuilt, int id, char player){

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

            if(id == 0){
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
                if (player == 'w')
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

    class CityLeft extends Polygon {
        CityLeft (double startX, double startY, boolean isBuilt, int id, char player){

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

            if(id == 0){
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
                if (player == 'w')
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

    class City{
        CityLeft cityLeft;
        CityRight cityRight;

        City (double startX, double startY, boolean isBuilt, int id, char player){
            cityLeft = new CityLeft(startX, startY, isBuilt, id, player);
            cityRight = new CityRight(startX, startY, isBuilt, id, player);
        }
    }

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

                // Making circles
                ResourceCircle circle = new ResourceCircle(startX, startY);
                this.board.getChildren().add(circle);


                // Making house
//                Polygon house1 = new Polygon();
//                int scale = 6;
//                house1.getPoints().addAll(new Double[]{
//                        0.0, 0.0,
//                        HEX_WIDTH/scale, 0.0,
//                        HEX_WIDTH/scale, -HEX_WIDTH/(scale+1),
//                        HEX_WIDTH/(scale*2), -1.3*(HEX_WIDTH/(scale+1)),
//                        0.0, -HEX_WIDTH/(scale+1)
//                        }
//
//                );
//                house1.setLayoutX(startX - (HEX_WIDTH/scale)/2);
//                house1.setLayoutY(startY + HEX_WIDTH/2 + (HEX_WIDTH/(scale+1)));
//
//                // UP
////                house1.setLayoutX(startX - (HEX_WIDTH/scale)/2);
////                house1.setLayoutY(startY - HEX_HEIGHT/2 - (HEX_WIDTH/(scale+1))/2);
//
//                house1.setFill(Color.YELLOW);
//                house1.setStroke(Color.GRAY);
//                house1.setStrokeWidth(2.0);
//                house1.setStrokeType(StrokeType.INSIDE);
//                structures.getChildren().add(house1);

//                Settlement settlement = new Settlement(startX,  startY, false, 1, 'w');
//                this.structures.getChildren().add(settlement);

                City city = new City(startX,  startY, false, 1, 'w');
                this.structures.getChildren().add(city.cityLeft);
                this.structures.getChildren().add(city.cityRight);


                Knight knight;
                Road road = new Road(startX, startY, false, x,'w');

                switch ((x+y)%3){

                    case 0 -> knight = new Knight(startX, startY, false, 'w');

                    case 1 -> {
                        knight = new Knight(startX, startY, true, 'w');
                        road = new Road(startX, startY, true, x,'w');
                    }

                    case 2 -> {
                        knight = new Knight(startX, startY, true, 'x');
                        road = new Road(startX, startY, true, x,'x');
                    }

                    default -> {
                        knight = new Knight(startX, startY, false, 'w');
                        road = new Road(startX, startY, false, x,'w');
                    }
                }

                this.structures.getChildren().add(knight.knightBody);
                this.structures.getChildren().add(knight.knightHead);
                this.structures.getChildren().add(road);
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
