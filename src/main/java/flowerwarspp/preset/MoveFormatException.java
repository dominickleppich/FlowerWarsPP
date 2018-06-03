package flowerwarspp.preset;

public class MoveFormatException extends IllegalArgumentException{
    public MoveFormatException(String msg) {
        super(msg);
    }

    public MoveFormatException(String msg, Throwable e) {
        super(msg, e);
    }
}
