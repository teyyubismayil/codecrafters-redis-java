public class RequestHandler {

    public static final String PING = "PING";
    public static final String PONG = "PONG";
    public static final String ECHO = "ECHO";
    public static final String GET = "GET";
    public static final String SET = "SET";
    public static final String OK = "OK";
    public static final String UNSUPPORTED_OPERATION = "Unsupported operation";

    private final Database database;

    public RequestHandler(Database database) {
        this.database = database;
    }

    public Response handle(Request request) {
        if (request.getCommand().equalsIgnoreCase(PING)) {
            return new Response(ResponseType.STRING, PONG);
        }
        if (request.getCommand().equalsIgnoreCase(ECHO)) {
            return new Response(ResponseType.STRING, request.getArgs()[0]);
        }
        if (request.getCommand().equalsIgnoreCase(GET)) {
            String value = database.get(request.getArgs()[0]);
            return new Response(ResponseType.BULK_STRING, value);
        }
        if (request.getCommand().equalsIgnoreCase(SET)) {
            Long px = request.getArgs().length > 3
                    ? Long.parseLong(request.getArgs()[3])
                    : null;
            database.set(request.getArgs()[0], request.getArgs()[1], px);
            return new Response(ResponseType.STRING, OK);
        }
        return new Response(ResponseType.ERROR, UNSUPPORTED_OPERATION);
    }
}
