package fr.poweroff.web.controller.html.account.Admin;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.User;
import fr.poweroff.web.util.LoginChecker;
import org.graalvm.compiler.lir.LIRInstruction;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Delete - Other account", value = Registries.PATH_DELETE_PEOPLE)
public class DeleteAccount extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        if (LoginChecker.performCheck(this, request, response)) {
            this.getServletContext().getRequestDispatcher(Registries.JSP_ACCOUNT).forward(request, response);
        }

        String mail = request.getParameter("mail");
        request.setAttribute("mail", mail);

        try {
            User user = User.getFirst(mail);
            assert user != null;
            user.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
