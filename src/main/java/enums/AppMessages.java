package enums;

public enum AppMessages {
    REGISTRATION_CONGRATULATION("CONGRATULATIONS, YOUR TRIAL ACCOUNT AWAITS"),
    DEAR("Dear "),
    LOGGED_IN_SALESFORCE("You recently logged in to Salesforce from a browser or app that we don't recognize");

    private String message;

    AppMessages(String name) {
        this.message = name;
    }

    public String getValue() {
        return message;
    }
}
