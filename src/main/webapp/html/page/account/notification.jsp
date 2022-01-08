<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="fr.poweroff.web.models.Notification" %>
<%@ page import="java.util.*" %><%--
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
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
        session = request.getSession(true);
    String mail = String.valueOf(session.getAttribute("email"));

    //Recup user
    User user = null;
    try {
        //Verfication de l'identifiant et du mot de passe
        user = User.getFirst(mail);
        assert user != null;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    Notification notification;
    List<Integer> notifs = new ArrayList<>();
    try {
        notification = Notification.create();
        assert user != null;
        notifs = Notification.getNotification(user.getUserId());
        assert notification != null;

    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
<div class="container">
    <h1 class="display-1 text-center">Notifications</h1>

    <div class="list-group">
        <%
            assert notifs != null;
            java.util.Collections.reverse(notifs);
            for (Integer i: notifs) {
                notification = Notification.getFirst(i);

                assert notification != null;
                StringBuilder world = new StringBuilder();
                char test = 'a';
                int a = 0;
                while(test != ' '){
                    test = notification.getContent().charAt(a);
                    a++;
                    world.append(test);
                }
                if(world.toString().equals("Attention ") && !notification.getUnRead()) { %>
                    <a href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_NOTIFICATION_DISPLAY%>?id=<%= notification.getNotificationId() %>"
                       role="button" type="button"
                       class="list-group-item list-group-item-action list-group-item-danger"><%= notification.getContent() %>
                    </a>
                <%
                } else
                    if(world.toString().equals("Attention ") && notification.getUnRead()) {%>
                    <a href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_NOTIFICATION_DISPLAY%>?id=<%= notification.getNotificationId() %>"
                       role="button" type="button"
                       class="list-group-item border-danger list-group-item-action list-group-item"><%= notification.getContent() %>
                    </a>
                <%
                } else
                if (!notification.getUnRead()) { %>
                    <a href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_NOTIFICATION_DISPLAY%>?id=<%= notification.getNotificationId() %>"
                       role="button" type="button"
                       class="list-group-item list-group-item-action list-group-item-info"><%= notification.getContent() %>
                    </a>
                <%
                }else {%>
                    <a href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_NOTIFICATION_DISPLAY%>?id=<%= notification.getNotificationId() %>"
                       role="button" type="button"
                       class="list-group-item list-group-item-action list-group-item"><%= notification.getContent() %>
                    </a>
                <%}
            }
        %>
    </div>
</div>
<script>
    title("Mes notifications");
</script>
<%@ include file="/html/bottom.jsp" %>
