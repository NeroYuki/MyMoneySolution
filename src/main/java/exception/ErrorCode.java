package exception;

public class ErrorCode {
    private int code = 0;
    private String message = "";
    private static final String[] errorMessagePool = {
            "Unknown failure when performing action",
            "Failed to establish connection to database",
            "Incorrect Username or Password"
    };

    public ErrorCode(int code) {
        if (code >= 0 && code < errorMessagePool.length) this.code = code;
        this.message = errorMessagePool[this.code];
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
