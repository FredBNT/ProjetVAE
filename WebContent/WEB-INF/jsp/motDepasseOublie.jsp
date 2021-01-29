<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.png" />
<%@include file="integrationStyle.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<meta charset="UTF-8">
<title>Ventes Aux Enchères</title>
</head>

<%@include file="integrationStyle.jsp"%>

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

<c:if test="${inexistant != null}" var="test">
	<div class="alert alert-danger" role="alert">${inexistant}</div>
</c:if>
<c:if test="${existMail != null}" var="test">
	<div class="alert alert-success" role="alert">${existMail}</div>
</c:if>
<c:if test="${existPseudo != null}" var="test">
	<div class="alert alert-success" role="alert">${existPseudo}</div>
</c:if>

<body>
	<br>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card">
					<header class="card-header">
						<h4 class="card-title mt-2">Mot de passe Oublié</h4>
					</header>
					<article class="card-body">
						<!-- Formulaire de connexion -->
						<form action="ServOublieMDP" method="post">
							<div class="form-row">
								<div class="col form-group">
									<label>Entrez votre identifiant ou votre adresse mail :
									</label> <input type="text" class="form-control"
										placeholder="Pseudo ou Email" name="sPseudo"
										required="required">
								</div>
							</div>
							<br>

							<c:if test="${(!retour)}" var="test">
								<button type="submit" class="btn btn-dark btn-block">Réinitialiser
									le mot de passe</button>
							</c:if>

						</form>
						<c:if test="${(retour)}" var="test">
							<form method="get" action="index.html">
								<button class="btn btn-dark btn-block" type="submit">Retour</button>
							</form>
						</c:if>
				</div>
			</div>

			<!-- form-group// -->






			</article>
			<!-- card-body end .// -->
		</div>
		<!-- card.// -->
	</div>
	<!-- col.//-->
	</div>
	<!-- row.//-->
	</div>
	<!--container end.//-->


</body>
</html>