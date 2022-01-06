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
    <h1 class="display-1 text-center">Mes Amis</h1>

    <div class="list-group">
        <%
            User us = User.create();
            List<User> user = null;
            String sessionmail = (String) request.getSession().getAttribute("email");
            //System.out.println(sessionmail);
            try {
                //Verifiacation si une donnée a été donné
                us = User.getFirst(sessionmail);
                user = us.getFriends();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            assert user != null;

            for (User u: user) {
            if (!u.getEmail().equals(sessionmail)) {%>
        <a href="${pageContext.request.contextPath}<%=Registries.PATH_PEOPLE_ABOUT%>?mail=<%= u.getEmail() %>"
           role="button" type="button"
           class="list-group-item list-group-item-action"><%= u.getFirstname() %> <%= u.getLastname() %> <%= u.getEmail() %>
        </a>
        <% }
        } %>

        <!--<% %>
        <button type="button" class="list-group-item list-group-item-action"></button>
        <% %>-->
    </div>
</div>
<script>
    title("Mes amies");
</script>
<%@ include file="/html/bottom.jsp" %>