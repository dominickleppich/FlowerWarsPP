package flowerwarspp.ui.graphical;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.slf4j.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.stream.*;

public class UIPanel extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(UIPanel.class);

    // ------------------------------------------------------------
    // * Customizable settings *

    private static final Color END_MOVE_COLOR = new Color(53, 11, 71);
    private static final Color BACKGROUND_COLOR_A = new Color(116, 116, 116);
    private static final Color BACKGROUND_COLOR_B = new Color(67, 67, 67);
    private static final Color BACKGROUND_RED_COLOR_A = new Color(197, 110, 30);
    private static final Color BACKGROUND_RED_COLOR_B = new Color(200, 34, 14);
    private static final Color BACKGROUND_BLUE_COLOR_A = new Color(37, 166, 197);
    private static final Color BACKGROUND_BLUE_COLOR_B = new Color(45, 73, 200);
    private static final double BORDER_SIZE = 0.2;

    private static final Color BOARD_BACKGROUND_COLOR = new Color(120, 157, 52);
    private static final Color BLOCKED_FLOWER_COLOR = new Color(64, 93, 41);
    private static final Color POSSIBLE_DITCH_COLOR = new Color(120, 157, 52);
    private static final Color BOARD_GRID_LINE_COLOR = new Color(75, 53, 34);
    private static final Color BOARD_GRID_POINT_COLOR = new Color(75, 53, 34);
    private static final double BOARD_GRID_POINT_SIZE = 0.3;
    private static final Color BOARD_GRID_POINT_LABEL_COLOR = new Color(255, 255, 255);
    private static final float BOARD_GRID_POINT_LABEL_FONT_SIZE = 0.1f;
    private static final float BOARD_GRID_NEUTRAL_LINE_STRENGTH = 0.1f;
    private static final float BOARD_GRID_DITCH_LINE_STRENGTH = 0.05f;

    private static final Color RED_PLAYER_COLOR = new Color(124, 45, 51);
    private static final Color BLUE_PLAYER_COLOR = new Color(43, 43, 93);
    private static final Color RED_HIGHLIGHT_COLOR = new Color(255, 77, 85);
    private static final Color BLUE_HIGHLIGHT_COLOR = new Color(85, 85, 255);
    private static final Color RED_HOVER_COLOR = new Color(215, 161, 165);
    private static final Color BLUE_HOVER_COLOR = new Color(122, 122, 154);
    private static final float HOVER_ALPHA = 0.8f;

    private static final Color STATUS_TEXT_COLOR_A = new Color(197, 177, 42);
    private static final Color STATUS_TEXT_COLOR_B = new Color(197, 88, 20);
    private static final float STATUS_TEXT_SIZE = 1.5f;
    private static final Color TEXT_BOX_BORDER_COLOR = new Color(70, 70, 70);
    private static final float TEXT_MARGIN = 0.2f;
    private static final float TEXT_BORDER_SIZE = 0.05f;
    private static final float POINT_TEXT_SIZE = 0.5f;
    private static final float END_TEXT_SIZE = 0.2f;
    private static final float TEXT_BACKGROUND_ARC = 0.3f;

    // ------------------------------------------------------------

    private UIWindow parentWindow;
    private Viewer viewer;
    private Object moveWaitingMonitor = new Object();
    private int WIDTH, HEIGHT;
    private float UNIT;

    private Map<Position, Point2D> positionPoints;
    private Map<Polygon, Flower> polygonFlowerMap;
    private Map<Polygon, Ditch> polygonDitchMap;
    private Set<Flower> blockedFlowers;
    private Color hoverColor;
    private Flower hoverFlower;
    private Ditch hoverDitch;

    private Collection<Move> possibleMoves;
    private PlayerColor turn;
    private boolean inputEnabled = false;
    private Move move;
    private Flower moveFirstFlower;
    private Flower moveSecondFlower;
    private Ditch moveDitch;

    private Status status;
    private Move performedMove;

    // ------------------------------------------------------------

    public UIPanel(UIWindow parentWindow) {
        this.parentWindow = parentWindow;
        setDoubleBuffered(true);
        update();

        addMouseListener(new MouseAdapter() {
            @Override
            public synchronized void mouseClicked(MouseEvent mouseEvent) {
                if (!inputEnabled)
                    return;

                // Select with left mouse button
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    String p = "";
                    if (hoverDitch != null) {
                        p = hoverDitch.toString();
                        moveDitch = hoverDitch;
                        createMove();
                    }
                    if (hoverFlower != null) {
                        p += hoverFlower.toString();
                        if (moveFirstFlower == null) {
                            moveFirstFlower = hoverFlower;
                            updateBlockedFlowers();
                            repaint();
                        } else {
                            moveSecondFlower = hoverFlower;
                            createMove();
                        }
                    }

                    logger.debug("Clicked: " + mouseEvent.getX() + ", " + mouseEvent.getY() + " [" + p + "]");
                }
                // Clear with right mouse button
                else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                    moveSecondFlower = null;
                    moveDitch = null;

                    clearHover();
                    clearFlowerSelection();
                    updateBlockedFlowers();
                    repaint();

                    logger.debug("Input cleared");
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public synchronized void mouseMoved(MouseEvent mouseEvent) {
                if (!inputEnabled)
                    return;

                Flower lastHoverFlower = hoverFlower;
                Ditch lastHoverDitch = hoverDitch;

                clearHover();

                if (moveFirstFlower == null)
                    hoverDitch = pointToDitch(mouseEvent.getPoint());
                if (hoverDitch == null)
                    hoverFlower = pointToFlower(mouseEvent.getPoint());
                else
                    hoverFlower = null;

                // Check if this is a valid move
                if (hoverDitch != null) {
                    Move hoverMove = new Move(hoverDitch);
                    if (!possibleMoves.contains(hoverMove)) {
                        hoverDitch = null;
                        return;
                    }
                } else if (hoverFlower != null) {
                    // Check if it is the first flower
                    if (moveFirstFlower == null) {
                        if (possibleMoves.stream()
                                         .filter(move -> move.getType() == MoveType.Flower)
                                         .filter(move -> hoverFlower.equals(move.getFirstFlower()) ||
                                                         hoverFlower.equals(move.getSecondFlower()))
                                         .count() == 0) {
                            hoverFlower = null;
                            return;
                        }
                    } else {
                        Move hoverMove = new Move(moveFirstFlower, hoverFlower);
                        if (!possibleMoves.contains(hoverMove)) {
                            hoverFlower = null;
                            return;
                        }
                    }
                }

                /*repaint((int) (mouseEvent.getX() - UNIT), (int) (mouseEvent.getY() - UNIT), (int) UNIT * 2,
                        (int) UNIT * 2);*/

                // Repaint of something changed
                if (lastHoverFlower != hoverFlower || lastHoverDitch != hoverDitch)
                    repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                UIPanel.this.keyPressed(keyEvent);
            }
        });
        parentWindow.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                UIPanel.this.keyPressed(keyEvent);
            }
        });
    }

    public synchronized void keyPressed(KeyEvent keyEvent) {
        logger.debug("Key pressed: " + keyEvent.getKeyChar() + " [" + keyEvent.getKeyCode() + "]");

        if (inputEnabled) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                logger.debug("Surrender button typed");
                move = new Move(MoveType.Surrender);
                createMove();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                // Ignore end move if not legal
                if (!possibleMoves.contains(new Move(MoveType.End)))
                    return;
                logger.debug("End button typed");
                move = new Move(MoveType.End);
                createMove();
            }
        }
    }

    // ------------------------------------------------------------

    private void clearHover() {
        // Clear old positions
        if (hoverFlower != null) {
            Rectangle bounds = flowerToPolygon(hoverFlower).getBounds();
            hoverFlower = null;
            repaint(bounds);
        }
        if (hoverDitch != null) {
            Rectangle bounds = ditchToPolygon(hoverDitch).getBounds();
            hoverDitch = null;
            repaint(bounds);
        }
    }

    private void clearFlowerSelection() {
        if (moveFirstFlower != null) {
            Rectangle bounds = flowerToPolygon(moveFirstFlower).getBounds();
            moveFirstFlower = null;
            repaint(bounds);
        }
    }

    private synchronized void createMove() {
        clearHover();
        if (moveFirstFlower != null && moveSecondFlower != null && !moveFirstFlower.equals(moveSecondFlower) &&
            moveDitch == null)
            move = new Move(moveFirstFlower, moveSecondFlower);
        else if (moveFirstFlower == null && moveSecondFlower == null && moveDitch != null)
            move = new Move(moveDitch);

        logger.debug("moveFirstFlower: " + moveFirstFlower + ", " + "moveSecondFlower: " + moveSecondFlower + ", " +
                     "moveDitch: " + moveDitch + ", move: " + move);

        clearFlowerSelection();
        moveFirstFlower = null;
        moveSecondFlower = null;
        moveDitch = null;

        if (move != null)
            confirmMove();
    }

    private synchronized void confirmMove() {

        logger.debug("Move confirmed");
        notify();
    }

    public synchronized Move request() throws InterruptedException {
        move = null;
        hoverColor = viewer.getTurn() == PlayerColor.Red ? RED_HOVER_COLOR : BLUE_HOVER_COLOR;
        possibleMoves = viewer.getPossibleMoves();
        updateBlockedFlowers();
        inputEnabled = true;
        repaint();
        turn = viewer.getTurn();
        logger.debug("Start waiting for ui to create a move");
        while (move == null) {
            logger.debug("Wait...");
            wait();
        }
        inputEnabled = false;
        possibleMoves = null;
        turn = null;
        blockedFlowers = null;
        hoverFlower = null;
        hoverDitch = null;
        repaint();

        logger.debug("UI created move: " + move);
        return move;
    }

    public synchronized void reset() {
        performedMove = null;
        status = null;
        repaint();
    }

    public synchronized void showStatus(Status status) {
        this.status = status;
    }


    public void showPerformedMove(Move move) {
        this.performedMove = move;
    }

    // ------------------------------------------------------------

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

    private synchronized void updateBlockedFlowers() {
        blockedFlowers = BoardImpl.getAllPossibleFlowers(viewer.getSize());

        if (moveFirstFlower == null) {
            for (Move m : possibleMoves) {
                if (m.getType() != MoveType.Flower)
                    continue;

                Flower f1 = m.getFirstFlower();
                Flower f2 = m.getSecondFlower();

                blockedFlowers.remove(f1);
                blockedFlowers.remove(f2);
            }
        } else {
            for (Move m : possibleMoves) {
                if (m.getType() != MoveType.Flower)
                    continue;

                Flower f1 = m.getFirstFlower();
                Flower f2 = m.getSecondFlower();

                if (moveFirstFlower.equals(f1))
                    blockedFlowers.remove(f2);
                else if (moveFirstFlower.equals(f2))
                    blockedFlowers.remove(f1);
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

    // ------------------------------------------------------------

    private synchronized void render(Graphics2D g) {
        /*int midX = WIDTH / 2;
        int midY = HEIGHT / 2;
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Float(midX - 10, midY - 10, 20, 20));
        g.setColor(Color.RED);
        g.draw(new Rectangle2D.Float(10, 10, WIDTH - 20, HEIGHT - 20));*/

        Color a = BACKGROUND_COLOR_A;
        Color b = BACKGROUND_COLOR_B;

        if (turn != null) {
            if (turn == PlayerColor.Red) {
                a = BACKGROUND_RED_COLOR_A;
                b = BACKGROUND_RED_COLOR_B;
            } else {
                a = BACKGROUND_BLUE_COLOR_A;
                b = BACKGROUND_BLUE_COLOR_B;
            }
        }

        Paint backgroundPaint = new GradientPaint(0, 0, a, WIDTH, HEIGHT, b);
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
        drawBackground(g, boardSize);
        drawBlockedFields(g);
        drawBoardFlowers(g);
        drawHoverFlowers(g);
        drawGrid(g, boardSize);
        drawDitches(g, boardSize);
        drawGridPoints(g);
        drawGridNumbers(g);
        Font backupFont = g.getFont();
        float uiScale = WIDTH / 10;
        drawEndButton(g, backupFont, uiScale);
        drawPoints(g, backupFont, uiScale);

        if (status != null)
            drawStatus(g, backupFont.deriveFont(Font.BOLD, uiScale * STATUS_TEXT_SIZE));
    }

    private void drawBackground(Graphics2D g, int boardSize) {
        // Draw board background
        g.setColor(BOARD_BACKGROUND_COLOR);
        Point2D bottomLeft, bottomRight, top;
        bottomLeft = positionPoints.get(new Position(1, 1));
        bottomRight = positionPoints.get(new Position(boardSize + 1, 1));
        top = positionPoints.get(new Position(1, boardSize + 1));
        g.fill(new Polygon(new int[]{(int) bottomLeft.getX(), (int) bottomRight.getX(), (int) top.getX()}, new int[]{
                (int) bottomLeft.getY(), (int) bottomRight.getY(), (int) top.getY()
        }, 3));
    }

    private void drawBlockedFields(Graphics2D g) {
        // Draw blocked fields
        if (blockedFlowers != null) {
            g.setColor(BLOCKED_FLOWER_COLOR);
            for (Flower f : blockedFlowers)
                g.fill(flowerToPolygon(f));
        }
    }

    private void drawBoardFlowers(Graphics2D g) {
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
    }

    private void drawHoverFlowers(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, HOVER_ALPHA));

        // Draw hover flower
        if (hoverFlower != null) {
            g.setColor(hoverColor);
            g.fill(flowerToPolygon(hoverFlower));
        }
        // Draw already selected first flower
        if (moveFirstFlower != null) {
            g.setColor(hoverColor);
            g.fill(flowerToPolygon(moveFirstFlower));
        }

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    private void drawGrid(Graphics2D g, int boardSize) {
        /*// Draw grid
        g.setStroke(new BasicStroke(UNIT * BOARD_GRID_NEUTRAL_LINE_STRENGTH));
        g.setColor(BOARD_GRID_LINE_COLOR);
        for (Map.Entry<Position, Point2D> e : positionPoints.entrySet()) {
            for (Position neighbor : BoardImpl.getNeighborPositions(e.getKey(), boardSize)) {
                g.draw(new Line2D.Double(e.getValue(), positionPoints.get(neighbor)));
            }
        }*/
        g.setStroke(new BasicStroke(UNIT * BOARD_GRID_NEUTRAL_LINE_STRENGTH));
        g.setColor(BOARD_GRID_LINE_COLOR);
        for (int i = 1; i <= boardSize; i++) {
            Position columnStart = new Position(i, 1);
            Position rowStart = new Position(1, i);
            Position columnEnd = new Position(i, boardSize - i + 2);
            Position rowEnd = new Position(boardSize - i + 2, i);
            Position diagStart = new Position(i + 1, 1);
            Position diagEnd = new Position(1, i + 1);
            g.draw(new Line2D.Double(positionPoints.get(columnStart), positionPoints.get(columnEnd)));
            g.draw(new Line2D.Double(positionPoints.get(rowStart), positionPoints.get(rowEnd)));
            g.draw(new Line2D.Double(positionPoints.get(diagStart), positionPoints.get(diagEnd)));
        }
    }

    private void drawDitches(Graphics2D g, int boardSize) {
        // Determine possible ditches
        Set<Ditch> possibleDitches = null;
        if (possibleMoves != null)
            possibleDitches = possibleMoves.stream()
                                           .filter(move -> move.getType() == MoveType.Ditch)
                                           .map(move -> move.getDitch())
                                           .collect(Collectors.toSet());

        // Last ditch
        Ditch lastDitch = null;
        if (performedMove != null && performedMove.getType() == MoveType.Ditch)
            lastDitch = performedMove.getDitch();

        g.setStroke(new BasicStroke(UNIT * BOARD_GRID_DITCH_LINE_STRENGTH));
        for (Map.Entry<Position, Point2D> e : positionPoints.entrySet()) {
            for (Position neighbor : BoardImpl.getNeighborPositions(e.getKey(), boardSize)) {
                Ditch d = new Ditch(e.getKey(), neighbor);

                // Show possible ditches
                if (possibleDitches != null && possibleDitches.contains(d)) {
                    g.setColor(POSSIBLE_DITCH_COLOR);
                    g.draw(new Line2D.Double(e.getValue(), positionPoints.get(neighbor)));
                }

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

                // Draw hover ditch
                if (d.equals(hoverDitch)) {
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, HOVER_ALPHA));
                    g.setColor(hoverColor);
                    g.draw(new Line2D.Double(e.getValue(), positionPoints.get(neighbor)));
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
            }
        }
    }

    private void drawPoints(Graphics2D g, Font backupFont, float uiScale) {
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
    }

    private void drawEndButton(Graphics2D g, Font backupFont, float uiScale) {
        // End
        if (possibleMoves != null && possibleMoves.contains(new Move(MoveType.End)))
            showTextBox(g, (float) (uiScale * BORDER_SIZE), (float) (uiScale * BORDER_SIZE), 0.0f, uiScale, Color.WHITE,
                    null, TEXT_BOX_BORDER_COLOR, END_MOVE_COLOR,
                    backupFont.deriveFont(Font.ITALIC, uiScale * END_TEXT_SIZE), " Press SPACE to end ");
    }

    private void drawGridNumbers(Graphics2D g) {
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
    }

    private void drawGridPoints(Graphics2D g) {
        g.setColor(BOARD_GRID_POINT_COLOR);
        // Draw grid points
        double dotSize = UNIT * BOARD_GRID_POINT_SIZE;
        for (Point2D p : positionPoints.values()) {
            g.fill(new Ellipse2D.Double(p.getX() - dotSize / 2, p.getY() - dotSize / 2, dotSize, dotSize));
        }
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
        if (status == null || status == Status.Ok)
            return;

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
