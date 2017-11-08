package handlers;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {


    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/admin", new AdminHandler());
        server.createContext("/static", new Static());
        server.createContext("/login", new LoginHandler());
        server.setExecutor(null);

        server.start();
        }

}