package comp1140.ass2.gui.backend;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class Constants {
    public static final int VIEWER_WIDTH = 1200;
    public static final int VIEWER_HEIGHT = 700;
    public static final double HEX_WIDTH = 140;
    public static final double HEX_HEIGHT = (int) (HEX_WIDTH * Math.sqrt(3) / 2);
    public static final int MARGIN_X = (int) (HEX_HEIGHT * 0.5);
    public static final int BOARD_X = (int) (HEX_HEIGHT * 0.75);
    public static final int MARGIN_Y = (int) (HEX_HEIGHT * 0.5);
    public static final int BOARD_Y = MARGIN_Y;
    public static final Color[] playersColour = new Color[]{Color.ORANGE, Color.RED};

    // Maps the Knight index to a class that contains centre coordinates of the tile the Knight is in
    public static HashMap<Integer, BoardTilePair> hmKnight = new HashMap<>() {{

        // Row 1
        put(0, new BoardTilePair(1, 0));
        put(1, new BoardTilePair(2, 0));
        put(2, new BoardTilePair(3, 0));

        // Row 2
        put(3, new BoardTilePair(1, 1));
        put(4, new BoardTilePair(2, 1));
        put(5, new BoardTilePair(3, 1));
        put(6, new BoardTilePair(4, 1));

        // Row 3
        put(7, new BoardTilePair(0, 2));
        put(8, new BoardTilePair(1, 2));
        put(9, new BoardTilePair(2, 2));
        put(10, new BoardTilePair(2, 2));
        put(11, new BoardTilePair(3, 2));
        put(12, new BoardTilePair(4, 2));

        // Row 4
        put(13, new BoardTilePair(1, 3));
        put(14, new BoardTilePair(2, 3));
        put(15, new BoardTilePair(3, 3));
        put(16, new BoardTilePair(4, 3));

        // Row 5
        put(17, new BoardTilePair(1, 4));
        put(18, new BoardTilePair(2, 4));
        put(19, new BoardTilePair(3, 4));
    }};

    // Maps the Road index to a class that contains centre coordinates of the tile the city is in and other
    // info to create the road
    public static HashMap<String, RoadStartID> hmRoads = new HashMap<>() {{

        // Row 1
        put("0307", new RoadStartID(0, 0));
        put("0003", new RoadStartID(0, 1));
        put("0004", new RoadStartID(0, 2));
        put("0408", new RoadStartID(0, 3));
        put("0812", new RoadStartID(0, 4));
        put("0712", new RoadStartID(0, 5));

        put("0104", new RoadStartID(1, 1));
        put("0105", new RoadStartID(1, 2));
        put("0509", new RoadStartID(1, 3));
        put("0913", new RoadStartID(1, 4));
        put("0813", new RoadStartID(1, 5));

        put("0205", new RoadStartID(2, 1));
        put("0206", new RoadStartID(2, 2));
        put("0610", new RoadStartID(2, 3));
        put("1014", new RoadStartID(2, 4));
        put("0914", new RoadStartID(2, 5));

        put("1116", new RoadStartID(3, 0));
        put("0711", new RoadStartID(3, 1));
        put("1217", new RoadStartID(3, 3));
        put("1722", new RoadStartID(3, 4));
        put("1622", new RoadStartID(3, 5));

        put("1318", new RoadStartID(4, 3));
        put("1823", new RoadStartID(4, 4));
        put("1723", new RoadStartID(4, 5));

        put("1419", new RoadStartID(5, 3));
        put("1924", new RoadStartID(5, 4));
        put("1824", new RoadStartID(5, 5));

        put("1015", new RoadStartID(6, 2));
        put("1520", new RoadStartID(6, 3));
        put("2025", new RoadStartID(6, 4));
        put("1925", new RoadStartID(6, 5));

        put("2127", new RoadStartID(7, 0));
        put("1621", new RoadStartID(7, 1));
        put("2228", new RoadStartID(7, 3));
        put("2833", new RoadStartID(7, 4));
        put("2733", new RoadStartID(7, 5));

        put("2329", new RoadStartID(8, 3));
        put("2934", new RoadStartID(8, 4));
        put("2834", new RoadStartID(8, 5));

        put("2430", new RoadStartID(9, 3));
        put("3035", new RoadStartID(9, 4));
        put("2935", new RoadStartID(9, 5));

        put("2531", new RoadStartID(11, 3));
        put("3136", new RoadStartID(11, 4));
        put("3036", new RoadStartID(11, 5));

        put("2026", new RoadStartID(12, 2));
        put("2632", new RoadStartID(12, 3));
        put("3237", new RoadStartID(12, 4));
        put("3137", new RoadStartID(12, 5));

        put("3338", new RoadStartID(13, 0));
        put("3439", new RoadStartID(13, 3));
        put("3943", new RoadStartID(13, 4));
        put("3843", new RoadStartID(13, 5));

        put("3540", new RoadStartID(14, 3));
        put("4044", new RoadStartID(14, 4));
        put("3944", new RoadStartID(14, 5));

        put("3641", new RoadStartID(15, 3));
        put("4145", new RoadStartID(15, 4));
        put("4045", new RoadStartID(15, 5));

        put("3742", new RoadStartID(16, 3));
        put("4246", new RoadStartID(16, 4));
        put("4146", new RoadStartID(16, 5));

        put("4347", new RoadStartID(17, 0));
        put("4448", new RoadStartID(17, 3));
        put("4851", new RoadStartID(17, 4));
        put("4751", new RoadStartID(17, 5));

        put("4549", new RoadStartID(18, 3));
        put("4952", new RoadStartID(18, 4));
        put("4852", new RoadStartID(18, 5));

        put("4650", new RoadStartID(19, 3));
        put("5043", new RoadStartID(19, 4));
        put("4953", new RoadStartID(19, 5));
    }};

    // Maps the Settlement index to a class that contains centre coordinates of the tile the settlement is in
    public static HashMap<String, SettlementStartID> hmSettlements = new HashMap<>() {{

        put("00", new SettlementStartID(0, 0));
        put("02", new SettlementStartID(2, 0));

        put("08", new SettlementStartID(4, 0));
        put("09", new SettlementStartID(5, 0));

        put("16", new SettlementStartID(7, 0));
        put("20", new SettlementStartID(12, 0));

        put("33", new SettlementStartID(7, 1));
        put("37", new SettlementStartID(12, 1));

        put("44", new SettlementStartID(14, 1));
        put("45", new SettlementStartID(15, 1));

        put("51", new SettlementStartID(17, 1));
        put("53", new SettlementStartID(19, 1));
    }};

    // Maps the City index to a class that contains centre coordinates of the tile the city is in
    public static HashMap<String, CityStartID> hmCities = new HashMap<>() {{

        put("01", new CityStartID(1, 0));

        put("07", new CityStartID(3, 0));
        put("10", new CityStartID(6, 0));

        put("17", new CityStartID(8, 0));
        put("18", new CityStartID(9, 0));
        put("19", new CityStartID(11, 0));

        put("34", new CityStartID(8, 1));
        put("35", new CityStartID(9, 1));
        put("36", new CityStartID(11, 1));

        put("43", new CityStartID(13, 1));
        put("46", new CityStartID(16, 1));

        put("52", new CityStartID(18, 1));
    }};
}
