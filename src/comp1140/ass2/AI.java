package comp1140.ass2;

public class AI {
    /**
     * The AI written for this assignment uses the minimax algorithm to compete against the opponent.
     * Authored by Manindra de Mel, u7156805.
     */
    private static class Minimax {
        /**
         * bestMove is used to keep track of the bestMove throughout the minimax recursive calls.
         */
        public String[] bestMove;

        /**
         * The minimax algorithm
         * @param boardState takes a boardstate
         * @param depth how many future boardstates to look into
         * @param AIturn if it's the AI's turn
         * @return the max/min child score depending on who's turn it is and other factors
         * Authored by Manindra de Mel, u7156805.
         */
        private int run(String boardState, int depth, int originalDepth, int alpha, int beta, boolean AIturn) {
            if (CatanDiceExtra.isGameOver(boardState)) {
                if (AIturn) {
                    return 10000;
                }
                return -10000;
            }
            else if (depth == 0 ) {
                return heuristic(boardState);
            }
            String[][] actionsSequences = CatanDiceExtra.generateAllPossibleActionSequences(boardState);
            if (AIturn) {
                int max = -1;
                for (String[] actionSequence : actionsSequences) {
                    String newBoardState = CatanDiceExtra.applyActionSequence(boardState, actionSequence);
                    int childScore = run(newBoardState, depth - 1, originalDepth, alpha, beta, false); // Will have to be changed when we have more than 2 players
                    if (childScore > max) {
                        max = childScore;
                        if (depth == originalDepth) {
                            this.bestMove = actionSequence;
                        }
                    }
                    alpha = Math.max(alpha, childScore);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return max;
            }
            else {
                int min = 1000;
                for (String[] actionSequence : actionsSequences) {
                    String newBoardState = CatanDiceExtra.applyActionSequence(boardState, actionSequence);
                    int childScore = run(newBoardState, depth - 1, originalDepth, alpha, beta, true);
                    if (childScore < min) {
                        min = childScore;
                    }
                    beta = Math.min(beta, childScore);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return min;
            }
        }

        /**
         * A way to evaluate a boardState is to simply look at the score of the AI.
         * @param boardState
         * @return the score of the AI/player's turn
         * Authored by Manindra de Mel, u7156805.
         */
        private static int heuristic(String boardState) {
            String score = Board.getScoreFromBoardState(boardState);
            char playerID = boardState.charAt(0);
            return Integer.parseInt(score.substring(score.indexOf(playerID) + 1, score.indexOf(playerID) + 3));
        }
    }

    /**
     * Runs the minimax algorithm at a depth of one to emulate a basic AI or the 'greedy' algorithm
     * @param boardState
     * @return the AI's chosen actionSequence
     * Authored By Manindra de Mel, u7156805
     */
    public static String[] basicAI(String boardState) {
        return runAI(boardState, 1);
    }

    /**
     * Runs minimax at a higher depth for a more advanced AI
     * @param boardState
     * @return the AI's chosen actionSequence
     * Authored By Manindra de Mel, u7156805
     */
    public static String[] advancedAI(String boardState) {
        return runAI(boardState, 5);
    }
    /**
     * A helper method for the advanced and basic AI, which just runs mini-max at a specified depth
     * @param boardState
     * @param depth
     * @return
     * Authored By Manindra de Mel, u7156805
     */
    private static String[] runAI(String boardState, int depth) {
        Minimax m = new Minimax();
        m.run(boardState, depth, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        return m.bestMove;
    }
}
