public enum ResponseType {
    STRING("+"),
    ERROR("-"),
    INTEGER(":"),
    BULK_STRING("$");

    private final String prefix;

    ResponseType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
