package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
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

import static handlers.helpers.Utilities.redirectLoggedUser;

public class StudentHandler implements HttpHandler {

    private JtwigModel model;
    private JtwigTemplate template;
    private ArtifactDao aDao = new ArtifactDao();
    private QuestDao qDao = new QuestDao();
    private StudentDao sDao = new StudentDao();
    private Map inputs;
    private Artifact artifact;
    private Map<String, User> sessionsData;

    public StudentHandler(Map<String, User> sessionsData) {
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
        User user = this.sessionsData.get(sessionId);
        if (this.sessionsData.containsKey(sessionId) && user instanceof Student) {

            if (path.equals("/student")) {
//                student = this.sessionsData.get(sessionId);
                model = createModel("templates/student-home-page.twig");
                model.with("student", user);


            } else if (path.equals("/student/student-buy-artifact")) {
//                student = this.sessionsData.get(sessionId);

                if (method.equals("GET")) {
                    try {
                        listArtifacts((Student) user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if (method.equals("POST")) {
                    try {
                        buyArtifact(httpExchange, (Student) user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/student/student-do-quest")) {
//                User student = this.sessionsData.get(sessionId);

                if (method.equals("GET")) {
                    try {
                        listQuestsToDo();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if (method.equals("POST")) {
                    try {
                        submitQuest(httpExchange, (Student) user );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, response.getBytes().length);

        } else if (this.sessionsData.containsKey(sessionId)){
            redirectLoggedUser(user, httpExchange);

        } else {
            httpExchange.getResponseHeaders().add("Location", "/login");
            httpExchange.sendResponseHeaders(302, -1);
        }

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }




    private void listArtifacts (Student student) throws SQLException {
        model = createModel("templates/student-buy-artifact.twig");
        ArrayList<Artifact> allArtifacts = aDao.getArtifactToBuyByUser(student.getCoins());

        model.with("artifacts", allArtifacts);
    }


    private void buyArtifact(HttpExchange httpExchange, Student student) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/student-buy-artifact-2.twig");
        artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("artifact").toString()));
        model.with("artifact", artifact);

        student.setCoins(student.getCoins() - artifact.getPrice());
        sDao.updateData(student);

        this.createBoughtArtifact(student, artifact);
    }


    private void createBoughtArtifact (Student student, Artifact artifact) {
        BoughtArtifactDao baDao = new BoughtArtifactDao();
        String usageDate = "0";
        BoughtArtifact newBoughtArtifact = new BoughtArtifact(student.getId(), artifact.getId(), usageDate);
        try {
            baDao.addObject(newBoughtArtifact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void listQuestsToDo () throws SQLException {
        model = createModel("templates/student-do-quest.twig");
        ArrayList<Quest> quests = qDao.getQuests();
        model.with("quests", quests);
    }


    private void submitQuest(HttpExchange httpExchange, Student student) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/student-do-quest-2.twig");
        Quest quest = qDao.getQuestById(Integer.valueOf(inputs.get("quest").toString()));
        model.with("quest", quest);

        this.createCompletedQuests(student, quest);
    }


    private void createCompletedQuests (Student student, Quest quest) {
        CompletedQuestDao compQuestDao = new CompletedQuestDao();
        String completeDate = "0";
        CompletedQuest newCompletedQuest = new CompletedQuest(student.getId(), quest.getId(), completeDate);
        try {
            compQuestDao.addObject(newCompletedQuest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Map<String, String> getInputs(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        inputs = ParserFormData.parseFormData(formData);

        return inputs;
    }


    private JtwigModel createModel(String path) {
        template = JtwigTemplate.classpathTemplate(path);
        model  = JtwigModel.newModel();
        return model;
    }
}
