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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class MentorHandler implements HttpHandler {
    private JtwigModel model;
    private JtwigTemplate template;
    private StudentDao sDao = new StudentDao();
    private QuestDao qDao = new QuestDao();
    private ArtifactDao aDao = new ArtifactDao();
    private Map inputs;
    private ClassDao cDao = new ClassDao();
    private Student student;
    private Map<String, User> sessionsData;

    public MentorHandler(Map<String, User> sessionsData) {
        this.sessionsData = sessionsData;
    }

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

        } else if (path.equals("/mentor/edit-artifact")) {

            if (method.equals("GET")) {

                model = createModel("templates/see-all-artifacts.twig");
                ArrayList<Artifact> artifacts;
                try {
                    artifacts = aDao.getArtifacts();
                    model.with("artifacts", artifacts);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {
                inputs = getInputs(httpExchange);
                model = createModel("templates/edit-artifact.twig");

                try {
                    Artifact artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("artifact").toString()));
                    model.with("artifact", artifact);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
          
        } else if (path.equals("/mentor/edit-artifact-finished")) {

            if (method.equals("POST")) {
                try {
                    updateArtifactData(httpExchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else if (path.equals("/mentor/edit-quest")) {

            if (method.equals("GET")) {

                model = createModel("templates/see-all-quests.twig");
                ArrayList<Quest> quests;
                try {
                    quests = qDao.getQuests();
                    model.with("quests", quests);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {
                inputs = getInputs(httpExchange);
                System.out.println(inputs);
                model = createModel("templates/edit-quest.twig");


                try {
                    Quest quest = qDao.getQuestById(Integer.valueOf(inputs.get("quest").toString()));
                    model.with("quest", quest);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if (path.equals("/mentor/edit-quest-finished")) {

            if (method.equals("POST")) {
                try {
                    updateQuestData(httpExchange);
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

        }else if (path.equals("/mentor/remove-student")) {
            if (method.equals("POST")) {
                try {
                    removeStudent(httpExchange);
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

    private void updateArtifactData(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-artifact-finished.twig");
        Artifact artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("id").toString()));
        artifact.setName(String.valueOf(inputs.get("name")));
        artifact.setCategory(String.valueOf(inputs.get("category")));
        artifact.setPrice(Integer.parseInt(inputs.get("price").toString()));
        aDao.updateData(artifact);
    }

    private void updateQuestData(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-quest-finished.twig");
        Quest quest = qDao.getQuestById(Integer.valueOf(inputs.get("id").toString()));
        quest.setName(String.valueOf(inputs.get("name")));
        quest.setCategory(String.valueOf(inputs.get("category")));
        quest.setValue(Integer.parseInt(inputs.get("value").toString()));
        qDao.updateData(quest);
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
        student.setCoins(Integer.valueOf(inputs.get("coins").toString()));
        student.setTotalCoins(Integer.valueOf(inputs.get("tcoins").toString()));
        sDao.updateData(student);
        cDao.removeUserFromClass(student);
        cDao.addUserToClass(student, Integer.valueOf(inputs.get("class-id").toString()));
    }

    private void removeStudent(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/student-removed.twig");
        student = sDao.getStudentById(Integer.valueOf(inputs.get("student").toString()));
        sDao.removeObject(student);
        cDao.removeUserFromClass(student);
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
