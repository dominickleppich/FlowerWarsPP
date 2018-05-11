package flowerwarspp.ui.graphical;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.slf4j.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class UIPanel extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(UIPanel.class);

    private UIWindow parentWindow;
    private Viewer viewer;

    // ------------------------------------------------------------

    private static final Color BACKGROUND_COLOR_A = new Color(210, 190, 30);
    private static final Color BACKGROUND_COLOR_B = new Color(230, 150, 0);
    private static final Color BOARD_BACKGROUND_COLOR = new Color(41, 112, 69);
    private static final Color RED_PLAYER_COLOR = new Color(173, 69, 29);
    private static final Color GREEN_PLAYER_COLOR = new Color(22, 173, 47);
    private static final Color HOVER_COLOR = new Color(218, 224, 31);

    private static final double GRID_DOT_SIZE = 0.3;
    private static final float GRID_NEUTRAL_LINE_STRENGTH = 0.1f;
    private static final float GRID_DITCH_LINE_STRENGTH = 0.05f;
    private static final double BORDER = 30.0;
    private int WIDTH, HEIGHT;
    private float UNIT;

    private Map<Position, Point2D> positionPoints;
    private Map<Polygon, Flower> polygonFlowerMap;
    private Flower hoverFlower;
    private Ditch hoverDitch;

    // ------------------------------------------------------------

    public UIPanel(UIWindow parentWindow) {
        this.parentWindow = parentWindow;
        setDoubleBuffered(true);
        update();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                logger.debug("Clicked: " + mouseEvent.getX() + ", " +
                        mouseEvent.getY() + " [" + pointToFlower(mouseEvent
                        .getPoint()) + "]");
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                // Clear old positions
                if (hoverFlower != null) {
                    Rectangle bounds = flowerToPolygon(hoverFlower).getBounds();
                    hoverFlower = null;
                    repaint(bounds);
                }

                hoverDitch = pointToDitch(mouseEvent.getPoint());
                if (hoverDitch == null)
                    hoverFlower = pointToFlower(mouseEvent.getPoint());
                else
                    hoverFlower = null;
                repaint((int) (mouseEvent.getX() - UNIT), (int) (mouseEvent
                        .getY() - UNIT), (int) UNIT * 2, (int) UNIT * 2);
            }
        });
    }

    public void update() {
        WIDTH = parentWindow.getContentPane().getWidth();
        HEIGHT = parentWindow.getContentPane().getHeight();
//        logger.debug("Constants updated to new window constraints: WIDTH =
// " + WIDTH + ", HEIGHT = " + HEIGHT);

        updateUIPositions();
    }

    private synchronized void updateUIPositions() {
        if (viewer == null)
            return;

        int boardSize = viewer.getSize();
        double fieldWidth = Math.min((WIDTH - 2 * BORDER) / boardSize, (
                (HEIGHT - 2 * BORDER) /
                        boardSize) / Math.sin(Math.toRadians(60)));
        UNIT = (float) fieldWidth;
        double fieldHeight = Math.sin(Math.toRadians(60)) * fieldWidth;

        double xOffset = (WIDTH - boardSize * fieldWidth - 2 * BORDER) / 2 +
                BORDER;
        double yOffset = (HEIGHT - boardSize * fieldHeight - 2 * BORDER) / 2
                + BORDER;

        positionPoints = new HashMap<>();

        // Create game board points
        for (int r = 0; r <= boardSize; r++) {
            for (int c = 0; c <= boardSize; c++) {
                if (r + c > boardSize)
                    continue;
                Point2D p = new Point2D.Double(xOffset + c * fieldWidth + r *
                        fieldWidth / 2, HEIGHT - yOffset - r * fieldHeight);
                positionPoints.put(new Position(c + 1, r + 1), p);
            }
        }

        // Create the polygon flower mapping
        polygonFlowerMap = new HashMap<>();
        for (int c = 1; c <= boardSize; c++) {
            for (int r = 1; r <= boardSize; r++) {
                if (c + r <= boardSize + 1) {
                    Flower f = new Flower(new Position(c, r), new Position(c,
                            r + 1), new Position(c + 1, r));
                    polygonFlowerMap.put(flowerToPolygon(f), f);
                }
                if (c + r <= boardSize) {
                    Flower f = new Flower(new Position(c + 1, r + 1), new
                            Position(c,
                            r + 1), new Position(c + 1, r));
                    polygonFlowerMap.put(flowerToPolygon(f), f);
                }
            }
        }

    }

    public synchronized void setViewer(Viewer viewer) {
        this.viewer = viewer;
        updateUIPositions();
        logger.debug("Viewer was set: " + viewer);
    }

    // ------------------------------------------------------------

    private Polygon flowerToPolygon(Flower f) {
        Point2D p1, p2, p3;
        p1 = positionPoints.get(f.getFirst());
        p2 = positionPoints.get(f.getSecond());
        p3 = positionPoints.get(f.getThird());
        int[] x = new int[]{(int) p1.getX(), (int) p2.getX(), (int) p3.getX()};
        int[] y = new int[]{(int) p1.getY(), (int) p2.getY(), (int) p3.getY()};
        return new Polygon(x, y, 3);
    }

    private synchronized Flower pointToFlower(Point point) {
        for (Map.Entry<Polygon, Flower> e : polygonFlowerMap.entrySet()) {
            if (e.getKey().contains(point))
                return e.getValue();
        }
        return null;
    }

    private Ditch pointToDitch(Point point) {
        // TODO
        return null;
    }

    // ------------------------------------------------------------

    private synchronized void render(Graphics2D g) {
        /*int midX = WIDTH / 2;
        int midY = HEIGHT / 2;
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Float(midX - 10, midY - 10, 20, 20));
        g.setColor(Color.RED);
        g.draw(new Rectangle2D.Float(10, 10, WIDTH - 20, HEIGHT - 20));*/
        Paint backgroundPaint = new GradientPaint(0, 0, BACKGROUND_COLOR_A,
                WIDTH,
                HEIGHT, BACKGROUND_COLOR_B);
        g.setPaint(backgroundPaint);
        g.fill(new Rectangle2D.Float(0, 0, WIDTH, HEIGHT));

        // DO SOME GENERAL STUFF

        // CONTINUE, IF VIEWER IS SET
        if (viewer == null)
            return;

        // *********************************************
        // GAME PAINTING LOGIC
        // *********************************************

        int boardSize = viewer.getSize();

        // Draw board background
        g.setColor(BOARD_BACKGROUND_COLOR);
        Point2D bottomLeft, bottomRight, top;
        bottomLeft = positionPoints.get(new Position(1, 1));
        bottomRight = positionPoints.get(new Position(boardSize + 1, 1));
        top = positionPoints.get(new Position(1, boardSize + 1));
        g.fill(new Polygon(new int[]{(int) bottomLeft.getX(), (int)
                bottomRight.getX(), (int) top.getX()}, new int[]{(int)
                bottomLeft.getY(), (int) bottomRight.getY(), (int) top.getY()
        }, 3));


        // Draw filled fields
        for (PlayerColor pc : PlayerColor.values()) {
            if (pc == PlayerColor.Red)
                g.setColor(RED_PLAYER_COLOR);
            else if (pc == PlayerColor.Green)
                g.setColor(GREEN_PLAYER_COLOR);
            else
                throw new IllegalStateException("only red and green player " +
                        "supported");

            for (Flower f : viewer.getFlowers(pc)) {
                g.fill(flowerToPolygon(f));
            }
        }

        // Draw hover flower
        if (hoverFlower != null) {
            g.setColor(HOVER_COLOR);
            g.fill(flowerToPolygon(hoverFlower));
        }


        // Draw grid
        g.setStroke(new BasicStroke(UNIT * GRID_NEUTRAL_LINE_STRENGTH));
        g.setColor(Color.BLACK);
        for (Map.Entry<Position, Point2D> e : positionPoints.entrySet()) {
            for (Position neighbor : BoardImpl.getNeighborPositions(e.getKey(),
                    boardSize)) {
                g.draw(new Line2D.Double(e.getValue(), positionPoints.get
                        (neighbor)));
            }
        }

        g.setStroke(new BasicStroke(UNIT * GRID_DITCH_LINE_STRENGTH));
        for (Map.Entry<Position, Point2D> e : positionPoints.entrySet()) {
            for (Position neighbor : BoardImpl.getNeighborPositions(e.getKey(),
                    boardSize)) {
                Ditch d = new Ditch(e.getKey(), neighbor);

                // Determine color
                if (viewer.getDitches(PlayerColor.Red).contains(d))
                    g.setColor(RED_PLAYER_COLOR);
                else if (viewer.getDitches(PlayerColor.Green).contains(d))
                    g.setColor(GREEN_PLAYER_COLOR);
                else
                    continue;
                g.draw(new Line2D.Double(e.getValue(), positionPoints.get
                        (neighbor)));
            }
        }

        g.setColor(Color.BLACK);
        // Draw grid points
        double dotSize = UNIT * GRID_DOT_SIZE;
        for (Point2D p : positionPoints.values()) {
            g.fill(new Ellipse2D.Double(p.getX() - dotSize / 2, p.getY() -
                    dotSize / 2, dotSize, dotSize));
        }
    }

    // ------------------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fill(new Rectangle2D.Float(0, 0, parentWindow.getWidth(),
                parentWindow.getHeight()));

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints
                .VALUE_ANTIALIAS_ON);

        render(g2d);
    }
}
