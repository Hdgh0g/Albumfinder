package hdgh0g.albumfinder.gui.artistlist;

import hdgh0g.albumfinder.domain.Artist;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class ArtistPanel extends JPanel {

    private FolderFinderPanel folderFinderPanel;

    private ArtistListPanel artistListPanel;

    public ArtistPanel() {
        super();
        setLayout(new BorderLayout());
        folderFinderPanel = new FolderFinderPanel();
        add(folderFinderPanel, BorderLayout.NORTH);
        artistListPanel = new ArtistListPanel(folderFinderPanel);
        folderFinderPanel.setArtistListPanel(artistListPanel);
        add(artistListPanel);
    }

    public Set<Artist> getArtists() {
        return artistListPanel.getArtists();
    }
}