package comp1140.ass2;

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

    final static ArrayList<String> allCoastalNodes = new ArrayList<>(Arrays.asList(
            "00",
            "03",
            "07",
            "11",
            "16",
            "21",
            "27",
            "33",
            "38",
            "43",
            "47",
            "51",
            "48",
            "52",
            "49",
            "53",
            "50",
            "46",
            "42",
            "37",
            "32",
            "26",
            "20",
            "15",
            "10",
            "06",
            "02",
            "05",
            "01",
            "04"
    ));

    /**
     * Checks the game phase based on board state
     *
     * Given a valid board state, return all applicable player action sequences.
     * The method should return an array of sequences, where each sequence is
     * an array of action string representations.
     *
     * @param boardState: string representation of the current board state.
     * @return array of possible action sequences. (returns the respective method for the current Gamestate)
     */
    public static String[][] generate(String boardState){
//        if (CatanDiceExtra.applyActionSequenceHelper.isStartingPhase(boardState))
//            return possibleStartingRoadBuilds(boardState);
        return switch (boardState.charAt(2)) {
            case '0' -> generateAllPossibleStartGameActionSequences(boardState);
            case '1', '2' -> generateAllPossibleRollPhaseActionSequences(boardState);
            default -> generateAllPossibleBuildPhaseActionSequences(boardState);
        };
    }

    public static String[][] possibleStartingRoadBuilds(String boardState) {
        List<String> coastalRoads = List.of(roads);
        coastalRoads = coastalRoads.stream().filter(r -> isCoastalRoad(r)).collect(Collectors.toList());
        coastalRoads = coastalRoads.stream().map(r -> "buildR" + r).collect(Collectors.toList());
        ArrayList<String> coastalRoadsArr = new ArrayList<>(coastalRoads);
        if (boardState.charAt(0) == 'W') {
            return toStringArr(new ArrayList<>(List.of(coastalRoadsArr)));
        }
        else {
            int rIndex = boardState.indexOf('R');
            String[] wRoad = new String[]{boardState.substring(rIndex + 1, rIndex + 3), boardState.substring(rIndex+3, rIndex+5)};
            int coastalIndex = coastalRoads.indexOf("buildR" + wRoad[0] + wRoad[1]);
            if (coastalIndex == -1) {
                coastalIndex = coastalRoads.indexOf("buildR" + wRoad[1] + wRoad[0]);
            }
            if (coastalIndex - 5 < 0) {
                ArrayList<String> splicedCoastalRoads = new ArrayList<>(coastalRoads.subList(coastalIndex + 5, coastalRoads.size() - 5));
                return toStringArr(new ArrayList<>(List.of(splicedCoastalRoads)));
            }
            else if (coastalIndex + 5 > coastalRoads.size()) {
                ArrayList<String> splicedCoastalRoads = new ArrayList<>(coastalRoads.subList(5, coastalIndex - 5));
                return toStringArr(new ArrayList<>(List.of(splicedCoastalRoads)));
            }
            else {
                ArrayList<String> splicedCoastalRoads = new ArrayList<>(coastalRoads.subList(coastalIndex + 5, coastalRoads.size()));
                ArrayList<String> splicedCoastalRoadsLOWER = new ArrayList<>(coastalRoads.subList(0, coastalIndex - 5));
                splicedCoastalRoads.addAll(splicedCoastalRoadsLOWER);
                return toStringArr(new ArrayList<>(List.of(splicedCoastalRoads)));
            }
        }
    } // this will be used for the actual game

    private static boolean isCoastalRoad(String road) {
        String firstCoord = road.substring(0, 2);
        String secondCoord = road.substring(2);
        return allCoastalNodes.contains(firstCoord) && allCoastalNodes.contains(secondCoord);
    }

    public static String[][] generateAllPossibleStartGameActionSequences(String boardState) {
        ArrayList<String[]> actionPossible = new ArrayList<>();

        for (var road : roads) {
            String action = "buil" + "R" + road;

            if (CatanDiceExtra.isActionValid(boardState, action))
                actionPossible.add(new String[]{action});
        }

        String[][] allActions = new String[actionPossible.size()][];

        for (int i = 0; i < allActions.length; i++) {
            allActions[i] = actionPossible.get(i);
        }

        return allActions;
    }

    public static String[][] generateAllPossibleRollPhaseActionSequences(String boardState) {

        String turn = getTurnFromBoardState(boardState);
        String resources = turn.substring(3);

        String[] resourceArray = new String[resources.length()];

        // CONVERTING STRING TO STRING ARRAY
        for (int i = 0; i < resourceArray.length; i++) {
            resourceArray[i] = resources.substring(i, i+1);
        }

        // GETTING ALL POSSIBLE COMBINATIONS
        List<List<String>> allPossible = getAllResourceCombination(resourceArray);
        ArrayList<String[]> FINAL = new ArrayList<>();
        FINAL.add(new String[]{"keep"});

        for (List<String> res : allPossible){
            String toAdd = "";
            for (String toAddEach : res)
                toAdd += toAddEach;
            FINAL.add(new String[]{"keep" + toAdd});
        }

        String[][] FINAL_ARRAY = new String[FINAL.size()][];

        for (int i = 0; i < FINAL.size(); i++) {
            FINAL_ARRAY[i] = FINAL.get(i);
        }
        return FINAL_ARRAY;
    }

    /**
     * Checks swaps, trades and normal builds given a boardstate
     * @param boardState
     * @return
     * Authored By Manindra de Mel, u7156805
     */
    public static String[][] generateAllPossibleBuildPhaseActionSequences(String boardState) {
        ArrayList<String[]> sequences = new ArrayList<>();
        String turn = getTurnFromBoardState(boardState);
        if (turn.contains("m")) {
            for (String[] s : addTrades(boardState)) {
                sequences.add(s);
            }
        }
        if (Board.containsKnights(boardState)) {
            for (String[] s : addSwaps(boardState)) {
                sequences.add(s);
            }
        }
        for (String[] s : findBuilds(boardState)) {
            sequences.add(s);
        }
        String[][] sequencesArr = new String[sequences.size() + 1][];
        for (int i = 0; i < sequences.size(); i++) {
            sequencesArr[i] = sequences.get(i);
        }
        sequencesArr[sequencesArr.length-1] = new String[]{};
        return sequencesArr;
    }

    /**
     * Computes all the possible combinations of resources
     *
     * @param resources String array of individual resource
     * @return Combination of all resources
     */
    public static List<List<String>> getAllResourceCombination(String[] resources) {

        List<List<String>> powerSet = new LinkedList<List<String>>();

        for (int i = 1; i <= resources.length; i++)
            powerSet.addAll(combination(Arrays.asList(resources), i));

        return powerSet;
    }

    /**
     * Helper method for getAllResourceCombination()
     *
     * @param values List of values for combination is to be found
     * @param size size is r in nCr
     * @return Combination of values of items of length size
     * @param <T> Any type
     */
    public static <T> List<List<T>> combination(List<T> values, int size) {

        if (0 == size) {
            return Collections.singletonList(Collections.<T> emptyList());
        }

        if (values.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<T>> combination = new LinkedList<List<T>>();

        T actual = values.iterator().next();

        List<T> subSet = new LinkedList<T>(values);
        subSet.remove(actual);

        List<List<T>> subSetCombination = combination(subSet, size - 1);

        for (List<T> set : subSetCombination) {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(combination(subSet, size));

        return combination;
    }

    /**
     * Returns the trades made + then the builds which can be made with those trades
     * @param boardState
     * @return [[trade, build, build], [trade, trade, build]]...
     * Authored By Manindra de Mel, u7156805
     */
    private static String[][] addTrades(String boardState) { // todo if the trade results in a knight being built then we have to call addSwaps() although unlikely to happen
        int numGold = Math.floorDiv(boardState.length() - boardState.replaceAll("m", "").length(), 2);
        if (numGold < 1) {
            return new String[0][];
        }
        ArrayList<Resource> resources = new ArrayList<>(Arrays.asList(Board.boardResourcesWithoutGold));
        ArrayList<ArrayList<Resource>> allResourceCombinations = Prices.powerset(resources, resources.size() - 1);
        List<ArrayList<Resource>> filteredResourceCombinations = allResourceCombinations.stream().filter(l -> l.size() <= numGold && l.size() > 0).collect(Collectors.toList());
        ArrayList<List<String>> tmp = new ArrayList<>();
        ArrayList<String> trades = new ArrayList<>();
        for (ArrayList<Resource> r : filteredResourceCombinations) {
            tmp.add(r.stream().map(e -> Board.resourceCharacterHashMap.get(e)).collect(Collectors.toList()));
        }
        for (List<String> f : tmp) {
            String tmpString = "";
            for (String s : f) {
                tmpString += s;
            }
            trades.add("trade" + tmpString);
        }
        return addBuilds(boardState, trades);
    }

    /**
     * Returns swaps made + then the builds which can be made with those trades
     * @param boardState
     * @return [[swap, build], [swap, swap, swap, build]]
     * Authored By Manindra de Mel, u7156805
     */
    private static String[][] addSwaps(String boardState) {
        Board b = new Board();
        b.applyBoardState(boardState);
        List<Integer> knightCoords = b.getKnightLocationsOfPlayer(boardState.charAt(0));
        List<Character> possibleSwapsWithKnights = knightCoords.stream().map(e -> Board.resourceCharacterHashMap.get(Board.tileToResource.get(Board.tileTypes[e])).charAt(0)).collect(Collectors.toList());
        ArrayList<Character> resources = CatanDiceExtra.validateClass.Misc.getResourcesFromBoardState(boardState);
        ArrayList<String> possibleSwaps = new ArrayList<>();
        if (possibleSwapsWithKnights.contains('m')) {
            for (Character a : resources) {
                for (Character c : Board.resourceChars) { // possible
                    String newSwap = "swap" + Character.toString(a) + Character.toString(c);
                    if (a != c && !possibleSwaps.contains(newSwap)) {
                        possibleSwaps.add(newSwap);
                    }
                }
            }
        }
        if (possibleSwaps.size() > 0) {
            for (Character c : resources) {
                for (Character e : possibleSwapsWithKnights) {
                    if (c != e && c != 'm') {
                        possibleSwaps.add("swap" + Character.toString(c) + Character.toString(e));
                    }
                }
            }
            String[][] defaultStates = addBuilds(boardState, possibleSwaps);
            //check for anymore swaps
            ArrayList<String[]> newStates = new ArrayList<>();
            for (String[] s : defaultStates) {
                for (String st : s) {
                    if (st.charAt(5) == 'K') {
                        String tmpBoardState = new String(boardState);
                        for (String st2 : s) {
                            tmpBoardState = CatanDiceExtra.applyAction(tmpBoardState, st2);
                        }
                        String[][] nextSwaps = addSwaps(tmpBoardState);
                        if (nextSwaps.length > 0) {
                            ArrayList<String[]> extendedStates = new ArrayList<>(Arrays.asList(defaultStates));
                            for (String[] action : nextSwaps) {
                                extendedStates.add(concatArrays(s, action));
                            }
                            defaultStates = toStringArrB(extendedStates);
                        }
                    }
                }
            }

            return defaultStates;
        }
        return new String[0][];
    }

    /**
     * concatenates two arrays together
     * @param a a String[]
     * @param b a String[]
     * @return the concatenation of these two arrays
     * Authored By Manindra de Mel, u7156805
     */
    private static String[] concatArrays(String[] a, String[] b) {
        String[] newArr = new String[a.length + b.length];
        System.arraycopy(a, 0, newArr, 0, a.length);
        System.arraycopy(b, 0, newArr, a.length, b.length);
        return newArr;
    }

    /**
     * Applies either a trade or a swap and checks if that action was redundant or not
     * @param boardState
     * @param actions
     * @return non-redundant trade/swap action builds with the associated trade/swap
     */
    private static String[][] addBuilds(String boardState, ArrayList<String> actions) {
        ArrayList<ArrayList<String>> sequences = new ArrayList<>();
        String[][] defaultBuilds = findBuilds(boardState);
        for (String action : actions) {
            String[][] buildsWithAppliedAction = findBuilds(CatanDiceExtra.applyAction(boardState, action));
            if (!Arrays.deepEquals(defaultBuilds, buildsWithAppliedAction) && buildsWithAppliedAction.length != 0 && !(defaultBuilds.length > buildsWithAppliedAction.length)) { // this means the builds have changed
                for (String[] s : buildsWithAppliedAction) {
                    ArrayList<String> tmpArr = new ArrayList<>(Arrays.asList(action));
                    tmpArr.addAll(new ArrayList<>(List.of(s)));
                    sequences.add(tmpArr);
                }
            }
        }
        return toStringArr(sequences);
    }

    /**
     * finds all the possible build combinations (checks price and location)
     * @param boardState
     * @return an array of combinations of builds
     * Authored By Manindra de Mel, u7156805
     */
    private static String[][] findBuilds(String boardState) {
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
                String tmpBoardState = CatanDiceExtra.applyAction(boardState, build);
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
            if (!possibleBuilds.contains(build)) {
                possibleBuilds.add(build);
            }
        }
        return toStringArr(possibleBuilds);
    }


    /**
     * Gets all the cities in string form
     * @param settlements
     * @return T0, T7, ...
     * Authored By Manindra de Mel, u7156805
     */
    private static List<String> getCities(Settlement[] settlements) {
        List<Settlement> cities = Arrays.stream(settlements).filter(s -> Board.cityLocations.contains(s.intersectionIndex)).collect(Collectors.toList());
        return cities.stream().map(t -> t.toString().replace("S", "T")).collect(Collectors.toList());
    }

    /**
     * Converts an Arraylist<Arraylist<String>> to a String[][]
     * @param builds
     * @return String[][]
     * Authored By Manindra de Mel, u7156805
     */
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

    private static String[][] toStringArrB(ArrayList<String[]> builds) {
        String[][] newArr = new String[builds.size()][];
        for (int i = 0; i < builds.size(); i++) {
            newArr[i] = builds.get(i);
        }
        return newArr;
    }
}
