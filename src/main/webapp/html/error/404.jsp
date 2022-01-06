<%@ page import="fr.poweroff.web.Registries" %><%--
  Created by IntelliJ IDEA.
  User: e_bon
  Date: 29/12/2021
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/html/top.jsp" %>
<p class="text-center text-lg-center" style="font-size:6em">OUPS...</p>
<div class="container">
    <div class="row">
        <div class="col-sm-6">
            <h1 class="card-title">La page que vous recherchez semble introuvable.</h1>
            <p class="card-text">Code erreur : 404</p>
            <p class="card-text">Voici quelques pages qui pourraient vous intéresser : </p>
            <a href="${pageContext.request.contextPath}<%=Registries.PATH_INDEX%>">Page d'accueil</a>
        </div>
        <div class="col-sm-6">
            <div class="text-center">
                <img src="${pageContext.request.contextPath}/public/covid.png" class="rounded" alt="imageErreur">
            </div>
        </div>
    </div>
</div>
<script>
    title("Erreur 404");
</script>
<%@ include file="/html/bottom.jsp" %>