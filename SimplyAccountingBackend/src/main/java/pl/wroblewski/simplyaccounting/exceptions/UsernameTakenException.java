package pl.wroblewski.simplyaccounting.exceptions;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException() {
        super("This username is already taken.");
    }
}
