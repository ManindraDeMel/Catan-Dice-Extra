package comp1140.ass2.gui;

import comp1140.ass2.gui.backend.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static comp1140.ass2.gui.backend.Constants.*;

public class Game extends Application {
    // FIXME Task 11: implement a playable game

    private final Group root = new Group();
    private final Group controls = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static GameMode GAME_MODE = GameMode.NOTCHOSEN;
    private final Group board = new Group();
    private final Group structures = new Group();
    private static int players;

    // CONSTANTS
    static final double HEX_WIDTH = 140;
    static final double HEX_HEIGHT = (int) (HEX_WIDTH * Math.sqrt(3) / 2);
    static final int MARGIN_X = (int) (HEX_HEIGHT * 0.5);
    static final int BOARD_X = (int) (HEX_HEIGHT * 0);
    static final int MARGIN_Y = (int) (HEX_HEIGHT * 0.5);
    static final int BOARD_Y = MARGIN_Y;
    static final Color[] playersColour = new Color[]{Color.ORANGE, Color.RED};


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


        // Outputting text for debugging
//        System.out.println(this.structures.getChildren());
//        System.out.println();


        /////////////////////////////////////////////
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
        button.setLayoutX(MARGIN_X + 90);
        button.setLayoutY(WINDOW_HEIGHT - 45);
        button.setOnAction(e -> this.newGame());
        this.controls.getChildren().add(button);

        Button instructions = new Button("Instructions");
        instructions.setLayoutX(MARGIN_X + 180);
        instructions.setLayoutY(WINDOW_HEIGHT - 45);
        instructions.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Instructions and Controls");
            alert.setContentText(INSTRUCTIONS);
            alert.show();
        });
        this.controls.getChildren().add(instructions);

        this.difficulty.setMin(0);
        this.difficulty.setMax(3);
        this.difficulty.setValue(0);
        this.difficulty.setShowTickLabels(true);
        this.difficulty.setShowTickMarks(true);
        this.difficulty.setMajorTickUnit(1);
        this.difficulty.setSnapToTicks(true);

        this.difficulty.setLayoutX(MARGIN_X + 60);
        this.difficulty.setLayoutY(WINDOW_HEIGHT - 85);
        this.controls.getChildren().add(this.difficulty);

        final Label difficultyCaption = new Label("Difficulty:");
        difficultyCaption.setTextFill(Color.GRAY);
        difficultyCaption.setLayoutX(MARGIN_X);
        difficultyCaption.setLayoutY(WINDOW_HEIGHT - 85);
        this.controls.getChildren().add(difficultyCaption);
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("CatanDice Extra Game - Created by Arjun, Manindra and Stephen");
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);

//        this.chooseGameMode();

        this.root.getChildren().add(controls);

        this.makeBoard();
        this.root.getChildren().add(this.board);
        this.root.getChildren().add(this.structures);

        this.displayState("XWXW0X0");

        stage.show();
    }
}
