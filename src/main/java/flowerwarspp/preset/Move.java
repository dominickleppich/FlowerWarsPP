package flowerwarspp.preset;

import java.io.*;

public class Move implements Serializable {
    private MoveType type;
    private Flower flowerA, flowerB;
    private Ditch ditch;

    // ------------------------------------------------------------

    public Move(final MoveType type) {
        if (type == MoveType.Flower || type == MoveType.Ditch)
            throw new IllegalArgumentException("flower and ditch moves have " +
                    "separate constructors");

        this.type = type;
    }

    public Move(final Flower a, final Flower b) {
        type = MoveType.Flower;

        if (a == null || b == null)
            throw new IllegalArgumentException("flowers cannot be null");
        flowerA = a;
        flowerB = b;
    }

    public Move(final Ditch b) {
        type = MoveType.Ditch;

        if (b == null)
            throw new IllegalArgumentException("ditch cannot be null");
        ditch = b;
    }

    // ------------------------------------------------------------

    public MoveType getType() {
        return type;
    }

    public Flower getFirstFlower() {
        if (getType() != MoveType.Flower)
            throw new IllegalStateException("cannot be called on type " + type);
        return flowerA;
    }

    public Flower getSecondFlower() {
        if (getType() != MoveType.Flower)
            throw new IllegalStateException("cannot be called on type " + type);
        return flowerB;
    }

    public Ditch getDitch() {
        if (getType() != MoveType.Ditch)
            throw new IllegalStateException("cannot be called on type " + type);
        return ditch;
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        String s = "{";
        switch (getType()) {
            case Flower:
                s += getFirstFlower().toString() + ", " +
                        getSecondFlower().toString();
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
