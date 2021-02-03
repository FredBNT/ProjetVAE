package fr.eni.vae.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.vae.bll.EnchereManager;
import fr.eni.vae.bll.UtilisateurManager;
import fr.eni.vae.bll.VenteManager;
import fr.eni.vae.bo.Enchere;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.DALException;

/**
 * Servlet implementation class ServEncherir AMR
 */
@WebServlet("/ServEncherir")
public class ServEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int proposEnchere = Integer.parseInt(request.getParameter("sPropoEnchere"));
		int numVente = Integer.parseInt(request.getParameter("NumVente").trim());
		HttpSession session = request.getSession();
		int numUtil = (int) session.getAttribute("numUtil");
		int nbrCreditUtil = (int) session.getAttribute("Credit");

		int ajoutCredit = 0;
		LocalDateTime dateTime = LocalDateTime.now(); // Date de mtn
		String date; //
		date = dateTime.toString(); // besoin de le mettre en to string pour l'insérer dans la base
		Vente laVente = null; // Objet vente pour y récupérer les méthodes get pour notamment le getdate
		Enchere verifEnchere = null; // Objet Encherir pour vérifier si user à déjà enchéri

		int montantEnchere;
		int vendeur = -1;

		// Vérification si l'utilisateur en cours n'a pas déjà enchéri

		try {
			verifEnchere = EnchereManager.verificationUserEncherir(numVente, numUtil);
		} catch (DALException e) {

			e.printStackTrace();
		}

		if (verifEnchere != null) {

			montantEnchere = verifEnchere.getPrixEnchere();
			// Permet de MAJ le crédit
			ajoutCredit = montantEnchere + nbrCreditUtil;
			try {
				UtilisateurManager.modifierCredit(ajoutCredit, numUtil);
				EnchereManager.suppressionEnchere(numUtil, numVente);

			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		/**
		 * 
		 */

		try {
			laVente = VenteManager.obtenirDateFinEnchere(numVente);
		} catch (DALException e2) {

			e2.printStackTrace();
		}
		LocalDate dateFinEnchere = laVente.getDateFinEnchere();
		LocalDateTime dateFinEnchere2 = dateFinEnchere.atStartOfDay();

		// méthode pour obtenir le numéro du vendeur associé à la vente
		try {
			vendeur = VenteManager.obtenirLeNumVendeur(numVente);
		} catch (DALException e2) {

			e2.printStackTrace();
		}
		// condition pour vérifier que le montant de l'enchere est supériere à celui en
		// cours.
		// condition pour également vérifier que la date de l'enchère ne soit pas
		// dépassée
		if (vendeur == numUtil) {
			request.setAttribute("message", "Vous ne pouvez pas enchérir sur votre propre vente !");

			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/detailVente.jsp").forward(request, response);
		} else if (nbrCreditUtil >= proposEnchere && dateTime.isBefore(dateFinEnchere2)) {
			// on va écire en BD le prix de vente
			try {
				VenteManager.initPrixVente(proposEnchere, numVente);
			} catch (DALException e) {

				e.printStackTrace();
			}

			Enchere enchere = new Enchere(dateTime, numUtil, numVente, proposEnchere);

			// on créer l'enchere en BD
			try {
				EnchereManager.ajouterEnchere(date, enchere);
			} catch (DALException e1) {

				e1.printStackTrace();
			}

			// on va ici débiter l'utilisateur des crédits utilisés pour faire l'enchère.SUR
			try {
				int creditRestant = ajoutCredit - proposEnchere; // opération pour savoir combien de crédit aura
																	// l'utilisateur après débit
				UtilisateurManager.modifierCredit(creditRestant, numUtil);
			} catch (DALException e) {

				e.printStackTrace();
			}

			request.setAttribute("messageEnchereOK", "Votre enchere a bien ete prise en compte, bonne chance!");
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/PagePrincipale.jsp").forward(request, response);

		} else {
			// A faire: 2 messages différents si c'est un souci de crédit ou de date.
			request.setAttribute("messageCreditInsuf",
					"Vous ne disposez pas de suffisament de crédits ou l'enchère est maintenant terminée !");
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/PagePrincipale.jsp").forward(request, response);

		}

	}

}