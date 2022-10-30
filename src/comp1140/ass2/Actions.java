package comp1140.ass2;

import java.util.ArrayList;
import java.util.Collections;

import static comp1140.ass2.Board.getTurnFromBoardState;

public class Actions {

    static String[] roads = new String[]{
            "0307", "0003", "0004", "0408", "0812", "0712",
            "0104", "0105", "0509", "0913", "0813",
            "0205", "0206", "0610", "1014", "0914",
            "1116", "0711", "1217", "1722", "1622",
            "1318", "1823", "1723",
            "1419", "1924", "1824",
            "1015", "1520", "2025", "1925",
            "2127", "1621", "2228", "2833", "2733",
            "2329", "2934", "2834",
            "2430", "3035", "2935",
            "2531", "3136", "3036",
            "2026", "2632", "3237", "3137",
            "3338", "3439", "3943", "3843",
            "3540", "4044", "3944",
            "3641", "4145", "4045",
            "3742", "4246", "4146",
            "4347", "4448", "4851", "4751",
            "4549", "4952", "4852",
            "4650", "5053", "4953"
    };

    /**
     * Checks the game phase based on board state
     *
     * @param boardState: string representation of the board state.
     * @return 0->start phase, 1->roll phase, 2->build phase
     */
    public static int getGamePhase(String boardState){
        if (boardState.charAt(1)=='0')
            return 0;
        else if (boardState.charAt(1)=='1'||boardState.charAt(1)=='2')
            return 1;
        else
            return 2;
    }

    public static String[][] generateAllPossibleStartGameActionSequences(String boardState) {
        ArrayList<String[]> actionPossible = new ArrayList<>();

        for (var road : roads) {
            String action = "buil" + "R" + road;

            if (CatanDiceExtra.isActionValid(boardState, action))
                actionPossible.add(new String[]{action});
        }

        return (String[][]) actionPossible.toArray();
    }

    public static String[][] generateAllPossibleRollPhaseActionSequences(String boardState) {

        ArrayList<String[]> acc = new ArrayList<String[]>();
        String turn = getTurnFromBoardState(boardState);
        String resources = turn.substring(2);
        for (int x = 0; x < (2 ^ (resources.length())); x++) {

            String binary = Integer.toBinaryString(x);
            String action = "keep";

            for (int i = 0; i < binary.length(); i++) {
                if (binary.charAt(binary.length() - i) == '1') {
                    action += resources.charAt(i);
                }
            }
            String[] keepArray = new String[1];
            keepArray[0] = action;
            acc.add(keepArray);
        }

        return (String[][]) acc.toArray();
    }

    public static String[][] generateAllPossibleBuildPhaseActionSequences(String boardState) {
        // TODO
        return null;
    }
}
