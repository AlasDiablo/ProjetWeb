<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="org.springframework.security.crypto.bcrypt.BCrypt" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: e_bon
  Date: 27/12/2021
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Peoples</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../../../menu.jsp" %>
<%
    String people = (String) request.getAttribute("people");
    List<User> user = null;
    List<User> userN = null;
    List<User> userLN = null;
    System.out.println(people);
    try {
        //Verifiacation si une donnée a été donné
        if(people == "")
            user = User.getUsers();
        else {
            user = User.getUsersMail(people);
            userN = User.getUsersName(people);
            userLN = User.getUsersLastName(people);
        }
        assert user != null;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    assert user != null;%>
<div class="container">
    <h1 class="display-1 text-center">Peoples</h1>

    <div class="list-group">
        <% for (User u: user) { %>
            <a href="people-pers?mail=<%= u.getEmail() %>" role="button" type="button" class="list-group-item list-group-item-action"><%= u.getFirstname() %> <%= u.getLastname() %> <%= u.getEmail() %></a>
        <% } %>
        <% if(userN != null ){
            for (User u: userN) { %>
                <a href="people-pers?mail=<%= u.getEmail() %>" role="button" type="button" class="list-group-item list-group-item-action"><%= u.getFirstname() %> <%= u.getLastname() %> <%= u.getEmail() %></a>
        <% }
        } %>
        <% if(userLN != null ){
            for (User u: userLN) { %>
        <a href="people-pers?mail=<%= u.getEmail() %>" role="button" type="button" class="list-group-item list-group-item-action"><%= u.getFirstname() %> <%= u.getLastname() %> <%= u.getEmail() %></a>
        <% }
        } %>
    </div>
</div>
</body>
</html>
