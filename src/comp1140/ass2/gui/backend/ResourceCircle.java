package comp1140.ass2.gui.backend;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import comp1140.ass2.CatanDiceExtra;

import java.io.FileInputStream;
import java.io.InputStream;

import static comp1140.ass2.gui.backend.Constants.HEX_HEIGHT;

/**
 * Used to create the resource circle shape under the Knight
 */
class ResourceCircle extends Circle {
    double startX, startY;
    ResourceCircle(double startX, double startY, Boolean isUsed, int id){
        double centerX = startX;
        double centerY = startY + HEX_HEIGHT/5;
        double radius = HEX_HEIGHT/8;
        InputStream stream = null;
        try {
            stream = new FileInputStream("assets/resources/" + CatanDiceExtra.validateClass.Misc.coordinateToResource[id].toString() + ".png");
        }catch (Exception e){
            System.out.println(e.toString());
        }

        Image resourceImage = new Image(stream);

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadius(radius);

        if (isUsed){
            try {this.setFill(new ImagePattern(new Image(new FileInputStream("assets/resources/x.png"))));}
            catch (Exception e){System.out.println(e);}
            this.setOpacity(0.75);
        }
        else {
            this.setFill(new ImagePattern(resourceImage));
        }

        this.setStrokeWidth(2);
        this.setStrokeMiterLimit(10);
        this.setStrokeType(StrokeType.INSIDE);
        this.setStroke(Color.valueOf("0x000000"));
    }
}
