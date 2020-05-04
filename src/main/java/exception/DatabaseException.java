package exception;

public class DatabaseException extends Exception{
    public DatabaseException(){
        super("can't add data to database");
    }
}
