package flowerwarspp.preset;

public class DitchFormatException extends IllegalArgumentException{
    public DitchFormatException(String msg) {
        super(msg);
    }

    public DitchFormatException(String msg, Throwable e) {
        super(msg, e);
    }
}
