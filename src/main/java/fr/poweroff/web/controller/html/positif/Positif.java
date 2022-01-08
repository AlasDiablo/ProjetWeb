package fr.poweroff.web.controller.html.positif;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Notification;
import fr.poweroff.web.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "Positif - Positif", value = Registries.PATH_POSITIF)
public class Positif extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String sessionmail = (String) request.getSession().getAttribute("email");

        //Ajout a la base de donnée que la personne est contaminée
        try {
            User user = User.getFirst(sessionmail);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            assert user != null;
            user.isContaminated(Date.valueOf(dtf.format(LocalDateTime.now())));
        } catch (SQLException e) {
            e.printStackTrace();
        }



        List<User> amis = new ArrayList<>();
        try {
            amis = Objects.requireNonNull(User.getFirst(sessionmail)).getFriends();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Notification notif = Notification.create();
        //Création de la notification
        notif.setContent("Attention ! " +
                request.getSession().getAttribute("name").toString() + " " +
                request.getSession().getAttribute("lastName").toString() +
                " (" +
                request.getSession().getAttribute("email").toString() +
                ") "+
                " est positif à la covid ! Vous êtes donc cas contact.");
        for (User u: amis) {
            notif.setTarget(u);
            notif.setUnRead(false);
            try {
                notif.saveAmi();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            this.getServletContext().getRequestDispatcher(Registries.JSP_INDEX).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_INDEX);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sessionmail = (String) request.getSession().getAttribute("email");

        List<User> amis = new ArrayList<>();
        try {
            amis = Objects.requireNonNull(User.getFirst(sessionmail)).getFriends();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Notification notif = Notification.create();
        //Création de la notification
        notif.setContent("Attention ! " +
                request.getSession().getAttribute("email").toString() +
                " est positif à la covid ! Vous êtes donc cas contact.");
        for (User u: amis) {
            notif.setTarget(u);
            notif.setUnRead(false);
            try {
                notif.saveAmi();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            this.getServletContext().getRequestDispatcher(Registries.JSP_INDEX).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_INDEX);
    }
}
