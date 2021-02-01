package fr.eni.vae.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.vae.bll.CategorieManager;
import fr.eni.vae.bll.RetraitManager;
import fr.eni.vae.bll.VenteManager;
import fr.eni.vae.bo.Categorie;
import fr.eni.vae.bo.Retrait;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.DALException;

/**
 * Servlet permettant de gerer une nouvelle vente
 */
@WebServlet("/ServNouvelleVente")
public class ServNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String rue;
		String cp;
		String ville;

		rue = (String) session.getAttribute("Rue");
		cp = (String) session.getAttribute("Cp");
		ville = (String) session.getAttribute("Ville");

		request.setAttribute("rue", rue);
		request.setAttribute("cp", cp);
		request.setAttribute("ville", ville);

		List<Categorie> listeCategorie = new ArrayList<>();
		try {
			listeCategorie = CategorieManager.recupCategorie();
		} catch (DALException e) {
			e.printStackTrace();
		}

		request.setAttribute("categorie", listeCategorie);

		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/nouvelleVente.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Vente nVente = null;
		String selectionner = "Selectionner";

		// Récupération des données de la nouvelle vente
		String vArticle = request.getParameter("sArticle");
		String vDescription = request.getParameter("sDescription");
		// ajout vro adresse photo
		String vPhoto = request.getParameter("sPhoto");
		String vPrix = request.getParameter("sPrix");
		String vDate = request.getParameter("sDate");
		String vAdresse = request.getParameter("sAdresse");
		String vCP = request.getParameter("sCP");
		String vVille = request.getParameter("sVille");
		String vCate = request.getParameter("sCate");
		HttpSession session = request.getSession();
		int numUtil = (int) session.getAttribute("numUtil");

		LocalDate dateFin = LocalDate.parse(vDate);
		int prixInit = Integer.parseInt(vPrix);
		int numCate = Integer.parseInt(vCate);
		Retrait retrait;
		
		if (request.getParameter("sPublier") != null) {
			nVente = new Vente(vArticle, vDescription, dateFin, prixInit, prixInit, numUtil, numCate, true, true);
		} else {
			nVente = new Vente(vArticle, vDescription, dateFin, prixInit, prixInit, numUtil, numCate, false, true);
		}

		try {
			VenteManager.ajouter(nVente);
		} catch (DALException e) {
			e.printStackTrace();
		}

		retrait = new Retrait(nVente.getNumVente(), vAdresse, vCP, vVille);

		try {
			RetraitManager.ajouterRetrait(retrait);
		} catch (DALException e) {
			e.printStackTrace();
		}
		response.sendRedirect("./ServPagePrincipale?successCreateVente=1");
	}
}
