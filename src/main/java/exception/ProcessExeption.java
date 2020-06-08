package exception;

public class ProcessExeption extends Exception{
    ErrorCode2 code;

    public ProcessExeption() {
        super("Error when processing an action ");
        this.code = new ErrorCode2(0);
    }

    public ProcessExeption(int error_code) {
        super("Error when processing an action ");
        this.code = new ErrorCode2(error_code);
    }

    public int getErrorCode() {
        return code.getCode();
    }

    public String getErrorCodeMessage() {
        return code.getMessage();
    }
}
