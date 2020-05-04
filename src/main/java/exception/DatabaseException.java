package exception;

public class DatabaseException extends Exception{
    ErrorCode code;

    public DatabaseException() {
        super("Error when perform action to database");
        this.code = new ErrorCode(0);
    }

    public DatabaseException(int error_code) {
        super("Error when perform action to database");
        this.code = new ErrorCode(error_code);
    }

    public int getErrorCode() {
        return code.getCode();
    }

    public String getErrorCodeMessage() {
        return code.getMessage();
    }
}
