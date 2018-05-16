package flowerwarspp.test.ui;

import org.slf4j.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleBoardStateCreator extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(flowerwarspp.ui.graphical.UIWindow.class);

    private SimpleBoardStateCreatorPanel panel;

    // ------------------------------------------------------------

    public SimpleBoardStateCreator(int boardSize) {
        super("FlowerWarsPP Board State Creator");

        this.panel = new SimpleBoardStateCreatorPanel(this, boardSize);

        add(panel);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                panel.update();
            }
        });

        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(320, 240));

        setSize(800, 600);
        setVisible(true);
    }

    // ------------------------------------------------------------

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide board size as parameter!");
            return;
        }
        new SimpleBoardStateCreator(Integer.parseInt(args[0]));
    }
}
