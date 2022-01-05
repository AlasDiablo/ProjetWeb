<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouté une activité</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/lodash@4.17.21/lodash.min.js"></script>
</head>
<body>
<%@ include file="../menu.jsp" %>
<div class="container">

    <h1 class="display-1 text-center">Ajout d'un activité</h1>
    <p>Les champs avec * sont obligatoires</p>

    <form name="inscription" action="/add-activity" method="post">

        <div class="form-group">
            <label for="address">Addresse (attention l'addresse doit avoir un format valide (utilisé l'auto complétion))
                *</label>
            <input type="text" class="form-control" id="address" aria-describedby="address" name="address" required>
            <div id="search-address" class="text-center">

            </div>
        </div>

        <div class="form-group">
            <label for="start_at">Arrivé *</label>
            <input type="date" id="start_at" name="start_at" required>

            <label for="end_at">Départ *</label>
            <input type="date" id="end_at" name="end_at" required>
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
                if (input.val() !== '') $.getJSON('/api/place?type=0&q=' + input.val(), (data) => {
                    createInputAutocomplete(_.map(data.result, 'name'));

                });
            });
            createInputAutocomplete([]);
        });
    </script>
</div>
</body>
</html>