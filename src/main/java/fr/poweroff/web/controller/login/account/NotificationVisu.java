package fr.poweroff.web.controller.login.account;

import fr.poweroff.web.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "/notifVisu", value = "/notif-visu")
public class NotificationVisu extends HttpServlet {
    private Integer ident;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String id = request.getParameter("id");
        request.setAttribute("id", id);

        ident = Integer.valueOf(id);

        this.getServletContext().getRequestDispatcher("/login/account/notificationVisu.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String demande = request.getParameter("demande");
        request.setAttribute("demande", demande);

        String mail = request.getParameter("user");
        request.setAttribute("user", mail);

        //Récupération de la notification
        fr.poweroff.web.models.Notification notification = null;
        try {
            notification = fr.poweroff.web.models.Notification.getFirst(ident);
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
        User user1 = null;
        try {
            user1 = User.getFirst((String) session.getAttribute("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (demande.equals("true")){
            //Ajout de la personne en ami

            try {
                assert user1 != null;
                assert user != null;
                user1.acceptFriendRequest(user.getUserId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
            //Supression de la personne en ami temp

            try {
                assert user1 != null;
                assert user != null;
                user1.removeFriend(user.getUserId());
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        response.sendRedirect("hello-servlet");
    }
}
