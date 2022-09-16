package comp1140.ass2;

import java.util.HashMap;

public class Board {
    HashMap <Tile, GamePiece>[][] pieces;

    public Tile[] tiles;
    public Coordinate[] coords;

    public Settlement[] settlements;

    public void instatiateBoard() {
        this.coords = new Coordinate[54];
        this.tiles = new Tile[20];
        this.settlements = new Settlement[26];
        int tilenum=0;
        int settlenum = 0;
        Boolean cityable = false;
        Coordinate[] tilecoords = new Coordinate[6];
        int y = 0;
        int x = 1;
        int rowlen = 6;
        for (int c = 0; c <= 53; c++) {
            this.coords[c] = new Coordinate(x, y);
            if (x%2==1) {
                if (y<3) {
                    tilecoords[0] = new Coordinate(x, y);
                    tilecoords[1] = new Coordinate(x+1, y);
                    tilecoords[2] = new Coordinate(x+2, y+1);
                    tilecoords[3] = new Coordinate(x+1, y+1);
                    tilecoords[4] = new Coordinate(x, y+1);
                    tilecoords[5] = new Coordinate(x-1, y);
                    this.tiles[tilenum] = new Tile(new Player(""), tilecoords, tilenum);
                    tilenum++;
                    if (x==5 && y==2) {
                        this.tiles[tilenum] = new Tile(new Player(""), tilecoords, tilenum);
                        tilenum++;
                    }
                } else if (y>3) {
                    tilecoords[0] = new Coordinate(x, y);
                    tilecoords[1] = new Coordinate(x+1, y);
                    tilecoords[2] = new Coordinate(x+2, y-1);
                    tilecoords[3] = new Coordinate(x+1, y-1);
                    tilecoords[4] = new Coordinate(x, y-1);
                    tilecoords[5] = new Coordinate(x-1, y);
                    this.tiles[tilenum] = new Tile(new Player(""), tilecoords, tilenum);
                    tilenum++;
                }
                if ( ( (rowlen==6) && (x==3) ) || ( (rowlen==8) && (x==1||x==5) ) || ( (rowlen==10) && (x!=1&&x!=5) ) ) {
                    cityable = true;
                } else {
                    cityable = false;
                }
                this.settlements[settlenum] = new Settlement(new Player(""), new Coordinate(x, y), cityable, c);
            }
            if (y<3) {
                rowlen = 2*y+6;
            } else {
                rowlen = -2*y+16;
            }
            if (x==(rowlen/2)) {
                x = 0;
            } else if (x==rowlen) {
                x=1;
                y++;
            } else {
                x+=2;
            }
        }

    }

    public Tile getTile() {
        return null;
    }

    public GamePiece getGamePiece() {
        return null;
    }
}
