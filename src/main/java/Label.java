import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class Label extends javafx.scene.control.Label {

    public final static String BACKGROUND_IMAGE = "blue_button13.png";

    public Label(String text) {

        setPrefWidth(380);
        setPrefHeight(49);
        setAlignment(Pos.CENTER);
        setText(text);
        setWrapText(true);
        setLabelFont();
        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, 380, 49, false, true), BackgroundRepeat.NO_REPEAT,
                javafx.scene.layout.BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        setBackground(new Background(backgroundImage));


    }

    private void setLabelFont() {
            setFont(Font.font("Verdana", 23));

        }
    }


