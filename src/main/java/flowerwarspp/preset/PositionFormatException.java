package flowerwarspp.preset;

public class PositionFormatException extends IllegalArgumentException{
    public PositionFormatException(String msg) {
        super(msg);
    }

    public PositionFormatException(String msg, Throwable e) {
        super(msg, e);
    }
}
