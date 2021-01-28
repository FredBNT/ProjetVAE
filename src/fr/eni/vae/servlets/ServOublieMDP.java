package fr.eni.vae.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.vae.bll.UtilisateurManager;
import fr.eni.vae.dal.DALException;

/**
 * Servlet implementation class MotDePasseOublie
 */
@WebServlet("/ServOublieMDP")
public class ServOublieMDP extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Redirection vers le formulaire de réinitialisation de mot de passe
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/motDepasseOublie.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean testP, testM, testT;
		String mailUtilisateur;
		
		testP = UtilisateurManager.verifPseudo(request.getParameter("sPseudo"));
		testM = UtilisateurManager.verifMail(request.getParameter("sPseudo"));

		if (!testP) {
			//récup adresse mail
			try {
				mailUtilisateur = UtilisateurManager.recupMail(request.getParameter("sPseudo"));
				request.setAttribute("existPseudo", "Ceci est un pseudo, un mail vous a été envoyé à l'adresse " + mailUtilisateur + ".");
			} catch (DALException e) {
				e.printStackTrace();
			}
		}else if (!testM) {
			mailUtilisateur = request.getParameter("sPseudo");
			request.setAttribute("existMail", "Un mail vous a été envoyé à l'adresse " + mailUtilisateur + ".");
		}else {
			request.setAttribute("inexistant", "Ce pseudo ou ce mail n'existe pas.");
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/motDepasseOublie.jsp").forward(request, response);		
	}
}
