package application;

import com.sun.net.httpserver.HttpServer;
import handlers.*;

import java.net.InetSocketAddress;

public class App {


    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8010), 0);

        server.createContext("/admin", new AdminHandler());
        server.createContext("/static", new Static());
        server.createContext("/login", new LoginHandler());
        server.createContext("/mentor", new MentorHandler());
        server.createContext("/student", new StudentHandler());
        server.setExecutor(null);

        server.start();
        }

}
