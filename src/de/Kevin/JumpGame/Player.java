package de.Kevin.JumpGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends GameField implements KeyListener {

    private int width;
    private int height;
    private int x;
    private int y;
    private Timer timer;
    private boolean timerStopped;
    private boolean jumped;
    boolean on;

    Player() {
        this.addKeyListener(this);
        timerStopped = true;
        jumped = false;
    }

    void setPos() {
        width = 50;
        height = 50;
        System.out.println("height: " + super.getHeight());
        System.out.println("width: " + super.getWidth());
        x = (super.getWidth() / 2) - width / 2;
        y = (int) (super.getHeight() + 30 - height - height / 1.5);
        System.out.println(width + " " + height + " " + y + " " + x);
        on = true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        g.fillOval(x, y, width, height);

        if (y > super.getHeight() - height) {
            y = super.getHeight() - height;
            System.out.println("do it y");
        }
        if (x > super.getWidth() - width) {
            x = super.getWidth() - width;
            System.out.println("do it x");
        }
        if (y < 0) {
            y = 0;
            System.out.println("do it y");
        }
        if (x < 0) {
            x = 0;
            System.out.println("do it x");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'd') {
            x += 3;
            System.out.println("X: " + x + " Y: " + y);
        }
        if (e.getKeyChar() == 'a') {
            x -= 3;
            System.out.println("X: " + x + " Y: " + y);
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (timerStopped) {
            if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                try {
                    Robot robot = new Robot();
                    System.out.println("space work");
                    timer = new Timer(10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            PointerInfo pi = MouseInfo.getPointerInfo();
                            Point p = pi.getLocation();
                            robot.mouseMove((int) p.getX(), (int) p.getY());
                            if(y >= (int) (Player.super.getHeight() + 33 - height - height / 1.5) - 250 && !jumped) {
                                y -= 1;
                                System.out.println(y);
                                if(y == (int) (Player.super.getHeight() + 33 - height - height / 1.5) - 250) {
                                    jumped = true;
                                }
                            } else if (y < (int) (Player.super.getHeight() + 33 - height - height / 1.5) && jumped) {
                                int j = 0;
                                    for (int i = 0; i < GameField.obstacles.size(); i++) {
                                        if (y == GameField.y.get(i) - height && x >= GameField.x.get(i) - 20 && x <= GameField.x.get(i) + GameField.width.get(i) - 20) {
                                            //while(y <= (int) (Player.super.getHeight() + 20 - height - height / 1.5)) {
                                                y = GameField.y.get(i) - height;
                                                j = i;
                                            //}
                                            on = false;
                                            //stopTimer();
                                            //onPlatform();
                                        }
                                    }
                                    if(on){
                                    y += 1;
                                }
                                    if(!on) {
                                        if (y == GameField.y.get(j) - height && x >= GameField.x.get(j) - 20 && x <= GameField.x.get(j) + GameField.width.get(j) - 20) {
                                            y = GameField.y.get(j) - height;
                                        } else {
                                            on = true;
                                        }
                                    }
                                System.out.println("check");
                                System.out.println(y);
                                if(y >= (int) (Player.super.getHeight() + 33 - height - height / 1.5)) {
                                    System.out.println("testtestetst");
                                    jumped = false;
                                    on = true;
                                    stopTimer();
                                }
                            }
                            repaint();
                        }
                    });
                    timer.start();
                    timerStopped = false;
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void stopTimer() {
        timer.stop();
        timerStopped = true;
        System.out.println("Timer stoped");
    }

    private void onPlatform() {
        if (timerStopped) {
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(y < getHeight()) {

                    } else if(y >= getHeight()-height) {
                        stopTimer();
                    }
                    repaint();
                }
            });
            timer.start();
            timerStopped = false;
        }
    }

}
