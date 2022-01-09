<%@ page import="fr.poweroff.web.Registries" %>
<%--
  Created by IntelliJ IDEA.
  User: AlasDiablo
  Date: 08/01/2022
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="map-chart"></div>
<script>
    vegaEmbed('#map-chart', {
        $schema: 'https://vega.github.io/schema/vega-lite/v5.json',
        width: 400,
        height: 300,
        layer: [
            {
                data: {
                    url: '${pageContext.request.contextPath}/public/a-reg2021-topojson.json',
                    format: {
                        type: 'topojson',
                        feature: 'a-reg2021'
                    }
                },
                projection: {
                    type: 'mercator'
                },
                mark: {
                    type: 'geoshape',
                    fill: 'lightgray',
                    stroke: 'white'
                }
            },
            {
                data: {
                    url: '${pageContext.request.contextPath}<%=Registries.PATH_API_DATA_MAP%>'
                },
                projection: {
                    type: 'mercator'
                },
                mark: 'circle',
                encoding: {
                    longitude: {
                        field: 'longitude',
                        type: 'quantitative'
                    },
                    latitude: {
                        field: 'latitude',
                        type: 'quantitative'
                    },
                    size: {
                        field: 'count',
                        title: 'Contaminations',
                        type: 'quantitative'
                    },
                    color: {
                        value: '#FF8585'
                    }
                }
            }
        ]
    });
</script>
