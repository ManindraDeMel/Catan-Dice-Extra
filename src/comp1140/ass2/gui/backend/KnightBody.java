package comp1140.ass2.gui.backend;

import comp1140.ass2.gui.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import static comp1140.ass2.gui.backend.Constants.HEX_HEIGHT;
import static comp1140.ass2.gui.backend.Constants.playersColour;

/**
 * Used to create a Knight's Body shape
 *
 * Authored by Arjun Raj, u7526852
 */
class KnightBody extends Rectangle {

    double startX, startY;
    int id;
    public boolean clicked = false;
    KnightShape knightShape;

    KnightBody(double startX, double startY, boolean isBuilt, char player, KnightShape knightShape) {
        this.knightShape = knightShape;
        this.startX = startX;
        this.startY = startY;

        double centerX = startX;
        double centerY = startY - HEX_HEIGHT/5;
        double radius = HEX_HEIGHT/15;

        double width = radius*2.3;
        double height = radius*3.5;

        this.setX(centerX - width/2);
        this.setY(centerY + radius*0.7);
        this.setWidth(width);
        this.setHeight(height);

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
                // TODO EXTRA
                if (!clicked) {
//                    setFill(Color.BROWN)
                    Game.BUILD = "build" + (knightShape.isUsed? "K": "J") + knightShape.id;
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
