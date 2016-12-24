package hdgh0g.albumfinder.gui.whatcd;

import hdgh0g.albumfinder.domain.Album;
import hdgh0g.albumfinder.domain.Artist;
import hdgh0g.albumfinder.utils.AlbumFindUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlbumsSearchPanel extends JPanel {

    private Set<Artist> artists = new HashSet<>();

    private JButton searchButton = new JButton("Искать новые альбомы");

    private JLabel albumsLabel = new JLabel("Найденные альбомы");

    private JList<Album> albumJList = new JList<>();

    private JProgressBar jProgressBar = new JProgressBar();

    private Set<Album> albums;

    public AlbumsSearchPanel() {
        super();
        searchButton.setEnabled(false);
        albumJList.setEnabled(false);

        addPanels();
        bindButtons();
    }

    private void bindButtons() {
        searchButton.addActionListener(e -> {
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.submit( () -> {
                searchButton.setEnabled(false);
                jProgressBar.setMinimum(0);
                jProgressBar.setMaximum(artists.size());
                jProgressBar.setStringPainted(true);
                albums = AlbumFindUtils.findAllAlbums(artists, (i) -> {
                    jProgressBar.setValue(i + 1);
                    this.repaint();
                    int value = jProgressBar.getValue();
                    System.out.println(value);
                });
                updateAlbumJList();
                searchButton.setEnabled(true);
            });
            service.shutdown();
        });
    }

    private void updateAlbumJList() {
        DefaultListModel<Album> listModel = new DefaultListModel<>();
        albums.stream().sorted((o1, o2) -> o2.getYear().compareTo(o1.getYear())).forEach(listModel::addElement);
        albumJList.setModel(listModel);
    }

    private void addPanels() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        searchButton.setAlignmentX(CENTER_ALIGNMENT);
        topPanel.add(searchButton);
        topPanel.add(jProgressBar);
        add(topPanel, BorderLayout.NORTH);

        JPanel artistPanel = new JPanel();
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
        artistPanel.add(albumsLabel);
        artistPanel.add(new JScrollPane(albumJList));
        add(artistPanel);
    }

    public void updateArtists(Set<Artist> artists) {
        this.artists = artists;
        boolean enabled = artists.size() > 0;
        searchButton.setEnabled(enabled);
        albumJList.setEnabled(enabled);
        if (!enabled) {
            albumJList.setModel(new DefaultListModel<>());
        }
    }
}
