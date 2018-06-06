package flowerwarspp.boarddisplay;

import flowerwarspp.preset.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

class BoardDisplayPanel extends JPanel {

    // ------------------------------------------------------------
    // ------------------------------------------------------------
    // * Customizable settings *

    private static final double BORDER_SIZE = 0.2;

    private static final double BOARD_GRID_POINT_SIZE = 0.3;
    private static final Color BOARD_GRID_POINT_LABEL_COLOR = new Color(255, 255, 255);
    private static final float BOARD_GRID_POINT_LABEL_FONT_SIZE = 0.1f;
    private static final float BOARD_GRID_NEUTRAL_LINE_STRENGTH = 0.1f;
    private static final float BOARD_GRID_DITCH_LINE_STRENGTH = 0.05f;

    private static final Color RED_PLAYER_COLOR = new Color(124, 45, 51);
    private static final Color BLUE_PLAYER_COLOR = new Color(43, 43, 93);
    private static final Color RED_HIGHLIGHT_COLOR = new Color(255, 77, 85);
    private static final Color BLUE_HIGHLIGHT_COLOR = new Color(85, 85, 255);

    private static final Color STATUS_TEXT_COLOR_A = new Color(197, 177, 42);
    private static final Color STATUS_TEXT_COLOR_B = new Color(197, 88, 20);
    private static final float STATUS_TEXT_SIZE = 1.5f;
    private static final Color TEXT_BOX_BORDER_COLOR = new Color(70, 70, 70);
    private static final float TEXT_MARGIN = 0.2f;
    private static final float TEXT_BORDER_SIZE = 0.05f;
    private static final float POINT_TEXT_SIZE = 0.5f;
    private static final float TEXT_BACKGROUND_ARC = 0.3f;

    // ------------------------------------------------------------
    // ------------------------------------------------------------

    private BoardDisplay parentWindow;
    private Viewer viewer;
    private int WIDTH, HEIGHT;
    private float UNIT;

    private Map<Position, Point2D> positionPoints;
    private Map<Polygon, Flower> polygonFlowerMap;
    private Map<Polygon, Ditch> polygonDitchMap;

    private Status status;
    private Move performedMove;

    // ------------------------------------------------------------

    public BoardDisplayPanel(BoardDisplay parentWindow) {
        this.parentWindow = parentWindow;
        setDoubleBuffered(true);
        update();
    }

    // ------------------------------------------------------------

    private static Set<Position> getNeighborPositions(Position position, int boardSize) {
        int c = position.getColumn();
        int r = position.getRow();
        Set<Position> neighbors = new HashSet<>();
        if (c + r <= boardSize + 1) {
            neighbors.add(new Position(c + 1, r));
            neighbors.add(new Position(c, r + 1));
        }
        if (c > 1) {
            neighbors.add(new Position(c - 1, r));
            neighbors.add(new Position(c - 1, r + 1));
        }
        if (r > 1) {
            neighbors.add(new Position(c, r - 1));
            neighbors.add(new Position(c + 1, r - 1));
        }

        return neighbors;
    }

    public synchronized void reset() {
        performedMove = null;
        status = null;
        repaint();
    }

    public synchronized void showStatus(Status status) {
        this.status = status;
    }

    // ------------------------------------------------------------

    public void showPerformedMove(Move move) {
        this.performedMove = move;
    }

    public void update() {
        WIDTH = parentWindow.getContentPane()
                            .getWidth();
        HEIGHT = parentWindow.getContentPane()
                             .getHeight();
        //        logger.debug("Constants updated to new window constraints: WIDTH =
        // " + WIDTH + ", HEIGHT = " + HEIGHT);

        updateUIPositions();
    }

    private synchronized void updateUIPositions() {
        if (viewer == null)
            return;

        int boardSize = viewer.getSize();
        double fieldWidth =
                Math.min(WIDTH / boardSize, (HEIGHT / boardSize) / Math.sin(Math.toRadians(60))) * (1 - BORDER_SIZE);
        UNIT = (float) fieldWidth;
        double fieldHeight = Math.sin(Math.toRadians(60)) * fieldWidth;

        double xOffset = (WIDTH - boardSize * fieldWidth - 2 * UNIT * BORDER_SIZE) / 2 + UNIT * BORDER_SIZE;
        double yOffset = (HEIGHT - boardSize * fieldHeight - 2 * UNIT * BORDER_SIZE) / 2 + UNIT * BORDER_SIZE;

        positionPoints = new HashMap<>();

        // Create game board points
        for (int r = 0; r <= boardSize; r++) {
            for (int c = 0; c <= boardSize; c++) {
                if (r + c > boardSize)
                    continue;
                Point2D p = new Point2D.Double(xOffset + c * fieldWidth + r * fieldWidth / 2,
                        HEIGHT - yOffset - r * fieldHeight);
                positionPoints.put(new Position(c + 1, r + 1), p);
            }
        }

        // Create the polygon flower mapping
        polygonFlowerMap = new HashMap<>();
        for (int c = 1; c <= boardSize; c++) {
            for (int r = 1; r <= boardSize; r++) {
                if (c + r <= boardSize + 1) {
                    Flower f = new Flower(new Position(c, r), new Position(c, r + 1), new Position(c + 1, r));
                    polygonFlowerMap.put(flowerToPolygon(f), f);
                }
                if (c + r <= boardSize) {
                    Flower f = new Flower(new Position(c + 1, r + 1), new Position(c, r + 1), new Position(c + 1, r));
                    polygonFlowerMap.put(flowerToPolygon(f), f);
                }
            }
        }

        // Create the polygon ditch mapping
        polygonDitchMap = new HashMap<>();
        for (int c = 1; c <= boardSize + 1; c++) {
            for (int r = 1; r <= boardSize; r++) {
                // For each point create 3 ditches, if available
                if (c + r <= boardSize + 1) {
                    Ditch d1 = new Ditch(new Position(c, r), new Position(c, r + 1));
                    Ditch d2 = new Ditch(new Position(c, r), new Position(c + 1, r));
                    polygonDitchMap.put(ditchToPolygon(d1), d1);
                    polygonDitchMap.put(ditchToPolygon(d2), d2);
                }
                if (c > 1 && c + r <= boardSize + 2) {
                    Ditch d = new Ditch(new Position(c, r), new Position(c - 1, r + 1));
                    polygonDitchMap.put(ditchToPolygon(d), d);
                }
            }
        }
    }

    // ------------------------------------------------------------

    public synchronized void setViewer(Viewer viewer) {
        this.viewer = viewer;
        updateUIPositions();
    }

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
            if (e.getKey()
                 .contains(point))
                return e.getValue();
        }
        return null;
    }

    private Polygon ditchToPolygon(Ditch hoverDitch) {
        // Calculate polygon depending on orientation
        Position first = hoverDitch.getFirst();
        Position second = hoverDitch.getSecond();

        int[] x = null, y = null;
        double angle = 0.0;

        // 0 degree
        if (first.getRow() == second.getRow()) {
            angle = 0.0;
        }
        // 60 degree
        else if (first.getColumn() == second.getColumn()) {
            angle = 120.0;
        }
        // 120 degree
        else {
            angle = 60.0;
        }

        Point2D pStart1, pStart2, pEnd1, pEnd2;
        Point2D firstPoint = positionPoints.get(first);
        Point2D secondPoint = positionPoints.get(second);

        pStart1 = rotateAroundCenter(
                new Point2D.Double(firstPoint.getX(), firstPoint.getY() + UNIT * BOARD_GRID_NEUTRAL_LINE_STRENGTH),
                firstPoint, angle);
        pStart2 = rotateAroundCenter(
                new Point2D.Double(firstPoint.getX(), firstPoint.getY() - UNIT * BOARD_GRID_NEUTRAL_LINE_STRENGTH),
                firstPoint, angle);
        pEnd1 = rotateAroundCenter(
                new Point2D.Double(secondPoint.getX(), secondPoint.getY() - UNIT * BOARD_GRID_NEUTRAL_LINE_STRENGTH),
                secondPoint, angle);
        pEnd2 = rotateAroundCenter(
                new Point2D.Double(secondPoint.getX(), secondPoint.getY() + UNIT * BOARD_GRID_NEUTRAL_LINE_STRENGTH),
                secondPoint, angle);

        x = new int[]{(int) pStart1.getX(), (int) pStart2.getX(), (int) pEnd1.getX(), (int) pEnd2.getX()};
        y = new int[]{(int) pStart1.getY(), (int) pStart2.getY(), (int) pEnd1.getY(), (int) pEnd2.getY()};

        return new Polygon(x, y, 4);
    }

    private Ditch pointToDitch(Point point) {
        for (Map.Entry<Polygon, Ditch> e : polygonDitchMap.entrySet()) {
            if (e.getKey()
                 .contains(point))
                return e.getValue();
        }
        return null;
    }

    // ------------------------------------------------------------

    private Point2D rotateAroundCenter(Point2D p, Point2D center, double degree) {
        double x = p.getX();
        double y = p.getY();
        double cx = center.getX();
        double cy = center.getY();

        x -= cx;
        y -= cy;

        double newx = x * Math.cos(Math.toRadians(degree)) - y * Math.sin(Math.toRadians(degree));
        double newy = x * Math.sin(Math.toRadians(degree)) + y * Math.cos(Math.toRadians(degree));

        return new Point2D.Double(newx + cx, newy + cy);
    }

    private synchronized void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Float(0, 0, WIDTH, HEIGHT));

        // CONTINUE, IF VIEWER IS SET
        if (viewer == null)
            return;

        // *********************************************
        // GAME PAINTING LOGIC
        // *********************************************

        int boardSize = viewer.getSize();

        // Draw board background
        g.setColor(Color.WHITE);
        Point2D bottomLeft, bottomRight, top;
        bottomLeft = positionPoints.get(new Position(1, 1));
        bottomRight = positionPoints.get(new Position(boardSize + 1, 1));
        top = positionPoints.get(new Position(1, boardSize + 1));
        g.fill(new Polygon(new int[]{(int) bottomLeft.getX(), (int) bottomRight.getX(), (int) top.getX()}, new int[]{
                (int) bottomLeft.getY(), (int) bottomRight.getY(), (int) top.getY()
        }, 3));


        // Show last performed move
        Flower lastFlower1 = null;
        Flower lastFlower2 = null;
        if (performedMove != null && performedMove.getType() == MoveType.Flower) {
            lastFlower1 = performedMove.getFirstFlower();
            lastFlower2 = performedMove.getSecondFlower();
        }

        // Draw filled fields
        for (PlayerColor pc : PlayerColor.values()) {
            Color color, lastColor;
            if (pc == PlayerColor.Red) {
                color = RED_PLAYER_COLOR;
                lastColor = RED_HIGHLIGHT_COLOR;
            } else if (pc == PlayerColor.Blue) {
                color = BLUE_PLAYER_COLOR;
                lastColor = BLUE_HIGHLIGHT_COLOR;
            } else
                throw new IllegalStateException("only red and blue player supported");

            for (Flower f : viewer.getFlowers(pc)) {
                g.setColor((f.equals(lastFlower1) || f.equals(lastFlower2)) ? lastColor : color);
                g.fill(flowerToPolygon(f));
            }
        }

        // Draw grid
        g.setStroke(new BasicStroke(UNIT * BOARD_GRID_NEUTRAL_LINE_STRENGTH));
        g.setColor(Color.GRAY);
        for (Map.Entry<Position, Point2D> e : positionPoints.entrySet()) {
            for (Position neighbor : getNeighborPositions(e.getKey(), boardSize)) {
                g.draw(new Line2D.Double(e.getValue(), positionPoints.get(neighbor)));
            }
        }

        // Last ditch
        Ditch lastDitch = null;
        if (performedMove != null && performedMove.getType() == MoveType.Ditch)
            lastDitch = performedMove.getDitch();

        g.setStroke(new BasicStroke(UNIT * BOARD_GRID_DITCH_LINE_STRENGTH));
        for (Map.Entry<Position, Point2D> e : positionPoints.entrySet()) {
            for (Position neighbor : getNeighborPositions(e.getKey(), boardSize)) {
                Ditch d = new Ditch(e.getKey(), neighbor);

                // Determine color
                if (viewer.getDitches(PlayerColor.Red)
                          .contains(d)) {
                    g.setColor(d.equals(lastDitch) ? RED_HIGHLIGHT_COLOR : RED_PLAYER_COLOR);
                    g.draw(new Line2D.Double(e.getValue(), positionPoints.get(neighbor)));
                } else if (viewer.getDitches(PlayerColor.Blue)
                                 .contains(d)) {
                    g.setColor(d.equals(lastDitch) ? BLUE_HIGHLIGHT_COLOR : BLUE_PLAYER_COLOR);
                    g.draw(new Line2D.Double(e.getValue(), positionPoints.get(neighbor)));
                }
            }
        }

        g.setColor(Color.GRAY);
        // Draw grid points
        double dotSize = UNIT * BOARD_GRID_POINT_SIZE;
        for (Point2D p : positionPoints.values()) {
            g.fill(new Ellipse2D.Double(p.getX() - dotSize / 2, p.getY() - dotSize / 2, dotSize, dotSize));
        }

        // Draw grid numbers
        g.setFont(g.getFont()
                   .deriveFont(Font.BOLD, UNIT * BOARD_GRID_POINT_LABEL_FONT_SIZE));
        FontMetrics fm = g.getFontMetrics();
        if (BOARD_GRID_POINT_LABEL_COLOR != null) {
            g.setColor(BOARD_GRID_POINT_LABEL_COLOR);

            for (Map.Entry<Position, Point2D> e : positionPoints.entrySet()) {
                Point2D p = e.getValue();
                Position pos = e.getKey();
                String s = "" + pos.getColumn() + "," + pos.getRow();

                if (p == null)
                    continue;

                float x = (float) (p.getX() - fm.stringWidth(s) / 2);
                float y = (float) (p.getY() + fm.getAscent() / 2);

                g.drawString(s, x, y);
            }
        }

        Font backupFont = g.getFont();

        float uiScale = WIDTH / 10;

        int redPoints = viewer.getPoints(PlayerColor.Red);
        int bluePoints = viewer.getPoints(PlayerColor.Blue);

        // Red points
        showTextBox(g, WIDTH - uiScale * 3.2f, uiScale * 0.2f, uiScale * 1.0f, uiScale, Color.WHITE, null,
                TEXT_BOX_BORDER_COLOR, RED_PLAYER_COLOR,
                backupFont.deriveFont(redPoints > bluePoints ? Font.BOLD : Font.PLAIN, uiScale * POINT_TEXT_SIZE),
                "" + redPoints);
        // Blue points
        showTextBox(g, WIDTH - uiScale * 1.6f, uiScale * 0.2f, uiScale * 1.0f, uiScale, Color.WHITE, null,
                TEXT_BOX_BORDER_COLOR, BLUE_PLAYER_COLOR,
                backupFont.deriveFont(bluePoints > redPoints ? Font.BOLD : Font.PLAIN, uiScale * POINT_TEXT_SIZE),
                "" + bluePoints);

        if (status != null)
            drawStatus(g, backupFont.deriveFont(Font.BOLD, uiScale * STATUS_TEXT_SIZE));
    }

    private void showTextBox(Graphics2D g, float x, float y, float minWidth, float scale, Color textColor,
                             Paint textPaint, Color borderColor, Color backgroundColor, Font font, String text) {
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        float width = textWidth;
        if (width < minWidth)
            width = minWidth;
        float height = textHeight + 2 * scale * TEXT_MARGIN;
        width += 2 * scale * TEXT_MARGIN;

        Shape s = new RoundRectangle2D.Double(x, y, width, height, scale * TEXT_BACKGROUND_ARC,
                scale * TEXT_BACKGROUND_ARC);

        if (backgroundColor != null) {
            g.setColor(backgroundColor);
            g.fill(s);
        }

        if (borderColor != null) {
            g.setColor(borderColor);
            g.setStroke(new BasicStroke(scale * TEXT_BORDER_SIZE));
            g.draw(s);
        }

        if (text != null && (textColor != null ^ textPaint != null)) {
            if (textColor != null)
                g.setColor(textColor);
            else
                g.setPaint(textPaint);
            g.drawString(text, x + (width - textWidth) / 2,
                    y + scale * TEXT_MARGIN + (textHeight + fm.getAscent()) / 2);
        }
    }

    private void drawStatus(Graphics2D g, Font font) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g.setColor(Color.BLACK);
        g.fill(new Rectangle2D.Float(0, 0, WIDTH, HEIGHT));

        g.setFont(font);
        Paint paint = new GradientPaint(0, 0, STATUS_TEXT_COLOR_A, WIDTH, HEIGHT, STATUS_TEXT_COLOR_B);
        g.setPaint(paint);

        FontMetrics fm = g.getFontMetrics();

        String statusText = "";
        switch (status) {
            case RedWin:
                statusText = "Red won!";
                break;
            case BlueWin:
                statusText = "Blue won!";
                break;
            case Draw:
                statusText = "Draw!";
                break;
            case Illegal:
                statusText = "Illegal state!!!";
                break;
            default:
                throw new IllegalStateException("Unknown state!");
        }

        int textWidth = fm.stringWidth(statusText);

        g.drawString(statusText, WIDTH / 2 - textWidth / 2, HEIGHT / 2 + fm.getAscent() / 2);
    }

    // ------------------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.BLACK);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        render(g2d);
    }
}
