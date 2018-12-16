package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Item extends Rectangle {
    boolean dead = false;
    final String type;
    Integer health = 100;

    /**
     * Constructor for bullets.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param w Width
     * @param h Height
     * @param type Type of item
     * @param color Color of item
     */
    Item(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }


    /**
     * Constructor for player and aliens.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param w Width
     * @param h Height
     * @param type Type of item
     */
    Item(int x, int y, int w, int h, String type) {
        super(w, h);

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