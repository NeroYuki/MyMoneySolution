package exception;

public class ErrorCode2 {
    private int code = 0;
    private String message = "";
    private static final String[] errorMessagePool = {
            "Unknown failure when performing action",       //0
            "Name String khong hop le",                     //1
            "FileName String khong hop le",                 //2
            "Info String khong hop le",                     //3
            "Type String khong hop le",                     //4
            "Gia tri nhap khong hop le",                    //5
            "Loi khi dang nhap" ,                           //6
            "Loi khi xu li ham tu data",                    //7
            "input value = null"           ,                 //8
            "invalid transaction"//9
    };

    public ErrorCode2(int code) {
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
