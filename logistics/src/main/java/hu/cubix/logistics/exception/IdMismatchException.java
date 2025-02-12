package hu.cubix.logistics.exception;

public class IdMismatchException extends RuntimeException {

    public IdMismatchException() {
        super("The ID specified within the DTO does not match the ID provided in the URI");
    }
}
