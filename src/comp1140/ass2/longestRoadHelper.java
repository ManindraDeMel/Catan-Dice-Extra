package comp1140.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class longestRoadHelper {

    /**
     * Finds and returns road indexes for both players in a single array list
     *
     * @param boardState board state of the game
     * @return the road indexes for both players in a single array list
     */
    public static ArrayList<ArrayList<String>> getIndexes(String boardState) {

        // Defining player strings and arrays
        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));
        ArrayList<ArrayList<String>> indexArr = new ArrayList<ArrayList<String>>();
        indexArr.add(new ArrayList<>());
        indexArr.add(new ArrayList<>());

        // Extracting road index strings of player W
        int countW = (int) playerW.chars().filter(ch -> ch == 'R').count();
        for (int i = 0; i < countW; i++) {
            String index1 = playerW.substring(playerW.indexOf('R') + 1 + i * 5, playerW.indexOf('R') + 3 + i * 5);
            String index2 = playerW.substring(playerW.indexOf('R') + 3 + i * 5, playerW.indexOf('R') + 5 + i * 5);
            if (!indexArr.get(0).contains(index1))
                indexArr.get(0).add(index1);

            if (!indexArr.get(0).contains(index2))
                indexArr.get(0).add(index2);
        }

        // Extracting roads strings of player X
        int countX = (int) playerX.chars().filter(ch -> ch == 'R').count();
        for (int i = 0; i < countX; i++) {
            String index1 = playerX.substring(playerX.indexOf('R') + 1 + i * 5, playerX.indexOf('R') + 3 + i * 5);
            String index2 = playerX.substring(playerX.indexOf('R') + 3 + i * 5, playerX.indexOf('R') + 5 + i * 5);
            if (!indexArr.get(1).contains(index1))
                indexArr.get(1).add(index1);

            if (!indexArr.get(1).contains(index2))
                indexArr.get(1).add(index2);
        }

        return indexArr;
    }

    /**
     * Finds all the roads for each player in the board state
     *
     * @param boardState board state of the game
     * @return ArrayList containing all roads constructed by each player
     */
    public static ArrayList<ArrayList<String>> getRoads(String boardState) {

        // Defining player strings and arrays
        String playerW = boardState.substring(boardState.indexOf('W', 1), boardState.indexOf('X', 1));
        String playerX = boardState.substring(boardState.indexOf('X', 1), boardState.indexOf('W', boardState.indexOf('X', 1)));
        ArrayList<ArrayList<String>> roadsArr = new ArrayList<ArrayList<String>>();
        roadsArr.add(new ArrayList<>());
        roadsArr.add(new ArrayList<>());

        // Extracting roads strings of player W
        int countW = (int) playerW.chars().filter(ch -> ch == 'R').count();
        for (int i = 0; i < countW; i++) {
            roadsArr.get(0).add(playerW.substring(playerW.indexOf('R') + 1 + i * 5, playerW.indexOf('R') + 5 + i * 5));
        }

        // Extracting roads strings of player X
        int countX = (int) playerX.chars().filter(ch -> ch == 'R').count();
        for (int i = 0; i < countX; i++) {
            roadsArr.get(1).add(playerX.substring(playerX.indexOf('R') + 1 + i * 5, playerX.indexOf('R') + 5 + i * 5));
        }

        return roadsArr;
    }

    public static ArrayList<ArrayList<ArrayList<String>>> getIndexNeighbours(ArrayList<ArrayList<String>> indexArr, ArrayList<ArrayList<String>> playerRoads) {

        ArrayList<ArrayList<ArrayList<String>>> neighbours = new ArrayList<ArrayList<ArrayList<String>>>();
        neighbours.add(new ArrayList<ArrayList<String>>());
        neighbours.add(new ArrayList<ArrayList<String>>());

        for (int i = 0; i < 2; i++) { // looping through each player
            for (int j = 0; j < indexArr.get(i).size(); j++) { // looping through each player's roads
                neighbours.get(i).add(new ArrayList<String>());
                for (String road : allNeighbourIndexes.get(indexArr.get(i).get(j))) { // looping all neighbour roads
                    if (indexArr.get(i).contains(road) && (playerRoads.get(i).contains(indexArr.get(i).get(j) + road) || playerRoads.get(i).contains(road + indexArr.get(i).get(j))))
                        neighbours.get(i).get(j).add(road);
                }
            }
        }
        return neighbours;
    }

    public static ArrayList<ArrayList<ArrayList<String>>> getNeighbours(ArrayList<ArrayList<String>> roadArr) {

        ArrayList<ArrayList<ArrayList<String>>> neighbours = new ArrayList<ArrayList<ArrayList<String>>>();
        neighbours.add(new ArrayList<ArrayList<String>>());
        neighbours.add(new ArrayList<ArrayList<String>>());

        for (int i = 0; i < 2; i++) { // looping through each player
            for (int j = 0; j < roadArr.get(i).size(); j++) { // looping through each player's roads
                neighbours.get(i).add(new ArrayList<String>());
                for (String road : allNeighbourRoads.get(roadArr.get(i).get(j))) { // looping all neighbour roads
                    if (roadArr.get(i).contains(road))
                        neighbours.get(i).get(j).add(road);
                }
            }
        }
        return neighbours;
    }

    static HashMap<String, ArrayList<String>> allNeighbourIndexes = new HashMap<>();
    static HashMap<String, ArrayList<String>> allNeighbourRoads = new HashMap<>();

    static {
        String[] roads = new String[]{
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

        for (int i = 0; i < 54; i++) {
            ArrayList<String> neighbours = new ArrayList<>();

            for (var road : roads) {
                String index1 = road.substring(0, 2);
                String index2 = road.substring(2);

                if (Integer.parseInt(index1) == i && !neighbours.contains(index2)) {
                    neighbours.add(index2);
                } else if (Integer.parseInt(index2) == i && !neighbours.contains(index1)) {
                    neighbours.add(index1);
                }

            }
            String key = "";
            if (i < 10)
                key = "0" + i;
            else
                key = Integer.toString(i);

            allNeighbourIndexes.put(key, neighbours);
        }


        ArrayList<String> allRoadsOnBoard = new ArrayList<>();


        for (int i = 0; i < roads.length; i++) {
            int index1 = Integer.parseInt(roads[i].substring(0, 2));
            int index2 = Integer.parseInt(roads[i].substring(2));
            ArrayList<String> neighbours = new ArrayList<>();

            for (int j = 0; j < roads.length; j++) {
                if (i==j) continue;

                int neighbourIndex1 = Integer.parseInt(roads[j].substring(0, 2));
                int neighbourIndex2 = Integer.parseInt(roads[j].substring(2));

                if (index1 == neighbourIndex1 || index1 == neighbourIndex2 || index2 == neighbourIndex1 || index2 == neighbourIndex2)
                    neighbours.add(roads[j]);
            }

            allNeighbourRoads.put(roads[i], neighbours);
        }
    }
    /**
     * Finds all the roads sequences for a player
     *
     * @param playerIndexes road indexes of a player
     * @param playerNeighbours connected movable neighbouring road indexes
     * @param allRoadSequence all the road sequence found so far
     * @param roadSequence current road sequence found
     * @param pos current index
     * @param notVisited indexes of roads where not visited
     */
    public static void getAllConnectedRoads(ArrayList<String> playerIndexes, ArrayList<ArrayList<String>> playerNeighbours,
                                             HashSet<ArrayList<String>> allRoadSequence, ArrayList<String> roadSequence, int pos,
                                             ArrayList<ArrayList<String>> notVisited) {

        List<String> longestRoadState = new ArrayList<>(roadSequence);

        for (var road : playerNeighbours.get(pos)) {
            if (!notVisited.get(pos).contains(road)) continue;

            roadSequence = new ArrayList<>(longestRoadState);
            roadSequence.add(road);

            allRoadSequence.add(roadSequence);

            notVisited.get(pos).remove(road);
            notVisited.get(playerIndexes.indexOf(road)).remove(playerIndexes.get(pos));
            getAllConnectedRoads(playerIndexes, playerNeighbours, allRoadSequence, roadSequence, playerIndexes.indexOf(road), notVisited);
            notVisited.get(pos).add(road);
            notVisited.get(playerIndexes.indexOf(road)).add(playerIndexes.get(pos));
        }
    }
}
