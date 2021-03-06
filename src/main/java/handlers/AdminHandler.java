package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import dao.ClassDao;
import dao.LevelDao;
import dao.MentorDao;
import handlers.helpers.ParserFormData;
import model.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import static handlers.helpers.Utilities.redirectLoggedUser;


public class AdminHandler implements HttpHandler {
    private JtwigModel model;
    private JtwigTemplate template;
    private MentorDao mDao = new MentorDao();
    private LevelDao lDao = new LevelDao();
    private ClassDao cDao = new ClassDao();
    private Map inputs;
    private Mentor mentor;
    private Map<String, User> sessionsData;

    public AdminHandler(Map<String, User> sessionsData) {
        this.sessionsData = sessionsData;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
        String sessionId = cookie.getValue();
        User user = this.sessionsData.get(sessionId);

        if (this.sessionsData.containsKey(sessionId) && user instanceof Admin) {

            if (path.equals("/admin")) {
                showHomePage(user);

            } else if (path.equals("/admin/add-mentor")) {

                if (method.equals("GET")) {
                    model = createModel("templates/add-mentor.twig");

                } else if (method.equals("POST")) {
                    try {
                        addMentor(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/see-mentor")) {

                if (method.equals("GET")) {
                    try {
                        listMentors();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if (method.equals("POST")) {
                    try {
                        seeMentor(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/edit-mentor")) {

                if (method.equals("POST")) {
                    try {
                        chooseMentorToEdit(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/edit-mentor-finished")) {
                if (method.equals("POST")) {
                    try {
                        updateMentorData(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/remove-mentor")) {
                if (method.equals("POST")) {
                    try {
                        removeMentor(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/classes")) {
                if (method.equals("GET")) {
                    try {
                        listClasses();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            } else if (path.equals("/admin/class-info")) {
                if (method.equals("POST")) {
                    try {
                        seeClassInfo(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/remove-class")) {
                if (method.equals("POST")) {
                    try {
                        removeClass(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/edit-class")) {
                if (method.equals("POST")) {
                    try {
                        chooseClassToEdit(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/edit-class-finished")) {
                if (method.equals("POST")) {
                    try {
                        updateClassData(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/add-class")) {
                if (method.equals("GET")) {
                    model = createModel("templates/add-class.twig");

                } else if (method.equals("POST")) {
                    try {
                        createClass(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/add-levels")) {

                if (method.equals("GET")) {
                    model = createModel("templates/add-levels.twig");

                } else if (method.equals("POST")) {
                    try {
                        createLevel(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/see-all-levels")) {

                if (method.equals("GET")) {
                    try {
                        listLevels();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/remove-level")) {
                if (method.equals("POST")) {
                    try {
                        removeLevel(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/edit-level")) {
                if (method.equals("POST")) {
                    try {
                        chooseLevelToEdit(httpExchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (path.equals("/admin/edit-level-finished")) {
                if (method.equals("POST")) {
                    try {
                        updateLevelData(httpExchange);
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

    private void showHomePage(User admin) {
        model = createModel("templates/admin/admin-home.twig");
        model.with("admin", admin);
    }

    private void addMentor(HttpExchange httpExchange) throws IOException, SQLException {

        inputs = getInputs(httpExchange);
        model = createModel("templates/add-mentor-finished.twig");
        String firstName = String.valueOf(inputs.get("first"));
        String lastName = String.valueOf(inputs.get("last"));
        String phoneNumber = String.valueOf(inputs.get("phone"));
        String email = String.valueOf(inputs.get("email"));
        String password = String.valueOf(inputs.get("passw"));
        mentor = new Mentor(firstName, lastName, phoneNumber, email, password);
        mDao.addObject(mentor);

    }

    private void listMentors() throws SQLException {
        model = createModel("templates/see-mentor.twig");
        ArrayList<Mentor> mentors = null;
        mentors = mDao.getMentors();
        model.with("mentors", mentors);
    }

    private void listClasses() throws SQLException {
        model = createModel("templates/see-classes.twig");
        ArrayList<Klass> classes = null;
        classes = cDao.getClasses();
        model.with("classes", classes);
    }

    private void seeClassInfo(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/mentors-in-class.twig");
        ArrayList<Mentor> mentors = null;
        mentors = cDao.getMentorsByClassId(Integer.valueOf(inputs.get("class").toString()));
        Klass klass = cDao.getClassById(Integer.valueOf(inputs.get("class").toString()));
        model.with("klass", klass);
        model.with("mentors", mentors);
    }

    private void removeClass(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/class-removed.twig");
        Klass klass = cDao.getClassById(Integer.valueOf(inputs.get("class").toString()));
        cDao.removeObject(klass);
    }

    private void seeMentor(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/see-mentor-2.twig");
        Mentor mentor = mDao.getMentorById(Integer.valueOf(inputs.get("mentor").toString()));
        Klass mentorClass = cDao.getClassByMentor(mentor);
        model.with("mentor", mentor);
        model.with("klass", mentorClass);
    }

    private void chooseClassToEdit(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-class.twig");
        Klass klass = cDao.getClassById(Integer.valueOf(inputs.get("class").toString()));
        model.with("class", klass);
    }

    private void updateClassData(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-class-finished.twig");
        Klass klass = cDao.getClassById(Integer.valueOf(inputs.get("class").toString()));
        klass.setName(String.valueOf(inputs.get("name")));
        cDao.updateData(klass);
    }

    private void chooseMentorToEdit(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-mentor-2.twig");
        Mentor mentor = mDao.getMentorById(Integer.valueOf(inputs.get("mentor").toString()));
        ArrayList<Klass> klasses = cDao.getClasses();
        model.with("mentor", mentor);
        model.with("classes", klasses);
    }

    private void updateMentorData(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-mentor-finished.twig");
        mentor = mDao.getMentorById(Integer.valueOf(inputs.get("id").toString()));
        mentor.setFirstName(String.valueOf(inputs.get("first")));
        mentor.setLastName(String.valueOf(inputs.get("last")));
        mentor.setPhoneNumber(String.valueOf(inputs.get("phone")));
        mentor.setEmail(String.valueOf(inputs.get("email")));
        mentor.setPassword(String.valueOf(inputs.get("passw")));
        mDao.updateData(mentor);
        cDao.removeUserFromClass(mentor);
        cDao.addUserToClass(mentor, Integer.valueOf(inputs.get("class-id").toString()));
    }

    private void removeMentor(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/mentor-removed.twig");
        mentor = mDao.getMentorById(Integer.valueOf(inputs.get("mentor").toString()));
        mDao.removeObject(mentor);
        cDao.removeUserFromClass(mentor);
    }

    private void createLevel(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/add-levels-finished.twig");
        String levelName = String.valueOf(inputs.get("name"));
        Integer exp = Integer.valueOf(String.valueOf(inputs.get("exp")));
        Level level = new Level(levelName, exp);
        lDao.addObject(level);
    }

    private void createClass(HttpExchange httpExchange) throws IOException, SQLException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/add-class-finished.twig");
        String className = String.valueOf(inputs.get("name"));
        Klass klass = new Klass(className);
        cDao.addObject(klass);
    }

    private void listLevels() throws SQLException {
        model = createModel("templates/see-all-levels.twig");
        ArrayList<Level> levels = null;
        levels = lDao.getLevels();
        model.with("levels", levels);
    }

    private void removeLevel(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/level-removed.twig");
        Level level = lDao.getLevelById(Integer.valueOf(inputs.get("level").toString()));
        lDao.removeObject(level);
    }

    private void chooseLevelToEdit(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-level.twig");
        Level level = lDao.getLevelById(Integer.valueOf(inputs.get("level").toString()));
        model.with("level", level);
    }

    private void updateLevelData(HttpExchange httpExchange) throws SQLException, IOException {
        inputs = getInputs(httpExchange);
        model = createModel("templates/edit-level-finished.twig");
        Level level = lDao.getLevelById(Integer.valueOf(inputs.get("level").toString()));
        level.setName(inputs.get("name").toString());
        level.setExpLevel(Integer.valueOf(inputs.get("exp").toString()));
        lDao.updateData(level);
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
