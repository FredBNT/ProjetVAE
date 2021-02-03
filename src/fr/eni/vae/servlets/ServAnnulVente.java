package fr.eni.vae.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.vae.bll.EnchereManager;
import fr.eni.vae.bll.RetraitManager;
import fr.eni.vae.bll.UtilisateurManager;
import fr.eni.vae.bll.VenteManager;
import fr.eni.vae.bo.Enchere;
import fr.eni.vae.bo.Utilisateur;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.DALException;

@WebServlet("/ServAnnulVente")
public class ServAnnulVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
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
		// Methode Suppression d'une vente et crédit au dernier enchéreur

		int numVente = Integer.parseInt(request.getParameter("numVente").trim());
		HttpSession session = request.getSession();
		int numUtil = (int) session.getAttribute("numUtil");
		Vente vente = new Vente();
		List<Enchere> listeEnchere = new ArrayList();
		int utilEnchere = 0; // a utiliser pour le nomUtil pour le recréditer du montant ou débiter le
								// gagnant.
		Utilisateur utilisateur = new Utilisateur(); // a utiliser pour le recréditer
		int montant = 0;

		// Recherche la plus grande enchère et crédite le bon user

		try {
			vente = VenteManager.AfficherArticle(numVente);
			listeEnchere = VenteManager.listeEncheres(numVente);
			
		} catch (DALException e) {

			e.printStackTrace();
		}
		
		// on recrédite tous les ceux qui ont fait des enchères même le winner
		for (Enchere enchere : listeEnchere) {
			utilEnchere = enchere.getNoUtilisateur();

			try {
				utilisateur = UtilisateurManager.obtenirUnUtil(utilEnchere); // on obtient l'utilisateur chargé
				montant = enchere.getMontantEnchere() + utilisateur.getCredit(); // pour obtenir le montant à créditer +
																					// ce qu'il a déjà
				UtilisateurManager.modifierCredit(montant, utilEnchere); // méthode pour modifier le crédit en BD
			} catch (DALException e1) {
				e1.printStackTrace();
			}
		}

		// Suppression de l'enchère
		try {
			
			EnchereManager.suppressionEnchereComplete(numVente);
			RetraitManager.supprimerUnRetrait(numVente);
			VenteManager.supprimerVente(numVente, numUtil);
			
		} catch (DALException e) {

			e.printStackTrace();
		}
		
		request.setAttribute("SuppressionVente", "Votre vente vient d'être annulée.");
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/PagePrincipale.jsp").forward(request, response);
	}

}