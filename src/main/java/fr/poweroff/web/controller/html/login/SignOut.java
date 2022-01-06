package fr.poweroff.web.controller.html.login;

import fr.poweroff.web.Registries;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Login - Sign out", value = Registries.PATH_SIGN_OUT)
public class SignOut extends HttpServlet {

    @Override
    public void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        if (LoginChecker.performCheck(this, request, response)) {
            HttpSession session = request.getSession();

            session.removeAttribute("name");
            session.removeAttribute("lastName");
            session.removeAttribute("email");

            session.invalidate();

            response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_INDEX);
        }
    }
}
