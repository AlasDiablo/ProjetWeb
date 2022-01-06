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
    <h1 class="display-1 text-center">Compte</h1>

    <div class="row">
        <div class="col-sm-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Mes informations</h5>
                    <p class="card-text">Information concernant votre compte.</p>
                    <a href="perso" class="btn btn-primary">Acceder</a>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Mes amis</h5>
                    <p class="card-text">Visualisation de vos amis.</p>
                    <a href="amis" class="btn btn-primary">Acceder</a>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Mes activités</h5>
                    <p class="card-text">Visualisation et ajout de vos activités.</p>
                    <a href="${pageContext.request.contextPath}/my-activity" class="btn btn-primary">Acceder</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
