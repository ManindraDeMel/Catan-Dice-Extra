package comp1140.ass2.gui.backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CurrentResource {
    public char resourceChar;
    public ImageView imageView;
    public boolean selected = true;

    public CurrentResource(char resource) throws FileNotFoundException {
        this.resourceChar = resource;

        InputStream stream = new FileInputStream("assets/resources/" + resource + ".png");
        Image image = new Image(stream);
        imageView = new ImageView(image);
        imageView.setScaleX(0.75);
        imageView.setScaleY(0.75);



        imageView.setOnMouseClicked(mouseEvent -> {
            if (selected){
                selected = false;
                imageView.setOpacity(0.25);
            }
            else{
                selected = true;
                imageView.setOpacity(1);
            }
            System.out.println(selected);
        });
    }
}
