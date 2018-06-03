package flowerwarspp.preset;

import java.io.*;
import java.util.*;

public class Ditch implements Serializable, Comparable<Ditch> {
    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------------

    private Position[] positions;

    // ------------------------------------------------------------

    public Ditch(final Position first, final Position second) {
        positions = new Position[2];

        setFirst(first);
        setSecond(second);

        updateOrder();
    }

    private void updateOrder() {
        Arrays.sort(positions);
    }

    // ------------------------------------------------------------

    public Position getFirst() {
        return positions[0];
    }

    private void setFirst(final Position start) {
        if (start == null)
            throw new IllegalArgumentException("first cannot be null");
        positions[0] = start;
    }

    public Position getSecond() {
        return positions[1];
    }

    private void setSecond(final Position end) {
        if (end == null)
            throw new IllegalArgumentException("second cannot be null");
        positions[1] = end;
    }

    // ------------------------------------------------------------

    public static Ditch parseDitch(String string) {
        if (string == null || string.equals(""))
            throw new DitchFormatException("cannot parse empty string");

        if (!string.startsWith("{") || !string.endsWith("}"))
            throw new DitchFormatException("wrong outer format! correct format is: {POSITION,POSITION}");

        // The splitting needs to be merged afterwards
        String[] parts = string.substring(1, string.length() - 1)
                               .split(",");

        if (parts.length != 4)
            throw new DitchFormatException(
                    "wrong number of positions! correct format is: {POSITION,POSITION}");

        try {
            // Merge splitted substrings accordingly
            return new Ditch(Position.parsePosition(parts[0] + ',' + parts[1]),
                    Position.parsePosition(parts[2] + ',' + parts[3]));
        } catch (PositionFormatException e) {
            throw new DitchFormatException("unable to parse ditch positions", e);
        }
    }

    @Override
    public int hashCode() {
        return getFirst().hashCode() * Position.COMBINATIONS + getSecond().hashCode();
    }

    @Override
    public int compareTo(Ditch ditch) {
        // Due to the unique ordering it is easy to calculate
        if (!getFirst().equals(ditch.getFirst()))
            return getFirst().compareTo(ditch.getFirst());
        else
            return getSecond().getColumn() - ditch.getSecond()
                                                  .getColumn();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Ditch))
            return false;
        Ditch b = (Ditch) o;
        return Arrays.equals(positions, b.positions);
    }

    @Override
    public String toString() {
        return "{" + getFirst().toString() + "," + getSecond().toString() + "}";
    }
}
