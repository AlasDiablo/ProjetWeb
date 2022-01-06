package fr.poweroff.web.controller.html.people;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Notification;
import fr.poweroff.web.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "People - People about", value = Registries.PATH_PEOPLE_ABOUT)
public class PeoplePers extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String mail = request.getParameter("mail");
        request.setAttribute("mail", mail);

        this.getServletContext().getRequestDispatcher(Registries.JSP_PEOPLE_ABOUT).forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String mail = request.getParameter("mail");
        request.setAttribute("mail", mail);
        //User c'est la personne a qui envoyer la notification
        User user = null;
        try {
            user = User.getFirst(mail);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Notification notif = Notification.create();
        try {
            this.getServletContext().getRequestDispatcher(Registries.JSP_PEOPLES).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        //Création de la notification
        notif.setContent(
                request.getSession().getAttribute("email").toString() + " vous a envoyé une demande d'ami. Vous pouvez accepter ou refuser la demande.");
        notif.setTarget(user);
        notif.setUnRead(false);

        //Enregistrement de la notification
        try {
            notif.saveAmi();
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
        }

        User userSession = null;
        try {
            userSession = User.getFirst(request.getSession().getAttribute("email").toString());
            assert userSession != null;
            assert user != null;
            userSession.sendFriendRequest(user.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_INDEX);
    }
}