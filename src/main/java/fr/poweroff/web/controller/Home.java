package fr.poweroff.web.controller;

import fr.poweroff.web.Registries;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Home", value = "")
public class Home extends HttpServlet {

    public void doGet(HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher(Registries.JSP_INDEX).forward(request, response);
    }
}