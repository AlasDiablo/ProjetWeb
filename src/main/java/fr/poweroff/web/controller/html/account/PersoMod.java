package fr.poweroff.web.controller.html.account;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.User;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "Account - Edit personal information", value = Registries.PATH_ACCOUNT_INFO_EDIT)
public class PersoMod extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        if (LoginChecker.performCheck(this, request, response)) {
            this.getServletContext().getRequestDispatcher(Registries.JSP_ACCOUNT_INFO_EDIT).forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (LoginChecker.performCheck(this, request, response)) {
            if (request.getParameter("name") != null
                    && request.getParameter("lname") != null
                    && request.getParameter("born") != null) {

                System.out.println(request.getParameter("lname"));

                String name  = request.getParameter("name");
                String lname = request.getParameter("lname");
                String born  = request.getParameter("born");

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
            response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_ACCOUNT_INFO);
        }
    }
}
