package hdgh0g.albumfinder.gui.whatcd;

import hdgh0g.albumfinder.gui.artistlist.ArtistPanel;

import javax.swing.*;
import java.awt.*;

public class WhatCdPanel extends JPanel {

    private ArtistPanel artistPanel;

    public WhatCdPanel(ArtistPanel artistPanel) {
        super();
        this.artistPanel = artistPanel;
        setLayout(new BorderLayout());
    }
}
