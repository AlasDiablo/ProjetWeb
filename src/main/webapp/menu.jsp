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
        <li class="nav-item">
            <a claa="nav-link" href="notif">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bell" viewBox="0 0 16 16">
                    <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z"/>
                </svg>
            </a>
        </li>
        <% }%>
        </ul>
        <form class="form-inline my-2 my-lg-0" action="people" method="post">
            <input class="form-control mr-sm-2" type="search" placeholder="People" name="people" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
