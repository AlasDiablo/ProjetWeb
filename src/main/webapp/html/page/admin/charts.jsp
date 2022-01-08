<%--
  Created by IntelliJ IDEA.
  User: AlasDiablo
  Date: 08/01/2022
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/html/top.jsp" %>
<div class="container">
    <h1 class="display-1 text-center">Graphique et Diagramme</h1>

    <div class="row">
        <div class="col-sm p-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Historique des contamination</h5>
                    <%@ include file="/html/components/chart/history.jsp" %>
                </div>
            </div>
        </div>

        <div class="col-sm p-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Carte des contamination</h5>
                    <%@ include file="/html/components/chart/map.jsp" %>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    #history-chart {
        padding: 0;
    }

    #map-chart {
        padding: 0;
    }

    .has-actions details {
        display: none;
        visibility: hidden;
    }
</style>
<script>
    title("Graphique et Diagramme");
</script>
<%@ include file="/html/bottom.jsp" %>
