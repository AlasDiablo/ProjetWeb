package fr.poweroff.web;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class  HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String password = "test";
        String hash     = BCrypt.hashpw(password, BCrypt.gensalt());

        //Verifiaction session
        HttpSession session = request.getSession(true);
        String name = String.valueOf(session.getAttribute("name"));


        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        if(Objects.equals(name, "null")) {
            out.println("<a href=\"sign-up\">S'inscrire</a>");
            out.println("<a href=\"sign-in\">S'identifier</a>");
        }
        else {
            out.println("<h2>\n Bonjour " + name + "</h2>\n");
            out.println("<a href=\"sign-out\">Se deconnecter</a>");
        }

        out.println("<h2>Password hash</h2>");
        out.println("- plaintext: " + password + "<br>");
        out.println("- hash: " + hash + "<br>");
        out.println("- is equals: " + BCrypt.checkpw("test", hash));



        out.println("</body></html>");
    }

    public void destroy() {
    }
}