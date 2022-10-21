package comp1140.ass2.gui.backend;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import static comp1140.ass2.gui.backend.Constants.HEX_WIDTH;

/**
 * Used to create a tile shape
 *
 * Authored by Arjun Raj, u7526852
 */
public class TileShape extends Polygon {
    double startX, startY;

    public TileShape(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
        this.setLayoutX(startX);
        this.setLayoutY(startY);

        this.addHexagonPoints();

        Color fillColour;
        Color strokeColour;
        fillColour = Color.DARKBLUE;
        strokeColour = Color.LIGHTBLUE;

        this.setStroke(strokeColour);
        this.setStrokeWidth(2.0);
        this.setStrokeType(StrokeType.INSIDE);
        this.setFill(fillColour);
//        this.setOnMouseClicked(
//                new EventHandler<MouseEvent>(){
//                    public void handle(MouseEvent e){
//                        System.out.println("HELLO");
//                    }
//                });
    }

    /**
     * Add the points necessary for each hexagon in the game.
     */
    private void addHexagonPoints() {
        double sideLength = HEX_WIDTH * 3 / 5;
        double bearing = 0;
        for (int i = 0; i < 6; i++) {
            double y = Math.cos(Math.PI / 18 * bearing) * sideLength;
            double x = Math.sin(Math.PI / 18 * bearing) * sideLength;
            this.getPoints().add(x);
            this.getPoints().add(y);
            bearing += 6;
        }
    }
}