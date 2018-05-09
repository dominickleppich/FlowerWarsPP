package flowerwarspp.ui.graphical;

import flowerwarspp.preset.*;
import flowerwarspp.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UIWindow extends JFrame implements UserInterface {
    private UIPanel panel;

    // ------------------------------------------------------------

    public UIWindow() {
        super("FlowerWarsPP v1.0");

        this.panel = new UIPanel(this);

        add(panel);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                panel.update();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(320, 240));

        setSize(800, 600);
        setVisible(true);
    }

    // ------------------------------------------------------------

    @Override
    public void setViewer(Viewer viewer) {
        panel.setViewer(viewer);
    }

    @Override
    public void update() {
        panel.repaint();
    }

    @Override
    public Move request() {
        return null;
    }
}
