package fr.eni.vae.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.vae.bll.UtilisateurManager;
import fr.eni.vae.bo.Utilisateur;
import fr.eni.vae.dal.DALException;

/**
 * Servlet implementation class AutreProfil
 */
@WebServlet("/AutreProfil")
public class ServAutreProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Récupération des attibuts de la BDD

		String pseudo = request.getParameter("pseudo");
		Utilisateur utilisateur = null;
		HttpSession session = request.getSession();
		int numUtil = (int) session.getAttribute("numUtil");

		try {
			utilisateur = UtilisateurManager.autreUtilisateur(pseudo);
		} catch (DALException e) {
			e.printStackTrace();
		}

		request.setAttribute("PseudoVendeur", utilisateur.getPseudo());
		request.setAttribute("RueVendeur", utilisateur.getRue());
		request.setAttribute("CpVendeur", utilisateur.getCp());
		request.setAttribute("VilleVendeur", utilisateur.getVille());
		request.setAttribute("TelephoneVendeur", utilisateur.getTelephone());

		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/autreProfil.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
