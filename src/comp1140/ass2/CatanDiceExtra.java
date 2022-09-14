package comp1140.ass2;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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

//        // Getting strings of different sections
//        String turn = boardState.substring(0, boardState.indexOf('W', 1));
//        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
//        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));
//        String rest = boardState.substring(boardState.indexOf('W', boardState.indexOf('X', 1)));
//
//
////        System.out.println(turn);
////        System.out.println(playerW);
////        System.out.println(playerX);
////        System.out.println(rest);
//
//        // Array to store the longest roads of each player
//        int[] longRoadArr = new int[2];
//
//
//        int countW = (int) playerW.chars().filter(ch -> ch == 'R').count();
//
//        String roadsW = "";
//
//        if (countW != 0) {
//            roadsW = playerW.substring(playerW.indexOf('R'), playerW.indexOf('R') + countW * 5);
//            longRoadArr[0] += 1;
//        }
//
//        for (int i = 0; i < countW - 1; i++) {
//            if (roadsW.substring(3 + i*5, 5 + i*5).equals(roadsW.substring(3 + (i+0)*5 + 3, 5 + (i+0)*5 + 3)))
//                longRoadArr[0] += 1;
////            System.out.println(roads);
////            else
////                System.out.println(roadsW.substring(3 + i * 5, 5 + i * 5) + " " + roadsW.substring(3 + (i + 1) * 5, 5 + (i + 1) * 5));
////                System.out.println(roadsW);
//        }
//
////        longRoadArr[0] = count;
//
//        int count = (int) playerX.chars().filter(ch -> ch == 'R').count();
////        longRoadArr[1] = (int) playerX.chars().filter(ch -> ch == 'R').count();
//        String roads = "";
//
//        if (count != 0) {
//            roads = playerX.substring(playerX.indexOf('R'), playerX.indexOf('R') + count * 5);
//            longRoadArr[1] += 1;
//        }
//
//        for (int i = 0; i < count - 1; i++) {
//            if (roads.substring(3 + i * 5, 5 + i * 5).equals(roads.substring(3 + (i + 0) * 5 + 3, 5 + (i + 0) * 5 + 3)))
////                System.out.println(roads.substring(3 + i * 5, 5 + i * 5) + " " + roads.substring(3 + (i + 1) * 5, 5 + (i + 1) * 5));
//                longRoadArr[1] += 1;
////            else
////                System.out.println(roads.substring(3 + i * 5, 5 + i * 5) + " " + roads.substring(3 + (i + 1) * 5, 5 + (i + 1) * 5));
////                System.out.println(roads);
//
//        }
//
//        System.out.println();
//        System.out.println("----------");
//        System.out.println(Arrays.toString(longRoadArr));
//        System.out.println("W: " + countW + " - " + playerW + " Roads: " + roadsW);
//        System.out.println("X: " + count + " - " + playerX + " Roads: " + roads);
//
//        for (int i = 0; i < countW - 1; i++) {
////            if (roadsW.substring(3 + i*5, 5 + i*5).equals(roadsW.substring(3 + (i+1)*5, 5 + (i+1)*5)))
////                longRoadArr[0] += 1;
//////            System.out.println(roads);
////            else
//            System.out.println(roadsW.substring(3 + i * 5, 5 + i * 5) + " " + roadsW.substring(3 + (i + 0) * 5 + 3, 5 + (i + 0) * 5 + 3));
////                System.out.println(roadsW);
//        }
//            System.out.println("-----------");
////        System.out.println();
//
//
//
////        System.out.println(Arrays.toString(longRoadArr));
////        System.out.println(playerW);
////        System.out.println(playerX);
//
//        return longRoadArr;
        //###############################################################################################

        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));


        // Array to store the longest roads of each player
        int[] longRoadArr = new int[2];

        int countW = (int) playerW.chars().filter(ch -> ch == 'R').count();
        String roadsW = "";
        if (countW != 0)
            roadsW = playerW.substring(playerW.indexOf('R'), playerW.indexOf('R') + countW * 5);
        LongestPathUndirectedTree.Graph graphW = new LongestPathUndirectedTree.Graph(54);

        for (int i = 0; i < countW; i++) {
            graphW.addEdge(Integer.parseInt(roadsW.substring(1 + i * 5, 3 + i * 5)), Integer.parseInt(roadsW.substring(3 + i*5, 5 + i*5)));
        }



        int countX = (int) playerX.chars().filter(ch -> ch == 'R').count();
        String roadsX = "";
        if (countX != 0)
            roadsX = playerX.substring(playerX.indexOf('R'), playerX.indexOf('R') + countX * 5);
        LongestPathUndirectedTree.Graph graphX = new LongestPathUndirectedTree.Graph(54);

        for (int i = 0; i < countX; i++) {
//            System.out.println(roadsX);
//            System.out.println(roadsX.substring(1 + i*4, 3 + i*5) + " " + roadsX.substring(3 + i*5, 5 + i*5));
            graphX.addEdge(Integer.parseInt(roadsX.substring(1 + i*5, 3 + i*5)), Integer.parseInt(roadsX.substring(3 + i*5, 5 + i*5)));
        }


        System.out.println();
        System.out.println("----------");

        if (countW != 0){
            longRoadArr[0] = graphW.longestPathLength(0) + 1;
        }
        else {
            longRoadArr[0] = graphW.longestPathLength(0);
        }

        if (countX != 0){
            longRoadArr[1] = graphX.longestPathLength(0) + 1;
        }
        else {
            longRoadArr[1] = graphX.longestPathLength(0);
        }

        int[][] arr = new int[2][54];
        for (int i = 0; i < 54; i++){
            arr[0][i] = graphW.longestPathLength(i);
            arr[1][i] = graphX.longestPathLength(i);
        }
        Arrays.sort(arr[0]);
        Arrays.sort(arr[1]);

        longRoadArr[0] = arr[0][53];
        longRoadArr[1] = arr[1][53];

//        longRoadArr[0] = graphW.longestPathLength() + 1;
//        longRoadArr[1] = graphX.longestPathLength() + 1;

//        longRoadArr[0] = graphW.printGraph2();
//        longRoadArr[1] = graphX.printGraph2();


        System.out.println(Arrays.toString(longRoadArr));
        System.out.println("W: " + countW + " - " + playerW + " Roads: " + roadsW);
        System.out.println("X: " + countX + " - " + playerX + " Roads: " + roadsX);

//        for (int i = 0; i < countW - 1; i++) {
////            if (roadsW.substring(3 + i*5, 5 + i*5).equals(roadsW.substring(3 + (i+1)*5, 5 + (i+1)*5)))
////                longRoadArr[0] += 1;
//////            System.out.println(roads);
////            else
//            System.out.println(roadsW.substring(3 + i * 5, 5 + i * 5) + " " + roadsW.substring(3 + (i + 0) * 5 + 3, 5 + (i + 0) * 5 + 3));
////                System.out.println(roadsW);
//        }
            System.out.println("-----------");

            graphW.printGraph();
        return longRoadArr;
    }

    // Java program to find the longest path of the tree
    class LongestPathUndirectedTree {

        // Utility Pair class for storing maximum distance
        // Node with its distance
        static class Pair<T,V> {
            T first; // maximum distance Node
            V second; // distance of maximum distance node

            //Constructor
            Pair(T first, V second) {
                this.first = first;
                this.second = second;
            }
        }

        // This class represents an undirected graph using adjacency list
        static class Graph {
            int V; // No. of vertices
            LinkedList<Integer>[] adj; //Adjacency List

            // Constructor
            Graph(int V) {
                this.V = V;
                // Initializing Adjacency List
                adj = new LinkedList[V];
                for(int i = 0; i < V; ++i) {
                    adj[i] = new LinkedList<Integer>();
                }
            }

            void printGraph(){
                for (int i = 0; i < V; i++){

                    System.out.print("Node" + i);
                    for (int x : adj[i]){
                        System.out.print(" -> " + x);
                    }
                    System.out.println();
                }
            }

            int printGraph2(){

                int[] arr = new int[V];

                for (int i = 0; i < V; i++){
                    System.out.print("Node" + i);
                    for (int x : adj[i]){
                        System.out.print(" -> " + x);
                    }
                    arr[i] = adj[i].size();
                    System.out.println();
                }
                Arrays.sort(arr);
                return arr[53];
            }

            // function to add an edge to graph
            void addEdge(int s, int d) {
                adj[s].add(d); // Add d to s's list.
                adj[d].add(s); // Since the graph is undirected
            }


            // method returns farthest node and its distance from node u
            Pair<Integer, Integer> bfs(int u) {
                int[] dis = new int[V];

                // mark all distance with -1
                Arrays.fill(dis, -1);

                Queue<Integer> q = new LinkedList<>();

                q.add(u);

                // distance of u from u will be 0
                dis[u] = 0;
                while (!q.isEmpty()) {
                    int t = q.poll();

                    // loop for all adjacent nodes of node-t
                    for(int i = 0; i < adj[t].size(); ++i) {
                        int v = adj[t].get(i);

                        // push node into queue only if
                        // it is not visited already
                        if(dis[v] == -1) {
                            q.add(v);
                            // make distance of v, one more
                            // than distance of t
                            dis[v] = dis[t] + 1;
                        }
                    }
                }

                int maxDis = 0;
                int nodeIdx = 0;

                // get farthest node distance and its index
                for(int i = 0; i < V; ++i) {
                    if(dis[i] > maxDis) {
                        maxDis = dis[i];
                        nodeIdx = i;
                    }
                }

                return new Pair<Integer, Integer>(nodeIdx, maxDis);
            }

            // method prints longest path of given tree
            int longestPathLength(int i) {
                Pair<Integer, Integer> t1, t2;

                // first bfs to find one end point of
                // the longest path
                t1 = bfs(i);

                // second bfs to find actual longest path
                t2 = bfs(t1.first);

                System.out.println("Longest path is from "+ t1.first
                        + " to "+ t2.first +" of length "+t2.second);
                return t2.second;
            }
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
