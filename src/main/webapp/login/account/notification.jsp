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
    <title>Compte</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../../menu.jsp" %>
<%
    //Récupérations des notifications de la personne.
    session = request.getSession(true);
    String mail = String.valueOf(session.getAttribute("email"));
    User user;
    try {
        //Verfication de l'identifiant et du mot de passe
        user = User.getFirst(mail);
        assert user != null;

    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
<div class="container">
    <h1 class="display-1 text-center">Notifications</h1>

    <div class="list-group">
        <a href="#" role="button" type="button" class="list-group-item list-group-item-action list-group-item-info">Test</a>
        <a href="#" role="button" type="button" class="list-group-item list-group-item-action list-group-item-info">Test</a>
        <a href="#" role="button" type="button" class="list-group-item list-group-item-action">Test</a>
        <a href="#" role="button" type="button" class="list-group-item list-group-item-action">Test</a>
    </div>
</div>
</body>
</html>
