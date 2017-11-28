package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.StudentDao;
import handlers.helpers.ParserFormData;
import model.Mentor;
import model.Student;
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
    private JtwigModel model;
    private JtwigTemplate template;
    private StudentDao sDao = new StudentDao();
    private Map inputs;
    private Student student;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();


        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.println(path);


        if (path.equals("/mentor")) {
            template = JtwigTemplate.classpathTemplate("static/mentor/mentor-home.html");
            model = JtwigModel.newModel();
        } else if (path.equals("/mentor/add-quest")) {
            if (method.equals("GET")) {
                template = JtwigTemplate.classpathTemplate("templates/add-quest.twig");
                model = JtwigModel.newModel();
            } else if (method.equals("POST")) {
                template = JtwigTemplate.classpathTemplate("templates/add-quest-finished.twig");
                model = JtwigModel.newModel();
            }

        } else if (path.equals("/mentor/add-artifact")) {
            if (method.equals("GET")) {
                template = JtwigTemplate.classpathTemplate("templates/add-artifact.twig");
                model = JtwigModel.newModel();
            } else if (method.equals("POST")) {
                template = JtwigTemplate.classpathTemplate("templates/add-artifact-finished.twig");
                model = JtwigModel.newModel();
            }

        } else if (path.equals("/mentor/add-student")) {

            if (method.equals("GET")) {
                model = createModel("templates/add-student.twig");

            } else if (method.equals("POST")) {
                try {
                    createStudent(httpExchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
//                if (method.equals("GET")) {
//                    template = JtwigTemplate.classpathTemplate("templates/add-student.twig");
//                    model = JtwigModel.newModel();
//                } else if (method.equals("POST")) {
//                    template = JtwigTemplate.classpathTemplate("templates/add-student-finished.twig");
//                    model = JtwigModel.newModel();
//                }



            } else if (path.equals("/mentor/see-students-wallet")) {
            if (method.equals("GET")) {

                model = createModel("templates/see-all-students.twig");
                ArrayList<Student> students = null;
                try {
                    students = sDao.getStudents();
                    model.with("students", students);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {
                inputs = getInputs(httpExchange);
                model = createModel("templates/see-student.twig");

                try {
                    Student student = sDao.getStudentById(Integer.valueOf(inputs.get("student").toString()));
                    model.with("student", student);
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

    private void createStudent(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        System.out.println(inputs);
        model = createModel("templates/add-student-finished.twig");
        String firstName = String.valueOf(inputs.get("first"));
        String lastName = String.valueOf(inputs.get("last"));
        String phoneNumber = String.valueOf(inputs.get("phone"));
        String email = String.valueOf(inputs.get("email"));
        String password = String.valueOf(inputs.get("passw"));
        Student student = new Student(firstName, lastName, phoneNumber, email, password);
        sDao.addObject(student);
    }

    private Map<String, String> getInputs(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map<String, String> inputs = ParserFormData.parseFormData(formData);

        return inputs;
    }

    private JtwigModel createModel(String path) {
        template = JtwigTemplate.classpathTemplate(path);
        model  = JtwigModel.newModel();
        return model;
    }

}
