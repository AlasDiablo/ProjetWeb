package fr.poweroff.web.controller.html.account;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Notification;
import fr.poweroff.web.models.User;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Account - Display Notification", value = Registries.PATH_ACCOUNT_NOTIFICATION_DISPLAY)
public class NotificationVisu extends HttpServlet {
    private Integer ident;

    @Override
    public void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        if (LoginChecker.performCheck(this, request, response)) {
            String id = request.getParameter("id");
            request.setAttribute("id", id);

            ident = Integer.valueOf(id);

            this.getServletContext().getRequestDispatcher(Registries.JSP_ACCOUNT_NOTIFICATION_DISPLAY).forward(request, response);
        }
    }

    @Override
    public void doPost(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        if (LoginChecker.performCheck(this, request, response)) {
            String demande = request.getParameter("demande");
            request.setAttribute("demande", demande);

            String mail = request.getParameter("user");
            request.setAttribute("user", mail);

            //Récupération de la notification
            Notification notification = null;
            try {
                notification = Notification.getFirst(ident);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Récupération de l'autre utilisateur
            User user = null;
            try {
                user = User.getFirst(mail);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            HttpSession session = request.getSession();
            User        user1   = null;
            try {
                user1 = User.getFirst((String) session.getAttribute("email"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (demande.equals("true")) {
                //Ajout de la personne en ami

                try {
                    assert user1 != null;
                    assert user != null;
                    user1.acceptFriendRequest(user.getUserId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                //Supression de la personne en ami temp

                try {
                    assert user1 != null;
                    assert user != null;
                    user1.removeFriend(user.getUserId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }

            response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_MY_ACTIVITY);
        }
    }
}
