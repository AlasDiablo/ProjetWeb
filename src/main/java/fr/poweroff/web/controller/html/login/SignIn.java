package fr.poweroff.web.controller.html.login;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Login - Sign in", value = Registries.PATH_SIGN_IN)
public class SignIn extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher(Registries.JSP_SIGN_IN).forward(request, response);
    }

    @Override
    public void doPost(@NotNull HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("mail") != null && request.getParameter("password") != null) {

            String mail     = request.getParameter("mail");
            String password = request.getParameter("password");

            try {
                //Verfication de l'identifiant et du mot de passe
                User user = User.getFirst(mail);
                assert user != null;
                if (BCrypt.checkpw(password, user.getPasswordHash())) {
                    response.sendRedirect("hello-servlet");
                }

                //Creation de la session
                HttpSession session = request.getSession();

                session.setAttribute("lastName", user.getLastname());
                session.setAttribute("name", user.getFirstname());
                session.setAttribute("email", user.getEmail());

                response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_ACCOUNT);
            } catch (SQLException ignored) {
                throw new RuntimeException();
            }

            /*database = new Database();

            Connection connec = this.database.loadDatabase();

            try {
                Statement stat = connec.createStatement();

                ResultSet result = stat.executeQuery("SELECT email, password_hash FROM user;");

                boolean existe = false;
                //Récupération des données
                while (result.next()){
                    if(mail.equals(result.getString("email")) && password.equals(result.getString("password_hash"))){
                        existe = true;
                    }
                }

                if(existe){
                    //redirection si c'est valide
                    response.sendRedirect("hello-servlet");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }*/

        } else {
            //si ca ne fonctionne pas
            response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_SIGN_IN);
        }
    }
}
