<%@ page import="fr.poweroff.web.models.User" %>
<%@ page import="java.sql.SQLException" %>
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
    String mail = (String) request.getAttribute("mail");
    User user = null;
    try {
        //Verfication de l'identifiant et du mot de passe
        user = User.getFirst(mail);
        assert user != null;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    assert user != null;%>
<div class="container">
    <h1 class="display-1 text-center">Informations</h1>
    <div class="card">
        <h5 class="card-header">Données personnelles
<%
    session = request.getSession(true);
    String mail2 = String.valueOf(session.getAttribute("email"));
    User userSession = User.create();
    try {
        userSession = User.getFirst(mail2);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    assert userSession != null;
    if(userSession != null){
        if(userSession.getLevel() == 1) { %>
        <div class="dropdown float-lg-right">
            <button type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="btn btn-secondary dropdown-toggle">
                Modification
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenu2" style="">
                <button class="dropdown-item text-danger" type="button" data-toggle="modal" data-target="#modalConfirm">Supprimer le compte de la personne</button>
            </div>
        </div>
        <% }
    }%>
        </h5>
        <div class="card-body">
            <p>Prénom : <b><%= user.getFirstname() %>
            </b></p>
            <p>Nom : <b><%= user.getLastname() %>
            </b></p>
            <p>Mail : <b><%= user.getEmail() %>
            </b></p>
            <p>Date de naissance : <b><%= user.getBorn() %>
            </b></p>

            <% String sessionmail = (String) request.getSession().getAttribute("email");
                //Verification qu'une personne est connectée
                if (sessionmail != null) {
                    User userP = User.create();
                    List<User> userList = null;
                    List<User> userList1 = null;
                    try {
                        userP = User.getFirst(sessionmail);
                        assert userP != null;
                        userList = userP.getFriends();
                        userList1 = userP.getFriends2();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    boolean ami = false;
                    boolean valide = false;
                    for (User u: userList) {
                        if (!u.getEmail().equals(sessionmail)) {
                            if(u.getEmail().equals(user.getEmail())){
                                ami = true;
                            }
                        }
                    }
                    for(User u : userList1){
                        if (!u.getEmail().equals(sessionmail)) {
                            if(u.getEmail().equals(user.getEmail())){
                                valide = true;
                            }
                        }
                    }
            %>


                        <% if(!ami && !valide){ %>
                            <form action="${pageContext.request.contextPath}<%=Registries.PATH_PEOPLE_ABOUT%>?mail=<%=user.getEmail()%>&etat=add" method="post">
                                <button type="submit" class="btn btn-success">Envoyer demande d'ami</button>
                            </form>
                        <% } else
                        if(!valide){
                        %>
                            <form action="${pageContext.request.contextPath}<%=Registries.PATH_PEOPLE_ABOUT%>?mail=<%=user.getEmail()%>&etat=remove" method="post">
                                <button type="submit" class="btn btn-danger">Supprimer de ses amis</button>
                            </form>
                        <%}%>
                <% } %>
            <!--<a href="people-pers" class="btn btn-success">Envoyer demande d'ami</a>-->
        </div>
    </div>
</div>

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
                Voulez-vous vraiment supprimer cette personne ?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">NON</button>
                <a type="button" class="btn btn-success" href="${pageContext.request.contextPath}<%=Registries.PATH_DELETE_PEOPLE%>?mail=<%= user.getEmail() %>">OUI</a>
            </div>
        </div>
    </div>
</div>
<script>
    title("Informations");
</script>
<%@ include file="/html/bottom.jsp" %>