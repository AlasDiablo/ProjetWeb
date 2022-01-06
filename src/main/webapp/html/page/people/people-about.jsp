<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="java.sql.SQLException" %>
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
    String mail = (String) request.getAttribute("mail");
    User user = null;
    try {
        //Verfication de l'identifiant et du mot de passe
        user = User.getFirst(mail);
        assert user != null;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    assert user != null;%>
<div class="container">
    <h1 class="display-1 text-center">Informations</h1>

    <div class="card">
        <h5 class="card-header">Données personnelles</h5>
        <div class="card-body">
            <p>Mon prénom : <b><%= user.getFirstname() %>
            </b></p>
            <p>Mon nom : <b><%= user.getLastname() %>
            </b></p>
            <p>Mon mail : <b><%= user.getEmail() %>
            </b></p>
            <p>Ma date de naissance : <b><%= user.getBorn() %>
            </b></p>

            <% String sessionmail = (String) request.getSession().getAttribute("email");
                if (sessionmail != null) {%>
            <form action="${pageContext.request.contextPath}<%=Registries.PATH_PEOPLE_ABOUT%>?mail=<%=user.getEmail()%>"
                  method="post">
                <button type="submit" class="btn btn-success">Envoyer demande d'ami</button>
            </form>
            <% } %>
            <!--<a href="people-pers" class="btn btn-success">Envoyer demande d'ami</a>-->
        </div>
    </div>
</div>
<script>
    title("Informations");
</script>
<%@ include file="/html/bottom.jsp" %>