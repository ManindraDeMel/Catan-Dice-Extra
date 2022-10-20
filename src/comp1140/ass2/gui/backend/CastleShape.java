package comp1140.ass2.gui.backend;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import static comp1140.ass2.gui.backend.Constants.*;

/**
 * Used to create a Castle shape
 */
public class CastleShape extends Polygon {
    public int id;
    double centerX;
    double centerY;

    public CastleShape(boolean isBuilt, int id, char player){
        double scale = 3.0;
        double right = 4.65;
        double top = 4.4;
        this.id = id;

        switch (this.id){
            // Top Right
            case 0 -> {
                centerX = HEX_WIDTH * right;
                centerY = 0;
            }
            // Bottom Right
            case 1 -> {
                centerX = HEX_WIDTH * right;
                centerY = HEX_HEIGHT * top;
            }
            // Bottom Left
            case 2 -> {
                centerX = 0;
                centerY = HEX_HEIGHT * top;
            }
            // Top Left
            case 3 -> {
                centerX = 0;
                centerY = 0;
            }
        }

        // Defining coordinates for the shape
        Double[] coordinates = new Double[]{
                0.0, 0.0,
                HEX_WIDTH/scale, 0.0,
                HEX_WIDTH/scale, -HEX_WIDTH/(scale+1),
                (HEX_WIDTH/scale)*0.75, -HEX_WIDTH/(scale+1),
                (HEX_WIDTH/scale)*0.75, -HEX_WIDTH/(scale-0.2),
                HEX_WIDTH/(scale*2), -(HEX_WIDTH/(scale-0.75)), // top nose coordinate
                HEX_WIDTH/scale - (HEX_WIDTH/scale)*0.75, -HEX_WIDTH/(scale-0.2),
                HEX_WIDTH/scale - (HEX_WIDTH/scale)*0.75, -HEX_WIDTH/(scale+1),
                0.0, -HEX_WIDTH/(scale+1)
        };

        // Setting coordinates
        this.getPoints().addAll(coordinates);
        this.setLayoutX(centerX);
        this.setLayoutY(centerY);

        // Setting colour
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
                System.out.println(id);
                //TODO
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
