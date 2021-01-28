<%@include file="integrationStyle.jsp"%>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.jpg" />
<!------ Include the above in your HEAD tag ---------->

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<div class="jumbotron">
	<div class="text-center" id="titre">
		<p id="Paragraphe">
			<b>Bonjour ${sessionScope.sessionUtilisateur.prenom}.</b>
		</p>
		<p id="Paragraphe">
			<b>Vous avez ${sessionScope.sessionUtilisateur.credit} crédit(s)</b>
		</p>
		<p id="¨Paragraphe">
			<b>Votre profil</b>
	</div>
</div>
<div class="container">

	<div class="row">
		<div class="col-xs-6 col-xs-12 col-md-12">
			<div class="well well-sm">
				<div class="row">
					<div class="col-sm-6 col-md-6">
						<img src="./lib/avatar_profil.png" alt="avatar"
							class="img-rounded img-responsive" />
					</div>
					<div class="col-sm-6 col-md-6">
						<h4 style="font-size: 30px">${Pseudo }</h4>
						<br /> <br />
						<form action="./Profil" method="post">
							<p>
								<i class="glyphicon glyphicon-user" style="font-size: 25px">
									${Nom }</i> <br />
							<p style="font-size: 25px; margin-left: 50px">
								${Prenom }
								</font-size>
							</p>
							<br /> <i class="glyphicon glyphicon-earphone"
								style="font-size: 25px"> ${Telephone }</i> <br /> <br /> <br />
							<i class="glyphicon glyphicon-envelope" style="font-size: 25px">
								${Mail }</i> <br /> <br /> <br /> <i
								class="glyphicon glyphicon-map-marker" style="font-size: 25px">
								${Rue } </i>

							<p style="font-size: 25px; margin-left: 50px">${Cp } ${Ville }</p>
							<br /> <i class="glyphicon glyphicon-usd" style="font-size: 25px">
								${Credit } crédits</i>
						</form>
						<br /> <br />
						<!-- Split button -->
						<a href="./ServMdpModifs"><button type="button"
								class="btn btn-primary btn-block">Modifier mon profil</button></a> <br />

						<a href="./ServPagePrincipale"><button type="button"
								class="btn btn-primary btn-block">Retour</button></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>

