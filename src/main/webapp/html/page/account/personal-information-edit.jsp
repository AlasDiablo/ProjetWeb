<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="fr.poweroff.web.Registries" %><%--
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
    <h1 class="display-1 text-center">Modification de mes informations</h1>

    <div class="card">
        <h5 class="card-header">Mes données personnelles</h5>
        <form name="inscription" action="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_INFO_EDIT%>"
              method="post">
            <div class="card-body">
                <p>Mon prénom : <input type="text" class="form-control" name="name" value="<%= user.getFirstname() %>">
                </p>
                <p>Mon nom : <input type="text" class="form-control" name="lname" value="<%= user.getLastname() %>"></p>
                <p>Ma date de naissance : <input type="text" class="form-control" name="born"
                                                 value="<%= user.getBorn() %>"></p>

                <button type="submit" class="btn btn-success">Enregistrer mes informations</button>
                <!--<a href="#" class="btn btn-danger">Supprimer mon compte</a>-->
            </div>
        </form>
    </div>
</div>
<script>
    title("Modifier mes information personnel");
</script>
<%@ include file="/html/bottom.jsp" %>