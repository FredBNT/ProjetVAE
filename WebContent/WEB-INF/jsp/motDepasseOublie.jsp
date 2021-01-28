<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!-- Jonathan / Page d'accueil -->
<html>
<head>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.png"/>
<meta charset="UTF-8">
<title>Ventes Aux Enchères</title>
</head>

<%@include file="integrationStyle.jsp" %>

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


<body>
	<div class="container">
		<div class="row justify-content-center">

			<div class="col-md-6">
				
				<div class="card">
					<header class="card-header">
						<h4 class="card-title mt-2">Mot de passe Oublié</h4>
					</header>
					<article class="card-body">
						<!-- Formulaire de connexion -->
						<form action="./index.html" " method="post">
							<div class="form-row">
								<div class="col form-group">
									<label>Entrez votre identifiant ou votre adresse mail :
									</label> <input type="text" class="form-control"
										placeholder="Pseudo ou Email" name="sPseudo"
										required="required">
								</div>
								<!-- form-group end.// -->
							</div>
							<!-- form-row end.// -->
				</div>
				<!-- Button trigger modal -->
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#exampleModal">Réinitialiser le mot de passe</button>
				</form>
				<!-- Modal -->
				<div class="modal fade" id="exampleModal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">Mot de passe
									réinitialisé</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							
							</div>
							<div class="modal-body">Un nouveau mot de passe vient de
								vous être envoyé par mail</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Fermer</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- form-group// -->
			

			<%
				if (request.getAttribute("error") != null) {
			%>
			<div class="alert alert-danger" role="alert"><%=request.getAttribute("error")%></div>
			<%
				}
			%>
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