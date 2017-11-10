package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.MentorDao;
import model.Mentor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MentorHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();
        JtwigModel model = null;
        JtwigTemplate template = null;

        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.println(path);


        if (path.equals("/mentor")) {
            template = JtwigTemplate.classpathTemplate("static/mentor/mentor-home.html");
            model  = JtwigModel.newModel();
        } else if (path.equals("/mentor/add-quest")) {
            if (method.equals("GET")) {
                template = JtwigTemplate.classpathTemplate("templates/add-quest.twig");
                model  = JtwigModel.newModel();
            } else if (method.equals("POST")) {
                template = JtwigTemplate.classpathTemplate("templates/add-quest-finished.twig");
                model  = JtwigModel.newModel();
            }
        } else if (path.equals("/mentor/add-artifact")) {
            if (method.equals("GET")) {
                template = JtwigTemplate.classpathTemplate("templates/add-artifact.twig");
                model  = JtwigModel.newModel();
            } else if (method.equals("POST")) {
                template = JtwigTemplate.classpathTemplate("templates/add-artifact-finished.twig");
                model  = JtwigModel.newModel();
            }
        }


        response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

}
