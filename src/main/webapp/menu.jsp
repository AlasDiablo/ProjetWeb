<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: e_bon
  Date: 27/12/2021
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<head>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Accueil</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">

    <% //Verifiaction session
        session = request.getSession(true);
        String name = String.valueOf(session.getAttribute("name"));
        if(Objects.equals(name, "null")) { %>
        <li class="nav-item">
            <a class="nav-link" href="sign-up">S'inscrire</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="sign-in">S'identifier</a>
        </li>
        <% } else {%>
        <li class="nav-item">
            <a class="nav-link" href="sign-out">Se deconnecter</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="account">Compte</a>
        </li>
        <% }%>
        </ul>
        <form class="form-inline my-2 my-lg-0" action="people" method="post">
            <input class="form-control mr-sm-2" type="search" placeholder="People" name="people" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
