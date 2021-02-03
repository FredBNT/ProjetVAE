<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.png"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<%@include file="integrationStyle.jsp"%>

<html>

<head>
<meta charset="UTF-8">
<title>Détail de la vente</title>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/header.jsp"></jsp:include>
	<br>
	<div class="container">

		<div class="col-xs-4 col-xl-12 col-md-12">

			<form action="./ServEncherir" method="post">
				<label>Vente n°</label><input type="text" name="NumVente"
					value="${NumVente }" readonly="readonly"> <strong><br>
					<br> Nom de l'article : </strong> ${NomArticle}<br> <strong><br>
					Description : </strong> ${Description }<br /> <br> 
				
				<img src="./pic/${photo}" class="bd-placeholder-img img-thumbnail" width="200" height="200" role="img" 
				aria-label="arialabel" preserveAspectRatio="xMidYMid slice" focusable="true"><br> 
	
				<c:if test="${PrixVente>PrixInitial }">
				<strong> Meilleure offre : </strong>${PrixVente}
				par <a href="AutreProfil?pseudo=${pseudoEnchere}">${pseudoEnchere}</a><br />
				</c:if>
				<br> <strong> Mise à prix : </strong> ${PrixInitial}<br /> <strong><br>
					Fin de l'enchère :</strong> ${DateFinEnchere}<br /> <br> <strong>Retrait
					: </strong><br>
				<div class="row">
					<div class="col-xl-4">
						${NomRueRetrait}<br> ${CodePostal} ${NomVilleRetrait}
					</div>
				</div>
				<br>
				<strong>Vendeur : </strong><a href="AutreProfil?pseudo=${pseudoVente}">${pseudoVente}<br>
					<br></a>
				
		<c:if test="${pseudoVente != sessionScope.sessionUtilisateur.pseudo}"
			var="test">
			<c:out value="${param.btn}" />
			<div class="row">
				<div class="col-xs-6">
					<span>Ma proposition :  </span>

				</div>
				<div class="col-xs-4">
					<input type="number" class="" id="propoEnchere"
						name="sPropoEnchere" min="${PrixVente + 1}"
						value="${PrixVente + 1}"><br>
				</div>


				<div class="col-xs-2">
					<button type="submit" class="btn btn-primary" name="sEncherir"
						value="Encherir">Enchérir</button>
					<br> <br>
				</div>
			</div>
		</c:if>
		
		</form>
		</div>
		<div class="col-xs-8 xl-4">
		<c:if test="${TestPresence != 0}" var="test">
			<a href="ServAnnulEnchere?numVente=${NumVente}"><button
					type="submit" class="btn btn-primary btn-block"
					name="sAnnulEnchere" value="Encherir">Annuler l'enchère</button></a>
		
		</c:if>
		</div>


		
		<div class="col-xs-8 xl-4">
		<c:if test="${pseudoVente == sessionScope.sessionUtilisateur.pseudo}"
			var="test">
			<a href="ServAnnulVente?numVente=${NumVente}"><button
					type="submit" class="btn btn-primary btn-block"
					name="sAnnulEnchere" value="Encherir">Annuler la vente</button></a>
		</div>
		</c:if>


		<div class="col-xs-8 xl-4">
		<br /> <a href="./ServPagePrincipale"><button type="submit"
				class="btn btn-primary btn-block">Retour à la page
				principale</button></a> <br> <br />
		</div>
	</div>
	</div>
</body>

<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>
</html>