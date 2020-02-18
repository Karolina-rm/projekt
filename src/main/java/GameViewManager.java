import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH =600;
    private static final int GAME_HEIGHT = 800;

    private Stage menuStage;
    private ImageView ship;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane;
    private GridPane gridPane2;
    private final static String BACKGROUND_IMAGE = "stars.jpg";

    private final static String BROWN_METEOR_IMAGE = "meteorBrown_med3.png";
    private final static String GREY_METEOR_IMAGE = "meteorGrey_med2.png";

    private ImageView[] brownMeteors;
    private ImageView[] greyMeteors;
    Random randomPositionGenerator;


    public GameViewManager() {
        initializeStage();
        createListeners();
        randomPositionGenerator = new Random();
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    private void createListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;

                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                }
            }

        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                }
            }


        });
    }

        public void createNewGame(Stage menuStage, SHIP choosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createShip(choosenShip);
        createGameElements();
        createGameLoop();
        gameStage.show();
        }

        private void createGameElements() {
            brownMeteors = new ImageView[4];
            for(int i=0; i<brownMeteors.length; i++) {
                brownMeteors[i] = new ImageView(BROWN_METEOR_IMAGE);
                setNewElementPosition(brownMeteors[i]);
                gamePane.getChildren().add(brownMeteors[i]);
            }

            greyMeteors = new ImageView[4];
            for(int i=0; i<greyMeteors.length; i++) {
                greyMeteors[i] = new ImageView(GREY_METEOR_IMAGE);
                setNewElementPosition(greyMeteors[i]);
                gamePane.getChildren().add(greyMeteors[i]);
            }
        }

        private void moveGameElements() {

            for(int i=0; i<brownMeteors.length; i++) {
                brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY()+7);
                brownMeteors[i].setRotate(brownMeteors[i].getRotate()+4);
            }

            for(int i=0; i<greyMeteors.length; i++) {
                greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY()+7);
                greyMeteors[i].setRotate(greyMeteors[i].getRotate()+4);
            }
        }

        private void checkIfElementsAreUnderTheScreen() {

            for(int i=0; i<brownMeteors.length; i++) {
                if(brownMeteors[i].getLayoutY() > 900) {
                    setNewElementPosition(brownMeteors[i]);
                }
            }

            for(int i=0; i<greyMeteors.length; i++) {
                if(greyMeteors[i].getLayoutY() > 900) {
                    setNewElementPosition(greyMeteors[i]);
                }
            }
        }

        private void setNewElementPosition(ImageView image) {
            image.setLayoutX(randomPositionGenerator.nextInt(370));
            image.setLayoutY(-(randomPositionGenerator.nextInt(3200)+600));

        }

        private void createShip(SHIP choosenShip) {
        ship = new ImageView(choosenShip.getUrl());
        ship.setLayoutX(GAME_WIDTH /4);
        ship.setLayoutY(GAME_HEIGHT - 190);
        gamePane.getChildren().add(ship);
        }

        private void createGameLoop() {
            gameTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    moveBackground();
                    moveGameElements();
                    checkIfElementsAreUnderTheScreen();
                    moveShip();
                }
            };

            gameTimer.start();
        }

        private void moveShip() {

            if (isLeftKeyPressed && !isRightKeyPressed) {
                if(angle > -30) {
                    angle -= 5;
                }
                ship.setRotate(angle);
                if(ship.getLayoutX() > -20) {
                    ship.setLayoutX(ship.getLayoutX() -3);
                }
            }
            if (isRightKeyPressed && !isLeftKeyPressed) {
                if(angle < 30) {
                    angle += 5;
                }
                ship.setRotate(angle);
                if(ship.getLayoutX() < 522) {
                    ship.setLayoutX(ship.getLayoutX() +3);
                }

            }

            if (isRightKeyPressed && isLeftKeyPressed) {
                if(angle < 0) {
                    angle +=5;
                } else if(angle >0) {
                    angle -=5;
                }
                ship.setRotate(angle);

            }

            if (!isLeftKeyPressed && !isRightKeyPressed) {
                if(angle < 0) {
                    angle +=5;
                } else if(angle >0) {
                    angle -=5;
                }
                ship.setRotate(angle);

            }
        }

        private void createBackground() {
            gridPane = new GridPane();
            gridPane2 = new GridPane();

            for (int i =0; i<12; i++) {
                ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
                ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
                GridPane.setConstraints(backgroundImage1, i%3, i/3);
                GridPane.setConstraints(backgroundImage2, i%3, i/3);
                gridPane.getChildren().add(backgroundImage1);
                gridPane2.getChildren().add(backgroundImage2);
            }

            gridPane2.setLayoutY(-1024);
            gamePane.getChildren().addAll(gridPane,gridPane2);
        }

        private void moveBackground() {
            gridPane.setLayoutY(gridPane.getLayoutY()+ 0.5);
            gridPane2.setLayoutY(gridPane2.getLayoutY()+ 0.5);

            if(gridPane.getLayoutY() >= 1024) {
                gridPane.setLayoutY(-1024);
            }

            if(gridPane2.getLayoutY() >= 1024) {
                gridPane2.setLayoutY(-1024);
            }

        }
    }


