<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="fr.poweroff.web.Registries" %>
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
session = request.getSession(true);
    String mail = String.valueOf(session.getAttribute("email"));
    User user;
    try {
        user = User.getFirst(mail);
        assert user != null;
    } catch (SQLException | NullPointerException ignored) {
        throw new RuntimeException();
    }
%>
<div class="container">
    <h1 class="display-1 text-center">Mes informations</h1>

    <div class="card">
        <h5 class="card-header">Mes données personnelles</h5>
        <div class="card-body">
            <p>Mon prénom : <b><%= user.getFirstname() %>
            </b></p>
            <p>Mon nom : <b><%= user.getLastname() %>
            </b></p>
            <p>Mon mail : <b><%= user.getEmail() %>
            </b></p>
            <p>Ma date de naissance : <b><%= user.getBorn() %>
            </b></p>

            <a href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_INFO_EDIT%>" class="btn btn-warning">Modifier
                mes informations</a>
            <!--<a href="#" class="btn btn-danger">Supprimer mon compte</a>-->
        </div>
    </div>
</div>
<script>
    title("Mes information personnel");
</script>
<%@ include file="/html/bottom.jsp" %>