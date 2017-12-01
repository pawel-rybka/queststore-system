package handlers.helpers;

import com.sun.net.httpserver.HttpExchange;
import model.Admin;
import model.Mentor;
import model.Student;
import model.User;

import java.io.IOException;

public class Utilities {

    public static void redirectLoggedUser(User user, HttpExchange httpExchange) throws IOException {

        if (user instanceof Admin) {
            httpExchange.getResponseHeaders().add("Location", "/admin" );
            httpExchange.sendResponseHeaders(302, -1);
        } else if (user instanceof Mentor) {
            httpExchange.getResponseHeaders().add("Location", "/mentor" );
            httpExchange.sendResponseHeaders(302, -1);
        } else if (user instanceof Student){
            httpExchange.getResponseHeaders().add("Location", "/student" );
            httpExchange.sendResponseHeaders(302, -1);
        }
    }
}
