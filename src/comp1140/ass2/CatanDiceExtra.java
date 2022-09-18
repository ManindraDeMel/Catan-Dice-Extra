package comp1140.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static comp1140.ass2.Coordinate.CheckAdjacent;

public class CatanDiceExtra {
    ArrayList<Player> players = new ArrayList<>();

    public Board board;
    ArrayList<String> playersNames = new ArrayList<>(Arrays.asList("Manindra", "Stephen", "Arjun")); // changes when we add GUI stuff (max of 6 players?)
    public void startGame() {
        for (String name : playersNames) {
            players.add(new Player(name));
        }
    }

    public void newTurn(Player player) {

    }


    public static Boolean isPlayerBoardStateWellFormed(String pBoardState) {
        int x;
        Set<Character> ints = Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
        for (x = 0; x < pBoardState.length(); x++) {
            if ((ints.contains(pBoardState.charAt(x)) == false) && (pBoardState.charAt(x) != 'C')) {
                break;
            }
        }
        if (x != 0) {
            if (x%2!=0) {
                return false;
            } else {
                String[] castles = new String[x/2];
                for (int y=2; y<=x; y+=2) {
                    castles[(y/2)-1] = pBoardState.substring(y-2, y);
                }
                String[] castlesSorted = castles;
                Arrays.sort(castlesSorted);
                if (castles.equals(castlesSorted)==false) {
                    return false;
                }
                for (int z=0; z<castles.length; z++) {
                    if ( ((castles[z].charAt(0)=='C')&&(ints.contains(castles[z].charAt(1))))==false){
                        return false;
                    }
                    int index = Integer.valueOf(castles[z].substring(1,2));
                    if (index>3) {
                        return false;
                    }
                }
            }
        }
        String pBoardStateNoC = pBoardState.substring(x);
        for (x = 0; x < pBoardStateNoC.length(); x++) {
            if ((ints.contains(pBoardStateNoC.charAt(x)) == false) && (pBoardStateNoC.charAt(x) != 'J')) {
                break;
            }
        }

        if (x != 0) {
            if (x%3!=0) {
                return false;
            } else {
                String[] jKnights = new String[x/3];
                for (int y=3; y<=x; y+=3) {
                    jKnights[(y/3)-1] = pBoardStateNoC.substring(y-3, y);
                }
                String[] jKnightsSorted = jKnights;
                Arrays.sort(jKnightsSorted);
                if (jKnights.equals(jKnightsSorted)==false) {
                    return false;
                }
                for (int z=0; z<jKnights.length; z++) {
                    if (jKnights[z].charAt(0)!='J') {
                        return false;
                    }
                    for (int n=1; n<=2; n++) {
                        if (ints.contains(jKnights[z].charAt(1))==false) {
                            return false;
                        }
                    }
                    int index = Integer.valueOf(jKnights[z].substring(1,3));
                    if (index>19) {
                        return false;
                    }
                }
            }
        }
        String pBoardStateNoCJ = pBoardStateNoC.substring(x);
        for (x = 0; x < pBoardStateNoCJ.length(); x++) {
            if ((ints.contains(pBoardStateNoCJ.charAt(x)) == false) && (pBoardStateNoCJ.charAt(x) != 'K')) {
                break;
            }
        }
        if (x != 0) {
            if (x%3!=0) {
                return false;
            } else {
                String[] kKnights = new String[x/3];
                for (int y=3; y<=x; y+=3) {
                    kKnights[(y/3)-1] = pBoardStateNoCJ.substring(y-3, y);
                }
                String[] kKnightsSorted = kKnights;
                Arrays.sort(kKnightsSorted);
                if (kKnights.equals(kKnightsSorted)==false) {
                    return false;
                }
                for (int z=0; z<kKnights.length; z++) {
                    if (kKnights[z].charAt(0)!='K') {
                        return false;
                    }
                    for (int n=1; n<=2; n++) {
                        if (ints.contains(kKnights[z].charAt(1))==false) {
                            return false;
                        }
                    }
                    int index = Integer.valueOf(kKnights[z].substring(1,3));
                    if (index>19) {
                        return false;
                    }
                }
            }
        }
        String pBoardStateNoCJK = pBoardStateNoCJ.substring(x);
        for (x = 0; x < pBoardStateNoCJK.length(); x++) {
            if ((ints.contains(pBoardStateNoCJK.charAt(x)) == false) && (pBoardStateNoCJK.charAt(x) != 'R')) {
                break;
            }
        }
        if (x != 0) {
            if (x%5!=0) {
                return false;
            } else {
                String[] roads = new String[x/5];
                for (int y=5; y<=x; y+=5) {
                    roads[(y/5)-1] = pBoardStateNoCJK.substring(y-5, y);
                }
                String[] roadsSorted = roads;
                Arrays.sort(roadsSorted);
                if (roads.equals(roadsSorted)==false) {
                    return false;
                }
                for (int z=0; z<roads.length; z++) {
                    if (roads[z].charAt(0)!='R') {
                        return false;
                    }
                    for (int n=1; n<=4; n++) {
                        if (ints.contains(roads[z].charAt(1))==false) {
                            return false;
                        }
                    }
                    int index1 = Integer.valueOf(roads[z].substring(1,3));
                    int index2 = Integer.valueOf(roads[z].substring(3,5));
                    if (index1>=index2||index1>53||index2>53) {
                        return false;
                    }
                }
            }
        }
        String pBoardStateNoCJKR = pBoardStateNoCJK.substring(x);
        for (x = 0; x < pBoardStateNoCJKR.length(); x++) {
            if ((ints.contains(pBoardStateNoCJKR.charAt(x)) == false) && (pBoardStateNoCJKR.charAt(x) != 'S')) {
                break;
            }
        }
        if (x != 0) {
            if (x%3!=0) {
                return false;
            } else {
                String[] settle = new String[x/3];
                for (int y=3; y<=x; y+=3) {
                    settle[(y/3)-1] = pBoardStateNoCJKR.substring(y-3, y);
                }
                String[] settleSorted = settle;
                Arrays.sort(settleSorted);
                if (settle.equals(settleSorted)==false) {
                    return false;
                }
                for (int z=0; z<settle.length; z++) {
                    if (settle[z].charAt(0)!='S') {
                        return false;
                    }
                    for (int n=1; n<=2; n++) {
                        if (ints.contains(settle[z].charAt(1))==false) {
                            return false;
                        }
                    }
                    int index = Integer.valueOf(settle[z].substring(1,3));
                    if (index>53) {
                        return false;
                    }
                }
            }
        }
        String pBoardStateNoCJKRS = pBoardStateNoCJKR.substring(x);
        for (x = 0; x < pBoardStateNoCJKRS.length(); x++) {
            if ((ints.contains(pBoardStateNoCJKRS.charAt(x)) == false) && (pBoardStateNoCJKRS.charAt(x) != 'T')) {
                break;
            }
        }
        if (x != 0) {
            if (x%3!=0) {
                return false;
            } else {
                String[] city = new String[x / 3];
                for (int y = 3; y <= x; y += 3) {
                    city[(y / 3) - 1] = pBoardStateNoCJKRS.substring(y - 3, y);
                }
                String[] citySorted = city;
                Arrays.sort(citySorted);
                if (city.equals(citySorted) == false) {
                    return false;
                }
                for (int z = 0; z < city.length; z++) {
                    if (city[z].charAt(0) != 'T') {
                        return false;
                    }
                    for (int n = 1; n <= 2; n++) {
                        if (ints.contains(city[z].charAt(1)) == false) {
                            return false;
                        }
                    }
                    int index = Integer.valueOf(city[z].substring(1,3));
                    if (index>53) {
                        return false;
                    }
                }
            }
        }
        if (pBoardStateNoCJKRS.length()!=x) {
            return false;
        }
        return true;
    }
    public static boolean isPlayerScoreWellFormed(String pScore) {
        Set<Character> ints = Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
        int l = pScore.length();
        if (l >= 2) {
            if (ints.contains(pScore.charAt(0))&&ints.contains(pScore.charAt(1))) {
                if (l==2) {
                    return true;
                } else if (l == 3) {
                    if (pScore.charAt(2)=='R'||pScore.charAt(2)=='A') {
                        return true;
                    }
                } else if (l == 4) {
                    if (pScore.charAt(2)=='R'&&pScore.charAt(3)=='A') {
                        return true;
                    }
                }

            }
        }
        return false;
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
        Set<Character> resources = Set.of('b', 'g', 'l', 'm', 'o', 'w');
        Board board = new Board();
        board.instatiateBoard();
        Coordinate coord1 = board.coords[03];
        Coordinate coord2 = board.coords[0];
        //coord1 = new Coordinate(0, 3);
        //coord2 = new Coordinate(1, 3);
        if (board.coords[33].y==0) {
            return true;
        }
        if (CheckAdjacent(coord1, coord2)==false) {
            return true;
        }
        if (boardState.charAt(0) != 'W' && boardState.charAt(0) != 'X') {
            return false;
        }
        if (boardState.charAt(1)!='0'||boardState.charAt(2) != '0') {
            if (boardState.charAt(1) != '3' && boardState.charAt(1) != '4' && boardState.charAt(1) != '5' && boardState.charAt(1) != '6') {
                return false;
            }
            if (boardState.charAt(2) != '1' && boardState.charAt(2) != '2' && boardState.charAt(2) != '3') {
                return false;
            }
        }
        String boardStateNoDice = boardState.substring(3);
        int x;
        for (x = 0; x < boardStateNoDice.length(); x++) {
            if (resources.contains(boardStateNoDice.charAt(x)) == false) {
                break;
            }
        }
        if (x>6) {
            return false;
        }
        if (x>0) {
            String resourceString = (boardStateNoDice.substring(0,x));
            char[] resourcesSort = resourceString.toCharArray();
            Arrays.sort(resourcesSort);
            String resourcesSortS = new String(resourcesSort);
            if (resourceString.equals(resourcesSortS) == false) {
                return false;
            }
        }
        if (boardStateNoDice.charAt(x)!='W') {
            return false;
        }
        if (boardStateNoDice.length()<=x) {
            return false;
        }
        String boardStateNoTurn = boardStateNoDice.substring(x+1);
        for (x=0;x<boardStateNoTurn.length();x++) {
            if (boardStateNoTurn.charAt(x)=='X') {
                break;
            }
        }
        if (isPlayerBoardStateWellFormed(boardStateNoTurn.substring(0,x))==false) {
            return false;
        }
        if (boardStateNoTurn.length()<=x) {
            return false;
        }
        String boardStateNoWS = boardStateNoTurn.substring(x+1);
        for (x=0;x<boardStateNoWS.length();x++) {
            if (boardStateNoWS.charAt(x)=='W') {
                break;
            }
        }
        if (isPlayerBoardStateWellFormed(boardStateNoWS.substring(0,x))==false) {
            return false;
        }
        if (boardStateNoWS.length()<=x) {
            return false;
        }
        String boardStateNoXS = boardStateNoWS.substring(x+1);
        for (x=0;x<boardStateNoXS.length();x++) {
            if (boardStateNoXS.charAt(x)=='X') {
                break;
            }
        }
        if (isPlayerScoreWellFormed(boardStateNoXS.substring(0,x))==false) {
            return false;
        }
        if (boardStateNoXS.length()<=x) {
            return false;
        }
        String boardStateNoWScore = boardStateNoXS.substring(x+1);
        if (isPlayerScoreWellFormed(boardStateNoWScore)==false) {
            return false;
        }
        // FIXME: Task 3
        return true;
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
        Set<Character> resources = Set.of('b', 'g', 'l', 'm', 'o', 'w');
        Set<Character> ints = Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
        int x=0;
        if (action.startsWith("keep")) {
            for (x=4;x<action.length();x++) {
                if (resources.contains(action.charAt(x))==false) {
                    return false;
                }
            }
            if (x>0) {
                String resourceString = (action.substring(4));
                char[] resourcesSort = resourceString.toCharArray();
                Arrays.sort(resourcesSort);
                String resourcesSortS = new String(resourcesSort);
                if (resourceString.equals(resourcesSortS)) {
                    return true;
                }
            }
        } else if (action.startsWith("trade")) {
            for (x=5;x<action.length();x++) {
                if ((resources.contains(action.charAt(x))==false)||(action.charAt(x)=='m')) {
                    return false;
                }
            }
            if (x>0) {
                String resourceString = (action.substring(5));
                char[] resourcesSort = resourceString.toCharArray();
                Arrays.sort(resourcesSort);
                String resourcesSortS = new String(resourcesSort);
                if (resourceString.equals(resourcesSortS)) {
                    return true;
                }
            }
        } else if (action.startsWith("swap")) {
            if (action.length()==6) {
                if (resources.contains(action.charAt(4))&&resources.contains(action.charAt(5))) {
                    return true;
                }
            }
        } else if (action.startsWith("build")&&action.length()>6) {
            if (action.charAt(5)=='C'&&action.length()==7) {
                if (ints.contains(action.charAt(6))) {
                    int index = Integer.valueOf(action.substring(6));
                    if (index<4) {
                        return true;
                    }
                }
            } else if (action.charAt(5)=='K'&&action.length()==8) {
                if (ints.contains(action.charAt(6))&&ints.contains(action.charAt(7))) {
                    int index = Integer.valueOf(action.substring(6));
                    if (index<20) {
                        return true;
                    }
                }
            } else if (action.charAt(5)=='S'&&action.length()==8) {
                if (ints.contains(action.charAt(6))&&ints.contains(action.charAt(7))) {
                    int index = Integer.valueOf(action.substring(6));
                    if (index<54) {
                        return true;
                    }
                }
            } else if (action.charAt(5)=='T'&&action.length()==8) {
                if (ints.contains(action.charAt(6))&&ints.contains(action.charAt(7))) {
                    int index = Integer.valueOf(action.substring(6));
                    if (index<54) {
                        return true;
                    }
                }
            } else if (action.charAt(5)=='R'&&action.length()==10) {
                for (x=6;x<10;x++) {
                    if (ints.contains(action.charAt(x))==false) {
                        return false;
                    }
                }
                int index1= Integer.valueOf(action.substring(6,8));
                int index2= Integer.valueOf(action.substring(8,10));
                if (index1<54&&index2<54&&index1<index2) {
                    return true;
                }

            }

        }
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
        // FIXME: Task 5
        return "";
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
        // FIXME: Task 7
        return false;
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
