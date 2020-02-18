import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpaceButton extends javafx.scene.control.Button {

    private final String FONT_PATH = "kenvector_future.ttf";

    public SpaceButton(String text) throws FileNotFoundException {

        Image image = new Image("blue_button04.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        setBackground(background);
        setText(text);
        setFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle("blue_button5.jpg");
        initializeButtonListeners();
    }

    private void setFont() throws FileNotFoundException {

        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {

            setFont(Font.font("Verdana", 23));
        }

    }
     private void setButtonPressedStyle() {
        setStyle("blue_button4.jpg");
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
     }

    private void setButtonUnpressedStyle() {
        setStyle("blue_button5.jpg");
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initializeButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonUnpressedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    setEffect(null);
                }
        });


    }
}