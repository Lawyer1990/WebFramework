package enums;

public enum AssertMessages {
    MSG_ELEMENT_NOT_FOUND("Element is not found"),
    MSG_FILE_NOT_FOUND("File is not found"),
    MSG_LOCATOR_NOT_SPECIFIED_IN_PROPERTIES("locator {} is not specified in properties"),
    MSG_FAILED_LOAD_FILE("Failed to load file"),
    MSG_TEXT_MESSAGEBOX_IS_NOT_CORRECT("Text in messagebox is not correct");


    private String message;

    AssertMessages(String name) {
        this.message = name;
    }

    public String getValue() {
        return message;
    }
}
