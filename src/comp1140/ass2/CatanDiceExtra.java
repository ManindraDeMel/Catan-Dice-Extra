package comp1140.ass2;

import org.w3c.dom.Node;

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
        int[] longArr = new int[2];

        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));

        // n is number of cities
        int n = 54*2;

        Vector<Vector<GFG.pair>> graph = new Vector<Vector<GFG.pair>>();
        for(int i = 0; i < n + 1; i++)
        {
            graph.add(new Vector<GFG.pair>());
        }

        int countW = (int) playerW.chars().filter(ch -> ch == 'R').count();
        String roadsW = "";
        if (countW != 0)
            roadsW = playerW.substring(playerW.indexOf('R'), playerW.indexOf('R') + countW * 5);

        System.out.println("--");
        for (int i = 0; i < countW; i++) {
            int one = Integer.parseInt(roadsW.substring(1 + i * 5, 3 + i * 5));
            int two = Integer.parseInt(roadsW.substring(3 + i*5, 5 + i*5));

            System.out.println(one + " " + two);

            graph.get(one).add(new GFG.pair(two, 1));
            graph.get(two).add(new GFG.pair(one, 1));
        }
        System.out.println("--");

        longArr[0] = GFG.longestCable(graph, n);

        ///////////////////////////
        Vector<Vector<GFG.pair>> graphX = new Vector<Vector<GFG.pair>>();
        for(int i = 0; i < n + 1; i++)
        {
            graphX.add(new Vector<GFG.pair>());
        }

        int countX = (int) playerX.chars().filter(ch -> ch == 'R').count();
        String roadsX = "";
        if (countX != 0)
            roadsX = playerX.substring(playerX.indexOf('R'), playerX.indexOf('R') + countX * 5);

        System.out.println("--");
        for (int i = 0; i < countX; i++) {
            int one = Integer.parseInt(roadsX.substring(1 + i*5, 3 + i*5));
            int two = Integer.parseInt(roadsX.substring(3 + i*5, 5 + i*5));

            System.out.println(one + " " + two);

            graphX.get(one).add(new GFG.pair(two, 1));
            graphX.get(two).add(new GFG.pair(one, 1));
        }
        System.out.println("--");

        longArr[1] = GFG.longestCable(graphX, n);

        // create undirected graph
        // first edge
//        graph.get(1).add(new GFG.pair(2, 3));
//        graph.get(2).add(new GFG.pair(1, 3));
//
//        // second edge
//        graph.get(2).add(new GFG.pair(3, 4));
//        graph.get(3).add(new GFG.pair(2, 4));
//
//        // third edge
//        graph.get(2).add(new GFG.pair(6, 2));
//        graph.get(6).add(new GFG.pair(2, 2));
//
//        // fourth edge
//        graph.get(4).add(new GFG.pair(6, 6));
//        graph.get(6).add(new GFG.pair(4, 6));
//
//        // fifth edge
//        graph.get(5).add(new GFG.pair(6, 5));
//        graph.get(6).add(new GFG.pair(5, 5));
//
//        System.out.print("Maximum length of cable = "
//                + GFG.longestCable(graph, n));

        String s="""
        int countW = (int) playerW.chars().filter(ch -> ch == 'R').count();
        String roadsW = "";
        if (countW != 0)
            roadsW = playerW.substring(playerW.indexOf('R'), playerW.indexOf('R') + countW * 5);
        GFG.Graph gW = new GFG.Graph(54);

        for (int i = 0; i < countW; i++) {
            gW.addEdge(Integer.parseInt(roadsW.substring(1 + i * 5, 3 + i * 5)), Integer.parseInt(roadsW.substring(3 + i*5, 5 + i*5)), 1);
        }


        int countX = (int) playerX.chars().filter(ch -> ch == 'R').count();
        String roadsX = "";
        if (countX != 0)
            roadsX = playerX.substring(playerX.indexOf('R'), playerX.indexOf('R') + countX * 5);
        GFG.Graph gX = new GFG.Graph(54);

        for (int i = 0; i < countX; i++) {
            gX.addEdge(Integer.parseInt(roadsX.substring(1 + i * 5, 3 + i * 5)), Integer.parseInt(roadsX.substring(3 + i * 5, 5 + i * 5)), 1);
        }


        int l1 = 0;
        int l2 = 0;


        for (int i = 0; i < 54; i++) {
            int s = 1;
//            System.out.print("Following are longest distances from source vertex "+ s + " \n" );
            if (l1 < gW.longestPath(i)) {
                l1 = gW.longestPath(i);
            }
            if (l2 < gW.longestPath(i)) {
                l2 = gW.longestPath(i);
            }
        }

        longArr[0] = l1;
        longArr[1] = l2;

        if (countW == 0)
            longArr[0] = 0;
        if (countX == 0)
            longArr[1] = 0;

        """;

//        longArr[0] -= 1;
//        longArr[1] -= 1;

        for (int i = 0; i < 2; i++) {
            if (longArr[0] < 0 || countW == 0)
                longArr[0] = 0;

            if (longArr[1] < 0|| countX == 0)
                longArr[1] = 0;
        }

        System.out.println("------------------------");
        System.out.println(Arrays.toString(longArr));
        System.out.println("W: " + countW + " " + playerW);
        for (int i = 0; i < countW; i++) {
            int one = Integer.parseInt(roadsW.substring(1 + i*5, 3 + i*5));
            int two = Integer.parseInt(roadsW.substring(3 + i*5, 5 + i*5));

            System.out.println(one + " " + two);
        }
        System.out.println("X: " + countX + " " + playerX);
        System.out.println("------------------------");

        return longArr;
    }

    // Java program to find the longest cable length
// between any two cities.

    public class GFG
    {

        // visited[] array to make nodes visited
        // src is starting node for DFS traversal
        // prev_len is sum of cable length till current node
        // max_len is pointer which stores the maximum length
        // of cable value after DFS traversal

        // Class containing left and
        // right child of current
        // node and key value
        static class pair {

            public int x, y;
            public pair(int f, int s)
            {
                x = f;
                y = s;
            }
        }

        // maximum length of cable among the connected
        // cities
        static int max_len = Integer.MIN_VALUE;

        static void DFS(Vector<Vector<pair>> graph, int src,
                        int prev_len, boolean[] visited)
        {

            // Mark the src node visited
            visited[src] = true;

            // curr_len is for length of cable
            // from src city to its adjacent city
            int curr_len = 0;

            // Adjacent is pair type which stores
            // destination city and cable length
            pair adjacent;

            // Traverse all adjacent
            for(int i = 0; i < graph.get(src).size(); i++)
            {
                // Adjacent element
                adjacent = graph.get(src).get(i);

                // If node or city is not visited
                if (!visited[adjacent.x])
                {
                    // Total length of cable from
                    // src city to its adjacent
                    curr_len = prev_len + adjacent.y;

                    // Call DFS for adjacent city
                    DFS(graph, adjacent.x, curr_len, visited);
                }

                // If total cable length till
                // now greater than previous
                // length then update it
                if (max_len < curr_len)
                {
                    max_len = curr_len;
                }

                // make curr_len = 0 for next adjacent
                curr_len = 0;
            }
        }

        // n is number of cities or nodes in graph
        // cable_lines is total cable_lines among the cities
        // or edges in graph
        public static int longestCable(Vector<Vector<pair>> graph, int n)
        {
            // call DFS for each city to find maximum
            // length of cable
            for (int i=1; i<=n; i++)
            {
                // initialize visited array with 0
                boolean[] visited = new boolean[n+1];

                // Call DFS for src vertex i
                DFS(graph, i, 0, visited);
            }

            return max_len;
        }

        public static void main(String[] args) {
            // n is number of cities
            int n = 6;

            Vector<Vector<pair>> graph = new Vector<Vector<pair>>();
            for(int i = 0; i < n + 1; i++)
            {
                graph.add(new Vector<pair>());
            }

            // create undirected graph
            // first edge
            graph.get(1).add(new pair(2, 3));
            graph.get(2).add(new pair(1, 3));

            // second edge
            graph.get(2).add(new pair(3, 4));
            graph.get(3).add(new pair(2, 4));

            // third edge
            graph.get(2).add(new pair(6, 2));
            graph.get(6).add(new pair(2, 2));

            // fourth edge
            graph.get(4).add(new pair(6, 6));
            graph.get(6).add(new pair(4, 6));

            // fifth edge
            graph.get(5).add(new pair(6, 5));
            graph.get(6).add(new pair(5, 5));

            System.out.print("Maximum length of cable = "
                    + longestCable(graph, n));
        }
    }

// This code is contributed by suresh07.





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

        int[] largeArr = new int[2];

        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));

        int countW = (int) playerW.chars().filter(ch -> ch == 'K').count() + (int) playerW.chars().filter(ch -> ch == 'J').count();
        int countX = (int) playerX.chars().filter(ch -> ch == 'K').count() + (int) playerX.chars().filter(ch -> ch == 'J').count();

        largeArr[0] = countW;
        largeArr[1] = countX;

        System.out.println("-------------------------");
        System.out.println(Arrays.toString(largeArr));
        System.out.println("W: " + playerW);
        System.out.println("X: " + playerX);
        System.out.println("-------------------------");
        System.out.println();


//        String roadsW = "";
//        if (countW != 0)
//            roadsW = playerW.substring(playerW.indexOf('R'), playerW.indexOf('R') + countW * 5);

        return largeArr;
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
