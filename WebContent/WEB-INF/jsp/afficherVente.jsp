<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/png" href="./lib/iconeLogo.png" />

<%@include file="integrationStyle.jsp"%>

<title>liste article</title>
</head>
<body>
	<div class="row justify-content-center">
		<div class="row col-md-12">

			<c:if test="${empty listeRecherchee }">
				<div class="card-body" style="">
					<article style="text-align: center;">Aucun element ne
						correspond à votre recherche</article>
				</div>
			</c:if>
			<c:if test="${!empty listeRecherchee }">
				<c:forEach var="v" items="${listeRecherchee }">

					<div class="row class="container">
						<div class="card-body">
							<div class="col-md-20" style="border: 1px black dashed;">
								<div class="row ">
									<div class="col-9">
										<a href="ServDetailVente?numVente=${v.getNumVente()}"><center>
												<b>${v.getNomArticle()} </b></a>
										<p>
											<Strong>Dernière offre </Strong>: ${v.getPrixvente()} points
											<br /> <Strong>Fin de l'enchère le </Strong>${v.getDateFinEnchere()}<br />
											<Strong>Article à retirer à </Strong>
											<c:forEach var="r" items="${listeRetrait }">
												<c:if test="${r.getNoVente() == v.getNumVente()}">
								 	 : ${r.getVille() }
								 	</c:if>
											</c:forEach></center>
										</p>
									</div>
									<div class="col-3">
										<a href="ServDetailVente?numVente=${v.getNumVente()}"><img src="./pic/${v.getPhoto()}"
											class="bd-placeholder-img img-thumbnail" width="200"
											height="200" role="img" aria-label="arialabel"
											preserveAspectRatio="xMidYMid slice" focusable="true"></a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>

		</div>
	</div>
	<jsp:include page="/WEB-INF/fragments/footer.jsp"></jsp:include>
</body>
</html>