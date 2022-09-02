package comp1140.ass2;
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
    public static final HashMap<ArrayList<Resource>, String> goldTrades = new HashMap<>() {{
        put(new ArrayList<Resource>(Arrays.asList(Resource.gold, Resource.gold)), "Wood");
        put(new ArrayList<Resource>(Arrays.asList(Resource.gold, Resource.gold)), "Stone");
        put(new ArrayList<Resource>(Arrays.asList(Resource.gold, Resource.gold)), "Sheep");
        put(new ArrayList<Resource>(Arrays.asList(Resource.gold, Resource.gold)), "Wheat");
        put(new ArrayList<Resource>(Arrays.asList(Resource.gold, Resource.gold)), "Brick");
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
    public static ArrayList<ArrayList<String>> findBuilds(ArrayList<Resource> resources) {
        ArrayList<ArrayList<Resource>> possibleBuildsR = new ArrayList<>();
        ArrayList<ArrayList<String>> validBuilds = new ArrayList<>();
        Collections.sort(resources); // check for one case which is not account for (3 roads)
        if (resources.equals(new ArrayList<>(Arrays.asList(Resource.brick, Resource.brick, Resource.brick, Resource.wood, Resource.wood, Resource.wood)))) {
            return new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("Road")),
                new ArrayList<>(Arrays.asList("Road", "Road")),
                new ArrayList<>(Arrays.asList("Road", "Road", "Road")  )
            ));
        }

        for (ArrayList<Resource> resource : powerset(resources, resources.size() - 1)) { // Possible individual builds
            if (resource.size() > 1) {
                Collections.sort(resource);
                if (builds.containsKey(resource)) {
                    possibleBuildsR.add(resource);
                    if (!(validBuilds.contains(new ArrayList<>(Arrays.asList(builds.get(resource)))))) {
                        validBuilds.add(new ArrayList<>(Arrays.asList(builds.get(resource))));
                    }
                }
            }
        }

        for (ArrayList<Resource> resources1 : possibleBuildsR) { // Possible joint builds (as most two)
            secondElement:
                for (ArrayList<Resource> resources2 : possibleBuildsR) {
                    ArrayList<Resource> tmpResources = new ArrayList<>();
                    tmpResources = resources;
                    for (Resource resourceItem : resources1) {
                        tmpResources.remove(resourceItem);
                    }
                    for (Resource resourceItem : resources2) {
                        if (!(tmpResources.remove(resourceItem))) {
                            continue secondElement;
                        }
                    }
                    validBuilds.add(new ArrayList<String>(Arrays.asList(builds.get(resources1), builds.get(resources2))));
                }
        }
        return validBuilds;
    }
}
