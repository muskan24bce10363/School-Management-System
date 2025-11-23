

public class SchoolManagementException extends RuntimeException {
    private final ErrorCode errorCode;
    
    public SchoolManagementException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public SchoolManagementException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}