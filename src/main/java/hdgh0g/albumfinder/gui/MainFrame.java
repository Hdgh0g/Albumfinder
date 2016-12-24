package hdgh0g.albumfinder.gui;

import hdgh0g.albumfinder.gui.artistlist.ArtistPanel;
import hdgh0g.albumfinder.gui.whatcd.AlbumsSearchPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private final static int HEIGHT = 700;
    private final static int WIDTH = 1000;
    private final static String name = "Album Finder";

    public MainFrame() {
        setSize(WIDTH, HEIGHT);
        setTitle(name);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(1,2));


        AlbumsSearchPanel albumsPanel = new AlbumsSearchPanel();
        ArtistPanel artistPanel = new ArtistPanel(albumsPanel);
        artistPanel.setBorder(new EmptyBorder(3,3,3,3));
        albumsPanel.setBorder(new EmptyBorder(3,3,3,3));
        add(artistPanel);
        add(albumsPanel);
    }
}