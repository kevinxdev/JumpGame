package de.Kevin.JumpGame;

import java.awt.*;
import java.util.Random;

abstract class Obstacle extends Graphics {

    private int width;
    private int height;
    private int x;
    private int y;
    Random random;

    Obstacle(int width, int height, int x, int y) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        random = new Random();
    }

    int getWidth() {
        return width;
    }
    int getHeight() {
        return height;
    }
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }

    abstract void properties();
    abstract Color color();

    public void paintComponent(Graphics g) {
        g.setColor(color());
        g.fillRect(x,y, getWidth(),getHeight());
    }

}
