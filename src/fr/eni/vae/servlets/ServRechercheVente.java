package fr.eni.vae.servlets;

import java.io.IOException;
import java.sql.SQLException;
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
import fr.eni.vae.bll.VenteManager;
import fr.eni.vae.bo.Categorie;
import fr.eni.vae.bo.Retrait;
import fr.eni.vae.bo.Utilisateur;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.DALException;

/**
 * Servlet implementation class rechercheVente
 */
@WebServlet("/rechercheVente")
public class ServRechercheVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// UTILISATION DE CETTE DOGET POUR REDIRIGER VERS LA JSP ENVOYER MESSAGE MEME
		// SIL N'Y A PAS DE RAPPORT
		String pseudo;

		pseudo = request.getParameter("pseudo");
		request.setAttribute("pseudo", pseudo);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/envoyerMessage.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// affichage des ventes dans la page principale
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String nom = null;
		int num;
		List<Vente> ventes = new ArrayList<>();
		Utilisateur user = new Utilisateur();

		// RECUP SESSION
		HttpSession session = request.getSession();
		user = (Utilisateur) session.getAttribute("sessionUtilisateur");
		List<Integer> numVente = new ArrayList<>();
		Retrait retrait = new Retrait();
		List<Retrait> listRetrait = new ArrayList<>();
		int numCate = Integer.parseInt(request.getParameter("sCategorie"));

		try {
			// AUCUN FILMTRE ACTIF
			if (request.getParameter("mVentes") == null && request.getParameter("mEnchere") == null
					&& request.getParameter("mAcquisitions") == null && request.getParameter("mAutres") == null) {
				// *Par cat�gorie* recherche des ventes d'une cat�gorie sans condition sur le
				// nom
				if (numCate == 0 && request.getParameter("sRecherche").isEmpty()) {
					ventes = VenteManager.listeVentes();
					System.out.println("1");
				} else if ((numCate != 0) && request.getParameter("sRecherche").isEmpty()) {
					ventes = VenteManager.ventesCategorie(numCate);
				}
				// *Par nom* recherche des ventes � partir du nom, sans condition sur la
				// cat�gorie
				if (request.getParameter("sRecherche") != null && !request.getParameter("sRecherche").isEmpty()
						&& numCate == 0) {
					nom = (request.getParameter("sRecherche"));
					ventes = VenteManager.listeVenteArticle(nom);
				}
			}
			// gestion des cas o� les filtres sont activ�s
			// *Par utilisateur* recherche des Ventes de l'utilisateur connect�
			// (mes ventes coch�, cat�gorie � Toutes, rien dans nom)
			if (request.getParameter("mVentes") != null && numCate == 0
					&& request.getParameter("sRecherche").isEmpty()) {
				ventes = VenteManager.ventesUtilisateur(user.getNumUtil());
			}
			// *Par utilisateur et cat�gorie* recherche des ventes de l'utilisateur dans une
			// cat�gorie
			// (mes ventes coch�, cat�gorie!= Toutes, rien dans nom)

			if (request.getParameter("mVentes") != null && numCate != 0
					&& request.getParameter("sRecherche").isEmpty()) {
				ventes = VenteManager.listeUtCat(numCate, user.getNumUtil());
			}
			// *Par utilisateur, cat�gorie et nom*
			// (mes ventes coch�, cat�gorie!= Toutes, nom saisi)
			if (request.getParameter("mVentes") != null && numCate != 0
					&& !request.getParameter("sRecherche").isEmpty()) {
				nom = request.getParameter("sRecherche");
				ventes = VenteManager.listeUtNomCat(nom, user.getNumUtil(), numCate);
			}
			// recherche ventes utilisateur � partir du nom de l'article
			if (numCate == 0 && !request.getParameter("sRecherche").isEmpty()
					&& request.getParameter("mVentes") != null) {
				nom = request.getParameter("sRecherche");
				ventes = VenteManager.listeUtNom(request.getParameter("sRecherche"), user.getNumUtil());
			}
			// *Mes ench�res* recherche des ventes pour lesquelles le user a fait une
			// enchère
			if (request.getParameter("mEnchere") != null) {
				ArrayList<Integer> listV = new ArrayList<>();
				listV = EnchereManager.quelleVenteEncherie(user.getNumUtil());
				if (listV != null) {
					// *Mes ench�res par cat�gorie et/ou nom*
					if (numCate != 0) {
						if (!request.getParameter("sRecherche").isEmpty()) {
							nom = request.getParameter("sRecherche");
							Categorie cate = new Categorie();
							for (Integer no : listV) {
								if (VenteManager.AfficherEnchereEnCours(no) != null) {
									ventes.add(VenteManager.mesEncheresParNomCat(no, numCate,
											request.getParameter("sRecherche")));
								}
							}
						}
					} else {

						for (Integer no : listV) {
							if (((Vente) VenteManager.AfficherEnchereEnCours(no)).getNumVente() != 0) {
								ventes.add((Vente) VenteManager.AfficherEnchereEnCours(no));
							}
						}
					}
				}
			}
			// *Mes acquisitions* ench�res termin�es que j'ai gagn�es
			if (request.getParameter("mAcquisitions") != null) {
				ArrayList<Integer> listV = new ArrayList<>();
				listV = EnchereManager.quelleVenteEncherie(user.getNumUtil());
				if (listV != null) {
					for (Integer no : listV) {
						if (VenteManager.mesAcquisitions(no).getNumVente() != 0) {
							ventes.add(VenteManager.mesAcquisitions(no));
							System.out.println(ventes);
						}
					}
				}
			}
		} catch (DALException e) {
			e.printStackTrace();
		}

		for (Vente v : ventes) {
			num = v.getNumVente();
			numVente.add(num);
		}

		for (Integer test : numVente) {
			try {
				retrait = RetraitManager.AfficherRetrait(test);
				listRetrait.add(retrait);
			} catch (DALException | SQLException e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("listeRetrait", listRetrait);
		request.setAttribute("listeRecherchee", ventes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/PagePrincipale.jsp").include(request, response);

	}
}
