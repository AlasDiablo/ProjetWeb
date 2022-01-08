<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="org.springframework.security.crypto.bcrypt.BCrypt" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="fr.poweroff.web.models.Notification" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: e_bon
  Date: 27/12/2021
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/html/top.jsp" %>
<%
    //Récupérations des notifications de la personne.


    String id = (String) request.getAttribute("id");

    Integer idN = Integer.valueOf(id);

    Notification notification = Notification.create();
    try {
        notification = Notification.getFirst(idN);

        //Mise a jour de la notification comme déjà lu
        assert notification != null;
        notification.setUnRead(true);
        notification.update();
    } catch (SQLException e) {
        e.printStackTrace();
    }


%>
<div class="container">
    <h1 class="display-1 text-center">Notification</h1>

    <div class="list-group">
        <%
            StringBuilder mailUser = new StringBuilder();
            char i = 'a';
            int a = 0;
            while(i != ' '){
                i = notification.getContent().charAt(a);
                a++;
                mailUser.append(i);
            }

            //Vérification que le premier mot est un mail
            User user = null;
            try {
                //Verfication de l'identifiant et du mot de passe
                user = User.getFirst(mailUser.toString());
                assert user != null;

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //si c'est un mail alors on affiche les infos de la personnes et les bouttons d'accepter ou non la demande.
            if (user != null) {%>

        <div class="card">
            <h5 class="card-header">Cette personne vous a envoyé une demande d'ami</h5>
            <div class="card-body">
                <p>Prénom : <b><%= user.getFirstname() %>
                </b></p>
                <p>Nom : <b><%= user.getLastname() %>
                </b></p>
                <p>Mail : <b><%= user.getEmail() %>
                </b></p>
                <p>Date de naissance : <b><%= user.getBorn() %>
                </b></p>

                <form action="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_NOTIFICATION_DISPLAY%>?demande=true&user=<%= user.getEmail() %>"
                      method="post">
                    <button type="submit" class="btn btn-success">Accepter</button>
                </form>
                <form action="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_NOTIFICATION_DISPLAY%>?demande=false&user=<%= user.getEmail() %>"
                      method="post">
                    <button type="submit" class="btn btn-danger">Refuser</button>
                </form>
                <!--<a href="#" class="btn btn-danger">Supprimer mon compte</a>-->
            </div>
        </div>

        <%} else
            if(mailUser.toString().equals("Attention ")) { %>
                <div class="alert alert-danger" role="alert">
                    <h4 class="alert-heading">ATTENTION </h4>
                    <p><%= notification.getContent() %></p>
                    <hr>
                    <p class="mb-0">Une personne autour de vous à été testé positif à la Covis, nous vous conseillons de vous isoller le temps de faire un test anti-génique.</p>
                </div>

        <%
            } else { %>
        <div class="alert alert-secondary " role="alert">
            <h4 class="alert-heading">Ohhh....</h4>
            <p><%= notification.getContent() %></p>
            <hr>
            <p class="mb-0">Nous sommes triste de vous annoncer cette nouvelle.</p>
        </div>
        <% } %>
    </div>
</div>
<script>
    title("Notification");
</script>
<%@ include file="/html/bottom.jsp" %>