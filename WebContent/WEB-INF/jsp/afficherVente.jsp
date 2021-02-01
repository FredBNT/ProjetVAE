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

					<div class="row col-md-25">
						<div class="card-body">
							<div class="col-md-20"
								style="margin: auto; border: 1px black dashed;">
								<a href="ServDetailVente?numVente=${v.getNumVente()}"><Strong>${v.getNomArticle()}
								</strong> </a>
								<p>
									<Strong>Dernière offre </Strong>: &euro; ${v.getPrixvente()} <br />
									<Strong>Fin de l'enchère le </Strong>${v.getDateFinEnchere()}<br />
									<Strong>Article à retirer à </Strong>
									<c:forEach var="r" items="${listeRetrait }">
										<c:if test="${r.getNoVente() == v.getNumVente()}">
								 	 : ${r.getVille() }
								 	</c:if>
									</c:forEach>
								</p>
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