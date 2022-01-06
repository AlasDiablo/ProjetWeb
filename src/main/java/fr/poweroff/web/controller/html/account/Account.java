package fr.poweroff.web.controller.html.account;

import fr.poweroff.web.Registries;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Account - My account", value = Registries.PATH_ACCOUNT)
public class Account extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        if (LoginChecker.performCheck(this, request, response)) {
            this.getServletContext().getRequestDispatcher(Registries.JSP_ACCOUNT).forward(request, response);
        }
    }
}
