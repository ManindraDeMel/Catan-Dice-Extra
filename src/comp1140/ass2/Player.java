package comp1140.ass2;

import java.util.ArrayList;

public class Player {
    char ID; // Stores the ID of the player (e.g. 'X', 'W')
    ArrayList<ArrayList<GamePiece>> structures = new ArrayList<ArrayList<GamePiece>>(); // Stores the structures built
    String name;
    Resource[] resources;
    Player(String name) {
        this.name = name;
    }

    /**
     * Player class constructor with player's ID and name as arguments and initialises
     * the structures Arraylist with 5 inner Arraylists to stores corresponding Structures.
     *
     * structures Arraylist:
     * Index 0 -> Castle
     * Index 1 -> Knight
     * Index 2 -> Road
     * Index 3 -> Settlement
     * Index 4 -> City
     *
     * @param ID player's ID
     * @param name player's name
     */
    public Player(char ID, String name) {
        this.ID = ID;
        this.name = name;

        for (int i = 0; i < 5; i++) {
            structures.add(new ArrayList<GamePiece>());
        }
    }

    /**
     * Adds a structure to the player's 'structures' Arraylist in the corresponding index.
     *
     * @param piece Structure object to be added (e.g. Road, Settlement, Castle, Knight)
     * @return true if 'piece' is successfully added to the Arraylist. If not, false.
     */
    public boolean addStructure(GamePiece piece){
        if (piece instanceof Castle){
            structures.get(0).add((Castle) piece);
            return true;
        }
//        else if (piece instanceof ) // TODO Knight

        else if (piece instanceof Road) {
            structures.get(2).add((Road) piece);
            return true;
        }

        else if (piece instanceof Settlement) {
            if (((Settlement) piece).isCity == false){
                structures.get(3).add((Settlement) piece);
            }
            else {
                structures.get(4).add((Settlement) piece);
            }
            return true;
        }

        return false;
    }

    public Action takeTurn() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder playerBoardState = new StringBuilder();

        playerBoardState.append(this.ID);

        for (int i = 0; i < structures.size(); i++) {
            for (var piece :  structures.get(i)) {
                StringBuilder struct = new StringBuilder();

                switch (i){
                    case 0 -> {
                        struct.append(((Castle) piece).toString());
                    }

                    case 1 -> {} // TODO Knight toString

                    case 2 -> {
//                        struct.append()
                    }
                }

                playerBoardState.append(struct);
            }
        }

        return super.toString();
    }
}
