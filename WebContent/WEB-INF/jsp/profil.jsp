<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.png" />

<%@include file="integrationStyle.jsp"%>

<jsp:include page="/WEB-INF/fragments/header.jsp"></jsp:include>

<div class="jumbotron">
	<div class="text-center" id="titre">
<h1>Modification du profil</h1>
	</div>
</div>
<div class="container">

<div class="col-md-12" style="text-align: center;">

		<div class="form-row">

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
<br>
						<a href="./ServPagePrincipale"><button type="button"
								class="btn btn-primary btn-block">Retour</button></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>

