package flowerwarspp.ui.graphical;

import flowerwarspp.preset.*;
import flowerwarspp.ui.*;
import org.slf4j.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UIWindow extends JFrame implements Display, Requestable {
    private static final Logger logger = LoggerFactory.getLogger(UIWindow.class);

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

//        setUndecorated(true);
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
    public void reset() {
        panel.reset();
    }

    @Override
    public void update(Move move) {
        panel.showPerformedMove(move);

        panel.repaint();
    }

    @Override
    public void showStatus(Status status) {
        panel.showStatus(status);
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public Move request() throws InterruptedException {
        return panel.request();
    }
}
