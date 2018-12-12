package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class AllienGameApplicationGUI extends Application {


    private Pane root = new Pane();

    private double t = 0;

    private Integer currentLevel = 0;

    private Integer enemyCount = 0;

    private Integer score = 0;

    private Item player = new Item(300, 750, 40, 40, "player", Color.PINK);


    Image level1Image = new Image(getClass().getResourceAsStream("/images/level1.png"));
    Image level2Image = new Image(getClass().getResourceAsStream("/images/level2.png"));
    Image level3Image = new Image(getClass().getResourceAsStream("/images/level3.png"));

    private Label levelLabel = new Label();

    private Label scoreLabel = new Label();

    private Label healthLabel = new Label();

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update();
        }
    };

    private Parent createContent() {
        root.setPrefSize(600, 800);
        root.getChildren().add(player);

        levelLabel.relocate(0, 0);
        levelLabel.setGraphic(new ImageView(level1Image));

        scoreLabel.relocate(520, 30);
        scoreLabel.setText("Score : " + score.toString());

        healthLabel.relocate(220, 30);
        healthLabel.setText("Health : " + player.getHealth().toString() + "HP");

        root.getChildren().addAll(levelLabel, scoreLabel, healthLabel);


        nextLevel(currentLevel);
        timer.stop();
        timer.start();


        return root;
    }

    private void nextLevel(Integer level) {

        if (level == 1) {
            for (int i = 0; i < 5; i++) {
                Item s = new Item(90 + i * 100, 100, 30, 30, "enemy", Color.BLACK);
                root.getChildren().add(s);
            }
            enemyCount = 5;
        } else if (level == 2) {
            levelLabel.setGraphic(new ImageView(level2Image));
            for (int i = 0; i < 5; i++) {
                Item s = new Item(90 + i * 100, 100, 30, 30, "enemy", Color.RED);

                root.getChildren().add(s);
            }
            for (int i = 0; i < 6; i++) {
                Item s = new Item(40 + i * 100, 150, 30, 30, "enemy", Color.RED);

                root.getChildren().add(s);
            }
            enemyCount = 11;
        } else if (level == 3) {
            levelLabel.setGraphic(new ImageView(level3Image));
            for (int i = 0; i < 5; i++) {
                Item s = new Item(90 + i * 100, 100, 30, 30, "enemy", Color.YELLOW);

                root.getChildren().add(s);
            }
            for (int i = 0; i < 6; i++) {
                Item s = new Item(40 + i * 100, 150, 30, 30, "enemy", Color.YELLOW);

                root.getChildren().add(s);
            }
            for (int i = 0; i < 5; i++) {
                Item s = new Item(90 + i * 100, 200, 30, 30, "enemy", Color.YELLOW);

                root.getChildren().add(s);
            }
            enemyCount = 16;
        } else {
            gameOverScene();
        }


    }

    private List<Item> items() {
        List<Object> itemList = root.getChildren().stream().filter(n -> n.getClass().getName() == "application.Item").collect(Collectors.toList());

        return itemList.stream().map(n -> (Item) n).collect(Collectors.toList());
    }

    private void update() {
        t += 0.016;
        if (t > 1) {

        }
        items().forEach(s -> {
            switch (s.type) {

                case "enemybullet":
                    s.moveDownBullet();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.health -= 20;
                        healthLabel.setText("Health : " + player.getHealth().toString() + "HP");
                        if (player.health == 0) {
                            player.dead = true;
                            gameOverScene();
                        }
                        s.dead = true;
                    }
                    if (s.getTranslateY() > 800) {
                        s.dead = true;
                    }
                    break;

                case "playerbullet":
                    s.moveUp();

                    items().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemyCount--;
                            score += 10;
                            scoreLabel.setText("Score : " + score.toString());
                            if (enemyCount == 0) {
                                currentLevel++;
                                nextLevel(currentLevel);
                            }
                        }
                        if (s.getTranslateY() < 0) {
                            s.dead = true;
                        }
                    });

                    break;

                case "player":

                    items().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            enemyCount--;
                            score += 10;
                            scoreLabel.setText("Score : " + score.toString());
                            if (enemyCount == 0) {
                                currentLevel++;
                                nextLevel(currentLevel);
                            }
                        }
                    });

                    break;
                case "enemy":
                    if (t > 1) {
                        if (Math.random() < 0.5) {
                            shoot(s);
                        }
                    }

                    break;
            }
        });

        root.getChildren().removeIf(n -> {
            if (n.getClass().getName() == "application.Item") {
                Item s = (Item) n;
                return s.dead;
            } else {
                return false;
            }
        });

        if (t > 1) {
            t = 0;
        }
    }


    private void shoot(Item who) {
        if (who.type.equals("player")) {
            if (who.dead) {

            } else {
                Item s = new Item((int) who.getTranslateX() + 20, (int) who.getTranslateY() + 20, 5, 20, who.type + "bullet", Color.ORANGE);
                root.getChildren().add(s);
            }
        } else {
            Item s = new Item((int) who.getTranslateX() + 15, (int) who.getTranslateY() + 15, 5, 20, who.type + "bullet", Color.PURPLE);
            root.getChildren().add(s);

        }

    }

    public void playGame(Stage stage) {
        root.getChildren().remove(0, root.getChildren().size());
        currentLevel = 1;

        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    if (player.getTranslateX() <= 0) break;
                    player.moveLeft();
                    break;
                case W:
                    if (player.getTranslateY() <= 0) break;
                    player.moveUp();
                    break;
                case D:
                    if (player.getTranslateX() + 40 >= 600) break;
                    player.moveRight();
                    break;
                case S:
                    if (player.getTranslateY() + 40 >= 800) break;
                    player.moveDown();
                    break;
                case SPACE:
                    shoot(player);
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Dünyayı Kurtaran Adamın Oğlu ve Uzay Gemisi");

        playGame(stage);


    }


    public void gameOverScene() {
//        Pane pane = new Pane();
        Button restartButton = new Button("Play Again!");
        root.getChildren().remove(0, root.getChildren().size());
        currentLevel = 0;
        player = new Item(300, 750, 40, 40, "player", Color.PINK);
        enemyCount = 0;
        score = 0;
        root.getChildren().add(restartButton);
//        Scene scene = new Scene(pane);
//        stage.setScene(scene);
//        stage.show();
        restartButton.setOnAction(event -> {
            root.getChildren().remove(0, root.getChildren().size());
            currentLevel += 1;
            createContent();
        });


    }


    public static void main(String[] args) {
        launch(args);
    }
}