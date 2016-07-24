package hdgh0g.albumfinder.gui.whatcd;

import api.user.User;
import hdgh0g.albumfinder.utils.WhatCdConnectionUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class WhatCdLoginPanel extends JPanel {

    private WhatCdSearchPanel searchPanel;

    private JPanel loggedInPanel;

    private JPanel notLoggedInPanel;

    private JButton loginButton = new JButton("Войти");

    private JButton logoutButton = new JButton("Выйти");

    private JLabel usernameLabel = new JLabel("Имя пользователя на what.cd");

    private JTextField usernameField = new JTextField();

    private JLabel passwordLabel = new JLabel("Пароль");

    private JPasswordField passwordField = new JPasswordField();

    private JLabel yourUsernameLabel = new JLabel("Вы вошли как ");

    private JLabel userAvatar = new JLabel();

    public WhatCdLoginPanel(WhatCdSearchPanel searchPanel) {
        this.searchPanel = searchPanel;
        createLoggedInPanel();
        createNotLoggedInPanel();
        setLayout(new GridLayout());
        add(notLoggedInPanel);
        bindButtons();
    }

    private void bindButtons() {
        loginButton.addActionListener(e -> {
            WhatCdConnectionUtils.connect(usernameField.getText(), new String(passwordField.getPassword()));
            if (WhatCdConnectionUtils.isConnected()) {
                updateLoggedInPanelWithUser(WhatCdConnectionUtils.getLoggedInUser());
                removeAll();
                add(loggedInPanel);
                revalidate();
                repaint();
                searchPanel.updateConnected(true);
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        logoutButton.addActionListener(e -> {
            removeAll();
            add(notLoggedInPanel);
            revalidate();
            repaint();
            searchPanel.updateConnected(false);
        });
    }

    private void updateLoggedInPanelWithUser(User loggedInUser) {
        yourUsernameLabel.setText("Вы вошли как " + loggedInUser.getProfile().getUsername());
        String imageURL = loggedInUser.getProfile().getAvatar();
        BufferedImage avatar = null;
        try {
            avatar = ImageIO.read(new URL(imageURL));
        } catch (IOException e) {
            try {
                avatar = ImageIO.read(WhatCdLoginPanel.class.getResourceAsStream("default_avatar.jpg"));
            } catch (IOException ignored) { }
        }
        userAvatar.setIcon(new ImageIcon(avatar.getScaledInstance(100,100,Image.SCALE_SMOOTH)));
    }

    private void createLoggedInPanel() {
        loggedInPanel = new JPanel(new BorderLayout());
        loggedInPanel.add(userAvatar, BorderLayout.WEST);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalGlue());
        yourUsernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(yourUsernameLabel);
        rightPanel.add(logoutButton);
        rightPanel.add(Box.createVerticalGlue());
        loggedInPanel.add(rightPanel);
    }

    private void createNotLoggedInPanel() {
        notLoggedInPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(loginButton);
        rightPanel.add(Box.createVerticalGlue());
        notLoggedInPanel.add(rightPanel, BorderLayout.EAST);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(usernameLabel);
        leftPanel.add(usernameField);
        leftPanel.add(passwordLabel);
        leftPanel.add(passwordField);
        notLoggedInPanel.add(leftPanel);
    }
}
