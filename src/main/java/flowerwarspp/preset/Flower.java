package flowerwarspp.preset;

import java.io.*;
import java.util.*;

public class Flower implements Serializable, Comparable<Flower> {
    private static final long serialVersionUID = 1L;

    public static final int COMBINATIONS = Position.COMBINATIONS * 2 * Position.COMBINATIONS;

    // ------------------------------------------------------------

    private Position[] positions;

    // ------------------------------------------------------------

    public Flower(final Position first, final Position second, final Position third) {
        positions = new Position[3];

        setFirst(first);
        setSecond(second);
        setThird(third);

        updateOrder();
    }

    private void updateOrder() {
        Arrays.sort(positions);
    }

    // ------------------------------------------------------------

    public Position getFirst() {
        return positions[0];
    }

    private void setFirst(final Position first) {
        if (first == null)
            throw new IllegalArgumentException("first cannot be null");
        positions[0] = first;
    }

    public Position getSecond() {
        return positions[1];
    }

    private void setSecond(final Position second) {
        if (second == null)
            throw new IllegalArgumentException("second cannot be null");
        positions[1] = second;
    }

    public Position getThird() {
        return positions[2];
    }

    private void setThird(final Position third) {
        if (third == null)
            throw new IllegalArgumentException("third cannot be null");
        positions[2] = third;
    }

    // ------------------------------------------------------------

    public static Flower parseFlower(String string) {
        if (string == null || string.equals(""))
            throw new FlowerFormatException("cannot parse empty string");

        if (!string.startsWith("{") || !string.endsWith("}"))
            throw new FlowerFormatException("wrong outer format! correct format is: {POSITION,POSITION,POSITION}");

        // The splitting needs to be merged afterwards
        String[] parts = string.substring(1, string.length() - 1)
                               .split(",");

        if (parts.length != 6)
            throw new FlowerFormatException(
                    "wrong number of positions! correct format is: {POSITION,POSITION,POSITION}");

        try {
            // Merge splitted substrings accordingly
            return new Flower(Position.parsePosition(parts[0] + ',' + parts[1]),
                    Position.parsePosition(parts[2] + ',' + parts[3]),
                    Position.parsePosition(parts[4] + ',' + parts[5]));
        } catch (PositionFormatException e) {
            throw new FlowerFormatException("unable to parse flower positions", e);
        }
    }

    @Override
    public int hashCode() {
        // Distributes perfectly for valid moves. For arbitrary flowers of
        // valid position combinations which does not result in correct
        // flowers collisions might occur!
        return getFirst().hashCode() * 2 + (getSecond().getColumn() > getFirst().getColumn() ? 0 : 1);
    }

    @Override
    public int compareTo(Flower flower) {
        // Due to the unique ordering it is easy to calculate
        if (!getFirst().equals(flower.getFirst()))
            return getFirst().compareTo(flower.getFirst());
        else
            return getSecond().getColumn() - flower.getSecond()
                                                   .getColumn();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Flower))
            return false;
        Flower l = (Flower) o;
        return Arrays.equals(positions, l.positions);
    }

    @Override
    public String toString() {
        return "{" + getFirst().toString() + "," + getSecond().toString() + "," + getThird().toString() + "}";
    }
}
