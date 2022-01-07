<%@ page import="java.util.Objects" %>
<%@ page import="fr.poweroff.web.Registries" %>
<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="fr.poweroff.web.models.Notification" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: AlasDiablo
  Date: 06/01/2022
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top" style="margin-bottom: 1em">
    <a class="navbar-brand" href="${pageContext.request.contextPath}<%=Registries.PATH_INDEX%>">Accueil</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="container-fluid" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <% //Verifiaction session
                    session = request.getSession(true);
                String name = String.valueOf(session.getAttribute("name"));
                if (Objects.equals(name, "null")) {
            %>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}<%=Registries.PATH_SIGN_UP%>">S'inscrire</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}<%=Registries.PATH_SIGN_IN%>">S'identifier</a>
            </li>

            <%
            } else {
            %>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}<%=Registries.PATH_SIGN_OUT%>">Se
                    deconnecter</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT%>">Compte</a>
            </li>
            <li class="nav-item">
                <a class="nav-link position-relative" href="${pageContext.request.contextPath}<%=Registries.PATH_ACCOUNT_NOTIFICATION%>"> Notification
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-bell" viewBox="0 0 16 16">
                        <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z"></path>
                    </svg>

                    <%
                        //Récupérations des notifications de la personne.
                        session = request.getSession(true);
                        String mail = String.valueOf(session.getAttribute("email"));

                        //Recup user
                        User userId = null;
                        try {
                            //Verfication de l'identifiant et du mot de passe
                            userId = User.getFirst(mail);
                            assert userId != null;

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        Notification notifications;
                        List<Integer> notifss = new ArrayList<>();
                        try {
                            notifications = Notification.create();
                            notifss = Notification.getNotification(userId.getUserId());
                            assert notifications != null;

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        assert notifss != null;
                        int alpha = 0;
                        for(Integer i : notifss){
                            notifications = Notification.getFirst(i);
                            if(!notifications.getUnRead()){
                                alpha ++;
                            }
                        }

                        if(alpha != 0){%>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger text-white">
                        <%= alpha%>
                    </span>
                    <%}%>
                </a>


            </li>
            <button class="btn btn-outline-danger" type="button" data-toggle="modal" data-target="#modalConfirm">Je suis positif à la COVID</button>

            <%
                }
            %>
        </ul>

        <form class="d-flex" action="${pageContext.request.contextPath}<%=Registries.PATH_PEOPLES%>"
              method="post">
            <input class="form-control me-2" type="search" placeholder="People" name="people" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>


<!-- Modal -->
<div class="modal fade" id="modalConfirm" tabindex="-1" aria-labelledby="modalConfirmLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalConfirmLabel">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Etes-vous bien positif à la Covid ?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">NON</button>
                <a type="button" class="btn btn-success" href="${pageContext.request.contextPath}<%=Registries.PATH_POSITIF%>">OUI</a>
            </div>
        </div>
    </div>
</div>