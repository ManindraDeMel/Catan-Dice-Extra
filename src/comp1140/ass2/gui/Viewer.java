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

//    class ResourceCircle extends Circle{
//        double startX, startY;
//        ResourceCircle(){
//            double centerX = startX;
//            double centerY = startY + HEX_HEIGHT/5;
//            double radius = HEX_HEIGHT/8;
//            Circle circle = new Circle(centerX, centerY, radius);
//            circle.setFill(Color.WHITE);
//            circle.setStrokeWidth(2);
//            circle.setStrokeMiterLimit(10);
//            circle.setStrokeType(StrokeType.INSIDE);
//            circle.setStroke(Color.valueOf("0x000000"));
//            this.board.getChildren().add(circle);
//
//        }
//    }

    class Road extends Rectangle{
        double height, width, startX, startY;
        int id;

        Road(double startX, double startY, boolean isBuilt, int id, char player){
            this.startX = startX;
            this.startY = startY;
            this.id = id;

            double sideLength = HEX_WIDTH * 3 / 5;
            double width = HEX_WIDTH/8;
            double height = width*2.5;

            double centerX = startX-HEX_WIDTH/2 - width/2;
            double centerY = startY - (sideLength - height)/2;

//            switch (this.id){
//                case 0 -> {
//                    this.setX(centerX-HEX_WIDTH/2- width/2);
//                    this.setY(centerY - (HEX_WIDTH)/2  + (sideLength - height)/2);
//                    this.setWidth(width);
//                    this.setHeight(height);
//                }
//                case 1 -> {
//
//                }
//                case 3 -> {
//                    this.setX(centerX + HEX_WIDTH/2 - width/2);
//                    this.setY(centerY - (HEX_WIDTH)/2  + (sideLength - height)/2);
//                    this.setWidth(width);
//                    this.setHeight(height);
//                }
//            }

            this.setX(centerX);
            this.setY(centerY);
            this.setWidth(width);
            this.setHeight(height);

            Rotate rotate = new Rotate();
            rotate.setPivotX(startX);
            rotate.setPivotY(startY);
            this.getTransforms().addAll(rotate);
            rotate.setAngle(30*id);
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
//                double startX = x * HEX_WIDTH;
//                double startY = (y + yOffset) * HEX_HEIGHT;
                double startX = (x+xOffset) * HEX_WIDTH;
                double startY = y * HEX_HEIGHT;
                Hexagon hexagon = new Hexagon(startX, startY);
                this.board.getChildren().add(hexagon);

                // Making circles
                double centerX = startX;
                double centerY = startY + HEX_HEIGHT/5;
                double radius = HEX_HEIGHT/8;
                Circle circle = new Circle(centerX, centerY, radius);
                circle.setFill(Color.WHITE);
                circle.setStrokeWidth(2);
                circle.setStrokeMiterLimit(10);
                circle.setStrokeType(StrokeType.INSIDE);
                circle.setStroke(Color.valueOf("0x000000"));
                this.board.getChildren().add(circle);

                // Making Knight
//                double centerXK = startX;
//                double centerYK = startY - HEX_HEIGHT/5;
//                double radiusK = HEX_HEIGHT/15;
//                Circle circleK = new Circle(centerXK, centerYK, radiusK);
//                circleK.setFill(Color.GREEN);
//                circleK.setStrokeWidth(2);
//                circleK.setStrokeMiterLimit(10);
//                circleK.setStrokeType(StrokeType.INSIDE);
//                circleK.setStroke(Color.valueOf("0x000000"));
//                this.board.getChildren().add(circleK);
//
//                Rectangle rectangle = new Rectangle();
//                double width = radiusK*2.3;
//                double height = radiusK*3.5;
//                //Setting the properties of the rectangle
//                rectangle.setX(centerXK - width/2);
//                rectangle.setY(centerYK + radiusK*0.7);
//                rectangle.setWidth(width);
//                rectangle.setHeight(height);
//
//                rectangle.setFill(Color.GREEN);
//                rectangle.setStrokeWidth(2);
//                rectangle.setStrokeMiterLimit(10);
//                rectangle.setStrokeType(StrokeType.INSIDE);
//                rectangle.setStroke(Color.valueOf("0x000000"));
//                this.board.getChildren().add(rectangle);

                // MAKING ROAD

                // left
//                double sideLength = HEX_WIDTH * 3 / 5;
//                Rectangle rectangle = new Rectangle();
//                double width = HEX_WIDTH/8;
//                double height = width*2.5;
//                //Setting the properties of the rectangle
////                rectangle.setX(centerX-HEX_WIDTH/2- width/2);
////                rectangle.setY(centerY - (HEX_WIDTH)/2  + (sideLength - height)/2);
//
//                rectangle.setX(startX-HEX_WIDTH/2 - width/2);
//                rectangle.setY(startY - (sideLength - height)/2);
//                rectangle.setWidth(width);
//                rectangle.setHeight(height);
////                rectangle.setRotate(180);
//
//                rectangle.setFill(Color.GREEN);
//                rectangle.setStrokeWidth(2);
//                rectangle.setStrokeMiterLimit(10);
//                rectangle.setStrokeType(StrokeType.INSIDE);
//                rectangle.setStroke(Color.valueOf("0x000000"));
//                this.board.getChildren().add(rectangle);

                // right
//                rectangle = new Rectangle();
//                //Setting the properties of the rectangle
//                rectangle.setX(centerX + HEX_WIDTH/2 - width/2);
//                rectangle.setY(centerY - (HEX_WIDTH)/2  + (sideLength - height)/2);
//                rectangle.setWidth(width);
//                rectangle.setHeight(height);
////                rectangle.setRotate(90);
//
//                rectangle.setFill(Color.GREEN);
//                rectangle.setStrokeWidth(2);
//                rectangle.setStrokeMiterLimit(10);
//                rectangle.setStrokeType(StrokeType.INSIDE);
//                rectangle.setStroke(Color.valueOf("0x000000"));
//                this.board.getChildren().add(rectangle);

                // top left
//                rectangle = new Rectangle();
//                //Setting the properties of the rectangle
//                rectangle.setX(startX-HEX_WIDTH/2 - width/2);
//                rectangle.setY(startY - (sideLength - height)/2);
//                rectangle.setWidth(width);
//                rectangle.setHeight(height);
////                rectangle.setRotate(90);
//
//                Rotate rotate = new Rotate();
//                rotate.setPivotX(startX);
//                rotate.setPivotY(startY);
//                rectangle.getTransforms().addAll(rotate);
//                rotate.setAngle(30);
//                rectangle.getTransforms().add(rotate);
//
//                rectangle.setFill(Color.GREEN);
//                rectangle.setStrokeWidth(2);
//                rectangle.setStrokeMiterLimit(10);
//                rectangle.setStrokeType(StrokeType.INSIDE);
//                rectangle.setStroke(Color.valueOf("0x000000"));
//                this.board.getChildren().add(rectangle);
//
//                // top right
//                rectangle = new Rectangle();
//                //Setting the properties of the rectangle
//                rectangle.setX(startX-HEX_WIDTH/2 - width/2);
//                rectangle.setY(startY - (sideLength - height)/2);
//                rectangle.setWidth(width);
//                rectangle.setHeight(height);
////                rectangle.setRotate(90);
//
//                rotate = new Rotate();
//                rotate.setPivotX(startX);
//                rotate.setPivotY(startY);
//                rectangle.getTransforms().addAll(rotate);
//                rotate.setAngle(60);
//                rectangle.getTransforms().add(rotate);
//
//                rectangle.setFill(Color.GREEN);
//                rectangle.setStrokeWidth(2);
//                rectangle.setStrokeMiterLimit(10);
//                rectangle.setStrokeType(StrokeType.INSIDE);
//                rectangle.setStroke(Color.valueOf("0x000000"));
//                this.board.getChildren().add(rectangle);


//                KnightHead knightHead = new KnightHead(startX, startY, false, 'w');
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
//                Knight knight = new Knight(startX, startY, false, 'w');
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
