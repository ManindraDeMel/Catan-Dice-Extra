package comp1140.ass2.gui;

import comp1140.ass2.Board;
import comp1140.ass2.CatanDiceExtra;
import comp1140.ass2.gui.backend.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static comp1140.ass2.CatanDiceExtra.validateClass.Misc.getResourcesFromBoardState;
import static comp1140.ass2.gui.backend.Constants.*;

/**
 * Authored by Arjun Raj, u7526852
 */
public class Game extends Application {
    // FIXME Task 11: implement a playable game

    private Group root = new Group();
    private Group controls = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static GameMode GAME_MODE = GameMode.NOTCHOSEN;
    private Group board = new Group();
    private Group structures = new Group();
    private Group information = new Group();
    private static int players;
    static String BOARD_STATE = "W00WXW00X00";
    static String UNCHANGED_BOARD_STATE = "W00WXW00X00";
    public static String BUILD = "";
    public static Object BUILD_STRUCTURE = null;
    public static ResourceCircle RESOURCE_IN = null;
    private boolean ROLL_STATE;
    public static boolean END_GAME = false;


    private static ArrayList<CurrentResource> currentResources = new ArrayList<>();
    private static ArrayList<String> playedActionSequences = new ArrayList<>();

    // CONSTANTS
    static final double HEX_WIDTH = 140;
    static final double HEX_HEIGHT = (int) (HEX_WIDTH * Math.sqrt(3) / 2);
    static final int MARGIN_X = (int) (HEX_HEIGHT * 0.5);
    static final int BOARD_X = (int) (HEX_HEIGHT * 0);
    static final int MARGIN_Y = (int) (HEX_HEIGHT * 0.5);
    static final int BOARD_Y = MARGIN_Y;
    static final Color[] playersColour = new Color[]{Color.ORANGE, Color.RED};

    private final static String INSTRUCTIONS = """
           Welcome to Catan Dice Extra!
                                                                                                                                                                               
           You're a new venturer seeking to dominante control over an island constented between you and other venturers!
           Once you've built your initial road, you test your luck and roll for resources on your turn! If you're unhappy with some of the resources you've rolled, you can try your luck again and roll again! But be careful, you only have 3 rolls. If you get lucky and roll 2 money resources you can trade it for any resource you desire! and if you've built a knight on a resource tile you can even swap another resource for the resource the knight is on! Talk about sneaky strategy.. Ultimately you must use swaps, trades and keep (reroll) actions to outplay your opponents and build as many structures as to can to dominante your control over the island, build long roads and many knights (you get bonus points). This first to 10 points wins! Good luck venturers!
           
           Controls:
           On your turn you have three different phases
           - the Roll Phase
             - In the roll phase you can use the 'keep' button and name the resources you want to keep, the other resources will be rerolled (at a max of 3 rerolls)
           - The Build Phase
             - build action
               - Here you can build several structures: Roads, settlements, citites, castles and knights all of which need a certain amount of resources to build and need to be connected to a road network owned by yourself
             - trade action
               - If you've attained 2 money resources in a roll, you can trade those 2 money resources for any resource you desire. The ratio is always 2 : 1
             - swap action
               - If you've built a knight and it hasn't been used yet, you can use that knight to 'swap' one of the resources you've rolled for the resource the knight is on
           """;


    private void chooseGameMode() {
        Group gameModeButtons = new Group();
        Font font = Font.font("Courier New", FontWeight.BOLD, 22);
        double height = 100;
        double width = 300;
        double gapScale = 1.25;
        double centerX = WINDOW_WIDTH/2 - width/2;
        double centerY = WINDOW_HEIGHT/2;

        // Adding Introductory Text
        Text text = new Text("Welcome to CatanDice Extra game!\nChoose Game Mode!");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 30));
        text.setTranslateX((WINDOW_WIDTH/2) - 280);
        text.setY(centerY*0.5);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.LIGHTSKYBLUE);
        gameModeButtons.getChildren().add(text);

        // Adding Chosen Text
        Text text1 = new Text("Chosen: -");
        text1.setTranslateX(75);
        text1.setTranslateY(WINDOW_HEIGHT-100);
        text1.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 30));
        text1.setFill(Color.ORANGE);
        gameModeButtons.getChildren().add(text1);

        // Adding buttons
        Button playersOnly = new Button();
        playersOnly.setText("1 Player vs 1 Player");
        playersOnly.setFont(font);
        playersOnly.setPrefSize(width, height);
        playersOnly.setTranslateX(centerX - width*gapScale);
        playersOnly.setTranslateY(centerY);
        gameModeButtons.getChildren().add(playersOnly);

        Button basicAI = new Button();
        basicAI.setText("1 Player vs Basic AI");
        basicAI.setFont(font);
        basicAI.setPrefSize(width, height);
        basicAI.setTranslateX(centerX);
        basicAI.setTranslateY(centerY);
        gameModeButtons.getChildren().add(basicAI);

        Button smartAI = new Button();
        smartAI.setText("1 Player vs Smart AI");
        smartAI.setFont(font);
        smartAI.setPrefSize(width, height);
        smartAI.setTranslateX(centerX + width*gapScale);
        smartAI.setTranslateY(centerY);
        gameModeButtons.getChildren().add(smartAI);

        // Event handler when button clicked
        EventHandler<ActionEvent> event = e -> {

            // Chosen Button
            Button chosenButton = ((Button) e.getSource());

            // Update Chosen Text
            text1.setText("Chosen: " + chosenButton.getText());

            // Update Game_Mode ENUM
            if (chosenButton == playersOnly)
                GAME_MODE = GameMode.PLAYERSONLY;
            else if (chosenButton == basicAI)
                GAME_MODE = GameMode.BASICAI;
            else if (chosenButton == smartAI)
                GAME_MODE = GameMode.SMARTAI;

            // Pausing game for effect
            try {TimeUnit.SECONDS.sleep(2);}
            catch(InterruptedException ex) {Thread.currentThread().interrupt();}

            // Removing buttons and text once game mode is chosen
            this.controls.getChildren().remove(gameModeButtons);
        };

        playersOnly.setOnAction(event);
        basicAI.setOnAction(event);
        smartAI.setOnAction(event);

        this.controls.getChildren().add(gameModeButtons);
    }
    public void updateBuild(Object struct){
//        if (struct ins)
        //TODO
    }

    public void endGame(char winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("GAME OVER");
        alert.setContentText("WINNER IS: Player " + winner);
        alert.showAndWait().ifPresent(e ->
        {
            Platform.exit();
        });
    }

    /**
     *  makeBoard() creates tiles (hexagon shaped) on the window
     */
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

                TileShape hexagon = new TileShape(startX, startY);
                this.board.getChildren().add(hexagon);
            }
        }

        Line line = new Line(HEX_WIDTH * 5.5, -WINDOW_HEIGHT, HEX_WIDTH * 5.5, WINDOW_HEIGHT);
        line.setFill(Color.WHITESMOKE);
        line.setStrokeType(StrokeType.CENTERED);
        this.board.getChildren().add(line);

        Rectangle background = new Rectangle();
        background.setWidth(HEX_WIDTH * 5.5 + MARGIN_X);
        background.setHeight(WINDOW_HEIGHT+100);
        background.setTranslateX(-MARGIN_X);
        background.setTranslateY(-MARGIN_Y-100);
        background.setFill(Color.valueOf("0E56B0"));
        this.board.getChildren().add(background);

        InputStream stream = null;
        try{stream = new FileInputStream("assets/board.png");}
        catch (Exception e) {}
        Image image = new Image(stream);
        ImageView board = new ImageView(image);
        board.setTranslateX(-170);
        board.setTranslateY(-170);
        board.setScaleX(0.94);
        board.setScaleY(0.95);
        this.board.getChildren().add(board);

        this.board.toBack();
    }

    /**
     * This class stores all the meta-data (eg. position, start pixels, etc) of a Knight structure
     *
     * Used to get the coordinates of the center of the tile the knight is in
     */
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

    void displayState(String boardState) {
        /////////////////////////////////////////////

        // Getting strings of different sections
        String turn = boardState.substring(0, boardState.indexOf('W', 1));
        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));
//        String wScore = boardState.substring();


        // Outputting useful text for debugging
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


        // Creating and storing all the structures in the array for player W
        while (placementsW.length() > 0) {
            String structureType = placementsW.substring(0, 1);

            switch (structureType) {
                case "C" -> {
                    placementsArrW.get(0).add(new CastleShape(true, Integer.parseInt(placementsW.substring(1, 2)), 'W'));
                    placementsW = placementsW.substring(2);
                }
                case "K" -> {
                    int id = Integer.parseInt(placementsW.substring(1, 3));
                    double startX = Double.parseDouble(getStartKnight(id).substring(0, getStartKnight(id).indexOf("\n")));
                    double startY = Double.parseDouble(getStartKnight(id).substring(getStartKnight(id).indexOf("\n")) + 1);

                    placementsArrW.get(1).add(new KnightShape(startX, startY, id, true, 'W'));

                    placementsW = placementsW.substring(3);
                }
                case "J" -> {
                    int id = Integer.parseInt(placementsW.substring(1, 3));
                    double startX = Double.parseDouble(getStartKnight(id).substring(0, getStartKnight(id).indexOf("\n")));
                    double startY = Double.parseDouble(getStartKnight(id).substring(getStartKnight(id).indexOf("\n")) + 1);

                    placementsArrW.get(1).add(new KnightShape(startX, startY, id, true, 'W', false));

                    placementsW = placementsW.substring(3);
                }
                case "R" -> {
                    RoadStartID roadCurr = hmRoads.get(placementsW.substring(1, 5));

                    int orient = roadCurr.getOrientation();
                    double startX = roadCurr.getStartX();
                    double startY = roadCurr.getStartY();

                    placementsArrW.get(2).add(new RoadShape(startX, startY, true, placementsW.substring(1, 5), orient, 'W'));

                    placementsW = placementsW.substring(5);
                }

                case "S" -> {
                    SettlementStartID settleCurr = hmSettlements.get(placementsW.substring(1, 3));

                    int isBottom = settleCurr.getIsBottom();
                    double startX = settleCurr.getStartX();
                    double startY = settleCurr.getStartY();

                    placementsArrW.get(3).add(new SettlementShape(startX, startY, true, placementsW.substring(1, 3), isBottom, 'W'));

                    placementsW = placementsW.substring(3);
                }

                case "T" -> {
                    String cityID = placementsW.substring(1, 3);
                    CityStartID cityCurr = hmCities.get(cityID);


                    int isBottom = cityCurr.getIsBottom();
                    double startX = cityCurr.getStartX();
                    double startY = cityCurr.getStartY();

                    placementsArrW.get(4).add(new CityShape(startX, startY, true, cityID, isBottom, 'W'));

                    placementsW = placementsW.substring(3);
                }
            }
        }


        // Creating and storing all the structures in the array for player X
        while (placementsX.length() > 0) {
            String structureType = placementsX.substring(0, 1);

            switch (structureType) {
                case "C" -> {
                    placementsArrX.get(0).add(new CastleShape(true, Integer.parseInt(placementsX.substring(1, 2)), 'X'));
                    placementsX = placementsX.substring(2);
                }
                case "K" -> {
                    int id = Integer.parseInt(placementsX.substring(1, 3));
                    double startX = Double.parseDouble(getStartKnight(id).substring(0, getStartKnight(id).indexOf("\n")));
                    double startY = Double.parseDouble(getStartKnight(id).substring(getStartKnight(id).indexOf("\n")) + 1);

                    placementsArrX.get(1).add(new KnightShape(startX, startY, id, true, 'X'));

                    placementsX = placementsX.substring(3);
                }
                case "J" -> {
                    int id = Integer.parseInt(placementsX.substring(1, 3));
                    double startX = Double.parseDouble(getStartKnight(id).substring(0, getStartKnight(id).indexOf("\n")));
                    double startY = Double.parseDouble(getStartKnight(id).substring(getStartKnight(id).indexOf("\n")) + 1);

                    placementsArrX.get(1).add(new KnightShape(startX, startY, id, true, 'X', false));

                    placementsX = placementsX.substring(3);
                }
                case "R" -> {
                    RoadStartID roadCurr = hmRoads.get(placementsX.substring(1, 5));

                    int orient = roadCurr.getOrientation();
                    double startX = roadCurr.getStartX();
                    double startY = roadCurr.getStartY();

                    placementsArrX.get(2).add(new RoadShape(startX, startY, true, placementsX.substring(1, 5), orient, 'X'));

                    placementsX = placementsX.substring(5);
                }

                case "S" -> {
                    SettlementStartID settleCurr = hmSettlements.get(placementsX.substring(1, 3));

                    int isBottom = settleCurr.getIsBottom();
                    double startX = settleCurr.getStartX();
                    double startY = settleCurr.getStartY();

                    placementsArrX.get(3).add(new SettlementShape(startX, startY, true, placementsX.substring(1, 3), isBottom, 'X'));

                    placementsX = placementsX.substring(3);
                }

                case "T" -> {
                    String cityID = placementsX.substring(1, 3);
                    CityStartID cityCurr = hmCities.get(cityID);


                    int isBottom = cityCurr.getIsBottom();
                    double startX = cityCurr.getStartX();
                    double startY = cityCurr.getStartY();

                    placementsArrX.get(4).add(new CityShape(startX, startY, true, cityID, isBottom, 'X'));

                    placementsX = placementsX.substring(3);
                }
            }
        }


        //Adding Castles on Board
        for (int i = 0; i < 4; i++) {
            CastleShape toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            for (int j = 0; j < placementsArrW.get(0).size(); j++){
                var castle = (CastleShape) placementsArrW.get(0).get(j);
                if (castle.id == i){
                    ownsW = true;
                    toAdd = (CastleShape) placementsArrW.get(0).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(0).size(); j++){
                var castle = (CastleShape) placementsArrX.get(0).get(j);
                if (castle.id == i){
                    ownsX = true;
                    toAdd = (CastleShape) placementsArrX.get(0).get(j);
                    break;
                }
            }

            if (!(ownsX || ownsW))
                toAdd = new CastleShape(false, i, '-');

            this.structures.getChildren().add(toAdd);
            System.out.println(toAdd.id);
        }


        // Adding Knights on board
        for (Map.Entry<Integer, BoardTilePair> mapElement : hmKnight.entrySet()) {
            KnightShape toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            int key = mapElement.getKey();

            for (int j = 0; j < placementsArrW.get(1).size(); j++){
                var knight = (KnightShape) placementsArrW.get(1).get(j);
                if (knight.id == key){
                    ownsW = true;
                    toAdd = (KnightShape) placementsArrW.get(1).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(1).size(); j++){
                var knight = (KnightShape) placementsArrX.get(1).get(j);
                if (knight.id == key){
                    ownsX = true;
                    toAdd = (KnightShape) placementsArrX.get(1).get(j);
                    break;
                }
            }

            if (!(ownsW || ownsX)){
                double startX = Double.parseDouble(getStartKnight(key).substring(0, getStartKnight(key).indexOf("\n")));
                double startY = Double.parseDouble(getStartKnight(key).substring(getStartKnight(key).indexOf("\n")) + 1);

                toAdd = new KnightShape(startX, startY, key,false, '-', false);
            }


            this.structures.getChildren().add(toAdd.knightHead);
            this.structures.getChildren().add(toAdd.knightBody);
            this.structures.getChildren().add(toAdd.resourceCircle);
        }

        // Adding Roads on board
        for (Map.Entry<String, RoadStartID> mapElement : hmRoads.entrySet()) {
            RoadShape toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            String key = mapElement.getKey();

            for (int j = 0; j < placementsArrW.get(2).size(); j++) {
                var road = (RoadShape) placementsArrW.get(2).get(j);
                if (road.id.equals(key)) {
                    ownsW = true;
                    toAdd = (RoadShape) placementsArrW.get(2).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(2).size(); j++) {
                var road = (RoadShape) placementsArrX.get(2).get(j);
                if (road.id.equals(key)) {
                    ownsX = true;
                    toAdd = (RoadShape) placementsArrX.get(2).get(j);
                    break;
                }
            }

            if (!(ownsW || ownsX)) {
                double startX = hmRoads.get(key).getStartX();
                double startY = hmRoads.get(key).getStartY();
                int orientation = hmRoads.get(key).getOrientation();

                toAdd = new RoadShape(startX, startY, false, key, orientation, '-');
            }

            this.structures.getChildren().add(toAdd);
        }

        // Adding Settlements on board
        for (Map.Entry<String, SettlementStartID> mapElement : hmSettlements.entrySet()) {
            SettlementShape toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            String key = mapElement.getKey();

            for (int j = 0; j < placementsArrW.get(3).size(); j++) {
                var settlement = (SettlementShape) placementsArrW.get(3).get(j);
                if (settlement.id.equals(key)) {
                    ownsW = true;
                    toAdd = (SettlementShape) placementsArrW.get(3).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(3).size(); j++) {
                var settlement = (SettlementShape) placementsArrX.get(3).get(j);
                if (settlement.id.equals(key)) {
                    ownsX = true;
                    toAdd = (SettlementShape) placementsArrX.get(3).get(j);
                    break;
                }
            }

            if (!(ownsW || ownsX)) {
                double startX = hmSettlements.get(key).getStartX();
                double startY = hmSettlements.get(key).getStartY();
                int isBottom = hmSettlements.get(key).getIsBottom();

                toAdd = new SettlementShape(startX, startY, false, key, isBottom, '-');
            }

            this.structures.getChildren().add(toAdd);
        }

        // Adding Cities on board
        for (Map.Entry<String, CityStartID> mapElement : hmCities.entrySet()) {
            CityShape toAdd = null;
            boolean ownsW = false;
            boolean ownsX = false;

            String key = mapElement.getKey();

            for (int j = 0; j < placementsArrW.get(4).size(); j++) {
                var city = (CityShape) placementsArrW.get(4).get(j);
                if (city.id.equals(key)) {
                    ownsW = true;
                    toAdd = (CityShape) placementsArrW.get(4).get(j);
                    break;
                }
            }

            for (int j = 0; j < placementsArrX.get(4).size(); j++) {
                var city = (CityShape) placementsArrX.get(4).get(j);
                if (city.id.equals(key)) {
                    ownsX = true;
                    toAdd = (CityShape) placementsArrX.get(4).get(j);
                    break;
                }
            }

            if (!(ownsW || ownsX)) {
                double startX = hmCities.get(key).getStartX();
                double startY = hmCities.get(key).getStartY();
                int isBottom = hmCities.get(key).getIsBottom();

                toAdd = new CityShape(startX, startY, false, key, isBottom, '-');
            }

            this.structures.getChildren().add(toAdd.cityRight);
            this.structures.getChildren().add(toAdd.cityLeft);
        }


        makeScoreBoard(boardState);
//        try{
//        makeTurnInfo();}catch (Exception e){}
//        makeControls();

        // Outputting text for debugging
//        System.out.println(this.structures.getChildren());
//        System.out.println();


        /////////////////////////////////////////////
    }

    private void makeScoreBoard(String boardState){
        String scores = Board.getScoreFromBoardState(boardState);
        int scoreX = Integer.parseInt(scores.substring(1, 3));
        int scoreY = Integer.parseInt(scores.substring(scores.indexOf('X') + 1, scores.indexOf('X') + 3));
        Font font = Font.font("Courier New", FontWeight.BOLD, 22);
        double centerX = WINDOW_WIDTH - 350;


        Text scoreStrX = new Text("Player X Score: " + scoreX);
        scoreStrX.setY(HEX_HEIGHT * 0.5);
        scoreStrX.setX(centerX);
        scoreStrX.setFill(playersColour[0]);
        scoreStrX.setFont(font);
        scoreStrX.setStrokeType(StrokeType.OUTSIDE);

        Text scoreStrY = new Text("Player Y Score: " + scoreY);
        scoreStrY.setY(HEX_WIDTH * 0.75);
        scoreStrY.setX(centerX);
        scoreStrY.setFill(playersColour[1]);
        scoreStrY.setFont(font);
        scoreStrY.setStrokeType(StrokeType.OUTSIDE);


        this.information.getChildren().addAll(scoreStrX, scoreStrY);
    }

    private void newGame() {
        try {
            //TODO
        } catch (IllegalArgumentException e) {
            System.err.println("Uh oh. " + e);
            e.printStackTrace();
            Platform.exit();
        }
    }

    private void makeControls() {
        Button button = new Button("Restart");
        button.setLayoutX(WINDOW_WIDTH - 100);
        button.setLayoutY(WINDOW_HEIGHT - 45);
        button.setOnAction(e -> this.newGame());
        this.controls.getChildren().add(button);

        Button instructions = new Button("Instructions");
        instructions.setLayoutX(WINDOW_WIDTH - 200);
        instructions.setLayoutY(WINDOW_HEIGHT - 45);
        instructions.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Instructions and Controls");
            alert.setContentText(INSTRUCTIONS);
            alert.show();
//            try {TimeUnit.SECONDS.sleep(2);}
//            catch(InterruptedException ex) {Thread.currentThread().interrupt();}
//            alert.close();
        });
        this.controls.getChildren().add(instructions);
    }

    private void makeTurnInfo() throws FileNotFoundException {
        Group resourceGroup = new Group();
        String turn = Board.getTurnFromBoardState(BOARD_STATE);
        String player = turn.substring(0, 1);
        ArrayList<Character> resources = getResourcesFromBoardState(BOARD_STATE);
        ArrayList<ImageView> resourcesImage = new ArrayList<ImageView>();
        Font font = Font.font("Courier New", FontWeight.BOLD, 22);
        double centerX = WINDOW_WIDTH - 350;


        Text currTurn = new Text("Current Turn: " + player);
        currTurn.setY(HEX_HEIGHT * 1.5);
        currTurn.setX(centerX);
        currTurn.setFill(Color.LIGHTBLUE);
        currTurn.setFont(font);
        currTurn.setStrokeType(StrokeType.OUTSIDE);


        for (int i = 0; i < resources.size(); i++) {
            CurrentResource resource = new CurrentResource(resources.get(i));
            ImageView resourceImage = resource.imageView;
            resourceImage.setTranslateX(centerX + 50*i);
            resourceImage.setTranslateY(HEX_HEIGHT * 1.65);

            this.information.getChildren().add(resourceImage);

            resourcesImage.add(resourceImage);
            currentResources.add(resource);
        }

        resourceGroup.getChildren().addAll(resourcesImage);
        this.information.getChildren().addAll(currTurn, resourceGroup);
    }
    private String reRoll(){
        String action = "keep";
        for (var resource: currentResources){
            if (resource.selected)
                action +=  resource.resourceChar;
        }

        return action;
    }

    private boolean checkRollState(){
        for (var action : playedActionSequences) {
            if (action.charAt(0) != 'k')
                return false;
        }

        return BOARD_STATE.charAt(2)=='1'|| BOARD_STATE.charAt(2)=='2';
    }

    private void makePlayControls(){
        Font font = Font.font("Courier New", FontWeight.BOLD, 22);
        ROLL_STATE = checkRollState();

        //TODO
        double height = 50;
        double width = 100;
        double gapScale = 1.5;
        double centerX = WINDOW_WIDTH - 350;
        double centerY = WINDOW_HEIGHT - 350;

        Button endTurn = new Button();
        endTurn.setText("End Turn");
        endTurn.setFont(font);
        endTurn.setPrefSize(width*2.5, height);
        endTurn.setTranslateX(centerX);
        endTurn.setTranslateY(centerY+ height * 2.75);
        this.controls.getChildren().add(endTurn);

        Button keep = new Button();
        keep.setText("ROLL");
        keep.setFont(font);
        keep.setPrefSize(width, height);
        keep.setTranslateX(centerX);
        keep.setTranslateY(centerY);
        this.controls.getChildren().add(keep);

        Button buildButton = new Button();
        buildButton.setText("BUILD");
        buildButton.setFont(font);
        buildButton.setPrefSize(width, height);
        buildButton.setTranslateX(centerX + width*gapScale);
        buildButton.setTranslateY(centerY);
        this.controls.getChildren().add(buildButton);

        Button swapButton = new Button();
        swapButton.setText("SWAP");
        swapButton.setFont(font);
        swapButton.setPrefSize(width, height);
        swapButton.setTranslateX(centerX + width*gapScale);
        swapButton.setTranslateY(centerY + height*1.5);
        this.controls.getChildren().add(swapButton);

        Button tradeButton = new Button();
        tradeButton.setText("TRADE");
        tradeButton.setFont(font);
        tradeButton.setPrefSize(width, height);
        tradeButton.setTranslateX(centerX);
        tradeButton.setTranslateY(centerY + height*1.5);
        this.controls.getChildren().add(tradeButton);

        //EVENTS
        endTurn.setOnMouseClicked(mouseEvent -> {
            System.out.println("PREVIOUS: " + BOARD_STATE);
            String[] actionsPlayed;
            if (playedActionSequences.size() == 0)
                actionsPlayed = new String[0];
            else{
                actionsPlayed = new String[playedActionSequences.size()];

                for (int i = 0; i < actionsPlayed.length; i++) {
                    actionsPlayed[i] = playedActionSequences.get(i);
                }
            }

            UNCHANGED_BOARD_STATE = CatanDiceExtra.applyActionSequence(UNCHANGED_BOARD_STATE, actionsPlayed);
            BOARD_STATE = UNCHANGED_BOARD_STATE;

            playedActionSequences = new ArrayList<>();
            updateBoardUI(BOARD_STATE);

            System.out.println("AFTER: " + BOARD_STATE);

        });


        keep.setOnMouseClicked(mouseEvent -> {
            if (ROLL_STATE){
                String action = reRoll();

                if (CatanDiceExtra.isActionValid(BOARD_STATE, action)) {
                    BOARD_STATE = CatanDiceExtra.applyAction(BOARD_STATE, action);
                    currentResources = new ArrayList<>();
                    playedActionSequences.add(action);
                    updateBoardUI(BOARD_STATE);
                }else {
                    actionNotAllowed(action);
                }
            }else {
                actionNotAllowed("ROLL PHASE OVER");
            }
        });

        buildButton.setOnMouseClicked(mouseEvent -> {
            String action = BUILD;
//            System.out.println("-----");
//            System.out.println(action);
//            System.out.println(CatanDiceExtra.isActionValid(BOARD_STATE, action));
//            System.out.println(BOARD_STATE);
//            System.out.println("-------");

            if (CatanDiceExtra.isActionValid(BOARD_STATE, action)) {
                BOARD_STATE = CatanDiceExtra.applyAction(BOARD_STATE, action);
                currentResources = new ArrayList<>();
                playedActionSequences.add(action);
                updateBoardUI(BOARD_STATE);
            }else {
                actionNotAllowed(action);
            }
        });

        // DONE
        swapButton.setOnMouseClicked(mouseEvent -> {
            if ((RESOURCE_IN == null || selectedOutResource().size() != 1)){
                actionNotAllowed("NOT SELECTED PROPERLY");
            }
            else{
                String action = "swap" + CatanDiceExtra.validateClass.Misc.coordinateToResource[RESOURCE_IN.id] + selectedOutResource().get(0).resourceChar;

                if (CatanDiceExtra.isActionValid(BOARD_STATE, action)) {
                    BOARD_STATE = CatanDiceExtra.applyAction(BOARD_STATE, action);
                    currentResources = new ArrayList<>();
                    playedActionSequences.add(action);
                    updateBoardUI(BOARD_STATE);
                }else {
                    actionNotAllowed(action);
            }}
        });

        tradeButton.setOnAction(new EventHandler<ActionEvent>() {
            String[] tradeResources = {""};
            public void handle(ActionEvent event) {
                
                Label secondLabel = new Label("Enter trade resources\nExample:" +
                        "\nb: brick" +
                        "\n" +
                        "g: grain" +
                        "\n" +
                        "l: lumber" +
                        "\n" +
                        "o: ore" +
                        "\n" +
                        "w: wool");

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, 500, 500);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Second Stage");
                newWindow.setScene(secondScene);

                // Specifies the modality for new window.
                newWindow.initModality(Modality.APPLICATION_MODAL);

                // Specifies the owner Window (parent) for new window
//                Window primaryStage = ;
//                newWindow.initOwner(primaryStage);
//
//                // Set position of second window, related to primary window.
                newWindow.setMinHeight(100);
                newWindow.setMinWidth(500);
//                newWindow.setX(primaryStage.getX() + 200);
//                newWindow.setY(primaryStage.getY() + 100);

                newWindow.show();

                ////
                Label boardLabel = new Label("Enter Trade Resources:");
                TextField boardTextField = new TextField();
                boardTextField.setPrefWidth(200);
                Button button = new Button("Continue");
                button.setPrefSize(100, 50);
                System.out.println(123456);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        tradeResources[0] = boardTextField.getText();

                        String action = "trade" + tradeResources[0];


                        // ACTUAL CODE
                        if (CatanDiceExtra.isActionValid(BOARD_STATE, action)) {
                            System.out.println("HELLOO TRADE");
                            BOARD_STATE = CatanDiceExtra.applyAction(BOARD_STATE, action);
                            currentResources = new ArrayList<>();
                            playedActionSequences.add(action);
                            updateBoardUI(BOARD_STATE);
                        }
                        else {
                            actionNotAllowed(action);
                        }

                        newWindow.close();
                    }
                });
                HBox hb = new HBox();
                hb.getChildren().addAll(boardLabel, boardTextField, button);
                hb.setSpacing(10);
                secondaryLayout.getChildren().add(hb);
            }
        });
    }

    private ArrayList<CurrentResource> selectedOutResource(){
        ArrayList<CurrentResource> selected = new ArrayList<>();
        for (var resource: currentResources){
            if (!resource.selected)
                selected.add(resource);
        }
        return selected;
    }

    private void actionNotAllowed(String action){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("NOT ALLOWED");
        alert.setContentText("Action: " + action + " not allowed on Board State: " + BOARD_STATE);
        alert.showAndWait();
    }

    private void updateBoardUI(String boardState){
        System.out.println("------------");
        this.root.getChildren().clear();
        this.board.getChildren().clear();
        this.structures.getChildren().clear();
        this.information.getChildren().clear();
        this.controls.getChildren().clear();

        this.makeBoard();
        this.makeControls();
        try{makeTurnInfo();} catch (Exception e){}
        makePlayControls();


        this.root.getChildren().add(this.board);
        this.root.getChildren().add(this.structures);
        this.root.getChildren().add(this.information);
        this.root.getChildren().add(this.controls);
        this.displayState(BOARD_STATE);

        if (END_GAME){
//            endGame(CatanDiceExtra.whoWon(BOARD_STATE));
        }
    }

    private void startNewGame(){

    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("CatanDice Extra Game - Created by Arjun, Manindra and Stephen");
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);

//        this.chooseGameMode();

        BOARD_STATE = "X61bglmowWK02R0105R0205R0509S02XR3237W01X00";
//        UNCHANGED_BOARD_STATE = BOARD_STATE;

        this.root.getChildren().add(controls);
        makeControls();
        makeTurnInfo();
        makePlayControls();

//        scene.setFill(Color.valueOf("0E56B0"));
//        scene.setFill(Color.valueOf("2159A8"));
//                scene.setFill(Color.BLUE);

        this.makeBoard();
        this.root.getChildren().add(this.board);
        this.root.getChildren().add(this.structures);
        this.root.getChildren().add(this.information);

        InputStream stream = new FileInputStream("assets/board.png");
        Image image = new Image(stream);
        ImageView board = new ImageView(image);
        board.setTranslateX(-170);
        board.setTranslateY(-170);
        board.setScaleX(0.94);
        board.setScaleY(0.95);

        board.toBack();
        this.board.getChildren().add(board);




        this.displayState(BOARD_STATE);
//        endGame('X');

        stage.show();
    }
}
