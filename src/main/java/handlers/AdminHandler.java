package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.MentorDao;
import model.Mentor;
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

        }else if (path.equals("/admin/see-mentor")) {
            if (method.equals("GET")) {
                template = JtwigTemplate.classpathTemplate("templates/see-mentor.twig");
                model  = JtwigModel.newModel();
                MentorDao mDao = new MentorDao();
                ArrayList<Mentor> mentors = null;
                try {
                    mentors = mDao.getMentors();
                    model.with("mentors", mentors);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {

                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();

                Map inputs = parseFormData(formData);
                MentorDao mDao = new MentorDao();
                template = JtwigTemplate.classpathTemplate("templates/see-mentor-2.twig");
                model  = JtwigModel.newModel();

                try {
                    Mentor mentor = mDao.getMentorById(Integer.valueOf(inputs.get("mentor").toString()));
                    model.with("mentor", mentor);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }else if (path.equals("/admin/edit-mentor")) {
            if (method.equals("GET")) {
                template = JtwigTemplate.classpathTemplate("templates/edit-mentor.twig");
                model = JtwigModel.newModel();

            } else if (method.equals("POST")) {
                template = JtwigTemplate.classpathTemplate("templates/edit-mentor-2.twig");
                model = JtwigModel.newModel();

            }

        }else if (path.equals("/admin/edit-mentor-finished")){
            if (method.equals("POST")) {
                template = JtwigTemplate.classpathTemplate("templates/edit-mentor-finished.twig");
                model = JtwigModel.newModel();
            }

        }else if (path.equals("/admin/add-class")) {
            template = JtwigTemplate.classpathTemplate("templates/add-class.twig");
            model  = JtwigModel.newModel();

        }else if (path.equals("/admin/add-level")) {
            template = JtwigTemplate.classpathTemplate("templates/add-levels.twig");
            model  = JtwigModel.newModel();

        }



        response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            if (keyValue.length == 1) {
                map.put(keyValue[0], "");
            } else {
                String value = URLDecoder.decode(keyValue[1], "UTF-8");
                map.put(keyValue[0], value);
            }
        }
        return map;
    }
}
