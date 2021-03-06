import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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

    }

    private void createSubScenes() throws FileNotFoundException {
        rankingSubScene = new SpaceSubscene();
        pane.getChildren().add(rankingSubScene);

        createShipPickerSubscene();
        createHelpSubscene();

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

    private void createHelpSubscene() {
        helpSubScene = new SpaceSubscene();
        pane.getChildren().add(helpSubScene);
        Text t = new Text();
        t.setText("How to play?");
        t.setFont(Font.font("Verdana", 20));
        t.setLayoutX(240);
        t.setLayoutY(50);
        Text t1 = new Text();
        t1.setText("If you want to start a game you have to press" + "\n" +"Start button, then choose your ship" + "\n" + "and press Play." + "\n" + "\n"+
        "If you want to move your ship" + "\n" + "use left and right arrow keys." + "\n" + "\n" +
                "If you want to save a game press down arrow key.");
        t1.setFont(Font.font("Verdana", 20));
        t1.setLayoutX(40);
        t1.setLayoutY(120);
        helpSubScene.getPane().getChildren().add(t);
        helpSubScene.getPane().getChildren().add(t1);


    }

    private HBox createShips() {

        HBox box = new HBox();
        box.setSpacing(20);
        shipList = new ArrayList<>();
        for (SHIP ship : SHIP.values()) {
            ShipPicker shipToPick = new ShipPicker(ship);
            shipList.add(shipToPick);
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (ShipPicker ship : shipList) {
                        ship.setIsCircle(false);
                    }
                    shipToPick.setIsCircle(true);
                    choosenShip = shipToPick.getShip();
                }
            });
        }
        box.setLayoutX(300 - (118 * 2));
        box.setLayoutY(100);
        return box;
    }

    private SpaceButton createButtonPlay(){
        SpaceButton playButton = new SpaceButton("PLAY");
        playButton.setLayoutX(350);
        playButton.setLayoutY(300);

        playButton.setOnAction(event -> {
                if (choosenShip != null) {
                    GameViewManager gameManager = new GameViewManager();
                    try {
                        gameManager.createNewGame(stage, choosenShip);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

        });

        return playButton;
    }


    private void showSubscene(SpaceSubscene subScene) {
        if (sceneToHide != null) {
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
        createButtonContinue();
        createButtonHelp();
        createButtonExit();
    }

    public void createButtonStart() {
        SpaceButton startButton = new SpaceButton("Start!");
        addMenu(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(shipPickerSubScene);
            }
        });
    }

    public void createButtonContinue() {
        SpaceButton continueButton = new SpaceButton("Continue");
        addMenu(continueButton);

        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            GameViewManager gm = new GameViewManager();
                try {
                    gm.loadData();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void createButtonHelp(){
        SpaceButton helpButton = new SpaceButton("Help");
        addMenu(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(helpSubScene);

            }
        });
    }

    public void createButtonExit(){
        SpaceButton exitButton = new SpaceButton("Exit");
        addMenu(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }

    private void createBackgroundScene() {
        Image image = new Image("stars.jpg", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        pane.setBackground(new Background(background));
    }

    private void createLogo() {
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

