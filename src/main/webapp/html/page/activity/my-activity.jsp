<%@ page import="fr.poweroff.web.Registries" %><%--
  Created by IntelliJ IDEA.
  User: AlasDiablo
  Date: 06/01/2022
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/html/top.jsp" %>
<div class="p-5">
    <a class="btn btn-success ms-5" href="${pageContext.request.contextPath}<%=Registries.PATH_ADD_ACTIVITY%>">Ajouté un
        activité</a>
    <div class="p-5 w-75 h-75 m-auto">
        <div id="calendar"></div>
    </div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const calendarEl = document.getElementById('calendar');
        const calendar = new FullCalendar.Calendar(calendarEl, {
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
            },
            locale: 'fr',
            buttonIcons: false,
            weekNumbers: true,
            navLinks: true,
            editable: false,
            droppable: false,
            dayMaxEvents: true,
            themeSystem: 'bootstrap',
            events: '${pageContext.request.contextPath}<%=Registries.PATH_API_ACTIVITY%>'
        });
        calendar.render();
    });
</script>
<script>
    title("Mes activités");
</script>
<%@ include file="/html/bottom.jsp" %>