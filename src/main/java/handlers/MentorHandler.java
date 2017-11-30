package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ClassDao;
import dao.StudentDao;
import dao.QuestDao;
import dao.ArtifactDao;
import dao.BoughtArtifactDao;
import handlers.helpers.ParserFormData;
import model.*;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class MentorHandler implements HttpHandler {
    private JtwigModel model;
    private JtwigTemplate template;
    private StudentDao sDao = new StudentDao();
    private QuestDao qDao = new QuestDao();
    private ArtifactDao aDao = new ArtifactDao();
    private Map inputs;
    private BoughtArtifactDao baDao = new BoughtArtifactDao();
    private ClassDao cDao = new ClassDao();
    private Student student;
    private Map<String, User> sessionsData;
    private Artifact artifact;

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
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
        String sessionId = cookie.getValue();
        User mentor = sessionsData.get(sessionId);


        if (sessionsData.containsKey(sessionId)) {

            if (path.equals("/mentor")) {
                model = createModel("static/mentor/mentor-home.twig");
                model.with("mentor", mentor);

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

            } else if (path.equals("/mentor/see-artifacts")) {

                if (method.equals("GET")) {
                    try {
                        listAllArtifacts(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if (method.equals("POST")) {
                    try {
                        seeChosenArtifact(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/mentor/edit-artifact")) {
                if (method.equals("POST")) {
                    try {
                        chooseArtifactToEdit(httpExchange);
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

            } else if (path.equals("/mentor/remove-artifact")) {
                if (method.equals("POST")) {
                    try {
                        removeArtifact(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/mentor/see-quests")) {

                if (method.equals("GET")) {
                    try {
                        listAllQuests(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if (method.equals("POST")) {
                    try {
                        seeChosenQuest(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/mentor/edit-quest")) {

                if (method.equals("POST")) {
                    try {
                        chooseQuestToEdit(httpExchange);
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

            } else if (path.equals("/mentor/remove-quest")) {
                if (method.equals("POST")) {
                    try {
                        removeQuest(httpExchange);
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
                    try {
                        listAllStudents(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if (method.equals("POST")) {
                    try {
                        seeChosenStudent(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            } else if (path.equals("/mentor/edit-student")) {
                if (method.equals("POST")) {
                    try {
                        chooseStudentToEdit(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/mentor/edit-student-finished")) {
                if (method.equals("POST")) {
                    try {
                        updateStudentData(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/mentor/remove-student")) {
                if (method.equals("POST")) {
                    try {
                        removeStudent(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/mentor/mark-artifacts")) {

                if (method.equals("GET")) {

                    model = createModel("templates/mark-artifact.twig");
                    ArrayList<BoughtArtifact> boughtArtifacts;
                    try {
                        boughtArtifacts = baDao.getUnmarkedArtifacts();
                        model.with("boughtArtifacts", boughtArtifacts);
                        model.with("sDao", sDao);
                        model.with("aDao", aDao);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if (method.equals("POST")) {
                    inputs = getInputs(httpExchange);
                    model = createModel("templates/mark-artifact-finished.twig");
                    int id = Integer.parseInt(inputs.get("boughtArtifact").toString());
                    try {
                        BoughtArtifact boughtArtifactToUpdate = baDao.getBoughtArtifactById(id);
                        boughtArtifactToUpdate.setUsageDate("1");
                        baDao.updateData(boughtArtifactToUpdate);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            } else {
                httpExchange.getResponseHeaders().add("Location", "/login");
                httpExchange.sendResponseHeaders(302, -1);
            }

            response = template.render(model);

            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private void listAllQuests(HttpExchange httpExchange) throws SQLException {
        model = createModel("templates/see-all-quests.twig");
        ArrayList<Quest> quests = qDao.getQuests();
        model.with("quests", quests);
    }

    private void seeChosenQuest(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/see-quest.twig");
        Quest quest = qDao.getQuestById(Integer.valueOf(inputs.get("quest").toString()));
        model.with("quest", quest);
    }

    private void chooseQuestToEdit(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-quest.twig");
        Quest quest = qDao.getQuestById(Integer.valueOf(inputs.get("quest").toString()));
        model.with("quest", quest);
    }

    private void removeQuest(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/quest-removed.twig");
        Quest quest = qDao.getQuestById(Integer.valueOf(inputs.get("quest").toString()));
        qDao.removeObject(quest);
    }


    private void listAllArtifacts(HttpExchange httpExchange) throws SQLException {
        model = createModel("templates/see-all-artifacts.twig");
        ArrayList<Artifact> artifacts = aDao.getArtifacts();
        model.with("artifacts", artifacts);
    }

    private void seeChosenArtifact(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/see-artifact.twig");
        artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("artifact").toString()));
        model.with("artifact", artifact);
    }

    private void chooseArtifactToEdit(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-artifact.twig");
        artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("artifact").toString()));
        model.with("artifact", artifact);
    }

    private void updateArtifactData(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-artifact-finished.twig");
        artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("artifact").toString()));
        artifact.setName(String.valueOf(inputs.get("name")));
        artifact.setCategory(String.valueOf(inputs.get("category")));
        artifact.setPrice(Integer.parseInt(inputs.get("price").toString()));
        aDao.updateData(artifact);
    }

    private void removeArtifact(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/artifact-removed.twig");
        artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("artifact").toString()));
        aDao.removeObject(artifact);
    }

    private void updateQuestData(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-quest-finished.twig");
        Quest quest = qDao.getQuestById(Integer.valueOf(inputs.get("quest").toString()));
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
        artifact = new Artifact(name, category, price);
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

    private void listAllStudents(HttpExchange httpExchange) throws SQLException {
        model = createModel("templates/see-all-students.twig");
        ArrayList<Student> students = sDao.getStudents();
        model.with("students", students);
    }

    private void seeChosenStudent(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/see-student.twig");
        Student student = sDao.getStudentById(Integer.valueOf(inputs.get("student").toString()));
        Klass studentClass = cDao.getClassByStudent(student);
        model.with("student", student);
        model.with("klass", studentClass);
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
