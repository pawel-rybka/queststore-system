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
    private JtwigModel model;
    private JtwigTemplate template;
    private MentorDao mDao = new MentorDao();
    private Map inputs;
    private Mentor mentor;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.println(path);


        if (path.equals("/admin")) {
            model = createModel("static/admin/admin-home.html");

        }else if (path.equals("/admin/add-mentor")) {

            if (method.equals("GET")) {
                model = createModel("templates/add-mentor.twig");

            } else if (method.equals("POST")) {
                model = createModel("templates/add-mentor-finished.twig");
            }


        }else if (path.equals("/admin/see-mentor")) {
            if (method.equals("GET")) {
                model = createModel("templates/see-mentor.twig");
                ArrayList<Mentor> mentors = null;
                try {
                    mentors = mDao.getMentors();
                    model.with("mentors", mentors);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {
                inputs = getInputs(httpExchange);
                model = createModel("templates/see-mentor-2.twig");

                try {
                    Mentor mentor = mDao.getMentorById(Integer.valueOf(inputs.get("mentor").toString()));
                    model.with("mentor", mentor);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }else if (path.equals("/admin/edit-mentor")) {
            if (method.equals("GET")) {
                model = createModel("templates/edit-mentor.twig");
                ArrayList<Mentor> mentors = null;

                try {
                    mentors = mDao.getMentors();
                    model.with("mentors", mentors);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {
                inputs = getInputs(httpExchange);
                model = createModel("templates/edit-mentor-2.twig");

                try {
                    Mentor mentor = mDao.getMentorById(Integer.valueOf(inputs.get("mentor").toString()));
                    model.with("mentor", mentor);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }else if (path.equals("/admin/edit-mentor-finished")){
            if (method.equals("POST")) {
                inputs = getInputs(httpExchange);
                model = createModel("templates/edit-mentor-finished.twig");

                try {
                    mentor = mDao.getMentorById(Integer.valueOf(inputs.get("id").toString()));
                    mentor.setFirstName(String.valueOf(inputs.get("first")));
                    mentor.setLastName(String.valueOf(inputs.get("last")));
                    mentor.setPhoneNumber(String.valueOf(inputs.get("phone")));
                    mentor.setEmail(String.valueOf(inputs.get("email")));
                    mentor.setPassword(String.valueOf(inputs.get("passw")));
                    mDao.updateData(mentor);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }else if (path.equals("/admin/add-class")) {
            model = createModel("templates/add-class.twig");

        }else if (path.equals("/admin/add-level")) {
            model = createModel("templates/add-levels.twig");

        }



        response = template.render(model);
        System.out.println(response.getBytes().length);
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

    private Map<String, String> getInputs(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        inputs = parseFormData(formData);

        return inputs;
    }

    private JtwigModel createModel(String path) {
        template = JtwigTemplate.classpathTemplate(path);
        model  = JtwigModel.newModel();

        return model;
    }
}
