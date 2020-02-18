import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View {

    private static final int WIDTH = 768;
    private static final int HEIGHT = 1024;
    private final static int BUTTONS_MENU_START_X = 100;
    private final static int BUTTONS_MENU_START_Y = 200;

    private SpaceSubscene helpSubScene;
    private SpaceSubscene rankingSubScene;
    private SpaceSubscene shipPickerSubScene;

    private SpaceSubscene sceneToHide;

    ArrayList<SpaceButton> menu;

    ArrayList<ShipPicker> shipList;
    private SHIP choosenShip;


    private AnchorPane pane;
    private Scene scene;
    private Stage stage;

    public View() throws FileNotFoundException {
        menu = new ArrayList<>();
        pane = new AnchorPane();
        scene = new Scene(pane, WIDTH, HEIGHT);
        stage = new Stage();
        stage.setScene(scene);
        stage.setHeight(1000);
        stage.setWidth(1100);
        createSubScenes();
        createButtons();
        createBackgroundScene();
        createLogo();
        /*SpaceSubscene subScene = new SpaceSubscene();
        subScene.setLayoutX(100);
        subScene.setLayoutY(100);
        pane.getChildren().add(subScene);*/

    }

    private void createSubScenes() throws FileNotFoundException {
        helpSubScene = new SpaceSubscene();
        pane.getChildren().add(helpSubScene);

        rankingSubScene = new SpaceSubscene();
        pane.getChildren().add(rankingSubScene);

        createShipPickerSubscene();

    }

    private void createShipPickerSubscene() throws FileNotFoundException {
        shipPickerSubScene = new SpaceSubscene();
        pane.getChildren().add(shipPickerSubScene);

        Label chooseShipLabel = new Label("CHOOSE YOUR SHIP");
        chooseShipLabel.setLayoutX(110);
        chooseShipLabel.setLayoutY(25);
        shipPickerSubScene.getPane().getChildren().add(chooseShipLabel);
        shipPickerSubScene.getPane().getChildren().add(createShips());
        shipPickerSubScene.getPane().getChildren().add(createButtonPlay());
    }

    private HBox createShips() {

        HBox box = new HBox();
        box.setSpacing(20);
        shipList = new ArrayList<>();
        for(SHIP ship : SHIP.values()) {
            ShipPicker shipToPick = new ShipPicker(ship);
            shipList.add(shipToPick);
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for(ShipPicker ship : shipList) {
                        ship.setIsCircle(false);}
                    shipToPick.setIsCircle(true);
                    choosenShip = shipToPick.getShip();
                }
            });
        }
        box.setLayoutX(300 - (118*2));
        box.setLayoutY(100);
        return box;
    }

    private SpaceButton createButtonPlay() throws FileNotFoundException {
        SpaceButton playButton = new SpaceButton("PLAY");
        playButton.setLayoutX(350);
        playButton.setLayoutY(300);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(choosenShip != null) {
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(stage, choosenShip);
                }
            }
        });

        return playButton;
    }



    private void showSubscene(SpaceSubscene subScene) {
        if(sceneToHide != null) {
            sceneToHide.moveSpaceSubscene();
        }

        subScene.moveSpaceSubscene();
        sceneToHide = subScene;

    }

    public Stage getStage() {
        return stage;
    }

    private void addMenu(SpaceButton button) {

        button.setLayoutX(BUTTONS_MENU_START_X);
        button.setLayoutY(BUTTONS_MENU_START_Y + (menu.size() * 100));
        menu.add(button);
        pane.getChildren().add(button);

    }

    public void createButtons() throws FileNotFoundException {
        createButtonStart();
        createButtonHelp();
        createButtonRanking();
        createButtonExit();
    }

    public void createButtonStart() throws FileNotFoundException {
        SpaceButton startButton = new SpaceButton("Start!");
        addMenu(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(shipPickerSubScene);
            }
        });
    }

    public void createButtonHelp() throws FileNotFoundException {
        SpaceButton helpButton = new SpaceButton("Help");
        addMenu(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(helpSubScene);
            }
        });
    }

    public void createButtonRanking() throws FileNotFoundException {
        SpaceButton rankingButton = new SpaceButton("Ranking");
        addMenu(rankingButton);

        rankingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(rankingSubScene);
            }
        });
    }

    public void createButtonExit() throws FileNotFoundException {
        SpaceButton exitButton = new SpaceButton("Exit");
        addMenu(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }




        /*SpaceButton button = new SpaceButton("Click!");
        pane.getChildren().add(button);
        button.setLayoutX(200);
        button.setLayoutY(200); */

    private void createBackgroundScene() {
        Image image = new Image("stars.jpg", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        pane.setBackground(new Background(background));
    }

    private void createLogo() {
        /*Text t = new Text();
        t.setText("SPACE RUNNER");
        t.setFont((Font.loadFont(getClass().getResourceAsStream("kenvector_future.ttf"), 23)));
        t.setFill(Color.DARKBLUE);*/
        ImageView logo = new ImageView("logo1.png");
        logo.setLayoutX(200);
        logo.setLayoutY(0);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);

            }
        });


        pane.getChildren().add(logo);
    }
}