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
    int j;
    int counter;
    boolean jump;
    boolean jump2;

    Player() {
        this.addKeyListener(this);
        timerStopped = true;
        jumped = false;
        on = true;
        j = 0;
        counter = 0;
        jump = true;
        jump2 = true;
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
            x = super.getWidth() - width;
        }
        if (y < 0) {
            y = 0;
        }
        if (x < 0) {
            x = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'd') {
            x += 3;
        }
        if (e.getKeyChar() == 'a') {
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
        try {
            Robot robot = new Robot();
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
                        //jumped = false;
                    }

                    if(y <= 0) {
                        jumped = true;
                    }
                    repaint();
                }
            });
            //jumped = false;
            timer.start();
            timerStopped = false;
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    private void stopTimer() {
        timer.stop();
        timerStopped = true;
    }

    private void onPlatform() {
        if (timerStopped) {
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < GameField.obstacles.size(); i++) {
                        if (y == GameField.y.get(i) - height && x >= GameField.x.get(i) - 20 && x <= GameField.x.get(i) + GameField.width.get(i) - 20) {
                            y = GameField.y.get(i) - height;
                            j = i;
                            on = false;
                        }
                    }
                    if(on){
                        y += 1;
                    }
                    if(!on) {
                        if (y == GameField.y.get(j) - height && x >= GameField.x.get(j) - 20 && x <= GameField.x.get(j) + GameField.width.get(j) - 20) {
                            y = GameField.y.get(j) - height;
                            counter = 1;
                            jumped = false;
                            jump = true;
                        } else {
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
