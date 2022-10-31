package comp1140.ass2;

import comp1140.ass2.gui.Game;

import java.util.*;
import java.util.stream.Collectors;

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
        ArrayList<ArrayList<String>> possibleBuilds = new ArrayList<>();
        Board board = new Board();
        List<String> roadsList = Arrays.stream(roads).map(r -> "R" + r).collect(Collectors.toList());
        List<String> castleList = Arrays.stream(board.castles).map(Castle::toString).collect(Collectors.toList());

        for (String castle : castleList) { // checking castles first because if you build a castle then you can't build anything else
            if (CatanDiceExtra.isActionValid(boardState, "build" + castle)) {
                return new String[][]{new String[]{"build" + castle}};
            }
        }

        ArrayList<List<String>> buildings = new ArrayList<>(Arrays.asList(
                Arrays.stream(board.tiles).map(k -> k.toString().replace("J", "K")).collect(Collectors.toList()), // knights
                Arrays.stream(board.settlements).map(Settlement::toString).collect(Collectors.toList()), // settlements
                getCities(board.settlements), // cities
                roadsList // roads
        ));
        ArrayList<Resource> resources = new ArrayList<>();
        for (char c : CatanDiceExtra.validateClass.Misc.getResourcesFromBoardState(boardState)) {
            resources.add(Prices.toResource.get(c));
        }
        if (Prices.findBuilds(resources).stream().filter(l -> l.size() == 3).collect(Collectors.toList()).size() > 0) { // check if there are any [road, road, road] possible buys
            for (String road1 : roadsList) {
                for (String road2 : roadsList) {
                    for (String road3 : roadsList) {
                        String[] actionSequence = new String[]{"build" + road1, "build" + road2, "build" + road3};
                        if (CatanDiceExtra.isActionSequenceValid(boardState, actionSequence)) {
                            possibleBuilds.add(new ArrayList<>(Arrays.asList(actionSequence)));
                        }
                    }
                }
            }
        }
        for (List<String> buildType : buildings) {
            for (String build : buildType) {
                if (CatanDiceExtra.isActionValid(boardState, "build" + build)) {
                    possibleBuilds.add(new ArrayList<>(Arrays.asList("build" + build)));
                }
            }
        }
        ArrayList<ArrayList<String>> secondaryBuilds = new ArrayList<>();
        ArrayList<List<String>> secondaryListOfBuildings = new ArrayList<>(Arrays.asList(roadsList, buildings.get(0))); // only roads or knights can be secondary builds
        for (int i = 0; i < possibleBuilds.size(); i++) {
            for (String build : possibleBuilds.get(i)) {
                String tmpBoardState = removeResourcesFromBoardState(boardState, build);
                for (List<String> buildType : secondaryListOfBuildings) {
                    for (String build2 : buildType) {
                        if (CatanDiceExtra.isActionValid(tmpBoardState, "build" + build2)) {
                            secondaryBuilds.add(new ArrayList<>(Arrays.asList(build, "build" + build2)));
                        }
                    }
                }
            }
        }
        for (ArrayList<String> build : secondaryBuilds) {
            if (!possibleBuilds.contains(build) && !possibleBuilds.contains(new ArrayList<>(Arrays.asList(build.get(1), build.get(0))))) {
                possibleBuilds.add(build);
            }
        }
        return toStringArr(possibleBuilds);
    }
    private static List<String> getCities(Settlement[] settlements) {
        List<Settlement> cities = Arrays.stream(settlements).filter(s -> Board.cityLocations.contains(s.intersectionIndex)).collect(Collectors.toList());
        return cities.stream().map(t -> t.toString().replace("S", "T")).collect(Collectors.toList());
    }
    private static String[][] toStringArr(ArrayList<ArrayList<String>> builds) { // probably the most cringe, imperative thing ive written in a while
        String[][] r = new String[builds.size()][];
        for (int a = 0; a < builds.size(); a++) {
            List<String> arr = builds.get(a);
            String[] c = new String[arr.size()];
            for (int b = 0; b < arr.size(); b++) {
                c[b] = arr.get(b);
            }
            r[a] = c;
        }
        return r;
    }
    private static String removeResourcesFromBoardState(String boardState, String build) {
        HashMap<Character, Character[]> buildToResources = new HashMap<>() {{
            put('R', new Character[]{'b', 'l'});
            put('S', new Character[]{'b', 'l', 'w', 'g'});
            put('K', new Character[]{'o', 'w', 'g'});
            put('T', new Character[]{'o', 'o', 'o', 'g', 'g'});

        }};
        Character[] resourcesToRemove = buildToResources.get(build.charAt(5));
        for (char c : resourcesToRemove) {
           boardState = boardState.replaceFirst(Character.toString(c), "");
        }
        return boardState;
    }
}
