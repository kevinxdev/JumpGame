package de.Kevin.JumpGame;

import javax.swing.*;

class Window extends JFrame {

    Window() {
        super("JumpGame");

        Player spieler = new Player();

        this.setSize(1280,720);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.add(spieler);

        this.setVisible(true);

        spieler.setPos();

    }

}
