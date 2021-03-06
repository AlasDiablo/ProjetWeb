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
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "Login - Sign up", value = Registries.PATH_SIGN_UP)
public class SignUp extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher(Registries.JSP_SIGN_UP).forward(request, response);
    }

    @Override
    public void doPost(@NotNull HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getParameter("name") != null
                && request.getParameter("Lname") != null
                && request.getParameter("mail") != null
                && request.getParameter("password") != null
                && request.getParameter("born") != null) {


            String mail = request.getParameter("mail");

            //Faire les verification de chaqu'un
            //Verification d'une adresse mail unique
            User usMail = null;
            try {
                usMail = User.getFirst(mail);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //On retourne sur la page
            if (usMail != null) {
                this.getServletContext().getRequestDispatcher("/login/sign-up.jsp").forward(request, response);
                //response.sendRedirect("sign-up");
            }


            String name     = request.getParameter("name");
            String lname    = request.getParameter("Lname");
            String password = request.getParameter("password");
            String born     = request.getParameter("born");

            String mdp = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = User.create();
            user.setFirstname(name);
            user.setLastname(lname);
            user.setEmail(mail);
            user.setPasswordHash(mdp); //Il faut hasher le mot de passe
            user.setBorn(Date.valueOf(born));
            user.setLevel(0);
            try {
                user.save();
            } catch (SQLException e) {
                throw new IOException(e);
            }

            //Creation de la session
            HttpSession session = request.getSession();

            session.setAttribute("lastName", user.getLastname());
            session.setAttribute("name", user.getFirstname());
            session.setAttribute("email", user.getEmail());

            /*Connection connec = this.database.loadDatabase();

            try {
                PreparedStatement preparedStatement = connec.prepareStatement("INSERT INTO user(firstname, lastname, email, password_hash, born, level) VALUES(?, ?, ?, ?, ?, ?);");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lname);
                preparedStatement.setString(3, mail);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, born);
                preparedStatement.setString(6, String.valueOf(0));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }*/

            //redirection si c'est valide
            response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_ACCOUNT);
        } else {
            //si ca ne fonctionne pas
            response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_SIGN_UP);
        }
    }


}