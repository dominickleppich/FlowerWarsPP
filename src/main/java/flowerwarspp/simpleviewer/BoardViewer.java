package flowerwarspp.simpleviewer;

import flowerwarspp.preset.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardViewer extends JFrame {

    private BoardViewerPanel panel;

    // ------------------------------------------------------------

    public BoardViewer() {
        super("FlowerWarsPP v1.0");

        this.panel = new BoardViewerPanel(this);

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

    public void setViewer(Viewer viewer) {
        panel.setViewer(viewer);
    }

    public void reset() {
        panel.reset();
    }

    public void update(Move move) {
        panel.showPerformedMove(move);
        panel.repaint();
    }

    public void showStatus(Status status) {
        panel.showStatus(status);
        panel.repaint();
    }

    public void close() {
        dispose();
    }
}
