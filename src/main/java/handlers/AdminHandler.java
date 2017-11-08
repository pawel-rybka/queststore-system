package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class AdminHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();
        JtwigModel model = null;
        JtwigTemplate template = null;

        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.println(path);


        if (path.equals("/admin")) {
            template = JtwigTemplate.classpathTemplate("static/admin/admin-home.html");
            model  = JtwigModel.newModel();

        }else if (path.equals("/admin/add-mentor")) {

            if (method.equals("GET")) {
                template = JtwigTemplate.classpathTemplate("templates/add-mentor.twig");
                model  = JtwigModel.newModel();

            } else if (method.equals("POST")) {
                template = JtwigTemplate.classpathTemplate("templates/add-mentor-finished.twig");
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
