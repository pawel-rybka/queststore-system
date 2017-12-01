package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AdminDao;
import dao.MentorDao;
import dao.StudentDao;
import handlers.helpers.ParserFormData;
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

import static handlers.helpers.Utilities.redirectLoggedUser;

public class LoginHandler implements HttpHandler {

    private Map<String, User> sessionsData;

    public LoginHandler(Map<String, User> sessionsData) {
        this.sessionsData = sessionsData;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

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

                HttpCookie cookie = new HttpCookie("sessionId", sessionId);
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                redirectLoggedUser(user, httpExchange);
            } else {
                httpExchange.getResponseHeaders().add("Location", "/login" );
                httpExchange.sendResponseHeaders(302, -1);
            }

        } else if (method.equals("GET")) {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

            if (cookieStr != null) {
                String sessionId = getSessionIdFromCookie(cookieStr);

                if (this.sessionsData.containsKey(sessionId)) {
                    User user = this.sessionsData.get(sessionId);
                    redirectLoggedUser(user, httpExchange);

                } else {
                    response = createResponseWithLoginPage();
                    httpExchange.sendResponseHeaders(200, response.getBytes().length);
                }

            } else {
                response = createResponseWithLoginPage();
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
            }
        }

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String createResponseWithLoginPage() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/static/index.html");
        return template.render(null);
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

    private String getSessionIdFromCookie(String cookieStr) {
        String[] cookiePairs = cookieStr.split("=");

        int i = 0;
        while(!cookiePairs[i].equals("sessionId") && i < cookiePairs.length) {
            i += 2;
        }

        String cookieValue = cookiePairs[i+1];
        Integer firstLetter = 1;
        Integer lastLetter = cookieValue.length() - 1;

        return cookieValue.substring(firstLetter, lastLetter);
    }


}
