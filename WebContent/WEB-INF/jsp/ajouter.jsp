<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.jpg" />
<%@include file="integrationStyle.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<a class="navbar-brand">Vente Aux Enchères </a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNavAltMarkup"
			aria-controls="navbarNavAltMarkup" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-link active" aria-current="page" href="index.html">Accueil</a>
				<a class="nav-link" href="index.html">Annuler</a> <a
					class="nav-link" href="#">A propos</a>
			</div>
		</div>
	</div>
</nav>


<h2 class="text-center">Création de nouveau compte</h2>

<c:if test="${erreurPseudo != null}" var="test">
	<div class="alert alert-danger" role="alert">${erreurPseudo}</div>
</c:if>
<c:if test="${erreurMail != null}" var="test">
	<div class="alert alert-danger" role="alert">${erreurMail}</div>
</c:if>
<c:if test="${erreurTel != null}" var="test">
	<div class="alert alert-danger" role="alert">${erreurTel}</div>
</c:if>
<c:if test="${erreurMDP != null}" var="test">
	<div class="alert alert-danger" role="alert">${erreurMDP}</div>
</c:if>
<c:if test="${messageCreation != null}" var="test">
	<div class="alert alert-danger" role="alert">${messageCreation}</div>
</c:if>
<!--  AMR  -->
<div class="container">



	<div class="row justify-content-center">
		<div class="col-md-6">
			<div class="card">
				<header class="card-header">
					<h4 class="card-title mt-2">Inscription</h4>
				</header>
				<article class="card-body">
					<form action="./AjouterUtilisateur" method="post">
						<div class="form-row">
							<div class="col form-group">
								<label>Pseudo </label> <input type="text" class="form-control"
									placeholder="" name="sPseudo" minlength="3" maxlength="30"
									required="required">
								<p>
									<font size="-1"><i>Entre 3 et 30 caractères</i></font>
								</p>
							</div>
							<!-- form-group end.// -->
							<div class="col form-group">
								<label>Nom</label> <input type="text" class="form-control"
									placeholder=" " name="sNom" pattern="[^0-9]{3,30}">
								<p>
									<font size="-1"><i>Entre 3 et 30 caractères</i></font>
								</p>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-row">
							<div class="col form-group">
								<label>Prenom </label> <input type="text" class="form-control"
									placeholder="" name="sPrenom" pattern="[^0-9]{3,30}">
								<p>
									<font size="-1"><i>Entre 3 et 30 caractères</i></font>
								</p>
							</div>
							<!-- form-group end.// -->
							<div class="col form-group">
								<label>Email</label> <input type="email" class="form-control"
									placeholder="" name="sMail" required="required"> <small
									class="form-text text-muted" maxlength="50"
									pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$">Votre
									adresse mail ne sera pas partagée.</small>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-row">
							<div class="col form-group">
								<label>Téléphone </label> <input type="text"
									class="form-control" placeholder="" name="sTelephone"
									placeholder=""
									pattern="[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}"
									required="required">
							</div>
							<!-- form-group end.// -->
							<div class="col form-group">
								<label>Adresse</label> <input type="text" class="form-control"
									placeholder="" name="sRue" maxlength="30" required="required">
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->

						<div class="form-row">
							<div class="col form-group">
								<label>Code Postal </label> <input type="text"
									class="form-control" placeholder="" name="sCP" max="99999"
									pattern="[0-9]{5}" required="required">
							</div>
							<!-- form-group end.// -->
							<div class="col form-group">
								<label>Ville</label> <select id="inputVille"
									class="form-control" name="sVille" required="required">
									<option>Sélectionnez une ville</option>
									<option>Paris</option>
									<option>Brest</option>
									<option selected="">Nantes</option>
									<option>Angers</option>
									<option>Tours</option>
								</select>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->

						<div class="form-row">
							<div class="col form-group">
								<label>Mot de Passe </label> <input class="form-control"
									type="password" name="sMDP" maxlength="30"
									pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
									title="Doit contenir une minuscule, une majuscule et un chiffre">
							</div>
							<!-- form-group end.// -->
							<div class="col form-group">
								<label>Confirmation du mot de passe</label> <input
									class="form-control" type="password" name="sVMDP"
									maxlength="30">
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->

						<br>
						<div class="form-group">
							<button type="submit" class="btn btn-primary btn-block">
								Inscription</button>

						</div>
						<!-- form-group// -->
					</form><br>
					<form method="get" action="index.html">
						<button class="btn btn-warning btn-block" type="submit">Abandonner</button>
					</form>
				</article>
				<!-- card-body end .// -->
				<div class="border-top card-body text-center"></div>
			</div>
			<!-- card.// -->
		</div>
		<!-- col.//-->

	</div>
	<!-- row.//-->


</div>

<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>
<!--container end.//-->
