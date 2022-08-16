public class TextRequestHandler {

    private final static String CRLF = "\r\n";

    private final RequestHandler requestHandler;

    public TextRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public String handle(String textRequest) {
        Request request = textToRequest(textRequest);
        return responseToString(requestHandler.handle(request));
    }

    private Request textToRequest(String textRequest) {
        String[] parts = textRequest.split(CRLF);
        String command = parts[2];
        int argsCount = (parts.length - 1) / 2 - 1;
        String[] args = new String[argsCount];
        for (int i = 0; i < argsCount; i++) {
            args[i] = parts[4 + i * 2];
        }
        return new Request(command, args);
    }

    private String responseToString(Response response) {
        if (response.getType() == ResponseType.BULK_STRING) {
            if (response.getResponse() == null) {
                return response.getType().getPrefix() + "-1" + CRLF;
            }
            return response.getType().getPrefix() + response.getResponse().length() +
                    CRLF + response.getResponse() + CRLF;
        }
        return response.getType().getPrefix() + response.getResponse() + CRLF;
    }
}
