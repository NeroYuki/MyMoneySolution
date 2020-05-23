package exception;

public class ErrorCode {
    private int code = 0;
    private String message = "";
    private static final String[] errorMessagePool = {
            "Unknown failure when performing action",       //0
            "Failed to establish connection to database",   //1
            "Incorrect Username or Password",               //2
            "Error happen when registering new user",       //3
            "Error happen when registering new balance",    //4
            "Error happen when registering new budget",     //5
            "Error happen when registering new loan",       //6
            "Error happen when registering new saving",     //7
            "Error happen when registering new transaction",     //8
            "Error happen when registering new category"    //9
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
