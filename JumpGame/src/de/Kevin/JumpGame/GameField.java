package de.Kevin.JumpGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

class GameField extends JPanel implements ActionListener {

    static ArrayList<Obstacle> obstacles;
    static boolean print;
    static Random random;
    static int count;
    static ArrayList<Integer> height;
    static ArrayList<Integer> width;
    static ArrayList<Integer> x;
    static ArrayList<Integer> y;
    int counter;

    GameField() {
        this.setBackground(Color.cyan);
        this.setFocusable(true);
        this.requestFocusInWindow();
        obstacles = new ArrayList<>();
        random = new Random();
        print = true;
        height = new ArrayList<>();
        width = new ArrayList<>();
        x = new ArrayList<>();
        y = new ArrayList<>();
        count = 0;
        //Timer t = new Timer(500, this);
        //t.start();
        for(int i = 0; i < 60; i++) {
            generateStructure();
        }

    }

    public void createPlatforms() {
        int rdm = random.nextInt(100)+1;
        height.add(random.nextInt(25)+5);
        width.add(random.nextInt(100)+100);
        x.add(random.nextInt(1270 - width.get(count)));
        y.add(0);
        if(rdm < 70) {
            obstacles.add(new StandardObstacle(width.get(count),height.get(count), x.get(count), y.get(count)));
        } else {
            obstacles.add(new BreakObstacle(width.get(count),height.get(count), x.get(count), y.get(count)));
        }
        count++;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(print) {
            for (Obstacle o : obstacles) {
                o.paintComponent(g);
            }
        }
    }

    void comeDown() {
        for(int i = 0; i < obstacles.size(); i++) {
            y.set(i, y.get(i) + 10);
        }
        updatePlatforms();
    }

    void updatePlatforms() {
        for(int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).setSettings(width.get(i),height.get(i), x.get(i), y.get(i));
            obstacles.set(i, obstacles.get(i));
        }
        repaint();
    }

    void generateStructure() {
        counter++;
        if(counter % (random.nextInt(5)+5) == 0) {
            createPlatforms();
            counter = 0;
        }
        comeDown();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        counter++;
        if(counter % (random.nextInt(5)+5) == 0) {
            createPlatforms();
            counter = 0;
        }
        comeDown();
    }
}