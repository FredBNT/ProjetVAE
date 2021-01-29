<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.png" />
<%@include file="integrationStyle.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- vro/page principale -->

<title>Vente Aux Enchères</title>
<html>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.jpg" />
<%@include file="integrationStyle.jsp"%>

<jsp:include page="/WEB-INF/fragments/header.jsp"></jsp:include>

<body>
	<c:if test="${sessionScope.sessionUtilisateur.prenom != null}"
		var="test">
		<br>
		<div class="jumbotron">
			<div class="text-center" id="titre">
				<p id="Paragraphe">
					<b>Bonjour ${sessionScope.sessionUtilisateur.prenom}, </b> <b>vous
						avez ${sessionScope.sessionUtilisateur.credit} crédit(s)</b>
				</p>
			</div>
		</div>
	</c:if>
	<!-- Messages -->
	<c:if test="${MessageAjoutVente != null}" var="test">
		<div class="alert alert-success" role="alert">${MessageAjoutVente}</div>
	</c:if>
	<c:if test="${ModificationOK != null}" var="test">
		<div class="alert alert-success" role="alert">${ModificationOK }</div>
	</c:if>
	<c:if test="${messageEnchereOK != null}" var="test">
		<div class="alert alert-success" role="alert">${messageEnchereOK}</div>
	</c:if>
	<c:if test="${messageCreditInsuf != null}" var="test">
		<div class="alert alert-danger" role="alert">${messageCreditInsuf}</div>
	</c:if>
	<c:if test="${SuppressionVente != null}" var="test">
		<div class="alert alert-success" role="alert">${SuppressionVente}</div>
	</c:if>
	<c:if test="${SuppressionEnchère != null}" var="test">
		<div class="alert alert-success" role="alert">${SuppressionEnchère}</div>
	</c:if>
	<c:if test="${VenteCloturee1 != null && VenteCloturee2 != null}"
		var="test">
		<div class="alert alert-success" role="alert">${VenteCloturee1}</div>
		<div class="alert alert-success" role="alert">${VenteCloturee2}</div>
	</c:if>

	<!-- Fin des messages -->

	<br>
	<br>
	<div class="row justify-content-center">

		<div class="col-md-6">
			<div class="card">
				<header class="card-header">
					<c:if test="${!empty sessionScope.sessionUtilisateur}">
						<h4 class="float-right btn btn-outline-primary mt-1">${sessionScope.sessionUtilisateur.pseudo}
							Connecté</h4>
					</c:if>
					<c:if test="${empty sessionScope.sessionUtilisateur}">
						<h4 class="float-right btn btn-outline-primary mt-1">Vous
							n'êtes pas connecté</h4>
					</c:if>

				</header>
				<article class="card-body" style="text-align: right;"></article>

				<form action="./rechercheVente" method="post">
					<input type="text"
						style="width: 200px; display: block; text-align: center; margin: auto;"
						name="sRecherche" placeholder="Le nom de l'article contient">
					<h6></h6>

					<div class="col form-group" style="text-align: center;">
						<label>Catégories </label> <select id="inputCategories"
							style="width: 200px; display: block; margin: auto;"
							class="form-control" name="sCategorie">
							<option selected="">Toutes</option>
							<option>Auto</option>
							<option>Sport</option>
							<option>Informatique</option>
							<option>Cuisine</option>
							<option>Brico</option>
						</select>
					</div>
					<h6></h6>

					<div style="text-align: center;">
						<h3>Filtres :</h3>

						<div class="form-check form-check-inline">
							<input type="checkbox" class="form-check-input" id="cVentes"
								name="mVentes"> <label for="scales">Mes ventes</label>
						</div>

						<div class="form-check form-check-inline">
							<input type="checkbox" class="form-check-input" id="cEnchere"
								name="mEnchere"> <label for="horns">Mes enchères
								en cours</label>
						</div>

						<div class="form-check form-check-inline">
							<input type="checkbox" class="form-check-input"
								id="cAcquisitions" name="mAcquisitions"> <label
								for="horns">Mes acquisitions</label>
						</div>

						<div class="form-check form-check-inline">
							<input type="checkbox" class="form-check-input" id="cAutres"
								name="mAutres"> <label for="horns">Autres
								enchères</label>
						</div>
					</div>
					<br> <input class="btn btn-primary btn-block"
						style="width: 200px; display: block; margin: auto; text-align: center;"
						type="submit" value="Rechercher">

				</form>
				<jsp:include page="afficherVente.jsp"></jsp:include>
			</div>

		</div>

	</div>
	<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>
</body>

</html>