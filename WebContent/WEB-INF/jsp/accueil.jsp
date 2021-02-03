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
<!-- deux façon de faire des IF dans les JSP -->
<%
	if (request.getAttribute("MessageAjoutUser") != null) {
%>
<div class="alert alert-success" role="alert"><%=request.getAttribute("MessageAjoutUser")%></div>
<%
	}
%>
<c:if test="${error != null}" var="test">
	<div class="alert alert-danger" role="alert">${error}</div>
</c:if>
			

<div class="container">
	<div class="row justify-content-center">


		<div class="col-md-6">
			<h1 class="text-muted"></h1>
			<p class="text-muted"></p>
			<div class="card">
				<header class="card-header">
					<h4 class="card-title mt-2">Connexion</h4>
				</header>
				<article class="card-body">
					<!-- Formulaire de connexion -->
					<form action="./connexion" method="post">
						<div class="form-row">
							<div class="col form-group">
								<label>Identifiant </label> <input type="text"
									class="form-control" placeholder="Pseudo ou Mail"
									name="sPseudo" value="${cookie.LOGIN.value}" required autofocus>
							</div>
						</div>
						<div class="form-row">
							<div class="col form-group">
								<label>Mot de Passe </label> <input class="form-control"
									type="password" placeholder="Mot de passe" name="sMdp"
									required="required">
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div>
							<input type="checkbox" class="form-check-input" id="memoire">
							<label class="checkbox checkbox-black" for="memoire"
								value="${sessionScope.checkMemoire}"> Se souvenir de moi
							</label>
							<p class="text-end">
								<a href="./ServOublieMDP" id="MotDePasseOublie">Mot de passe
									oublié ?</a>
							</p>
						</div>

						<div class="form-group">
							<button type="submit" class="btn btn-dark btn-block"
								name="sConnexion">Connexion</button>
						</div>
						<!-- form-group// -->
					</form>


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

<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>