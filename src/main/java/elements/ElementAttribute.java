package elements;

/**
 * Declares the constant used in element attribute names
 *
 */
public enum ElementAttribute {
    A("./a"),
    DIV("./div"),
    DIV_DIV("./div/div"),
    SPAN("./span"),
    INPUT("./input"),
    INHERITED("inherited"),
    ID("id"),
    CLASS("class"),
    STYLE("style"),
    VALUE("value"),
    EXPANDED("aria-expanded"),
    DATA_ASSIGNMENT("data-assignment"),
    TITLE("title"),
    TEXT("textContent"),
    DATA_NULL("data-null"),
    XLINK_HREF("xlink:href"),
    DATA_TUID("data-tuid"),
    ARIA_SELECTED("aria-selected"),
    SELECT("select"),
    DISABLED_LINK("disabled-link"),
    HREF("href"),
    DISABLED("disabled"),
    COLOR("color"),
    NO_COLOR("#000"),
    FALSE("false"),
    TRUE("true"),
    EXPANDER("expander"),
    HIDE("hide"),
    I("./i/*"),
    TR("/tr"),
    CHECKED("checked"),
    TD("./td");

    private String name;

    ElementAttribute(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
