package comp1140.ass2.gui.backend;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import static comp1140.ass2.gui.backend.Constants.HEX_HEIGHT;
import static comp1140.ass2.gui.backend.Constants.playersColour;

/**
 * Used to create a Knight's Head shape
 */
class KnightHead extends Circle {
    double startX, startY;
    KnightHead(double startX, double startY, boolean isBuilt, char player) {
        this.startX = startX;
        this.startY = startY;

        double centerX = startX;
        double centerY = startY - HEX_HEIGHT/5;
        double radius = HEX_HEIGHT/15;

        this.setLayoutX(startX);
        this.setLayoutY(centerY);
        this.setRadius(radius);

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

        // Event Handlers
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
