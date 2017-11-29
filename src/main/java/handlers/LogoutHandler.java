package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.User;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;
import java.util.UUID;

public class LogoutHandler implements HttpHandler{

    private Map<UUID, User> sessionsData;

    public LogoutHandler(Map<UUID,User> sessionsData) {
        this.sessionsData = sessionsData;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
        UUID sessionId = UUID.fromString(cookie.getValue());

        this.sessionsData.remove(sessionId);
        httpExchange.getResponseHeaders().add("Location", "/login");
        httpExchange.sendResponseHeaders(302, -1);
    }
}
