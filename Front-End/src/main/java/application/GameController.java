package application;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

class GameController {

    private static final String easyAlienImageURL = "/images/ship1.png";
    private static final String mediumAlienImageURL = "/images/ship2.png";
    private static final String hardAlienImageURL = "/images/ship3.png";

    private static final String playerShipImageURL = "/images/playerShip.png";

    private static final String mainMenuURL = "/fxml/mainMenu.fxml";

    private static final Integer widthOfScreen = 600;
    private static final Integer heightOfScreen = 800;


    private String userId;

    private Pane root = new Pane();

    private double timeCounter = 0;

    private Integer currentLevel = 0;

    private Integer enemyCount = 0;

    private Integer score = 0;

    private Stage mainStage = new Stage();

    private Item player = new Item(270, 740, 60, 60, "player");


    private Image level1Image = new Image(getClass().getResourceAsStream("/images/level1.png"));
    private Image level2Image = new Image(getClass().getResourceAsStream("/images/level2.png"));
    private Image level3Image = new Image(getClass().getResourceAsStream("/images/level3.png"));

    private Label levelLabel = new Label();

    private Label scoreLabel = new Label();

    private Label healthLabel = new Label();

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update();
        }
    };

    void playGame(Stage stage, String id) throws IOException {
        System.out.println(id);
        userId = id;
        root.getChildren().remove(0, root.getChildren().size());
        currentLevel = 1;

        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.A) {
                if (player.getTranslateX() > 0)
                    player.moveLeft();
            } else if (key.getCode() == KeyCode.W) {
                if (player.getTranslateY() > 0)
                    player.moveUp();
            } else if (key.getCode() == KeyCode.D) {
                if (player.getTranslateX() + 40 < widthOfScreen)
                    player.moveRight();
            } else if (key.getCode() == KeyCode.S) {
                if (player.getTranslateY() + 40 < heightOfScreen)
                    player.moveDown();
            } else if (key.getCode() == KeyCode.SPACE) {
                shoot(player);
            }

        });

        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }

    private Parent createContent() throws IOException {
        root.setPrefSize(600, 800);
        root.getChildren().add(player);
        Image img = new Image(playerShipImageURL);
        player.setFill(new ImagePattern(img));
        levelLabel.relocate(0, 0);
        levelLabel.setGraphic(new ImageView(level1Image));

        scoreLabel.relocate(520, 30);
        scoreLabel.setText("Score : " + score.toString());

        healthLabel.relocate(220, 30);
        healthLabel.setText("Health : " + player.getHealth().toString() + "HP");

        root.getChildren().addAll(levelLabel, scoreLabel, healthLabel);


        nextLevelPreparer(currentLevel);


        timer.start();


        return root;
    }

    private void nextLevelPreparer(Integer level) throws IOException {

        if (level == 1) {
            createEasyAlien();
            enemyCount = 5;
        } else if (level == 2) {
            levelLabel.setGraphic(new ImageView(level2Image));
            createEasyAlien();
            createMediumAlien();
            enemyCount = 11;
        } else if (level == 3) {
            levelLabel.setGraphic(new ImageView(level3Image));
            createEasyAlien();
            createMediumAlien();
            createHardAlien();
            enemyCount = 16;
        } else {
            gameOverScene();
        }


    }

    private void createHardAlien() {
        for (int i = 0; i < 5; i++) {
            Item s = new Item(75 + i * 100, 200, 50, 50, "enemy");
            root.getChildren().add(s);
            Image img = new Image(hardAlienImageURL);
            s.setFill(new ImagePattern(img));
        }
    }

    private void createMediumAlien() {
        for (int i = 0; i < 6; i++) {
            Item s = new Item(25 + i * 100, 150, 50, 50, "enemy");
            root.getChildren().add(s);
            Image img = new Image(mediumAlienImageURL);
            s.setFill(new ImagePattern(img));
        }
    }

    private void createEasyAlien() {
        for (int i = 0; i < 5; i++) {
            Item s = new Item(75 + i * 100, 100, 50, 50, "enemy");
            root.getChildren().add(s);
            Image img = new Image(easyAlienImageURL);
            s.setFill(new ImagePattern(img));
        }
    }

    private List<Item> items() {
        List<Object> itemList = root.getChildren().stream().filter(n -> n.getClass().getName().contains("Item")).collect(Collectors.toList());

        return itemList.stream().map(n -> (Item) n).collect(Collectors.toList());
    }


    private void update() {
        timeCounter += 0.016;
        items().forEach(item -> {
            if (item.type.equals("enemybullet")) {

                item.moveDownBullet();

                if (item.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    player.health -= 20;
                    healthLabel.setText("Health : " + player.getHealth().toString() + "HP");
                    if (player.health <= 0) {
                        player.dead = true;
                        try {
                            gameOverScene();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    item.dead = true;
                }
                if (item.getTranslateY() > 800) {
                    item.dead = true;
                }
            } else if (item.type.equals("playerbullet")) {
                item.moveUp();

                items().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                    if (item.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        enemy.dead = true;
                        item.dead = true;
                        enemyCount--;
                        score += 10;
                        scoreLabel.setText("Score : " + score.toString());
                        nextLevel();
                    }
                    if (item.getTranslateY() < 0) {
                        item.dead = true;
                    }
                });

            } else if (item.type.equals("player")) {

                items().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                    if (item.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        enemy.dead = true;
                        enemyCount--;
                        score += 10;
                        scoreLabel.setText("Score : " + score.toString());
                        nextLevel();
                    }
                });

            } else if (item.type.equals("enemy")) {
                if (timeCounter > 1) {
                    if (Math.random() < 0.5) {
                        shoot(item);
                    }
                }

            }

        });

        root.getChildren().

                removeIf(n ->

                {
                    if (n.getClass().getName().contains("Item")) {
                        Item s = (Item) n;
                        return s.dead;
                    } else {
                        return false;
                    }
                });

        if (timeCounter > 1) {
            timeCounter = 0;
        }

    }

    private void nextLevel() {
        if (enemyCount == 0) {
            currentLevel++;
            try {
                nextLevelPreparer(currentLevel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void shoot(Item item) {
        if (item.type.equals("player")) {

            Item s = new Item((int) item.getTranslateX() + 30, (int) item.getTranslateY() + 30, 5, 10, item.type + "bullet", Color.ORANGE);
            root.getChildren().add(s);

        } else {
            Item s = new Item((int) item.getTranslateX() + 25, (int) item.getTranslateY() + 25, 5, 10, item.type + "bullet", Color.PURPLE);
            root.getChildren().add(s);

        }

    }


    private void gameOverScene() throws IOException {

        timer.stop();

        Window owner = mainStage.getScene().getWindow();

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Game is Over!",
                "Your Score is " + score.toString() + "!");

        RestServiceConsumer restServiceConsumer = new RestServiceConsumer();
        restServiceConsumer.addScore(userId, score.toString());


        Parent mainMenu = FXMLLoader.load(getClass().getResource(mainMenuURL));

        mainMenu.setId(userId);

        Scene mainMenuScene = new Scene(mainMenu);

        mainStage.setScene(mainMenuScene);

        mainStage.show();


    }

}