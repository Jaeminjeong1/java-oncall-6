package oncall.utils;

public enum ErrorMessage {

    ERROR("유효하지 않은 입력 값입니다. 다시 입력해 주세요.");

    private final static String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
