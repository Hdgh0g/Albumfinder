package hdgh0g.albumfinder.gui.artistlist;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FolderFinderPanel extends JPanel {

    private static final int VISIBLE_ROW_COUNT = 10;

    private JFileChooser chooser;

    private JButton openJFileChooser = new JButton("Добавить папки");

    private JButton deleteFolderFromList = new JButton("Удалить папку из списка");

    private JList folderList = new JList();

    private JLabel folderLabel = new JLabel("Выбранные папки");

    private ArtistListPanel artistListPanel;

    private Set<File> folders = new HashSet<>();

    FolderFinderPanel() {
        super();
        configureFileChooser();
        addPanels();
        bindButtons();
    }

    private void configureFileChooser() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        chooser.setDialogTitle("Выберите необходимые папки");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(true);
    }

    private void bindButtons() {
        openJFileChooser.addActionListener(e -> {
            chooser.showOpenDialog(this);
            File[] chosenFolders = chooser.getSelectedFiles();
            Collections.addAll(folders, chosenFolders);
            reloadFoldersList();
        });

        deleteFolderFromList.addActionListener(e -> {
            File selectedFile = (File) folderList.getSelectedValue();
            folders.remove(selectedFile);
            reloadFoldersList();
        });
    }

    private void addPanels() {
        setLayout(new BorderLayout());

        JPanel panelForButtons = new JPanel();
        panelForButtons.add(openJFileChooser);
        panelForButtons.add(deleteFolderFromList);
        add(panelForButtons, BorderLayout.NORTH);

        JPanel panelForList = new JPanel();
        panelForList.setLayout(new BoxLayout(panelForList, BoxLayout.Y_AXIS));
        panelForList.add(folderLabel);
        panelForList.add(new JScrollPane(folderList));
        folderList.setVisibleRowCount(VISIBLE_ROW_COUNT);
        add(panelForList);
    }

    private void reloadFoldersList() {
        artistListPanel.setButtonEnabled(folders.size() > 0);
        DefaultListModel listModel = new DefaultListModel();
        folders.forEach(folder -> listModel.addElement(folder));
        folderList.setModel(listModel);
    }

    public Set<File> getFolders() {
        return folders;
    }

    public void setArtistListPanel(ArtistListPanel artistListPanel) {
        this.artistListPanel = artistListPanel;
    }
}
