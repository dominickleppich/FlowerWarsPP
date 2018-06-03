package flowerwarspp.preset;

import java.io.*;
import java.util.*;

public class Move implements Serializable, Comparable<Move> {
    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------------

    private MoveType type;
    private Flower[] flowers;
    private Ditch ditch;

    // ------------------------------------------------------------

    public Move(final MoveType type) {
        if (type == MoveType.Flower || type == MoveType.Ditch)
            throw new IllegalArgumentException("flower and ditch moves have " + "separate constructors");

        this.type = type;
    }

    public Move(final Flower a, final Flower b) {
        type = MoveType.Flower;

        flowers = new Flower[2];

        setFirstFlower(a);
        setSecondFlower(b);

        updateOrder();
    }

    public Move(final Ditch b) {
        type = MoveType.Ditch;

        /*if (b == null)
            throw new IllegalArgumentException("ditch cannot be null");*/
        ditch = b;
    }

    private void updateOrder() {
        Arrays.sort(flowers);
    }

    // ------------------------------------------------------------

    public MoveType getType() {
        return type;
    }

    public Flower getFirstFlower() {
        if (getType() != MoveType.Flower)
            throw new IllegalStateException("cannot be called on type " + type);
        return flowers[0];
    }

    private void setFirstFlower(final Flower first) {
        if (first == null)
            throw new IllegalArgumentException("first flower cannot be null");
        flowers[0] = first;
    }

    public Flower getSecondFlower() {
        if (getType() != MoveType.Flower)
            throw new IllegalStateException("cannot be called on type " + type);
        return flowers[1];
    }

    private void setSecondFlower(final Flower second) {
        if (second == null)
            throw new IllegalArgumentException("second flower cannot be null");
        flowers[1] = second;
    }

    public Ditch getDitch() {
        if (getType() != MoveType.Ditch)
            throw new IllegalStateException("cannot be called on type " + type);
        return ditch;
    }

    // ------------------------------------------------------------

    public static Move parseMove(String string) {
        if (string == null || string.equals(""))
            throw new MoveFormatException("cannot parse empty string");

        if (!string.startsWith("{") || !string.endsWith("}"))
            throw new MoveFormatException(
                    "wrong outer format! correct format is: {MOVE}\nMOVE can be one of the following: Flower, Ditch, \"End\", \"Surrender\"");

        // The splitting needs to be merged afterwards
        String[] parts = string.substring(1, string.length() - 1)
                               .split(",");


        try {
            switch (parts.length) {
                // Primitive move type
                case 1:
                    switch (parts[0]) {
                        case "End":
                            return new Move(MoveType.End);
                        case "Surrender":
                            return new Move(MoveType.Surrender);
                        default:
                            throw new MoveFormatException("unknown move type");
                    }
                    // Ditch move
                case 4:
                    return new Move(Ditch.parseDitch(string.substring(1, string.length() - 1)));
                // Flower move
                case 12:
                    // Do some split merging here
                    return new Move(Flower.parseFlower(
                            parts[0] + ',' + parts[1] + ',' + parts[2] + ',' + parts[3] + ',' + parts[4] + ',' + parts[5]),
                            Flower.parseFlower(
                                    parts[6] + ',' + parts[7] + ',' + parts[8] + ',' + parts[9] + ',' + parts[10] + ',' + parts[11]));
                default:
                    throw new MoveFormatException(
                            "Illegal number of arguments! correct format is: {MOVE}\nMOVE can be one of the following: Flower, Ditch, \"End\", \"Surrender\"");
            }
        } catch (FlowerFormatException e) {
            throw new MoveFormatException("unable to parse flower", e);
        } catch (DitchFormatException e) {
            throw new MoveFormatException("unable to parse ditch", e);
        }
    }

    @Override
    public int hashCode() {
        switch (type) {
            case Flower:
                return getFirstFlower().hashCode() * Flower.COMBINATIONS + getSecondFlower().hashCode();
            case Ditch:
                return -ditch.hashCode();
            case Surrender:
                return Integer.MAX_VALUE;
            case End:
                // There is no other default case
            default:
                return Integer.MIN_VALUE;
        }
    }

    @Override
    public int compareTo(Move move) {
        // User defined ordering
        int myType = type.ordinal();
        int otherType = move.type.ordinal();
        if (myType != otherType)
            return myType - otherType;
        else {
            switch (type) {
                case Flower:
                    if (!getFirstFlower().equals(move.getFirstFlower()))
                        return getFirstFlower().compareTo(move.getFirstFlower());
                    else
                        return getSecondFlower().compareTo(move.getSecondFlower());
                case Ditch:
                    return ditch.compareTo(move.ditch);
                default:
                    return 0;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Move))
            return false;
        Move m = (Move) o;
        if (type != m.type)
            return false;
        switch (type) {
            case Flower:
                return getFirstFlower().equals(m.getFirstFlower()) && getSecondFlower().equals(m.getSecondFlower());
            case Ditch:
                return ditch.equals(m.ditch);
            default:
                // Type is already the same
                return true;
        }
    }

    @Override
    public String toString() {
        String s = "{";
        switch (getType()) {
            case Flower:
                s += getFirstFlower().toString() + ", " + getSecondFlower().toString();
                break;
            case Ditch:
                s += ditch.toString();
                break;
            default:
                s += type.toString();
        }
        return s + "}";
    }
}
