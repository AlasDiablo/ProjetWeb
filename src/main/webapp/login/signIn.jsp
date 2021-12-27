<%--
  Created by IntelliJ IDEA.
  User: e_bon
  Date: 21/12/2021
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>S'identifier</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../menu.jsp" %>
<div class="container">
<h1 class="display-1 text-center">Connection</h1>
<p>Les champs avec * sont obligatoires</p>
<form name="inscription" action="\sign-in" method="post">

    <div class="form-group">
        <label for="mail">Email address *</label>
        <input type="email" class="form-control" id="mail" aria-describedby="emailHelp" name="mail" required>
    </div>
    <div class="form-group">
        <label for="password">Password *</label>
        <input type="password" class="form-control" id="password" name="password" required>
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>
    <input type="reset" class="btn btn-danger" value="Remove">

</form>
</div>
</body>
</html>
