import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox {

    private ImageView circleImage;
    private ImageView shipImage;

    private String circleNotChoosen = "grey_circle.png";
    private String circleChoosen = "blue_boxTick.png";

    private SHIP ship;
    private boolean isCircle;

    public ShipPicker(SHIP ship) {
        circleImage = new ImageView(circleNotChoosen);
        shipImage = new ImageView(ship.getUrl());
        this.ship = ship;
        isCircle = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(shipImage);

    }

    public SHIP getShip() {
        return ship;
    }


    public void setIsCircle(boolean isCircle) {
        this.isCircle = isCircle;
        String imageToSet = this.isCircle ? circleChoosen : circleNotChoosen;
        circleImage.setImage(new Image(imageToSet));
    }
}


