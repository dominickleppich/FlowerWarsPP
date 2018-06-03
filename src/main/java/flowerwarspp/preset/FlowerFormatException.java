package flowerwarspp.preset;

public class FlowerFormatException extends IllegalArgumentException{
    public FlowerFormatException(String msg) {
        super(msg);
    }

    public FlowerFormatException(String msg, Throwable e) {
        super(msg, e);
    }
}
