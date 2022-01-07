package fr.poweroff.web.controller.html.positif;

import fr.poweroff.web.Registries;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Positif - Positif", value = Registries.PATH_POSITIF)
public class Positif extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher(Registries.JSP_PEOPLES).forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


        try {
            this.getServletContext().getRequestDispatcher(Registries.JSP_INDEX).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_INDEX);
    }
}
