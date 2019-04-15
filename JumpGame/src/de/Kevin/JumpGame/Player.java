package de.Kevin.JumpGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends GameField implements KeyListener {

    static int width;
    static int height;
    static int x;
    static int y;
    private Timer timer;
    private boolean timerStopped;
    private boolean jumped;
    static boolean on;
    static int j;
    int counter;
    boolean jump;
    boolean jump2;
    int c1;
    int c2;
    Robot robot;

    Player() {
        this.addKeyListener(this);
        timerStopped = true;
        jumped = false;
        on = true;
        j = 0;
        counter = 0;
        jump = true;
        jump2 = true;
        c1 = 0;
        c2 = 0;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    void setPos() {
        width = 50;
        height = 50;
        x = (super.getWidth() / 2) - width / 2;
        y = (int) (super.getHeight() + 30 - height - height / 1.5);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        g.fillOval(x, y, width, height);

        if (y > super.getHeight() - height) {
            y = super.getHeight() - height;
        }
        if (x > super.getWidth() - width) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x < 0) {
            x = super.getWidth() - width;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'd') {
            x += 3;
        } else if (e.getKeyChar() == 'a') {
            x -= 3;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (timerStopped) {
            if(jump2) {
                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    jumpEvent();
                    jump2 = false;
                }
            }
        }
    }

    private void jumpEvent() {
            int ys = y;
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PointerInfo pi = MouseInfo.getPointerInfo();
                    Point p = pi.getLocation();
                    robot.mouseMove((int) p.getX(), (int) p.getY());
                    if(y >= ys - 250 && !jumped) {
                        y -= 1;
                        if(y == ys - 250) {
                            jumped = true;
                        }
                    } else if (y < (int) (Player.super.getHeight() + 33 - height - height / 1.5) && jumped) {
                        stopTimer();
                        onPlatform();
                    }

                    if(y <= 0) {
                        jumped = true;
                    }
                    repaint();
                    c1++;
                    if(c1 == 10) {
                        setObstacles();
                        c1 = 0;
                    }
                }
            });
            timer.start();
            timerStopped = false;
    }

    private void stopTimer() {
        timer.stop();
        timerStopped = true;
    }

    private void setObstacles() {
        if(Player.y <= Player.super.getHeight()) {
            generateStructure();
        }
    }

    private void onPlatform() {
        if (timerStopped) {
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PointerInfo pi = MouseInfo.getPointerInfo();
                    Point p = pi.getLocation();
                    robot.mouseMove((int) p.getX(), (int) p.getY());
                    for (int i = 0; i < GameField.obstacles.size(); i++) {
                        if (y == GameField.y.get(i) - height && x >= GameField.x.get(i) - 20 && x <= GameField.x.get(i) + GameField.width.get(i) - 20) {
                            y = GameField.y.get(i) - height;
                            j = i;
                            on = false;
                        }
                    }
                    if(on){
                        y += 1;
                        jump = false;
                    }
                    if(!on) {
                        if (y == GameField.y.get(j) - height && x >= GameField.x.get(j) - 20 && x <= GameField.x.get(j) + GameField.width.get(j) - 20) {
                            y = GameField.y.get(j) - height;
                            counter = 1;
                            jumped = false;
                            jump = true;
                            on = true;
                            addKeyListener(new KeyListener() {
                                @Override
                                public void keyTyped(KeyEvent e) {

                                }

                                @Override
                                public void keyPressed(KeyEvent e) {

                                }

                                @Override
                                public void keyReleased(KeyEvent e) {
                                    if(jump) {
                                        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                                            stopTimer();
                                            jumpEvent();
                                            jump = false;
                                        }
                                    }
                                }
                            });
                            if(GameField.obstacles.get(j) instanceof BreakObstacle) {
                                GameField.obstacles.get(j).properties();
                            }
                        }
                    }
                    if(y >= (int) (Player.super.getHeight() + 33 - height - height / 1.5)) {
                        jumped = false;
                        jump2 = true;
                        on = true;
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
