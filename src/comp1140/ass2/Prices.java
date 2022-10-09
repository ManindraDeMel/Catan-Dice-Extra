package comp1140.ass2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
public class Prices {
    public static final HashMap<ArrayList<Resource>, String> builds = new HashMap<>() {{
        put(new ArrayList<Resource>(Arrays.asList(Resource.brick, Resource.wood)), "Road"); // there is implicit ordering in Enums, therefore we can just sort the list and use it as a key for this hashmap
        put(new ArrayList<Resource>(Arrays.asList(Resource.brick, Resource.wood, Resource.sheep, Resource.wheat)), "Settlement");
        put(new ArrayList<Resource>(Arrays.asList(Resource.sheep, Resource.stone, Resource.wheat)), "Solider");
        put(new ArrayList<Resource>(Arrays.asList(Resource.stone, Resource.stone, Resource.stone, Resource.wheat, Resource.wheat)), "City");
        // castles, (let me know if there is a better way to write this)
        put(new ArrayList<Resource>(Arrays.asList(Resource.brick, Resource.brick, Resource.brick, Resource.brick, Resource.brick)), "Castle");
        put(new ArrayList<Resource>(Arrays.asList(Resource.wood, Resource.wood, Resource.wood, Resource.wood, Resource.wood)), "Castle");
        put(new ArrayList<Resource>(Arrays.asList(Resource.sheep, Resource.sheep, Resource.sheep, Resource.sheep, Resource.sheep)), "Castle");
        put(new ArrayList<Resource>(Arrays.asList(Resource.stone, Resource.stone, Resource.stone, Resource.stone, Resource.stone)), "Castle");
        put(new ArrayList<Resource>(Arrays.asList(Resource.wheat, Resource.wheat, Resource.wheat, Resource.wheat, Resource.wheat)), "Castle");
    }};

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
    public static ArrayList<ArrayList<String>> findBuildsWithManualGoldTrade(ArrayList<Resource> resources, ArrayList<Resource> resourcesAttainedFromGold) { // resourcesAttainedFromGold is what the player decides when they want to exchange gold for materials
        if (validateGoldTrade(resources, resourcesAttainedFromGold)) {
            resources = removeGold(resources, (resourcesAttainedFromGold.size() * 2));
            resources.addAll(resourcesAttainedFromGold);
        }
        return findBuilds(resources);
    }
    // Create one which finds the most efficient gold trade automatically (for the AI)

    public static String swap(String actionSub, String playerId) {
        return "";
    }
    public static String trade(String boardState, String action) {
        int endOfResourcesIndex = 3 + Integer.parseInt(boardState.substring(1, 2));
        String oldResources = boardState.substring(3, endOfResourcesIndex);
        String newResources = "";
        newResources = oldResources;
        for (char c : action.substring(1).toCharArray()) {
            newResources = newResources.replaceFirst("g", "");
            newResources.replaceFirst("g", Character.toString(c));
        }
        newResources = CatanDiceExtra.sortString(newResources);
        return boardState.replace(oldResources, newResources);
    }

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
