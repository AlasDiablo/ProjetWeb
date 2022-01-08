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
    <div class="p-5 m-auto">
        <div id="calendar"></div>
    </div>
</div>

<!-- Modal Event -->
<button hidden id="delete-activity-event" type="button" data-toggle="modal" data-target="#modalActivity"></button>
<!-- Modal -->
<div class="modal fade" id="modalActivity" tabindex="-1" aria-labelledby="modalConfirmLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalActivityLabel"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="delete-activity-content"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-close" data-dismiss="modal">Retour</button>
                <a type="button" class="btn btn-danger" id="delete-activity-button">Supprimer</a>
            </div>
        </div>
    </div>
</div>

<script>
    const deleteUrl = '${pageContext.request.contextPath}<%=Registries.PATH_ADMIN_ACTIVITY%>';
    const modalContent = document.getElementById('delete-activity-content');
    const modalButton = document.getElementById('delete-activity-button');
    const modalLabel = document.getElementById('modalActivityLabel');
    const buttonEvent = document.getElementById('delete-activity-event');

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
            events: '${pageContext.request.contextPath}<%=Registries.PATH_API_ACTIVITY%>?all',
            eventClick: (info) => {
                const user = info.event.extendedProps.user;

                modalLabel.innerText = "Détails de l'activité n°" + info.event.id;
                modalContent.innerHTML = "Activité de " + info.event.extendedProps.user.firstname + " " + user.lastname + "<br>";
                modalContent.innerHTML += "User ID : " + user.userId + "<br>";
                modalContent.innerHTML += "Email : " + user.email;
                modalButton.href = deleteUrl + "?id=" + info.event.id;

                buttonEvent.click();
            }
        });
        calendar.render();
    });
</script>
<script>
    title("Liste des activités");
</script>
<%@ include file="/html/bottom.jsp" %>