package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ArtifactDao;
import model.Artifact;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        JtwigModel model = null;
        JtwigTemplate template = null;

        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.println(path);

        if (path.equals("/student")) {
            template = JtwigTemplate.classpathTemplate("static/student/student-home.html");
            model = JtwigModel.newModel();

        } else if (path.equals("/student/student-buy-artifact")) {

            if (method.equals("GET")) {
                template = JtwigTemplate.classpathTemplate("templates/student-buy-artifact.twig");
                model = JtwigModel.newModel();
                ArtifactDao aDao = new ArtifactDao();
                ArrayList<Artifact> artifacts = null;
                try {
                    artifacts = aDao.getArtifacts();
                    model.with("artifacts", artifacts);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {
                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();

                Map inputs = parseFormData(formData);
                ArtifactDao aDao = new ArtifactDao();
                template = JtwigTemplate.classpathTemplate("templates/student-buy-artifact-2.twig");
                model = JtwigModel.newModel();
                try {
                    Artifact artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("artifact").toString()));
                    model.with("artifact", artifact);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
