<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.jpg" />
<meta charset="UTF-8">
<title>Nouvelle Vente</title>
<%@include file="integrationStyle.jsp"%>
</head>
<jsp:include page="/WEB-INF/fragments/header.jsp"></jsp:include>

<c:if test="${erreur != null}" var="test">
	<div class="alert alert-danger" role="alert">${erreur}</div>
</c:if>

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

<div class="col-md-12" style="text-align: center;">

	<form action="./ServNouvelleVente" method="post">

		<div class="form-row">
			<div class="form-group col-md-12">
				<label for="Article">Article : </label> <input
					style="width: 250px; display: block; margin: auto;" type="text"
					class="form-control" id="Article"
					placeholder="Entrez le nom de l'article" name="sArticle"
					required>
				<p></p>
			</div>
			<div class="form-group col-md-12">
				<label for="Description">Description</label>
				<textarea style="width: 250px; display: block; margin: auto;"
					type="text" class="form-control" id="description"
					placeholder="Entrez une description" name="sDescription"
					required></textarea>
				<p></p>
			</div>
		</div>
		<div class="input-group md-12">
			<div class="input-group mb-12">
				<div class="form-group col-md-12">
					<label> Upload : </label>
					<div class="custom-file">
						<input type="file" class="custom-file-input" id="inputGroupFile01"
							aria-describedby="inputGroupFileAddon01" name="sPhoto"> <label
							class="custom-file-label" for="inputGroupFile01">Choisissez
							une photo</label>
					</div>
					<p></p>
				</div>
			</div>
			<div class="input-group mb-12">
				<div class="form-group col-md-12">
					<label> Catégorie : </label> <select id="inputCate"
						style="width: 200px; display: block; margin: auto;"
						class="form-control" name="sCate">
						<option  value="0" selected="">Toutes</option>
						<c:forEach items="${ categorie }" var="categorie">
							<option value="${ categorie.numCate }">${ categorie.libelle }</option>
						</c:forEach>
					</select>
					<p></p>
				</div>
			</div>
		</div>
		<h6></h6>


		<div class="form-group">
			<label for="PrixInitial">Prix initial :</label> <input
				style="width: 250px; display: block; margin: auto;" type="number"
				class="form-control" id="prix" name="sPrix" min="0"
				required>
			<p></p>
		</div>
		<div class="form-row">
			<div class="form-group col-md-12">
				<label for="FinEnchere">Fin de l'enchère :</label> <input
					style="width: 250px; display: block; margin: auto;" type="date"
					class="form-control" id="date" min="2019-07-05" name="sDate"
					required>
				<p></p>
			</div>
			<div class="form-group col-md-12">
				<label for="inputState">Retrait</label> 
				<input style="width: 350px; display: block; margin: auto;"
					class="form-control" id="retraitRue" value="${rue}" 
					name="sAdresse" required placeholder="Entrez une adresse">
				<input style="width: 150px; display: block; margin: auto;"
					type="text" class="form-control" id="retraitCp" value="${cp}"
					name="sCP" required placeholder="Entrez un code postal"> 
				<input
					style="width: 200px; display: block; margin: auto;" type="text"
					class="form-control" id="retaritVille" value="${ville}"
					name="sVille" required placeholder="Entrez une vile">
				<p></p>
			</div>

		</div>
		<button type="submit" class="btn btn-primary" name="sEnregistrer">Enregistrer</button>
		<button type="submit" class="btn btn-primary" name="sPublier"
			value="1">Publier</button>
	</form>
</div>


<div class="form-group col-md-12" style="text-align: center;">
	<br> <a href="ServPagePrincipale"><button type="submit"
			class="btn btn-primary">Annuler</button></a>

</div>
<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>
</html>