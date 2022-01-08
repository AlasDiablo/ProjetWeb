package fr.poweroff.web.controller.html.admin;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.User;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

@WebServlet(name = "Admin - Delete account", value = Registries.PATH_DELETE_PEOPLE)
public class DeleteAccount extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        if (LoginChecker.performAdminCheck(this, request, response)) {
            String mail = request.getParameter("mail");
            request.setAttribute("mail", mail);

            try {
                User user = User.getFirst(mail);
                assert user != null;
                user.delete();
            } catch (SQLException e) {
                throw new RemoteException();
            }

            response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_INDEX);
        }
    }
}
