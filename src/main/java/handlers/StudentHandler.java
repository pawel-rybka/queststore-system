package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ArtifactDao;
import dao.BoughtArtifactDao;
import dao.QuestDao;
import handlers.helpers.ParserFormData;
import model.Artifact;
import model.BoughtArtifact;
import model.Quest;
import model.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.ParameterMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentHandler implements HttpHandler {

    private JtwigModel model;
    private JtwigTemplate template;
    private ArtifactDao aDao = new ArtifactDao();
    private QuestDao qDao = new QuestDao();
    private Map inputs;
    private Artifact artifact;



    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();


        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.println(path);


        if (path.equals("/student")) {
            Student student = new Student(5, "dupa", "dupa1", "586758378", "dupa2", "2qfg43we", 100, 1000);
            model = createModel("templates/student-home-page.twig");
            model.with("student", student);


        } else if (path.equals("/student/student-buy-artifact")) {

            if (method.equals("GET")) {
                try {
                    listArtifacts(httpExchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {
                try {
                    buyArtifact(httpExchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else if (path.equals("/student/student-do-quest")) {

            if (method.equals("GET")) {
                try {
                    listQuestsToDo(httpExchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (method.equals("POST")) {
                try {
                    submitQuest(httpExchange);
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



    private void listArtifacts (HttpExchange httpExchange) throws SQLException {
        model = createModel("templates/student-buy-artifact.twig");
        ArrayList<Artifact> allArtifacts = aDao.getArtifacts();
        ArrayList<Artifact> artifactsForUser = null;
        // TU MUSI BYC PODANY STUDENT ZEBY SPRAWDZIC KTÓRE ARTEFAKTY DLA NIEGO WYPRINTOWAC
//        for(Artifact artifact : allArtifacts) {
//            if(artifact.getPrice() <= student.getCoins()) {
//                artifactsForUser.add(artifact);
//            }
//        }
        model.with("artifacts", artifactsForUser);
    }


    private void buyArtifact(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/student-buy-artifact-2.twig");
        Artifact artifact = aDao.getArtifactById(Integer.valueOf(inputs.get("artifact").toString()));
        model.with("artifact", artifact);
        //TUTAJ MUSI BYC DODANE createBoughtArtifact ALE MUSIMY TU MIEC STUDENTA ZEBY GO PODAC DALEJ
    }


    private void createBoughtArtifact (Student student, Artifact artifact) {
        BoughtArtifactDao baDao = new BoughtArtifactDao();
        String usageDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        BoughtArtifact newBoughtArtifact = new BoughtArtifact(student.getId(), artifact.getId(), usageDate);
        try {
            baDao.addObject(newBoughtArtifact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void listQuestsToDo (HttpExchange httpExchange) throws SQLException {
        model = createModel("templates/student-do-quest.twig");
        ArrayList<Quest> quests = qDao.getQuests();
        model.with("quests", quests);
    }


    private void submitQuest(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/student-do-quest-2.twig");
        Quest quest = qDao.getQuestById(Integer.valueOf(inputs.get("quest").toString()));
        model.with("quest", quest);
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
