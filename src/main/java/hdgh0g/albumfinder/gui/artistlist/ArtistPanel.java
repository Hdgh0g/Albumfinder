package hdgh0g.albumfinder.gui.artistlist;

import hdgh0g.albumfinder.gui.whatcd.AlbumsSearchPanel;

import javax.swing.*;
import java.awt.*;

public class ArtistPanel extends JPanel {

    public ArtistPanel(AlbumsSearchPanel albumsPanel) {
        super();
        setLayout(new BorderLayout());
        FolderFinderPanel folderFinderPanel = new FolderFinderPanel();
        add(folderFinderPanel, BorderLayout.NORTH);
        ArtistListPanel artistListPanel = new ArtistListPanel(folderFinderPanel, albumsPanel);
        folderFinderPanel.setArtistListPanel(artistListPanel);
        add(artistListPanel);
    }
}
