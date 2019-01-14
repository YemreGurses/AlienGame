package application.controller;

import application.consumer.RestServiceConsumer;
import application.entity.Item;
import application.entity.Player;
import application.entity.PlayerMixIn;
import application.helper.AlertHelper;
import ceng453.entity.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final String EASY_ALIEN_IMAGE_URL = "/images/ship1.png";
    private static final String MEDIUM_ALIEN_IMAGE_URL = "/images/ship2.png";
    private static final String HARD_ALIEN_IMAGE_URL = "/images/ship3.png";
    private static final String FINAL_ALIEN_IMAGE_URL = "/images/ship4.png";


    private static final String PLAYER_SHIP_IMAGE_URL = "/images/playerShip.png";

    private static final String MAIN_MENU_URL = "/fxml/mainMenu.fxml";

    private static final Integer WIDTH_OF_SCREEN = 600;
    private static final Integer HEIGHT_OF_SCREEN = 800;


    private String userId;

    private Pane root = new Pane();

    private double timeCounter = 0;

    private Integer currentLevel = 0;

    private Integer enemyCount = 0;

    private Integer score = 0;

    private Integer pattern = 0;

    private Stage mainStage = new Stage();

    private Item player = new Item(270, 740, 60, 60, "player");

    private Item finalBoss = new Item(20, 100, 560, 240, "enemy");


    private Image level1Image = new Image(getClass().getResourceAsStream("/images/level1.png"));
    private Image level2Image = new Image(getClass().getResourceAsStream("/images/level2.png"));
    private Image level3Image = new Image(getClass().getResourceAsStream("/images/level3.png"));

    private Label levelLabel = new Label();

    private Label scoreLabel = new Label();

    private Label bossHealthLabel = new Label();


    private Label healthLabel = new Label();

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update();
        }
    };

    /**
     * This method creates content for game and set Scene for the game.
     *
     * @param stage Stage of the game
     * @param id    Id of active User
     * @throws IOException It throws an exception if it is not load to FXML
     */
    void playGame(Stage stage, String id) throws IOException {
        userId = id;
        root.getChildren().remove(0, root.getChildren().size());
        currentLevel = 4;

        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.A) {
                if (player.getTranslateX() > 0)
                    player.moveLeft();
            } else if (key.getCode() == KeyCode.W) {
                if (player.getTranslateY() > 0)
                    player.moveUp();
            } else if (key.getCode() == KeyCode.D) {
                if (player.getTranslateX() + 40 < WIDTH_OF_SCREEN)
                    player.moveRight();
            } else if (key.getCode() == KeyCode.S) {
                if (player.getTranslateY() + 40 < HEIGHT_OF_SCREEN)
                    player.moveDown();
            } else if (key.getCode() == KeyCode.SPACE) {
                shoot(player);
            }

        });

        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }

    /**
     * This method creates labels for game screen and starts timer for the game.
     *
     * @return Parent to set the scene
     * @throws IOException It throws an exception if it is not load to FXML
     */
    private Parent createContent() throws IOException {
        root.setPrefSize(600, 800);
        root.getChildren().add(player);
        Image img = new Image(PLAYER_SHIP_IMAGE_URL);
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

    /**
     * This method creates game objects for given level.
     *
     * @param level Level to prepare
     * @throws IOException It throws an exception if it is not load to FXML
     */
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
        } else if (level == 4) {
            root.getChildren().remove(levelLabel);
            createFinalAlien();
            bossHealthLabel.relocate(20, 30);
            bossHealthLabel.setText("Boss Health : " + finalBoss.getHealth().toString() + "HP");
            root.getChildren().add(bossHealthLabel);
            enemyCount = 1;
        } else {
            gameOverScene();
        }


    }

    /**
     * This method creates final boss.
     */
    private void createFinalAlien() {
        finalBoss.health = 500;
        root.getChildren().add(finalBoss);
        Image img = new Image(FINAL_ALIEN_IMAGE_URL);
        finalBoss.setFill(new ImagePattern(img));
    }


    /**
     * This method creates hard aliens.
     */
    private void createHardAlien() {
        for (int i = 0; i < 5; i++) {
            Item s = new Item(75 + i * 100, 200, 50, 50, "enemy");
            root.getChildren().add(s);
            Image img = new Image(HARD_ALIEN_IMAGE_URL);
            s.setFill(new ImagePattern(img));
        }
    }

    /**
     * This method creates medium aliens.
     */
    private void createMediumAlien() {
        for (int i = 0; i < 6; i++) {
            Item s = new Item(25 + i * 100, 150, 50, 50, "enemy");
            root.getChildren().add(s);
            Image img = new Image(MEDIUM_ALIEN_IMAGE_URL);
            s.setFill(new ImagePattern(img));
        }
    }

    /**
     * This method creates easy aliens.
     */
    private void createEasyAlien() {
        for (int i = 0; i < 5; i++) {
            Item s = new Item(75 + i * 100, 100, 50, 50, "enemy");
            root.getChildren().add(s);
            Image img = new Image(EASY_ALIEN_IMAGE_URL);
            s.setFill(new ImagePattern(img));
        }
    }

    /**
     * This method gets all game objects and return them as a list.
     *
     * @return List of items
     */
    private List<Item> items() {
        List<Object> itemList = root.getChildren().stream().filter(n -> n.getClass().getName().contains("Item")).collect(Collectors.toList());

        return itemList.stream().map(n -> (Item) n).collect(Collectors.toList());
    }

    /**
     * This method updates game screen with timer and creates random fires from aliens and also remove objects when they die.
     */
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
                        if (currentLevel == 4) {
                            finalBoss.health -= 20;
                            item.dead = true;
                            bossHealthLabel.setText("Health : " + finalBoss.getHealth().toString() + "HP");
                            if (finalBoss.health <= 0) {
                                enemyCount--;
                                finalBoss.dead = true;
                                nextLevel();
                            }
                        } else {
                            enemy.dead = true;
                            item.dead = true;
                            enemyCount--;
                            score += 10;
                            scoreLabel.setText("Score : " + score.toString());
                            nextLevel();
                        }
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
                if (currentLevel == 4) {
                    if (timeCounter > 1) {
                        bossShoot();
                    }
                } else {
                    if (timeCounter > 1) {
                        if (Math.random() < 0.5) {
                            shoot(item);
                        }
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

    /**
     * This method gets next level.
     */
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

    /**
     * This method creates fires for boss with a pattern which we created
     */
    private void bossShoot() {
        Item s1 = new Item((int) finalBoss.getTranslateX() + 40, (int) finalBoss.getTranslateY() + 160, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s2 = new Item((int) finalBoss.getTranslateX() + 80, (int) finalBoss.getTranslateY() + 80, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s3 = new Item((int) finalBoss.getTranslateX() + 120, (int) finalBoss.getTranslateY() + 160, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s4 = new Item((int) finalBoss.getTranslateX() + 160, (int) finalBoss.getTranslateY() + 240, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s5 = new Item((int) finalBoss.getTranslateX() + 200, (int) finalBoss.getTranslateY() + 80, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s6 = new Item((int) finalBoss.getTranslateX() + 240, (int) finalBoss.getTranslateY() + 240, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s7 = new Item((int) finalBoss.getTranslateX() + 280, (int) finalBoss.getTranslateY() + 160, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s8 = new Item((int) finalBoss.getTranslateX() + 320, (int) finalBoss.getTranslateY() + 240, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s9 = new Item((int) finalBoss.getTranslateX() + 360, (int) finalBoss.getTranslateY() + 80, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s10 = new Item((int) finalBoss.getTranslateX() + 400, (int) finalBoss.getTranslateY() + 240, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s11 = new Item((int) finalBoss.getTranslateX() + 440, (int) finalBoss.getTranslateY() + 160, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s12 = new Item((int) finalBoss.getTranslateX() + 480, (int) finalBoss.getTranslateY() + 80, 5, 10, finalBoss.type + "bullet", Color.BLACK);
        Item s13 = new Item((int) finalBoss.getTranslateX() + 520, (int) finalBoss.getTranslateY() + 160, 5, 10, finalBoss.type + "bullet", Color.BLACK);

        if ((pattern % 8) == 0) {
            root.getChildren().addAll(s2, s4, s6, s8, s9, s13);
            pattern++;
        } else if ((pattern % 8) == 1) {
            root.getChildren().addAll(s1, s3, s8, s9, s10);
            pattern++;
        } else if ((pattern % 8) == 2) {
            root.getChildren().addAll(s2, s3, s8, s9, s10, s11, s13);
            pattern++;
        } else if ((pattern % 8) == 3) {
            root.getChildren().addAll(s1, s7, s8, s10, s11, s12);
            pattern++;
        } else if ((pattern % 8) == 4) {
            root.getChildren().addAll(s2, s5, s6, s7, s12, s13);
            pattern++;
        } else if ((pattern % 8) == 5) {
            root.getChildren().addAll(s1, s3, s6, s9, s10, s12);
            pattern++;
        } else if ((pattern % 8) == 6) {
            root.getChildren().addAll(s3, s6, s7, s11, s13);
            pattern++;
        } else if ((pattern % 8) == 7) {
            root.getChildren().addAll(s2, s5, s6, s8, s12);
            pattern = 0;
        }


    }

    /**
     * This method creates fires for player and also for aliens.
     *
     * @param item Item to create bullet
     */
    private void shoot(Item item) {
        if (item.type.equals("player")) {

            Item s = new Item((int) item.getTranslateX() + 30, (int) item.getTranslateY() + 30, 5, 10, item.type + "bullet", Color.ORANGE);
            root.getChildren().add(s);

        } else {
            Item s = new Item((int) item.getTranslateX() + 25, (int) item.getTranslateY() + 25, 5, 10, item.type + "bullet", Color.PURPLE);
            root.getChildren().add(s);

        }

    }

    /**
     * This method prepares game over screen and load Main Menu.
     *
     * @throws IOException It throws an exception if it is not load to FXML
     */
    private void gameOverScene() throws IOException {

        timer.stop();

        Window owner = mainStage.getScene().getWindow();

        RestServiceConsumer restServiceConsumer = new RestServiceConsumer();
        restServiceConsumer.addScore(userId, score.toString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        mapper.addMixIn(Player.class, PlayerMixIn.class);
        User winner = mapper.readValue(restServiceConsumer.getUser(userId), User.class);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Game is Over!",
                "Winner is " + winner.getName() + "!");


        Parent mainMenu = FXMLLoader.load(getClass().getResource(MAIN_MENU_URL));

        mainMenu.setId(userId);

        Scene mainMenuScene = new Scene(mainMenu);

        mainStage.setScene(mainMenuScene);

        mainStage.show();


    }

}