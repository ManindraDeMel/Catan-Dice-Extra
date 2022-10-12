package comp1140.ass2;

import java.util.*;
import java.util.stream.Collectors;

import static comp1140.ass2.Coordinate.*;
import static comp1140.ass2.TileType.*;


public class Board {
    public Tile[] tiles;
    public static Coordinate[] coords;

    public static final TileType[] tileTypes= {
            wool, grain, ore,
            ore, bricks, timber, wool,
            grain, timber, desert, desert, bricks, grain,
            wool, bricks, timber, ore,
            ore, grain, wool
    };
    public Settlement[] settlements;
    public Castle[] castles;
    public ArrayList<Road> roads;
    public static HashMap<Coordinate, ArrayList<Coordinate>> neighbours;

    public String turn = "";

    public String oldScore = "";
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
    public Board(String turn, String oldScore) {
        coords = new Coordinate[54];
        roads = new ArrayList<>();
        this.tiles = new Tile[20];
        this.settlements = new Settlement[24]; // isn't there only 24 settlements?
        this.castles = new Castle[4];
        neighbours = new HashMap<>();
        this.turn = turn;
        this.oldScore = oldScore;
        for (int i = 0;i<4;i++) {
            this.castles[i] = new Castle(new Player(""));
        }
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
                if ( ( (rowlen==6) && (x==3) ) || ( (rowlen==8) && (x==1||x==7) ) || ( (rowlen==10) && (x!=1&&x!=9) ) ) {
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


    public static HashMap<Character, Integer[]> calculateScores(String boardState) { // calculates the points for each player [W,X] of this boardstate.
        HashMap<Character, Integer[]> scores = new HashMap<>(){{
            put('W', new Integer[]{0, 0, 0});
            put('X', new Integer[]{0, 0, 0});
        }};
        int index = 0;
        boolean checkedTitles = false;
        int[] largestArmy = CatanDiceExtra.largestArmy(boardState);
        int[] longestRoad = CatanDiceExtra.longestRoad(boardState);

        if (largestArmy[0] == largestArmy[1] && largestArmy[0] >= 3) { // the first player to build a Road sequence of length 5 gains
                                                // the longest road title; if another player also builds a sequence of length
                                                // 5, the title stays with the player who did it first.
            scores.get(boardState.charAt(0))[0] += 2;
            scores.get(boardState.charAt(0))[2]++;
            checkedTitles = true;
        }
        if (longestRoad[0] == longestRoad[1] && longestRoad[0] >= 5) {
            scores.get(boardState.charAt(0))[0] += 2;
            scores.get(boardState.charAt(0))[1]++;
            checkedTitles = true;
        }

        for (Character player : new Character[]{'W', 'X'}) {
            int score = 0;
            String playerBoardState = CatanDiceExtra.validateClass.Misc.getPlayerBoardState(boardState, player);

            if (!checkedTitles) {
                if (largestArmy[index] == Arrays.stream(largestArmy).max().getAsInt() && largestArmy[index] >= 3) {
                    score += 2;
                    if (scores.get(player)[2] < 1) {
                        scores.get(player)[2]++;
                    }
                }
                if (longestRoad[index] == Arrays.stream(longestRoad).max().getAsInt() && longestRoad[index] >= 5) {
                    score +=2;
                    if (scores.get(player)[1] < 1) {
                        scores.get(player)[1]++;
                    }
                }
            }
            for (Character c : playerBoardState.toCharArray()) {
                if (c == 'S') {
                    score++;
                }
                else if (c == 'T' || c == 'C') {
                    score += 2;
                }
                scores.get(player)[0] = score;
            }
            index++;
        }
        return scores;
    }

    public static int[] extractScoreFromNewScore(HashMap<Character, Integer[]> h) {
        return new int[]{h.get('W')[0], h.get('X')[0]};
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
                this.tiles[Integer.valueOf(playerBoardState.substring(1, 3))].Owner.name = playerId;
                this.tiles[Integer.valueOf(playerBoardState.substring(1, 3))].used = false;
                applyPlayerBoardState(playerBoardState.substring(3), playerId);
                break;
            case 'K':
                this.tiles[Integer.valueOf(playerBoardState.substring(1, 3))].Owner.name = playerId;
                this.tiles[Integer.valueOf(playerBoardState.substring(1, 3))].used = true;
                applyPlayerBoardState(playerBoardState.substring(3), playerId);
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
                buildSettlement(playerBoardState, playerId, false);
                applyPlayerBoardState(playerBoardState.substring(3), playerId);
                break;
            case 'T':
                buildSettlement(playerBoardState, playerId, true);
                applyPlayerBoardState(playerBoardState.substring(3), playerId);
                break;
        }
    }

    public void buildBuilding(String actionSub, String playerId) {
        turn = turn.substring(0, 3) + Board.removeResources(turn.substring(3), actionSub.charAt(0));
        switch (actionSub.charAt(0)) {
            case 'C':
                this.castles[Integer.valueOf(actionSub.substring(1,2))].Owner.name = playerId;
                break;
            case 'J':
                this.tiles[Integer.valueOf(actionSub.substring(1, 3))].Owner.name = playerId;
                this.tiles[Integer.valueOf(actionSub.substring(1, 3))].used = true;
                break;
            case 'K':
                this.tiles[Integer.valueOf(actionSub.substring(1, 3))].Owner.name = playerId;
                this.tiles[Integer.valueOf(actionSub.substring(1, 3))].used = false;
                break;
            case 'R':
                roads.add(
                        new Road(new Player(playerId),
                                coords[Integer.valueOf(actionSub.substring(1, 3))],
                                coords[Integer.valueOf(actionSub.substring(3, 5))]
                        )
                );
                break;
            case 'S':
                buildSettlement(actionSub, playerId, false);
                break;
            case 'T':
                buildSettlement(actionSub, playerId, true);
                break;
        }
    }

    private void buildSettlement(String actionSub, String playerId, boolean isCity) {
        for (int y = 0; y<this.settlements.length; y++) {
            if (this.settlements[y].intersectionIndex == Integer.valueOf(actionSub.substring(1, 3))) {
                this.settlements[y].isBuilt = true;
                this.settlements[y].isCity = isCity;
                this.settlements[y].Owner.name = playerId;
                break;
            }
        }
    }

    public static String removeResources(String turn, char actionType) {
        return switch (actionType) {
            case 'R' -> removeResourcesHelper(turn, new String[]{"b", "l"});
            case 'S' -> removeResourcesHelper(turn, new String[]{"b", "l", "w", "g"});
            case 'T' -> removeResourcesHelper(turn, new String[]{"o", "o", "o", "g", "g"});
            case 'J', 'K' ->  removeResourcesHelper(turn, new String[]{"o", "w", "g"});
            default -> turn;
        };
    }

    private static String removeResourcesHelper(String turn, String[] resourcesToRemove) {
        for (String s : resourcesToRemove) {
            turn = turn.replaceFirst(s, "");
        }
        return turn;
    }
    public void applyBoardState(String boardState) {
        for (char p : new char[]{'W', 'X'}) {
            applyPlayerBoardState(CatanDiceExtra.validateClass.Misc.getPlayerBoardState(boardState, p), Character.toString(p));
        }
    }

    public static String getScoreFromBoardState(String boardState) {
        return boardState.substring(boardState.indexOf('W', boardState.indexOf('W', 2) + 1));
    }
    public static String getTurnFromBoardState(String boardState) {
        return boardState.substring(0, boardState.indexOf('W', 2));
    }
    public static String toStringWithNewScore(Board board) {
        HashMap<Character, Integer[]> scores = Board.calculateScores(board.toString());
        String wScore = "W" + CatanDiceExtra.validateClass.Misc.addZero(Board.extractScoreFromNewScore(scores)[0]);
        String xScore = "X" + CatanDiceExtra.validateClass.Misc.addZero(Board.extractScoreFromNewScore(scores)[1]);
        if (scores.get('W')[1] == 1) {
            wScore += "R";
        }
        else if (scores.get('X')[1] == 1) {
            xScore += "R";
        }
        if (scores.get('W')[2] == 1) {
            wScore += "A";
        }
        else if (scores.get('X')[2] == 1) {
            xScore += "A";
        }
        String newScore = wScore + xScore;
        return board.toString().replace(board.oldScore, newScore);

    }

    @Override
    public String toString() {
        String[] playerBoardStates = new String[2];
        int index = 0;
        for (String name : new String[]{"W", "X"}) {
            String playerBoardState = "";
            playerBoardState += name;

            List<Castle> castleList = Arrays.asList(castles); // filter by owner
            castleList = castleList.stream().filter(castle -> filterCondition(castle, name.charAt(0))).collect(Collectors.toList());
            List<Tile> tileList = Arrays.asList(tiles);
            tileList = tileList.stream().filter(tile -> filterCondition(tile, name.charAt(0))).collect(Collectors.toList());
            List<Road> roadList = new ArrayList<>();
            roadList.addAll(roads);
            roadList = roadList.stream().filter(road -> filterCondition(road, name.charAt(0))).collect(Collectors.toList());
            Object[] roadArr = roadList.toArray();
            Arrays.sort(roadArr);
            List<Settlement> settlementList = Arrays.asList(settlements);
            settlementList = settlementList.stream().filter(settlement -> filterCondition(settlement, name.charAt(0)) && !settlement.isCity).collect(Collectors.toList());
            List<Settlement> cityList = Arrays.asList(settlements);
            cityList = cityList.stream().filter(city -> filterCondition(city, name.charAt(0)) && city.isCity).collect(Collectors.toList());

            for (Castle castle : castleList) { // add to each boardstate
                playerBoardState += castle.toString();
            }
            for (Tile tile : tileList) {
                playerBoardState += tile.toString();
            }
            for (Object road : roadArr) {
                playerBoardState += road.toString();
            }
            for (Settlement settlement : settlementList) {
                playerBoardState += settlement.toString();
            }
            for (Settlement city : cityList) {
                playerBoardState += city.toString();
            }
            playerBoardStates[index] = playerBoardState;
            index++;
        }
        return turn + playerBoardStates[0] + playerBoardStates[1] + oldScore;
    }
    private static boolean filterCondition(GamePiece g, char playerToMatch) {
        if (g.Owner.name != "") {
            return g.Owner.name.charAt(0) == playerToMatch;
        }
        return false;
    }
}
