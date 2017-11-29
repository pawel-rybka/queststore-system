package application;

import com.sun.net.httpserver.HttpServer;
import handlers.*;
import model.User;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class App {


    public static void main(String[] args) throws Exception {
        Map<String, User> sessionsData = new HashMap<>();
        HttpServer server = HttpServer.create(new InetSocketAddress(8010), 0);

        server.createContext("/login", new LoginHandler(sessionsData));
        server.createContext("/logout", new LogoutHandler(sessionsData));
        server.createContext("/admin", new AdminHandler(sessionsData));
        server.createContext("/static", new Static());
        server.createContext("/mentor", new MentorHandler(sessionsData));
        server.createContext("/student", new StudentHandler(sessionsData));
        server.setExecutor(null);

        server.start();
    }

}
