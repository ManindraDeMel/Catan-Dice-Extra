package comp1140.ass2.gui.backend;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import static comp1140.ass2.gui.backend.Constants.HEX_HEIGHT;

/**
 * Used to create the resource circle shape under the Knight
 */
class ResourceCircle extends Circle {
    double startX, startY;
    ResourceCircle(double startX, double startY, Boolean isUsed){
        double centerX = startX;
        double centerY = startY + HEX_HEIGHT/5;
        double radius = HEX_HEIGHT/8;

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadius(radius);

        if (isUsed){
            this.setFill(Color.WHITE);
        }
        else {
            this.setFill(Color.DARKGRAY);
        }

        this.setStrokeWidth(2);
        this.setStrokeMiterLimit(10);
        this.setStrokeType(StrokeType.INSIDE);
        this.setStroke(Color.valueOf("0x000000"));
    }
}
