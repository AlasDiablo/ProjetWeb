<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="org.springframework.security.crypto.bcrypt.BCrypt" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: e_bon
  Date: 27/12/2021
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Informations</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../../../menu.jsp" %>
<%
    session = request.getSession(true);
    String mail = String.valueOf(session.getAttribute("email"));
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
            <p>Mon prénom : <b><%= user.getFirstname() %></b></p>
            <p>Mon nom : <b><%= user.getLastname() %></b></p>
            <p>Mon mail : <b><%= user.getEmail() %></b></p>
            <p>Ma date de naissance : <b><%= user.getBorn() %></b></p>

            <a href="perso-mod" class="btn btn-success">Envoyer demande d'ami</a>
            <!--<a href="#" class="btn btn-danger">Supprimer mon compte</a>-->
        </div>
    </div>
</div>
</body>
</html>
