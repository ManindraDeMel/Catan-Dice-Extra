package comp1140.ass2;

public class Castle extends GamePiece {
    int index; // Stores the index of the Castle (e.g. 0, 1, 2, 3)

    public Castle(Player player, int index) {
        super(player);
        this.index = index;
    }

    @Override
    public String toString() {
        return "C" + index;
    }
}
