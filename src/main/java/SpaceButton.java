import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

public class SpaceButton extends javafx.scene.control.Button {


    public SpaceButton(String text) {

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

    private void setFont(){
            setFont(Font.font("Verdana", 23));
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
        setOnMousePressed(event ->  {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
        });

        setOnMouseReleased(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonUnpressedStyle();
                }
        });

        setOnMouseEntered(event -> {
                setEffect(new DropShadow());

        });

        setOnMouseExited(event -> {
                    setEffect(null);

        });


    }
}