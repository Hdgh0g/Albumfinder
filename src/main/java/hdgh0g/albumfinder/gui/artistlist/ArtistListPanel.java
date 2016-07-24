package hdgh0g.albumfinder.gui.artistlist;

import hdgh0g.albumfinder.domain.Album;
import hdgh0g.albumfinder.domain.Artist;
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

    private JList artistList = new JList<>();

    private JLabel artistLabel = new JLabel("Выбранные артисты");

    private JButton scanFolders = new JButton("Сканировать папки");

    private JButton addArtist = new JButton("Добавить вручную");

    private JButton deleteArtistFromList = new JButton("Удалить из списка");

    private Set<Artist> artists = new HashSet<>();

    public ArtistListPanel(FolderFinderPanel folderFinderPanel) {
        super();
        this.folderFinderPanel = folderFinderPanel;
        scanFolders.setEnabled(false);

        addPanels();
        bindButtons();
    }

    private void bindButtons() {
        addArtist.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Введите имя артиста", "Добавление нового артиста",  JOptionPane.QUESTION_MESSAGE);
            if (!StringUtils.isBlank(artistName)) {
                artists.add(new Artist(artistName));
            }
            reloadArtistList();
        });

        scanFolders.addActionListener(e -> {
            try {
                Set<File> files = FileFinderUtils.findMusicFilesInFolders(false, folderFinderPanel.getFolders());
                Set<Album> albums = TagExtractUtils.getAllAlbumFromFiles(files.toArray(new File[files.size()]));
                artists.addAll(albums.stream().map(Album::getAlbumArtist).collect(Collectors.toSet()));
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Ошибка в чтении какого-то файла или папки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            reloadArtistList();
        });

        deleteArtistFromList.addActionListener(e -> {
            Artist selectedArtist = (Artist) artistList.getSelectedValue();
            artists.remove(selectedArtist);
            reloadArtistList();
        });
    }

    private void addPanels() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(scanFolders);
        topPanel.add(addArtist);
        topPanel.add(deleteArtistFromList);
        add(topPanel, BorderLayout.NORTH);

        JPanel artistPanel = new JPanel();
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
        artistPanel.add(artistLabel);
        artistPanel.add(new JScrollPane(artistList));
        artistList.setVisibleRowCount(VISIBLE_ROW_COUNT);
        add(artistPanel);
    }

    private void reloadArtistList() {
        DefaultListModel listModel = new DefaultListModel();
        artists.forEach(artist -> listModel.addElement(artist));
        artistList.setModel(listModel);
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setButtonEnabled(boolean state) {
        scanFolders.setEnabled(state);
    }
}
