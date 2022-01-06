<%@ page import="fr.poweroff.web.Registries" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/html/top.jsp" %>
<div class="container">
    <h1 class="display-1 text-center">Ajout d'un activité</h1>
    <p>Les champs avec * sont obligatoires</p>

    <form name="inscription" action="${pageContext.request.contextPath}<%=Registries.PATH_ADD_ACTIVITY%>" method="post">

        <div class="form-group">
            <label for="address">Adresse (attention l'adresse doit avoir un format valide (utilisé l'auto complétion))
                *</label>
            <input type="text" class="form-control" id="address" aria-describedby="address" name="address" required>
            <div id="search-address" class="text-center">

            </div>
        </div>

        <div class="form-group">
            <label for="start_at">Arrivé *</label>
            <input type="datetime-local" id="start_at" name="start_at" required>

            <label for="end_at">Départ *</label>
            <input type="datetime-local" id="end_at" name="end_at" required>
        </div>


        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <script>
        const performClick = (e) => {
            $('#address').val(e)
        }
        $(function () {
            const input = $('#address');
            const createInputAutocomplete = (data) => {
                const searchAddress = $('#search-address');
                searchAddress.html("")
                data.forEach(element => {
                    const button = "<button class='btn btn-outline-secondary btn-sm' onclick='performClick(\"" + element + "\")'>" + element + "</button>"
                    searchAddress.html(searchAddress.html() + button);
                })
            }
            input.keyup(() => {
                if (input.val() !== '') $.getJSON('${pageContext.request.contextPath}<%=Registries.PATH_API_PLACE%>?type=0&q=' + input.val(), (data) => {
                    createInputAutocomplete(_.map(data.result, 'name'));

                });
            });
            createInputAutocomplete([]);
        });
    </script>
</div>
<script>
    title("Ajouté un activité");
</script>
<%@ include file="/html/bottom.jsp" %>