package diavolopp.preset;

import java.io.*;

public class Position implements Serializable, Comparable<Position> {
    public static final int MAX_COLUMN = 100;
    public static final int MAX_ROW = 100;

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
        if (column <= 0 || column > MAX_COLUMN)
            throw new IllegalArgumentException("illegal column value: " +
                    column);
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    private void setRow(final int row) {
        if (row <= 0 || row > MAX_ROW)
            throw new IllegalArgumentException("illegal row value: " + row);
        this.row = row;
    }

    // ------------------------------------------------------------

    @Override
    public int hashCode() {
        return getColumn() * MAX_COLUMN + getRow();
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
