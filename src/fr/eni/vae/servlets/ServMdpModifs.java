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
 * Servlet implementation class ServMdpModifs
 */
@WebServlet("/ServMdpModifs")
public class ServMdpModifs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/mdpModifs.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String choix = request.getParameter("mChoix");
		request.setAttribute("mChoix", choix);
		Utilisateur vUtilisateur = new Utilisateur();

		HttpSession session = request.getSession();
		int numUtil = (int) session.getAttribute("numUtil");
		String mDp = request.getParameter("MdpSecu");
		if (mDp != null) {

			try {
				vUtilisateur = UtilisateurManager.verificationMdp(numUtil);
			} catch (DALException e) {

				e.printStackTrace();
			}
		}
		String test = vUtilisateur.getMdp();

		if (choix.equals("retour")) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);
		}
		if (mDp.equals(test)) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Mot de passe incorrect");
			doGet(request, response);

		}
	}

}
