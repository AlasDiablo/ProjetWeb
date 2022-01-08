package fr.poweroff.web.controller.html.positif;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Activity;
import fr.poweroff.web.models.Notification;
import fr.poweroff.web.models.User;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "Positif - Positif", value = Registries.PATH_POSITIF)
public class Positif extends HttpServlet {

    @Override
    public void doGet(@NotNull HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (LoginChecker.performCheck(this, request, response)) {
            try {
                String sessionmail = (String) request.getSession().getAttribute("email");

                //Ajout a la base de donnée que la personne est contaminée
                User          user    = Objects.requireNonNull(User.getFirst(sessionmail));
                Date          date    = Date.from(Instant.now());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                user.isContaminated(sqlDate);

                this.sendToFriends(user);
                this.sendToOtherPeople(user, date);

                response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_INDEX);
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }

    private void sendToOtherPeople(@NotNull User user, Date date) throws SQLException {
        Notification notif = Notification.create();
        notif.setContent(
                "Attention ! Un personne a étais est positif à la covid ! Vous êtes donc cas contact. \n" +
                        "Merci de verifier votre list d'activité pour voir dans quelle activité vous avait étais cas de contact."
        );
        List<Activity> activities = user.getActivities(new Timestamp(date.getTime()));
        for (Activity activity: activities) {
            List<User> users = Activity.setContact(date, activity.getCity());
            for (User userContact: users) {
                if (!Objects.equals(userContact.getUserId(), user.getUserId())) {
                    notif.setTarget(userContact);
                    notif.setUnRead(false);
                    notif.save();
                }
            }
        }
    }

    private void sendToFriends(@NotNull User user) throws SQLException {
        List<User> amis = user.getFriends();

        Notification notif = Notification.create();

        //Création de la notification
        notif.setContent(String.format(
                "Attention ! %s %s (%s) est positif à la covid ! Vous êtes donc cas contact.",
                user.getFirstname(), user.getLastname(), user.getEmail()
        ));

        for (User u: amis) {
            notif.setTarget(u);
            notif.setUnRead(false);
            //notif.saveAmi();
            notif.save();
        }
    }
}
