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
<div class="container">
    <h1 class="display-1 text-center">Compte</h1>

    <div class="row">
        <div class="col-sm-6 p-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Mes informations</h5>
                    <p class="card-text">Information concernant votre compte.</p>
                    <a href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_INFO%>"
                       class="btn btn-primary">Acceder</a>
                </div>
            </div>
        </div>
        <div class="col-sm-6 p-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Mes amis</h5>
                    <p class="card-text">Visualisation de vos amis.</p>
                    <a href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_FRIENDS%>"
                       class="btn btn-primary">Acceder</a>
                </div>
            </div>
        </div>
        <div class="col-sm-6 p-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Mes activités</h5>
                    <p class="card-text">Visualisation et ajout de vos activités.</p>
                    <a href="${pageContext.request.contextPath}<%=Registries.PATH_MY_ACTIVITY%>"
                       class="btn btn-primary">Acceder</a>
                </div>
            </div>
        </div>
        <div class="col-sm-6 p-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Theme du site</h5>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="flexRadioDefault"
                               onchange="onChangeTheme('lumen')" id="lumen">
                        <label class="form-check-label" for="lumen">
                            Lumen
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="flexRadioDefault"
                               onchange="onChangeTheme('solar')" id="solar">
                        <label class="form-check-label" for="solar">
                            Solar
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    title("Mon compte");
    loadThemeSystem();
</script>
<%@ include file="/html/bottom.jsp" %>