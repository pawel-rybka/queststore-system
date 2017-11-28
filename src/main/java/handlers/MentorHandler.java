package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ClassDao;
import dao.StudentDao;
import dao.QuestDao;
import dao.ArtifactDao;
import handlers.helpers.ParserFormData;
import model.*;
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
    private QuestDao qDao = new QuestDao();
    private ArtifactDao aDao = new ArtifactDao();
    private Map inputs;
    private ClassDao cDao = new ClassDao();
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
                model = createModel("templates/add-quest.twig");

            } else if (method.equals("POST")) {
                try {
                    createQuest(httpExchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        } else if (path.equals("/mentor/add-artifact")) {

            if (method.equals("GET")) {
                model = createModel("templates/add-artifact.twig");

            } else if (method.equals("POST")) {
                try {
                    createArtifact(httpExchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

        } else if (path.equals("/mentor/see-students")) {
            if (method.equals("GET")) {

                model = createModel("templates/see-all-students.twig");
                ArrayList<Student> students;
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
                    Klass studentClass = cDao.getClassByStudent(student);
                    model.with("student", student);
                    model.with("klass", studentClass);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        }else if (path.equals("/mentor/edit-student")) {
            if (method.equals("POST")) {
                try {
                    chooseStudentToEdit(httpExchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }else if (path.equals("/mentor/edit-student-finished")){
            if (method.equals("POST")) {
                try {
                    updateStudentData(httpExchange);
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

    private void createArtifact(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/add-artifact-finished.twig");
        String name = String.valueOf(inputs.get("name"));
        String category = String.valueOf(inputs.get("category"));
        Integer price = Integer.parseInt(inputs.get("price").toString());
        Artifact artifact = new Artifact(name, category, price);
        aDao.addObject(artifact);
    }


    private void createQuest(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/add-quest-finished.twig");
        String name = String.valueOf(inputs.get("name"));
        String category = String.valueOf(inputs.get("category"));
        Integer value = Integer.parseInt(inputs.get("value").toString());
        Quest quest = new Quest(name, category, value);
        qDao.addObject(quest);
    }

    private void createStudent(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/add-student-finished.twig");
        String firstName = String.valueOf(inputs.get("first"));
        String lastName = String.valueOf(inputs.get("last"));
        String phoneNumber = String.valueOf(inputs.get("phone"));
        String email = String.valueOf(inputs.get("email"));
        String password = String.valueOf(inputs.get("passw"));
        Student student = new Student(firstName, lastName, phoneNumber, email, password);
        sDao.addObject(student);
    }

    private void chooseStudentToEdit(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-student-2.twig");
        student = sDao.getStudentById(Integer.valueOf(inputs.get("student").toString()));
        ArrayList<Klass> klasses = cDao.getClasses();
        model.with("student", student);
        model.with("classes", klasses);
    }

    private void updateStudentData(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-student-finished.twig");
        student = sDao.getStudentById(Integer.valueOf(inputs.get("id").toString()));
        student.setFirstName(String.valueOf(inputs.get("first")));
        student.setLastName(String.valueOf(inputs.get("last")));
        student.setPhoneNumber(String.valueOf(inputs.get("phone")));
        student.setEmail(String.valueOf(inputs.get("email")));
        student.setPassword(String.valueOf(inputs.get("passw")));
        sDao.updateData(student);
        cDao.removeUserFromClass(student);
        cDao.addUserToClass(student, Integer.valueOf(inputs.get("class-id").toString()));
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
