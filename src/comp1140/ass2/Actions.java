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
        return switch (boardState.charAt(2)) {
            case '0' -> generateAllPossibleStartGameActionSequences(boardState);
            case '1', '2' -> generateAllPossibleRollPhaseActionSequences(boardState);
            default -> generateAllPossibleBuildPhaseActionSequences(boardState);
        };
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

    public static List<List<String>> getIt(String[] args) {

        List<List<String>> powerSet = new LinkedList<List<String>>();

        for (int i = 1; i <= args.length; i++)
            powerSet.addAll(combination(Arrays.asList(args), i));

        return powerSet;
    }

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

    public static String[][] generateAllPossibleRollPhaseActionSequences(String boardState) {

        String turn = getTurnFromBoardState(boardState);
        String resources = turn.substring(3);

        String[] resourceArray = new String[resources.length()];

        // CONVERTING STRING TO STRING ARRAY
        for (int i = 0; i < resourceArray.length; i++) {
            resourceArray[i] = resources.substring(i, i+1);
        }
        System.out.println("RESOURCES: " + resources);

        // GETTING ALL POSSIBLE COMBINATIONS
        List<List<String>> allPossible = getIt(resourceArray);
        System.out.println("FINAL: " + allPossible);
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
            System.out.println(Arrays.toString(FINAL.get(i)));
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
        String[][] sequencesArr = new String[sequences.size() + 2][];
        for (int i = 0; i < sequences.size(); i++) {
            sequencesArr[i] = sequences.get(i);
        }
        sequencesArr[sequencesArr.length - 2] = new String[0]; // add the null case
        sequencesArr[sequencesArr.length - 1] = new String[]{"keep"};
        return sequencesArr;
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
        for (Character c : resources) {
            for (Character e : possibleSwapsWithKnights) {
                if (c != e && c != 'm') { // todo swap for center knights
                    possibleSwaps.add("swap" + Character.toString(c) + Character.toString(e));
                }
            }
        }
        return addBuilds(boardState, possibleSwaps);
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
            if (!possibleBuilds.contains(build) && !possibleBuilds.contains(new ArrayList<>(Arrays.asList(build.get(1), build.get(0))))) {
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

    /**
     * Removes resources given a certain build
     * @param boardState
     * @param build "buildR050", "buildS0"
     * @return a new boardstate with the new resources in the turn phase
     * Authored By Manindra de Mel, u7156805
     */
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
