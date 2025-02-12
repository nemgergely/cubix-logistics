package hu.cubix.logistics.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String tableName) {
        super("The " + tableName + " with the given ID was not found in the database");
    }
}
