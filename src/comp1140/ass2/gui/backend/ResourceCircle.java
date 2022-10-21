package comp1140.ass2.gui.backend;

import comp1140.ass2.gui.Game;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
 *
 * Authored by Arjun Raj, u7526852
 */
public class ResourceCircle extends Circle {
    double startX, startY;
    public KnightShape knightShape;
    public boolean clicked = false;
    public int id;
    ResourceCircle(double startX, double startY, Boolean isUsed, int id, KnightShape knightShape){
        this.knightShape = knightShape;
        this.id = id;
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


        // Event Handlers
        this.setOnMouseClicked(mouseEvent -> {
            if (!clicked) {
                if (Game.RESOURCE_IN == null){
                    Game.RESOURCE_IN = this;
                    this.setStrokeType(StrokeType.OUTSIDE);
                    this.setOpacity(0.5);
                }else {
                    Game.RESOURCE_IN.setStrokeType(StrokeType.INSIDE);
                    Game.RESOURCE_IN.clicked = false;
                    Game.RESOURCE_IN.setOpacity(1);

                    Game.RESOURCE_IN = this;

                    this.setStrokeType(StrokeType.OUTSIDE);
                    this.setOpacity(0.5);
                }
                this.clicked = true;
            }
            else{
                clicked = false;
                this.setStrokeType(StrokeType.INSIDE);
                this.setOpacity(1);
            }

            System.out.println(CatanDiceExtra.validateClass.Misc.coordinateToResource[id].toString());
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
