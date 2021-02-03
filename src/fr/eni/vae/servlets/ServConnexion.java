package fr.eni.vae.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.vae.bll.UtilisateurManager;
import fr.eni.vae.bo.Utilisateur;
import fr.eni.vae.dal.DALException;

@WebServlet("/connexion")
public class ServConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 365; // 1 an
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("successCreateUser") != null
				&& request.getParameter("successCreateUser").equals("1")) {
			request.setAttribute("MessageAjoutUser", "Votre compte vient d'être créé.");
		}
		if (request.getParameter("error") != null) {
			request.setAttribute("error", null);
		}
		
		Cookie[] cookies = request.getCookies();

	/*	for (Cookie c : cookies) {
			if ((c.getName().equals("LOGIN") || c.getName().equals("MDP")) && c.getValue() != null
					&& (!c.getValue().equals("") || c.getValue() != null)) {

				request.setAttribute("checkMemoire", "checked");
			}
		}
		*/
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		if (request.getParameter("successCreateUser") != null
				&& request.getParameter("successCreateUser").equals("1")) {
			doGet(request, response);
		}
		String vLogin = request.getParameter("sPseudo");
		String vMdp = request.getParameter("sMdp");
		String vMail = request.getParameter("sPseudo");

		HttpSession session = request.getSession();

		if ((vLogin != null || vMail != null) && vMdp != null) {
			Utilisateur vUtilisateur = null;

			try {
				vUtilisateur = UtilisateurManager.verification(vLogin, vMdp, vMail);
			} catch (DALException e) {
				e.printStackTrace();
			}
			// Ajout des informations utilisateur sur son profil

			session.setAttribute("Pseudo", vUtilisateur.getPseudo());
			session.setAttribute("Nom", vUtilisateur.getNom());
			session.setAttribute("Prenom", vUtilisateur.getPrenom());
			session.setAttribute("Mail", vUtilisateur.getMail());
			session.setAttribute("Telephone", vUtilisateur.getTelephone());
			session.setAttribute("Rue", vUtilisateur.getRue());
			session.setAttribute("Cp", vUtilisateur.getCp());
			session.setAttribute("Ville", vUtilisateur.getVille());
			session.setAttribute("Mdp", vUtilisateur.getMdp());
			session.setAttribute("Credit", vUtilisateur.getCredit());
			session.setAttribute("numUtil", vUtilisateur.getNumUtil());

			if (vUtilisateur != null && vUtilisateur.getPseudo() != null && vUtilisateur.getPseudo().equals(vLogin)
					&& vUtilisateur.getMdp() != null && vUtilisateur.getMdp().equals(vMdp)
					|| vUtilisateur != null && vUtilisateur.getMail() != null && vUtilisateur.getMail().equals(vMail)
							&& vUtilisateur.getMdp() != null && vUtilisateur.getMdp().equals(vMdp)) {

				session.setAttribute("sessionUtilisateur", vUtilisateur);
				if (request.getParameter("memoire") != null) {
					setCookie(response, "MDP", vMdp, COOKIE_MAX_AGE);
					setCookie(response, "LOGIN", vLogin, COOKIE_MAX_AGE);
				} else {
					setCookie(response, "MDP", null, COOKIE_MAX_AGE);
					setCookie(response, "LOGIN", null, COOKIE_MAX_AGE);
				}
				response.sendRedirect("./ServPagePrincipale");

			} else {
				request.setAttribute("error", "Mot de passe ou identifiant incorrect");
				session.setAttribute("sessionUtilisateur", null);
				doGet(request, response);
			}

		} else {
			session.setAttribute("sessionUtilisateur", null);
		}

	}

	private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge)
			throws UnsupportedEncodingException {
		Cookie cookie;
		if (valeur != null) {
			cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8").trim());
		} else {
			cookie = new Cookie(nom, null);
		}

		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
}
