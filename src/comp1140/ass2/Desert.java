package comp1140.ass2;

public class Desert extends Tile {
    Player[] Owners;
    Desert(Player player, Player player2) {
        super(null);
        this.Owners = new Player[]{player, player2};
    }
}
