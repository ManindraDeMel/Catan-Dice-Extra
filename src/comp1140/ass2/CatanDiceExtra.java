package comp1140.ass2;
import java.util.*;

public class CatanDiceExtra {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<String> playersNames = new ArrayList<>(Arrays.asList("Manindra", "Stephen", "Arjun")); // changes when we add GUI stuff (max of 6 players?)
    public void startGame() {
        Board board = new Board();
        board.instatiateBoard();
        for (String name : playersNames) {
            players.add(new Player(name));
        }
    }

    public void newTurn(Player player) {

    }

    /**
     * Check if the string encoding of a board state is well-formed.
     * Note that this does not mean checking if the state is valid
     * (represents a state that the player could get to in game play),
     * only that the string representation is syntactically well-formed.
     *
     * A description of the board state string will be provided in a
     * later update of the project README.
     *
     * @param boardState: The string representation of the board state.
     * @return true iff the string is a well-formed representation of
     * a board state, false otherwise.
     */
    public static boolean isBoardStateWellFormed(String boardState) {
        // FIXME: Task 3
	return false;
    }

    /**
     * Check if the string encoding of a player action is well-formed.
     * Note that this does not mean checking if the action is valid
     * (represents a player action that the player could get to in game play),
     * only that the string representation is syntactically well-formed.
     *
     * A description of the board state string will be provided in a
     * later update of the project README.
     *
     * @param action: The string representation of the action.
     * @return true iff the string is a well-formed representation of
     * a player action, false otherwise.
     */
    public static boolean isActionWellFormed(String action) {
        // FIXME: Task 4
	    return false;
    }

    /**
     * Roll the specified number of *random* dice, and return the
     * rolled resources in string form.
     * The outcomes of dice rolls should be uniformly distributed.
     *
     * @param numOfDice the number of dices to roll
     * @return alphabetically ordered [Resources] with characters
     * 'b', 'l', 'w', 'g', 'o', 'm'.
     */
    public static String rollDice(int numOfDice) {
        HashMap<Integer, String> mapToDiceVal = new HashMap<>() {{
            put(1, "b"); //brick
            put(2, "l"); //lumber
            put(3, "w"); //wool
            put(4, "g"); //grain
            put(5, "o"); //ore
            put(6, "m"); //gold (money)
        }};
        Random random = new Random();
        String resources = "";
        for (int i = 0; i < numOfDice; i++) {
            resources += String.format("%s", mapToDiceVal.get(random.nextInt(6) + 1));
        }
        char[] sortedResource = resources.toCharArray();
        Arrays.sort(sortedResource);
        String sortedResourcesStr = "";
        for (int i = 0; i < sortedResource.length; i++) {
            sortedResourcesStr += Character.toString(sortedResource[i]);
        }
        return sortedResourcesStr;
    }

    /**
     * Given a valid board state and player action, determine whether the
     * action can be executed.
     * The permitted actions depend on the current game phase:
     *
     * A. Roll Phase (keep action)
     * 1. A keep action is valid if it satisfies the following conditions:
     * - Action follows the correct format : "keep[Resources]", and the
     *   current player has the resources specified.
     * - [Rolls Done] is less than 3
     *
     *
     * B. Build Phase (build, trade, and swap actions)
     *
     * 1. A build action is valid if it satisfies the following conditions:
     * - Action follows the correct format : "build[Structure Identifier]"
     * - The current player has sufficient resources available for building
     *   the structure.
     * - The structure satisfies the build constraints (is connected to the
     *   current players road network).
     * - See details of the cost of buildable structure in README.md.
     *
     * 2. A trade action is valid if it satisfies the following conditions:
     * - Action follows the correct format : "trade[Resources]"
     * - The current player has sufficient resources available to pay for
     *   the trade.
     *
     * 3. A swap action is valid if it satisfies the following conditions:
     * - Action follows the correct format : "swap[Resource Out][Resource In]"
     * - The current player has sufficient resources available to swap out.
     * - The current player has an unused knight (resource joker) on the
     *   board which allows to swap for the desired resource.
     * @param boardState: string representation of the board state.
     * @param action: string representation of the player action.
     * @return true iff the action is executable, false otherwise.
     */

    public static boolean isActionValid(String boardState, String action) {
        char[] characters = action.toCharArray();
        String actionState = "";
        for (int i = 0; i < 4; i++) {
            actionState += characters[i];
        }
        return switch (actionState) {
            case "keep" -> validateClass.validateKeep(boardState, action);
            case "buil" -> validateClass.validateBuild(boardState, action);
            case "trad" -> validateClass.validateTrade(boardState, action);
            case "swap" -> validateClass.validateSwap(boardState, action);
            default -> false;
        };
    }
    private class validateClass {
        public static boolean validateBuild(String boardState, String action) {
            ArrayList<Character> validformat = new ArrayList<>(Arrays.asList(
                    'b',
                    'u',
                    'i',
                    'l',
                    'd',
                    'K',
                    'R',
                    'C',
                    'S',
                    'T'
            ));
            for (int i = 0; i < 10; i++) {
                validformat.add(Integer.toString(i).charAt(0)); // Add 0-9 to list
            }
            for (Character c : action.toCharArray()) {
                if (!validformat.contains(c)) {
                    return false; // Check the format
                }
            }
            // ###########
            Character buildType = action.charAt(5);
            if (ValidateBuildHelperFuncs.sufficentResourcesForBuild(boardState, buildType)) { // check for resources
                return switch (buildType) {
                    case 'C' -> ValidateBuildHelperFuncs.validateCastleBuild(boardState, action.charAt(action.length() - 1));
                    case 'K' -> ValidateBuildHelperFuncs.validateKnightBuild(boardState, action);
                    case 'R' -> ValidateBuildHelperFuncs.validateRoadBuild(boardState, action);
                    case 'S' -> ValidateBuildHelperFuncs.validateSettlementBuild(boardState, action);
                    case 'T' -> ValidateBuildHelperFuncs.validateCityBuild(boardState, action);
                    default -> false;
                };
            }
            return ValidateBuildHelperFuncs.checkBaseCase(boardState, action, buildType); // if there aren't sufficient resources, the game might have just begun.
        }
        // #######################################################################################
        public static boolean validateTrade(String boardState, String action) {
            ArrayList<Character> validFormat = new ArrayList<>(Arrays.asList('t', 'r', 'a', 'd', 'e'));
            validFormat.addAll(Misc.possibleResources); // checks for format
            for (Character c : action.toCharArray()) { // can't trade for gold.
                if (c == 'm' || !validFormat.contains(c)) {
                    return false;
                }
            }
            int numResources = action.length() - 5; // the -4 is for "trade"
            ArrayList<Character> resources = Misc.getResourcesFromBoardState(boardState);
            int numGold = 0;
            for (Character c : resources) {
                if (c == 'm') {
                    numGold++;
                }
            }
            if (numGold >= numResources * 2) {
                return true;
            }
            return false;
        }
        // #######################################################################################
        public static boolean validateKeep(String boardState, String action) {
            int numRolls = Integer.parseInt(Character.toString(boardState.toCharArray()[2]));
            if (numRolls < 3) {
                ArrayList<Character> turnBoardState = new ArrayList<>(Arrays.asList('k', 'e', 'e', 'p')); // resources are valid, next check boardstate if the resources exist
                turnBoardState.addAll(Misc.getResourcesFromBoardState(boardState));
                for (Character c : action.toCharArray()) { // checking for format of keep[Resources] && check for if resources in gamestate too
                    if (!turnBoardState.contains(c)) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        // #######################################################################################
        public static boolean validateSwap(String boardState, String action) {
            ArrayList<Character> resources = Misc.getResourcesFromBoardState(boardState);
            Character resourceIn = action.charAt(4);
            Character resourceOut = action.charAt(5);
            if (!resources.contains(resourceIn)) {
                return false; // Player doesn't have resource to trade.
            }
            String playerBoardState = Misc.getPlayerBoardState(boardState);
            if (playerBoardState == "[]") {
                return false; // Player has no builds
            }
            int currentIndex = 0;
            ArrayList<Integer> locations = new ArrayList<>();
            while (playerBoardState.indexOf("J", currentIndex) != -1) {
                int newIndex = playerBoardState.indexOf("J", currentIndex);
                locations.add(Integer.parseInt(Character.toString(playerBoardState.charAt(newIndex+1)) + Character.toString(playerBoardState.charAt(newIndex+2))));
                currentIndex = newIndex+1;
            }
            if (locations.size() == 0) {
                return false; // Player has no usable knights
            }
            for (int location : locations) {
                if (location == 9 || location == 10) {
                    return true; // multipurpose knight owned
                }
                else if (Misc.coordinateToResource[location] == resourceOut) {
                    return true; // correct knight owned
                }
            }
            return false;
        }
        // ####################################################################################### Helper functions for validateBuild();
        private class ValidateBuildHelperFuncs {
            // #######################################################################################
            private static boolean validateCastleBuild(String boardState, Character castlePosition) {
                if (boardState.contains("C" + castlePosition)) {
                    return false; // check existence
                }
                return true;
            }
            // ####################
            private static boolean validateCityBuild(String boardState, String action) {
                final ArrayList<Integer> validCityBuildLocations = new ArrayList<>(Arrays.asList(
                        1,
                        7,
                        10,
                        17,
                        18,
                        19,
                        34,
                        35,
                        36,
                        43,
                        46,
                        52
                ));
                String playerBoardState = Misc.getPlayerBoardState(boardState);
                String settlement = "S" + Character.toString(action.charAt(6)) + Character.toString(action.charAt(7));
                int location = Integer.parseInt(Character.toString(action.charAt(6)) + Character.toString(action.charAt(7)));
                if (playerBoardState.contains(settlement) && validCityBuildLocations.contains(location)) { // settlement already built check && location is in a cityable location.
                    return true;
                }
                return false;
            }
            // ####################
            private static boolean validateKnightBuild(String boardState, String action) {
                int actionCoord = Integer.parseInt(Character.toString(action.charAt(action.length() - 2)) + Character.toString(action.charAt(action.length() - 1)));
                if (actionCoord >= 0 && actionCoord < 20) {
                    ArrayList<Integer> surroundingCoords = Misc.knightIndexingToRowIndexing.get(actionCoord);
                    String playerBoardState = Misc.getPlayerBoardState(boardState);
                    return roadConnectedToKnight(playerBoardState, surroundingCoords) || settlementConnectedToKnight(playerBoardState, surroundingCoords);
                }
                return false;
            }
            // ####################
            private static boolean validateRoadBuild(String boardState, String action) {
                return !checkIfExists(boardState, action) && checkIfConnectedR(boardState, action) && checkForSettlement(boardState, action);
            }
            // ####################
            private static boolean validateSettlementBuild(String boardState, String action) {
                return !checkIfExists(boardState, action) && checkIfConnected(boardState, action);
            }
            // #################### sub-helper functions below
            private static boolean sufficentResourcesForBuild(String boardState, Character buildType) {
                final HashMap<Character, Resource> charToResource = new HashMap<>() {{ // maps to convert between our program and the assignment requirements.
                    put('b', Resource.brick);
                    put('g', Resource.wheat);
                    put('l', Resource.wood);
                    put('m', Resource.gold);
                    put('o', Resource.stone);
                    put('w', Resource.sheep);
                }};
                final HashMap<String, Character> nameToBuildID = new HashMap<>() {{
                    put("Road", 'R');
                    put("Solider", 'K');
                    put("Settlement", 'S');
                    put("Castle", 'C');
                    put("City", 'T');
                }};
                // ###############
                ArrayList<Resource> resources = new ArrayList<>();
                for (Character c : Misc.getResourcesFromBoardState(boardState)) {
                    resources.add(charToResource.get(c)); // Convert to resource type
                }
                ArrayList<ArrayList<String>> possibleBuilds = Prices.findBuilds(resources);
                for (ArrayList<String> i : possibleBuilds) {
                    for (String build : i) {
                        if (nameToBuildID.get(build) == buildType) {
                            return true;
                        }
                    }
                }
                return false;
            }
            private static boolean checkBaseCase(String boardState, String action, Character buildType) {
                if (buildType == 'R' && validateRoadLength(action)) {
                    if (boardState == "W00WXW00X00" || boardState == "X00WXW00X00") {
                        return roadOnCoast(action);
                    }
                    else if (boardState.length() == 16 && boardState.contains("R")) {
                        ArrayList<Integer> actionLocations = new ArrayList<>(Arrays.asList(
                                Integer.parseInt(Character.toString(action.charAt(action.length() - 2)) + Character.toString(action.charAt(action.length() - 1))),
                                Integer.parseInt(Character.toString(action.charAt(action.length() - 4)) + Character.toString(action.charAt(action.length() - 3)))
                        ));
                        int indexOfRoad = boardState.indexOf("R");
                        ArrayList<Integer> boardLocations = new ArrayList<>(Arrays.asList(
                                Integer.parseInt(Character.toString(boardState.charAt(indexOfRoad + 1)) + Character.toString(boardState.charAt(indexOfRoad + 2))),
                                Integer.parseInt(Character.toString(boardState.charAt(indexOfRoad + 3)) + Character.toString(boardState.charAt(indexOfRoad + 4)))
                        ));
                        for (int i = 0; i < 2; i++) {
                            for (int j = 0; j < 2; j ++) {
                                if (Misc.getDistance(Misc.convertToCoordinate(actionLocations.get(i)), Misc.convertToCoordinate((boardLocations.get(j)))) < 5) {
                                    return false;
                                }
                            }
                        }
                        return roadOnCoast(action);
                    }
                }
                return false;
            }
            private static boolean roadOnCoast(String action) {
                for (String s : Misc.getRoadCoordsFromAction(action)) {
                    if (Misc.coastalRoadNodes.contains(s)) {
                        return true;
                    }
                }
                return false;
            }
            private static boolean checkIfExists(String boardState, String action) {
                String build = "";
                if (action.charAt(5) == 'R') {
                    for (Character c : new char[]{action.charAt(5), action.charAt(6), action.charAt(7), action.charAt(8), action.charAt(9)}) {
                        build += Character.toString(c);
                    }
                }
                else {
                    for (Character c : new char[]{action.charAt(5), action.charAt(6), action.charAt(7)}) {
                        build += Character.toString(c);
                    }
                }
                if (boardState.contains(build)) {
                    return true;
                }
                return false;
            }
            private static boolean checkIfConnected(String boardState, String action) {
                String playerBoardState = Misc.getPlayerBoardState(boardState);
                int actionCoord = Integer.parseInt(Character.toString(action.charAt(action.length() - 2)) + Character.toString(action.charAt(action.length() - 1)));
                int currentIndex = 0;
                while (playerBoardState.indexOf("R", currentIndex) != -1) { // Maybe make this a getRoadsFromPlayerBoardState() function??
                    int newIndex = playerBoardState.indexOf("R", currentIndex);
                    int firstCoord = Integer.parseInt(Character.toString(playerBoardState.charAt(newIndex + 1)) + Character.toString(playerBoardState.charAt(newIndex + 2)));
                    int secondCoord = Integer.parseInt(Character.toString(playerBoardState.charAt(newIndex + 3)) + Character.toString(playerBoardState.charAt(newIndex + 4)));
                    if (actionCoord == firstCoord || actionCoord == secondCoord) {
                        return true;
                    }
                    currentIndex = newIndex + 1;
                }
                return false;
            }
            private static boolean checkIfConnectedR(String boardState, String action) { // because roads have two coordinates we can check them both recursively.
                String[] coords = Misc.getRoadCoordsFromAction(action);
                if (validateRoadLength(action)) { // coordinates for the road can't be unreasonably far away.
                    return checkIfConnected(boardState, coords[0]) || checkIfConnected(boardState, coords[1]);
                }
                return false;
            }
            private static boolean validateRoadLength(String action) {
                String[] coords = Misc.getRoadCoordsFromAction(action);
                ArrayList<ArrayList<Integer>> roads = Misc.knightIndexingToRowIndexing;
                for (ArrayList<Integer> hexCoords : roads) {
                    if (hexCoords.contains(Integer.parseInt(coords[0]))) {
                        int indexOfOneCoord = hexCoords.indexOf(Integer.parseInt(coords[0]));
                        if (indexOfOneCoord == 0) {
                            if (hexCoords.get(1) == Integer.parseInt(coords[1]) || hexCoords.get(hexCoords.size() - 1) == Integer.parseInt(coords[1])) {
                                return true;
                            }
                        }
                        else if (indexOfOneCoord == hexCoords.size() - 1) {
                            if (hexCoords.get(0) == Integer.parseInt(coords[1]) || hexCoords.get(hexCoords.size() - 2) == Integer.parseInt(coords[1])) {
                                return true;
                            }
                        }
                        else {
                            if (hexCoords.get(indexOfOneCoord - 1) == Integer.parseInt(coords[1]) || hexCoords.get(indexOfOneCoord + 1) == Integer.parseInt(coords[1])) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
            private static boolean checkForSettlement(String boardState, String action) {
                ArrayList<Integer> coordsWithoutSettlements = new ArrayList<>(Arrays.asList(
                        3, 4, 5, 6, 11, 12, 13, 14, 15, 38, 39, 40, 41, 42, 47, 48, 49, 50
                ));
                for (int i = 21; i < 33; i++) {
                    coordsWithoutSettlements.add(i); // add middle part
                }
                // ###
                String[] coords = Misc.getRoadCoordsFromAction(action);
                String playerBoardState = Misc.getPlayerBoardState(boardState);
                // ##
                int currentIndex = 0;
                ArrayList<String> firstCoordsInPlayerState = new ArrayList<>();
                while (playerBoardState.indexOf("R", currentIndex) != -1) {
                    int newIndex = playerBoardState.indexOf("R", currentIndex);
                    firstCoordsInPlayerState.add(Character.toString(playerBoardState.charAt(newIndex+1)) + Character.toString(playerBoardState.charAt(newIndex+2)));
                    currentIndex = newIndex + 1;
                }
                for (String coord : coords) {
                    for (String firstCoord : firstCoordsInPlayerState) {
                        if (playerBoardState.contains("R" + coord) || playerBoardState.contains("R" + firstCoord + coord)) { // this means coord is the common coord to check for a settlement
                            if (coordsWithoutSettlements.contains(Integer.parseInt(coord))) {
                                return true;
                            }
                            else if (playerBoardState.contains("S" + coord)) {
                                return true;
                            }
                            else if (playerBoardState.contains("T" + coord)) {
                                return true;
                            }
                        }
                    }
                }
                return false; // no common roads to connect to.
            }
            private static boolean roadConnectedToKnight(String playerBoardState, ArrayList<Integer> surroundingCoords) {
                int currentIndex = 0;
                while (playerBoardState.indexOf("R", currentIndex) != -1) {
                    int newIndex = playerBoardState.indexOf("R", currentIndex);
                    ArrayList<Integer> road = new ArrayList<>();
                    road.add(
                            Integer.parseInt(Character.toString(playerBoardState.charAt(newIndex+1)) +
                            Character.toString(playerBoardState.charAt(newIndex+2)))
                    );
                    road.add(
                            Integer.parseInt(Character.toString(playerBoardState.charAt(newIndex+3)) +
                            Character.toString(playerBoardState.charAt(newIndex+4)))
                    );

                    if (surroundingCoords.contains(road.get(0)) && surroundingCoords.contains(road.get(1))) {
                        int indexOfOneCoord = surroundingCoords.indexOf(road.get(0)); // check if the roads are next to each other
                        if (indexOfOneCoord == 0) { // borders
                            if (surroundingCoords.get(1) == road.get(1) || surroundingCoords.get(surroundingCoords.size() - 1) == road.get(1)) {
                                return true;
                            }
                        }
                        else if (indexOfOneCoord == surroundingCoords.size() - 1) { // borders
                            if (surroundingCoords.get(0) == road.get(1) || surroundingCoords.get(surroundingCoords.size() - 2) == road.get(1)) {
                                return true;
                            }
                        }
                        else {
                            if (surroundingCoords.get(indexOfOneCoord - 1) == road.get(1) || surroundingCoords.get(indexOfOneCoord + 1) == road.get(1)) {
                                return true;
                            }
                        }
                    }
                    currentIndex = newIndex + 1;
                }
                return false;
            }
            private static boolean settlementConnectedToKnight(String playerBoardState, ArrayList<Integer> surroundingCoords) {
                return settlementConnectedToKnightHelper(playerBoardState, surroundingCoords, "S") || settlementConnectedToKnightHelper(playerBoardState, surroundingCoords, "T");
            }
            private static boolean settlementConnectedToKnightHelper (String playerBoardState, ArrayList<Integer> surroundingCoords, String buildtype) {
                int currentIndex = 0;
                while (playerBoardState.indexOf(buildtype, currentIndex) != -1) {
                    int newIndex = playerBoardState.indexOf(buildtype, currentIndex);
                    int coord  = Integer.parseInt(
                            Character.toString(playerBoardState.charAt(newIndex+1)) + Character.toString(playerBoardState.charAt(newIndex+2))
                    );
                    if (surroundingCoords.contains(coord)) {
                        return true;
                    }
                    currentIndex = newIndex + 1;
                }
                return false;
            }
        }
        // ####################################################################################### Miscellaneous Helper functions / constants
        private class Misc {
            private static final List<Character> possibleResources = new ArrayList<>(Arrays.asList('b', 'g', 'l', 'm', 'o', 'w'));

            private static final ArrayList<ArrayList<Integer>> knightIndexingToRowIndexing = new ArrayList<>(Arrays.asList(
                    // # Row 1
                    new ArrayList<>(Arrays.asList(0, 4, 8, 12, 7, 3)),
                    new ArrayList<>(Arrays.asList(1, 5, 9, 13, 8, 4)),
                    new ArrayList<>(Arrays.asList(2, 6, 10, 14, 9, 5)),
                    // # Row 2
                    new ArrayList<>(Arrays.asList(7, 12, 17, 22, 16, 11)),
                    new ArrayList<>(Arrays.asList(8, 13, 18, 23, 17, 12)),
                    new ArrayList<>(Arrays.asList(9, 14, 19, 24, 18, 13)),
                    new ArrayList<>(Arrays.asList(10, 15, 20, 25, 19, 14)),
                    // # Row 3
                    new ArrayList<>(Arrays.asList(16, 22, 28, 33, 27, 21)),
                    new ArrayList<>(Arrays.asList(17, 23, 29, 34, 28, 22)),

                    new ArrayList<>(Arrays.asList(18, 24, 30, 35, 29, 23)), // duplication for tile 9 && 10
                    new ArrayList<>(Arrays.asList(18, 24, 30, 35, 29, 23)),

                    new ArrayList<>(Arrays.asList(19, 25, 31, 36, 30, 24)),
                    new ArrayList<>(Arrays.asList(20, 26, 32, 37, 31, 25)),
                    // # Row 4
                    new ArrayList<>(Arrays.asList(28, 34, 39, 43, 38, 33)),
                    new ArrayList<>(Arrays.asList(29, 35, 40, 44, 39, 34)),
                    new ArrayList<>(Arrays.asList(30, 36, 41, 45, 40, 35)),
                    new ArrayList<>(Arrays.asList(31, 37, 42, 46, 41, 36)),
                    // # Row 5
                    new ArrayList<>(Arrays.asList(39, 44, 48, 51, 47, 43)),
                    new ArrayList<>(Arrays.asList(40, 45, 49, 52, 48, 44)),
                    new ArrayList<>(Arrays.asList(41, 46, 50, 53, 49, 45))
            ));
            final static Character[] coordinateToResource = new Character[]{
                    // # Row 1
                    'w',
                    'g',
                    'o',
                    // # Row 2
                    'o',
                    'b',
                    'l',
                    'w',
                    // # Row 3
                    'g',
                    'l',
                    'm',
                    'm',
                    'b',
                    'g',
                    // # Row 4
                    'w',
                    'b',
                    'l',
                    'o',
                    // # Row 5
                    'o',
                    'g',
                    'w'
            };
            final static ArrayList<String> coastalRoadNodes = new ArrayList<>(Arrays.asList(
                    "03",
                    "07",
                    "16",
                    "21",
                    "33",
                    "43",
                    "47",
                    "48",
                    "49",
                    "50",
                    "46",
                    "37",
                    "32",
                    "20",
                    "10",
                    "06",
                    "05",
                    "04"
            ));
            //
            private static ArrayList<Character> getResourcesFromBoardState(String boardState) {
                ArrayList<Character> resources = new ArrayList<>();
                for (Character c : boardState.toCharArray()) {
                    if (possibleResources.contains(c)) { // might be less than 6 resources so we might cut into a players gamestate, this filters that out.
                        resources.add(c);
                    }
                }
                return resources;
            }

            public static String getPlayerBoardState(String boardState) {
                HashMap<Character, String> switchPlayers = new HashMap<>() {{
                    put('W', "X");
                    put('X', "W");
                }};
                Character playerTurn = boardState.charAt(0);
                return getString(boardState, playerTurn, switchPlayers);
            }
            public static String getPlayerBoardState(String boardState, Character playerTurn) {
                HashMap<Character, String> switchPlayers = new HashMap<>() {{
                    put('W', "X");
                    put('X', "W");
                }};
                return getString(boardState, playerTurn, switchPlayers);
            }
            private static String getString(String boardState, Character playerTurn, HashMap<Character, String> switchPlayers) {
                int startOfPlayerBoardState = boardState.indexOf(Character.toString(playerTurn), 1);
                int endOfPlayerBoardState = boardState.indexOf(switchPlayers.get(playerTurn), startOfPlayerBoardState);
                List<Character> playerBoardState = new ArrayList<>();
                for (Character c : boardState.toCharArray()) {
                    playerBoardState.add(c);
                }
                String result = "";
                for (Character c : playerBoardState.subList(startOfPlayerBoardState + 1, endOfPlayerBoardState)) {
                    result += Character.toString(c);
                }
                return result;
            }

            private static Coordinate convertToCoordinate(int boardCoord) {
                int yCoord = 0, xCoord = 0;
                for (int hexIndex = 0; hexIndex < Misc.knightIndexingToRowIndexing.size(); hexIndex++) {
                    if (Misc.knightIndexingToRowIndexing.get(hexIndex).contains(boardCoord)) {
                        yCoord = hexIndex;
                        xCoord = Misc.knightIndexingToRowIndexing.get(hexIndex).indexOf(boardCoord);
                    }
                }
                return new Coordinate(xCoord, yCoord);
            }
            private static String[] getRoadCoordsFromAction(String action) {
                String firstCoord = Character.toString(action.charAt(action.length() - 4)) + Character.toString(action.charAt(action.length() - 3));
                String secondCoord = Character.toString(action.charAt(action.length() - 2)) + Character.toString(action.charAt(action.length() - 1));
                return new String[]{firstCoord, secondCoord};
            }
            private static double getDistance(Coordinate a, Coordinate b) {
                return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
            }
        }
    }

    /**
     * Return an integer array containing the length of the longest contiguous
     * road owned by each player.
     * For example : given [Board State] =
     * "W61bgglwwWJ05K01K02R0105R0205R0206R0509R0610R0913R1015S02S09S10XK12R2026R2632R3137R3237R3742S37W07RAX01"
     * - Player 'W' has the longest road length of 6
     * - Player 'X' has the longest road length of 4
     * - The method should return {6, 4}
     * @param boardState: string representation of the board state.
     * @return array of contiguous road lengths, one per player.
     */
    public static int[] longestRoad(String boardState) {
        int[] longestRoadArr = new int[2];
        char[] players = new char[]{'W', 'X'};
        for (int i = 0; i < players.length; i++) {
            char player = players[i];
            ArrayList<ArrayList<Integer>> playerRoads = longestRoadHelper.getRoads(player, boardState);
            if (playerRoads.size() != 0) { // has at least 1 road
                longestRoadArr[i] = longestRoadHelper.getLongestRoad(longestRoadHelper.generateGraph(playerRoads));
            }
            else {
                longestRoadArr[i] = 0;
            }
        }
        return longestRoadArr;
    }
    private class longestRoadHelper {
        public static ArrayList<ArrayList<Integer>> getRoad(String playerBoardState) {
            int currentIndex = 0;
            ArrayList<ArrayList<Integer>> roads = new ArrayList<>();
            while (playerBoardState.indexOf("R", currentIndex) != -1) {
                int newIndex = playerBoardState.indexOf("R", currentIndex);
                roads.add(new ArrayList<>(Arrays.asList(
                        Integer.parseInt(Character.toString(playerBoardState.toCharArray()[newIndex+1]) + Character.toString(playerBoardState.toCharArray()[newIndex+2])),
                        Integer.parseInt(Character.toString(playerBoardState.toCharArray()[newIndex+3]) + Character.toString(playerBoardState.toCharArray()[newIndex+4]))
                )));
                currentIndex = newIndex + 1;
            }
            return roads;
        }
        public static ArrayList<ArrayList<Integer>> getRoads(Character player, String boardState) {
            return getRoad(validateClass.Misc.getPlayerBoardState(boardState, player));
        }
        public static HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> generateGraph(ArrayList<ArrayList<Integer>> roads) {
            HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> graph = new HashMap<>();
            for(ArrayList<Integer> road : roads) {
                ArrayList<ArrayList<Integer>> connectedRoads = new ArrayList<>();
                for (ArrayList<Integer> road2 : roads) {
                    if (road != road2) {
                        if (road.get(1) == road2.get(0)) {
                                connectedRoads.add(road2);
                        }
                    }
                }
                graph.put(road, connectedRoads);
            }
            return graph;
        }
        public static int getLongestRoad(HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> graph) {
            ArrayList<Integer> coastalNodesInt = new ArrayList<>();
            for (String s : validateClass.Misc.coastalRoadNodes) {
                coastalNodesInt.add(Integer.parseInt(s));
            }
            for (ArrayList<Integer> road : graph.keySet()) {
                if (coastalNodesInt.contains(road.get(0)) || coastalNodesInt.contains(road.get(1))) {
                    return getLongestRoadHelper(1, graph.get(road), graph);
                }
            }
            return -1; // if there are no coastal roads
        }
        public static int getLongestRoadHelper(int counter, ArrayList<ArrayList<Integer>> connectedRoads, HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> graph) {
            if (connectedRoads.size() == 0) {
                return counter;
            }
            ArrayList<Integer> children = new ArrayList<>();
            counter++;
            for (ArrayList<Integer> road : connectedRoads) { // basic recursion
                int debug = getLongestRoadHelper(counter, graph.get(road), graph);
                children.add(debug);
            }
            return Collections.max(children);
        }
    }

    /**
     * Return an integer array containing the size of the army owned by
     * each player.
     * For example : given [Board State] =
     * "W61bgglwwWJ05K01K02R0105R0205R0206R0509R0610R0913R1015S02S09S10XK12R2026R2632R3137R3237R3742S37W07RAX01"
     * - Player 'W' has an army of size 3
     * - Player 'X' has an army of size 1
     * - The method should return {3, 1}
     * @param boardState: string representation of the board state.
     * @return array of army sizes, one per player.
     */
    public static int[] largestArmy(String boardState) {
        int[] largeArmy = new int[2];

        // Extracting the Board State of each player
        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));

        // Checking the army size for each player
        largeArmy[0]= (int) playerW.chars().filter(ch -> ch == 'K').count() + (int) playerW.chars().filter(ch -> ch == 'J').count();
        largeArmy[1] = (int) playerX.chars().filter(ch -> ch == 'K').count() + (int) playerX.chars().filter(ch -> ch == 'J').count();

        return largeArmy;

    }

    /**
     * Given a valid board state and player action, this method should return
     * the next new board state that results from executing the action.
     * This method should both handle Start of the Game, Middle of the Game,
     * and Game End.
     *
     * A. Start of the Game
     * For example : given boardState = "W00WXW00X00", action = "buildR0205"
     * - Player 'W' builds a road from index 02 to 05
     * - The next boardState should be "X00WR0205XW00X00"
     * - Consult details of the rules for the Start of the Game in README.md
     *
     * B. Middle of the Game
     * For example : given boardState = "W61bbbgwwWR0205R0509S02XR3237W01X00", action = "keepbbbw"
     * - Player 'W' keeps three brick and one wool, and re-rolls two dice.
     * - The next boardState should be "W62[Next Resources]WR0205R0509S02XR3237W01X00"
     * - [Next Resources] can be any 6 resources that contain 3 bricks, 1 wool
     * - Some examples of [Next Resources] are "bbbbmw", "bbbglw", "bbblow", etc
     *
     * C. Game End
     * For example : given boardState = "X63lmoWK01K02K04K05K06R0105R0205R0206R0408R0509R0610R0812R0813R0913R0914R1014R1015R1318R1419R1520S01S02S08S09T10XJ09K10K11K12K15K16R1824R1924R1925R2025R2026R2430R2531R2632R3035R3036R3136R3137R3237R3641R3742R4145R4146R4246R4549S19S20S37S45T36W06X10RA"
     * - Player 'X' got the score 10 and game ended
     * - No action can be applied at this stage
     * @param boardState: string representation of the board state.
     * @param action: string representation of the player action.
     * @return string representation of the updated board state.
     */
    public static String applyAction(String boardState, String action) {
        // FIXME: Task 9
        return null;
    }

    /**
     * Given valid board state, this method checks if a sequence of player
     * actions is executable.
     * For example : given boardState = "W63bbglowWR0205R0509S02XR3237W01X00", actionSequence = {"buildK02","swapbo","buildR0105"}
     * - Player 'W' has resources available to build a knight at index 02 using 1 ore, 1 wool and 1 grain
     * - Player 'W' has resources available to swap 1 brick for 1 ore, using the knight
     * - Player 'W' has resources available to build a road at index 01 to 05 using 1 brick and 1 lumber
     * @param boardState: string representation of the board state.
     * @param actionSequence: array of strings, each representing one action
     * @return true if the sequence is executable, false otherwise.
     */
    public static boolean isActionSequenceValid(String boardState, String[] actionSequence) {
        // FIXME: Task 10a
        return false;
    }

    /**
     * Given a valid board state and a sequence of player actions, this
     * method returns the new board state after executing the sequence of
     * actions. You can assume that the sequence is executable.
     * For example : given boardState = "W63bbglowWR0205R0509S02XR3237W01X00", actionSequence = "buildK02","swapbo","buildR0105"
     * - The next boardState should be "X61[Next Resources]WK02R0105R0205R0509S02XR3237W01X00"
     * - Player 'W' knight at index 02 is built
     * - Player 'W' swaps a resource and the knight becomes used
     * - Player 'W' built road R0105
     * - Player 'W' turn ends and the current player becomes 'X'
     * - [Next Resources] can be any of 6 resources of player 'X'
     * @param boardState: string representation of the board state
     * @param actionSequence: array of strings, each representing one action
     * @return string representation of the new board state
     */
    public static String applyActionSequence(String boardState, String[] actionSequence) {
        // FIXME: Task 10b
        return null;
    }

    /**
     * Given a valid board state, return all applicable player action sequences.
     * The method should return an array of sequences, where each sequence is
     * an array of action string representations.
     *
     * If the current phase of the game is the Start of Game phase, each of
     * the sequences should contain just one road building action (that is
     * a permitted initial road for the player).
     *
     * If the current phase of the game is the Roll phase, each of the
     * sequences should contain just one action, specifying a possible
     * next roll (i.e., resources to keep).
     *
     * If the current phase is the Build phase, the sequences should be all
     * non-redundant sequences of trade, swap and build actions that the
     * player can take.
     *
     * In this context, an action sequence is considered non-redundant if
     * 1. All resources gained through trade and swap actions are totally used.
     *    i.e. the turn finishes with 0 of that resource.
     * 2. A trade action occurs at most once during the action sequence.
     * 3. Gained resources through the trade and swap actions are not later
     *    traded/swapped away.
     * 4. The empty sequence is always non-redundant (i.e. the player takes no
     *    action).
     *
     * Note, there are other sources of redundancy in action sequences besides the
     * ones that are listed here. One of the more noteworthy ones is the ordering of
     * actions within a sequence whereby two different action sequences may result
     * in the same state when applied. While this is not relevant for this task, it
     * may prove useful to consider this for your "smart" game AI in task 14.
     *
     * In the build phase, one of the possible sequences is always to end
     * the player's turn without taking any action, i.e., an empty sequence.
     *
     * The order of the action sequences in the return array is unspecified,
     * i.e., does not matter. (Of course, the order of actions within each
     * sequence does matter.)
     *
     * @param boardState: string representation of the current board state.
     * @return array of possible action sequences.
     */
    public static String[][] generateAllPossibleActionSequences(String boardState) {
        // FIXME: Task 12
        return null;
    }

    /**
     * Given a valid board state, return a valid action sequence.
     *
     * This method is the interface to your game AI. It is given the current
     * state of the game, and should return the sequence of actions your AI
     * chooses to take.
     *
     * An array of length 0 is interpreted as finishing the current turn
     * without taking any further action.
     *
     * @param boardState: string representation of the board state.
     * @return array of strings representing the actions the AI will take.
     */
    public static String[] generateAction(String boardState) {
        // FIXME: Task 13
        // FIXME: Task 14 Implement a "smart" generateAction()
        return null;
    }

    public Boolean isGameOver() {
        return null;
    }

    public void GameOver() {

    }
}
