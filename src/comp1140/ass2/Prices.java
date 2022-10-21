package comp1140.ass2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
public class Prices {
    /**
     * a set of resources which points to each build
     * Authored By Manindra de Mel, u7156805
     */
    public static final HashMap<ArrayList<Resource>, String> builds = new HashMap<>() {{
        put(new ArrayList<Resource>(Arrays.asList(Resource.brick, Resource.wood)), "Road"); // there is implicit ordering in Enums, therefore we can just sort the list and use it as a key for this hashmap
        put(new ArrayList<Resource>(Arrays.asList(Resource.brick, Resource.wood, Resource.sheep, Resource.wheat)), "Settlement");
        put(new ArrayList<Resource>(Arrays.asList(Resource.sheep, Resource.stone, Resource.wheat)), "Solider"); // Solider = knight sorry.
        put(new ArrayList<Resource>(Arrays.asList(Resource.stone, Resource.stone, Resource.stone, Resource.wheat, Resource.wheat)), "City");
        // castles, (let me know if there is a better way to write this)
        put(new ArrayList<Resource>(Arrays.asList(Resource.brick, Resource.brick, Resource.brick, Resource.brick, Resource.brick)), "Castle");
        put(new ArrayList<Resource>(Arrays.asList(Resource.wood, Resource.wood, Resource.wood, Resource.wood, Resource.wood)), "Castle");
        put(new ArrayList<Resource>(Arrays.asList(Resource.sheep, Resource.sheep, Resource.sheep, Resource.sheep, Resource.sheep)), "Castle");
        put(new ArrayList<Resource>(Arrays.asList(Resource.stone, Resource.stone, Resource.stone, Resource.stone, Resource.stone)), "Castle");
        put(new ArrayList<Resource>(Arrays.asList(Resource.wheat, Resource.wheat, Resource.wheat, Resource.wheat, Resource.wheat)), "Castle");
    }};

    /**
     * Given a list of resources, find the powerset of that list.
     * @param resources a list of resources owned by a player
     * @param index an index used for recursion for the index of each set
     * @return powerset of that list
     * Authored By Manindra de Mel, u7156805
     */
    private static ArrayList<ArrayList<Resource>> powerset(ArrayList<Resource> resources, int index) {
        ArrayList<ArrayList<Resource>> subsets;
        if (index < 0) {
            subsets = new ArrayList<>();
            subsets.add(new ArrayList<Resource>());
        }
        else {
            subsets = powerset(resources, index - 1);
            Resource resource = resources.get(index);
            ArrayList<ArrayList<Resource>> deepSubset = new ArrayList<>();
            for (ArrayList<Resource> subset : subsets) {
                ArrayList<Resource> childSubset = new ArrayList<>();
                childSubset.addAll(subset);
                childSubset.add(resource);
                deepSubset.add(childSubset);
            }
            subsets.addAll(deepSubset);
        }
        return subsets;
    }

    /**
     * Given a list of resources, return a list of all the possible buildings that can be created from that list of resources
     * @param resources
     * @return a list of buildings that can be created from the list of resources
     * Authored By Manindra de Mel, u7156805
     */
    public static ArrayList<ArrayList<String>> findBuilds(ArrayList<Resource> resources) { // need to account for the case of two gold
        ArrayList<ArrayList<Resource>> possibleBuildsR = new ArrayList<>();
        ArrayList<ArrayList<String>> validBuilds = new ArrayList<>();

        Collections.sort(resources); // check for one case which is not account for (3 roads)
        if (resources.equals(new ArrayList<>(Arrays.asList(Resource.brick, Resource.brick, Resource.brick, Resource.wood, Resource.wood, Resource.wood)))) {
            return new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("Road")),
                new ArrayList<>(Arrays.asList("Road", "Road")),
                new ArrayList<>(Arrays.asList("Road", "Road", "Road"))
            ));
        }

        for (ArrayList<Resource> resource : powerset(resources, resources.size() - 1)) { // Possible individual builds
            if (resource.size() > 1) {
                Collections.sort(resource);
                if (builds.containsKey(resource)) {
                    if (builds.get(resource) != "Road" || builds.get(resource) != "Solider") {
                        if (!(possibleBuildsR.contains(resource))) {
                            possibleBuildsR.add(resource);
                        }
                    }
                    else {
                        possibleBuildsR.add(resource);
                    }
                    if (!(validBuilds.contains(new ArrayList<>(Arrays.asList(builds.get(resource)))))) {
                        validBuilds.add(new ArrayList<>(Arrays.asList(builds.get(resource))));
                    }
                }
            }
        }

        for (ArrayList<Resource> resources1 : possibleBuildsR) { // Possible joint builds (as most two)
            secondElement:
                for (ArrayList<Resource> resources2 : possibleBuildsR) {
                    ArrayList<Resource> tmpResources = new ArrayList<>(resources);
                    for (Resource resourceItem : resources1) {
                        tmpResources.remove(resourceItem);
                    }
                    for (Resource resourceItem : resources2) {
                        if (!(tmpResources.remove(resourceItem))) {
                            continue secondElement;
                        }
                    }
                    if (!validBuilds.contains(new ArrayList<String>(Arrays.asList(builds.get(resources2), builds.get(resources1))))) {
                        validBuilds.add(new ArrayList<String>(Arrays.asList(builds.get(resources1), builds.get(resources2))));
                    }
                }
        }
        return validBuilds;
    }

    /**
     * Similarly to findbuilds, this finds all the possible builds given a list of resources, but all takes a gold trade decided by the player and finds all
     * the new builds possible.
     * @param resources a list of resources
     * @param resourcesAttainedFromGold resources gained from the trade
     * @return a list of builds
     * Authored By Manindra de Mel, u7156805
     */
    public static ArrayList<ArrayList<String>> findBuildsWithManualGoldTrade(ArrayList<Resource> resources, ArrayList<Resource> resourcesAttainedFromGold) { // resourcesAttainedFromGold is what the player decides when they want to exchange gold for materials
        if (validateGoldTrade(resources, resourcesAttainedFromGold)) {
            resources = removeGold(resources, (resourcesAttainedFromGold.size() * 2));
            resources.addAll(resourcesAttainedFromGold);
        }
        return findBuilds(resources);
    }

    /**
     * check if there is sufficient amount of gold for the gold trade
     * @param resources list of current resources
     * @param resourcesAttainedFromGold resources to be exchanged for gold
     * @return a bool based on if the trade is valid or not (Task 7)
     * Authored By Manindra de Mel, u7156805
     */
    private static Boolean validateGoldTrade(ArrayList<Resource> resources, ArrayList<Resource> resourcesAttainedFromGold) {
        int numGold = 0;
        for (Resource resource : resources) {
            if (resource == Resource.gold) {
                numGold++;
            }
        }
        if (resourcesAttainedFromGold.size() - (numGold / 2) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Remove a certain amount of gold from a list of resources
     * @param resources list of resources
     * @param goldToBeRemoved number of gold to be removed
     * @return a new list with the removed gold
     * Authored By Manindra de Mel, u7156805
     */
    private static ArrayList<Resource> removeGold(ArrayList<Resource> resources, int goldToBeRemoved) {
        for (int i = 0; i < resources.size(); i++) {
            if (goldToBeRemoved == 0) {
                break;
            }
            else if (resources.get(i) == Resource.gold) {
                resources.remove(i);
            }
        }
        return resources;
    }

    /**
     * Applies the 'swap' action for task 9.
     * @param boardState the current boardstate
     * @param actionSub the action substring. i.e. (swapbg) -> (bg). where bg is the substring
     * @param playerId the current player's turn
     * @return a new boardState with the swap applied
     * Authored By Manindra de Mel, u7156805
     */
    public static String swap(String boardState, String actionSub, String playerId) {

        HashMap<String, TileType> convertToTileType = new HashMap<>(){{
            put("b", TileType.bricks);
            put("g", TileType.grain);
            put("l", TileType.timber);
            put("o", TileType.ore);
            put("w", TileType.wool);
        }};
        String resources = Board.getTurnFromBoardState(boardState).substring(3);
        String newResources = CatanDiceExtra.sortString(resources.replaceFirst(actionSub.substring(0,1), actionSub.substring(1)));
        boardState = boardState.replaceFirst(resources, newResources);
        Board board = new Board(Board.getTurnFromBoardState(boardState), Board.getScoreFromBoardState(boardState));
        board.applyBoardState(boardState);
        boolean foundSpecificKnight = false;
        for (int i = 0; i < board.tiles.length; i++) {
            if (!(i == 9 || i == 10)) {
                foundSpecificKnight = setUsedTrue(actionSub, playerId, convertToTileType, board, i);
                if (foundSpecificKnight) {
                    break;
                }
            }
        }
        if (!(foundSpecificKnight)) {
            for (int i = 9; i < 11; i++) { // check multipurpose knight
                setUsedTrue(actionSub, playerId, convertToTileType, board, i);
            }
        }
        return Board.toStringWithNewScore(board);
    }
    /**
     * A helper function for the 'swap' method. This method sets a knight from unused to used.
     * @param actionSub the action substring for keep. i.e. (keepbb) -> (bb)
     * @param playerId the current player's turn
     * @param convertToTileType the hashmap which converts to a tile type
     * @param board A Board representation of the string boardstate
     * @param i the index
     * @return a boolean which returns true if a knight has been set to used.
     * Authored By Manindra de Mel, u7156805
     */
    private static boolean setUsedTrue(String actionSub, String playerId, HashMap<String, TileType> convertToTileType, Board board, int i) {
        if (board.tiles[i].Owner.name != "") {
            if (board.tiles[i].Owner.name.charAt(0) == playerId.charAt(0)) {
                if (board.tiles[i].tileType == convertToTileType.get(actionSub.substring(1)) || board.tiles[i].tileType == TileType.desert) {
                    if (!board.tiles[i].used) {
                        board.tiles[i].used = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Applies the 'keep' action for task 9
     * @param boardState the current boardstate
     * @param action the action given
     * @return a new boardstate with the action applied
     * Authored By Manindra de Mel, u7156805
     */
    public static String keep(String boardState, String action) {
        boardState = boardState.substring(0, 2) + String.valueOf(Integer.parseInt(boardState.substring(2, 3)) + 1) + boardState.substring(3); // +1 to roll counter
        int endOfResourcesIndex = 3 + Integer.parseInt(boardState.substring(1, 2));
        String oldResources = boardState.substring(3, endOfResourcesIndex);
        String newResources = Prices.modifyResources(oldResources, action.substring(4), Integer.parseInt(boardState.substring(1, 2)));
        boardState = boardState.replace(oldResources, newResources);
        return boardState;
    }
    /**
     * Applies the 'trade' action the the boardstate (for task 9)
     * @param boardState a String representation of the boardState
     * @param action the action
     * @return a new boardstate with the action applied
     * Authored By Manindra de Mel, u7156805
     */
    public static String trade(String boardState, String action) {
        String oldResources = boardState.substring(3, boardState.indexOf('W', 2));
        String newResources = "";
        newResources = oldResources;
        for (char c : action.substring(5).toCharArray()) {
            newResources = newResources.replaceFirst("m", "");
            newResources = newResources.replaceFirst("m", Character.toString(c));
        }
        newResources = CatanDiceExtra.sortString(newResources);
        return boardState.replace(oldResources, newResources);
    }

    /**
     * A helper function for the 'keep' action, which rolls new resources that the player didn't keep.
     * @param resources string of the resources from the board
     * @param actionSub the resources that were kept from the keep action
     * @param numDice the number of dice/new resources to roll/attain
     * @return a new resources string
     * Authored By Manindra de Mel, u7156805
     */
    public static String modifyResources(String resources, String actionSub, int numDice) {
        ArrayList<Character> actionSubList = new ArrayList<>();
        for (Character c : actionSub.toCharArray()) {
            actionSubList.add(c);
        }
        for (Character c : resources.toCharArray()) {
            if (actionSubList.contains(c)) {
                resources = resources.replaceFirst(Character.toString(c), "");
                actionSubList.remove(actionSubList.indexOf(c));
            }
        }
        String newResources = CatanDiceExtra.rollDice(numDice - actionSub.length());
        return CatanDiceExtra.sortString(actionSub + newResources);
    }

}
