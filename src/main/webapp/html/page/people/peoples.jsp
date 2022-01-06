<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
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
    String people = (String) request.getAttribute("people");
    List<User> user = null;
    List<User> userN = null;
    List<User> userLN = null;
    String sessionmail = (String) request.getSession().getAttribute("email");
    //System.out.println(sessionmail);
    try {
        //Verifiacation si une donnée a été donné
        if (people == "")
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
        <% for (User u: user) {
            if (!u.getEmail().equals(sessionmail)) {%>
        <a href="${pageContext.request.contextPath}<%=Registries.PATH_PEOPLE_ABOUT%>?mail=<%= u.getEmail() %>"
           role="button" type="button"
           class="list-group-item list-group-item-action"><%= u.getFirstname() %> <%= u.getLastname() %> <%= u.getEmail() %>
        </a>
        <% }
        } %>
        <% if (userN != null) {
            for (User u: userN) {
                if (!u.getEmail().equals(sessionmail)) {%>
        <a href="${pageContext.request.contextPath}<%=Registries.PATH_PEOPLE_ABOUT%>?mail=<%= u.getEmail() %>"
           role="button" type="button"
           class="list-group-item list-group-item-action"><%= u.getFirstname() %> <%= u.getLastname() %> <%= u.getEmail() %>
        </a>
        <% }
        }
        } %>
        <% if (userLN != null) {
            for (User u: userLN) {
                if (!u.getEmail().equals(sessionmail)) {%>
        <a href="${pageContext.request.contextPath}<%=Registries.PATH_PEOPLE_ABOUT%>?mail=<%= u.getEmail() %>"
           role="button" type="button"
           class="list-group-item list-group-item-action"><%= u.getFirstname() %> <%= u.getLastname() %> <%= u.getEmail() %>
        </a>
        <% }
        }
        } %>
    </div>
</div>
<script>
    title("Personnes");
</script>
<%@ include file="/html/bottom.jsp" %>
