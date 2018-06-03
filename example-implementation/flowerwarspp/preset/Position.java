package flowerwarspp.preset;

import javafx.geometry.*;

import java.io.*;

public class Position implements Serializable, Comparable<Position> {
    public static final int MAX_VALUE = 31;
    public static final int COMBINATIONS = MAX_VALUE * MAX_VALUE;

    // ------------------------------------------------------------

    private int column, row;

    // ------------------------------------------------------------

    public Position(final int column, final int row) {
        setColumn(column);
        setRow(row);
    }

    // ------------------------------------------------------------

    public int getColumn() {
        return column;
    }

    private void setColumn(final int column) {
        if (column <= 0 || column > MAX_VALUE)
            throw new IllegalArgumentException("illegal column value: " + column);
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    private void setRow(final int row) {
        if (row <= 0 || row > MAX_VALUE)
            throw new IllegalArgumentException("illegal row value: " + row);
        this.row = row;
    }

    // ------------------------------------------------------------

    public static Position parsePositions(String string) {
        if (string == null || string.equals(""))
            throw new PositionFormatException("cannot parse empty string");

        if (!string.startsWith("(") || !string.endsWith(")"))
            throw new PositionFormatException("wrong outer format! correct format is: (COLUMN,ROW)");

        String[] parts = string.substring(1, string.length() - 1).split(",");

        if (parts.length != 2)
            throw new PositionFormatException("wrong number of arguments! correct format is: (COLUMN,ROW)");

        try {
            return new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } catch (NumberFormatException e) {
            throw new PositionFormatException("wrong number format! correct format is: (COLUMN,ROW)");
        }
    }

    @Override
    public int hashCode() {
        return getColumn() * MAX_VALUE + getRow();
    }

    @Override
    public int compareTo(Position p) {
        if (getRow() != p.getRow())
            return getRow() - p.getRow();
        return getColumn() - p.getColumn();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Position))
            return false;
        Position p = (Position) o;
        return getColumn() == p.getColumn() && getRow() == p.getRow();
    }

    @Override
    public String toString() {
        return "(" + getColumn() + "," + getRow() + ")";
    }
}
