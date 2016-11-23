package hdgh0g.albumfinder.gui.whatcd;

import javax.swing.*;
import java.awt.*;

public class WhatCdPanel extends JPanel {

    private WhatCdLoginPanel loginPanel;

    private WhatCdSearchPanel searchPanel;

    public WhatCdPanel() {
        super();
        setLayout(new BorderLayout());
        searchPanel = new WhatCdSearchPanel();
        loginPanel = new WhatCdLoginPanel(searchPanel);
        add(loginPanel, BorderLayout.NORTH);
        add(searchPanel);
    }

    public WhatCdSearchPanel getSearchPanel() {
        return searchPanel;
    }
}