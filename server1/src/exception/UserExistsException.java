package exception;

public class UserExistsException extends Exception {
    public UserExistsException() {
        super("Пользователь уже существует.");
    }
}
