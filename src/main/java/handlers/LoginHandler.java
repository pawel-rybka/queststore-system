package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AdminDao;
import dao.MentorDao;
import dao.StudentDao;
import handlers.helpers.ParserFormData;
import model.Admin;
import model.Mentor;
import model.User;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class LoginHandler implements HttpHandler {

    private Map<String, User> sessionsData;

    public LoginHandler(Map<String, User> sessionsData) {
        this.sessionsData = sessionsData;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        if (method.equals("POST")) {

            String formData = getFormData(httpExchange);
            Map<String, String> inputs = ParserFormData.parseFormData(formData);
            User user = null;

            try {
                user = getUserFromDB(inputs);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (user != null) {
                String sessionId = UUID.randomUUID().toString();
                this.sessionsData.put(sessionId, user);

                HttpCookie cookie = new HttpCookie("sessionId", sessionId.toString());
                System.out.println(cookie.toString());
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                redirectLoggedUser(user, httpExchange);
            } else {
                httpExchange.getResponseHeaders().add("Location", "/login" );
                httpExchange.sendResponseHeaders(302, -1);
            }

        } else if (method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/static/index.html");
            response = template.render(null);
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
        }

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getFormData(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }

    private User getUserFromDB(Map inputs) throws SQLException {
        User user;
        AdminDao adminDao = new AdminDao();
        MentorDao mentorDao = new MentorDao();
        StudentDao studentDao = new StudentDao();

        String email = inputs.get("email").toString();
        String password = inputs.get("password").toString();

        user = adminDao.getAdminByLogin(email, password);
        if (user == null) {
            user = mentorDao.getMentorByLogin(email, password);
            if (user == null) {
                user = studentDao.getStudentByLogin(email, password);
            }
        }
        return user;
    }

    private void redirectLoggedUser(User user, HttpExchange httpExchange) throws IOException {

        if (user instanceof Admin) {
            httpExchange.getResponseHeaders().add("Location", "/admin" );
            httpExchange.sendResponseHeaders(302, -1);
        } else if (user instanceof Mentor) {
            httpExchange.getResponseHeaders().add("Location", "/mentor" );
            httpExchange.sendResponseHeaders(302, -1);
        } else {
            httpExchange.getResponseHeaders().add("Location", "/student" );
            httpExchange.sendResponseHeaders(302, -1);
        }
    }
}
