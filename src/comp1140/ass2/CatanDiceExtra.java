package comp1140.ass2;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
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
        private static final List<Character> possibleResources = new ArrayList<>(Arrays.asList('b', 'g', 'l', 'm', 'o', 'w')); // helper constants and methods
        //
        private static ArrayList<Character> getResourcesFromBoardState(String boardState) {
            ArrayList <Character> resources = new ArrayList<>();
            for (Character c : boardState.toCharArray()) {
                if (possibleResources.contains(c)) { // might be less than 6 resources so we might cut into a players gamestate, this filters that out.
                    resources.add(c);
                }
            }
            return resources;
        }

        private static ArrayList<Character> StringToCharacterArrayList(String s) {
            ArrayList<Character> l = new ArrayList<>();
            for (Character c : s.toCharArray()) {
                l.add(c);
            }
            return l;
        }

        private static String getPlayerBoardState(String boardState) {
            HashMap<Character, String> switchPlayers = new HashMap<>(){{
                put('W',"X");
                put('X',"W");
            }};
            Character playerTurn = boardState.charAt(0);
            int startOfPlayerBoardState = boardState.indexOf(Character.toString(playerTurn), 1);
            int endOfPlayerBoardState = boardState.indexOf(switchPlayers.get(playerTurn), startOfPlayerBoardState);
            List<Character> playerBoardState = StringToCharacterArrayList(boardState);
            return playerBoardState.subList(startOfPlayerBoardState+1, endOfPlayerBoardState).toString();
        }
        // #######################################################################################
        public static boolean validateBuild(String boardState, String action) { // TODO
            ArrayList<Character> validformat = new ArrayList<>(Arrays.asList(
                    'b',
                    'u',
                    'i',
                    'l',
                    'd',
                    'K',
                    'R',
                    'C',
                    'S'
            ));
            for (int i = 0; i < 10; i++) {
                validformat.add(Integer.toString(i).charAt(0));
            }
            for (Character c : action.toCharArray()) {
                if (!validformat.contains(c)) {
                    return false; // Check the format
                }
            }
            // ###########
            Character buildtype = action.charAt(5);
            if (sufficentResourcesForBuild(boardState, buildtype)) { // check for resources
                return switch (buildtype) {
                    case 'C' -> validateCastleBuild(boardState, action.charAt(action.length() - 1));
                    case 'K' -> validateKnightBuild(boardState, action);
                    case 'R' -> validateRoadBuild(boardState, action);
                    case 'S' -> validateSettlementBuild(boardState, action);
                    default -> false;
                };
            }
            return false;
        }
        private static boolean validateCastleBuild(String boardState, Character castlePosition) {
            return !checkIfExists(boardState, "C" + castlePosition); // check existence
        }
        private static boolean validateKnightBuild(String boardState, String action) { // These are harder because we have to check if they're connected to the player.
            String build = new char[]{action.charAt(5), action.charAt(6), action.charAt(7)}.toString();
            if (!checkIfExists(boardState, build)) {

            }
            return false;
        }
        private static boolean validateRoadBuild(String boardState, String action) {
            return false;
        }
        private static boolean validateSettlementBuild(String boardState, String action) {
            return false;
        }
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
            }};
            // ###############
            ArrayList<Resource> resources = new ArrayList<>();
            for (Character c : getResourcesFromBoardState(boardState)) {
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
        private static boolean checkIfExists(String boardState, String build) {
            if (boardState.contains(build)) {
                return true;
            }
            return false;
        }
        private static boolean checkIfConnected(String boardState, String action) { // TODO
            return false;
        }
        // #######################################################################################
        public static boolean validateTrade(String boardState, String action) {
            ArrayList<Character> validFormat = new ArrayList<>(Arrays.asList('t', 'r', 'a', 'd', 'e'));
            validFormat.addAll(possibleResources); // checks for format
            for (Character c : action.toCharArray()) { // can't trade for gold.
                if (c == 'm' || !validFormat.contains(c)) {
                    return false;
                }
            }
            int numResources = action.length() - 4; // the -4 is for "keep"
            ArrayList<Character> resources = getResourcesFromBoardState(boardState);
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
        public final static boolean validateKeep(String boardState, String action) {
            int numRolls = Integer.parseInt(Character.toString(boardState.toCharArray()[2]));
            if (numRolls < 3) {
                ArrayList<Character> turnBoardState = new ArrayList<>(Arrays.asList('k', 'e', 'e', 'p')); // resources are valid, next check boardstate if the resources exist
                turnBoardState.addAll(getResourcesFromBoardState(boardState));
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
            final Character[] coordinateToResource = new Character[]{
                    'w',
                    'g',
                    'o',
                    'o',
                    'b',
                    'l',
                    'w',
                    'g',
                    'l',
                    'b',
                    'g',
                    'w',
                    'b',
                    'l',
                    'o',
                    'o',
                    'g',
                    'w'
            };
            ArrayList<Character> resources = getResourcesFromBoardState(boardState);
            Character resourceIn = action.charAt(4);
            Character resourceOut = action.charAt(5);
            if (!resources.contains(resourceIn)) {
                return false; // Player doesn't have resource to trade.
            }
            String playerBoardState = getPlayerBoardState(boardState);
            if (playerBoardState == "[]") {
                return false; // Player has no builds
            }
            int currentIndex = 0;
            ArrayList<Character> boardStateChars = StringToCharacterArrayList(boardState);
            ArrayList<Integer> locations = new ArrayList<>();
            while (playerBoardState.indexOf(currentIndex) != -1) {
                int newIndex = playerBoardState.indexOf("J", currentIndex);
                locations.add(Integer.parseInt(Character.toString(playerBoardState.charAt(newIndex+1)) + Character.toString(playerBoardState.charAt(newIndex+2))));
                currentIndex = newIndex+1;
            }
            if (locations.size() == 0) {
                return false; // Player has no usable knights
            }
            for (int location : locations) {
                if (coordinateToResource[location] == resourceOut) {
                    return true; // correct knight owned
                }
                else if (location == 9 || location == 10) {
                    return true; // multipurpose knight owned
                }
            }
            return false;
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
        // FIXME: Task 8a
        return null;
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
        // FIXME: Task 8b
        return null;
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
