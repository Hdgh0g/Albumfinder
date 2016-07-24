package hdgh0g.albumfinder.gui.whatcd;

import hdgh0g.albumfinder.domain.Album;
import hdgh0g.albumfinder.domain.Artist;
import hdgh0g.albumfinder.utils.AlbumFindUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class WhatCdSearchPanel extends JPanel {

    private Set<Artist> artists = new HashSet<>();

    private boolean connected = false;

    private JButton searchButton = new JButton("Искать новые альбомы");

    private JLabel albumsLabel = new JLabel("Найденные альбомы");

    private JList albumJList = new JList();

    private Set<Album> albums;

    public WhatCdSearchPanel() {
        super();
        searchButton.setEnabled(false);
        albumJList.setEnabled(false);

        addPanels();
        bindButtons();
    }

    private void bindButtons() {
        searchButton.addActionListener(e -> {
            albums = AlbumFindUtils.findAllAlbums(artists);
            updateAlbumJList();
        });
    }

    private void updateAlbumJList() {
        DefaultListModel listModel = new DefaultListModel();
        albums.forEach(artist -> listModel.addElement(artist));
        albumJList.setModel(listModel);
    }

    private void addPanels() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel artistPanel = new JPanel();
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
        artistPanel.add(albumsLabel);
        artistPanel.add(new JScrollPane(albumJList));
        add(artistPanel);
    }

    public void updateArtists(Set<Artist> artists) {
        this.artists = artists;
        updateSearchButton();
    }

    public void updateConnected(boolean connected) {
        this.connected = connected;
        updateSearchButton();
    }

    private void updateSearchButton() {
        boolean enabled = connected && artists.size() > 0;
        searchButton.setEnabled(enabled);
        albumJList.setEnabled(enabled);
        if (!enabled) {
            albumJList.setModel(new DefaultListModel());
        }
    }
}
