<%@ page import="fr.poweroff.web.Registries" %>
<%--
  Created by IntelliJ IDEA.
  User: e_bon
  Date: 21/12/2021
  Time: 07:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/html/top.jsp" %>
<div class="container">
    <h1 class="display-1 text-center">Formulaire</h1>
    <p>Les champs avec * sont obligatoires</p>
    <form name="inscription" action="${pageContext.request.contextPath}<%=Registries.PATH_SIGN_UP%>" method="post">

        <label for="name">First name *</label>
        <input type="text" class="form-control" id="name" name="name" required>

        <label for="Lname">Last name *</label>
        <input type="text" class="form-control" id="Lname" name="Lname" required>

        <div class="form-group">
            <label for="mail">Email address *</label>
            <input type="email" class="form-control" id="mail" aria-describedby="emailHelp" name="mail" required>
        </div>
        <div class="form-group">
            <label for="password">Password *</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="born">Born *</label>
            <input type="date" id="born" name="born" min="1900-01-01" max="2020-12-31" required>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
<script>
    title("S'inscrire");
</script>
<%@ include file="/html/bottom.jsp" %>
