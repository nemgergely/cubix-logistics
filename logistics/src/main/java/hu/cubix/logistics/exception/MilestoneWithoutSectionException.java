package hu.cubix.logistics.exception;

public class MilestoneWithoutSectionException extends RuntimeException {

    public MilestoneWithoutSectionException() {
        super("This milestone does not belong to any section of this transport plan");
    }
}
