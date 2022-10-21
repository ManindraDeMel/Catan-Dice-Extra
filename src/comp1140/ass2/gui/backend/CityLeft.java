package comp1140.ass2.gui.backend;

import comp1140.ass2.gui.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import static comp1140.ass2.gui.backend.Constants.*;

/**
 * Used to create the left half of a City shape
 */
class CityLeft extends Polygon {

    CityShape cityShape;
    boolean clicked = false;

    CityLeft (double startX, double startY, boolean isBuilt, int bottom, char player, CityShape cityShape){
        this.cityShape = cityShape;
        double sideLength = HEX_WIDTH * 3 / 5;
        int scale = 7;
        double centerX;
        double centerY;
        Double[] coordinates = new Double[]{
                0.0, 0.0,
                HEX_WIDTH/scale, 0.0,
                HEX_WIDTH/scale, -HEX_WIDTH/(scale+1),
                HEX_WIDTH/(scale*2), -1.3*(HEX_WIDTH/(scale+1)),
                0.0, -HEX_WIDTH/(scale+1)
        };

        this.getPoints().addAll(coordinates);

        if(bottom == 1){
            // down
            centerX = startX - (HEX_WIDTH/scale);
            centerY = startY + HEX_WIDTH/2 + (HEX_WIDTH/(scale+1));
        }else {
            // up
            centerX = startX - (HEX_WIDTH/scale);
            centerY = startY - HEX_HEIGHT/2 - (HEX_WIDTH/(scale+1))/2;
        }

        this.setLayoutX(centerX);
        this.setLayoutY(centerY);

        Color fillColour;
        Color strokeColour;
        if (isBuilt == false) {
            fillColour = Color.DARKGRAY;
            strokeColour = Color.LIGHTGRAY;
        }
        else {
            strokeColour = Color.BLUE;
            if (player == 'W')
                fillColour = playersColour[0];
            else
                fillColour = playersColour[1];
        }

        this.setStroke(strokeColour);
        this.setStrokeWidth(2.0);
        this.setStrokeType(StrokeType.INSIDE);
        this.setFill(fillColour);


        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!clicked) {
//                    setFill(Color.BROWN)
                    Game.BUILD = "build" + "S" + cityShape.id;
                    clicked = true;
                }
                else{
//                    Game.updateBuild();
                    clicked = false;
                }
            }
        });

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStrokeType(StrokeType.OUTSIDE);
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStrokeType(StrokeType.INSIDE);
            }
        });
    }
}
