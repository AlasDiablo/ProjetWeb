package fr.poweroff.web.controller.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "/signOut", value = "/sign-out")
public class SignOut extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();

        session.removeAttribute("name");
        session.removeAttribute("lastName");
        session.removeAttribute("email");

        session.invalidate();

        response.sendRedirect("hello-servlet");

        //this.getServletContext().getRequestDispatcher("/login/signIn.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.sendRedirect("hello-servlet");
    }
}
