package hdgh0g.albumfinder.gui.artistlist;

import hdgh0g.albumfinder.domain.Album;
import hdgh0g.albumfinder.domain.Artist;
import hdgh0g.albumfinder.gui.whatcd.AlbumsSearchPanel;
import hdgh0g.albumfinder.utils.FileFinderUtils;
import hdgh0g.albumfinder.utils.TagExtractUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtistListPanel extends JPanel {

    private static final int VISIBLE_ROW_COUNT = 40;

    private FolderFinderPanel folderFinderPanel;

    private AlbumsSearchPanel searchPanel;

    private JList<Artist> artistJList = new JList<>();

    private JLabel artistJLabel = new JLabel("Выбранные артисты");

    private JButton scanFoldersButton = new JButton("Сканировать папки");

    private JButton addArtistButton = new JButton("Добавить вручную");

    private JButton deleteArtistFromListButton = new JButton("Удалить из списка");

    private Set<Artist> artists = new HashSet<>();

    public ArtistListPanel(FolderFinderPanel folderFinderPanel, AlbumsSearchPanel searchPanel) {
        super();
        this.folderFinderPanel = folderFinderPanel;
        this.searchPanel = searchPanel;
        scanFoldersButton.setEnabled(false);

        addPanels();
        bindButtons();
    }

    private void bindButtons() {
        addArtistButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Введите имя артиста", "Добавление нового артиста",  JOptionPane.QUESTION_MESSAGE);
            if (!StringUtils.isBlank(artistName)) {
                artists.add(new Artist(artistName));
            }
            updateArtistJList();
        });

        scanFoldersButton.addActionListener(e -> {
            try {
                Set<File> files = FileFinderUtils.findMusicFilesInFolders(true, folderFinderPanel.getFolders());
                Set<Album> albums = TagExtractUtils.getAllAlbumFromFiles(files.toArray(new File[files.size()]));
                artists.addAll(albums.stream().map(Album::getAlbumArtist).collect(Collectors.toSet()));
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Ошибка в чтении какого-то файла или папки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            updateArtistJList();
        });

        deleteArtistFromListButton.addActionListener(e -> {
            Artist selectedArtist = artistJList.getSelectedValue();
            artists.remove(selectedArtist);
            updateArtistJList();
        });
    }

    private void addPanels() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(scanFoldersButton);
        topPanel.add(addArtistButton);
        topPanel.add(deleteArtistFromListButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel artistPanel = new JPanel();
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
        artistPanel.add(artistJLabel);
        artistPanel.add(new JScrollPane(artistJList));
        artistJList.setVisibleRowCount(VISIBLE_ROW_COUNT);
        add(artistPanel);
    }

    private void updateArtistJList() {
        DefaultListModel<Artist> listModel = new DefaultListModel<>();
        artists.forEach(listModel::addElement);
        artistJList.setModel(listModel);
        searchPanel.updateArtists(artists);
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setButtonEnabled(boolean state) {
        scanFoldersButton.setEnabled(state);
    }
}
