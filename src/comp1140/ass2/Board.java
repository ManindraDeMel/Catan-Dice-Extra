package comp1140.ass2;

import java.util.*;

import static comp1140.ass2.Coordinate.*;
import static comp1140.ass2.TileType.*;
import static java.lang.VersionProps.build;


public class Board {
    public Tile[] tiles;
    public static Coordinate[] coords;

    public Settlement[] settlements;
    public Castle[] castles;
    public ArrayList<Road> roads;
    public static HashMap<Coordinate, ArrayList<Coordinate>> neighbours;
    /**
     * Instantiates all the array fields of a Board.
     * Each Array that can be is indexed the same as the indices for corners and tiles provided.
     * Loops through all 54 coordinates, changing x and y as appropriate.
     * Additionally creates unbuilt settlements and tiles where appropriate.
     * Designed to be modular, allowing for simple changes to things like tiltypes,
     * but larger changes to the dimension of the board would require a complete rewrite.
     * Creates everything with an empty player name "", so should only every be executed once.
     * Authored by Stephen Burg - u7146285, but the system of coordinates it instantiates was created collaboratively
     * by the team and also Jonte before he left.
     */
    public Board() {
        coords = new Coordinate[54];
        roads = new ArrayList<>();
        this.tiles = new Tile[20];
        this.settlements = new Settlement[24]; // isn't there only 24 settlements?
        this.castles = new Castle[4];
        neighbours = new HashMap<>();
        for (int i = 0;i<4;i++) {
            this.castles[i] = new Castle(new Player(""));
        }
        TileType[] tileTypes= {
                wool, grain, ore,
                ore, bricks, timber, wool,
                grain, timber, desert, desert, bricks, grain,
                wool, bricks, timber, ore,
                ore,grain, wool};
        int tilenum=0;
        int settlenum = 0;
        Boolean cityable = false;
        Coordinate[] tilecoords = new Coordinate[6];
        int y = 0;
        int x = 1;
        int rowlen = 6;
        for (int c = 0; c <= 53; c++) {
            coords[c] = new Coordinate(x, y);
            coords[c].setIndex(c);
            if (x%2==1) {
                if (y<3) {
                    tilecoords[0] = new Coordinate(x, y);
                    tilecoords[1] = new Coordinate(x+1, y);
                    tilecoords[2] = new Coordinate(x+2, y+1);
                    tilecoords[3] = new Coordinate(x+1, y+1);
                    tilecoords[4] = new Coordinate(x, y+1);
                    tilecoords[5] = new Coordinate(x-1, y);
                    this.tiles[tilenum] = new Tile(new Player(""), tilecoords, tilenum, false, tileTypes[tilenum]);
                    tilenum++;
                    if (tileTypes[tilenum]==desert) {
                        this.tiles[tilenum] = new Tile(new Player(""), tilecoords, tilenum, false, tileTypes[tilenum]);
                        tilenum++;
                    }
                } else if (y>3) {
                    tilecoords[0] = new Coordinate(x, y);
                    tilecoords[1] = new Coordinate(x+1, y);
                    tilecoords[2] = new Coordinate(x+2, y-1);
                    tilecoords[3] = new Coordinate(x+1, y-1);
                    tilecoords[4] = new Coordinate(x, y-1);
                    tilecoords[5] = new Coordinate(x-1, y);
                    this.tiles[tilenum] = new Tile(new Player(""), tilecoords, tilenum, false, tileTypes[tilenum]);
                    tilenum++;
                }
                if ( ( (rowlen==6) && (x==3) ) || ( (rowlen==8) && (x==1||x==5) ) || ( (rowlen==10) && (x!=1&&x!=5) ) ) {
                    cityable = true;
                } else {
                    cityable = false;
                }
                this.settlements[settlenum] = new Settlement(new Player(""), new Coordinate(x, y), cityable, c);
                settlenum++;
            }
            if (y==2&&x==10) {
                x = 0;
                y = 3;
            } else if (y<3) {
                rowlen = 2*y+6;
                if (x==(rowlen-1)) {
                    x = 0;
                } else if (x==rowlen) {
                    x=1;
                    y++;
                } else {
                    x+=2;
                }
            } else {
                rowlen = -2*y+16;
                if (x==(rowlen-1)) {
                    x = 0;
                    y++;
                } else if (x==rowlen) {
                    x=1;
                } else {
                    x+=2;
                }
            }

        }
        for (Coordinate coord : coords) {
            ArrayList<Coordinate> acc = new ArrayList<>();
            for (Coordinate nb : coords) {
                if (checkAdjacent(coord, nb)) {
                    acc.add(nb);
                }
            }
            neighbours.put(coord, acc);
        }
    }

    /**
     * Applies a Player Board State String to an already instantiated board, changeing the states and ownership of various structures.
     * Handles roads by adding them to an arraylist, as no clean method of keeping them unbuilt in an array and trying to find them
     * like settlements was found.
     * Does not check if things are already owned or in the Road list, should most likely only be used applied to a newly instantiated board.
     * On a turn by turn basis actions should be applied to an existing board by some yet to be written method, rather than full boardstates.
     * So far untested.
     * Authored by Stephen Burg - u7146285
     */
    public void applyPlayerBoardState(String playerBoardState, String playerId) {
        if (playerBoardState == "") {
            return;
        }
        switch (playerBoardState.charAt(0)) {
            case 'C':
                this.castles[Integer.valueOf(playerBoardState.substring(1,2))].Owner.name = playerId;
                applyPlayerBoardState(playerBoardState.substring(2), playerId);
                break;
            case 'J':
                this.tiles[Integer.valueOf(playerBoardState.substring(1, 4))].Owner.name = playerId;
                this.tiles[Integer.valueOf(playerBoardState.substring(1, 4))].used = true;
                applyPlayerBoardState(playerBoardState.substring(4), playerId);
                break;
            case 'K':
                this.tiles[Integer.valueOf(playerBoardState.substring(1, 4))].Owner.name = playerId;
                this.tiles[Integer.valueOf(playerBoardState.substring(1, 4))].used = false;
                applyPlayerBoardState(playerBoardState.substring(4), playerId);
                break;
            case 'R':
                roads.add(
                        new Road(new Player(playerId),
                                coords[Integer.valueOf(playerBoardState.substring(1, 3))],
                                coords[Integer.valueOf(playerBoardState.substring(3, 5))]
                        )
                );
                applyPlayerBoardState(playerBoardState.substring(5), playerId);
                break;
            case 'S':
                for (int y = 0; y<this.settlements.length; y++) {
                    if (this.settlements[y].intersectionIndex == Integer.valueOf(playerBoardState.substring(1, 3))) {
                        this.settlements[y].isBuilt = true;
                        this.settlements[y].isCity = false;
                        this.settlements[y].Owner.name = playerId;
                    }
                }
                applyPlayerBoardState(playerBoardState.substring(3), playerId);
                break;
            case 'T':
                for (int y = 0; y<this.settlements.length; y++) {
                    if (this.settlements[y].intersectionIndex == Integer.valueOf(playerBoardState.substring(1, 3))) {
                        this.settlements[y].isBuilt = true;
                        this.settlements[y].isCity = true;
                        this.settlements[y].Owner.name = playerId;
                    }
                }
                applyPlayerBoardState(playerBoardState.substring(3), playerId);
                break;
        }
    }


    public void addAction(String boardState, String action) { // Task 9 functionality
        switch (action.substring(0, 5)) { // here we match for the type of action we received
            case "keep" -> modifyResources();
            case "buil" -> buildBuilding();
            case "trad" -> trade(); // TODO
            case "swap" -> swap();
        };
    }

    private void buildBuilding() {
    }

    private void swap() {

    }
    private void trade() {
    }

    private void modifyResources() {
    }


    public void applyBoardState(String boardState) {
        for (char p : new char[]{'W', 'X'}) {
            applyPlayerBoardState(CatanDiceExtra.validateClass.Misc.getPlayerBoardState(boardState, p), Character.toString(p));
        }
    }

    public Tile getTile() {
        return null;
    }

    public GamePiece getGamePiece() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
