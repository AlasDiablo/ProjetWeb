<%@ page import="fr.poweroff.web.Registries" %>
<%--
  Created by IntelliJ IDEA.
  User: AlasDiablo
  Date: 08/01/2022
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="history-chart"></div>
<script>
    vegaEmbed('#history-chart', {
        $schema: 'https://vega.github.io/schema/vega-lite/v5.json',
        width: 400,
        height: 300,
        data: {
            url: '${pageContext.request.contextPath}<%=Registries.PATH_API_DATA_HISTORY%>'
        },
        transform: [{
            window: [
                {
                    field: 'count',
                    op: 'mean',
                    as: 'rolling_mean'
                }
            ],
            frame: [-15, 15]
        }],
        encoding: {
            x: {
                field: 'date',
                type: 'temporal',
                title: 'Date'
            },
            y: {
                type: 'quantitative',
                axis: {
                    title: 'Max Contamination and Rolling Mean'
                }
            }
        },
        layer: [
            {
                mark: {
                    type: 'point',
                    opacity: 0.3
                },
                encoding: {
                    y: {
                        field: 'count',
                        title: 'Max Contamination'
                    }
                }
            },
            {
                mark: {
                    type: 'line',
                    color: 'red',
                    size: 3
                },
                encoding: {
                    y: {
                        field: 'rolling_mean',
                        title: 'Rolling Mean of Max Contamination'
                    }
                }
            }
        ]
    });
</script>
