package hdgh0g.albumfinder.gui;

import hdgh0g.albumfinder.gui.artistlist.ArtistPanel;
import hdgh0g.albumfinder.gui.whatcd.WhatCdPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final static int HEIGHT = 700;
    private final static int WIDTH = 900;
    private final static String name = "Album Finder";

    public MainFrame() {
        setSize(WIDTH, HEIGHT);
        setTitle(name);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(1,2));

        ArtistPanel artistPanel = new ArtistPanel();
        add(artistPanel);
        add(new WhatCdPanel(artistPanel));
    }
}