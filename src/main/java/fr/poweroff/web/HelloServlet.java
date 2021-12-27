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
        out.println("<html><head>\n" +
                "    <title>Accueil</title>\n" +
                "    <link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "</head>" +
                "<body>");
        out.println("<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" +
                "  <a class=\"navbar-brand\" href=\"#\">Accueil</a>\n" +
                "   <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
                "    <span class=\"navbar-toggler-icon\"></span>\n" +
                "  </button>" +
                "   <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n" +
                "    <ul class=\"navbar-nav mr-auto\">");
        if(Objects.equals(name, "null")) {
            out.println("<li class=\"nav-item\">\n" +
                            "<a class=\"nav-link\" href=\"sign-up\">S'inscrire</a>" +
                    "   </li>");
            out.println("<li class=\"nav-item\">\n" +
                    "<a class=\"nav-link\" href=\"sign-in\">S'identifier</a>" +
                    "   </li>");
        }
        else {
            //out.println("<h2>\n Bonjour " + name + "</h2>\n");
            out.println("<li class=\"nav-item\">\n" +
                    "<a class=\"nav-link\" href=\"sign-out\">Se deconnecter</a>" +
                    "   </li>");
            out.println("<li class=\"nav-item\">\n" +
                    "<a class=\"nav-link\" href=\"account\">Compte</a>" +
                    "   </li>");
        }
        out.println("    </ul>" +
                "    <form class=\"form-inline my-2 my-lg-0\" action=\"\\people\" method=\"post\">\n" +
                "      <input class=\"form-control mr-sm-2\" type=\"search\" placeholder=\"People\" aria-label=\"Search\">\n" +
                "      <button class=\"btn btn-outline-success my-2 my-sm-0\" type=\"submit\">Search</button>\n" +
                "    </form>" +
                "   </div>" +
                "</nav>\n");
        out.println("<h1>" + message + "</h1>");
        if(!Objects.equals(name, "null")) {
            out.println("<h2>\n Bonjour " + name + "</h2>\n");
        }

        out.println("</body></html>");
    }

    public void destroy() {
    }
}