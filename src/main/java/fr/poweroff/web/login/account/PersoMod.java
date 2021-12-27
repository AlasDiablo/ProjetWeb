package fr.poweroff.web.login.account;

import fr.poweroff.web.models.User;
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

@WebServlet(name = "/persoMod", value = "/perso-mod")
public class PersoMod extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        this.getServletContext().getRequestDispatcher("/login/account/perso/accountPersoMod.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("name") != null
                && request.getParameter("lname") != null
                && request.getParameter("born") != null) {

            System.out.println(request.getParameter("lname"));

            String name = request.getParameter("name");
            String lname = request.getParameter("lname");
            String born = request.getParameter("born");

            HttpSession session = request.getSession();

            User user = null;
            try {
                user = User.getFirst(session.getAttribute("email").toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            assert user != null;
            user.setFirstname(name);
            user.setLastname(lname);
            user.setBorn(Date.valueOf(born));
            try {
                user.update();
            } catch (SQLException e) {
                throw new IOException(e);
            }
        }

        response.sendRedirect("perso");
    }
}
