<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<title>Vente Aux Enchères</title>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.png" />
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
				<a class="nav-link" href="./AjouterUtilisateur">Créer un compte</a>
				<a class="nav-link" href="#">A propos</a>
			</div>
		</div>
	</div>
</nav>


<div class="container">



	<div class="row justify-content-center">
		<div class="col-md-6">
			<div class="card">
				<header class="card-header">

					<h4 class="card-title mt-2">Veuillez saisir votre mot de passe
						pour continuer</h4>
				</header>
				<article class="card-body">
					<form action="ServMdpModifs" method="post">
						<div class="form-row">
							<div class="col form-group">

								<div class="form-row">
									<div class="col form-group">
										<label>Mot de passe </label> <input class="form-control"
											type="password" name="MdpSecu">
									</div>
									<!-- form-group end.// -->

									<!-- form-group end.// -->
								</div>
								<!-- form-row end.// -->
								<c:if test="${error != null}" var="test">
									<div class="alert alert-danger" role="alert">${error}</div>
								</c:if><br>




								<div class="form-group">
									<button type="submit" class="btn btn-primary btn-block"
										value="valider" name="mChoix">Valider</button>
									 <a href="./Profil">
										<button type="submit" class="btn btn-primary btn-block justify-content-md-end"
											value="retour" name="mChoix">Retour</button>
									</a>
								</div>
							</div>
						</div>
						<!-- form-group// -->
					</form>

				</article>

			</div>
			<!-- card.// -->
		</div>
		<!-- col.//-->

	</div>
	<!-- row.//-->


</div>

<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>