package project;

public class UninitializedValueException extends RuntimeException {
    public UninitializedValueException() {
        super("");
    }
}
