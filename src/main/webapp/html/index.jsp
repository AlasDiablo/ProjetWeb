<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/html/top.jsp" %>

<h1 class="display-1 text-center">Accueil</h1>

<div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
        <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner ">
        <div class="carousel-item active justify-content-center">
            <img src="${pageContext.request.contextPath}/public/covid_molecule.jpg" class="d-block justify-content-center h-100 w-50" alt="..." style="margin: auto;">
            <div class="carousel-caption d-none d-md-block">
                <h5>Empêcher la circulation de la Covid</h5>
                <p>Le virus touche tout le monde, protégez vous !</p>
            </div>
        </div>
        <div class="carousel-item justify-content-center">
            <img src="${pageContext.request.contextPath}/public/covid-19_molecule.jpg" class="d-block justify-content-center h-80 w-50" alt="..." style="margin: auto;">
            <div class="carousel-caption d-none d-md-block">
                <h5>Signaler les cas positifs de la Covid</h5>
                <p>Le virus se propage facilement et rapidement.</p>
            </div>
        </div>
        <div class="carousel-item justify-content-center ">
            <img src="${pageContext.request.contextPath}/public/covid-19_molecule2.jpg" class="d-block justify-content-center h-80 w-50" alt="..." style="margin: auto;">
            <div class="carousel-caption d-none d-md-block">
                <h5>Renseigner ses amis et ses activités</h5>
                <p>Le virus peut faire du mal, protégez vos amis, votre famille et les personnes que vous croisez.</p>
            </div>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-target="#carouselExampleCaptions" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-target="#carouselExampleCaptions" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </button>
</div>
<br>
<div class="container">
    <div class="card border-success">
        <div class="card-header">
            Limiter les risques
        </div>
        <div class="card-body">
            <blockquote class="blockquote mb-0">
                <p>Afin de limiter au mieux les risques de contamination, nous devons suivre les gestes barrières. Cependant si nous sommes diagnostiqué positif
                au covid, nous vous recommandons de renseigner celà sur ce site. Nous vous recommandons également de renseigner vos activités.</p>
                <p class="text-success">Permet de prévenir vos amis et les personnes présentes au même endroit qu'elles sont cas contact.</p>
            </blockquote>
        </div>
    </div>
    <br>
    <div class="card border-danger">
        <div class="card-header">
            Les gestes barrières
        </div>
        <div class="card-body">
            <blockquote class="blockquote mb-0">
                <img src="${pageContext.request.contextPath}/public/hygiene_anti_covid_19_.jpg" class="d-block justify-content-center " alt="hygiene" style="margin: auto;">
                <footer class="blockquote-footer">Source de l'image sur le site officiel du <cite title="Source Title"><a href="https://www.gouvernement.fr/prevention-contre-la-covid-19-les-gestes-a-adopter">gouvernement</a>.</cite></footer>
            </blockquote>
        </div>
    </div>
</div>
<script>
    title("Accueil");
</script>
<%@ include file="/html/bottom.jsp" %>