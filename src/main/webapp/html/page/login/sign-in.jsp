<%@ page import="fr.poweroff.web.Registries" %>
<%--
  Created by IntelliJ IDEA.
  User: e_bon
  Date: 21/12/2021
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/html/top.jsp" %>
<div class="container">
    <h1 class="display-1 text-center">Connection</h1>
    <p>Les champs avec * sont obligatoires</p>
    <form name="connection" action="${pageContext.request.contextPath}<%=Registries.PATH_SIGN_IN%>" method="post">

        <div class="form-group">
            <label for="mail">Email address *</label>
            <input type="email" class="form-control" id="mail" aria-describedby="emailHelp" name="mail" required>
        </div>
        <div class="form-group">
            <label for="password">Password *</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
<script>
    title("S'identifier");
</script>
<%@ include file="/html/bottom.jsp" %>
