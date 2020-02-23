import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    public Stage gameStage;

    private static final int GAME_WIDTH =600;
    private static final int GAME_HEIGHT = 800;

    private Stage menuStage;
    private ImageView ship;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean saveGame;
    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane;
    private GridPane gridPane2;
    private final static String BACKGROUND_IMAGE = "stars.jpg";

    private final static String BROWN_METEOR_IMAGE = "meteorBrown_med3.png";
    private final static String GREY_METEOR_IMAGE = "meteorGrey_med2.png";

    private ImageView[] brownMeteors;
    private ImageView[] greyMeteors;
    private Random randomPositionGenerator;

    private ImageView star;
    private Points pointsBox;
    private ImageView[] lifes;
    private int life;
    private int points;
    private final static String GREY_STAR_IMAGE = "star_silver.png";

    private final static int STAR_RADIUS = 12;
    private final static int SHIP_RADIUS = 27;
    private final static int METEOR_RADIUS = 20;


    public GameViewManager() {
        initializeStage();
        createListeners();
        randomPositionGenerator = new Random();
    }

    public void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    private void createListeners() {
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                isLeftKeyPressed = true;

            } else if (event.getCode() == KeyCode.RIGHT) {
                isRightKeyPressed = true;

            } else if (event.getCode() == KeyCode.DOWN) {
                saveGame = true;
            }
        });

        gameScene.setOnKeyReleased(event-> {
                if(event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                } else if (event.getCode() == KeyCode.DOWN) {
                    saveGame = false;
                }

        });
    }

        public void createNewGame(Stage menuStage, SHIP choosenShip) throws FileNotFoundException {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createShip(choosenShip);
        createGameElements(choosenShip);
        createGameLoop();
        gameStage.show();
        }

        private void createGameElements(SHIP choosenShip) throws FileNotFoundException {
        life = 2;
        star = new ImageView(GREY_STAR_IMAGE);
        setNewElementPosition(star);
        gamePane.getChildren().add(star);
        pointsBox = new Points("POINTS: 0");
        pointsBox.setLayoutX(460);
        pointsBox.setLayoutY(20);
        gamePane.getChildren().add(pointsBox);
        lifes = new ImageView[3];

        for(int i=0; i < lifes.length; i++) {
            lifes[i] = new ImageView(choosenShip.getUrlLife());
            lifes[i].setLayoutX(455+(i*50));
            lifes[i].setLayoutY(80);
            gamePane.getChildren().add(lifes[i]);
            }

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

            star.setLayoutY(star.getLayoutY() +5);

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

            if(star.getLayoutY() > 1200) {
                setNewElementPosition(star);
            }

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
                    checkCollision();
                    moveShip();
                    savingGame();
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

    private void savingGame() {
        if (saveGame) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("SavedGame.txt"));
                bw.write("" + points);
                bw.newLine();
                bw.write("" + life);
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            gameStage.close();
            menuStage.show();
        }

    }

    public void loadData() throws FileNotFoundException {

        try {
            BufferedReader br = new BufferedReader(new FileReader("SavedGame.txt"));
            points = Integer.parseInt(br.readLine());
            life = Integer.parseInt(br.readLine());

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createNewGame(menuStage, SHIP.BLUE);
    }


        private void createBackground() {
            gridPane = new GridPane();
            gridPane2 = new GridPane();

            for (int i =0; i<16; i++) {
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

        private void checkCollision() {
            if (SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX() + 49, star.getLayoutX() + 15, ship.getLayoutY() + 37, star.getLayoutY() + 15))
                ;
            { setNewElementPosition(star);
            points++;
            String textToSet = "POINTS: ";
            if(points <10) {
                textToSet = textToSet + "0";
            }
            pointsBox.setText(textToSet+points);
            }

            for(int i=0; i <brownMeteors.length; i++) {
                if (METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX()+49, brownMeteors[i].getLayoutX()+20, ship.getLayoutY()+37, brownMeteors[i].getLayoutY()+20)){
                    removeLife();
                    setNewElementPosition(brownMeteors[i]);
                }
            }

            for(int i=0; i <greyMeteors.length; i++) {
                if (METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX()+49, greyMeteors[i].getLayoutX()+20, ship.getLayoutY()+37, greyMeteors[i].getLayoutY()+20)){
                    removeLife();
                    setNewElementPosition(greyMeteors[i]);
                }
            }
        }

        private void removeLife() {
            gamePane.getChildren().remove(lifes[life]);
            life--;
            if (life < 0) {
                gameStage.close();
                gameTimer.stop();
                menuStage.show();
            }
        }

        private double calculateDistance(double a1, double a2, double b1, double b2) {
            return Math.sqrt(Math.pow(a1-a2, 2) + Math.pow(b1-b2, 2));
        }




    }


