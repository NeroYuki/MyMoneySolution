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
            "invalid transaction",//9
            "invalid days input",//10
            "invalid Value input",//11
            "invalid balance Name input", // 12
            "description too long", //13
            "Balance selected is null",//14
            "Failure to excecute code from database",//15
            "Expire days is before begin day",//16
            "Null balance",//17
            "threshold is negative",
            "Incorect usename or password",//19,
            "Fill correct username, email and dayofbirth"


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
