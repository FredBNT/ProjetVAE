package fr.eni.vae.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.vae.bo.Enchere;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.DALException;
import fr.eni.vae.bll.EnchereManager;
import fr.eni.vae.bll.UtilisateurManager;
import fr.eni.vae.bll.VenteManager;

@WebServlet("/ServAnnulEnchere")
public class ServAnnulEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Méthode pour annuler une enchère
		int numVente = Integer.parseInt(request.getParameter("numVente").trim());
		HttpSession session = request.getSession();
		int numUtil = (int) session.getAttribute("numUtil");
		Enchere verifEnchere = null;
		Enchere meilleureMontant = null;
		Vente vente = null;
		int montantEnchere = 0;
		int nbrCreditUtil = (int) session.getAttribute("Credit");
		int ajoutCredit = 0;

		try {
			verifEnchere = EnchereManager.verificationUserEncherir(numVente, numUtil);
			montantEnchere = verifEnchere.getPrixEnchere();
			ajoutCredit = montantEnchere + nbrCreditUtil;
			UtilisateurManager.modifierCredit(ajoutCredit, numUtil);

			// Suppression d'une enchère
			EnchereManager.suppressionEnchere(numUtil, numVente);

			// Récupère le numUtil et le montantEnchere pour MAJ la BDD
			meilleureMontant = EnchereManager.rechercheMeilleureMontant(numVente);

			if (meilleureMontant.getPrixEnchere() == 0) {
				// MAJ de la table Vente
				vente = VenteManager.AfficherArticle(numVente);
				VenteManager.initPrixVente(vente.getPrixInitial(), numVente);

			} else {
				// MAJ de la table Vente
				VenteManager.initPrixVente(meilleureMontant.getPrixEnchere(), numVente);
			}

		} catch (DALException e) {
			e.printStackTrace();
		}

		request.setAttribute("SuppressionEnchère", "Votre enchère vient d'être supprimée");
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/PagePrincipale.jsp").forward(request, response);
	}

}
