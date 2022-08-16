public class Response {

    private final ResponseType type;
    private final String response;

    public Response(ResponseType type, String response) {
        this.type = type;
        this.response = response;
    }

    public ResponseType getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }
}
