package comp1140.ass2.gui.backend;

import comp1140.ass2.gui.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;

import static comp1140.ass2.gui.backend.Constants.HEX_WIDTH;
import static comp1140.ass2.gui.backend.Constants.playersColour;

/**
 * Used to create road shape
 *
 * Authored by Arjun Raj, u7526852
 */
public class RoadShape extends Rectangle {
    double height, width, startX, startY;
    int orientation;
    public String id;
    public boolean clicked = false;

    public RoadShape(double startX, double startY, boolean isBuilt, String id, int orientation, char player){
        this.startX = startX;
        this.startY = startY;
        this.orientation = orientation;
        this.id = id;

        double sideLength = HEX_WIDTH * 3 / 5;
        this.width = HEX_WIDTH/8;
        this.height = width*2.5;

        double centerX = startX-HEX_WIDTH/2 - width/2;
        double centerY = startY - (sideLength - height)/2;

        this.setX(centerX);
        this.setY(centerY);
        this.setWidth(this.width);
        this.setHeight(this.height);

        Rotate rotate = new Rotate();
        rotate.setPivotX(startX);
        rotate.setPivotY(startY);
        this.getTransforms().addAll(rotate);
        rotate.setAngle(30*this.orientation);
        this.getTransforms().add(rotate);

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
//                    setFill(Color.BROWN);
                    Game.BUILD = "build" + "R" + id;
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