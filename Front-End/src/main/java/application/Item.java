package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Item extends Rectangle {
    boolean dead = false;
    final String type;
    Integer health = 100;

    Item(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    Integer getHealth() {
        return health;
    }


    void moveLeft() {
        setTranslateX(getTranslateX() - 15);
    }

    void moveRight() {
        setTranslateX(getTranslateX() + 15);
    }

    void moveUp() {
        setTranslateY(getTranslateY() - 15);
    }

    void moveDown() {
        setTranslateY(getTranslateY() + 15);
    }

    void moveDownBullet() {
        setTranslateY(getTranslateY() + 10);
    }
}